package com.aionemu.gameserver.model.broker;

public enum BrokerMessages {
  NO_ENOUGHT_KINAH(1), CANT_REGISTER_ITEM(2);

  private int id;

  BrokerMessages(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
