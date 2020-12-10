package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;




















public class _1012MaskedLoiterers
  extends QuestHandler
{
  private static final int questId = 1012;
  
  public _1012MaskedLoiterers() {
    super(Integer.valueOf(1012));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203111).addOnTalkEvent(1012);
    this.qe.setQuestEnterZone(ZoneName.Q1012).add(1012);
    this.qe.addQuestLvlUp(1012);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1012);
    boolean lvlCheck = QuestService.checkLevelRequirement(1012, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1012);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203111)
      {
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          case 10000:
            if (var == 0 || var == 0) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            if (var == 2)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          case 10001:
            if (var == 2 || var == 2) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            if (var == 3) {
              
              long itemCount = player.getInventory().getItemCountByItemId(182200010);
              if (itemCount >= 5L) {
                
                if (env.getDialogId().intValue() == 33)
                {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
                }

                
                ItemService.removeItemFromInventoryByItemId(player, 182200010);
                qs.setQuestVarById(0, var + 1);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                
                return true;
              } 

              
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
            } 
            return true;
        } 
      
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203111)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }


  
  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.Q1012)
      return false; 
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1012);
    if (qs == null || qs.getQuestVars().getQuestVars() != 1)
      return false; 
    env.setQuestId(Integer.valueOf(1012));
    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1012MaskedLoiterers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
