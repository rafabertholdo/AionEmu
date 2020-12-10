package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _1032ARulersDuty extends QuestHandler {
  private static final int questId = 1032;

  public _1032ARulersDuty() {
    super(Integer.valueOf(1032));
  }

  public void register() {
    this.qe.setQuestItemIds(182201001).add(1032);
    this.qe.setNpcQuestData(203932).addOnTalkEvent(1032);
    this.qe.setNpcQuestData(730020).addOnTalkEvent(1032);
    this.qe.setNpcQuestData(730019).addOnTalkEvent(1032);
    this.qe.setNpcQuestData(700157).addOnTalkEvent(1032);
    this.qe.addQuestLvlUp(1032);
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201001)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.PUTRID_MIRE))
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(1032);
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            qs.setQuestVar(4);
            _1032ARulersDuty.this.updateQuestStatus(player, qs);
          }
        }3000L);
    
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1032);
    boolean lvlCheck = QuestService.checkLevelRequirement(1032, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1032);
    if (qs == null)
      return false; 
    if (targetId == 203932) {
      
      if (qs.getStatus() == QuestStatus.START) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 10000) {
          
          qs.setQuestVar(1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.REWARD)
      {
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
        return defaultQuestEndDialog(env);
      }
    
    } else if (targetId == 730020) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        if (env.getDialogId().intValue() == 10001) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 10004) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      }
    
    }
    else if (targetId == 730019) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        if (env.getDialogId().intValue() == 10002) {
          
          ItemService.decreaseItemCountByItemId(player, 182201001, 1L);
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 49));
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        if (env.getDialogId().intValue() == 10003) {
          
          qs.setQuestVar(5);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      }
    
    }
    else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
      
      switch (targetId) {

        
        case 700157:
          if (qs.getQuestVarById(0) == 3 && env.getDialogId().intValue() == -1) {
            
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201001, 1)));
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  }
                }3000L);
          } 
          break;
      } 


    
    } 
    return false;
  }
}
