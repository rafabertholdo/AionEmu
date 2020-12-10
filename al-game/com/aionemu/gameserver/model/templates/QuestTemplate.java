/*     */ package com.aionemu.gameserver.model.templates;
/*     */ 
/*     */ import com.aionemu.gameserver.model.Gender;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.templates.quest.CollectItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestDrop;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.Rewards;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlList;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "Quest", propOrder = {"collectItems", "rewards", "questDrop", "finishedQuestConds", "classPermitted", "genderPermitted", "questWorkItems", "fighterSelectableReward", "knightSelectableReward", "rangerSelectableReward", "assassinSelectableReward", "wizardSelectableReward", "elementalistSelectableReward", "priestSelectableReward", "chanterSelectableReward"})
/*     */ public class QuestTemplate
/*     */ {
/*     */   @XmlElement(name = "collect_items")
/*     */   protected CollectItems collectItems;
/*     */   protected List<Rewards> rewards;
/*     */   @XmlElement(name = "quest_drop")
/*     */   protected List<QuestDrop> questDrop;
/*     */   @XmlList
/*     */   @XmlElement(name = "finished_quest_conds", type = Integer.class)
/*     */   protected List<Integer> finishedQuestConds;
/*     */   @XmlList
/*     */   @XmlElement(name = "class_permitted")
/*     */   protected List<PlayerClass> classPermitted;
/*     */   @XmlElement(name = "gender_permitted")
/*     */   protected Gender genderPermitted;
/*     */   @XmlElement(name = "quest_work_items")
/*     */   protected QuestWorkItems questWorkItems;
/*     */   @XmlElement(name = "fighter_selectable_reward")
/*     */   protected List<QuestItems> fighterSelectableReward;
/*     */   @XmlElement(name = "knight_selectable_reward")
/*     */   protected List<QuestItems> knightSelectableReward;
/*     */   @XmlElement(name = "ranger_selectable_reward")
/*     */   protected List<QuestItems> rangerSelectableReward;
/*     */   @XmlElement(name = "assassin_selectable_reward")
/*     */   protected List<QuestItems> assassinSelectableReward;
/*     */   @XmlElement(name = "wizard_selectable_reward")
/*     */   protected List<QuestItems> wizardSelectableReward;
/*     */   @XmlElement(name = "elementalist_selectable_reward")
/*     */   protected List<QuestItems> elementalistSelectableReward;
/*     */   @XmlElement(name = "priest_selectable_reward")
/*     */   protected List<QuestItems> priestSelectableReward;
/*     */   @XmlElement(name = "chanter_selectable_reward")
/*     */   protected List<QuestItems> chanterSelectableReward;
/*     */   @XmlAttribute(required = true)
/*     */   protected int id;
/*     */   @XmlAttribute
/*     */   protected String name;
/*     */   @XmlAttribute
/*     */   protected Integer nameId;
/*     */   @XmlAttribute(name = "minlevel_permitted")
/*     */   protected Integer minlevelPermitted;
/*     */   @XmlAttribute(name = "max_repeat_count")
/*     */   protected Integer maxRepeatCount;
/*     */   @XmlAttribute(name = "cannot_share")
/*     */   protected Boolean cannotShare;
/*     */   @XmlAttribute(name = "cannot_giveup")
/*     */   protected Boolean cannotGiveup;
/*     */   @XmlAttribute(name = "use_class_reward")
/*     */   protected Boolean useClassReward;
/*     */   @XmlAttribute(name = "race_permitted")
/*     */   protected Race racePermitted;
/*     */   @XmlAttribute
/*     */   protected Integer combineskill;
/*     */   @XmlAttribute(name = "combine_skillpoint")
/*     */   protected Integer combineSkillpoint;
/*     */   @XmlAttribute(name = "timer")
/*     */   protected Boolean timer;
/*     */   
/*     */   public CollectItems getCollectItems() {
/* 114 */     return this.collectItems;
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
/*     */   public List<Rewards> getRewards() {
/* 140 */     if (this.rewards == null)
/*     */     {
/* 142 */       this.rewards = new ArrayList<Rewards>();
/*     */     }
/* 144 */     return this.rewards;
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
/*     */   public List<QuestDrop> getQuestDrop() {
/* 170 */     if (this.questDrop == null)
/*     */     {
/* 172 */       this.questDrop = new ArrayList<QuestDrop>();
/*     */     }
/* 174 */     return this.questDrop;
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
/*     */   public List<Integer> getFinishedQuestConds() {
/* 200 */     if (this.finishedQuestConds == null)
/*     */     {
/* 202 */       this.finishedQuestConds = new ArrayList<Integer>();
/*     */     }
/* 204 */     return this.finishedQuestConds;
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
/*     */   public List<PlayerClass> getClassPermitted() {
/* 230 */     if (this.classPermitted == null)
/*     */     {
/* 232 */       this.classPermitted = new ArrayList<PlayerClass>();
/*     */     }
/* 234 */     return this.classPermitted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gender getGenderPermitted() {
/* 245 */     return this.genderPermitted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuestWorkItems getQuestWorkItems() {
/* 256 */     return this.questWorkItems;
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
/*     */   public List<QuestItems> getFighterSelectableReward() {
/* 282 */     if (this.fighterSelectableReward == null)
/*     */     {
/* 284 */       this.fighterSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 286 */     return this.fighterSelectableReward;
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
/*     */   public List<QuestItems> getKnightSelectableReward() {
/* 312 */     if (this.knightSelectableReward == null)
/*     */     {
/* 314 */       this.knightSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 316 */     return this.knightSelectableReward;
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
/*     */   public List<QuestItems> getRangerSelectableReward() {
/* 342 */     if (this.rangerSelectableReward == null)
/*     */     {
/* 344 */       this.rangerSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 346 */     return this.rangerSelectableReward;
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
/*     */   public List<QuestItems> getAssassinSelectableReward() {
/* 372 */     if (this.assassinSelectableReward == null)
/*     */     {
/* 374 */       this.assassinSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 376 */     return this.assassinSelectableReward;
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
/*     */   public List<QuestItems> getWizardSelectableReward() {
/* 402 */     if (this.wizardSelectableReward == null)
/*     */     {
/* 404 */       this.wizardSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 406 */     return this.wizardSelectableReward;
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
/*     */   public List<QuestItems> getElementalistSelectableReward() {
/* 432 */     if (this.elementalistSelectableReward == null)
/*     */     {
/* 434 */       this.elementalistSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 436 */     return this.elementalistSelectableReward;
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
/*     */   public List<QuestItems> getPriestSelectableReward() {
/* 462 */     if (this.priestSelectableReward == null)
/*     */     {
/* 464 */       this.priestSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 466 */     return this.priestSelectableReward;
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
/*     */   public List<QuestItems> getChanterSelectableReward() {
/* 492 */     if (this.chanterSelectableReward == null)
/*     */     {
/* 494 */       this.chanterSelectableReward = new ArrayList<QuestItems>();
/*     */     }
/* 496 */     return this.chanterSelectableReward;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 505 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 516 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getNameId() {
/* 527 */     return this.nameId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMinlevelPermitted() {
/* 538 */     return this.minlevelPermitted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMaxRepeatCount() {
/* 549 */     return this.maxRepeatCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCannotShare() {
/* 560 */     if (this.cannotShare == null)
/*     */     {
/* 562 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 566 */     return this.cannotShare.booleanValue();
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
/*     */   public boolean isCannotGiveup() {
/* 578 */     if (this.cannotGiveup == null)
/*     */     {
/* 580 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 584 */     return this.cannotGiveup.booleanValue();
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
/*     */   public boolean isUseClassReward() {
/* 596 */     if (this.useClassReward == null)
/*     */     {
/* 598 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 602 */     return this.useClassReward.booleanValue();
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
/*     */   public Race getRacePermitted() {
/* 614 */     return this.racePermitted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getCombineSkill() {
/* 625 */     return this.combineskill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getCombineSkillPoint() {
/* 636 */     return this.combineSkillpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimer() {
/* 647 */     if (this.timer == null)
/*     */     {
/* 649 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 653 */     return this.timer.booleanValue();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\QuestTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */