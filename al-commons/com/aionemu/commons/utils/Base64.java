/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public class Base64
/*     */ {
/*  85 */   private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
/*  86 */   private static final int[] IA = new int[256];
/*     */ 
/*     */   
/*     */   static {
/*  90 */     Arrays.fill(IA, -1);
/*  91 */     for (int i = 0, iS = CA.length; i < iS; i++)
/*  92 */       IA[CA[i]] = i; 
/*  93 */     IA[61] = 0;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] encodeToChar(byte[] sArr, boolean lineSep) {
/* 114 */     int sLen = (sArr != null) ? sArr.length : 0;
/* 115 */     if (sLen == 0) {
/* 116 */       return new char[0];
/*     */     }
/* 118 */     int eLen = sLen / 3 * 3;
/* 119 */     int cCnt = (sLen - 1) / 3 + 1 << 2;
/* 120 */     int dLen = cCnt + (lineSep ? ((cCnt - 1) / 76 << 1) : 0);
/* 121 */     char[] dArr = new char[dLen];
/*     */     
/*     */     int cc;
/* 124 */     for (int s = 0, d = 0; s < eLen; ) {
/*     */ 
/*     */       
/* 127 */       int i = (sArr[s++] & 0xFF) << 16 | (sArr[s++] & 0xFF) << 8 | sArr[s++] & 0xFF;
/*     */ 
/*     */       
/* 130 */       dArr[d++] = CA[i >>> 18 & 0x3F];
/* 131 */       dArr[d++] = CA[i >>> 12 & 0x3F];
/* 132 */       dArr[d++] = CA[i >>> 6 & 0x3F];
/* 133 */       dArr[d++] = CA[i & 0x3F];
/*     */ 
/*     */       
/* 136 */       if (lineSep && ++cc == 19 && d < dLen - 2) {
/*     */         
/* 138 */         dArr[d++] = '\r';
/* 139 */         dArr[d++] = '\n';
/* 140 */         cc = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 145 */     int left = sLen - eLen;
/* 146 */     if (left > 0) {
/*     */ 
/*     */       
/* 149 */       int i = (sArr[eLen] & 0xFF) << 10 | ((left == 2) ? ((sArr[sLen - 1] & 0xFF) << 2) : 0);
/*     */ 
/*     */       
/* 152 */       dArr[dLen - 4] = CA[i >> 12];
/* 153 */       dArr[dLen - 3] = CA[i >>> 6 & 0x3F];
/* 154 */       dArr[dLen - 2] = (left == 2) ? CA[i & 0x3F] : '=';
/* 155 */       dArr[dLen - 1] = '=';
/*     */     } 
/* 157 */     return dArr;
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
/*     */   public static byte[] decode(char[] sArr) {
/* 172 */     int sLen = (sArr != null) ? sArr.length : 0;
/* 173 */     if (sLen == 0) {
/* 174 */       return new byte[0];
/*     */     }
/*     */ 
/*     */     
/* 178 */     int sepCnt = 0;
/* 179 */     for (int i = 0; i < sLen; i++) {
/*     */       
/* 181 */       if (IA[sArr[i]] < 0) {
/* 182 */         sepCnt++;
/*     */       }
/*     */     } 
/* 185 */     if ((sLen - sepCnt) % 4 != 0) {
/* 186 */       return null;
/*     */     }
/* 188 */     int pad = 0;
/* 189 */     for (int j = sLen; j > 1 && IA[sArr[--j]] <= 0;) {
/* 190 */       if (sArr[j] == '=')
/* 191 */         pad++; 
/*     */     } 
/* 193 */     int len = ((sLen - sepCnt) * 6 >> 3) - pad;
/*     */     
/* 195 */     byte[] dArr = new byte[len];
/*     */     
/* 197 */     for (int s = 0, d = 0; d < len; ) {
/*     */ 
/*     */       
/* 200 */       int k = 0;
/* 201 */       for (int m = 0; m < 4; m++) {
/*     */         
/* 203 */         int c = IA[sArr[s++]];
/* 204 */         if (c >= 0) {
/* 205 */           k |= c << 18 - m * 6;
/*     */         } else {
/* 207 */           m--;
/*     */         } 
/*     */       } 
/* 210 */       dArr[d++] = (byte)(k >> 16);
/* 211 */       if (d < len) {
/*     */         
/* 213 */         dArr[d++] = (byte)(k >> 8);
/* 214 */         if (d < len)
/* 215 */           dArr[d++] = (byte)k; 
/*     */       } 
/*     */     } 
/* 218 */     return dArr;
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
/*     */   public static byte[] decodeFast(char[] sArr) {
/* 236 */     int sLen = sArr.length;
/* 237 */     if (sLen == 0) {
/* 238 */       return new byte[0];
/*     */     }
/* 240 */     int sIx = 0, eIx = sLen - 1;
/*     */ 
/*     */     
/* 243 */     while (sIx < eIx && IA[sArr[sIx]] < 0) {
/* 244 */       sIx++;
/*     */     }
/*     */     
/* 247 */     while (eIx > 0 && IA[sArr[eIx]] < 0) {
/* 248 */       eIx--;
/*     */     }
/*     */     
/* 251 */     int pad = (sArr[eIx] == '=') ? ((sArr[eIx - 1] == '=') ? 2 : 1) : 0;
/* 252 */     int cCnt = eIx - sIx + 1;
/* 253 */     int sepCnt = (sLen > 76) ? (((sArr[76] == '\r') ? (cCnt / 78) : 0) << 1) : 0;
/*     */     
/* 255 */     int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
/* 256 */     byte[] dArr = new byte[len];
/*     */ 
/*     */     
/* 259 */     int d = 0; int cc, eLen;
/* 260 */     for (cc = 0, eLen = len / 3 * 3; d < eLen; ) {
/*     */ 
/*     */       
/* 263 */       int i = IA[sArr[sIx++]] << 18 | IA[sArr[sIx++]] << 12 | IA[sArr[sIx++]] << 6 | IA[sArr[sIx++]];
/*     */ 
/*     */       
/* 266 */       dArr[d++] = (byte)(i >> 16);
/* 267 */       dArr[d++] = (byte)(i >> 8);
/* 268 */       dArr[d++] = (byte)i;
/*     */ 
/*     */       
/* 271 */       if (sepCnt > 0 && ++cc == 19) {
/*     */         
/* 273 */         sIx += 2;
/* 274 */         cc = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 278 */     if (d < len) {
/*     */ 
/*     */       
/* 281 */       int i = 0;
/* 282 */       for (int j = 0; sIx <= eIx - pad; j++) {
/* 283 */         i |= IA[sArr[sIx++]] << 18 - j * 6;
/*     */       }
/* 285 */       for (int r = 16; d < len; r -= 8) {
/* 286 */         dArr[d++] = (byte)(i >> r);
/*     */       }
/*     */     } 
/* 289 */     return dArr;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encodeToByte(byte[] sArr, boolean lineSep) {
/* 310 */     int sLen = (sArr != null) ? sArr.length : 0;
/* 311 */     if (sLen == 0) {
/* 312 */       return new byte[0];
/*     */     }
/* 314 */     int eLen = sLen / 3 * 3;
/* 315 */     int cCnt = (sLen - 1) / 3 + 1 << 2;
/* 316 */     int dLen = cCnt + (lineSep ? ((cCnt - 1) / 76 << 1) : 0);
/* 317 */     byte[] dArr = new byte[dLen];
/*     */     
/*     */     int cc;
/* 320 */     for (int s = 0, d = 0; s < eLen; ) {
/*     */ 
/*     */       
/* 323 */       int i = (sArr[s++] & 0xFF) << 16 | (sArr[s++] & 0xFF) << 8 | sArr[s++] & 0xFF;
/*     */ 
/*     */       
/* 326 */       dArr[d++] = (byte)CA[i >>> 18 & 0x3F];
/* 327 */       dArr[d++] = (byte)CA[i >>> 12 & 0x3F];
/* 328 */       dArr[d++] = (byte)CA[i >>> 6 & 0x3F];
/* 329 */       dArr[d++] = (byte)CA[i & 0x3F];
/*     */ 
/*     */       
/* 332 */       if (lineSep && ++cc == 19 && d < dLen - 2) {
/*     */         
/* 334 */         dArr[d++] = 13;
/* 335 */         dArr[d++] = 10;
/* 336 */         cc = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 341 */     int left = sLen - eLen;
/* 342 */     if (left > 0) {
/*     */ 
/*     */       
/* 345 */       int i = (sArr[eLen] & 0xFF) << 10 | ((left == 2) ? ((sArr[sLen - 1] & 0xFF) << 2) : 0);
/*     */ 
/*     */       
/* 348 */       dArr[dLen - 4] = (byte)CA[i >> 12];
/* 349 */       dArr[dLen - 3] = (byte)CA[i >>> 6 & 0x3F];
/* 350 */       dArr[dLen - 2] = (left == 2) ? (byte)CA[i & 0x3F] : 61;
/* 351 */       dArr[dLen - 1] = 61;
/*     */     } 
/* 353 */     return dArr;
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
/*     */   public static byte[] decode(byte[] sArr) {
/* 368 */     int sLen = sArr.length;
/*     */ 
/*     */ 
/*     */     
/* 372 */     int sepCnt = 0;
/* 373 */     for (int i = 0; i < sLen; i++) {
/*     */       
/* 375 */       if (IA[sArr[i] & 0xFF] < 0) {
/* 376 */         sepCnt++;
/*     */       }
/*     */     } 
/* 379 */     if ((sLen - sepCnt) % 4 != 0) {
/* 380 */       return null;
/*     */     }
/* 382 */     int pad = 0;
/* 383 */     for (int j = sLen; j > 1 && IA[sArr[--j] & 0xFF] <= 0;) {
/* 384 */       if (sArr[j] == 61)
/* 385 */         pad++; 
/*     */     } 
/* 387 */     int len = ((sLen - sepCnt) * 6 >> 3) - pad;
/*     */     
/* 389 */     byte[] dArr = new byte[len];
/*     */     
/* 391 */     for (int s = 0, d = 0; d < len; ) {
/*     */ 
/*     */       
/* 394 */       int k = 0;
/* 395 */       for (int m = 0; m < 4; m++) {
/*     */         
/* 397 */         int c = IA[sArr[s++] & 0xFF];
/* 398 */         if (c >= 0) {
/* 399 */           k |= c << 18 - m * 6;
/*     */         } else {
/* 401 */           m--;
/*     */         } 
/*     */       } 
/*     */       
/* 405 */       dArr[d++] = (byte)(k >> 16);
/* 406 */       if (d < len) {
/*     */         
/* 408 */         dArr[d++] = (byte)(k >> 8);
/* 409 */         if (d < len) {
/* 410 */           dArr[d++] = (byte)k;
/*     */         }
/*     */       } 
/*     */     } 
/* 414 */     return dArr;
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
/*     */   public static byte[] decodeFast(byte[] sArr) {
/* 432 */     int sLen = sArr.length;
/* 433 */     if (sLen == 0) {
/* 434 */       return new byte[0];
/*     */     }
/* 436 */     int sIx = 0, eIx = sLen - 1;
/*     */ 
/*     */     
/* 439 */     while (sIx < eIx && IA[sArr[sIx] & 0xFF] < 0) {
/* 440 */       sIx++;
/*     */     }
/*     */     
/* 443 */     while (eIx > 0 && IA[sArr[eIx] & 0xFF] < 0) {
/* 444 */       eIx--;
/*     */     }
/*     */     
/* 447 */     int pad = (sArr[eIx] == 61) ? ((sArr[eIx - 1] == 61) ? 2 : 1) : 0;
/* 448 */     int cCnt = eIx - sIx + 1;
/* 449 */     int sepCnt = (sLen > 76) ? (((sArr[76] == 13) ? (cCnt / 78) : 0) << 1) : 0;
/*     */     
/* 451 */     int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
/* 452 */     byte[] dArr = new byte[len];
/*     */ 
/*     */     
/* 455 */     int d = 0; int cc, eLen;
/* 456 */     for (cc = 0, eLen = len / 3 * 3; d < eLen; ) {
/*     */ 
/*     */       
/* 459 */       int i = IA[sArr[sIx++]] << 18 | IA[sArr[sIx++]] << 12 | IA[sArr[sIx++]] << 6 | IA[sArr[sIx++]];
/*     */ 
/*     */       
/* 462 */       dArr[d++] = (byte)(i >> 16);
/* 463 */       dArr[d++] = (byte)(i >> 8);
/* 464 */       dArr[d++] = (byte)i;
/*     */ 
/*     */       
/* 467 */       if (sepCnt > 0 && ++cc == 19) {
/*     */         
/* 469 */         sIx += 2;
/* 470 */         cc = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 474 */     if (d < len) {
/*     */ 
/*     */       
/* 477 */       int i = 0;
/* 478 */       for (int j = 0; sIx <= eIx - pad; j++) {
/* 479 */         i |= IA[sArr[sIx++]] << 18 - j * 6;
/*     */       }
/* 481 */       for (int r = 16; d < len; r -= 8) {
/* 482 */         dArr[d++] = (byte)(i >> r);
/*     */       }
/*     */     } 
/* 485 */     return dArr;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeToString(byte[] sArr, boolean lineSep) {
/* 506 */     return new String(encodeToChar(sArr, lineSep));
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
/*     */   public static byte[] decode(String str) {
/* 523 */     int sLen = (str != null) ? str.length() : 0;
/* 524 */     if (sLen == 0) {
/* 525 */       return new byte[0];
/*     */     }
/*     */ 
/*     */     
/* 529 */     int sepCnt = 0;
/* 530 */     for (int i = 0; i < sLen; i++) {
/*     */       
/* 532 */       if (IA[str.charAt(i)] < 0) {
/* 533 */         sepCnt++;
/*     */       }
/*     */     } 
/* 536 */     if ((sLen - sepCnt) % 4 != 0) {
/* 537 */       return null;
/*     */     }
/*     */     
/* 540 */     int pad = 0;
/* 541 */     for (int j = sLen; j > 1 && IA[str.charAt(--j)] <= 0;) {
/* 542 */       if (str.charAt(j) == '=')
/* 543 */         pad++; 
/*     */     } 
/* 545 */     int len = ((sLen - sepCnt) * 6 >> 3) - pad;
/*     */     
/* 547 */     byte[] dArr = new byte[len];
/*     */     
/* 549 */     for (int s = 0, d = 0; d < len; ) {
/*     */ 
/*     */       
/* 552 */       int k = 0;
/* 553 */       for (int m = 0; m < 4; m++) {
/*     */         
/* 555 */         int c = IA[str.charAt(s++)];
/* 556 */         if (c >= 0) {
/* 557 */           k |= c << 18 - m * 6;
/*     */         } else {
/* 559 */           m--;
/*     */         } 
/*     */       } 
/* 562 */       dArr[d++] = (byte)(k >> 16);
/* 563 */       if (d < len) {
/*     */         
/* 565 */         dArr[d++] = (byte)(k >> 8);
/* 566 */         if (d < len)
/* 567 */           dArr[d++] = (byte)k; 
/*     */       } 
/*     */     } 
/* 570 */     return dArr;
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
/*     */   public static byte[] decodeFast(String s) {
/* 588 */     int sLen = s.length();
/* 589 */     if (sLen == 0) {
/* 590 */       return new byte[0];
/*     */     }
/* 592 */     int sIx = 0, eIx = sLen - 1;
/*     */ 
/*     */     
/* 595 */     while (sIx < eIx && IA[s.charAt(sIx) & 0xFF] < 0) {
/* 596 */       sIx++;
/*     */     }
/*     */     
/* 599 */     while (eIx > 0 && IA[s.charAt(eIx) & 0xFF] < 0) {
/* 600 */       eIx--;
/*     */     }
/*     */     
/* 603 */     int pad = (s.charAt(eIx) == '=') ? ((s.charAt(eIx - 1) == '=') ? 2 : 1) : 0;
/* 604 */     int cCnt = eIx - sIx + 1;
/* 605 */     int sepCnt = (sLen > 76) ? (((s.charAt(76) == '\r') ? (cCnt / 78) : 0) << 1) : 0;
/*     */     
/* 607 */     int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
/* 608 */     byte[] dArr = new byte[len];
/*     */ 
/*     */     
/* 611 */     int d = 0; int cc, eLen;
/* 612 */     for (cc = 0, eLen = len / 3 * 3; d < eLen; ) {
/*     */ 
/*     */       
/* 615 */       int i = IA[s.charAt(sIx++)] << 18 | IA[s.charAt(sIx++)] << 12 | IA[s.charAt(sIx++)] << 6 | IA[s.charAt(sIx++)];
/*     */ 
/*     */ 
/*     */       
/* 619 */       dArr[d++] = (byte)(i >> 16);
/* 620 */       dArr[d++] = (byte)(i >> 8);
/* 621 */       dArr[d++] = (byte)i;
/*     */ 
/*     */       
/* 624 */       if (sepCnt > 0 && ++cc == 19) {
/*     */         
/* 626 */         sIx += 2;
/* 627 */         cc = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 631 */     if (d < len) {
/*     */ 
/*     */       
/* 634 */       int i = 0;
/* 635 */       for (int j = 0; sIx <= eIx - pad; j++) {
/* 636 */         i |= IA[s.charAt(sIx++)] << 18 - j * 6;
/*     */       }
/* 638 */       for (int r = 16; d < len; r -= 8) {
/* 639 */         dArr[d++] = (byte)(i >> r);
/*     */       }
/*     */     } 
/* 642 */     return dArr;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */