package com.aionemu.gameserver.model.templates.mail;

public enum MailMessage {
  MAIL_SEND_SECCESS(0), NO_SUCH_CHARACTER_NAME(1), RECIPIENT_MAILBOX_FULL(2), MAIL_IS_ONE_RACE_ONLY(3),
  YOU_ARE_IN_RECIPIENT_IGNORE_LIST(4), RECIPIENT_IGNORING_MAIL_FROM_PLAYERS_LOWER_206_LVL(5),
  MAILSPAM_WAIT_FOR_SOME_TIME(6);

  private int id;

  MailMessage(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
