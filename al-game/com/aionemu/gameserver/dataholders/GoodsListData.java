/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.goods.GoodsList;
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
/*    */ @XmlRootElement(name = "goodslists")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class GoodsListData
/*    */ {
/*    */   @XmlElement(required = true)
/*    */   protected List<GoodsList> list;
/*    */   private TIntObjectHashMap<GoodsList> goodsListData;
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 48 */     this.goodsListData = new TIntObjectHashMap();
/* 49 */     for (GoodsList it : this.list)
/*    */     {
/* 51 */       this.goodsListData.put(it.getId(), it);
/*    */     }
/* 53 */     this.list = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public GoodsList getGoodsListById(int id) {
/* 58 */     return (GoodsList)this.goodsListData.get(id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 66 */     return this.goodsListData.size();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\GoodsListData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */