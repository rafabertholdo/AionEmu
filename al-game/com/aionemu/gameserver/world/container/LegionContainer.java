/*     */ package com.aionemu.gameserver.world.container;
/*     */ 
/*     */ import com.aionemu.gameserver.model.legion.Legion;
/*     */ import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegionContainer
/*     */ {
/*  37 */   private final Map<Integer, Legion> legionsById = (Map<Integer, Legion>)(new FastMap()).shared();
/*     */ 
/*     */ 
/*     */   
/*  41 */   private final Map<String, Legion> legionsByName = (Map<String, Legion>)(new FastMap()).shared();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Legion legion) {
/*  50 */     if (this.legionsById.put(Integer.valueOf(legion.getLegionId()), legion) != null)
/*  51 */       throw new DuplicateAionObjectException(); 
/*  52 */     if (this.legionsByName.put(legion.getLegionName().toLowerCase(), legion) != null) {
/*  53 */       throw new DuplicateAionObjectException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Legion legion) {
/*  63 */     this.legionsById.remove(Integer.valueOf(legion.getLegionId()));
/*  64 */     this.legionsByName.remove(legion.getLegionName().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Legion get(int legionId) {
/*  76 */     return this.legionsById.get(Integer.valueOf(legionId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Legion get(String name) {
/*  88 */     return this.legionsByName.get(name.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int legionId) {
/*  99 */     return this.legionsById.containsKey(Integer.valueOf(legionId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String name) {
/* 110 */     return this.legionsByName.containsKey(name.toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Legion> getLegions() {
/* 115 */     return this.legionsById.values();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\container\LegionContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */