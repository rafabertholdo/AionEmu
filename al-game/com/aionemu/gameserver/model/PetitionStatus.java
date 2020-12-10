package com.aionemu.gameserver.model;

public enum PetitionStatus {
  PENDING(0), IN_PROGRESS(1), REPLIED(2);

  private int element;

  PetitionStatus(int id) {
    this.element = id;
  }

  public int getElementId() {
    return this.element;
  }
}
