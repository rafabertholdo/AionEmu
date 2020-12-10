/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class CraftSkillUpdateService
/*     */ {
/*  44 */   private static final Logger log = Logger.getLogger(CraftSkillUpdateService.class);
/*     */   
/*  46 */   private static final Map<Integer, LearnTemplate> npcBySkill = new HashMap<Integer, LearnTemplate>();
/*  47 */   private static final Map<Integer, Integer> cost = new HashMap<Integer, Integer>();
/*  48 */   private static final List<Integer> craftingSkillIds = new ArrayList<Integer>();
/*     */ 
/*     */   
/*     */   public static final CraftSkillUpdateService getInstance() {
/*  52 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private CraftSkillUpdateService() {
/*  58 */     npcBySkill.put(Integer.valueOf(204096), new LearnTemplate(30002, false, "Extract Vitality"));
/*  59 */     npcBySkill.put(Integer.valueOf(204257), new LearnTemplate(30003, false, "Extract Aether"));
/*     */     
/*  61 */     npcBySkill.put(Integer.valueOf(204100), new LearnTemplate(40001, true, "Cooking"));
/*  62 */     npcBySkill.put(Integer.valueOf(204104), new LearnTemplate(40002, true, "Weaponsmithing"));
/*  63 */     npcBySkill.put(Integer.valueOf(204106), new LearnTemplate(40003, true, "Armorsmithing"));
/*  64 */     npcBySkill.put(Integer.valueOf(204110), new LearnTemplate(40004, true, "Tailoring"));
/*  65 */     npcBySkill.put(Integer.valueOf(204102), new LearnTemplate(40007, true, "Alchemy"));
/*  66 */     npcBySkill.put(Integer.valueOf(204108), new LearnTemplate(40008, true, "Handicrafting"));
/*     */ 
/*     */     
/*  69 */     npcBySkill.put(Integer.valueOf(203780), new LearnTemplate(30002, false, "Extract Vitality"));
/*  70 */     npcBySkill.put(Integer.valueOf(203782), new LearnTemplate(30003, false, "Extract Aether"));
/*     */     
/*  72 */     npcBySkill.put(Integer.valueOf(203784), new LearnTemplate(40001, true, "Cooking"));
/*  73 */     npcBySkill.put(Integer.valueOf(203788), new LearnTemplate(40002, true, "Weaponsmithing"));
/*  74 */     npcBySkill.put(Integer.valueOf(203790), new LearnTemplate(40003, true, "Armorsmithing"));
/*  75 */     npcBySkill.put(Integer.valueOf(203793), new LearnTemplate(40004, true, "Tailoring"));
/*  76 */     npcBySkill.put(Integer.valueOf(203786), new LearnTemplate(40007, true, "Alchemy"));
/*  77 */     npcBySkill.put(Integer.valueOf(203792), new LearnTemplate(40008, true, "Handicrafting"));
/*     */     
/*  79 */     cost.put(Integer.valueOf(0), Integer.valueOf(3500));
/*  80 */     cost.put(Integer.valueOf(99), Integer.valueOf(17000));
/*  81 */     cost.put(Integer.valueOf(199), Integer.valueOf(115000));
/*  82 */     cost.put(Integer.valueOf(299), Integer.valueOf(460000));
/*  83 */     cost.put(Integer.valueOf(399), Integer.valueOf(1500000));
/*     */     
/*  85 */     craftingSkillIds.add(Integer.valueOf(40001));
/*  86 */     craftingSkillIds.add(Integer.valueOf(40002));
/*  87 */     craftingSkillIds.add(Integer.valueOf(40003));
/*  88 */     craftingSkillIds.add(Integer.valueOf(40004));
/*  89 */     craftingSkillIds.add(Integer.valueOf(40007));
/*  90 */     craftingSkillIds.add(Integer.valueOf(40008));
/*     */     
/*  92 */     log.info("CraftSkillUpdateService: Initialized.");
/*     */   }
/*     */ 
/*     */   
/*     */   class LearnTemplate
/*     */   {
/*     */     private int skillId;
/*     */     
/*     */     private boolean isCraftSkill;
/*     */ 
/*     */     
/*     */     public boolean isCraftSkill() {
/* 104 */       return this.isCraftSkill;
/*     */     }
/*     */     
/*     */     LearnTemplate(int skillId, boolean isCraftSkill, String skillName) {
/* 108 */       this.skillId = skillId;
/* 109 */       this.isCraftSkill = isCraftSkill;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getSkillId() {
/* 116 */       return this.skillId;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void learnSkill(Player player, Npc npc) {
/* 122 */     if (player.getLevel() < 10)
/*     */       return; 
/* 124 */     LearnTemplate template = npcBySkill.get(Integer.valueOf(npc.getNpcId()));
/* 125 */     if (template == null)
/*     */       return; 
/* 127 */     final int skillId = template.getSkillId();
/* 128 */     if (skillId == 0) {
/*     */       return;
/*     */     }
/* 131 */     int skillLvl = 0;
/* 132 */     if (player.getSkillList().isSkillPresent(skillId)) {
/* 133 */       skillLvl = player.getSkillList().getSkillLevel(skillId);
/*     */     }
/* 135 */     if (!cost.containsKey(Integer.valueOf(skillLvl))) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     if (isCraftingSkill(skillId) && !canLearnMoreMasterCraftingSkill(player) && skillLvl == 399) {
/*     */ 
/*     */       
/* 142 */       PacketSendUtility.sendMessage(player, "You can only master 2 craft skill.");
/*     */       
/*     */       return;
/*     */     } 
/* 146 */     final int price = ((Integer)cost.get(Integer.valueOf(skillLvl))).intValue();
/* 147 */     Item kinahItem = player.getInventory().getKinahItem();
/* 148 */     if (price > kinahItem.getItemCount()) {
/*     */       
/* 150 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "You don't have enough Kinah.", ChatType.ANNOUNCEMENTS));
/*     */       return;
/*     */     } 
/* 153 */     final int skillLevel = skillLvl;
/* 154 */     RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)npc)
/*     */       {
/*     */         
/*     */         public void acceptRequest(Creature requester, Player responder)
/*     */         {
/* 159 */           if (ItemService.decreaseKinah(responder, price)) {
/*     */             
/* 161 */             responder.getSkillList().addSkill(responder, skillId, skillLevel + 1, true);
/* 162 */             responder.getRecipeList().autoLearnRecipe(responder, skillId, skillLevel + 1);
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void denyRequest(Creature requester, Player responder) {}
/*     */       };
/* 173 */     boolean result = player.getResponseRequester().putRequest(900852, responseHandler);
/* 174 */     if (result)
/*     */     {
/* 176 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(900852, 0, new Object[] { new DescriptionId(DataManager.SKILL_DATA.getSkillTemplate(skillId).getNameId()), String.valueOf(price) }));
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
/*     */   private static boolean isCraftingSkill(int skillId) {
/* 188 */     for (Iterator<Integer> i$ = craftingSkillIds.iterator(); i$.hasNext(); ) { int i = ((Integer)i$.next()).intValue();
/*     */       
/* 190 */       if (i == skillId)
/* 191 */         return true;  }
/*     */     
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getTotalMasterCraftingSkills(Player player) {
/* 202 */     int mastered = 0;
/*     */     
/* 204 */     for (Iterator<Integer> i$ = craftingSkillIds.iterator(); i$.hasNext(); ) { int i = ((Integer)i$.next()).intValue();
/*     */       
/* 206 */       int skillId = i;
/* 207 */       int skillLvl = 0;
/* 208 */       if (player.getSkillList().isSkillPresent(skillId)) {
/*     */         
/* 210 */         skillLvl = player.getSkillList().getSkillLevel(skillId);
/* 211 */         if (skillLvl > 399) {
/* 212 */           mastered++;
/*     */         }
/*     */       }  }
/*     */     
/* 216 */     return mastered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canLearnMoreMasterCraftingSkill(Player player) {
/* 226 */     if (getTotalMasterCraftingSkills(player) < 2) {
/* 227 */       return true;
/*     */     }
/* 229 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 235 */     protected static final CraftSkillUpdateService instance = new CraftSkillUpdateService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\CraftSkillUpdateService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */