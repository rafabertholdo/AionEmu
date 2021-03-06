package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GiveItemOperation")
public class GiveItemOperation extends QuestOperation {
  @XmlAttribute(name = "item_id", required = true)
  protected int itemId;
  @XmlAttribute(required = true)
  protected int count;

  public void doOperate(QuestEnv env) {
    Player player = env.getPlayer();
    player.getController().addItems(this.itemId, this.count);
  }
}
