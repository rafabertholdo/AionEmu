/*    */ package com.aionemu.commons.objects.filter;
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
/*    */ public class AndObjectFilter<T>
/*    */   implements ObjectFilter<T>
/*    */ {
/*    */   private ObjectFilter<? super T>[] filters;
/*    */   
/*    */   public AndObjectFilter(ObjectFilter<? super T>... filters) {
/* 39 */     this.filters = filters;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean acceptObject(T object) {
/* 48 */     for (ObjectFilter<? super T> filter : this.filters) {
/*    */       
/* 50 */       if (filter != null && !filter.acceptObject(object))
/* 51 */         return false; 
/*    */     } 
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\objects\filter\AndObjectFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */