/*     */ package com.aionemu.gameserver.model.legion;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.LegionConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
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
/*     */ public class Legion
/*     */ {
/*     */   private static final int PERMISSION1_MIN = 96;
/*     */   private static final int PERMISSION2_MIN = 0;
/*     */   private static final int LEGIONAR_PERMISSION2_MAX = 8;
/*     */   private static final int CENTURION_PERMISSION1_MAX = 124;
/*     */   private static final int CENTURION_PERMISSION2_MAX = 14;
/*  47 */   private int legionId = 0;
/*  48 */   private String legionName = "";
/*  49 */   private int legionLevel = 1;
/*  50 */   private int legionRank = 0;
/*  51 */   private int contributionPoints = 0;
/*  52 */   private List<Integer> legionMembers = new ArrayList<Integer>();
/*     */   private static final int legionarPermission1 = 64;
/*  54 */   private int legionarPermission2 = 0;
/*  55 */   private int centurionPermission1 = 96;
/*  56 */   private int centurionPermission2 = 0;
/*     */   private int disbandTime;
/*  58 */   private TreeMap<Timestamp, String> announcementList = new TreeMap<Timestamp, String>();
/*  59 */   private LegionEmblem legionEmblem = new LegionEmblem();
/*     */ 
/*     */ 
/*     */   
/*     */   private LegionWarehouse legionWarehouse;
/*     */ 
/*     */   
/*     */   private SortedSet<LegionHistory> legionHistory;
/*     */ 
/*     */ 
/*     */   
/*     */   public Legion(int legionId, String legionName) {
/*  71 */     this();
/*  72 */     this.legionId = legionId;
/*  73 */     this.legionName = legionName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Legion() {
/*  84 */     this.legionWarehouse = new LegionWarehouse(this);
/*  85 */     this.legionHistory = new TreeSet<LegionHistory>(new Comparator<LegionHistory>()
/*     */         {
/*     */           
/*     */           public int compare(LegionHistory o1, LegionHistory o2)
/*     */           {
/*  90 */             return (o1.getTime().getTime() < o2.getTime().getTime()) ? 1 : -1;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegionId(int legionId) {
/* 102 */     this.legionId = legionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLegionId() {
/* 110 */     return this.legionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegionName(String legionName) {
/* 119 */     this.legionName = legionName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLegionName() {
/* 127 */     return this.legionName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegionMembers(ArrayList<Integer> legionMembers) {
/* 136 */     this.legionMembers = legionMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> getLegionMembers() {
/* 144 */     return this.legionMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Player> getOnlineLegionMembers() {
/* 152 */     ArrayList<Player> onlineLegionMembers = new ArrayList<Player>();
/* 153 */     for (Iterator<Integer> i$ = this.legionMembers.iterator(); i$.hasNext(); ) { int legionMemberObjId = ((Integer)i$.next()).intValue();
/*     */       
/* 155 */       Player onlineLegionMember = World.getInstance().findPlayer(legionMemberObjId);
/* 156 */       if (onlineLegionMember != null)
/* 157 */         onlineLegionMembers.add(onlineLegionMember);  }
/*     */     
/* 159 */     return onlineLegionMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addLegionMember(int playerObjId) {
/* 169 */     if (canAddMember()) {
/*     */       
/* 171 */       this.legionMembers.add(Integer.valueOf(playerObjId));
/* 172 */       return true;
/*     */     } 
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteLegionMember(int playerObjId) {
/* 184 */     this.legionMembers.remove(new Integer(playerObjId));
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
/*     */   public boolean setLegionPermissions(int legionarPermission2, int centurionPermission1, int centurionPermission2) {
/* 197 */     if (checkPermissions(legionarPermission2, centurionPermission1, centurionPermission2)) {
/*     */       
/* 199 */       this.legionarPermission2 = legionarPermission2;
/* 200 */       this.centurionPermission1 = centurionPermission1;
/* 201 */       this.centurionPermission2 = centurionPermission2;
/* 202 */       return true;
/*     */     } 
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkPermissions(int legionarPermission2, int centurionPermission1, int centurionPermission2) {
/* 214 */     if (legionarPermission2 < 0 || legionarPermission2 > 8)
/* 215 */       return false; 
/* 216 */     if (centurionPermission1 < 96 || centurionPermission1 > 124)
/* 217 */       return false; 
/* 218 */     if (centurionPermission2 < 0 || centurionPermission2 > 14)
/* 219 */       return false; 
/* 220 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLegionarPermission1() {
/* 228 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLegionarPermission2() {
/* 236 */     return this.legionarPermission2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCenturionPermission1() {
/* 244 */     return this.centurionPermission1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCenturionPermission2() {
/* 252 */     return this.centurionPermission2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLegionLevel() {
/* 260 */     return this.legionLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegionLevel(int legionLevel) {
/* 268 */     this.legionLevel = legionLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegionRank(int legionRank) {
/* 277 */     this.legionRank = legionRank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLegionRank() {
/* 285 */     return this.legionRank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addContributionPoints(int contributionPoints) {
/* 294 */     this.contributionPoints += contributionPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContributionPoints(int contributionPoints) {
/* 302 */     this.contributionPoints = contributionPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContributionPoints() {
/* 310 */     return this.contributionPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRequiredMembers() {
/* 320 */     switch (getLegionLevel()) {
/*     */       
/*     */       case 1:
/* 323 */         if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL2_REQUIRED_MEMBERS)
/* 324 */           return true; 
/*     */         break;
/*     */       case 2:
/* 327 */         if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL3_REQUIRED_MEMBERS)
/* 328 */           return true; 
/*     */         break;
/*     */       case 3:
/* 331 */         if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL4_REQUIRED_MEMBERS)
/* 332 */           return true; 
/*     */         break;
/*     */       case 4:
/* 335 */         if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL5_REQUIRED_MEMBERS)
/* 336 */           return true; 
/*     */         break;
/*     */     } 
/* 339 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKinahPrice() {
/* 349 */     switch (getLegionLevel()) {
/*     */       
/*     */       case 1:
/* 352 */         return LegionConfig.LEGION_LEVEL2_REQUIRED_KINAH;
/*     */       case 2:
/* 354 */         return LegionConfig.LEGION_LEVEL3_REQUIRED_KINAH;
/*     */       case 3:
/* 356 */         return LegionConfig.LEGION_LEVEL4_REQUIRED_KINAH;
/*     */       case 4:
/* 358 */         return LegionConfig.LEGION_LEVEL5_REQUIRED_KINAH;
/*     */     } 
/* 360 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContributionPrice() {
/* 370 */     switch (getLegionLevel()) {
/*     */       
/*     */       case 1:
/* 373 */         return LegionConfig.LEGION_LEVEL2_REQUIRED_CONTRIBUTION;
/*     */       case 2:
/* 375 */         return LegionConfig.LEGION_LEVEL3_REQUIRED_CONTRIBUTION;
/*     */       case 3:
/* 377 */         return LegionConfig.LEGION_LEVEL4_REQUIRED_CONTRIBUTION;
/*     */       case 4:
/* 379 */         return LegionConfig.LEGION_LEVEL5_REQUIRED_CONTRIBUTION;
/*     */     } 
/* 381 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canAddMember() {
/* 391 */     switch (getLegionLevel()) {
/*     */       
/*     */       case 1:
/* 394 */         if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL1_MAX_MEMBERS)
/* 395 */           return true; 
/*     */         break;
/*     */       case 2:
/* 398 */         if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL2_MAX_MEMBERS)
/* 399 */           return true; 
/*     */         break;
/*     */       case 3:
/* 402 */         if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL3_MAX_MEMBERS)
/* 403 */           return true; 
/*     */         break;
/*     */       case 4:
/* 406 */         if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL4_MAX_MEMBERS)
/* 407 */           return true; 
/*     */         break;
/*     */       case 5:
/* 410 */         if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL5_MAX_MEMBERS)
/* 411 */           return true; 
/*     */         break;
/*     */     } 
/* 414 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnouncementList(TreeMap<Timestamp, String> announcementList) {
/* 423 */     this.announcementList = announcementList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnnouncementToList(Timestamp unixTime, String announcement) {
/* 431 */     this.announcementList.put(unixTime, announcement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFirstEntry() {
/* 439 */     this.announcementList.remove(this.announcementList.firstEntry().getKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeMap<Timestamp, String> getAnnouncementList() {
/* 447 */     return this.announcementList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map.Entry<Timestamp, String> getCurrentAnnouncement() {
/* 455 */     if (this.announcementList.size() > 0)
/* 456 */       return this.announcementList.lastEntry(); 
/* 457 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisbandTime(int disbandTime) {
/* 466 */     this.disbandTime = disbandTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisbandTime() {
/* 474 */     return this.disbandTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisbanding() {
/* 482 */     if (this.disbandTime > 0)
/*     */     {
/* 484 */       return true;
/*     */     }
/* 486 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMember(int playerObjId) {
/* 497 */     return this.legionMembers.contains(Integer.valueOf(playerObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegionEmblem(LegionEmblem legionEmblem) {
/* 506 */     this.legionEmblem = legionEmblem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionEmblem getLegionEmblem() {
/* 514 */     return this.legionEmblem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegionWarehouse(LegionWarehouse legionWarehouse) {
/* 523 */     this.legionWarehouse = legionWarehouse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionWarehouse getLegionWarehouse() {
/* 531 */     return this.legionWarehouse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWarehouseSlots() {
/* 541 */     switch (getLegionLevel()) {
/*     */       
/*     */       case 1:
/* 544 */         return 24;
/*     */       case 2:
/* 546 */         return 32;
/*     */       case 3:
/* 548 */         return 40;
/*     */       case 4:
/* 550 */         return 48;
/*     */       case 5:
/* 552 */         return 56;
/*     */     } 
/* 554 */     return 24;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<LegionHistory> getLegionHistory() {
/* 562 */     return this.legionHistory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHistory(LegionHistory history) {
/* 570 */     this.legionHistory.add(history);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\Legion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */