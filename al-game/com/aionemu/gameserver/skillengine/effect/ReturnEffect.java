package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnEffect")
public class ReturnEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    TeleportService.moveToBindLocation((Player) effect.getEffector(), true, 500);
  }

  public void calculate(Effect effect) {
    if (effect.getEffected().isSpawned())
      effect.addSucessEffect(this);
  }
}
