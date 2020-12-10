/*     */ package com.aionemu.gameserver.world;
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
/*     */ public enum WorldMapType
/*     */ {
/*  22 */   PANDAEMONIUM(120010000),
/*  23 */   MARCHUTAN(120020000),
/*  24 */   ISHALGEN(220010000),
/*  25 */   MORHEIM(220020000),
/*  26 */   ALTGARD(220030000),
/*  27 */   BELUSLAN(220040000),
/*  28 */   BRUSTHONIN(220050000),
/*     */ 
/*     */   
/*  31 */   SANCTUM(110010000),
/*  32 */   KAISINEL(110020000),
/*  33 */   POETA(210010000),
/*  34 */   VERTERON(210030000),
/*  35 */   ELTNEN(210020000),
/*  36 */   HEIRON(210040000),
/*  37 */   THEOMOBOS(210060000),
/*     */ 
/*     */   
/*  40 */   INGGISON(210050000),
/*  41 */   GELKMAROS(220070000),
/*  42 */   SILENTERA_CANYON(600010000),
/*     */ 
/*     */   
/*  45 */   PRISON(510010000),
/*  46 */   DF_PRISON(520010000),
/*     */   
/*  48 */   RESHANTA(400010000),
/*     */ 
/*     */   
/*  51 */   NOCHSANA_TRAINING_CAMP(300030000),
/*  52 */   DARK_POETA(300040000),
/*  53 */   ASTERIA_CHAMBER(300050000),
/*  54 */   SULFUR_TREE_NEST(300060000),
/*  55 */   CHAMBER_OF_ROAH(300070000),
/*  56 */   LEFT_WING_CHAMBER(300080000),
/*  57 */   RIGHT_WING_CHAMBER(300090000),
/*  58 */   STEEL_RAKE(300100000),
/*  59 */   DREDGION(300110000),
/*  60 */   KYSIS_CHAMBER(300120000),
/*  61 */   MIREN_CHAMBER(300130000),
/*  62 */   KROTAN_CHAMBER(300140000),
/*  63 */   AETHEROGENETICS_LAB(310050000),
/*  64 */   INDRATU_FORTRESS(310090000),
/*  65 */   AZOTURAN_FORTRESS(310100000),
/*  66 */   THEOBOMOS_LAB(310110000),
/*  67 */   SKY_TEMPLE_INTERIOR(320050000),
/*  68 */   DRAUPNIR_CAVE(320080000),
/*  69 */   FIRE_TEMPLE(320100000),
/*  70 */   ALQUIMIA(320110000),
/*  71 */   ADMA_STRONGHOLD(320130000),
/*  72 */   TEMPLE_UP(300150000),
/*  73 */   TEMPLE_LOW(300160000),
/*  74 */   CATACOMBS(300170000),
/*  75 */   ELIM(300190000),
/*  76 */   NOVICE(300200000),
/*  77 */   DREDGION_02(300210000),
/*  78 */   DEBRIS_OF_ABYSS(300220000),
/*  79 */   CROMEDE(300230000),
/*  80 */   ABPROD3(320140000),
/*  81 */   DF1B(320060000),
/*  82 */   SPACE(320070000),
/*  83 */   DC1_ARENA(320090000),
/*  84 */   DC1_ARENA_3F(320120000),
/*     */ 
/*     */   
/*  87 */   TEST_BASIC(900020000),
/*  88 */   TEST_SERVER(900030000),
/*  89 */   TEST_GIANTMONSTER(900100000),
/*  90 */   HOUSING_BARRACK(900110000);
/*     */ 
/*     */   
/*     */   private final int worldId;
/*     */ 
/*     */   
/*     */   WorldMapType(int worldId) {
/*  97 */     this.worldId = worldId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 102 */     return this.worldId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldMapType getWorld(int id) {
/* 111 */     for (WorldMapType type : values()) {
/*     */       
/* 113 */       if (type.getId() == id)
/*     */       {
/* 115 */         return type;
/*     */       }
/*     */     } 
/* 118 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\WorldMapType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */