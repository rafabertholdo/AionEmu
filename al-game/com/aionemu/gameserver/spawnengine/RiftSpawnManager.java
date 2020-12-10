/*     */ package com.aionemu.gameserver.spawnengine;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.controllers.NpcController;
/*     */ import com.aionemu.gameserver.controllers.RiftController;
/*     */ import com.aionemu.gameserver.controllers.effect.EffectController;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.NpcTemplate;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import com.aionemu.gameserver.world.KnownList;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
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
/*     */ public class RiftSpawnManager
/*     */ {
/*  48 */   private static final Logger log = Logger.getLogger(RiftSpawnManager.class);
/*     */   
/*  50 */   private static final ConcurrentLinkedQueue<Npc> rifts = new ConcurrentLinkedQueue<Npc>();
/*     */   
/*     */   private static final int RIFT_RESPAWN_DELAY = 6000000;
/*     */   
/*     */   private static final int RIFT_LIFETIME = 1560000;
/*     */   
/*  56 */   private static final Map<String, SpawnGroup> spawnGroups = new HashMap<String, SpawnGroup>();
/*     */ 
/*     */   
/*     */   public static void addRiftSpawnGroup(SpawnGroup spawnGroup) {
/*  60 */     spawnGroups.put(spawnGroup.getAnchor(), spawnGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void startRiftPool() {
/*  65 */     ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/*  70 */             RiftSpawnManager.RiftEnum rift1 = RiftSpawnManager.RiftEnum.values()[Rnd.get(0, 6)];
/*  71 */             RiftSpawnManager.RiftEnum rift2 = RiftSpawnManager.RiftEnum.values()[Rnd.get(7, 13)];
/*  72 */             RiftSpawnManager.RiftEnum rift3 = RiftSpawnManager.RiftEnum.values()[Rnd.get(14, 20)];
/*  73 */             RiftSpawnManager.RiftEnum rift4 = RiftSpawnManager.RiftEnum.values()[Rnd.get(21, 27)];
/*     */             
/*  75 */             RiftSpawnManager.spawnRift(rift1);
/*  76 */             RiftSpawnManager.spawnRift(rift2);
/*  77 */             RiftSpawnManager.spawnRift(rift3);
/*  78 */             RiftSpawnManager.spawnRift(rift4);
/*     */           }
/*     */         }10000L, 6000000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void spawnRift(RiftEnum rift) {
/*  88 */     log.info("Spawning rift : " + rift.name());
/*  89 */     SpawnGroup masterGroup = spawnGroups.get(rift.getMaster());
/*  90 */     SpawnGroup slaveGroup = spawnGroups.get(rift.getSlave());
/*     */     
/*  92 */     if (masterGroup == null || slaveGroup == null) {
/*     */       return;
/*     */     }
/*  95 */     int instanceCount = World.getInstance().getWorldMap(masterGroup.getMapid()).getInstanceCount();
/*     */     
/*  97 */     SpawnTemplate masterTemplate = masterGroup.getNextRandomTemplate();
/*  98 */     SpawnTemplate slaveTemplate = slaveGroup.getNextRandomTemplate();
/*     */     
/* 100 */     for (int i = 1; i <= instanceCount; i++) {
/*     */       
/* 102 */       Npc slave = spawnInstance(i, masterGroup, slaveTemplate, new RiftController(null, rift));
/* 103 */       spawnInstance(i, masterGroup, masterTemplate, new RiftController(slave, rift));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Npc spawnInstance(int instanceIndex, SpawnGroup spawnGroup, SpawnTemplate spawnTemplate, RiftController riftController) {
/* 109 */     NpcTemplate masterObjectTemplate = DataManager.NPC_DATA.getNpcTemplate(spawnGroup.getNpcid());
/* 110 */     Npc npc = new Npc(IDFactory.getInstance().nextId(), (NpcController)riftController, spawnTemplate, (VisibleObjectTemplate)masterObjectTemplate);
/*     */ 
/*     */     
/* 113 */     npc.setKnownlist(new KnownList((VisibleObject)npc));
/* 114 */     npc.setEffectController(new EffectController((Creature)npc));
/* 115 */     npc.getController().onRespawn();
/*     */     
/* 117 */     World world = World.getInstance();
/* 118 */     world.storeObject((AionObject)npc);
/* 119 */     world.setPosition((VisibleObject)npc, spawnTemplate.getWorldId(), instanceIndex, spawnTemplate.getX(), spawnTemplate.getY(), spawnTemplate.getZ(), spawnTemplate.getHeading());
/*     */     
/* 121 */     world.spawn((VisibleObject)npc);
/* 122 */     rifts.add(npc);
/*     */     
/* 124 */     scheduleDespawn(npc);
/* 125 */     riftController.sendAnnounce();
/*     */     
/* 127 */     return npc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void scheduleDespawn(final Npc npc) {
/* 135 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 140 */             if (npc != null && npc.isSpawned()) {
/*     */               
/* 142 */               PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_DELETE((AionObject)npc, 15));
/* 143 */               npc.getController().onDespawn(true);
/*     */             } 
/* 145 */             RiftSpawnManager.rifts.remove(npc);
/*     */           }
/*     */         }1560000L);
/*     */   }
/*     */   
/*     */   public enum RiftEnum
/*     */   {
/* 152 */     ELTNEN_AM("ELTNEN_AM", "MORHEIM_AS", 12, 28, Race.ASMODIANS),
/* 153 */     ELTNEN_BM("ELTNEN_BM", "MORHEIM_BS", 20, 32, Race.ASMODIANS),
/* 154 */     ELTNEN_CM("ELTNEN_CM", "MORHEIM_CS", 35, 36, Race.ASMODIANS),
/* 155 */     ELTNEN_DM("ELTNEN_DM", "MORHEIM_DS", 35, 37, Race.ASMODIANS),
/* 156 */     ELTNEN_EM("ELTNEN_EM", "MORHEIM_ES", 45, 40, Race.ASMODIANS),
/* 157 */     ELTNEN_FM("ELTNEN_FM", "MORHEIM_FS", 50, 40, Race.ASMODIANS),
/* 158 */     ELTNEN_GM("ELTNEN_GM", "MORHEIM_GS", 50, 45, Race.ASMODIANS),
/*     */     
/* 160 */     HEIRON_AM("HEIRON_AM", "BELUSLAN_AS", 24, 35, Race.ASMODIANS),
/* 161 */     HEIRON_BM("HEIRON_BM", "BELUSLAN_BS", 36, 35, Race.ASMODIANS),
/* 162 */     HEIRON_CM("HEIRON_CM", "BELUSLAN_CS", 48, 46, Race.ASMODIANS),
/* 163 */     HEIRON_DM("HEIRON_DM", "BELUSLAN_DS", 48, 40, Race.ASMODIANS),
/* 164 */     HEIRON_EM("HEIRON_EM", "BELUSLAN_ES", 60, 50, Race.ASMODIANS),
/* 165 */     HEIRON_FM("HEIRON_FM", "BELUSLAN_FS", 60, 50, Race.ASMODIANS),
/* 166 */     HEIRON_GM("HEIRON_GM", "BELUSLAN_GS", 72, 50, Race.ASMODIANS),
/*     */     
/* 168 */     MORHEIM_AM("MORHEIM_AM", "ELTNEN_AS", 12, 28, Race.ELYOS),
/* 169 */     MORHEIM_BM("MORHEIM_BM", "ELTNEN_BS", 20, 32, Race.ELYOS),
/* 170 */     MORHEIM_CM("MORHEIM_CM", "ELTNEN_CS", 35, 36, Race.ELYOS),
/* 171 */     MORHEIM_DM("MORHEIM_DM", "ELTNEN_DS", 35, 37, Race.ELYOS),
/* 172 */     MORHEIM_EM("MORHEIM_EM", "ELTNEN_ES", 45, 40, Race.ELYOS),
/* 173 */     MORHEIM_FM("MORHEIM_FM", "ELTNEN_FS", 50, 40, Race.ELYOS),
/* 174 */     MORHEIM_GM("MORHEIM_GM", "ELTNEN_GS", 50, 45, Race.ELYOS),
/*     */     
/* 176 */     BELUSLAN_AM("BELUSLAN_AM", "HEIRON_AS", 24, 35, Race.ELYOS),
/* 177 */     BELUSLAN_BM("BELUSLAN_BM", "HEIRON_BS", 36, 35, Race.ELYOS),
/* 178 */     BELUSLAN_CM("BELUSLAN_CM", "HEIRON_CS", 48, 46, Race.ELYOS),
/* 179 */     BELUSLAN_DM("BELUSLAN_DM", "HEIRON_DS", 48, 40, Race.ELYOS),
/* 180 */     BELUSLAN_EM("BELUSLAN_EM", "HEIRON_ES", 60, 50, Race.ELYOS),
/* 181 */     BELUSLAN_FM("BELUSLAN_FM", "HEIRON_FS", 60, 50, Race.ELYOS),
/* 182 */     BELUSLAN_GM("BELUSLAN_GM", "HEIRON_GS", 72, 50, Race.ELYOS);
/*     */     
/*     */     private String master;
/*     */     
/*     */     private String slave;
/*     */     private int entries;
/*     */     private int maxLevel;
/*     */     private Race destination;
/*     */     
/*     */     RiftEnum(String master, String slave, int entries, int maxLevel, Race destination) {
/* 192 */       this.master = master;
/* 193 */       this.slave = slave;
/* 194 */       this.entries = entries;
/* 195 */       this.maxLevel = maxLevel;
/* 196 */       this.destination = destination;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getMaster() {
/* 204 */       return this.master;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getSlave() {
/* 212 */       return this.slave;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getEntries() {
/* 220 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaxLevel() {
/* 228 */       return this.maxLevel;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Race getDestination() {
/* 236 */       return this.destination;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendRiftStatus(Player activePlayer) {
/* 245 */     for (Npc rift : rifts) {
/*     */       
/* 247 */       if (rift.getWorldId() == activePlayer.getWorldId())
/*     */       {
/* 249 */         ((RiftController)rift.getController()).sendMessage(activePlayer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\spawnengine\RiftSpawnManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */