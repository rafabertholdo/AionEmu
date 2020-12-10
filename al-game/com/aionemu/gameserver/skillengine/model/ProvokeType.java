package com.aionemu.gameserver.skillengine.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ProvokeType")
@XmlEnum
public enum ProvokeType {
  ATTACK, ATTACKED;
}
