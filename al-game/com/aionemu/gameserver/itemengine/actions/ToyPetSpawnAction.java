/*     */ package com.aionemu.gameserver.itemengine.actions;
/*     */ 
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.spawnengine.SpawnEngine;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "ToyPetSpawnAction")
/*     */ public class ToyPetSpawnAction
/*     */   extends AbstractItemAction
/*     */ {
/*     */   @XmlAttribute
/*     */   protected int npcid;
/*     */   @XmlAttribute
/*     */   protected int time;
/*     */   
/*     */   public int getNpcId() {
/*  59 */     return this.npcid;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTime() {
/*  64 */     return this.time;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAct(Player player, Item parentItem, Item targetItem) {
/*  69 */     if (player.getFlyState() != 0) {
/*     */       
/*  71 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_USE_BINDSTONE_ITEM_WHILE_FLYING);
/*  72 */       return false;
/*     */     } 
/*  74 */     if (player.isInInstance()) {
/*     */       
/*  76 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_BINDSTONE_FAR_FROM_NPC);
/*  77 */       return false;
/*     */     } 
/*     */     
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void act(Player player, Item parentItem, Item targetItem) {
/*  86 */     SpawnEngine spawnEngine = SpawnEngine.getInstance();
/*  87 */     float x = player.getX();
/*  88 */     float y = player.getY();
/*  89 */     float z = player.getZ();
/*  90 */     byte heading = (byte)((player.getHeading() + 60) % 120);
/*  91 */     int worldId = player.getWorldId();
/*  92 */     int instanceId = player.getInstanceId();
/*     */     
/*  94 */     SpawnTemplate spawn = spawnEngine.addNewSpawn(worldId, instanceId, this.npcid, x, y, z, heading, 0, 0, true, true);
/*     */ 
/*     */     
/*  97 */     final Kisk kisk = spawnEngine.spawnKisk(spawn, instanceId, player);
/*     */ 
/*     */     
/* 100 */     Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 105 */             kisk.getController().onDespawn(true);
/*     */           }
/*     */         },  7200000L);
/*     */ 
/*     */     
/* 110 */     kisk.getController().addTask(TaskId.DESPAWN, task);
/*     */ 
/*     */     
/* 113 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId()), true);
/*     */ 
/*     */ 
/*     */     
/* 117 */     ItemService.decreaseItemCount(player, parentItem, 1L);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\ToyPetSpawnAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */