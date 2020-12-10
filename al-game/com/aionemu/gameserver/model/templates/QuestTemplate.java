package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.quest.CollectItems;
import com.aionemu.gameserver.model.templates.quest.QuestDrop;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
import com.aionemu.gameserver.model.templates.quest.Rewards;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Quest", propOrder = { "collectItems", "rewards", "questDrop", "finishedQuestConds", "classPermitted",
    "genderPermitted", "questWorkItems", "fighterSelectableReward", "knightSelectableReward", "rangerSelectableReward",
    "assassinSelectableReward", "wizardSelectableReward", "elementalistSelectableReward", "priestSelectableReward",
    "chanterSelectableReward" })
public class QuestTemplate {
  @XmlElement(name = "collect_items")
  protected CollectItems collectItems;
  protected List<Rewards> rewards;
  @XmlElement(name = "quest_drop")
  protected List<QuestDrop> questDrop;
  @XmlList
  @XmlElement(name = "finished_quest_conds", type = Integer.class)
  protected List<Integer> finishedQuestConds;
  @XmlList
  @XmlElement(name = "class_permitted")
  protected List<PlayerClass> classPermitted;
  @XmlElement(name = "gender_permitted")
  protected Gender genderPermitted;
  @XmlElement(name = "quest_work_items")
  protected QuestWorkItems questWorkItems;
  @XmlElement(name = "fighter_selectable_reward")
  protected List<QuestItems> fighterSelectableReward;
  @XmlElement(name = "knight_selectable_reward")
  protected List<QuestItems> knightSelectableReward;
  @XmlElement(name = "ranger_selectable_reward")
  protected List<QuestItems> rangerSelectableReward;
  @XmlElement(name = "assassin_selectable_reward")
  protected List<QuestItems> assassinSelectableReward;
  @XmlElement(name = "wizard_selectable_reward")
  protected List<QuestItems> wizardSelectableReward;
  @XmlElement(name = "elementalist_selectable_reward")
  protected List<QuestItems> elementalistSelectableReward;
  @XmlElement(name = "priest_selectable_reward")
  protected List<QuestItems> priestSelectableReward;
  @XmlElement(name = "chanter_selectable_reward")
  protected List<QuestItems> chanterSelectableReward;
  @XmlAttribute(required = true)
  protected int id;
  @XmlAttribute
  protected String name;
  @XmlAttribute
  protected Integer nameId;
  @XmlAttribute(name = "minlevel_permitted")
  protected Integer minlevelPermitted;
  @XmlAttribute(name = "max_repeat_count")
  protected Integer maxRepeatCount;
  @XmlAttribute(name = "cannot_share")
  protected Boolean cannotShare;
  @XmlAttribute(name = "cannot_giveup")
  protected Boolean cannotGiveup;
  @XmlAttribute(name = "use_class_reward")
  protected Boolean useClassReward;
  @XmlAttribute(name = "race_permitted")
  protected Race racePermitted;
  @XmlAttribute
  protected Integer combineskill;
  @XmlAttribute(name = "combine_skillpoint")
  protected Integer combineSkillpoint;
  @XmlAttribute(name = "timer")
  protected Boolean timer;

  public CollectItems getCollectItems() {
    return this.collectItems;
  }

  public List<Rewards> getRewards() {
    if (this.rewards == null) {
      this.rewards = new ArrayList<Rewards>();
    }
    return this.rewards;
  }

  public List<QuestDrop> getQuestDrop() {
    if (this.questDrop == null) {
      this.questDrop = new ArrayList<QuestDrop>();
    }
    return this.questDrop;
  }

  public List<Integer> getFinishedQuestConds() {
    if (this.finishedQuestConds == null) {
      this.finishedQuestConds = new ArrayList<Integer>();
    }
    return this.finishedQuestConds;
  }

  public List<PlayerClass> getClassPermitted() {
    if (this.classPermitted == null) {
      this.classPermitted = new ArrayList<PlayerClass>();
    }
    return this.classPermitted;
  }

  public Gender getGenderPermitted() {
    return this.genderPermitted;
  }

  public QuestWorkItems getQuestWorkItems() {
    return this.questWorkItems;
  }

  public List<QuestItems> getFighterSelectableReward() {
    if (this.fighterSelectableReward == null) {
      this.fighterSelectableReward = new ArrayList<QuestItems>();
    }
    return this.fighterSelectableReward;
  }

  public List<QuestItems> getKnightSelectableReward() {
    if (this.knightSelectableReward == null) {
      this.knightSelectableReward = new ArrayList<QuestItems>();
    }
    return this.knightSelectableReward;
  }

  public List<QuestItems> getRangerSelectableReward() {
    if (this.rangerSelectableReward == null) {
      this.rangerSelectableReward = new ArrayList<QuestItems>();
    }
    return this.rangerSelectableReward;
  }

  public List<QuestItems> getAssassinSelectableReward() {
    if (this.assassinSelectableReward == null) {
      this.assassinSelectableReward = new ArrayList<QuestItems>();
    }
    return this.assassinSelectableReward;
  }

  public List<QuestItems> getWizardSelectableReward() {
    if (this.wizardSelectableReward == null) {
      this.wizardSelectableReward = new ArrayList<QuestItems>();
    }
    return this.wizardSelectableReward;
  }

  public List<QuestItems> getElementalistSelectableReward() {
    if (this.elementalistSelectableReward == null) {
      this.elementalistSelectableReward = new ArrayList<QuestItems>();
    }
    return this.elementalistSelectableReward;
  }

  public List<QuestItems> getPriestSelectableReward() {
    if (this.priestSelectableReward == null) {
      this.priestSelectableReward = new ArrayList<QuestItems>();
    }
    return this.priestSelectableReward;
  }

  public List<QuestItems> getChanterSelectableReward() {
    if (this.chanterSelectableReward == null) {
      this.chanterSelectableReward = new ArrayList<QuestItems>();
    }
    return this.chanterSelectableReward;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Integer getNameId() {
    return this.nameId;
  }

  public Integer getMinlevelPermitted() {
    return this.minlevelPermitted;
  }

  public Integer getMaxRepeatCount() {
    return this.maxRepeatCount;
  }

  public boolean isCannotShare() {
    if (this.cannotShare == null) {
      return false;
    }

    return this.cannotShare.booleanValue();
  }

  public boolean isCannotGiveup() {
    if (this.cannotGiveup == null) {
      return false;
    }

    return this.cannotGiveup.booleanValue();
  }

  public boolean isUseClassReward() {
    if (this.useClassReward == null) {
      return false;
    }

    return this.useClassReward.booleanValue();
  }

  public Race getRacePermitted() {
    return this.racePermitted;
  }

  public Integer getCombineSkill() {
    return this.combineskill;
  }

  public Integer getCombineSkillPoint() {
    return this.combineSkillpoint;
  }

  public boolean isTimer() {
    if (this.timer == null) {
      return false;
    }

    return this.timer.booleanValue();
  }
}
