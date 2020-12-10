/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Letter;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import javolution.util.FastMap;
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
/*     */ public class Mailbox
/*     */ {
/*  35 */   private Map<Integer, Letter> mails = (Map<Integer, Letter>)new FastMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putLetterToMailbox(Letter letter) {
/*  43 */     this.mails.put(Integer.valueOf(letter.getObjectId()), letter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Letter> getLetters() {
/*  53 */     SortedSet<Letter> letters = new TreeSet<Letter>(new Comparator<Letter>()
/*     */         {
/*     */           
/*     */           public int compare(Letter o1, Letter o2)
/*     */           {
/*  58 */             if (o1.getTimeStamp().getTime() > o2.getTimeStamp().getTime())
/*  59 */               return 1; 
/*  60 */             if (o1.getTimeStamp().getTime() < o2.getTimeStamp().getTime()) {
/*  61 */               return -1;
/*     */             }
/*  63 */             return (o1.getObjectId() > o2.getObjectId()) ? 1 : -1;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  68 */     for (Letter letter : this.mails.values())
/*     */     {
/*  70 */       letters.add(letter);
/*     */     }
/*  72 */     return letters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Letter getLetterFromMailbox(int letterObjId) {
/*  83 */     return this.mails.get(Integer.valueOf(letterObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean haveUnread() {
/*  93 */     for (Letter letter : this.mails.values()) {
/*     */       
/*  95 */       if (letter.isUnread()) {
/*  96 */         return true;
/*     */       }
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFreeSlots() {
/* 108 */     return 65536 - this.mails.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean haveFreeSlots() {
/* 117 */     return (this.mails.size() < 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLetter(int letterId) {
/* 125 */     this.mails.remove(Integer.valueOf(letterId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 135 */     return this.mails.size();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Mailbox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */