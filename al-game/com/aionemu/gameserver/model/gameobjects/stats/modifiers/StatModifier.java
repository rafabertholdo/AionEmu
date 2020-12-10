/*     */ package com.aionemu.gameserver.model.gameobjects.stats.modifiers;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javolution.text.TextBuilder;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "Modifier")
/*     */ public abstract class StatModifier
/*     */   implements Comparable<StatModifier>
/*     */ {
/*     */   @XmlAttribute
/*     */   private StatEnum name;
/*     */   @XmlAttribute
/*     */   private boolean bonus;
/*  43 */   protected static int MODIFIER_ID = 0;
/*     */   
/*     */   protected int id;
/*     */ 
/*     */   
/*     */   public StatModifier() {
/*  49 */     nextId();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setStat(StatEnum stat) {
/*  54 */     this.name = stat;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setBonus(boolean bonus) {
/*  59 */     this.bonus = bonus;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void nextId() {
/*  64 */     MODIFIER_ID = (MODIFIER_ID + 1) % Integer.MAX_VALUE;
/*  65 */     this.id = MODIFIER_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   public StatEnum getStat() {
/*  70 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonus() {
/*  75 */     return this.bonus;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(StatModifier o) {
/*  81 */     int result = getPriority().getValue() - o.getPriority().getValue();
/*  82 */     if (result == 0)
/*     */     {
/*  84 */       result = this.id - o.id;
/*     */     }
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  92 */     boolean result = (o != null);
/*  93 */     result = (result && o instanceof StatModifier);
/*  94 */     result = (result && ((StatModifier)o).id == this.id);
/*  95 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     TextBuilder tb = TextBuilder.newInstance();
/*     */     
/* 109 */     tb.append(getClass().getSimpleName() + ",");
/* 110 */     tb.append("i:" + this.id + ",");
/* 111 */     tb.append("s:" + this.name + ",");
/* 112 */     tb.append("b:" + this.bonus);
/*     */     
/* 114 */     String toString = tb.toString();
/* 115 */     TextBuilder.recycle(tb);
/*     */     
/* 117 */     return toString;
/*     */   }
/*     */   
/*     */   public abstract int apply(int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract StatModifierPriority getPriority();
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\StatModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */