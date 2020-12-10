package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.stats.CalculatedPlayerStatsTemplate;
import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player_stats_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerStatsData {
  @XmlElement(name = "player_stats", required = true)
  private List<PlayerStatsType> templatesList = new ArrayList<PlayerStatsType>();

  private final TIntObjectHashMap<PlayerStatsTemplate> playerTemplates = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (PlayerStatsType pt : this.templatesList) {

      int code = makeHash(pt.getRequiredPlayerClass(), pt.getRequiredLevel());
      this.playerTemplates.put(code, pt.getTemplate());
    }

    this.playerTemplates.put(makeHash(PlayerClass.WARRIOR, 0), new CalculatedPlayerStatsTemplate(PlayerClass.WARRIOR));
    this.playerTemplates.put(makeHash(PlayerClass.ASSASSIN, 0),
        new CalculatedPlayerStatsTemplate(PlayerClass.ASSASSIN));
    this.playerTemplates.put(makeHash(PlayerClass.CHANTER, 0), new CalculatedPlayerStatsTemplate(PlayerClass.CHANTER));
    this.playerTemplates.put(makeHash(PlayerClass.CLERIC, 0), new CalculatedPlayerStatsTemplate(PlayerClass.CLERIC));
    this.playerTemplates.put(makeHash(PlayerClass.GLADIATOR, 0),
        new CalculatedPlayerStatsTemplate(PlayerClass.GLADIATOR));
    this.playerTemplates.put(makeHash(PlayerClass.MAGE, 0), new CalculatedPlayerStatsTemplate(PlayerClass.MAGE));
    this.playerTemplates.put(makeHash(PlayerClass.PRIEST, 0), new CalculatedPlayerStatsTemplate(PlayerClass.PRIEST));
    this.playerTemplates.put(makeHash(PlayerClass.RANGER, 0), new CalculatedPlayerStatsTemplate(PlayerClass.RANGER));
    this.playerTemplates.put(makeHash(PlayerClass.SCOUT, 0), new CalculatedPlayerStatsTemplate(PlayerClass.SCOUT));
    this.playerTemplates.put(makeHash(PlayerClass.SORCERER, 0),
        new CalculatedPlayerStatsTemplate(PlayerClass.SORCERER));
    this.playerTemplates.put(makeHash(PlayerClass.SPIRIT_MASTER, 0),
        new CalculatedPlayerStatsTemplate(PlayerClass.SPIRIT_MASTER));
    this.playerTemplates.put(makeHash(PlayerClass.TEMPLAR, 0), new CalculatedPlayerStatsTemplate(PlayerClass.TEMPLAR));

    this.templatesList.clear();
    this.templatesList = null;
  }

  public PlayerStatsTemplate getTemplate(Player player) {
    PlayerStatsTemplate template = getTemplate(player.getCommonData().getPlayerClass(), player.getLevel());
    if (template == null)
      template = getTemplate(player.getCommonData().getPlayerClass(), 0);
    return template;
  }

  public PlayerStatsTemplate getTemplate(PlayerClass playerClass, int level) {
    PlayerStatsTemplate template = (PlayerStatsTemplate) this.playerTemplates.get(makeHash(playerClass, level));
    if (template == null)
      template = getTemplate(playerClass, 0);
    return template;
  }

  public int size() {
    return this.playerTemplates.size();
  }

  @XmlRootElement(name = "playerStatsTemplateType")
  private static class PlayerStatsType {
    @XmlAttribute(name = "class", required = true)
    private PlayerClass requiredPlayerClass;

    @XmlAttribute(name = "level", required = true)
    private int requiredLevel;
    @XmlElement(name = "stats_template")
    private PlayerStatsTemplate template;

    public PlayerClass getRequiredPlayerClass() {
      return this.requiredPlayerClass;
    }

    public int getRequiredLevel() {
      return this.requiredLevel;
    }

    public PlayerStatsTemplate getTemplate() {
      return this.template;
    }
  }

  private static int makeHash(PlayerClass playerClass, int level) {
    return level << 8 | playerClass.ordinal();
  }
}
