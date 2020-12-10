package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;





















public class _1016SourceOfThePollution
  extends QuestHandler
{
  private static final int questId = 1016;
  private static final int[] npc_ids = new int[] { 203149, 203148, 203832, 203705, 203822, 203761, 203098, 203195 };

  
  public _1016SourceOfThePollution() {
    super(Integer.valueOf(1016));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1016);
    this.qe.setNpcQuestData(210318).addOnKillEvent(1016);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1016);
    }
  }

  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1016);
    boolean lvlCheck = QuestService.checkLevelRequirement(1016, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1016);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203098) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    if (qs.getStatus() == QuestStatus.START)
    {
      switch (targetId) {
        
        case 203149:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              if (var == 7)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
              if (var == 8) {
                
                if (player.getInventory().getItemCountByItemId(182200015) == 0L) {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3484);
                }
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3569);
              } 
            case 3400:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 28));
              break;
            case 10000:
            case 10002:
              if (var == 0 || var == 2) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
            case 10007:
              if (var == 7) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                ItemService.decreaseItemCountByItemId(player, 182200013, 1L);
                ItemService.decreaseItemCountByItemId(player, 182200014, 1L);
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200015, 2))));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
            case 10008:
              if (var == 8) {
                
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200015, 2))));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
        case 203148:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            case 10001:
              if (var == 1) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200017, 1))));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
        case 203832:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 3)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
            case 10003:
              if (var == 3) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200013, 1))));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
        case 203705:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 4)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
            case 10004:
              if (var == 4) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
        case 203822:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 5)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
            case 10005:
              if (var == 5) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                ItemService.decreaseItemCountByItemId(player, 182200017, 1L);
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200018, 1))));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
        case 203761:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
            case 10006:
              if (var == 6) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                ItemService.decreaseItemCountByItemId(player, 182200018, 1L);
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200014, 1))));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
        case 203195:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 9)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
            case 10008:
              if (var == 9) {
                
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                ItemService.decreaseItemCountByItemId(player, 182200015, 1L);
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200016, 1))));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                final Npc npc = (Npc)env.getVisibleObject();
                ThreadPoolManager.getInstance().schedule(new Runnable()
                    {
                      public void run()
                      {
                        npc.getController().onDelete();
                      }
                    },  40000L);
                return true;
              }  break;
          } 
          break;
      }  } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1016);
    if (qs.getStatus() != QuestStatus.START) {
      return false;
    }
    final int instanceId = player.getInstanceId();
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    final Npc npc = (Npc)env.getVisibleObject();
    
    switch (targetId) {
      
      case 210318:
        if (var == 8) {
          
          qs.setQuestVar(9);
          updateQuestStatus(player, qs);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  QuestService.addNewSpawn(210030000, instanceId, 203195, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
                }
              }5000L);

          
          return true;
        }  break;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1016SourceOfThePollution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
