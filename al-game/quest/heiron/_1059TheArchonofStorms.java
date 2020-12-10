package quest.heiron;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.skillengine.effect.EffectId;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _1059TheArchonofStorms extends QuestHandler {
    private static final int questId = 1059;
    private static final int[] npc_ids = new int[] { 204505, 204533, 700282, 204535 };

    public _1059TheArchonofStorms() {
        super(Integer.valueOf(1059));
    }

    public void register() {
        this.qe.addQuestLvlUp(1059);
        this.qe.setQuestMovieEndIds(193).add(1059);
        this.qe.setQuestItemIds(182201619).add(1059);
        for (int npc_id : npc_ids) {
            this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1059);
        }
    }

    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1059);
        boolean lvlCheck = QuestService.checkLevelRequirement(1059, player.getCommonData().getLevel());
        if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
            return false;
        }
        QuestState qs2 = player.getQuestStateList().getQuestState(1500);
        if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
            return false;
        qs.setStatus(QuestStatus.START);
        updateQuestStatus(player, qs);
        return true;
    }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1059);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 204505) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {
      
      return false;
    } 
    if (targetId == 204505) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 204533) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10003:
          if (var == 3) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 204535) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        case 10004:
          if (var == 4) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201619, 1)));
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 700282 && var == 2) {
      
      if (env.getDialogId().intValue() == -1) {
        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 193));
              }
            }3000L);
      } 
    } 
    return false;
  }

  public boolean onMovieEndEvent(QuestEnv env, int movieId) {
    if (movieId != 193)
      return false; 
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1059);
    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 2)
      return false; 
    qs.setQuestVar(3);
    updateQuestStatus(player, qs);
    player.getEffectController().setAbnormal(EffectId.SHAPECHANGE.getEffectId());
    player.setTransformedModelId(212000);
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            player.getEffectController().unsetAbnormal(EffectId.SHAPECHANGE.getEffectId());
            player.setTransformedModelId(0);
            PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
          }
        }15000L);
    
    return true;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201619)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.PATEMA_GEYSER))
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(1059);
    if (qs == null)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 192));
            ItemService.decreaseItemCountByItemId(player, 182201619, 1L);
            qs.setQuestVarById(0, 5);
            qs.setStatus(QuestStatus.REWARD);
            _1059TheArchonofStorms.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\heiron\_1059TheArchonofStorms.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */