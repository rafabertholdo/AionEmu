/*     */ package com.aionemu.gameserver.utils;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import javolution.text.TextBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   public static String toHex(ByteBuffer data) {
/*  37 */     TextBuilder result = TextBuilder.newInstance();
/*  38 */     int counter = 0;
/*     */     
/*  40 */     while (data.hasRemaining()) {
/*     */       
/*  42 */       if (counter % 16 == 0) {
/*  43 */         result.append(String.format("%04X: ", new Object[] { Integer.valueOf(counter) }));
/*     */       }
/*  45 */       int b = data.get() & 0xFF;
/*  46 */       result.append(String.format("%02X ", new Object[] { Integer.valueOf(b) }));
/*     */       
/*  48 */       counter++;
/*  49 */       if (counter % 16 == 0) {
/*     */         
/*  51 */         result.append("  ");
/*  52 */         toText(data, result, 16);
/*  53 */         result.append("\n");
/*     */       } 
/*     */     } 
/*  56 */     int rest = counter % 16;
/*  57 */     if (rest > 0) {
/*     */       
/*  59 */       for (int i = 0; i < 17 - rest; i++)
/*     */       {
/*  61 */         result.append("   ");
/*     */       }
/*  63 */       toText(data, result, rest);
/*     */     } 
/*     */     
/*  66 */     String toString = result.toString();
/*     */     
/*  68 */     TextBuilder.recycle(result);
/*     */     
/*  70 */     return toString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void toText(ByteBuffer data, TextBuilder result, int cnt) {
/*  88 */     int charPos = data.position() - cnt;
/*  89 */     for (int a = 0; a < cnt; a++) {
/*     */       
/*  91 */       int c = data.get(charPos++);
/*  92 */       if (c > 31 && c < 128) {
/*  93 */         result.append((char)c);
/*     */       } else {
/*  95 */         result.append('.');
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertName(String name) {
/* 107 */     if (!name.isEmpty()) {
/* 108 */       return name.substring(0, 1).toUpperCase() + name.toLowerCase().substring(1);
/*     */     }
/* 110 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */