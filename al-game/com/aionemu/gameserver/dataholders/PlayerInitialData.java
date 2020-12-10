/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import gnu.trove.THashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlIDREF;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlRootElement(name = "player_initial_data")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class PlayerInitialData
/*     */ {
/*     */   @XmlElement(name = "player_data")
/*  32 */   private List<PlayerCreationData> dataList = new ArrayList<PlayerCreationData>();
/*     */   
/*     */   @XmlElement(name = "elyos_spawn_location", required = true)
/*     */   private LocationData elyosSpawnLocation;
/*     */   
/*     */   @XmlElement(name = "asmodian_spawn_location", required = true)
/*     */   private LocationData asmodianSpawnLocation;
/*     */   
/*  40 */   private THashMap<PlayerClass, PlayerCreationData> data = new THashMap();
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  44 */     for (PlayerCreationData pt : this.dataList)
/*     */     {
/*  46 */       this.data.put(pt.getRequiredPlayerClass(), pt);
/*     */     }
/*     */     
/*  49 */     this.dataList.clear();
/*  50 */     this.dataList = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerCreationData getPlayerCreationData(PlayerClass cls) {
/*  55 */     return (PlayerCreationData)this.data.get(cls);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  60 */     return this.data.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocationData getSpawnLocation(Race race) {
/*  65 */     switch (race) {
/*     */       
/*     */       case ASMODIANS:
/*  68 */         return this.asmodianSpawnLocation;
/*     */       case ELYOS:
/*  70 */         return this.elyosSpawnLocation;
/*     */     } 
/*  72 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PlayerCreationData
/*     */   {
/*     */     @XmlAttribute(name = "class")
/*     */     private PlayerClass requiredPlayerClass;
/*     */ 
/*     */ 
/*     */     
/*     */     @XmlElement(name = "items")
/*     */     private ItemsType itemsType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PlayerClass getRequiredPlayerClass() {
/*  92 */       return this.requiredPlayerClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ItemType> getItems() {
/*  97 */       return Collections.unmodifiableList(this.itemsType.items);
/*     */     }
/*     */     
/*     */     static class ItemsType {
/*     */       @XmlElement(name = "item")
/* 102 */       public List<PlayerInitialData.PlayerCreationData.ItemType> items = new ArrayList<PlayerInitialData.PlayerCreationData.ItemType>();
/*     */     }
/*     */ 
/*     */     
/*     */     public static class ItemType
/*     */     {
/*     */       @XmlAttribute(name = "id")
/*     */       @XmlIDREF
/*     */       public ItemTemplate template;
/*     */       
/*     */       @XmlAttribute(name = "count")
/*     */       public int count;
/*     */ 
/*     */       
/*     */       public ItemTemplate getTemplate() {
/* 117 */         return this.template;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getCount() {
/* 122 */         return this.count;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public String toString() {
/* 128 */         StringBuilder sb = new StringBuilder();
/* 129 */         sb.append("ItemType");
/* 130 */         sb.append("{template=").append(this.template);
/* 131 */         sb.append(", count=").append(this.count);
/* 132 */         sb.append('}');
/* 133 */         return sb.toString();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LocationData
/*     */   {
/*     */     @XmlAttribute(name = "map_id")
/*     */     private int mapId;
/*     */ 
/*     */ 
/*     */     
/*     */     @XmlAttribute(name = "x")
/*     */     private float x;
/*     */ 
/*     */     
/*     */     @XmlAttribute(name = "y")
/*     */     private float y;
/*     */ 
/*     */     
/*     */     @XmlAttribute(name = "z")
/*     */     private float z;
/*     */ 
/*     */     
/*     */     @XmlAttribute(name = "heading")
/*     */     private byte heading;
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMapId() {
/* 166 */       return this.mapId;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getX() {
/* 171 */       return this.x;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getY() {
/* 176 */       return this.y;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getZ() {
/* 181 */       return this.z;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getHeading() {
/* 186 */       return this.heading;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\PlayerInitialData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */