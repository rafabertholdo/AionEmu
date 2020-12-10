package com.aionemu.gameserver.questEngine.handlers.template;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.handlers.models.MonsterInfo;
import com.aionemu.gameserver.questEngine.handlers.models.XmlQuestData;
import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnKillEvent;
import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnTalkEvent;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import java.util.Iterator;

public class XmlQuest extends QuestHandler {
  private final XmlQuestData xmlQuestData;

  public XmlQuest(XmlQuestData xmlQuestData) {
    super(Integer.valueOf(xmlQuestData.getId()));
    this.xmlQuestData = xmlQuestData;
  }

  public void register() {
    if (this.xmlQuestData.getStartNpcId() != null) {

      this.qe.setNpcQuestData(this.xmlQuestData.getStartNpcId().intValue()).addOnQuestStart(getQuestId().intValue());
      this.qe.setNpcQuestData(this.xmlQuestData.getStartNpcId().intValue()).addOnTalkEvent(getQuestId().intValue());
    }
    if (this.xmlQuestData.getEndNpcId() != null) {
      this.qe.setNpcQuestData(this.xmlQuestData.getEndNpcId().intValue()).addOnTalkEvent(getQuestId().intValue());
    }
    for (OnTalkEvent talkEvent : this.xmlQuestData.getOnTalkEvent()) {
      for (Iterator<Integer> i$ = talkEvent.getIds().iterator(); i$.hasNext();) {
        int npcId = ((Integer) i$.next()).intValue();
        this.qe.setNpcQuestData(npcId).addOnTalkEvent(getQuestId().intValue());
      }

    }
    for (OnKillEvent killEvent : this.xmlQuestData.getOnKillEvent()) {
      for (MonsterInfo monsterInfo : killEvent.getMonsterInfos()) {
        this.qe.setNpcQuestData(monsterInfo.getNpcId()).addOnKillEvent(getQuestId().intValue());
      }
    }
  }

  public boolean onDialogEvent(QuestEnv env) {
    env.setQuestId(getQuestId());
    for (OnTalkEvent talkEvent : this.xmlQuestData.getOnTalkEvent()) {

      if (talkEvent.operate(env)) {
        return true;
      }
    }
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(getQuestId().intValue());
    QuestTemplate template = DataManager.QUEST_DATA.getQuestById(getQuestId().intValue());
    if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE
        && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue())) {

      if (targetId == this.xmlQuestData.getStartNpcId().intValue()) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.REWARD && targetId == this.xmlQuestData.getEndNpcId().intValue()) {

      return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    env.setQuestId(getQuestId());
    for (OnKillEvent killEvent : this.xmlQuestData.getOnKillEvent()) {

      if (killEvent.operate(env))
        return true;
    }
    return false;
  }
}
