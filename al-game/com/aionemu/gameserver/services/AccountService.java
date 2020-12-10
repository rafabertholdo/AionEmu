/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.configs.main.CacheConfig;
/*     */ import com.aionemu.gameserver.dao.InventoryDAO;
/*     */ import com.aionemu.gameserver.dao.LegionMemberDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerAppearanceDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerDAO;
/*     */ import com.aionemu.gameserver.model.account.Account;
/*     */ import com.aionemu.gameserver.model.account.AccountTime;
/*     */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.StorageType;
/*     */ import com.aionemu.gameserver.model.legion.LegionMember;
/*     */ import com.aionemu.gameserver.utils.collections.cachemap.CacheMap;
/*     */ import com.aionemu.gameserver.utils.collections.cachemap.CacheMapFactory;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccountService
/*     */ {
/*  50 */   private static final Logger log = Logger.getLogger(AccountService.class);
/*     */   
/*  52 */   private static CacheMap<Integer, Account> accountsMap = CacheMapFactory.createSoftCacheMap("Account", "account");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Account getAccount(int accountId, String accountName, AccountTime accountTime, byte accessLevel, byte membership) {
/*  67 */     log.debug("[AS] request for account: " + accountId);
/*     */     
/*  69 */     Account account = (Account)accountsMap.get(Integer.valueOf(accountId));
/*  70 */     if (account == null) {
/*     */       
/*  72 */       account = loadAccount(accountId);
/*     */       
/*  74 */       if (CacheConfig.CACHE_ACCOUNTS) {
/*  75 */         accountsMap.put(Integer.valueOf(accountId), account);
/*     */       }
/*     */     } 
/*  78 */     account.setName(accountName);
/*  79 */     account.setAccountTime(accountTime);
/*  80 */     account.setAccessLevel(accessLevel);
/*  81 */     account.setMembership(membership);
/*     */     
/*  83 */     removeDeletedCharacters(account);
/*     */     
/*  85 */     return account;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeDeletedCharacters(Account account) {
/*  96 */     Iterator<PlayerAccountData> it = account.iterator();
/*  97 */     while (it.hasNext()) {
/*     */       
/*  99 */       PlayerAccountData pad = it.next();
/* 100 */       int deletionTime = pad.getDeletionTimeInSeconds() * 1000;
/* 101 */       if (deletionTime != 0 && deletionTime <= System.currentTimeMillis()) {
/*     */         
/* 103 */         it.remove();
/* 104 */         PlayerService.deletePlayerFromDB(pad.getPlayerCommonData().getPlayerObjId());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Account loadAccount(int accountId) {
/* 118 */     Account account = new Account(accountId);
/*     */     
/* 120 */     PlayerDAO playerDAO = (PlayerDAO)DAOManager.getDAO(PlayerDAO.class);
/* 121 */     PlayerAppearanceDAO appereanceDAO = (PlayerAppearanceDAO)DAOManager.getDAO(PlayerAppearanceDAO.class);
/*     */     
/* 123 */     List<Integer> playerOids = playerDAO.getPlayerOidsOnAccount(accountId);
/*     */     
/* 125 */     for (Iterator<Integer> i$ = playerOids.iterator(); i$.hasNext(); ) { int playerOid = ((Integer)i$.next()).intValue();
/*     */       
/* 127 */       PlayerCommonData playerCommonData = playerDAO.loadPlayerCommonData(playerOid);
/* 128 */       PlayerAppearance appereance = appereanceDAO.load(playerOid);
/*     */ 
/*     */       
/* 131 */       LegionMember legionMember = ((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).loadLegionMember(playerOid);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       List<Item> equipment = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadEquipment(playerOid);
/*     */       
/* 138 */       PlayerAccountData acData = new PlayerAccountData(playerCommonData, appereance, equipment, legionMember);
/*     */       
/* 140 */       playerDAO.setCreationDeletionTime(acData);
/*     */       
/* 142 */       account.addPlayerAccountData(acData); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     Storage accWarehouse = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadStorage(null, account.getId(), StorageType.ACCOUNT_WAREHOUSE);
/* 149 */     ItemService.loadItemStones(accWarehouse.getStorageItems());
/* 150 */     account.setAccountWarehouse(accWarehouse);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (account.getAccountWarehouse() == null) {
/*     */       
/* 157 */       account.setAccountWarehouse(new Storage(StorageType.ACCOUNT_WAREHOUSE));
/* 158 */       account.getAccountWarehouse().setOwnerId(account.getId());
/*     */     } 
/*     */     
/* 161 */     return account;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\AccountService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */