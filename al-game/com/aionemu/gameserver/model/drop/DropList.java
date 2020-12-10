/*    */ package com.aionemu.gameserver.model.drop;
/*    */ 
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public class DropList
/*    */ {
/* 29 */   private TIntObjectHashMap<Set<DropTemplate>> templatesMap = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   public void addDropTemplate(int mobId, DropTemplate dropTemplate) {
/* 33 */     Set<DropTemplate> dropTemplates = (Set<DropTemplate>)this.templatesMap.get(mobId);
/* 34 */     if (dropTemplates == null) {
/*    */       
/* 36 */       dropTemplates = new HashSet<DropTemplate>();
/* 37 */       this.templatesMap.put(mobId, dropTemplates);
/*    */     } 
/* 39 */     dropTemplates.add(dropTemplate);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<DropTemplate> getDropsFor(int mobId) {
/* 44 */     return (Set<DropTemplate>)this.templatesMap.get(mobId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 49 */     return this.templatesMap.size();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\drop\DropList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */