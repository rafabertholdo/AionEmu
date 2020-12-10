package quest.poeta;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _1107TheLostAxe
  extends QuestHandler
{
  private static final int questId = 1107;
  
  public _1107TheLostAxe() {
    super(Integer.valueOf(1107));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203075).addOnTalkEvent(1107);
    this.qe.setQuestItemIds(182200501).add(1107);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1107);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (targetId == 0) {
      
      if (env.getDialogId().intValue() == 1002) {
        
        QuestService.startQuest(env, QuestStatus.START);
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
        return true;
      } 
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
    }
    else if (targetId == 203075) {
      
      if (qs != null) {
        
        if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        }
        if (env.getDialogId().intValue() == 1009) {
          
          ItemService.removeItemFromInventoryByItemId(player, 182200501);
          qs.setQuestVar(1);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        } 
        
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onItemUseEvent(QuestEnv env, Item item) {
    Player player = env.getPlayer();
    int id = item.getItemTemplate().getTemplateId();
    int itemObjId = item.getObjectId();
    QuestState qs = player.getQuestStateList().getQuestState(1107);
    
    if (id != 182200501)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
      sendQuestDialog(player, 0, 4); 
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1107TheLostAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
