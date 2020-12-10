package com.aionemu.gameserver.questEngine.model;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public class QuestEnv {
  private VisibleObject visibleObject;
  private Player player;
  private Integer questId;
  private Integer dialogId;

  public QuestEnv(VisibleObject visibleObject, Player player, Integer questId, Integer dialogId) {
    this.visibleObject = visibleObject;
    this.player = player;
    this.questId = questId;
    this.dialogId = dialogId;
  }

  public VisibleObject getVisibleObject() {
    return this.visibleObject;
  }

  public void setVisibleObject(VisibleObject visibleObject) {
    this.visibleObject = visibleObject;
  }

  public Player getPlayer() {
    return this.player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public Integer getQuestId() {
    return this.questId;
  }

  public void setQuestId(Integer questId) {
    this.questId = questId;
  }

  public Integer getDialogId() {
    return this.dialogId;
  }

  public void setDialogId(Integer dialogId) {
    this.dialogId = dialogId;
  }
}
