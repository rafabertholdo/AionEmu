/*    */ package com.aionemu.gameserver.model;
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
/*    */ public enum EmotionType
/*    */ {
/* 25 */   UNK(-1),
/* 26 */   SELECT_TARGET(0),
/* 27 */   JUMP(1),
/* 28 */   SIT(2),
/* 29 */   STAND(3),
/* 30 */   CHAIR_SIT(4),
/* 31 */   CHAIR_UP(5),
/* 32 */   START_FLYTELEPORT(6),
/* 33 */   LAND_FLYTELEPORT(7),
/* 34 */   FLY(11),
/* 35 */   LAND(12),
/* 36 */   DIE(16),
/* 37 */   RESURRECT(17),
/* 38 */   EMOTE(19),
/* 39 */   END_DUEL(20),
/* 40 */   ATTACKMODE(22),
/* 41 */   NEUTRALMODE(23),
/* 42 */   WALK(24),
/* 43 */   RUN(25),
/* 44 */   SWITCH_DOOR(29),
/* 45 */   START_EMOTE(30),
/* 46 */   OPEN_PRIVATESHOP(31),
/* 47 */   CLOSE_PRIVATESHOP(32),
/* 48 */   START_EMOTE2(33),
/* 49 */   POWERSHARD_ON(34),
/* 50 */   POWERSHARD_OFF(35),
/* 51 */   ATTACKMODE2(36),
/* 52 */   NEUTRALMODE2(37),
/* 53 */   START_LOOT(38),
/* 54 */   END_LOOT(39),
/* 55 */   START_QUESTLOOT(40),
/* 56 */   END_QUESTLOOT(41);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   EmotionType(int id) {
/* 62 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTypeId() {
/* 67 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static EmotionType getEmotionTypeById(int id) {
/* 73 */     for (EmotionType emotionType : values()) {
/*    */       
/* 75 */       if (emotionType.getTypeId() == id)
/* 76 */         return emotionType; 
/*    */     } 
/* 78 */     return UNK;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\EmotionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */