package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1035RefreshingtheSprings extends QuestHandler {
  private static final int questId = 1035;
  private static final int[] npc_ids = new int[] { 203917, 203992, 700158, 203965, 203968, 203987, 700160, 203934,
      700159 };

  public _1035RefreshingtheSprings() {
    super(Integer.valueOf(1035));
  }

  public void register() {
    this.qe.addQuestLvlUp(1035);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1035);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1035);
    boolean lvlCheck = QuestService.checkLevelRequirement(1035, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1035);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203917) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {
      
      return false;
    } 
    if (targetId == 203917) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          return false;
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10001:
          if (var == 4) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 203992) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10002:
          if (var == 3) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    } else {
      if (targetId == 700158 && var == 2) {
        
        if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182201014) == 1L) {
          
          final int targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  ItemService.decreaseItemCountByItemId(player, 182201014, 1L);
                  qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                  _1035RefreshingtheSprings.this.updateQuestStatus(player, qs);
                }
              }3000L);
        } 
        return false;
      } 
      if (targetId == 203965) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 4)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          case 10003:
            if (var == 4) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            return false;
        } 
      
      } else if (targetId == 203968) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 5)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
          case 10004:
            if (var == 5) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            return false;
        } 
      
      } else if (targetId == 203987) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 6)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
            if (var == 8)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
          case 10005:
            if (var == 6) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              ItemService.addItems(player, Collections.singletonList(new QuestItems(182201024, 1)));
              return true;
            } 
          case 10006:
            if (var == 8) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              ItemService.addItems(player, Collections.singletonList(new QuestItems(182201025, 1)));
              return true;
            } 
            return false;
        } 
      } else {
        if (targetId == 700160 && var == 7) {
          
          if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182201024) == 1L) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 31));
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    ItemService.decreaseItemCountByItemId(player, 182201024, 1L);
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    _1035RefreshingtheSprings.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 
          return false;
        } 
        if (targetId == 203934)
        
        { switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 9)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
              if (var == 11)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
            case 10007:
              if (var == 9) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              }
              else if (var == 11) {
                
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return true;
              } 
              return false;
          } 
           }
        else if (targetId == 700159 && var == 10)
        
        { if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182201025) == 1L) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    ItemService.decreaseItemCountByItemId(player, 182201025, 1L);
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    _1035RefreshingtheSprings.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 
          return false; } 
      } 
    }  return false;
  }
}
