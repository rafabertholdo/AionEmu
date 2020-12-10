/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*     */ import com.aionemu.gameserver.controllers.movement.StartMovingListener;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Gatherable;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.GatherableTemplate;
/*     */ import com.aionemu.gameserver.model.templates.gather.Material;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.services.RespawnService;
/*     */ import com.aionemu.gameserver.skillengine.task.GatheringTask;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
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
/*     */ public class GatherableController
/*     */   extends VisibleObjectController<Gatherable>
/*     */ {
/*     */   private int gatherCount;
/*     */   private int currentGatherer;
/*     */   private GatheringTask task;
/*     */   
/*     */   public enum GatherState
/*     */   {
/*  52 */     GATHERED,
/*  53 */     GATHERING,
/*  54 */     IDLE;
/*     */   }
/*     */   
/*  57 */   private GatherState state = GatherState.IDLE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStartUse(final Player player) {
/*  67 */     GatherableTemplate template = getOwner().getObjectTemplate();
/*     */     
/*  69 */     if (!checkPlayerSkill(player, template)) {
/*     */       return;
/*     */     }
/*  72 */     List<Material> materials = template.getMaterials().getMaterial();
/*     */     
/*  74 */     int index = 0;
/*  75 */     Material material = materials.get(index);
/*  76 */     int count = materials.size();
/*     */     
/*  78 */     if (count < 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (count != 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       if (player.getInventory().isFull()) {
/*     */         
/*  93 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.EXTRACT_GATHER_INVENTORY_IS_FULL());
/*     */         
/*     */         return;
/*     */       } 
/*  97 */       int gatherRate = 1;
/*  98 */       float maxrate = 0.0F;
/*  99 */       int rate = 0;
/* 100 */       int i = 0;
/*     */ 
/*     */       
/* 103 */       SortedMap<Integer, Integer> hasMat = new TreeMap<Integer, Integer>();
/* 104 */       for (Material mat : materials) {
/*     */         
/* 106 */         maxrate += mat.getRate().intValue();
/* 107 */         hasMat.put(mat.getRate(), Integer.valueOf(i));
/* 108 */         i++;
/*     */       } 
/*     */       
/* 111 */       Iterator<Integer> it = hasMat.keySet().iterator();
/* 112 */       while (it.hasNext()) {
/*     */         
/* 114 */         rate = ((Integer)it.next()).intValue();
/* 115 */         float percent = Rnd.get() * 100.0F;
/* 116 */         float chance = rate / maxrate * 100.0F * gatherRate;
/*     */ 
/*     */         
/* 119 */         if (percent < chance) {
/*     */           
/* 121 */           index = ((Integer)hasMat.get(Integer.valueOf(rate))).intValue();
/* 122 */           material = materials.get(index);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 128 */     Material finalMaterial = material;
/*     */     
/* 130 */     if (this.state != GatherState.GATHERING) {
/*     */       
/* 132 */       this.state = GatherState.GATHERING;
/* 133 */       this.currentGatherer = player.getObjectId();
/* 134 */       player.getObserveController().attach((ActionObserver)new StartMovingListener()
/*     */           {
/*     */             
/*     */             public void moved()
/*     */             {
/* 139 */               GatherableController.this.finishGathering(player);
/*     */             }
/*     */           });
/* 142 */       int skillLvlDiff = player.getSkillList().getSkillLevel(template.getHarvestSkill()) - template.getSkillLevel();
/* 143 */       this.task = new GatheringTask(player, getOwner(), finalMaterial, skillLvlDiff);
/* 144 */       this.task.start();
/*     */     } 
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
/*     */   private boolean checkPlayerSkill(Player player, GatherableTemplate template) {
/* 157 */     int harvestSkillId = template.getHarvestSkill();
/*     */ 
/*     */     
/* 160 */     if (!player.getSkillList().isSkillPresent(harvestSkillId))
/*     */     {
/*     */       
/* 163 */       return false;
/*     */     }
/* 165 */     if (player.getSkillList().getSkillLevel(harvestSkillId) < template.getSkillLevel())
/*     */     {
/*     */       
/* 168 */       return false;
/*     */     }
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void completeInteraction() {
/* 175 */     this.state = GatherState.IDLE;
/* 176 */     this.gatherCount++;
/* 177 */     if (this.gatherCount == getOwner().getObjectTemplate().getHarvestCount()) {
/* 178 */       onDie();
/*     */     }
/*     */   }
/*     */   
/*     */   public void rewardPlayer(Player player) {
/* 183 */     if (player != null) {
/*     */       
/* 185 */       int skillLvl = getOwner().getObjectTemplate().getSkillLevel();
/* 186 */       int xpReward = (int)((0.008D * (skillLvl + 100) * (skillLvl + 100) + 60.0D) * player.getRates().getGatheringXPRate());
/*     */       
/* 188 */       if (player.getSkillList().addSkillXp(player, getOwner().getObjectTemplate().getHarvestSkill(), xpReward)) {
/*     */         
/* 190 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.EXTRACT_GATHER_SUCCESS_GETEXP());
/* 191 */         player.getCommonData().addExp(xpReward);
/*     */       } else {
/*     */         
/* 194 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_DONT_GET_PRODUCTION_EXP(new DescriptionId(DataManager.SKILL_DATA.getSkillTemplate(getOwner().getObjectTemplate().getHarvestSkill()).getNameId())));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishGathering(Player player) {
/* 207 */     if (this.currentGatherer == player.getObjectId()) {
/*     */       
/* 209 */       if (this.state == GatherState.GATHERING)
/*     */       {
/* 211 */         this.task.abort();
/*     */       }
/* 213 */       this.currentGatherer = 0;
/* 214 */       this.state = GatherState.IDLE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onDie() {
/* 220 */     Gatherable owner = getOwner();
/* 221 */     RespawnService.scheduleRespawnTask((VisibleObject)owner);
/* 222 */     World.getInstance().despawn((VisibleObject)owner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRespawn() {
/* 229 */     this.gatherCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Gatherable getOwner() {
/* 235 */     return super.getOwner();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\GatherableController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */