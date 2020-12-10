/*     */ package com.aionemu.gameserver.network.aion;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SimpleModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.items.GodStone;
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
/*     */ import com.aionemu.gameserver.model.items.ManaStone;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Set;
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
/*     */ public abstract class InventoryPacket
/*     */   extends AionServerPacket
/*     */ {
/*     */   protected void writeGeneralInfo(ByteBuffer buf, Item item) {
/*  43 */     writeD(buf, item.getObjectId());
/*  44 */     ItemTemplate itemTemplate = item.getItemTemplate();
/*  45 */     writeD(buf, itemTemplate.getTemplateId());
/*  46 */     writeH(buf, 36);
/*  47 */     writeD(buf, itemTemplate.getNameId());
/*  48 */     writeH(buf, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeMailGeneralInfo(ByteBuffer buf, Item item) {
/*  53 */     writeD(buf, item.getObjectId());
/*  54 */     ItemTemplate itemTemplate = item.getItemTemplate();
/*  55 */     writeD(buf, itemTemplate.getTemplateId());
/*  56 */     writeD(buf, 1);
/*  57 */     writeD(buf, 0);
/*  58 */     writeH(buf, 36);
/*  59 */     writeD(buf, itemTemplate.getNameId());
/*  60 */     writeH(buf, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeGeneralItemInfo(ByteBuffer buf, Item item, boolean privateStore, boolean mail) {
/*  70 */     writeH(buf, 22);
/*  71 */     writeC(buf, 0);
/*  72 */     writeH(buf, item.getItemMask());
/*  73 */     writeQ(buf, item.getItemCount());
/*  74 */     writeD(buf, 0);
/*  75 */     writeD(buf, 0);
/*  76 */     if (!privateStore)
/*  77 */       writeH(buf, 0); 
/*  78 */     writeC(buf, 0);
/*  79 */     if (!mail) {
/*  80 */       writeH(buf, item.getEquipmentSlot());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeStigmaInfo(ByteBuffer buf, Item item) {
/*  85 */     writeH(buf, 325);
/*  86 */     writeC(buf, 6);
/*  87 */     if (item.isEquipped()) {
/*  88 */       writeD(buf, item.getEquipmentSlot());
/*     */     } else {
/*  90 */       writeD(buf, 0);
/*  91 */     }  writeC(buf, 7);
/*  92 */     writeH(buf, 702);
/*  93 */     writeD(buf, 0);
/*  94 */     writeH(buf, 0);
/*  95 */     writeD(buf, 60);
/*     */     
/*  97 */     writeD(buf, 0);
/*  98 */     writeD(buf, 0);
/*     */     
/* 100 */     writeD(buf, 0);
/* 101 */     writeD(buf, 0);
/* 102 */     writeD(buf, 0);
/* 103 */     writeD(buf, 0);
/* 104 */     writeD(buf, 0);
/* 105 */     writeD(buf, 0);
/* 106 */     writeD(buf, 0);
/* 107 */     writeD(buf, 0);
/* 108 */     writeD(buf, 0);
/* 109 */     writeD(buf, 0);
/* 110 */     writeD(buf, 0);
/* 111 */     writeD(buf, 0);
/* 112 */     writeD(buf, 0);
/* 113 */     writeD(buf, 0);
/* 114 */     writeD(buf, 0);
/* 115 */     writeD(buf, 0);
/* 116 */     writeD(buf, 0);
/* 117 */     writeD(buf, 0);
/* 118 */     writeD(buf, 0);
/* 119 */     writeD(buf, 0);
/* 120 */     writeD(buf, 0);
/* 121 */     writeD(buf, 0);
/* 122 */     writeD(buf, 0);
/* 123 */     writeD(buf, 0);
/* 124 */     writeD(buf, 0);
/* 125 */     writeD(buf, 0);
/* 126 */     writeD(buf, 0);
/* 127 */     writeD(buf, 0);
/* 128 */     writeD(buf, 0);
/* 129 */     writeD(buf, 0);
/* 130 */     writeD(buf, 0);
/* 131 */     writeD(buf, 0);
/* 132 */     writeD(buf, 0);
/* 133 */     writeD(buf, 0);
/* 134 */     writeD(buf, 0);
/* 135 */     writeD(buf, 0);
/*     */     
/* 137 */     writeD(buf, 0);
/* 138 */     writeD(buf, 0);
/* 139 */     writeD(buf, 1);
/* 140 */     writeD(buf, 0);
/*     */     
/* 142 */     writeD(buf, 0);
/* 143 */     writeD(buf, 0);
/* 144 */     writeD(buf, 0);
/* 145 */     writeD(buf, 0);
/* 146 */     writeD(buf, 0);
/* 147 */     writeD(buf, 0);
/* 148 */     writeD(buf, 0);
/* 149 */     writeD(buf, 0);
/* 150 */     writeD(buf, 0);
/* 151 */     writeD(buf, 0);
/* 152 */     writeD(buf, 0);
/* 153 */     writeD(buf, 0);
/* 154 */     writeD(buf, 0);
/* 155 */     writeD(buf, 0);
/* 156 */     writeD(buf, 0);
/* 157 */     writeD(buf, 0);
/*     */     
/* 159 */     writeD(buf, 0);
/* 160 */     writeD(buf, 0);
/* 161 */     writeD(buf, 0);
/* 162 */     writeH(buf, 0);
/* 163 */     writeH(buf, 11);
/*     */ 
/*     */     
/* 166 */     writeC(buf, 0);
/* 167 */     writeD(buf, item.getItemTemplate().getTemplateId());
/*     */     
/* 169 */     writeD(buf, 0);
/* 170 */     writeD(buf, 0);
/* 171 */     writeD(buf, 0);
/* 172 */     writeD(buf, 0);
/* 173 */     writeD(buf, 0);
/* 174 */     writeD(buf, 0);
/* 175 */     writeD(buf, 0);
/* 176 */     writeD(buf, 0);
/* 177 */     writeC(buf, 0);
/*     */     
/* 179 */     writeD(buf, 82750);
/*     */     
/* 181 */     writeD(buf, 0);
/* 182 */     writeD(buf, 0);
/* 183 */     writeD(buf, 0);
/* 184 */     writeD(buf, 0);
/* 185 */     writeC(buf, 0);
/*     */     
/* 187 */     writeC(buf, 34);
/* 188 */     writeH(buf, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeKinah(ByteBuffer buf, Item item, boolean isInventory) {
/* 198 */     writeH(buf, 22);
/* 199 */     writeC(buf, 0);
/* 200 */     writeH(buf, item.getItemMask());
/* 201 */     writeQ(buf, item.getItemCount());
/* 202 */     writeD(buf, 0);
/* 203 */     writeD(buf, 0);
/* 204 */     writeH(buf, 0);
/* 205 */     writeC(buf, 0);
/* 206 */     writeH(buf, 255);
/* 207 */     if (isInventory) {
/* 208 */       writeC(buf, 0);
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
/*     */   protected void writeWeaponInfo(ByteBuffer buf, Item item, boolean isInventory) {
/* 220 */     writeWeaponInfo(buf, item, isInventory, false, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeWeaponInfo(ByteBuffer buf, Item item, boolean isInventory, boolean isWeaponSwitch, boolean privateStore, boolean mail) {
/* 231 */     int itemSlotId = item.getEquipmentSlot();
/*     */     
/* 233 */     if (isWeaponSwitch) {
/* 234 */       writeH(buf, 5);
/*     */     } else {
/* 236 */       writeH(buf, 75);
/*     */     } 
/* 238 */     writeC(buf, 6);
/* 239 */     writeD(buf, item.isEquipped() ? itemSlotId : 0);
/*     */     
/* 241 */     if (!isWeaponSwitch) {
/*     */       
/* 243 */       writeC(buf, 1);
/* 244 */       writeD(buf, ((ItemSlot)ItemSlot.getSlotsFor(item.getItemTemplate().getItemSlot()).get(0)).getSlotIdMask());
/* 245 */       writeD(buf, 2);
/* 246 */       writeC(buf, 11);
/* 247 */       writeC(buf, item.isSoulBound() ? 1 : 0);
/* 248 */       writeC(buf, item.getEnchantLevel());
/* 249 */       writeD(buf, item.getItemSkinTemplate().getTemplateId());
/* 250 */       writeC(buf, 0);
/*     */       
/* 252 */       writeItemStones(buf, item);
/*     */       
/* 254 */       GodStone godStone = item.getGodStone();
/* 255 */       writeD(buf, (godStone == null) ? 0 : godStone.getItemId());
/* 256 */       writeC(buf, 0);
/*     */       
/* 258 */       writeD(buf, 0);
/*     */       
/* 260 */       writeD(buf, 0);
/* 261 */       writeC(buf, 0);
/*     */       
/* 263 */       writeH(buf, item.getItemMask());
/* 264 */       writeQ(buf, item.getItemCount());
/* 265 */       writeD(buf, 0);
/* 266 */       writeD(buf, 0);
/* 267 */       if (!privateStore)
/* 268 */         writeH(buf, 0); 
/* 269 */       writeC(buf, 0);
/* 270 */       if (!mail)
/* 271 */         writeH(buf, item.isEquipped() ? 255 : item.getEquipmentSlot()); 
/* 272 */       if (isInventory) {
/* 273 */         writeC(buf, 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeItemStones(ByteBuffer buf, Item item) {
/* 284 */     int count = 0;
/*     */     
/* 286 */     if (item.hasManaStones()) {
/*     */       
/* 288 */       Set<ManaStone> itemStones = item.getItemStones();
/*     */       
/* 290 */       for (ManaStone itemStone : itemStones) {
/*     */         
/* 292 */         if (count == 6) {
/*     */           break;
/*     */         }
/* 295 */         StatModifier modifier = itemStone.getFirstModifier();
/* 296 */         if (modifier != null) {
/*     */           
/* 298 */           count++;
/* 299 */           writeC(buf, modifier.getStat().getItemStoneMask());
/*     */         } 
/*     */       } 
/* 302 */       writeB(buf, new byte[6 - count]);
/* 303 */       count = 0;
/* 304 */       for (ManaStone itemStone : itemStones) {
/*     */         
/* 306 */         if (count == 6) {
/*     */           break;
/*     */         }
/* 309 */         StatModifier modifier = itemStone.getFirstModifier();
/* 310 */         if (modifier != null) {
/*     */           
/* 312 */           count++;
/* 313 */           writeH(buf, ((SimpleModifier)modifier).getValue());
/*     */         } 
/*     */       } 
/* 316 */       writeB(buf, new byte[(6 - count) * 2]);
/*     */     }
/*     */     else {
/*     */       
/* 320 */       writeB(buf, new byte[18]);
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
/*     */   
/*     */   protected void writeArmorInfo(ByteBuffer buf, Item item, boolean isInventory, boolean privateStore, boolean mail) {
/* 333 */     int itemSlotId = item.getEquipmentSlot();
/* 334 */     writeH(buf, 79);
/* 335 */     writeC(buf, 6);
/* 336 */     writeD(buf, item.isEquipped() ? itemSlotId : 0);
/* 337 */     writeC(buf, 2);
/* 338 */     writeD(buf, ((ItemSlot)ItemSlot.getSlotsFor(item.getItemTemplate().getItemSlot()).get(0)).getSlotIdMask());
/* 339 */     writeD(buf, 0);
/* 340 */     writeD(buf, 0);
/* 341 */     writeC(buf, 11);
/* 342 */     writeC(buf, item.isSoulBound() ? 1 : 0);
/* 343 */     writeC(buf, item.getEnchantLevel());
/* 344 */     writeD(buf, item.getItemSkinTemplate().getTemplateId());
/*     */     
/* 346 */     writeC(buf, 0);
/*     */     
/* 348 */     writeItemStones(buf, item);
/*     */     
/* 350 */     writeC(buf, 0);
/* 351 */     writeD(buf, item.getItemColor());
/* 352 */     writeD(buf, 0);
/*     */     
/* 354 */     writeD(buf, 0);
/* 355 */     writeC(buf, 0);
/*     */     
/* 357 */     writeH(buf, item.getItemMask());
/* 358 */     writeQ(buf, item.getItemCount());
/* 359 */     writeD(buf, 0);
/* 360 */     writeD(buf, 0);
/* 361 */     if (!privateStore)
/* 362 */       writeH(buf, 0); 
/* 363 */     writeC(buf, 0);
/* 364 */     if (!mail)
/* 365 */       writeH(buf, item.isEquipped() ? 255 : item.getEquipmentSlot()); 
/* 366 */     if (isInventory)
/* 367 */       writeC(buf, 1); 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\InventoryPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */