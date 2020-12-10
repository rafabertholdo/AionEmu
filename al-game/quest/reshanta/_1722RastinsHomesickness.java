package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;




















public class _1722RastinsHomesickness
  extends QuestHandler
{
  private static final int questId = 1722;
  private static final int[] npc_ids = new int[] { 278547, 278560, 278517, 278544, 278532, 278539, 278524, 278555, 278567 };

  
  public _1722RastinsHomesickness() {
    super(Integer.valueOf(1722));
  }


  
  public void register() {
    this.qe.setNpcQuestData(278547).addOnQuestStart(1722);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1722);
    }
  }

  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1722);
    if (targetId == 278547)
    {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      } 
    }
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 278547)
        ItemService.removeItemFromInventoryByItemId(player, 182202101); 
      return defaultQuestEndDialog(env);
    } 
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 278560) {
      
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
    
    } else if (targetId == 278517) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 278544) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        case 1009:
          if (var == 2) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 278532) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        case 10003:
          if (var == 3) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 278539) {
      
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
          } 
          return false;
      } 
    
    } else if (targetId == 278524) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
        case 10005:
          if (var == 5) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 278555) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
        case 10006:
          if (var == 6) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 278567) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 7)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
        case 10255:
          if (var == 7) {
            
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182202101, 1)));
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1722RastinsHomesickness.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
