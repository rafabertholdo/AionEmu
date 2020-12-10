package com.aionemu.gameserver.model;

public class Announcement {
  private int id;
  private String faction;
  private String announce;
  private String chatType;
  private int delay;

  public Announcement(String announce, String faction, String chatType, int delay) {
    this.announce = announce;

    if (!faction.equalsIgnoreCase("ELYOS") && !faction.equalsIgnoreCase("ASMODIANS")) {
      faction = "ALL";
    }
    this.faction = faction;
    this.chatType = chatType;
    this.delay = delay;
  }

  public Announcement(int id, String announce, String faction, String chatType, int delay) {
    this.id = id;
    this.announce = announce;

    if (!faction.equalsIgnoreCase("ELYOS") && !faction.equalsIgnoreCase("ASMODIANS")) {
      faction = "ALL";
    }
    this.faction = faction;
    this.chatType = chatType;
    this.delay = delay;
  }

  public int getId() {
    if (this.id != 0) {
      return this.id;
    }
    return -1;
  }

  public String getAnnounce() {
    return this.announce;
  }

  public String getFaction() {
    return this.faction;
  }

  public Race getFactionEnum() {
    if (this.faction.equalsIgnoreCase("ELYOS"))
      return Race.ELYOS;
    if (this.faction.equalsIgnoreCase("ASMODIANS")) {
      return Race.ASMODIANS;
    }
    return null;
  }

  public String getType() {
    return this.chatType;
  }

  public ChatType getChatType() {
    if (this.chatType.equalsIgnoreCase("NORMAL"))
      return ChatType.PERIOD_NOTICE;
    if (this.chatType.equalsIgnoreCase("YELLOW"))
      return ChatType.ANNOUNCEMENTS;
    if (this.chatType.equalsIgnoreCase("SHOUT"))
      return ChatType.SHOUT;
    if (this.chatType.equalsIgnoreCase("ORANGE")) {
      return ChatType.GROUP_LEADER;
    }
    return ChatType.SYSTEM_NOTICE;
  }

  public int getDelay() {
    return this.delay;
  }
}
