package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Servant;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.concurrent.Future;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummonServantEffect")
public class SummonServantEffect extends SummonEffect {
  @XmlAttribute(name = "skill_id", required = true)
  protected int skillId;
  @XmlAttribute(name = "hp_ratio", required = true)
  protected int hpRatio;

  public void applyEffect(Effect effect) {
    Creature effector = effect.getEffector();
    SpawnEngine spawnEngine = SpawnEngine.getInstance();
    float x = effector.getX();
    float y = effector.getY();
    float z = effector.getZ();
    byte heading = effector.getHeading();
    int worldId = effector.getWorldId();
    int instanceId = effector.getInstanceId();

    SpawnTemplate spawn = spawnEngine.addNewSpawn(worldId, instanceId, this.npcId, x, y, z, heading, 0, 0, true, true);
    final Servant servant = spawnEngine.spawnServant(spawn, instanceId, effector, this.skillId, this.hpRatio);

    Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable() {

      public void run() {
        servant.getController().onDespawn(true);
      }
    }, 60000L);
    servant.getController().addTask(TaskId.DESPAWN, task);
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }
}
