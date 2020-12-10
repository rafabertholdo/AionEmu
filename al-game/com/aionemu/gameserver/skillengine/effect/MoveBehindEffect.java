package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_MOVE;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoveBehindEffect")
public class MoveBehindEffect extends DamageEffect {
  public void applyEffect(Effect effect) {
    super.applyEffect(effect);
    Player effector = (Player) effect.getEffector();
    Creature effected = effect.getEffected();

    double radian = Math.toRadians(MathUtil.convertHeadingToDegree(effected.getHeading()));
    float x1 = (float) (Math.cos(Math.PI + radian) * 1.2999999523162842D);
    float y1 = (float) (Math.sin(Math.PI + radian) * 1.2999999523162842D);
    World.getInstance().updatePosition((VisibleObject) effector, effected.getX() + x1, effected.getY() + y1,
        effected.getZ() + 0.25F, effected.getHeading());

    PacketSendUtility.sendPacket(effector, (AionServerPacket) new SM_PLAYER_MOVE(effector.getX(), effector.getY(),
        effector.getZ(), effector.getHeading()));
  }

  public void calculate(Effect effect) {
    if (effect.getEffector() instanceof Player && effect.getEffected() != null) {
      calculate(effect, DamageType.PHYSICAL);
    }
  }
}
