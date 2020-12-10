/*     */ package com.aionemu.gameserver.restrictions;
/*     */ 
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectId;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillType;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class PlayerRestrictions
/*     */   extends AbstractRestrictions
/*     */ {
/*     */   public boolean canAffectBySkill(Player player, VisibleObject target) {
/*  42 */     Skill skill = player.getCastingSkill();
/*  43 */     if (skill == null) {
/*  44 */       return false;
/*     */     }
/*  46 */     if (((Creature)target).getLifeStats().isAlreadyDead() && !skill.getSkillTemplate().hasResurrectEffect()) {
/*     */       
/*  48 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.INVALID_TARGET());
/*  49 */       return false;
/*     */     } 
/*     */     
/*  52 */     if (skill.getSkillTemplate().hasItemHealFpEffect() && !player.isInState(CreatureState.FLYING)) {
/*     */       
/*  54 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_SKILL_RESTRICTION_FLY_ONLY);
/*  55 */       return false;
/*     */     } 
/*     */     
/*  58 */     if (player.getEffectController().isAbnormalState(EffectId.CANT_ATTACK_STATE) && skill.getSkillTemplate().getSkillId() != 1968)
/*     */     {
/*  60 */       return false;
/*     */     }
/*  62 */     if (player.isInState(CreatureState.PRIVATE_SHOP)) {
/*     */       
/*  64 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_CANNOT_USE_ITEM_WHILE_PRIVATE_STORE);
/*  65 */       return false;
/*     */     } 
/*     */     
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseSkill(Player player, Skill skill) {
/*  76 */     if (player.isCasting()) {
/*  77 */       return false;
/*     */     }
/*  79 */     if (!player.canAttack() && skill.getSkillTemplate().getSkillId() != 1968) {
/*  80 */       return false;
/*     */     }
/*  82 */     if (skill.getSkillTemplate().getType() == SkillType.MAGICAL && player.getEffectController().isAbnoramlSet(EffectId.SILENCE))
/*     */     {
/*  84 */       return false;
/*     */     }
/*  86 */     if (skill.getSkillTemplate().getType() == SkillType.PHYSICAL && player.getEffectController().isAbnoramlSet(EffectId.BLOCKADE))
/*     */     {
/*  88 */       return false;
/*     */     }
/*  90 */     if (player.isSkillDisabled(skill.getSkillTemplate().getSkillId())) {
/*  91 */       return false;
/*     */     }
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInviteToGroup(Player player, Player target) {
/*  99 */     PlayerGroup group = player.getPlayerGroup();
/*     */     
/* 101 */     if (group != null && group.isFull()) {
/*     */       
/* 103 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.FULL_GROUP());
/* 104 */       return false;
/*     */     } 
/* 106 */     if (group != null && player.getObjectId() != group.getGroupLeader().getObjectId()) {
/*     */       
/* 108 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.ONLY_GROUP_LEADER_CAN_INVITE());
/* 109 */       return false;
/*     */     } 
/* 111 */     if (target == null) {
/*     */       
/* 113 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.INVITED_PLAYER_OFFLINE());
/* 114 */       return false;
/*     */     } 
/* 116 */     if (target.getCommonData().getRace() != player.getCommonData().getRace()) {
/*     */       
/* 118 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_CANT_INVITE_OTHER_RACE());
/* 119 */       return false;
/*     */     } 
/* 121 */     if (target.sameObjectId(player.getObjectId())) {
/*     */       
/* 123 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.CANNOT_INVITE_YOURSELF());
/* 124 */       return false;
/*     */     } 
/* 126 */     if (target.getLifeStats().isAlreadyDead()) {
/*     */       
/* 128 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SELECTED_TARGET_DEAD());
/* 129 */       return false;
/*     */     } 
/* 131 */     if (player.getLifeStats().isAlreadyDead()) {
/*     */       
/* 133 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.CANNOT_INVITE_BECAUSE_YOU_DEAD());
/* 134 */       return false;
/*     */     } 
/* 136 */     if (target.isInGroup()) {
/*     */       
/* 138 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.PLAYER_IN_ANOTHER_GROUP(target.getName()));
/* 139 */       return false;
/*     */     } 
/* 141 */     if (target.isInAlliance()) {
/*     */       
/* 143 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_ALREADY_OTHER_FORCE(target.getName()));
/* 144 */       return false;
/*     */     } 
/*     */     
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInviteToAlliance(Player player, Player target) {
/* 153 */     if (target == null) {
/*     */       
/* 155 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_NO_USER_TO_INVITE());
/* 156 */       return false;
/*     */     } 
/* 158 */     if (target.getCommonData().getRace() != player.getCommonData().getRace()) {
/*     */       
/* 160 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_CANT_INVITE_OTHER_RACE());
/* 161 */       return false;
/*     */     } 
/*     */     
/* 164 */     PlayerAlliance alliance = player.getPlayerAlliance();
/*     */     
/* 166 */     if (target.isInAlliance()) {
/*     */       
/* 168 */       if (target.getPlayerAlliance() == alliance) {
/*     */         
/* 170 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_HE_IS_ALREADY_MEMBER_OF_OUR_ALLIANCE(target.getName()));
/* 171 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 175 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_ALREADY_OTHER_FORCE(target.getName()));
/* 176 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 180 */     if (alliance != null && alliance.isFull()) {
/*     */       
/* 182 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_CANT_ADD_NEW_MEMBER());
/* 183 */       return false;
/*     */     } 
/* 185 */     if (alliance != null && !alliance.hasAuthority(player.getObjectId())) {
/*     */       
/* 187 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_ONLY_PARTY_LEADER_CAN_LEAVE_ALLIANCE());
/* 188 */       return false;
/*     */     } 
/* 190 */     if (target.sameObjectId(player.getObjectId())) {
/*     */       
/* 192 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_CAN_NOT_INVITE_SELF());
/* 193 */       return false;
/*     */     } 
/* 195 */     if (target.getLifeStats().isAlreadyDead()) {
/*     */       
/* 197 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SELECTED_TARGET_DEAD());
/* 198 */       return false;
/*     */     } 
/* 200 */     if (player.getLifeStats().isAlreadyDead()) {
/*     */       
/* 202 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_CANT_INVITE_WHEN_DEAD());
/* 203 */       return false;
/*     */     } 
/* 205 */     if (target.isInGroup()) {
/*     */       
/* 207 */       PlayerGroup targetGroup = target.getPlayerGroup();
/* 208 */       if (target != targetGroup.getGroupLeader()) {
/*     */         
/* 210 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_INVITE_PARTY_HIM(target.getName(), targetGroup.getGroupLeader().getName()));
/* 211 */         return false;
/*     */       } 
/* 213 */       if (alliance != null && targetGroup.size() + alliance.size() >= 24) {
/*     */         
/* 215 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_INVITE_FAILED_NOT_ENOUGH_SLOT());
/* 216 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 221 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttack(Player player, VisibleObject target) {
/* 227 */     if (target == null) {
/* 228 */       return false;
/*     */     }
/* 230 */     if (!(target instanceof Creature)) {
/* 231 */       return false;
/*     */     }
/* 233 */     Creature creature = (Creature)target;
/*     */     
/* 235 */     if (creature.getLifeStats().isAlreadyDead()) {
/* 236 */       return false;
/*     */     }
/* 238 */     if (creature instanceof com.aionemu.gameserver.model.gameobjects.Monster) {
/* 239 */       return true;
/*     */     }
/* 241 */     if (creature instanceof Npc) {
/*     */       
/* 243 */       Npc npc = (Npc)creature;
/* 244 */       if (!npc.isAggressiveTo((Creature)player))
/* 245 */         return false; 
/*     */     } 
/* 247 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseWarehouse(Player player) {
/* 253 */     if (player == null || !player.isOnline()) {
/* 254 */       return false;
/*     */     }
/*     */     
/* 257 */     if (player.isTrading()) {
/* 258 */       return false;
/*     */     }
/* 260 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTrade(Player player) {
/* 266 */     if (player == null || !player.isOnline()) {
/* 267 */       return false;
/*     */     }
/*     */     
/* 270 */     if (player.isTrading()) {
/* 271 */       return false;
/*     */     }
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canChat(Player player) {
/* 279 */     if (player == null || !player.isOnline()) {
/* 280 */       return false;
/*     */     }
/* 282 */     return !player.isGagged();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\restrictions\PlayerRestrictions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */