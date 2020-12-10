package com.aionemu.gameserver.spawnengine;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.controllers.RiftController;
import com.aionemu.gameserver.controllers.effect.EffectController;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.NpcTemplate;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.KnownList;
import com.aionemu.gameserver.world.World;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;

public class RiftSpawnManager {
  private static final Logger log = Logger.getLogger(RiftSpawnManager.class);

  private static final ConcurrentLinkedQueue<Npc> rifts = new ConcurrentLinkedQueue<Npc>();

  private static final int RIFT_RESPAWN_DELAY = 6000000;

  private static final int RIFT_LIFETIME = 1560000;

  private static final Map<String, SpawnGroup> spawnGroups = new HashMap<String, SpawnGroup>();

  public static void addRiftSpawnGroup(SpawnGroup spawnGroup) {
    spawnGroups.put(spawnGroup.getAnchor(), spawnGroup);
  }

  public static void startRiftPool() {
    ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
        {
          
          public void run()
          {
            RiftSpawnManager.RiftEnum rift1 = RiftSpawnManager.RiftEnum.values()[Rnd.get(0, 6)];
            RiftSpawnManager.RiftEnum rift2 = RiftSpawnManager.RiftEnum.values()[Rnd.get(7, 13)];
            RiftSpawnManager.RiftEnum rift3 = RiftSpawnManager.RiftEnum.values()[Rnd.get(14, 20)];
            RiftSpawnManager.RiftEnum rift4 = RiftSpawnManager.RiftEnum.values()[Rnd.get(21, 27)];
            
            RiftSpawnManager.spawnRift(rift1);
            RiftSpawnManager.spawnRift(rift2);
            RiftSpawnManager.spawnRift(rift3);
            RiftSpawnManager.spawnRift(rift4);
          }
        }10000L, 6000000L);
  }

  private static void spawnRift(RiftEnum rift) {
    log.info("Spawning rift : " + rift.name());
    SpawnGroup masterGroup = spawnGroups.get(rift.getMaster());
    SpawnGroup slaveGroup = spawnGroups.get(rift.getSlave());

    if (masterGroup == null || slaveGroup == null) {
      return;
    }
    int instanceCount = World.getInstance().getWorldMap(masterGroup.getMapid()).getInstanceCount();

    SpawnTemplate masterTemplate = masterGroup.getNextRandomTemplate();
    SpawnTemplate slaveTemplate = slaveGroup.getNextRandomTemplate();

    for (int i = 1; i <= instanceCount; i++) {

      Npc slave = spawnInstance(i, masterGroup, slaveTemplate, new RiftController(null, rift));
      spawnInstance(i, masterGroup, masterTemplate, new RiftController(slave, rift));
    }
  }

  private static Npc spawnInstance(int instanceIndex, SpawnGroup spawnGroup, SpawnTemplate spawnTemplate,
      RiftController riftController) {
    NpcTemplate masterObjectTemplate = DataManager.NPC_DATA.getNpcTemplate(spawnGroup.getNpcid());
    Npc npc = new Npc(IDFactory.getInstance().nextId(), (NpcController) riftController, spawnTemplate,
        (VisibleObjectTemplate) masterObjectTemplate);

    npc.setKnownlist(new KnownList((VisibleObject) npc));
    npc.setEffectController(new EffectController((Creature) npc));
    npc.getController().onRespawn();

    World world = World.getInstance();
    world.storeObject((AionObject) npc);
    world.setPosition((VisibleObject) npc, spawnTemplate.getWorldId(), instanceIndex, spawnTemplate.getX(),
        spawnTemplate.getY(), spawnTemplate.getZ(), spawnTemplate.getHeading());

    world.spawn((VisibleObject) npc);
    rifts.add(npc);

    scheduleDespawn(npc);
    riftController.sendAnnounce();

    return npc;
  }

  private static void scheduleDespawn(final Npc npc) {
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          
          public void run()
          {
            if (npc != null && npc.isSpawned()) {
              
              PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_DELETE((AionObject)npc, 15));
              npc.getController().onDespawn(true);
            } 
            RiftSpawnManager.rifts.remove(npc);
          }
        }1560000L);
  }

  public enum RiftEnum {
    ELTNEN_AM("ELTNEN_AM", "MORHEIM_AS", 12, 28, Race.ASMODIANS),
    ELTNEN_BM("ELTNEN_BM", "MORHEIM_BS", 20, 32, Race.ASMODIANS),
    ELTNEN_CM("ELTNEN_CM", "MORHEIM_CS", 35, 36, Race.ASMODIANS),
    ELTNEN_DM("ELTNEN_DM", "MORHEIM_DS", 35, 37, Race.ASMODIANS),
    ELTNEN_EM("ELTNEN_EM", "MORHEIM_ES", 45, 40, Race.ASMODIANS),
    ELTNEN_FM("ELTNEN_FM", "MORHEIM_FS", 50, 40, Race.ASMODIANS),
    ELTNEN_GM("ELTNEN_GM", "MORHEIM_GS", 50, 45, Race.ASMODIANS),

    HEIRON_AM("HEIRON_AM", "BELUSLAN_AS", 24, 35, Race.ASMODIANS),
    HEIRON_BM("HEIRON_BM", "BELUSLAN_BS", 36, 35, Race.ASMODIANS),
    HEIRON_CM("HEIRON_CM", "BELUSLAN_CS", 48, 46, Race.ASMODIANS),
    HEIRON_DM("HEIRON_DM", "BELUSLAN_DS", 48, 40, Race.ASMODIANS),
    HEIRON_EM("HEIRON_EM", "BELUSLAN_ES", 60, 50, Race.ASMODIANS),
    HEIRON_FM("HEIRON_FM", "BELUSLAN_FS", 60, 50, Race.ASMODIANS),
    HEIRON_GM("HEIRON_GM", "BELUSLAN_GS", 72, 50, Race.ASMODIANS),

    MORHEIM_AM("MORHEIM_AM", "ELTNEN_AS", 12, 28, Race.ELYOS),
    MORHEIM_BM("MORHEIM_BM", "ELTNEN_BS", 20, 32, Race.ELYOS),
    MORHEIM_CM("MORHEIM_CM", "ELTNEN_CS", 35, 36, Race.ELYOS),
    MORHEIM_DM("MORHEIM_DM", "ELTNEN_DS", 35, 37, Race.ELYOS),
    MORHEIM_EM("MORHEIM_EM", "ELTNEN_ES", 45, 40, Race.ELYOS),
    MORHEIM_FM("MORHEIM_FM", "ELTNEN_FS", 50, 40, Race.ELYOS),
    MORHEIM_GM("MORHEIM_GM", "ELTNEN_GS", 50, 45, Race.ELYOS),

    BELUSLAN_AM("BELUSLAN_AM", "HEIRON_AS", 24, 35, Race.ELYOS),
    BELUSLAN_BM("BELUSLAN_BM", "HEIRON_BS", 36, 35, Race.ELYOS),
    BELUSLAN_CM("BELUSLAN_CM", "HEIRON_CS", 48, 46, Race.ELYOS),
    BELUSLAN_DM("BELUSLAN_DM", "HEIRON_DS", 48, 40, Race.ELYOS),
    BELUSLAN_EM("BELUSLAN_EM", "HEIRON_ES", 60, 50, Race.ELYOS),
    BELUSLAN_FM("BELUSLAN_FM", "HEIRON_FS", 60, 50, Race.ELYOS),
    BELUSLAN_GM("BELUSLAN_GM", "HEIRON_GS", 72, 50, Race.ELYOS);

    private String master;

    private String slave;
    private int entries;
    private int maxLevel;
    private Race destination;

    RiftEnum(String master, String slave, int entries, int maxLevel, Race destination) {
      this.master = master;
      this.slave = slave;
      this.entries = entries;
      this.maxLevel = maxLevel;
      this.destination = destination;
    }

    public String getMaster() {
      return this.master;
    }

    public String getSlave() {
      return this.slave;
    }

    public int getEntries() {
      return this.entries;
    }

    public int getMaxLevel() {
      return this.maxLevel;
    }

    public Race getDestination() {
      return this.destination;
    }
  }

  public static void sendRiftStatus(Player activePlayer) {
    for (Npc rift : rifts) {

      if (rift.getWorldId() == activePlayer.getWorldId()) {
        ((RiftController) rift.getController()).sendMessage(activePlayer);
      }
    }
  }
}
