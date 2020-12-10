/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.NpcController;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.legion.Legion;
/*     */ import com.aionemu.gameserver.model.templates.NpcTemplate;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.KiskStatsTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_KISK_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class Kisk
/*     */   extends Npc
/*     */ {
/*     */   private String ownerName;
/*     */   private Legion ownerLegion;
/*     */   private Race ownerRace;
/*     */   private int ownerObjectId;
/*     */   private KiskStatsTemplate kiskStatsTemplate;
/*     */   private int remainingResurrections;
/*     */   private long kiskSpawnTime;
/*  50 */   private final List<Player> kiskMembers = new ArrayList<Player>();
/*  51 */   private int currentMemberCount = 0;
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
/*     */   public Kisk(int objId, NpcController controller, SpawnTemplate spawnTemplate, NpcTemplate npcTemplate, Player owner) {
/*  63 */     super(objId, controller, spawnTemplate, (VisibleObjectTemplate)npcTemplate);
/*     */     
/*  65 */     this.kiskStatsTemplate = npcTemplate.getKiskStatsTemplate();
/*  66 */     if (this.kiskStatsTemplate == null) {
/*  67 */       this.kiskStatsTemplate = new KiskStatsTemplate();
/*     */     }
/*  69 */     this.remainingResurrections = this.kiskStatsTemplate.getMaxResurrects();
/*  70 */     this.kiskSpawnTime = System.currentTimeMillis() / 1000L;
/*     */     
/*  72 */     this.ownerName = owner.getName();
/*  73 */     this.ownerLegion = owner.getLegion();
/*  74 */     this.ownerRace = owner.getCommonData().getRace();
/*  75 */     this.ownerObjectId = owner.getObjectId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggressiveTo(Creature creature) {
/*  84 */     if (creature instanceof Player) {
/*     */       
/*  86 */       Player player = (Player)creature;
/*  87 */       if (player.getCommonData().getRace() != this.ownerRace)
/*  88 */         return true; 
/*     */     } 
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyNpc(Npc npc) {
/*  96 */     return (npc instanceof Monster || npc.isAggressiveTo(this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyPlayer(Player player) {
/* 102 */     return (player.getCommonData().getRace() != this.ownerRace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcObjectType getNpcObjectType() {
/* 111 */     return NpcObjectType.NORMAL;
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
/*     */   public int getUseMask() {
/* 124 */     return this.kiskStatsTemplate.getUseMask();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getCurrentMemberList() {
/* 129 */     return this.kiskMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentMemberCount() {
/* 137 */     return this.currentMemberCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxMembers() {
/* 145 */     return this.kiskStatsTemplate.getMaxMembers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRemainingResurrects() {
/* 153 */     return this.remainingResurrections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRessurects() {
/* 161 */     return this.kiskStatsTemplate.getMaxResurrects();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRemainingLifetime() {
/* 169 */     long timeElapsed = System.currentTimeMillis() / 1000L - this.kiskSpawnTime;
/* 170 */     int timeRemaining = (int)(7200L - timeElapsed);
/* 171 */     return (timeRemaining > 0) ? timeRemaining : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBind(Player player) {
/* 180 */     String playerName = player.getName();
/*     */     
/* 182 */     if (playerName != this.ownerName) {
/*     */       boolean isMember;
/*     */       
/* 185 */       switch (getUseMask()) {
/*     */         
/*     */         case 1:
/* 188 */           if (this.ownerRace == player.getCommonData().getRace()) {
/* 189 */             return false;
/*     */           }
/*     */           break;
/*     */         case 2:
/* 193 */           if (this.ownerLegion == null)
/* 194 */             return false; 
/* 195 */           if (!this.ownerLegion.isMember(player.getObjectId())) {
/* 196 */             return false;
/*     */           }
/*     */           break;
/*     */         case 3:
/* 200 */           return false;
/*     */         
/*     */         case 4:
/* 203 */           isMember = false;
/* 204 */           if (player.isInGroup()) {
/*     */             
/* 206 */             for (Player member : player.getPlayerGroup().getMembers())
/*     */             {
/* 208 */               if (member.getObjectId() == this.ownerObjectId) {
/* 209 */                 isMember = true;
/*     */               }
/*     */             }
/*     */           
/* 213 */           } else if (player.isInAlliance()) {
/*     */             
/* 215 */             for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembersForGroup(player.getObjectId())) {
/*     */               
/* 217 */               if (allianceMember.getObjectId() == this.ownerObjectId) {
/* 218 */                 isMember = true;
/*     */               }
/*     */             } 
/*     */           } 
/* 222 */           if (!isMember) {
/* 223 */             return false;
/*     */           }
/*     */           break;
/*     */         case 5:
/* 227 */           if (!player.isInAlliance() || player.getPlayerAlliance().getPlayer(this.ownerObjectId) == null)
/*     */           {
/* 229 */             return false;
/*     */           }
/*     */           break;
/*     */         default:
/* 233 */           return false;
/*     */       } 
/*     */     
/*     */     } 
/* 237 */     if (getCurrentMemberCount() >= getMaxMembers()) {
/* 238 */       return false;
/*     */     }
/* 240 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(Player player) {
/* 248 */     this.kiskMembers.add(player);
/* 249 */     player.setKisk(this);
/* 250 */     this.currentMemberCount++;
/* 251 */     broadcastKiskUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reAddPlayer(Player player) {
/* 259 */     this.kiskMembers.add(player);
/* 260 */     player.setKisk(this);
/* 261 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_KISK_UPDATE(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(Player player) {
/* 269 */     this.kiskMembers.remove(player);
/* 270 */     player.setKisk(null);
/* 271 */     this.currentMemberCount--;
/* 272 */     broadcastKiskUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void broadcastKiskUpdate() {
/* 281 */     for (Player member : this.kiskMembers) {
/*     */       
/* 283 */       if (!getKnownList().knowns((AionObject)member))
/* 284 */         PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_KISK_UPDATE(this)); 
/*     */     } 
/* 286 */     for (VisibleObject obj : getKnownList().getKnownObjects().values()) {
/*     */       
/* 288 */       if (obj instanceof Player) {
/*     */         
/* 290 */         Player target = (Player)obj;
/* 291 */         if (target.getCommonData().getRace() == this.ownerRace) {
/* 292 */           PacketSendUtility.sendPacket(target, (AionServerPacket)new SM_KISK_UPDATE(this));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastPacket(SM_SYSTEM_MESSAGE message) {
/* 302 */     for (Player member : this.kiskMembers) {
/*     */       
/* 304 */       if (member != null) {
/* 305 */         PacketSendUtility.sendPacket(member, (AionServerPacket)message);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resurrectionUsed(Player player) {
/* 314 */     this.remainingResurrections--;
/* 315 */     if (this.remainingResurrections <= 0) {
/*     */       
/* 317 */       player.getKisk().getController().onDespawn(true);
/*     */     }
/*     */     else {
/*     */       
/* 321 */       broadcastKiskUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Race getOwnerRace() {
/* 330 */     return this.ownerRace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOwnerName() {
/* 338 */     return this.ownerName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOwnerObjectId() {
/* 346 */     return this.ownerObjectId;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Kisk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */