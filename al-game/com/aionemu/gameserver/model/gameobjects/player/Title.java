package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.templates.TitleTemplate;

public class Title {
  private TitleTemplate template;

  public Title(TitleTemplate template) {
    this.template = template;
  }

  public TitleTemplate getTemplate() {
    return this.template;
  }
}
