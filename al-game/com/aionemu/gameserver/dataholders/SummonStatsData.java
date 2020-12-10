package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "summon_stats_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class SummonStatsData {
  @XmlElement(name = "summon_stats", required = true)
  private List<SummonStatsType> summonTemplatesList = new ArrayList<SummonStatsType>();

  private final TIntObjectHashMap<SummonStatsTemplate> summonTemplates = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (SummonStatsType st : this.summonTemplatesList) {

      int code1 = makeHash(st.getNpcIdDark(), st.getRequiredLevel());
      this.summonTemplates.put(code1, st.getTemplate());
      int code2 = makeHash(st.getNpcIdLight(), st.getRequiredLevel());
      this.summonTemplates.put(code2, st.getTemplate());
    }
  }

  public SummonStatsTemplate getSummonTemplate(int npcId, int level) {
    SummonStatsTemplate template = (SummonStatsTemplate) this.summonTemplates.get(makeHash(npcId, level));
    if (template == null)
      template = (SummonStatsTemplate) this.summonTemplates.get(makeHash(201022, 10));
    return template;
  }

  public int size() {
    return this.summonTemplates.size();
  }

  @XmlRootElement(name = "summonStatsTemplateType")
  private static class SummonStatsType {
    @XmlAttribute(name = "npc_id_dark", required = true)
    private int npcIdDark;

    @XmlAttribute(name = "npc_id_light", required = true)
    private int npcIdLight;

    @XmlAttribute(name = "level", required = true)
    private int requiredLevel;

    @XmlElement(name = "stats_template")
    private SummonStatsTemplate template;

    public int getNpcIdDark() {
      return this.npcIdDark;
    }

    public int getNpcIdLight() {
      return this.npcIdLight;
    }

    public int getRequiredLevel() {
      return this.requiredLevel;
    }

    public SummonStatsTemplate getTemplate() {
      return this.template;
    }
  }

  private static int makeHash(int npcId, int level) {
    return npcId << 8 | level;
  }
}
