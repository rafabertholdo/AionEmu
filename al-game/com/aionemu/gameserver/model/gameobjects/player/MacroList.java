/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class MacroList
/*     */ {
/*  23 */   private static final Logger logger = Logger.getLogger(MacroList.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<Integer, String> macrosses;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroList() {
/*  35 */     this.macrosses = new HashMap<Integer, String>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroList(Map<Integer, String> arg) {
/*  45 */     this.macrosses = arg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, String> getMacrosses() {
/*  55 */     return Collections.unmodifiableMap(this.macrosses);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean addMacro(int macroPosition, String macroXML) {
/*  70 */     if (this.macrosses.containsKey(Integer.valueOf(macroPosition))) {
/*     */       
/*  72 */       logger.warn("Trying to add macro with already existing order.");
/*  73 */       return false;
/*     */     } 
/*     */     
/*  76 */     this.macrosses.put(Integer.valueOf(macroPosition), macroXML);
/*  77 */     return true;
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
/*     */   public synchronized boolean removeMacro(int macroPosition) {
/*  89 */     String m = this.macrosses.remove(Integer.valueOf(macroPosition));
/*  90 */     if (m == null) {
/*     */       
/*  92 */       logger.warn("Trying to remove non existing macro.");
/*  93 */       return false;
/*     */     } 
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 105 */     return this.macrosses.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<Integer, String>> entrySet() {
/* 113 */     return Collections.unmodifiableSet(getMacrosses().entrySet());
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\MacroList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */