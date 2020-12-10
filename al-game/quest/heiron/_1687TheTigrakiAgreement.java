package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;
import org.apache.log4j.Logger;






















public class _1687TheTigrakiAgreement
  extends QuestHandler
{
  private static final int questId = 1687;
  private int Choix;
  private static final Logger log = Logger.getLogger(_1687TheTigrakiAgreement.class);

  
  public _1687TheTigrakiAgreement() {
    super(Integer.valueOf(1687));
  }


  
  public void register() {
    this.qe.setNpcQuestData(204601).addOnQuestStart(1687);
    this.qe.setNpcQuestData(204601).addOnTalkEvent(1687);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1687);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE)
    {
      if (targetId == 204601) {
        
        if (env.getDialogId().intValue() == 25)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        
        return defaultQuestStartDialog(env);
      } 
    }
    
    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {
      long itemCount1; long itemCount2;
      switch (targetId) {

        
        case 204601:
          switch (env.getDialogId().intValue()) {

            
            case 25:
              itemCount1 = player.getInventory().getItemCountByItemId(186000035);
              itemCount2 = player.getInventory().getItemCountByItemId(186000036);
              if (itemCount1 >= 2L && itemCount2 >= 5L)
              {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              }

            
            case 10009:
              SetChoix(1);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);

            
            case 10019:
              SetChoix(2);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);

            
            case 10029:
              SetChoix(3);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);

            
            case 8:
              log.info("Received Choix id :" + getChoix());
              switch (getChoix()) {

                
                case 1:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111100788, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 2:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112100747, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 3:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114100825, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;
              } 
            
            
            
            case 9:
              switch (getChoix()) {

                
                case 1:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111300792, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 2:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112300744, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 3:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114300851, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;
              } 
            
            
            
            case 10:
              switch (getChoix()) {

                
                case 1:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111500775, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 2:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112500732, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 3:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114500797, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;
              } 
            
            
            
            case 11:
              switch (getChoix()) {

                
                case 1:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111600767, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 2:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112600743, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;

                
                case 3:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114600754, 1))))
                  {
                    
                    return true;
                  }
                  QuestFinish(env);
                  return true;
              } 
              
              break;
          } 
          break;
      } 
    } 
    return false;
  }

  
  public int getChoix() {
    return this.Choix;
  }

  
  public void SetChoix(int Choix) {
    this.Choix = Choix;
  }

  
  private void QuestFinish(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1687);
    
    ItemService.removeItemFromInventoryByItemId(player, 186000035);
    ItemService.removeItemFromInventoryByItemId(player, 186000036);
    qs.setStatus(QuestStatus.COMPLETE);
    qs.setCompliteCount(qs.getCompliteCount() + 1);
    int rewardExp = player.getRates().getQuestXpRate() * 1535800;
    int rewardAbyssPoint = player.getRates().getQuestXpRate() * 200;
    player.getCommonData().addExp(rewardExp);
    player.getCommonData().addAp(rewardAbyssPoint);
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(1687, QuestStatus.COMPLETE, 2));
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
    updateQuestStatus(player, qs);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1687TheTigrakiAgreement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
