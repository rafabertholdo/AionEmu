package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.CacheConfig;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.LegionMemberDAO;
import com.aionemu.gameserver.dao.PlayerAppearanceDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.AccountTime;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.model.legion.LegionMember;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMap;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMapFactory;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class AccountService {
  private static final Logger log = Logger.getLogger(AccountService.class);

  private static CacheMap<Integer, Account> accountsMap = CacheMapFactory.createSoftCacheMap("Account", "account");

  public static Account getAccount(int accountId, String accountName, AccountTime accountTime, byte accessLevel,
      byte membership) {
    log.debug("[AS] request for account: " + accountId);

    Account account = (Account) accountsMap.get(Integer.valueOf(accountId));
    if (account == null) {

      account = loadAccount(accountId);

      if (CacheConfig.CACHE_ACCOUNTS) {
        accountsMap.put(Integer.valueOf(accountId), account);
      }
    }
    account.setName(accountName);
    account.setAccountTime(accountTime);
    account.setAccessLevel(accessLevel);
    account.setMembership(membership);

    removeDeletedCharacters(account);

    return account;
  }

  private static void removeDeletedCharacters(Account account) {
    Iterator<PlayerAccountData> it = account.iterator();
    while (it.hasNext()) {

      PlayerAccountData pad = it.next();
      int deletionTime = pad.getDeletionTimeInSeconds() * 1000;
      if (deletionTime != 0 && deletionTime <= System.currentTimeMillis()) {

        it.remove();
        PlayerService.deletePlayerFromDB(pad.getPlayerCommonData().getPlayerObjId());
      }
    }
  }

  private static Account loadAccount(int accountId) {
    Account account = new Account(accountId);

    PlayerDAO playerDAO = (PlayerDAO) DAOManager.getDAO(PlayerDAO.class);
    PlayerAppearanceDAO appereanceDAO = (PlayerAppearanceDAO) DAOManager.getDAO(PlayerAppearanceDAO.class);

    List<Integer> playerOids = playerDAO.getPlayerOidsOnAccount(accountId);

    for (Iterator<Integer> i$ = playerOids.iterator(); i$.hasNext();) {
      int playerOid = ((Integer) i$.next()).intValue();

      PlayerCommonData playerCommonData = playerDAO.loadPlayerCommonData(playerOid);
      PlayerAppearance appereance = appereanceDAO.load(playerOid);

      LegionMember legionMember = ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class))
          .loadLegionMember(playerOid);

      List<Item> equipment = ((InventoryDAO) DAOManager.getDAO(InventoryDAO.class)).loadEquipment(playerOid);

      PlayerAccountData acData = new PlayerAccountData(playerCommonData, appereance, equipment, legionMember);

      playerDAO.setCreationDeletionTime(acData);

      account.addPlayerAccountData(acData);
    }

    Storage accWarehouse = ((InventoryDAO) DAOManager.getDAO(InventoryDAO.class)).loadStorage(null, account.getId(),
        StorageType.ACCOUNT_WAREHOUSE);
    ItemService.loadItemStones(accWarehouse.getStorageItems());
    account.setAccountWarehouse(accWarehouse);

    if (account.getAccountWarehouse() == null) {

      account.setAccountWarehouse(new Storage(StorageType.ACCOUNT_WAREHOUSE));
      account.getAccountWarehouse().setOwnerId(account.getId());
    }

    return account;
  }
}
