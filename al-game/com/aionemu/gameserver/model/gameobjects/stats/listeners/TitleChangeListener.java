package com.aionemu.gameserver.model.gameobjects.stats.listeners;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;
import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
import com.aionemu.gameserver.model.templates.TitleTemplate;

public class TitleChangeListener {
  public static void onTitleChange(CreatureGameStats<?> cgs, int titleId, boolean isSet) {
    TitleTemplate tt = DataManager.TITLE_DATA.getTitleTemplate(titleId);
    if (tt == null) {
      return;
    }

    StatEffectId eid = StatEffectId.getInstance(tt.getTitleId(), StatEffectType.TITLE_EFFECT);
    if (!isSet) {

      cgs.endEffect(eid);
    } else {

      cgs.addModifiers(eid, tt.getModifiers());
    }
  }
}
