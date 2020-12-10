/*     */ package com.aionemu.gameserver.model.items;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.loadingutils.adapters.NpcEquipmentList;
/*     */ import com.aionemu.gameserver.dataholders.loadingutils.adapters.NpcEquippedGearAdapter;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javolution.util.FastList;
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
/*     */ @XmlJavaTypeAdapter(NpcEquippedGearAdapter.class)
/*     */ public class NpcEquippedGear
/*     */ {
/*     */   private Map<ItemSlot, ItemTemplate> items;
/*     */   private short mask;
/*     */   private NpcEquipmentList v;
/*     */   
/*     */   public NpcEquippedGear(NpcEquipmentList v) {
/*  44 */     this.v = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getItemsMask() {
/*  52 */     if (this.items == null) {
/*  53 */       init();
/*     */     }
/*  55 */     return this.mask;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Map.Entry<ItemSlot, ItemTemplate>> getItems() {
/*  60 */     if (this.items == null) {
/*  61 */       init();
/*     */     }
/*  63 */     return this.items.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  72 */     synchronized (this) {
/*     */       
/*  74 */       if (this.items == null) {
/*     */         
/*  76 */         this.items = new TreeMap<ItemSlot, ItemTemplate>();
/*  77 */         for (ItemTemplate item : this.v.items) {
/*     */           
/*  79 */           FastList<ItemSlot> fastList = ItemSlot.getSlotsFor(item.getItemSlot());
/*  80 */           for (ItemSlot itemSlot : fastList) {
/*     */             
/*  82 */             if (this.items.get(itemSlot) == null) {
/*     */               
/*  84 */               this.items.put(itemSlot, item);
/*  85 */               this.mask = (short)(this.mask | itemSlot.getSlotIdMask());
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*  91 */       this.v = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemTemplate getItem(ItemSlot itemSlot) {
/* 102 */     return (this.items != null) ? this.items.get(itemSlot) : null;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\NpcEquippedGear.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */