package quest.ishalgen;

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




















public class _2000Prologue
  extends QuestHandler
{
  private static final int questId = 2000;
  
  public _2000Prologue() {
    super(Integer.valueOf(2000));
  }


  
  public void register() {
    this.qe.addOnEnterWorld(2000);
    this.qe.setQuestMovieEndIds(2).add(2000);
  }


  
  public boolean onEnterWorldEvent(QuestEnv env) {
    Player player = env.getPlayer();
    if (player.getCommonData().getRace() != Race.ASMODIANS)
      return false; 
    QuestState qs = player.getQuestStateList().getQuestState(2000);
    if (qs == null) {
      
      env.setQuestId(Integer.valueOf(2000));
      QuestService.startQuest(env, QuestStatus.START);
    } 
    qs = player.getQuestStateList().getQuestState(2000);
    if (qs.getStatus() == QuestStatus.START) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(1, 2));
      return true;
    } 
    return false;
  }


  
  public boolean onMovieEndEvent(QuestEnv env, int movieId) {
    if (movieId != 2)
      return false; 
    Player player = env.getPlayer();
    if (player.getCommonData().getRace() != Race.ASMODIANS)
      return false; 
    QuestState qs = player.getQuestStateList().getQuestState(2000);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false; 
    qs.setStatus(QuestStatus.REWARD);
    QuestService.questFinish(env);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2000Prologue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
