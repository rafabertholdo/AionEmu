package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.PersistentState;

public class SkillListEntry {
  private int skillId;
  private int skillLvl;
  private boolean isStigma;
  private int currentXp;
  private PersistentState persistentState;

  public SkillListEntry(int skillId, boolean isStigma, int skillLvl, PersistentState persistentState) {
    this.skillId = skillId;
    this.skillLvl = skillLvl;
    this.isStigma = isStigma;
    this.persistentState = persistentState;
  }

  public int getSkillId() {
    return this.skillId;
  }

  public int getSkillLevel() {
    return this.skillLvl;
  }

  public boolean isStigma() {
    return this.isStigma;
  }

  public String getSkillName() {
    return DataManager.SKILL_DATA.getSkillTemplate(this.skillId).getName();
  }

  public void setSkillLvl(int skillLvl) {
    this.skillLvl = skillLvl;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public int getExtraLvl() {
    switch (this.skillId) {

      case 30002:
      case 30003:
      case 40001:
      case 40002:
      case 40003:
      case 40004:
      case 40007:
      case 40008:
        return this.skillLvl / 100;
    }
    return 0;
  }

  public int getCurrentXp() {
    return this.currentXp;
  }

  public void setCurrentXp(int currentXp) {
    this.currentXp = currentXp;
  }

  public boolean addSkillXp(int xp) {
    this.currentXp += xp;
    if (this.currentXp > 0.15D * (this.skillLvl + 30) * (this.skillLvl + 30)) {

      this.currentXp = 0;
      setSkillLvl(this.skillLvl + 1);
      return true;
    }
    return false;
  }

  public PersistentState getPersistentState() {
    return this.persistentState;
  }

  public void setPersistentState(PersistentState persistentState) {
    switch (persistentState) {

      case DELETED:
        if (this.persistentState == PersistentState.NEW) {
          this.persistentState = PersistentState.NOACTION;
        } else {
          this.persistentState = PersistentState.DELETED;
        }
        return;
      case UPDATE_REQUIRED:
        if (this.persistentState != PersistentState.NEW)
          this.persistentState = PersistentState.UPDATE_REQUIRED;
        return;
    }
    this.persistentState = persistentState;
  }
}
