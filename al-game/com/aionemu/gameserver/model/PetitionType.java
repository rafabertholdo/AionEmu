package com.aionemu.gameserver.model;

public enum PetitionType {
  CHARACTER_STUCK(256), CHARACTER_RESTORATION(512), BUG(768), QUEST(1024), UNACCEPTABLE_BEHAVIOR(1280),
  SUGGESTION(1536), INQUIRY(65280);

  private int element;

  PetitionType(int id) {
    this.element = id;
  }

  public int getElementId() {
    return this.element;
  }
}
