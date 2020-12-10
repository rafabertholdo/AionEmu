package quest.poeta;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _1000Prologue
  extends QuestHandler
{
  private static final int questId = 1000;
  
  public _1000Prologue() {
    super(Integer.valueOf(1000));
  }


  
  public void register() {
    this.qe.addOnEnterWorld(1000);
    this.qe.setQuestMovieEndIds(1).add(1000);
  }


  
  public boolean onEnterWorldEvent(QuestEnv env) {
    Player player = env.getPlayer();
    if (player.getCommonData().getRace() != Race.ELYOS)
      return false; 
    QuestState qs = player.getQuestStateList().getQuestState(1000);
    if (qs == null) {
      
      env.setQuestId(Integer.valueOf(1000));
      QuestService.startQuest(env, QuestStatus.START);
    } 
    qs = player.getQuestStateList().getQuestState(1000);
    if (qs.getStatus() == QuestStatus.START) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(1, 1));
      return true;
    } 
    return false;
  }


  
  public boolean onMovieEndEvent(QuestEnv env, int movieId) {
    if (movieId != 1)
      return false; 
    Player player = env.getPlayer();
    if (player.getCommonData().getRace() != Race.ELYOS)
      return false; 
    QuestState qs = player.getQuestStateList().getQuestState(1000);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false; 
    qs.setStatus(QuestStatus.REWARD);
    QuestService.questFinish(env);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1000Prologue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
