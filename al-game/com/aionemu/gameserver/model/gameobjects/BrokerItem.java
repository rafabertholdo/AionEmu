/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.model.broker.BrokerRace;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Comparator;
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
/*     */ public class BrokerItem
/*     */   implements Comparable<BrokerItem>
/*     */ {
/*     */   private Item item;
/*     */   private int itemId;
/*     */   private int itemUniqueId;
/*     */   private long itemCount;
/*     */   private long price;
/*     */   private String seller;
/*     */   private int sellerId;
/*     */   private BrokerRace itemBrokerRace;
/*     */   private boolean isSold;
/*     */   private boolean isSettled;
/*     */   private Timestamp expireTime;
/*     */   private Timestamp settleTime;
/*     */   PersistentState state;
/*     */   
/*     */   public BrokerItem(Item item, long price, String seller, int sellerId, BrokerRace itemBrokerRace) {
/*  57 */     this.item = item;
/*  58 */     this.itemId = item.getItemTemplate().getTemplateId();
/*  59 */     this.itemUniqueId = item.getObjectId();
/*  60 */     this.itemCount = item.getItemCount();
/*  61 */     this.price = price;
/*  62 */     this.seller = seller;
/*  63 */     this.sellerId = sellerId;
/*  64 */     this.itemBrokerRace = itemBrokerRace;
/*  65 */     this.isSold = false;
/*  66 */     this.isSettled = false;
/*  67 */     this.expireTime = new Timestamp(Calendar.getInstance().getTimeInMillis() + 691200000L);
/*  68 */     this.settleTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
/*     */     
/*  70 */     this.state = PersistentState.NEW;
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
/*     */ 
/*     */   
/*     */   public BrokerItem(Item item, int itemId, int itemUniqueId, long itemCount, long price, String seller, int sellerId, BrokerRace itemBrokerRace, boolean isSold, boolean isSettled, Timestamp expireTime, Timestamp settleTime) {
/*  84 */     this.item = item;
/*  85 */     this.itemId = itemId;
/*  86 */     this.itemUniqueId = itemUniqueId;
/*  87 */     this.price = price;
/*  88 */     this.seller = seller;
/*  89 */     this.sellerId = sellerId;
/*  90 */     this.itemBrokerRace = itemBrokerRace;
/*  91 */     this.itemCount = itemCount;
/*  92 */     if (item == null) {
/*     */       
/*  94 */       this.isSold = true;
/*  95 */       this.isSettled = true;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 100 */       this.isSold = isSold;
/* 101 */       this.isSettled = isSettled;
/*     */     } 
/*     */     
/* 104 */     this.expireTime = expireTime;
/* 105 */     this.settleTime = settleTime;
/*     */     
/* 107 */     this.state = PersistentState.NOACTION;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 116 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeItem() {
/* 121 */     this.item = null;
/* 122 */     this.isSold = true;
/* 123 */     this.isSettled = true;
/* 124 */     this.settleTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemId() {
/* 129 */     return this.itemId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemUniqueId() {
/* 134 */     return this.itemUniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getPrice() {
/* 143 */     return this.price;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeller() {
/* 152 */     return this.seller;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSellerId() {
/* 157 */     return this.sellerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrokerRace getItemBrokerRace() {
/* 166 */     return this.itemBrokerRace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSold() {
/* 175 */     return this.isSold;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 180 */     switch (persistentState) {
/*     */       
/*     */       case DELETED:
/* 183 */         if (this.state == PersistentState.NEW) {
/* 184 */           this.state = PersistentState.NOACTION;
/*     */         } else {
/* 186 */           this.state = PersistentState.DELETED;
/*     */         }  return;
/*     */       case UPDATE_REQUIRED:
/* 189 */         if (this.state == PersistentState.NEW)
/*     */           return;  break;
/*     */     } 
/* 192 */     this.state = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 199 */     return this.state;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSettled() {
/* 204 */     return this.isSettled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSettled() {
/* 209 */     this.isSettled = true;
/* 210 */     this.settleTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
/*     */   }
/*     */ 
/*     */   
/*     */   public Timestamp getExpireTime() {
/* 215 */     return this.expireTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public Timestamp getSettleTime() {
/* 220 */     return this.settleTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getItemCount() {
/* 225 */     return this.itemCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getItemLevel() {
/* 233 */     return this.item.getItemTemplate().getLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getPiecePrice() {
/* 241 */     return getPrice() / getItemCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getItemName() {
/* 249 */     return this.item.getItemName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(BrokerItem o) {
/* 258 */     return (this.itemUniqueId > o.getItemUniqueId()) ? 1 : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 264 */   static Comparator<BrokerItem> NAME_SORT_ASC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 269 */         if (o1 == null || o2 == null)
/* 270 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 271 */         return o1.getItemName().compareTo(o2.getItemName());
/*     */       }
/*     */     };
/*     */   
/* 275 */   static Comparator<BrokerItem> NAME_SORT_DESC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 280 */         if (o1 == null || o2 == null)
/* 281 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 282 */         return o1.getItemName().compareTo(o2.getItemName());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 289 */   static Comparator<BrokerItem> PRICE_SORT_ASC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 294 */         if (o1 == null || o2 == null)
/* 295 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 296 */         if (o1.getPrice() == o2.getPrice())
/* 297 */           return 0; 
/* 298 */         return (o1.getPrice() > o2.getPrice()) ? 1 : -1;
/*     */       }
/*     */     };
/*     */   
/* 302 */   static Comparator<BrokerItem> PRICE_SORT_DESC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 307 */         if (o1 == null || o2 == null)
/* 308 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 309 */         if (o1.getPrice() == o2.getPrice())
/* 310 */           return 0; 
/* 311 */         return (o1.getPrice() > o2.getPrice()) ? -1 : 1;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 318 */   static Comparator<BrokerItem> PIECE_PRICE_SORT_ASC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 323 */         if (o1 == null || o2 == null)
/* 324 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 325 */         if (o1.getPiecePrice() == o2.getPiecePrice())
/* 326 */           return 0; 
/* 327 */         return (o1.getPiecePrice() > o2.getPiecePrice()) ? 1 : -1;
/*     */       }
/*     */     };
/*     */   
/* 331 */   static Comparator<BrokerItem> PIECE_PRICE_SORT_DESC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 336 */         if (o1 == null || o2 == null)
/* 337 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 338 */         if (o1.getPiecePrice() == o2.getPiecePrice())
/* 339 */           return 0; 
/* 340 */         return (o1.getPiecePrice() > o2.getPiecePrice()) ? -1 : 1;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 347 */   static Comparator<BrokerItem> LEVEL_SORT_ASC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 352 */         if (o1 == null || o2 == null)
/* 353 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 354 */         if (o1.getItemLevel() == o2.getItemLevel())
/* 355 */           return 0; 
/* 356 */         return (o1.getItemLevel() > o2.getItemLevel()) ? 1 : -1;
/*     */       }
/*     */     };
/*     */   
/* 360 */   static Comparator<BrokerItem> LEVEL_SORT_DESC = new Comparator<BrokerItem>()
/*     */     {
/*     */       
/*     */       public int compare(BrokerItem o1, BrokerItem o2)
/*     */       {
/* 365 */         if (o1 == null || o2 == null)
/* 366 */           return BrokerItem.comparePossiblyNull((T)o1, (T)o2); 
/* 367 */         if (o1.getItemLevel() == o2.getItemLevel())
/* 368 */           return 0; 
/* 369 */         return (o1.getItemLevel() > o2.getItemLevel()) ? -1 : 1;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private static <T extends Comparable<T>> int comparePossiblyNull(T aThis, T aThat) {
/* 375 */     int result = 0;
/* 376 */     if (aThis == null && aThat != null) {
/*     */       
/* 378 */       result = -1;
/*     */     }
/* 380 */     else if (aThis != null && aThat == null) {
/*     */       
/* 382 */       result = 1;
/*     */     } 
/* 384 */     return result;
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
/*     */ 
/*     */   
/*     */   public static Comparator<BrokerItem> getComparatoryByType(int sortType) {
/* 398 */     switch (sortType) {
/*     */       
/*     */       case 0:
/* 401 */         return NAME_SORT_ASC;
/*     */       case 1:
/* 403 */         return NAME_SORT_DESC;
/*     */       case 2:
/* 405 */         return LEVEL_SORT_ASC;
/*     */       case 3:
/* 407 */         return LEVEL_SORT_DESC;
/*     */       case 4:
/* 409 */         return PRICE_SORT_ASC;
/*     */       case 5:
/* 411 */         return PRICE_SORT_DESC;
/*     */       case 6:
/* 413 */         return PIECE_PRICE_SORT_ASC;
/*     */       case 7:
/* 415 */         return PIECE_PRICE_SORT_DESC;
/*     */     } 
/* 417 */     throw new IllegalArgumentException("Illegal sort type for broker items");
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\BrokerItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */