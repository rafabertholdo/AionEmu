/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "summon_stats_templates")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class SummonStatsData
/*     */ {
/*     */   @XmlElement(name = "summon_stats", required = true)
/*  41 */   private List<SummonStatsType> summonTemplatesList = new ArrayList<SummonStatsType>();
/*     */ 
/*     */   
/*  44 */   private final TIntObjectHashMap<SummonStatsTemplate> summonTemplates = new TIntObjectHashMap();
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  48 */     for (SummonStatsType st : this.summonTemplatesList) {
/*     */       
/*  50 */       int code1 = makeHash(st.getNpcIdDark(), st.getRequiredLevel());
/*  51 */       this.summonTemplates.put(code1, st.getTemplate());
/*  52 */       int code2 = makeHash(st.getNpcIdLight(), st.getRequiredLevel());
/*  53 */       this.summonTemplates.put(code2, st.getTemplate());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SummonStatsTemplate getSummonTemplate(int npcId, int level) {
/*  65 */     SummonStatsTemplate template = (SummonStatsTemplate)this.summonTemplates.get(makeHash(npcId, level));
/*  66 */     if (template == null)
/*  67 */       template = (SummonStatsTemplate)this.summonTemplates.get(makeHash(201022, 10)); 
/*  68 */     return template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  78 */     return this.summonTemplates.size();
/*     */   }
/*     */ 
/*     */   
/*     */   @XmlRootElement(name = "summonStatsTemplateType")
/*     */   private static class SummonStatsType
/*     */   {
/*     */     @XmlAttribute(name = "npc_id_dark", required = true)
/*     */     private int npcIdDark;
/*     */     
/*     */     @XmlAttribute(name = "npc_id_light", required = true)
/*     */     private int npcIdLight;
/*     */     
/*     */     @XmlAttribute(name = "level", required = true)
/*     */     private int requiredLevel;
/*     */     
/*     */     @XmlElement(name = "stats_template")
/*     */     private SummonStatsTemplate template;
/*     */ 
/*     */     
/*     */     public int getNpcIdDark() {
/*  99 */       return this.npcIdDark;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNpcIdLight() {
/* 107 */       return this.npcIdLight;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRequiredLevel() {
/* 116 */       return this.requiredLevel;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SummonStatsTemplate getTemplate() {
/* 125 */       return this.template;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int makeHash(int npcId, int level) {
/* 139 */     return npcId << 8 | level;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\SummonStatsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */