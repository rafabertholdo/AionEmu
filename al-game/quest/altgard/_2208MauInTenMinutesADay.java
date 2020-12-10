package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;






















public class _2208MauInTenMinutesADay
  extends QuestHandler
{
  private static final int questId = 2208;
  
  public _2208MauInTenMinutesADay() {
    super(Integer.valueOf(2208));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203591).addOnQuestStart(2208);
    this.qe.setNpcQuestData(203591).addOnTalkEvent(2208);
    this.qe.setNpcQuestData(203589).addOnTalkEvent(2208);
    this.qe.setQuestItemIds(182203205).add(2208);
    this.deletebleItems = new int[] { 182203205 };
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(2208);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203591)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 1002) {
          
          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203205, 1))))
            return defaultQuestStartDialog(env); 
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      }
    
    } else if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203589) {
        
        int var = qs.getQuestVarById(0);
        if (env.getDialogId().intValue() == 25) {
          
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          if (var == 1) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          }
        } else if (env.getDialogId().intValue() == 10000) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }
      
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203591)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }


  
  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182203205)
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(2208);
    if (qs == null)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.removeItemFromInventory(player, item);
            qs.setQuestVarById(0, 1);
            _2208MauInTenMinutesADay.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2208MauInTenMinutesADay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
