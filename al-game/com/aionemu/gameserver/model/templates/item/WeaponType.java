/*    */ package com.aionemu.gameserver.model.templates.item;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
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
/*    */ @XmlType(name = "weapon_type")
/*    */ @XmlEnum
/*    */ public enum WeaponType
/*    */ {
/* 30 */   DAGGER_1H(new int[] { 30, 9 }, 1),
/* 31 */   MACE_1H(new int[] { 3, 10 }, 1),
/* 32 */   SWORD_1H(new int[] { 1, 8 }, 1),
/* 33 */   TOOLHOE_1H(new int[0], 1),
/* 34 */   BOOK_2H(new int[] { 64 }, 2),
/* 35 */   ORB_2H(new int[] { 64 }, 2),
/* 36 */   POLEARM_2H(new int[] { 16 }, 2),
/* 37 */   STAFF_2H(new int[] { 53 }, 2),
/* 38 */   SWORD_2H(new int[] { 15 }, 2),
/* 39 */   TOOLPICK_2H(new int[0], 2),
/* 40 */   TOOLROD_2H(new int[0], 2),
/* 41 */   BOW(new int[] { 17 }, 2);
/*    */   
/*    */   private int[] requiredSkill;
/*    */   
/*    */   private int slots;
/*    */   
/*    */   WeaponType(int[] requiredSkills, int slots) {
/* 48 */     this.requiredSkill = requiredSkills;
/* 49 */     this.slots = slots;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getRequiredSkills() {
/* 54 */     return this.requiredSkill;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRequiredSlots() {
/* 59 */     return this.slots;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMask() {
/* 67 */     return 1 << ordinal();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\WeaponType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */