/*     */ package com.aionemu.gameserver.skillengine.effect;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MANTRA_EFFECT;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.concurrent.Future;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "AuraEffect")
/*     */ public class AuraEffect
/*     */   extends EffectTemplate
/*     */ {
/*     */   @XmlAttribute
/*     */   protected int distance;
/*     */   @XmlAttribute(name = "skill_id")
/*     */   protected int skillId;
/*     */   
/*     */   public void applyEffect(Effect effect) {
/*  52 */     effect.addToEffectedController();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculate(Effect effect) {
/*  58 */     effect.addSucessEffect(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPeriodicAction(Effect effect) {
/*  64 */     Player effector = (Player)effect.getEffector();
/*  65 */     if (effector.isInAlliance()) {
/*     */       
/*  67 */       for (PlayerAllianceMember allianceMember : effector.getPlayerAlliance().getMembersForGroup(effector.getObjectId()))
/*     */       {
/*  69 */         if (allianceMember.isOnline() && MathUtil.isIn3dRange((VisibleObject)effector, (VisibleObject)allianceMember.getPlayer(), (this.distance + 4)))
/*     */         {
/*  71 */           applyAuraTo(allianceMember.getPlayer());
/*     */         }
/*     */       }
/*     */     
/*  75 */     } else if (effector.isInGroup()) {
/*     */       
/*  77 */       for (Player member : effector.getPlayerGroup().getMembers())
/*     */       {
/*  79 */         if (MathUtil.isIn3dRange((VisibleObject)effector, (VisibleObject)member, (this.distance + 4)))
/*     */         {
/*  81 */           applyAuraTo(member);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  87 */       applyAuraTo(effector);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyAuraTo(Player effector) {
/*  97 */     SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.skillId);
/*  98 */     Effect e = new Effect((Creature)effector, (Creature)effector, template, template.getLvl(), template.getEffectsDuration());
/*  99 */     e.initialize();
/* 100 */     e.applyEffect();
/* 101 */     PacketSendUtility.broadcastPacket((VisibleObject)effector, (AionServerPacket)new SM_MANTRA_EFFECT(effector, this.skillId));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEffect(final Effect effect) {
/* 107 */     Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 112 */             AuraEffect.this.onPeriodicAction(effect);
/*     */           }
/*     */         },  0L, 6500L);
/* 115 */     effect.setPeriodicTask(task, this.position);
/*     */   }
/*     */   
/*     */   public void endEffect(Effect effect) {}
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\AuraEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */