/*     */ package com.aionemu.gameserver.model.group;
/*     */ 
/*     */ import com.aionemu.commons.objects.filter.ObjectFilter;
/*     */ import com.aionemu.gameserver.configs.main.GroupConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_MEMBER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEAVE_GROUP_MEMBER;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Collection;
/*     */ import javolution.util.FastMap;
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
/*     */ public class PlayerGroup
/*     */   extends AionObject
/*     */ {
/*  41 */   private LootGroupRules lootGroupRules = new LootGroupRules();
/*     */   
/*     */   private Player groupLeader;
/*     */   
/*  45 */   private FastMap<Integer, Player> groupMembers = (new FastMap()).shared();
/*     */   
/*  47 */   private int RoundRobinNr = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerGroup(int groupId, Player groupleader) {
/*  56 */     super(Integer.valueOf(groupId));
/*  57 */     this.groupMembers.put(Integer.valueOf(groupleader.getObjectId()), groupleader);
/*  58 */     setGroupLeader(groupleader);
/*  59 */     groupleader.setPlayerGroup(this);
/*  60 */     PacketSendUtility.sendPacket(this.groupLeader, (AionServerPacket)new SM_GROUP_INFO(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGroupId() {
/*  68 */     return getObjectId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getGroupLeader() {
/*  76 */     return this.groupLeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupLeader(Player groupLeader) {
/*  87 */     this.groupLeader = groupLeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayerToGroup(Player newComer) {
/*  97 */     this.groupMembers.put(Integer.valueOf(newComer.getObjectId()), newComer);
/*  98 */     newComer.setPlayerGroup(this);
/*  99 */     PacketSendUtility.sendPacket(newComer, (AionServerPacket)new SM_GROUP_INFO(this));
/* 100 */     updateGroupUIToEvent(newComer, GroupEvent.ENTER);
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
/*     */   public int getRoundRobinMember(Npc npc) {
/* 112 */     this.RoundRobinNr = ++this.RoundRobinNr % size();
/* 113 */     int i = 0;
/* 114 */     for (Player player : getMembers()) {
/*     */       
/* 116 */       if (i == this.RoundRobinNr) {
/*     */         
/* 118 */         if (MathUtil.isIn3dRange((VisibleObject)player, (VisibleObject)npc, GroupConfig.GROUP_MAX_DISTANCE))
/*     */         {
/* 120 */           return player.getObjectId();
/*     */         }
/*     */ 
/*     */         
/* 124 */         return 0;
/*     */       } 
/*     */       
/* 127 */       i++;
/*     */     } 
/* 129 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromGroup(Player player) {
/* 139 */     this.groupMembers.remove(Integer.valueOf(player.getObjectId()));
/* 140 */     player.setPlayerGroup(null);
/* 141 */     updateGroupUIToEvent(player, GroupEvent.LEAVE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_LEAVE_GROUP_MEMBER(), true, new ObjectFilter<Player>()
/*     */         {
/*     */           public boolean acceptObject(Player object)
/*     */           {
/* 150 */             return (object.getPlayerGroup() == null);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void disband() {
/* 156 */     this.groupMembers.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGroupMemberLogIn(Player player) {
/* 161 */     this.groupMembers.remove(Integer.valueOf(player.getObjectId()));
/* 162 */     this.groupMembers.put(Integer.valueOf(player.getObjectId()), player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 172 */     return (this.groupMembers.size() == 6);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Player> getMembers() {
/* 177 */     return this.groupMembers.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Integer> getMemberObjIds() {
/* 182 */     return this.groupMembers.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 190 */     return this.groupMembers.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LootGroupRules getLootGroupRules() {
/* 198 */     return this.lootGroupRules;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootGroupRules(LootGroupRules lgr) {
/* 203 */     this.lootGroupRules = lgr;
/* 204 */     for (Player member : this.groupMembers.values()) {
/* 205 */       PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_INFO(this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateGroupUIToEvent(Player subjective, GroupEvent groupEvent) {
/*     */     boolean changeleader;
/* 214 */     switch (groupEvent) {
/*     */ 
/*     */       
/*     */       case CHANGELEADER:
/* 218 */         for (Player member : getMembers()) {
/*     */           
/* 220 */           PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_INFO(this));
/* 221 */           if (subjective.equals(member))
/* 222 */             PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.CHANGE_GROUP_LEADER()); 
/* 223 */           PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case LEAVE:
/* 229 */         changeleader = false;
/* 230 */         if (subjective == getGroupLeader()) {
/*     */           
/* 232 */           setGroupLeader(getMembers().iterator().next());
/* 233 */           changeleader = true;
/*     */         } 
/* 235 */         for (Player member : getMembers()) {
/*     */           
/* 237 */           if (changeleader) {
/*     */             
/* 239 */             PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_INFO(this));
/* 240 */             PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.CHANGE_GROUP_LEADER());
/*     */           } 
/* 242 */           if (!subjective.equals(member))
/* 243 */             PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent)); 
/* 244 */           if (size() > 1)
/* 245 */             PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.MEMBER_LEFT_GROUP(subjective.getName())); 
/*     */         } 
/* 247 */         eventToSubjective(subjective, GroupEvent.LEAVE);
/*     */         return;
/*     */ 
/*     */       
/*     */       case ENTER:
/* 252 */         eventToSubjective(subjective, GroupEvent.ENTER);
/* 253 */         for (Player member : getMembers()) {
/*     */           
/* 255 */           if (!subjective.equals(member)) {
/* 256 */             PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
/*     */           }
/*     */         } 
/*     */         return;
/*     */     } 
/*     */     
/* 262 */     for (Player member : getMembers()) {
/*     */       
/* 264 */       if (!subjective.equals(member)) {
/* 265 */         PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void eventToSubjective(Player subjective, GroupEvent groupEvent) {
/* 275 */     for (Player member : getMembers()) {
/*     */       
/* 277 */       if (!subjective.equals(member))
/* 278 */         PacketSendUtility.sendPacket(subjective, (AionServerPacket)new SM_GROUP_MEMBER_INFO(this, member, groupEvent)); 
/*     */     } 
/* 280 */     if (groupEvent == GroupEvent.LEAVE) {
/* 281 */       PacketSendUtility.sendPacket(subjective, (AionServerPacket)SM_SYSTEM_MESSAGE.YOU_LEFT_GROUP());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 287 */     return "Player Group";
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\PlayerGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */