package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.templates.TitleTemplate;





















public class Title
{
  private TitleTemplate template;
  
  public Title(TitleTemplate template) {
    this.template = template;
  }
  
  public TitleTemplate getTemplate() {
    return this.template;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Title.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
