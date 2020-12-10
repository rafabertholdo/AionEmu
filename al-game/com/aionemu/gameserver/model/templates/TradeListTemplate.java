/*     */ package com.aionemu.gameserver.model.templates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ 
/*     */ @XmlRootElement(name = "tradelist_template")
/*     */ @XmlAccessorType(XmlAccessType.NONE)
/*     */ public class TradeListTemplate
/*     */ {
/*     */   @XmlAttribute(name = "npc_id", required = true)
/*     */   private int npcId;
/*     */   @XmlAttribute(name = "name", required = true)
/*  46 */   private String name = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "count", required = true)
/*  52 */   private int Count = 0;
/*     */   
/*     */   @XmlAttribute(name = "abyss")
/*     */   private boolean abyss;
/*     */   
/*     */   @XmlElement(name = "tradelist")
/*     */   protected List<TradeTab> tradeTablist;
/*     */   
/*     */   @XmlAttribute(name = "sell_price_rate")
/*  61 */   protected int sellPriceRate = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TradeTab> getTradeTablist() {
/*  69 */     if (this.tradeTablist == null) {
/*  70 */       this.tradeTablist = new ArrayList<TradeTab>();
/*     */     }
/*  72 */     return this.tradeTablist;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNpcId() {
/*  82 */     return this.npcId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  87 */     return this.Count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbyss() {
/*  95 */     return this.abyss;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSellPriceRate() {
/* 103 */     return this.sellPriceRate;
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
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name = "Tradelist")
/*     */   public static class TradeTab
/*     */   {
/*     */     @XmlAttribute
/*     */     protected int id;
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
/*     */     public int getId() {
/* 135 */       return this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\TradeListTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */