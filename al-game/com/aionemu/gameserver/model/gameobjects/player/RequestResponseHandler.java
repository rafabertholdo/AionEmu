package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.gameobjects.Creature;

public abstract class RequestResponseHandler {
  private Creature requester;

  public RequestResponseHandler(Creature requester) {
    this.requester = requester;
  }

  public void handle(Player responder, int response) {
    if (response == 0) {
      denyRequest(this.requester, responder);
    } else {
      acceptRequest(this.requester, responder);
    }
  }

  public abstract void acceptRequest(Creature paramCreature, Player paramPlayer);

  public abstract void denyRequest(Creature paramCreature, Player paramPlayer);
}
