/*     */ package com.aionemu.gameserver.model.account;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class Account
/*     */   implements Iterable<PlayerAccountData>
/*     */ {
/*     */   private final int id;
/*     */   private String name;
/*     */   private byte accessLevel;
/*     */   private byte membership;
/*     */   private AccountTime accountTime;
/*  59 */   private Map<Integer, PlayerAccountData> players = new HashMap<Integer, PlayerAccountData>();
/*     */   
/*     */   private Storage accountWarehouse;
/*     */ 
/*     */   
/*     */   public Account(int id) {
/*  65 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/*  70 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  80 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccountTime getAccountTime() {
/*  85 */     return this.accountTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAccountTime(AccountTime accountTime) {
/*  90 */     this.accountTime = accountTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getAccessLevel() {
/*  98 */     return this.accessLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccessLevel(byte accessLevel) {
/* 106 */     this.accessLevel = accessLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getMembership() {
/* 114 */     return this.membership;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMembership(byte membership) {
/* 122 */     this.membership = membership;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 128 */     if (this == o)
/*     */     {
/* 130 */       return true;
/*     */     }
/*     */     
/* 133 */     if (!(o instanceof Account))
/*     */     {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     Account account = (Account)o;
/*     */     
/* 140 */     return (this.id == account.id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerAccountData getPlayerAccountData(int chaOid) {
/* 155 */     return this.players.get(Integer.valueOf(chaOid));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayerAccountData(PlayerAccountData accPlData) {
/* 163 */     this.players.put(Integer.valueOf(accPlData.getPlayerCommonData().getPlayerObjId()), accPlData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Storage getAccountWarehouse() {
/* 171 */     return this.accountWarehouse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccountWarehouse(Storage accountWarehouse) {
/* 179 */     this.accountWarehouse = accountWarehouse;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 185 */     return this.players.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<PlayerAccountData> getSortedAccountsList() {
/* 193 */     ArrayList<PlayerAccountData> list = new ArrayList<PlayerAccountData>();
/* 194 */     list.addAll(this.players.values());
/* 195 */     Collections.sort(list, new Comparator<PlayerAccountData>()
/*     */         {
/*     */           public int compare(PlayerAccountData x, PlayerAccountData y)
/*     */           {
/* 199 */             Timestamp t1 = x.getPlayerCommonData().getLastOnline();
/* 200 */             Timestamp t2 = y.getPlayerCommonData().getLastOnline();
/* 201 */             if (t2 == null)
/* 202 */               return 1; 
/* 203 */             if (t1 == null)
/* 204 */               return -1; 
/* 205 */             return y.getPlayerCommonData().getLastOnline().compareTo(x.getPlayerCommonData().getLastOnline());
/*     */           }
/*     */         });
/* 208 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<PlayerAccountData> iterator() {
/* 217 */     return this.players.values().iterator();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\account\Account.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */