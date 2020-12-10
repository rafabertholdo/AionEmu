package com.aionemu.gameserver.model.templates;

public abstract class VisibleObjectTemplate {
  public abstract int getTemplateId();

  public abstract String getName();

  public abstract int getNameId();

  public int getState() {
    return 0;
  }
}
