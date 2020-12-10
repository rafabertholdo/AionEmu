package com.aionemu.gameserver.skillengine.task;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class AbstractCraftTask extends AbstractInteractionTask {
  protected int successValue;
  protected int failureValue;
  protected int currentSuccessValue;
  protected int currentFailureValue;
  protected int skillLvlDiff;
  protected boolean critical;
  protected boolean setCritical = false;

  public AbstractCraftTask(Player requestor, VisibleObject responder, int successValue, int failureValue,
      int skillLvlDiff) {
    super(requestor, responder);
    this.successValue = successValue;
    this.failureValue = failureValue;
    this.skillLvlDiff = skillLvlDiff;
    this.critical = (Rnd.get(100) <= 15);
  }

  protected boolean onInteraction() {
    if (this.currentSuccessValue == this.successValue) {

      onSuccessFinish();
      return true;
    }
    if (this.currentFailureValue == this.failureValue) {

      onFailureFinish();
      return true;
    }

    analyzeInteraction();

    sendInteractionUpdate();
    return false;
  }

  private void analyzeInteraction() {
    int multi = Math.max(0, 33 - this.skillLvlDiff * 5);
    if (Rnd.get(100) > multi) {

      if (this.critical && Rnd.get(100) < 30)
        this.setCritical = true;
      this.currentSuccessValue += Rnd.get(this.successValue / (multi + 1) / 2, this.successValue);
    } else {

      this.currentFailureValue += Rnd.get(this.failureValue / (multi + 1) / 2, this.failureValue);
    }

    if (this.currentSuccessValue >= this.successValue) {

      if (this.critical)
        this.setCritical = true;
      this.currentSuccessValue = this.successValue;
    } else if (this.currentFailureValue >= this.failureValue) {

      this.currentFailureValue = this.failureValue;
    }
  }

  protected abstract void sendInteractionUpdate();

  protected abstract void onSuccessFinish();

  protected abstract void onFailureFinish();
}
