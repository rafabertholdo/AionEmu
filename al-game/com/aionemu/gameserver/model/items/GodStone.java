/*    */ package com.aionemu.gameserver.model.items;
/*    */ 
/*    */ import com.aionemu.commons.utils.Rnd;
/*    */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.item.GodstoneInfo;
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*    */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class GodStone
/*    */   extends ItemStone
/*    */ {
/* 39 */   private static final Logger log = Logger.getLogger(GodStone.class);
/*    */   
/*    */   private final GodstoneInfo godstoneInfo;
/*    */   
/*    */   private ActionObserver actionListener;
/*    */   private final int probability;
/*    */   private final int probabilityLeft;
/*    */   
/*    */   public GodStone(int itemObjId, int itemId, PersistentState persistentState) {
/* 48 */     super(itemObjId, itemId, 0, ItemStone.ItemStoneType.GODSTONE, persistentState);
/* 49 */     ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
/* 50 */     this.godstoneInfo = itemTemplate.getGodstoneInfo();
/*    */     
/* 52 */     if (this.godstoneInfo != null) {
/*    */       
/* 54 */       this.probability = this.godstoneInfo.getProbability();
/* 55 */       this.probabilityLeft = this.godstoneInfo.getProbabilityleft();
/*    */     }
/*    */     else {
/*    */       
/* 59 */       this.probability = 0;
/* 60 */       this.probabilityLeft = 0;
/* 61 */       log.warn("CHECKPOINT: Godstone info missing for item : " + itemId);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEquip(final Player player) {
/* 72 */     if (this.godstoneInfo == null) {
/*    */       return;
/*    */     }
/* 75 */     this.actionListener = new ActionObserver(ActionObserver.ObserverType.ATTACK)
/*    */       {
/*    */         public void attack(Creature creature)
/*    */         {
/* 79 */           int rand = Rnd.get(GodStone.this.probability - GodStone.this.probabilityLeft, GodStone.this.probability);
/* 80 */           if (rand > Rnd.get(0, 1000)) {
/*    */             
/* 82 */             Skill skill = SkillEngine.getInstance().getSkill((Creature)player, GodStone.this.godstoneInfo.getSkillid(), GodStone.this.godstoneInfo.getSkilllvl(), player.getTarget());
/*    */             
/* 84 */             skill.useSkill();
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 89 */     player.getObserveController().addObserver(this.actionListener);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUnEquip(Player player) {
/* 98 */     if (this.actionListener != null)
/* 99 */       player.getObserveController().removeObserver(this.actionListener); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\GodStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */