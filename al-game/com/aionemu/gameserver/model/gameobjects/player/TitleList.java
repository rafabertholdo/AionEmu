/*    */ package com.aionemu.gameserver.model.gameobjects.player;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.templates.TitleTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_LIST;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import java.util.Collection;
/*    */ import javolution.util.FastMap;
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
/*    */ public class TitleList
/*    */ {
/* 39 */   private FastMap<Integer, Title> titles = new FastMap();
/* 40 */   private Player owner = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOwner(Player owner) {
/* 45 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   
/*    */   public Player getOwner() {
/* 50 */     return this.owner;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addTitle(int titleId) {
/* 55 */     TitleTemplate tt = DataManager.TITLE_DATA.getTitleTemplate(titleId);
/* 56 */     if (tt == null)
/*    */     {
/* 58 */       throw new IllegalArgumentException("Invalid title id " + titleId);
/*    */     }
/* 60 */     if (this.owner != null)
/*    */     {
/* 62 */       if (this.owner.getCommonData().getRace().getRaceId() != tt.getRace())
/*    */       {
/* 64 */         return false;
/*    */       }
/*    */     }
/* 67 */     if (!this.titles.containsKey(Integer.valueOf(titleId))) {
/*    */       
/* 69 */       this.titles.put(Integer.valueOf(titleId), new Title(tt));
/*    */     }
/*    */     else {
/*    */       
/* 73 */       return false;
/*    */     } 
/*    */     
/* 76 */     if (this.owner != null)
/*    */     {
/* 78 */       PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_TITLE_LIST(this.owner));
/*    */     }
/* 80 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 85 */     return this.titles.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Title> getTitles() {
/* 90 */     return this.titles.values();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\TitleList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */