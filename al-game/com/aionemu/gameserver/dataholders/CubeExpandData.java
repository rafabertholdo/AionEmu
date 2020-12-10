/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.CubeExpandTemplate;
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
/*    */ @XmlRootElement(name = "cube_expander")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class CubeExpandData
/*    */ {
/*    */   @XmlElement(name = "cube_npc")
/*    */   private List<CubeExpandTemplate> clist;
/* 42 */   private TIntObjectHashMap<CubeExpandTemplate> npctlistData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 46 */     for (CubeExpandTemplate npc : this.clist)
/*    */     {
/* 48 */       this.npctlistData.put(npc.getNpcId(), npc);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 54 */     return this.npctlistData.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public CubeExpandTemplate getCubeExpandListTemplate(int id) {
/* 59 */     return (CubeExpandTemplate)this.npctlistData.get(id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\CubeExpandData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */