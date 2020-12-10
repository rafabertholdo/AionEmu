/*    */ package com.aionemu.gameserver.itemengine.actions;
/*    */ 
/*    */ import com.aionemu.gameserver.model.TaskId;
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.EnchantService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "ExtractAction")
/*    */ public class ExtractAction
/*    */   extends AbstractItemAction
/*    */ {
/*    */   public boolean canAct(Player player, Item parentItem, Item targetItem) {
/* 43 */     if (targetItem == null) {
/*    */       
/* 45 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ITEM_ERROR);
/* 46 */       return false;
/*    */     } 
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void act(final Player player, final Item parentItem, final Item targetItem) {
/* 55 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 5000, 0, 0));
/*    */     
/* 57 */     player.getController().cancelTask(TaskId.ITEM_USE);
/* 58 */     player.getController().addNewTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */           {
/*    */             
/*    */             public void run()
/*    */             {
/* 63 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, 1, 0));
/*    */ 
/*    */               
/* 66 */               EnchantService.breakItem(player, targetItem, parentItem);
/*    */             }
/*    */           }5000L));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\ExtractAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */