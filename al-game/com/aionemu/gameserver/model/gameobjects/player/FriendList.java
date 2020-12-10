/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_NOTIFY;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_UPDATE;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
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
/*     */ public class FriendList
/*     */   implements Iterable<Friend>
/*     */ {
/*     */   public static final int MAX_FRIENDS = 10;
/*  37 */   private Status status = Status.OFFLINE;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Queue<Friend> friends;
/*     */ 
/*     */ 
/*     */   
/*     */   private Player player;
/*     */ 
/*     */ 
/*     */   
/*     */   public FriendList(Player player) {
/*  50 */     this(player, new ConcurrentLinkedQueue<Friend>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FriendList(Player owner, Collection<Friend> newFriends) {
/*  59 */     this.friends = new ConcurrentLinkedQueue<Friend>(newFriends);
/*  60 */     this.player = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Friend getFriend(int objId) {
/*  70 */     for (Friend friend : this.friends) {
/*     */       
/*  72 */       if (friend.getOid() == objId)
/*  73 */         return friend; 
/*     */     } 
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  84 */     return this.friends.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFriend(Friend friend) {
/*  94 */     this.friends.add(friend);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Friend getFriend(String name) {
/* 104 */     for (Friend friend : this.friends) {
/* 105 */       if (friend.getName().equalsIgnoreCase(name))
/* 106 */         return friend; 
/* 107 */     }  return null;
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
/*     */   public void delFriend(int friendOid) {
/* 119 */     Iterator<Friend> it = iterator();
/* 120 */     while (it.hasNext()) {
/* 121 */       if (((Friend)it.next()).getOid() == friendOid)
/* 122 */         it.remove(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFull() {
/* 127 */     return (getSize() >= 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Status getStatus() {
/* 136 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(Status status) {
/* 146 */     Status previousStatus = this.status;
/* 147 */     this.status = status;
/*     */     
/* 149 */     for (Friend friend : this.friends) {
/*     */       
/* 151 */       if (friend.isOnline()) {
/*     */         
/* 153 */         Player friendPlayer = friend.getPlayer();
/*     */ 
/*     */         
/* 156 */         if (friendPlayer == null) {
/*     */           continue;
/*     */         }
/* 159 */         friendPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_UPDATE(this.player.getObjectId()));
/*     */         
/* 161 */         if (previousStatus == Status.OFFLINE) {
/*     */ 
/*     */           
/* 164 */           friendPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_NOTIFY(0, this.player.getName())); continue;
/*     */         } 
/* 166 */         if (status == Status.OFFLINE)
/*     */         {
/*     */           
/* 169 */           friendPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_NOTIFY(1, this.player.getName()));
/*     */         }
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
/*     */   
/*     */   public Iterator<Friend> iterator() {
/* 183 */     return this.friends.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Status
/*     */   {
/* 191 */     OFFLINE(0),
/*     */ 
/*     */ 
/*     */     
/* 195 */     ONLINE(1),
/*     */ 
/*     */ 
/*     */     
/* 199 */     AWAY(2);
/*     */     
/*     */     int value;
/*     */ 
/*     */     
/*     */     Status(int value) {
/* 205 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIntValue() {
/* 210 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Status getByValue(int value) {
/* 221 */       for (Status stat : values()) {
/* 222 */         if (stat.getIntValue() == value)
/* 223 */           return stat; 
/* 224 */       }  return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\FriendList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */