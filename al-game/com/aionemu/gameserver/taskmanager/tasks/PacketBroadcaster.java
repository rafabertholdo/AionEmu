package com.aionemu.gameserver.taskmanager.tasks;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;




















public final class PacketBroadcaster
  extends AbstractFIFOPeriodicTaskManager<Creature>
{
  private static final class SingletonHolder
  {
    private static final PacketBroadcaster INSTANCE = new PacketBroadcaster();
  }

  
  public static PacketBroadcaster getInstance() {
    return SingletonHolder.INSTANCE;
  }

  
  private PacketBroadcaster() {
    super(100);
  }
  
  public enum BroadcastMode
  {
    UPDATE_PLAYER_HP_STAT
    {
      public void sendPacket(Creature creature)
      {
        ((Player)creature).getLifeStats().sendHpPacketUpdateImpl();
      }
    },
    UPDATE_PLAYER_MP_STAT
    {
      public void sendPacket(Creature creature)
      {
        ((Player)creature).getLifeStats().sendMpPacketUpdateImpl();
      }
    },
    UPDATE_PLAYER_EFFECT_ICONS
    {
      public void sendPacket(Creature creature)
      {
        ((Player)creature).getEffectController().updatePlayerEffectIconsImpl();
      }
    },
    UPDATE_NEARBY_QUEST_LIST
    {
      public void sendPacket(Creature creature)
      {
        ((Player)creature).getController().updateNearbyQuestListImpl();
      }
    },
    UPDATE_PLAYER_FLY_TIME
    {
      public void sendPacket(Creature creature)
      {
        ((Player)creature).getLifeStats().sendFpPacketUpdateImpl();
      }
    },
    BROAD_CAST_EFFECTS
    {
      public void sendPacket(Creature creature)
      {
        creature.getEffectController().broadCastEffectsImp();
      }
    };

    
    private final byte MASK;

    
    BroadcastMode() {
      this.MASK = (byte)(1 << ordinal());
    }

    
    public byte mask() {
      return this.MASK;
    }



    
    protected final void trySendPacket(Creature creature, byte mask) {
      if ((mask & mask()) == mask()) {
        
        sendPacket(creature);
        creature.removePacketBroadcastMask(this);
      } 
    }
    
    protected abstract void sendPacket(Creature param1Creature); }
  private static final BroadcastMode[] VALUES = BroadcastMode.values();

  
  protected void callTask(Creature creature) {
    byte mask;
    while ((mask = creature.getPacketBroadcastMask()) != 0) {
      
      for (BroadcastMode mode : VALUES)
      {
        mode.trySendPacket(creature, mask);
      }
    } 
  }


  
  protected String getCalledMethodName() {
    return "packetBroadcast()";
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\tasks\PacketBroadcaster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
