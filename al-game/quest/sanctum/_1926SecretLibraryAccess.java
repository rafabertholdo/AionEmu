package quest.sanctum;

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
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapType;
import java.util.Collections;

public class _1926SecretLibraryAccess extends QuestHandler {
  private static final int questId = 1926;
  private static final int[] npc_ids = new int[] { 203894, 203098 };

  public _1926SecretLibraryAccess() {
    super(Integer.valueOf(1926));
  }

  public void register() {
    this.qe.setNpcQuestData(203894).addOnQuestStart(1926);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1926);
    }
  }

  private boolean AreVerteronQuestsFinished(Player player) {
    QuestState qs = player.getQuestStateList().getQuestState(1020);
    return !(qs == null || qs.getStatus() != QuestStatus.COMPLETE);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1926);
    
    if (targetId == 203894) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762); 
        return defaultQuestStartDialog(env);
      }  if ((qs.getStatus() == QuestStatus.REWARD && qs.getQuestVarById(0) == 0) || qs.getStatus() == QuestStatus.COMPLETE) {
        
        if (env.getDialogId().intValue() == -1 && qs.getStatus() == QuestStatus.REWARD)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
        if (env.getDialogId().intValue() == 17) {
          ItemService.removeItemFromInventoryByItemId(player, 182206022);
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        }  if (env.getDialogId().intValue() == 1009) {
          return defaultQuestEndDialog(env);
        }
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              
              public void run()
              {
                TeleportService.teleportTo(player, WorldMapType.SANCTUM.getId(), 2032.9F, 1473.1F, 592.2F, 195);
              }
            }3000L);
      } 
    } else if (targetId == 203098) {
      
      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        
        if (env.getDialogId().intValue() == 25) {
          if (AreVerteronQuestsFinished(player))
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1097);
        } 
        if (env.getDialogId().intValue() == 10255) {
          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206022, 1)))) {
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
          } 
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
          return true;
        } 
        return defaultQuestStartDialog(env);
      } 
    } 
    return false;
  }
}
