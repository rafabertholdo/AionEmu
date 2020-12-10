package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_USESKILL;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PetOrderUseUltraSkillEffect")
public class PetOrderUseUltraSkillEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    Player effector = (Player) effect.getEffector();
    int effectorId = effector.getSummon().getObjectId();

    int npcId = effector.getSummon().getNpcId();
    int orderSkillId = effect.getSkillId();

    int petUseSkillId = DataManager.PET_SKILL_DATA.getPetOrderSkill(orderSkillId, npcId);
    int targetId = effect.getEffected().getObjectId();

    PacketSendUtility.sendPacket(effector,
        (AionServerPacket) new SM_SUMMON_USESKILL(effectorId, petUseSkillId, 1, targetId));
  }

  public void calculate(Effect effect) {
    if (effect.getEffector() instanceof Player && effect.getEffected() != null)
      effect.addSucessEffect(this);
  }
}
