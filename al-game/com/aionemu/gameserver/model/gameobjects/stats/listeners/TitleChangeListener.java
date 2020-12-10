/*    */ package com.aionemu.gameserver.model.gameobjects.stats.listeners;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
/*    */ import com.aionemu.gameserver.model.templates.TitleTemplate;
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
/*    */ public class TitleChangeListener
/*    */ {
/*    */   public static void onTitleChange(CreatureGameStats<?> cgs, int titleId, boolean isSet) {
/* 33 */     TitleTemplate tt = DataManager.TITLE_DATA.getTitleTemplate(titleId);
/* 34 */     if (tt == null) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     StatEffectId eid = StatEffectId.getInstance(tt.getTitleId(), StatEffectType.TITLE_EFFECT);
/* 39 */     if (!isSet) {
/*    */       
/* 41 */       cgs.endEffect(eid);
/*    */     }
/*    */     else {
/*    */       
/* 45 */       cgs.addModifiers(eid, tt.getModifiers());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\listeners\TitleChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */