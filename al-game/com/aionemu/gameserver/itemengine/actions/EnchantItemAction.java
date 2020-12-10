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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "EnchantItemAction")
/*    */ public class EnchantItemAction
/*    */   extends AbstractItemAction
/*    */ {
/*    */   public boolean canAct(Player player, Item parentItem, Item targetItem) {
/* 44 */     if (targetItem == null) {
/*    */       
/* 46 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ITEM_ERROR);
/* 47 */       return false;
/*    */     } 
/*    */     
/* 50 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void act(Player player, Item parentItem, Item targetItem) {
/* 56 */     act(player, parentItem, targetItem, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void act(final Player player, final Item parentItem, final Item targetItem, final Item supplementItem) {
/* 62 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 5000, 0, 0));
/*    */     
/* 64 */     player.getController().cancelTask(TaskId.ITEM_USE);
/* 65 */     player.getController().addNewTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */           {
/*    */ 
/*    */             
/*    */             public void run()
/*    */             {
/* 71 */               int itemId = parentItem.getItemTemplate().getTemplateId();
/* 72 */               if (itemId > 166000000 && itemId < 167000000) {
/*    */                 
/* 74 */                 boolean result = EnchantService.enchantItem(player, parentItem, targetItem, supplementItem);
/* 75 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, result ? 1 : 2, 0));
/*    */               
/*    */               }
/*    */               else {
/*    */                 
/* 80 */                 boolean result = EnchantService.socketManastone(player, parentItem, targetItem, supplementItem);
/* 81 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, result ? 1 : 2, 0));
/*    */               } 
/*    */             }
/*    */           }5000L));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\EnchantItemAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */