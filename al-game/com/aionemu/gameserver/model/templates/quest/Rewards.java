/*     */ package com.aionemu.gameserver.model.templates.quest;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
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
/*     */ @XmlType(name = "Rewards", propOrder = {"selectableRewardItem", "rewardItem"})
/*     */ public class Rewards
/*     */ {
/*     */   @XmlElement(name = "selectable_reward_item")
/*     */   protected List<QuestItems> selectableRewardItem;
/*     */   @XmlElement(name = "reward_item")
/*     */   protected List<QuestItems> rewardItem;
/*     */   @XmlAttribute
/*     */   protected Integer gold;
/*     */   @XmlAttribute
/*     */   protected Integer exp;
/*     */   @XmlAttribute(name = "reward_abyss_point")
/*     */   protected Integer rewardAbyssPoint;
/*     */   @XmlAttribute
/*     */   protected Integer title;
/*     */   @XmlAttribute(name = "extend_inventory")
/*     */   protected Integer extendInventory;
/*     */   @XmlAttribute(name = "extend_stigma")
/*     */   protected Integer extendStigma;
/*     */   
/*     */   public List<QuestItems> getSelectableRewardItem() {
/*  75 */     if (this.selectableRewardItem == null) {
/*  76 */       this.selectableRewardItem = new ArrayList<QuestItems>();
/*     */     }
/*  78 */     return this.selectableRewardItem;
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
/*     */   public List<QuestItems> getRewardItem() {
/* 104 */     if (this.rewardItem == null) {
/* 105 */       this.rewardItem = new ArrayList<QuestItems>();
/*     */     }
/* 107 */     return this.rewardItem;
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
/*     */   public Integer getGold() {
/* 119 */     return this.gold;
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
/*     */   public Integer getExp() {
/* 131 */     return this.exp;
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
/*     */   public Integer getRewardAbyssPoint() {
/* 143 */     return this.rewardAbyssPoint;
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
/*     */   public Integer getTitle() {
/* 155 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getExtendInventory() {
/* 163 */     return this.extendInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getExtendStigma() {
/* 171 */     return this.extendStigma;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\quest\Rewards.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */