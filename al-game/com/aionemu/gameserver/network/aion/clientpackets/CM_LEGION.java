/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.legion.Legion;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_INFO;
/*     */ import com.aionemu.gameserver.services.LegionService;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class CM_LEGION
/*     */   extends AionClientPacket
/*     */ {
/*  34 */   private static final Logger log = Logger.getLogger(CM_LEGION.class);
/*     */   
/*     */   private int exOpcode;
/*     */   
/*     */   private int legionarPermission2;
/*     */   
/*     */   private int centurionPermission1;
/*     */   
/*     */   private int centurionPermission2;
/*     */   
/*     */   private int rank;
/*     */   
/*     */   private String legionName;
/*     */   
/*     */   private String charName;
/*     */   
/*     */   private String newNickname;
/*     */   
/*     */   private String announcement;
/*     */   
/*     */   private String newSelfIntro;
/*     */   
/*     */   public CM_LEGION(int opcode) {
/*  57 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  66 */     this.exOpcode = readC();
/*     */     
/*  68 */     switch (this.exOpcode) {
/*     */ 
/*     */       
/*     */       case 0:
/*  72 */         readD();
/*  73 */         this.legionName = readS();
/*     */ 
/*     */       
/*     */       case 1:
/*  77 */         readD();
/*  78 */         this.charName = readS();
/*     */ 
/*     */       
/*     */       case 2:
/*  82 */         readD();
/*  83 */         readH();
/*     */ 
/*     */       
/*     */       case 4:
/*  87 */         readD();
/*  88 */         this.charName = readS();
/*     */ 
/*     */       
/*     */       case 5:
/*  92 */         readD();
/*  93 */         this.charName = readS();
/*     */ 
/*     */       
/*     */       case 6:
/*  97 */         this.rank = readD();
/*  98 */         this.charName = readS();
/*     */ 
/*     */       
/*     */       case 7:
/* 102 */         readD();
/* 103 */         this.charName = readS();
/*     */ 
/*     */       
/*     */       case 8:
/*     */         return;
/*     */       
/*     */       case 9:
/* 110 */         readD();
/* 111 */         this.announcement = readS();
/*     */ 
/*     */       
/*     */       case 10:
/* 115 */         readD();
/* 116 */         this.newSelfIntro = readS();
/*     */ 
/*     */       
/*     */       case 13:
/* 120 */         this.centurionPermission1 = readC();
/* 121 */         this.centurionPermission2 = readC();
/* 122 */         readC();
/* 123 */         this.legionarPermission2 = readC();
/*     */ 
/*     */       
/*     */       case 14:
/* 127 */         readD();
/* 128 */         readH();
/*     */       
/*     */       case 15:
/* 131 */         this.charName = readS();
/* 132 */         this.newNickname = readS();
/*     */     } 
/*     */     
/* 135 */     log.info("Unknown Legion exOpcode? 0x" + Integer.toHexString(this.exOpcode).toUpperCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/* 146 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 147 */     if (activePlayer.isLegionMember()) {
/*     */       
/* 149 */       Legion legion = activePlayer.getLegion();
/*     */       
/* 151 */       if (this.charName != null)
/*     */       {
/* 153 */         LegionService.getInstance().handleCharNameRequest(this.exOpcode, activePlayer, this.charName, this.newNickname, this.rank);
/*     */       }
/*     */       else
/*     */       {
/* 157 */         switch (this.exOpcode) {
/*     */ 
/*     */           
/*     */           case 8:
/* 161 */             sendPacket((AionServerPacket)new SM_LEGION_INFO(legion));
/*     */             return;
/*     */           
/*     */           case 9:
/* 165 */             LegionService.getInstance().handleLegionRequest(this.exOpcode, activePlayer, this.announcement);
/*     */             return;
/*     */           
/*     */           case 10:
/* 169 */             LegionService.getInstance().handleLegionRequest(this.exOpcode, activePlayer, this.newSelfIntro);
/*     */             return;
/*     */           
/*     */           case 13:
/* 173 */             if (activePlayer.getLegionMember().isBrigadeGeneral()) {
/* 174 */               LegionService.getInstance().changePermissions(legion, this.legionarPermission2, this.centurionPermission1, this.centurionPermission2);
/*     */             }
/*     */             return;
/*     */         } 
/*     */         
/* 179 */         LegionService.getInstance().handleLegionRequest(this.exOpcode, activePlayer);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 186 */       switch (this.exOpcode) {
/*     */ 
/*     */         
/*     */         case 0:
/* 190 */           LegionService.getInstance().createLegion(activePlayer, this.legionName);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_LEGION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */