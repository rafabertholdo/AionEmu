package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnPointEffect")
public class ReturnPointEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    ItemTemplate itemTemplate = effect.getItemTemplate();
    int worldId = itemTemplate.getReturnWorldId();
    String pointAlias = itemTemplate.getReturnAlias();
    TeleportService.teleportToPortalExit((Player) effect.getEffector(), pointAlias, worldId, 500);
  }

  public void calculate(Effect effect) {
    ItemTemplate itemTemplate = effect.getItemTemplate();
    if (itemTemplate != null)
      effect.addSucessEffect(this);
  }
}
