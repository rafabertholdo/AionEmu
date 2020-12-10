package com.aionemu.commons.network.packet;

public abstract class BasePacket {
  public static final String TYPE_PATTERN = "[%s] 0x%02X %s";
  private final PacketType packetType;
  private int opcode;

  protected BasePacket(PacketType packetType, int opcode) {
    this.packetType = packetType;
    this.opcode = opcode;
  }

  protected BasePacket(PacketType packetType) {
    this.packetType = packetType;
  }

  protected void setOpcode(int opcode) {
    this.opcode = opcode;
  }

  public final int getOpcode() {
    return this.opcode;
  }

  public final PacketType getPacketType() {
    return this.packetType;
  }

  public String getPacketName() {
    return getClass().getSimpleName();
  }

  public enum PacketType {
    SERVER("S"),

    CLIENT("C");

    private final String name;

    PacketType(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
  }

  public String toString() {
    return String.format("[%s] 0x%02X %s",
        new Object[] { getPacketType().getName(), Integer.valueOf(getOpcode()), getPacketName() });
  }
}
