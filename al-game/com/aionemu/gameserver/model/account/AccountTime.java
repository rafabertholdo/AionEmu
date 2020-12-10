package com.aionemu.gameserver.model.account;

public class AccountTime {
  private long accumulatedOnlineTime;
  private long accumulatedRestTime;

  public long getAccumulatedOnlineTime() {
    return this.accumulatedOnlineTime;
  }

  public void setAccumulatedOnlineTime(long accumulatedOnlineTime) {
    this.accumulatedOnlineTime = accumulatedOnlineTime;
  }

  public long getAccumulatedRestTime() {
    return this.accumulatedRestTime;
  }

  public void setAccumulatedRestTime(long accumulatedRestTime) {
    this.accumulatedRestTime = accumulatedRestTime;
  }

  public int getAccumulatedOnlineHours() {
    return toHours(this.accumulatedOnlineTime);
  }

  public int getAccumulatedOnlineMinutes() {
    return toMinutes(this.accumulatedOnlineTime);
  }

  public int getAccumulatedRestHours() {
    return toHours(this.accumulatedRestTime);
  }

  public int getAccumulatedRestMinutes() {
    return toMinutes(this.accumulatedRestTime);
  }

  private static int toHours(long millis) {
    return (int) (millis / 1000L) / 3600;
  }

  private static int toMinutes(long millis) {
    return (int) (millis / 1000L % 3600L) / 60;
  }
}
