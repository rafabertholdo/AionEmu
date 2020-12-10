package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
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
import com.aionemu.gameserver.world.zone.ZoneName;




















public class _1023ANestofLepharists
  extends QuestHandler
{
  private static final int questId = 1023;
  
  public _1023ANestofLepharists() {
    super(Integer.valueOf(1023));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203098).addOnTalkEvent(1023);
    this.qe.setNpcQuestData(203183).addOnTalkEvent(1023);
    this.qe.setQuestEnterZone(ZoneName.MYSTERIOUS_SHIPWRECK).add(1023);
    this.qe.addQuestLvlUp(1023);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1023);
    boolean lvlCheck = QuestService.checkLevelRequirement(1023, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1023);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203098) {
        
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
            break;
        } 
      } else if (targetId == 203183) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          case 1012:
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 30));
            break;
          case 10000:
            if (var == 1 || var == 1) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            if (var == 3)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          case 10001:
            if (var == 3 || var == 3) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            if (var == 4) {
              
              long itemCount = player.getInventory().getItemCountByItemId(182200026);
              if (itemCount >= 1L) {
                
                if (env.getDialogId().intValue() == 33)
                {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
                }

                
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 23));
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
      
      if (targetId == 203098)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }


  
  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.MYSTERIOUS_SHIPWRECK)
      return false; 
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1023);
    if (qs == null || qs.getQuestVars().getQuestVars() != 2)
      return false; 
    env.setQuestId(Integer.valueOf(1023));
    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1023ANestofLepharists.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
