package com.aionemu.gameserver.utils;

import java.nio.ByteBuffer;
import javolution.text.TextBuilder;

public class Util {
  public static String toHex(ByteBuffer data) {
    TextBuilder result = TextBuilder.newInstance();
    int counter = 0;

    while (data.hasRemaining()) {

      if (counter % 16 == 0) {
        result.append(String.format("%04X: ", new Object[] { Integer.valueOf(counter) }));
      }
      int b = data.get() & 0xFF;
      result.append(String.format("%02X ", new Object[] { Integer.valueOf(b) }));

      counter++;
      if (counter % 16 == 0) {

        result.append("  ");
        toText(data, result, 16);
        result.append("\n");
      }
    }
    int rest = counter % 16;
    if (rest > 0) {

      for (int i = 0; i < 17 - rest; i++) {
        result.append("   ");
      }
      toText(data, result, rest);
    }

    String toString = result.toString();

    TextBuilder.recycle(result);

    return toString;
  }

  private static void toText(ByteBuffer data, TextBuilder result, int cnt) {
    int charPos = data.position() - cnt;
    for (int a = 0; a < cnt; a++) {

      int c = data.get(charPos++);
      if (c > 31 && c < 128) {
        result.append((char) c);
      } else {
        result.append('.');
      }
    }
  }

  public static String convertName(String name) {
    if (!name.isEmpty()) {
      return name.substring(0, 1).toUpperCase() + name.toLowerCase().substring(1);
    }
    return "";
  }
}
