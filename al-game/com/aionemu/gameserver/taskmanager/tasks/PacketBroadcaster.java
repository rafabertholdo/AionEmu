/*     */ package com.aionemu.gameserver.taskmanager.tasks;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;
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
/*     */ public final class PacketBroadcaster
/*     */   extends AbstractFIFOPeriodicTaskManager<Creature>
/*     */ {
/*     */   private static final class SingletonHolder
/*     */   {
/*  31 */     private static final PacketBroadcaster INSTANCE = new PacketBroadcaster();
/*     */   }
/*     */ 
/*     */   
/*     */   public static PacketBroadcaster getInstance() {
/*  36 */     return SingletonHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   private PacketBroadcaster() {
/*  41 */     super(100);
/*     */   }
/*     */   
/*     */   public enum BroadcastMode
/*     */   {
/*  46 */     UPDATE_PLAYER_HP_STAT
/*     */     {
/*     */       public void sendPacket(Creature creature)
/*     */       {
/*  50 */         ((Player)creature).getLifeStats().sendHpPacketUpdateImpl();
/*     */       }
/*     */     },
/*  53 */     UPDATE_PLAYER_MP_STAT
/*     */     {
/*     */       public void sendPacket(Creature creature)
/*     */       {
/*  57 */         ((Player)creature).getLifeStats().sendMpPacketUpdateImpl();
/*     */       }
/*     */     },
/*  60 */     UPDATE_PLAYER_EFFECT_ICONS
/*     */     {
/*     */       public void sendPacket(Creature creature)
/*     */       {
/*  64 */         ((Player)creature).getEffectController().updatePlayerEffectIconsImpl();
/*     */       }
/*     */     },
/*  67 */     UPDATE_NEARBY_QUEST_LIST
/*     */     {
/*     */       public void sendPacket(Creature creature)
/*     */       {
/*  71 */         ((Player)creature).getController().updateNearbyQuestListImpl();
/*     */       }
/*     */     },
/*  74 */     UPDATE_PLAYER_FLY_TIME
/*     */     {
/*     */       public void sendPacket(Creature creature)
/*     */       {
/*  78 */         ((Player)creature).getLifeStats().sendFpPacketUpdateImpl();
/*     */       }
/*     */     },
/*  81 */     BROAD_CAST_EFFECTS
/*     */     {
/*     */       public void sendPacket(Creature creature)
/*     */       {
/*  85 */         creature.getEffectController().broadCastEffectsImp();
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     private final byte MASK;
/*     */ 
/*     */     
/*     */     BroadcastMode() {
/*  94 */       this.MASK = (byte)(1 << ordinal());
/*     */     }
/*     */ 
/*     */     
/*     */     public byte mask() {
/*  99 */       return this.MASK;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void trySendPacket(Creature creature, byte mask) {
/* 106 */       if ((mask & mask()) == mask()) {
/*     */         
/* 108 */         sendPacket(creature);
/* 109 */         creature.removePacketBroadcastMask(this);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected abstract void sendPacket(Creature param1Creature); }
/* 114 */   private static final BroadcastMode[] VALUES = BroadcastMode.values();
/*     */ 
/*     */   
/*     */   protected void callTask(Creature creature) {
/*     */     byte mask;
/* 119 */     while ((mask = creature.getPacketBroadcastMask()) != 0) {
/*     */       
/* 121 */       for (BroadcastMode mode : VALUES)
/*     */       {
/* 123 */         mode.trySendPacket(creature, mask);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCalledMethodName() {
/* 131 */     return "packetBroadcast()";
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\tasks\PacketBroadcaster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */