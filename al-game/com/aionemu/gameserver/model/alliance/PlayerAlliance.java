/*     */ package com.aionemu.gameserver.model.alliance;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.services.AllianceService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public class PlayerAlliance
/*     */   extends AionObject
/*     */ {
/*     */   private int captainObjectId;
/*  40 */   private List<Integer> viceCaptainObjectIds = new ArrayList<Integer>();
/*     */   
/*  42 */   private FastMap<Integer, PlayerAllianceMember> allianceMembers = (new FastMap()).shared();
/*  43 */   private FastMap<Integer, PlayerAllianceGroup> allianceGroupForMember = (new FastMap()).shared();
/*     */   
/*  45 */   private FastMap<Integer, PlayerAllianceGroup> allianceGroups = (new FastMap()).shared();
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerAlliance(int objectId, int leaderObjectId) {
/*  50 */     super(Integer.valueOf(objectId));
/*  51 */     setLeader(leaderObjectId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMember(Player member) {
/*  56 */     PlayerAllianceGroup group = getOpenAllianceGroup();
/*  57 */     PlayerAllianceMember allianceMember = new PlayerAllianceMember(member);
/*  58 */     group.addMember(allianceMember);
/*     */     
/*  60 */     this.allianceMembers.put(Integer.valueOf(member.getObjectId()), allianceMember);
/*  61 */     this.allianceGroupForMember.put(Integer.valueOf(member.getObjectId()), group);
/*     */     
/*  63 */     member.setPlayerAlliance(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlayerAllianceGroup getOpenAllianceGroup() {
/*  71 */     for (int i = 1000; i <= 1004; i++) {
/*     */       
/*  73 */       PlayerAllianceGroup group = (PlayerAllianceGroup)this.allianceGroups.get(Integer.valueOf(i));
/*     */       
/*  75 */       if (group == null) {
/*     */         
/*  77 */         group = new PlayerAllianceGroup(this);
/*  78 */         group.setAllianceId(i);
/*  79 */         this.allianceGroups.put(Integer.valueOf(i), group);
/*  80 */         return group;
/*     */       } 
/*     */       
/*  83 */       if (group.getMembers().size() < 6)
/*  84 */         return group; 
/*     */     } 
/*  86 */     throw new RuntimeException("All Alliance Groups Full.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMember(int memberObjectId) {
/*  94 */     ((PlayerAllianceGroup)this.allianceGroupForMember.get(Integer.valueOf(memberObjectId))).removeMember(memberObjectId);
/*  95 */     this.allianceGroupForMember.remove(Integer.valueOf(memberObjectId));
/*  96 */     this.allianceMembers.remove(Integer.valueOf(memberObjectId));
/*     */ 
/*     */     
/*  99 */     if (this.viceCaptainObjectIds.contains(Integer.valueOf(memberObjectId)))
/*     */     {
/* 101 */       this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(memberObjectId)));
/*     */     }
/*     */ 
/*     */     
/* 105 */     if (memberObjectId == this.captainObjectId)
/*     */     {
/*     */       
/* 108 */       if (this.viceCaptainObjectIds.size() > 0) {
/*     */         
/* 110 */         int newCaptain = ((Integer)this.viceCaptainObjectIds.get(0)).intValue();
/* 111 */         this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(newCaptain)));
/* 112 */         this.captainObjectId = newCaptain;
/*     */       }
/* 114 */       else if (this.allianceMembers.size() != 0) {
/*     */ 
/*     */         
/* 117 */         PlayerAllianceMember newCaptain = this.allianceMembers.values().iterator().next();
/* 118 */         this.captainObjectId = newCaptain.getObjectId();
/*     */       } 
/*     */     }
/*     */     
/* 122 */     AllianceService.getInstance().broadcastAllianceInfo(this, PlayerAllianceEvent.UPDATE, new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLeader(int newLeaderObjectId) {
/* 131 */     if (this.viceCaptainObjectIds.contains(Integer.valueOf(newLeaderObjectId))) {
/*     */ 
/*     */       
/* 134 */       this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(newLeaderObjectId)));
/* 135 */       this.viceCaptainObjectIds.add(Integer.valueOf(this.captainObjectId));
/*     */     } 
/* 137 */     this.captainObjectId = newLeaderObjectId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void promoteViceLeader(int viceLeaderObjectId) {
/* 145 */     this.viceCaptainObjectIds.add(Integer.valueOf(viceLeaderObjectId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void demoteViceLeader(int viceLeaderObjectId) {
/* 153 */     this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(viceLeaderObjectId)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerAllianceMember getCaptain() {
/* 161 */     return getPlayer(getCaptainObjectId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCaptainObjectId() {
/* 169 */     return this.captainObjectId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> getViceCaptainObjectIds() {
/* 177 */     return this.viceCaptainObjectIds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAllianceIdFor(int playerObjectId) {
/* 186 */     if (!this.allianceGroupForMember.containsKey(Integer.valueOf(playerObjectId))) {
/* 187 */       return 0;
/*     */     }
/* 189 */     return ((PlayerAllianceGroup)this.allianceGroupForMember.get(Integer.valueOf(playerObjectId))).getAllianceId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerAllianceMember getPlayer(int playerObjectId) {
/* 198 */     return (PlayerAllianceMember)this.allianceMembers.get(Integer.valueOf(playerObjectId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 206 */     return getMembers().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 214 */     return (size() >= 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PlayerAllianceMember> getMembers() {
/* 222 */     return this.allianceMembers.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAuthority(int playerObjectId) {
/* 231 */     return (playerObjectId == this.captainObjectId || this.viceCaptainObjectIds.contains(Integer.valueOf(playerObjectId)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 238 */     return "Player Alliance";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swapPlayers(int playerObjectId1, int playerObjectId2) {
/* 247 */     PlayerAllianceGroup group1 = (PlayerAllianceGroup)this.allianceGroupForMember.get(Integer.valueOf(playerObjectId1));
/* 248 */     PlayerAllianceGroup group2 = (PlayerAllianceGroup)this.allianceGroupForMember.get(Integer.valueOf(playerObjectId2));
/*     */     
/* 250 */     PlayerAllianceMember player1 = group1.removeMember(playerObjectId1);
/* 251 */     PlayerAllianceMember player2 = group2.removeMember(playerObjectId2);
/*     */     
/* 253 */     group1.addMember(player2);
/* 254 */     group2.addMember(player1);
/*     */     
/* 256 */     this.allianceGroupForMember.put(Integer.valueOf(playerObjectId1), group2);
/* 257 */     this.allianceGroupForMember.put(Integer.valueOf(playerObjectId2), group1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllianceGroupFor(int memberObjectId, int allianceGroupId) {
/* 268 */     PlayerAllianceGroup leavingGroup = (PlayerAllianceGroup)this.allianceGroupForMember.get(Integer.valueOf(memberObjectId));
/* 269 */     PlayerAllianceMember member = leavingGroup.getMemberById(memberObjectId);
/* 270 */     leavingGroup.removeMember(memberObjectId);
/*     */     
/* 272 */     PlayerAllianceGroup group = (PlayerAllianceGroup)this.allianceGroups.get(Integer.valueOf(allianceGroupId));
/*     */     
/* 274 */     if (group == null) {
/*     */       
/* 276 */       group = new PlayerAllianceGroup(this);
/* 277 */       group.setAllianceId(allianceGroupId);
/* 278 */       this.allianceGroups.put(Integer.valueOf(allianceGroupId), group);
/*     */     } 
/*     */     
/* 281 */     group.addMember(member);
/* 282 */     this.allianceGroupForMember.put(Integer.valueOf(memberObjectId), group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerAllianceGroup getPlayerAllianceGroupForMember(int objectId) {
/* 291 */     return (PlayerAllianceGroup)this.allianceGroupForMember.get(Integer.valueOf(objectId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerLogin(Player player) {
/* 299 */     ((PlayerAllianceMember)this.allianceMembers.get(Integer.valueOf(player.getObjectId()))).onLogin(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerDisconnect(Player player) {
/* 307 */     PlayerAllianceMember allianceMember = (PlayerAllianceMember)this.allianceMembers.get(Integer.valueOf(player.getObjectId()));
/* 308 */     allianceMember.onDisconnect();
/*     */     
/* 310 */     for (PlayerAllianceMember member : this.allianceMembers.values()) {
/*     */ 
/*     */       
/* 313 */       if (member.isOnline()) {
/*     */         
/* 315 */         PacketSendUtility.sendPacket(member.getPlayer(), (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_HE_BECOME_OFFLINE(player.getName()));
/* 316 */         PacketSendUtility.sendPacket(member.getPlayer(), (AionServerPacket)new SM_ALLIANCE_MEMBER_INFO(allianceMember, PlayerAllianceEvent.DISCONNECTED));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PlayerAllianceMember> getMembersForGroup(int playerObjectId) {
/* 327 */     PlayerAllianceGroup group = (PlayerAllianceGroup)this.allianceGroupForMember.get(Integer.valueOf(playerObjectId));
/*     */     
/* 329 */     if (group == null) return (new FastMap()).values(); 
/* 330 */     return group.getMembers();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\alliance\PlayerAlliance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */