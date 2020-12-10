package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Trap;
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
@XmlType(name = "SummonTrapEffect")
public class SummonTrapEffect extends SummonEffect {
  @XmlAttribute(name = "skill_id", required = true)
  protected int skillId;
  @XmlAttribute(name = "time", required = true)
  protected int time;

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
    final Trap trap = spawnEngine.spawnTrap(spawn, instanceId, effector, this.skillId);

    Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable() {

      public void run() {
        trap.getController().onDespawn(true);
      }
    }, (this.time * 1000));
    trap.getController().addTask(TaskId.DESPAWN, task);
  }

  public void calculate(Effect effect) {
    super.calculate(effect);
  }
}
