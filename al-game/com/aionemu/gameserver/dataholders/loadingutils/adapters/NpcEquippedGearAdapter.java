/*    */ package com.aionemu.gameserver.dataholders.loadingutils.adapters;
/*    */ 
/*    */ import com.aionemu.gameserver.model.items.NpcEquippedGear;
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*    */ public class NpcEquippedGearAdapter
/*    */   extends XmlAdapter<NpcEquipmentList, NpcEquippedGear>
/*    */ {
/*    */   public NpcEquipmentList marshal(NpcEquippedGear v) throws Exception {
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NpcEquippedGear unmarshal(NpcEquipmentList v) throws Exception {
/* 45 */     return new NpcEquippedGear(v);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\loadingutils\adapters\NpcEquippedGearAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */