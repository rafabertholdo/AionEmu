package quest.altgard;

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
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;





















public class _2013ADangerousCrop
  extends QuestHandler
{
  private static final int questId = 2013;
  
  public _2013ADangerousCrop() {
    super(Integer.valueOf(2013));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2013);
    this.qe.setNpcQuestData(203605).addOnTalkEvent(2013);
    this.qe.setNpcQuestData(700096).addOnTalkEvent(2013);
    this.qe.setQuestEnterZone(ZoneName.MUMU_FARMLAND_220030000).add(2013);
    this.deletebleItems = new int[] { 182203012 };
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(2013);
    if (qs == null) {
      return false;
    }
    final int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203605:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              if (var == 8)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              if (var == 9)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
            case 1012:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 61));
              break;
            case 10000:
            case 10001:
            case 10002:
              if (var == 0 || var == 2 || var == 8) {
                
                if (var == 2 && 
                  !ItemService.addItems(player, Collections.singletonList(new QuestItems(182203012, 1))))
                  return true; 
                if (var == 8)
                  ItemService.removeItemFromInventoryByItemId(player, 182203012); 
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
            case 33:
              if (var == 9) {
                
                if (QuestService.collectItemCheck(env, true)) {
                  
                  qs.setStatus(QuestStatus.REWARD);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                } 
                
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
              }  break;
          } 
          break;
        case 700096:
          switch (env.getDialogId().intValue()) {
            
            case -1:
              if (var >= 3 && var < 6) {
                
                final int targetObjectId = env.getVisibleObject().getObjectId();
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
                
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
                
                ThreadPoolManager.getInstance().schedule(new Runnable()
                    {
                      public void run()
                      {
                        if (!player.isTargeting(targetObjectId))
                          return; 
                        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                        
                        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                        
                        if (var == 5) {
                          qs.setQuestVarById(0, 8);
                        } else {
                          qs.setQuestVarById(0, var + 1);
                        }  _2013ADangerousCrop.this.updateQuestStatus(player, qs);
                      }
                    }3000L);
                
                return true;
              }  break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203605)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }


  
  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.MUMU_FARMLAND_220030000)
      return false; 
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2013);
    if (qs == null)
      return false; 
    if (qs.getQuestVarById(0) == 1) {
      
      qs.setQuestVarById(0, 2);
      updateQuestStatus(player, qs);
      return true;
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2013);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2013ADangerousCrop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
