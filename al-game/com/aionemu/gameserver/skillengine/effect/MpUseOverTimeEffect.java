package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.concurrent.Future;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpUseOverTimeEffect")
public class MpUseOverTimeEffect extends EffectTemplate {
  @XmlAttribute(required = true)
  protected int checktime;
  @XmlAttribute
  protected int value;

  public void applyEffect(final Effect effect) {
    Creature effected = effect.getEffected();
    int maxMp = effected.getGameStats().getCurrentStat(StatEnum.MAXMP);
    final int requiredMp = maxMp * this.value / 100;
    
    Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
        {
          
          public void run()
          {
            MpUseOverTimeEffect.this.onPeriodicAction(effect, requiredMp);
          }
        }0L, this.checktime);
    effect.setMpUseTask(task);
  }

  public void onPeriodicAction(Effect effect, int value) {
    Creature effected = effect.getEffected();
    if (effected.getLifeStats().getCurrentMp() < value) {
      effect.endEffect();
    }
    effected.getLifeStats().reduceMp(value);
  }

  public void calculate(Effect effect) {
    Creature effected = effect.getEffected();
    int maxMp = effected.getGameStats().getCurrentStat(StatEnum.MAXMP);
    int requiredMp = maxMp * this.value / 100;
    if (effected.getLifeStats().getCurrentMp() < requiredMp) {
      return;
    }
    effect.addSucessEffect(this);
  }
}
