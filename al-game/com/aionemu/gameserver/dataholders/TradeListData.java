/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.TradeListTemplate;
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.Unmarshaller;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "npc_trade_list")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class TradeListData
/*    */ {
/*    */   @XmlElement(name = "tradelist_template")
/*    */   private List<TradeListTemplate> tlist;
/* 49 */   private TIntObjectHashMap<TradeListTemplate> npctlistData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 53 */     for (TradeListTemplate npc : this.tlist)
/*    */     {
/* 55 */       this.npctlistData.put(npc.getNpcId(), npc);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 61 */     return this.npctlistData.size();
/*    */   }
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
/*    */   public TradeListTemplate getTradeListTemplate(int id) {
/* 74 */     return (TradeListTemplate)this.npctlistData.get(id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\TradeListData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */