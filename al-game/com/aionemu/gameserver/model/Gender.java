package com.aionemu.gameserver.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Gender {
  MALE(0),

  FEMALE(1);

  private int genderId;

  Gender(int genderId) {
    this.genderId = genderId;
  }

  public int getGenderId() {
    return this.genderId;
  }
}
