/*     */ package com.aionemu.gameserver.world.container;
/*     */ 
/*     */ import com.aionemu.gameserver.model.legion.LegionMember;
/*     */ import com.aionemu.gameserver.model.legion.LegionMemberEx;
/*     */ import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
/*     */ import javolution.util.FastMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegionMemberContainer
/*     */ {
/*  32 */   private final FastMap<Integer, LegionMember> legionMemberById = new FastMap();
/*     */   
/*  34 */   private final FastMap<Integer, LegionMemberEx> legionMemberExById = new FastMap();
/*  35 */   private final FastMap<String, LegionMemberEx> legionMemberExByName = new FastMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMember(LegionMember legionMember) {
/*  44 */     if (this.legionMemberById.containsKey(Integer.valueOf(legionMember.getObjectId())))
/*  45 */       throw new DuplicateAionObjectException(); 
/*  46 */     this.legionMemberById.put(Integer.valueOf(legionMember.getObjectId()), legionMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMember getMember(int memberObjId) {
/*  56 */     return (LegionMember)this.legionMemberById.get(Integer.valueOf(memberObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMemberEx(LegionMemberEx legionMember) {
/*  66 */     if (this.legionMemberExById.containsKey(Integer.valueOf(legionMember.getObjectId())) || this.legionMemberExByName.containsKey(legionMember.getName()))
/*     */     {
/*  68 */       throw new DuplicateAionObjectException(); } 
/*  69 */     this.legionMemberExById.put(Integer.valueOf(legionMember.getObjectId()), legionMember);
/*  70 */     this.legionMemberExByName.put(legionMember.getName(), legionMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMemberEx getMemberEx(int memberObjId) {
/*  80 */     return (LegionMemberEx)this.legionMemberExById.get(Integer.valueOf(memberObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMemberEx getMemberEx(String memberName) {
/*  90 */     return (LegionMemberEx)this.legionMemberExByName.get(memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(LegionMemberEx legionMember) {
/* 100 */     this.legionMemberById.remove(Integer.valueOf(legionMember.getObjectId()));
/* 101 */     this.legionMemberExById.remove(Integer.valueOf(legionMember.getObjectId()));
/* 102 */     this.legionMemberExByName.remove(legionMember.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int memberObjId) {
/* 113 */     return this.legionMemberById.containsKey(Integer.valueOf(memberObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsEx(int memberObjId) {
/* 124 */     return this.legionMemberExById.containsKey(Integer.valueOf(memberObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsEx(String memberName) {
/* 135 */     return this.legionMemberExByName.containsKey(memberName);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\container\LegionMemberContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */