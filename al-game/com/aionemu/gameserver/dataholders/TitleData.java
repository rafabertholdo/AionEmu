/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.TitleTemplate;
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
/*    */ @XmlRootElement(name = "player_titles")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class TitleData
/*    */ {
/*    */   @XmlElement(name = "title")
/*    */   private List<TitleTemplate> tts;
/*    */   private TIntObjectHashMap<TitleTemplate> titles;
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 46 */     this.titles = new TIntObjectHashMap();
/* 47 */     for (TitleTemplate tt : this.tts)
/*    */     {
/* 49 */       this.titles.put(tt.getTitleId(), tt);
/*    */     }
/* 51 */     this.tts = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public TitleTemplate getTitleTemplate(int titleId) {
/* 56 */     return (TitleTemplate)this.titles.get(titleId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 64 */     return this.titles.size();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\TitleData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */