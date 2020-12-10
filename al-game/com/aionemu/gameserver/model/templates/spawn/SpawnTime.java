/*    */ package com.aionemu.gameserver.model.templates.spawn;
/*    */ 
/*    */ import com.aionemu.gameserver.utils.gametime.DayTime;
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlType(name = "SpawnTime")
/*    */ @XmlEnum
/*    */ public enum SpawnTime
/*    */ {
/* 32 */   DAY,
/* 33 */   NIGHT;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAllowedDuring(DayTime dayTime) {
/* 41 */     switch (this) {
/*    */       
/*    */       case DAY:
/* 44 */         return (dayTime == DayTime.AFTERNOON || dayTime == DayTime.MORNING || dayTime == DayTime.EVENING);
/*    */       
/*    */       case NIGHT:
/* 47 */         return (dayTime == DayTime.NIGHT);
/*    */     } 
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\spawn\SpawnTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */