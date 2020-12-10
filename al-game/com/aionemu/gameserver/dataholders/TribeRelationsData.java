/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.tribe.AggroRelations;
/*     */ import com.aionemu.gameserver.model.templates.tribe.FriendlyRelations;
/*     */ import com.aionemu.gameserver.model.templates.tribe.HostileRelations;
/*     */ import com.aionemu.gameserver.model.templates.tribe.NeutralRelations;
/*     */ import com.aionemu.gameserver.model.templates.tribe.SupportRelations;
/*     */ import com.aionemu.gameserver.model.templates.tribe.Tribe;
/*     */ import gnu.trove.THashMap;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlRootElement(name = "tribe_relations")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class TribeRelationsData
/*     */ {
/*     */   @XmlElement(name = "tribe", required = true)
/*     */   protected List<Tribe> tribeList;
/*  47 */   protected THashMap<String, Tribe> tribeNameMap = new THashMap();
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  51 */     for (Tribe tribe : this.tribeList)
/*     */     {
/*  53 */       this.tribeNameMap.put(tribe.getName(), tribe);
/*     */     }
/*  55 */     this.tribeList = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  63 */     return this.tribeNameMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAggressiveRelations(String tribeName) {
/*  73 */     Tribe tribe = (Tribe)this.tribeNameMap.get(tribeName);
/*  74 */     if (tribe == null)
/*  75 */       return false; 
/*  76 */     AggroRelations aggroRelations = tribe.getAggroRelations();
/*  77 */     return (aggroRelations != null && !aggroRelations.getTo().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasHostileRelations(String tribeName) {
/*  87 */     Tribe tribe = (Tribe)this.tribeNameMap.get(tribeName);
/*  88 */     if (tribe == null)
/*  89 */       return false; 
/*  90 */     HostileRelations hostileRelations = tribe.getHostileRelations();
/*  91 */     return (hostileRelations != null && !hostileRelations.getTo().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggressiveRelation(String tribeName1, String tribeName2) {
/* 102 */     Tribe tribe1 = (Tribe)this.tribeNameMap.get(tribeName1);
/* 103 */     if (tribe1 == null)
/* 104 */       return false; 
/* 105 */     AggroRelations aggroRelations = tribe1.getAggroRelations();
/* 106 */     if (aggroRelations == null) {
/* 107 */       return false;
/*     */     }
/* 109 */     return aggroRelations.getTo().contains(tribeName2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportRelation(String tribeName1, String tribeName2) {
/* 120 */     Tribe tribe1 = (Tribe)this.tribeNameMap.get(tribeName1);
/* 121 */     if (tribe1 == null)
/* 122 */       return false; 
/* 123 */     SupportRelations supportRelations = tribe1.getSupportRelations();
/* 124 */     if (supportRelations == null)
/* 125 */       return false; 
/* 126 */     return supportRelations.getTo().contains(tribeName2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFriendlyRelation(String tribeName1, String tribeName2) {
/* 137 */     Tribe tribe1 = (Tribe)this.tribeNameMap.get(tribeName1);
/* 138 */     if (tribe1 == null)
/* 139 */       return false; 
/* 140 */     FriendlyRelations friendlyRelations = tribe1.getFriendlyRelations();
/* 141 */     if (friendlyRelations == null)
/* 142 */       return false; 
/* 143 */     return friendlyRelations.getTo().contains(tribeName2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNeutralRelation(String tribeName1, String tribeName2) {
/* 154 */     Tribe tribe1 = (Tribe)this.tribeNameMap.get(tribeName1);
/* 155 */     if (tribe1 == null)
/* 156 */       return false; 
/* 157 */     NeutralRelations neutralRelations = tribe1.getNeutralRelations();
/* 158 */     if (neutralRelations == null)
/* 159 */       return false; 
/* 160 */     return neutralRelations.getTo().contains(tribeName2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHostileRelation(String tribeName1, String tribeName2) {
/* 171 */     Tribe tribe1 = (Tribe)this.tribeNameMap.get(tribeName1);
/* 172 */     if (tribe1 == null)
/* 173 */       return false; 
/* 174 */     HostileRelations hostileRelations = tribe1.getHostileRelations();
/* 175 */     if (hostileRelations == null)
/* 176 */       return false; 
/* 177 */     return hostileRelations.getTo().contains(tribeName2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGuardDark(String tribeName) {
/* 187 */     Tribe tribe = (Tribe)this.tribeNameMap.get(tribeName);
/* 188 */     if (tribe == null) {
/* 189 */       return false;
/*     */     }
/* 191 */     if ("GUARD_DARK".equals(tribe.getName())) {
/* 192 */       return true;
/*     */     }
/* 194 */     String baseTribe = tribe.getBase();
/* 195 */     if (baseTribe != null) {
/* 196 */       return isGuardDark(baseTribe);
/*     */     }
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGuardLight(String tribeName) {
/* 208 */     Tribe tribe = (Tribe)this.tribeNameMap.get(tribeName);
/* 209 */     if (tribe == null) {
/* 210 */       return false;
/*     */     }
/* 212 */     if ("GUARD".equals(tribe.getName())) {
/* 213 */       return true;
/*     */     }
/* 215 */     String baseTribe = tribe.getBase();
/* 216 */     if (baseTribe != null) {
/* 217 */       return isGuardLight(baseTribe);
/*     */     }
/* 219 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\TribeRelationsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */