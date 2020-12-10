/*    */ package com.aionemu.gameserver.model;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlEnum
/*    */ public enum Race
/*    */ {
/* 35 */   ELYOS(0),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   ASMODIANS(1),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   LYCAN(2),
/* 46 */   CONSTRUCT(3),
/* 47 */   CARRIER(4),
/* 48 */   DRAKAN(5),
/* 49 */   LIZARDMAN(6),
/* 50 */   TELEPORTER(7),
/* 51 */   NAGA(8),
/* 52 */   BROWNIE(9),
/* 53 */   KRALL(10),
/* 54 */   SHULACK(11),
/* 55 */   BARRIER(12),
/* 56 */   PC_LIGHT_CASTLE_DOOR(13),
/* 57 */   PC_DARK_CASTLE_DOOR(14),
/* 58 */   DRAGON_CASTLE_DOOR(15),
/* 59 */   GCHIEF_LIGHT(16),
/* 60 */   GCHIEF_DARK(17),
/* 61 */   DRAGON(18),
/* 62 */   OUTSIDER(19),
/* 63 */   RATMAN(20),
/* 64 */   DEMIHUMANOID(21),
/* 65 */   UNDEAD(22),
/* 66 */   BEAST(23),
/* 67 */   MAGICALMONSTER(24),
/* 68 */   ELEMENTAL(25),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 73 */   NONE(26),
/* 74 */   PC_ALL(27);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int raceId;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Race(int raceId) {
/* 89 */     this.raceId = raceId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRaceId() {
/* 99 */     return this.raceId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\Race.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */