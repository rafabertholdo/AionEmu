/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.stats.CalculatedPlayerStatsTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
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
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "player_stats_templates")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class PlayerStatsData
/*     */ {
/*     */   @XmlElement(name = "player_stats", required = true)
/*  46 */   private List<PlayerStatsType> templatesList = new ArrayList<PlayerStatsType>();
/*     */ 
/*     */   
/*  49 */   private final TIntObjectHashMap<PlayerStatsTemplate> playerTemplates = new TIntObjectHashMap();
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  53 */     for (PlayerStatsType pt : this.templatesList) {
/*     */       
/*  55 */       int code = makeHash(pt.getRequiredPlayerClass(), pt.getRequiredLevel());
/*  56 */       this.playerTemplates.put(code, pt.getTemplate());
/*     */     } 
/*     */ 
/*     */     
/*  60 */     this.playerTemplates.put(makeHash(PlayerClass.WARRIOR, 0), new CalculatedPlayerStatsTemplate(PlayerClass.WARRIOR));
/*  61 */     this.playerTemplates.put(makeHash(PlayerClass.ASSASSIN, 0), new CalculatedPlayerStatsTemplate(PlayerClass.ASSASSIN));
/*  62 */     this.playerTemplates.put(makeHash(PlayerClass.CHANTER, 0), new CalculatedPlayerStatsTemplate(PlayerClass.CHANTER));
/*  63 */     this.playerTemplates.put(makeHash(PlayerClass.CLERIC, 0), new CalculatedPlayerStatsTemplate(PlayerClass.CLERIC));
/*  64 */     this.playerTemplates.put(makeHash(PlayerClass.GLADIATOR, 0), new CalculatedPlayerStatsTemplate(PlayerClass.GLADIATOR));
/*  65 */     this.playerTemplates.put(makeHash(PlayerClass.MAGE, 0), new CalculatedPlayerStatsTemplate(PlayerClass.MAGE));
/*  66 */     this.playerTemplates.put(makeHash(PlayerClass.PRIEST, 0), new CalculatedPlayerStatsTemplate(PlayerClass.PRIEST));
/*  67 */     this.playerTemplates.put(makeHash(PlayerClass.RANGER, 0), new CalculatedPlayerStatsTemplate(PlayerClass.RANGER));
/*  68 */     this.playerTemplates.put(makeHash(PlayerClass.SCOUT, 0), new CalculatedPlayerStatsTemplate(PlayerClass.SCOUT));
/*  69 */     this.playerTemplates.put(makeHash(PlayerClass.SORCERER, 0), new CalculatedPlayerStatsTemplate(PlayerClass.SORCERER));
/*  70 */     this.playerTemplates.put(makeHash(PlayerClass.SPIRIT_MASTER, 0), new CalculatedPlayerStatsTemplate(PlayerClass.SPIRIT_MASTER));
/*  71 */     this.playerTemplates.put(makeHash(PlayerClass.TEMPLAR, 0), new CalculatedPlayerStatsTemplate(PlayerClass.TEMPLAR));
/*     */     
/*  73 */     this.templatesList.clear();
/*  74 */     this.templatesList = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerStatsTemplate getTemplate(Player player) {
/*  84 */     PlayerStatsTemplate template = getTemplate(player.getCommonData().getPlayerClass(), player.getLevel());
/*  85 */     if (template == null)
/*  86 */       template = getTemplate(player.getCommonData().getPlayerClass(), 0); 
/*  87 */     return template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerStatsTemplate getTemplate(PlayerClass playerClass, int level) {
/*  98 */     PlayerStatsTemplate template = (PlayerStatsTemplate)this.playerTemplates.get(makeHash(playerClass, level));
/*  99 */     if (template == null)
/* 100 */       template = getTemplate(playerClass, 0); 
/* 101 */     return template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 111 */     return this.playerTemplates.size();
/*     */   }
/*     */ 
/*     */   
/*     */   @XmlRootElement(name = "playerStatsTemplateType")
/*     */   private static class PlayerStatsType
/*     */   {
/*     */     @XmlAttribute(name = "class", required = true)
/*     */     private PlayerClass requiredPlayerClass;
/*     */     
/*     */     @XmlAttribute(name = "level", required = true)
/*     */     private int requiredLevel;
/*     */     @XmlElement(name = "stats_template")
/*     */     private PlayerStatsTemplate template;
/*     */     
/*     */     public PlayerClass getRequiredPlayerClass() {
/* 127 */       return this.requiredPlayerClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRequiredLevel() {
/* 132 */       return this.requiredLevel;
/*     */     }
/*     */ 
/*     */     
/*     */     public PlayerStatsTemplate getTemplate() {
/* 137 */       return this.template;
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
/*     */   private static int makeHash(PlayerClass playerClass, int level) {
/* 149 */     return level << 8 | playerClass.ordinal();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\PlayerStatsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */