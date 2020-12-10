package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;





















public class CM_PLAY_MOVIE_END
  extends AionClientPacket
{
  private int type;
  private int movieId;
  
  public CM_PLAY_MOVIE_END(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.type = readC();
    readD();
    readD();
    this.movieId = readH();
    readD();
  }





  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    QuestEngine.getInstance().onMovieEnd(new QuestEnv(null, activePlayer, Integer.valueOf(0), Integer.valueOf(0)), this.movieId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_PLAY_MOVIE_END.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
