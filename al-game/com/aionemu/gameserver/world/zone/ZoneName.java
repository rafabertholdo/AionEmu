/*     */ package com.aionemu.gameserver.world.zone;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlEnum;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlType(name = "ZoneName")
/*     */ @XmlEnum
/*     */ public enum ZoneName
/*     */ {
/*  31 */   DEFORESTED_AREA,
/*  32 */   WORG_RUN,
/*  33 */   DAMINU_FOREST,
/*  34 */   MELPONEHS_CAMPSITE,
/*  35 */   AGERS_FARM,
/*  36 */   AKARIOS_VILLAGE,
/*  37 */   TIMOLIA_MINE,
/*  38 */   KABARAH_STRIP_MINE,
/*  39 */   FEIRAS_DOCK,
/*  40 */   AKARIOS_PLAINS,
/*  41 */   KALESS_FARM,
/*  42 */   CLIONA_LAKE,
/*  43 */   NYMPHS_POND,
/*  44 */   AGARIC_SPORE_ROAD,
/*  45 */   ITEMUSE_Q1006,
/*  46 */   Q1123,
/*     */   
/*  48 */   DUBARO_VINE_CANYON,
/*  49 */   ANTUROON_SENTRY_POST,
/*  50 */   SAP_FARM,
/*  51 */   ISHALGEN_PRISON_CAMP,
/*  52 */   ODELLA_PLANTATION,
/*  53 */   ALDELLE_HILL,
/*  54 */   MUNIHELE_FOREST,
/*  55 */   NEGIS_DOCK,
/*  56 */   THE_FORSAKEN_HOLLOW,
/*  57 */   ANTUROON_COAST,
/*  58 */   ISHALGEN_SENTRY_POST,
/*  59 */   LAKE_TUNAPRE,
/*  60 */   ALDELLE_VILLAGE,
/*  61 */   EYVINDR_ANCHORAGE,
/*  62 */   KARDS_CAMPSITE,
/*  63 */   ALDELLE_BASIN,
/*  64 */   GUHEITUNS_TENT,
/*  65 */   ANTUROON_CROSSING,
/*  66 */   DARU_SPRING,
/*  67 */   HATATAS_HIDEOUT,
/*     */   
/*  69 */   COLISEUM,
/*  70 */   OUTER_DOCK,
/*  71 */   DIONYSIA_TAVERN,
/*     */   
/*  73 */   LIBRARY_OF_THE_SAGES,
/*  74 */   SANCTUM_GATE,
/*  75 */   LYCEUM,
/*  76 */   HALL_OF_PROSPERITY,
/*  77 */   DIVINE_ROAD,
/*  78 */   PROTECTORS_HALL,
/*  79 */   AIRSHIP_DOCK,
/*  80 */   ELYOS_SQUARE,
/*  81 */   STR_SZ_LC1_SUB_H,
/*  82 */   ARTISANS_HALL,
/*  83 */   SKY_GARDEN,
/*  84 */   TEMPEST_SHIPYARD,
/*  85 */   SKY_CANAL,
/*  86 */   EXALTED_PATH,
/*     */   
/*  88 */   ZUMION_CANYON,
/*  89 */   VERTERON_CITADEL,
/*  90 */   CANTAS_COAST,
/*  91 */   GEOLUS_CAMPSITE,
/*  92 */   TURSIN_OUTPOST,
/*  93 */   TURSIN_TOTEM_POLE,
/*  94 */   KRAKAS_DEN,
/*  95 */   BELBUAS_FARM,
/*  96 */   ODIUM_REFINING_CAULDRON,
/*  97 */   TALUNS_NEST,
/*  98 */   ALTAMIA_RIDGE,
/*  99 */   TURSIN_OUTPOST_ENTRANCE,
/* 100 */   FRILLNECK_WOODS,
/* 101 */   PILGRIMS_RESPITE,
/* 102 */   ALTAR_OF_THE_CRIMSON_DRAGON,
/* 103 */   MIRAJUS_HOLY_GROUND,
/* 104 */   NORTHERN_TOLBAS_FOREST,
/* 105 */   ZUMION_RUINS,
/* 106 */   TOLBAS_VILLAGE,
/* 107 */   ARDUS_SHRINE,
/* 108 */   ROAD_TO_ELTNEN,
/* 109 */   DUKAKI_SETTLEMENT,
/* 110 */   CONTAMINATED_SWAMP,
/* 111 */   DUKAKI_MINE,
/* 112 */   ELLUN_RIVER_MOUTH,
/* 113 */   VERTERON_OBSERVATORY,
/* 114 */   MYSTERIOUS_SHIPWRECK,
/* 115 */   VERTERON_SWAMP,
/* 116 */   SOUTHERN_TOLBAS_FOREST,
/* 117 */   TURSIN_GARRISON,
/* 118 */   Q1012,
/*     */   
/* 120 */   PUTRID_MIRE,
/* 121 */   DRAKE_HABITAT,
/* 122 */   ELTNEN_OBSERVATORY,
/* 123 */   ERACUS_TEMPLE_CAVERN,
/* 124 */   EASTERN_ERACUS_DESERT,
/* 125 */   MYSTIC_SPRING_OF_LAQUEPIN,
/* 126 */   KAIDAN_MINE,
/* 127 */   MABANGTAHS_HIDEOUT,
/* 128 */   PRELLUS_WASTELAND,
/* 129 */   REFUGEE_CAMP,
/* 130 */   KURIULLU_PASS,
/* 131 */   KYOLA_TEMPLE,
/* 132 */   MANDURI_VILLAGE,
/* 133 */   GEROCH_CALDERA,
/* 134 */   TIGRIC_VILLAGE,
/* 135 */   ELTNEN_FORTRESS,
/* 136 */   MYSTIC_SPRING_OF_ANATHE,
/* 137 */   SATALOCAS_TOMB,
/* 138 */   MANDURI_FOREST,
/* 139 */   MANDURI_STORES,
/* 140 */   ROAD_TO_HEIRON,
/* 141 */   LEPHARIST_CITADEL,
/* 142 */   RUBEREIN_WASTES,
/* 143 */   ERACUS_TEMPLE_CAVERN_ENTRANCE,
/* 144 */   LANKUSIS_PLATEAU,
/* 145 */   OUTPOST_RUINS,
/* 146 */   KURIULLU_MOUNTAIN_OUTPOST,
/* 147 */   WESTERN_ERACUS_DESERT,
/* 148 */   KLAW_NEST,
/* 149 */   GOLDEN_BOUGH_GARRISON,
/* 150 */   KYOLA_TEMPLE_ENTRANCE,
/* 151 */   KAIDAN_HEADQUARTERS,
/* 152 */   CALDERA_OUTPOST,
/* 153 */   DESERT_SCOUT_CAMP,
/* 154 */   ELTNEN_FORTRESS_ENTRANCE,
/* 155 */   AGAIRON_VILLAGE,
/* 156 */   MYSTIC_SPRING_OF_AGAIRON,
/* 157 */   KURIULLU_MOUNTAIN,
/* 158 */   ROAD_TO_VERTERON,
/* 159 */   EIRON_DESERT,
/* 160 */   KLAW_DEN,
/*     */   
/* 162 */   INDRATU_BARRACKS,
/* 163 */   MANOR_CEMETERY,
/* 164 */   NUTE_WARRENS,
/* 165 */   BERONES_RUINED_HOUSE,
/* 166 */   NEW_HEIRON_GATE,
/* 167 */   POISON_EXTRACTION_LAB,
/* 168 */   PRETOR_EXPERIMENT_LAB,
/* 169 */   KISHAR_VILLAGE,
/* 170 */   PATEMA_GEYSER,
/* 171 */   JEIAPARAN_VILLAGE,
/* 172 */   KISHAR_OBSERVATION_POST,
/* 173 */   STENON_BAY,
/* 174 */   TIGRIC_VILLAGE_HEIRON,
/* 175 */   DRAGONS_BLOOD_CANYON,
/* 176 */   ORTON_FARM,
/* 177 */   DRAKE_FARM,
/* 178 */   CURSED_ANCIENT_TEMPLE,
/* 179 */   PATEMA_RUINS,
/* 180 */   LEPHARIST_RESEARCH_CENTER,
/* 181 */   PUITONEN_BOGS,
/* 182 */   RESEARCH_CENTER_OVERWATCH,
/* 183 */   HEIRON_PASS,
/* 184 */   KLAWTANS_WALLOW,
/* 185 */   GLOOMY_MIRE,
/* 186 */   ISLAND_OF_ETERNITY,
/* 187 */   CONTESTED_EXPANSE,
/* 188 */   MEDEUS_ALTAR,
/* 189 */   BLACK_TEARS_SWAMP,
/* 190 */   DEATHSONG_FOREST,
/* 191 */   VAIZELS_PEAK,
/* 192 */   DESTROYED_GUARD_TOWER,
/* 193 */   ROTRON_EXPERIMENT_LAB,
/* 194 */   ARBOLUS_HAVEN,
/* 195 */   MEDEUS_MANOR_WEST,
/* 196 */   MEDEUS_MANOR_EAST,
/* 197 */   ROAD_TO_ELTNEN_HEIRON,
/* 198 */   NOLANTIS_RUINS,
/* 199 */   MANDURI_VALLEY,
/* 200 */   THE_STORM_CIRCLE,
/* 201 */   KAKUNAS_NEST,
/* 202 */   CHANGARNERKS_CAMPSITE,
/* 203 */   MUDTHORN_EXPERIMENT_LAB,
/* 204 */   DECAYED_GROVE,
/* 205 */   RUINED_DRAKE_TEMPLE,
/* 206 */   POYA_JUNGLE,
/* 207 */   HEIRONOPOLIS,
/* 208 */   MONITOR_FARM,
/* 209 */   HEIRON_OBSERVATORY,
/* 210 */   KUNPAPA_OUTPOST,
/* 211 */   SENEAS_CAMPSITE,
/* 212 */   GRAY_FOG_MARSHES,
/*     */ 
/*     */   
/* 215 */   MAHINDEL_SWAMP_220030000,
/* 216 */   MANIRS_CAMPSITE_220030000,
/* 217 */   GERGER_VILLAGE_220030000,
/* 218 */   IDUNS_LAKE_220030000,
/* 219 */   ALTGARD_FORTRESS_DUNGEON_220030000,
/* 220 */   ALTGARD_OBSERVATORY_220030000,
/* 221 */   GRAVE_ROBBERS_DEN_220030000,
/* 222 */   ALTGARD_ICE_LAKE_220030000,
/* 223 */   MUMU_FARMLAND_220030000,
/* 224 */   GRIBADE_CANYON_220030000,
/* 225 */   GRIBADE_CRATER_LAKE_220030000,
/* 226 */   MANIRS_DOCK_220030000,
/* 227 */   GUNMARSONS_CAMPSITE_220030000,
/* 228 */   BLACK_CLAW_VILLAGE_220030000,
/* 229 */   BASFELT_HUNTING_GROUND_220030000,
/* 230 */   BASFELT_VILLAGE_220030000,
/* 231 */   MOSLAN_FOREST_220030000,
/* 232 */   LEPHARIST_ENCAMPMENT_220030000,
/* 233 */   MUMU_VILLAGE_220030000,
/* 234 */   HEART_OF_IMPETUSIUM_220030000,
/* 235 */   ROAD_TO_MORHEIM_220030000,
/* 236 */   IMPETUSIUM_220030000,
/* 237 */   TRADERS_BERTH_220030000,
/* 238 */   ALTGARD_FORTRESS_DUNGEON_ENTRANCE_220030000,
/* 239 */   MOSLAN_RELICS_220030000,
/* 240 */   BLACK_CLAW_OUTPOST_220030000,
/* 241 */   MOSLAN_CROSSROAD_220030000,
/* 242 */   ZEMURRUS_GRAVE_220030000,
/* 243 */   MOSBEAR_HABITAT_220030000,
/* 244 */   ALTAR_OF_TIAMAT_220030000,
/* 245 */   KAIBECHS_CAMPSITE_220030000,
/* 246 */   CALDERON_HILL_220030000,
/* 247 */   KARLS_CAMPSITE_220030000,
/* 248 */   ALTGARD_EAST_GATE_220030000,
/* 249 */   SEGGURHEIM_220030000,
/* 250 */   ALTGARD_FORTRESS_220030000,
/* 251 */   Q2016,
/*     */ 
/*     */   
/* 254 */   EXECUTION_GROUND_OF_DELTRAS_220020000,
/* 255 */   MIST_MANE_VILLAGE_220020000,
/* 256 */   SALINTUS_OBSERVATION_POST_220020000,
/* 257 */   ROAD_TO_BELUSLAN_220020000,
/* 258 */   AIRSHIP_CRASH_SITE_220020000,
/* 259 */   MIST_MANE_TRAINING_GROUND_220020000,
/* 260 */   CHAIKATAS_HIDEOUT_220020000,
/* 261 */   WONSHIKUTZS_LABORATORY_220020000,
/* 262 */   LAVA_CAVE_OF_TARAN_220020000,
/* 263 */   PATAMOR_THICKET_220020000,
/* 264 */   MT_MUSPHEL_220020000,
/* 265 */   SKY_TEMPLE_OF_ARKANIS_220020000,
/* 266 */   HALABANA_HOT_SPRINGS_220020000,
/* 267 */   MUNMUN_GINSENG_PLANTATION_220020000,
/* 268 */   ROAD_TO_ALTGARD_220020000,
/* 269 */   SKY_BRIDGE_VALLEY_220020000,
/* 270 */   HILL_OF_BELEMU_220020000,
/* 271 */   FIRE_TEMPLE_220020000,
/* 272 */   MORHEIM_OBSERVATORY_220020000,
/* 273 */   SILVER_MANE_VILLAGE_220020000,
/* 274 */   SPRIGG_HABITAT_220020000,
/* 275 */   SALINTUS_RISE_220020000,
/* 276 */   KLAW_HABITAT_220020000,
/* 277 */   ALSIG_VILLAGE_220020000,
/* 278 */   SKY_TEMPLE_ENTRANCE_220020000,
/* 279 */   SALINTUS_DESERT_220020000,
/* 280 */   NUNU_VILLAGE_220020000,
/* 281 */   MIST_MANE_VILLAGE_ENTRANCE_220020000,
/* 282 */   PATAMOR_RIDGE_PATH_220020000,
/* 283 */   KARHELS_AETHERIC_FIELD_220020000,
/* 284 */   KELLANS_CABIN_220020000,
/* 285 */   MT_MUSPHEL_ENTRANCE_220020000,
/* 286 */   ALSIG_BASIN_220020000,
/* 287 */   FORTRESS_OF_SORROW_220020000,
/* 288 */   MORHEIM_SNOW_FIELD_220020000,
/* 289 */   KASAKAS_WOOD_CAVE_220020000,
/* 290 */   GIANT_ROCK_WATERFALL_220020000,
/* 291 */   CRYSTAL_RISE_220020000,
/* 292 */   ALTAR_OF_THE_BLACK_DRAGON_220020000,
/* 293 */   ALTAR_OF_TRIAL_220020000,
/* 294 */   OCTANUS_LAIR_220020000,
/* 295 */   ALSIG_CROSSROAD_220020000,
/* 296 */   LEPHARIST_CITADEL_220020000,
/* 297 */   FALL_ROAD_220020000,
/* 298 */   KENTARI_VILLAGE_220020000,
/* 299 */   ICE_CLAW_VILLAGE_220020000,
/* 300 */   MORHEIM_ICE_FORTRESS_220020000,
/* 301 */   RED_LAVA_CLIFF_220020000,
/* 302 */   Q1466,
/* 303 */   Q2033,
/* 304 */   Q2393,
/*     */ 
/*     */   
/* 307 */   HEIRNIRS_CABIN_220050000,
/* 308 */   BROHUNIR_220050000,
/* 309 */   BLACK_OPAL_SHIP_ANCHORAGE_220050000,
/* 310 */   DECOMPOSED_GREENS_220050000,
/* 311 */   NAHOR_LAKE_220050000,
/* 312 */   THE_EASTWEALD_220050000,
/* 313 */   BALTASAR_CEMETERY_220050000,
/* 314 */   OLD_NAHOR_CASTLE_220050000,
/* 315 */   GRIFFONS_CLAW_ENCAMPMENT_220050000,
/* 316 */   SETTLERS_CAMPSITE_220050000,
/* 317 */   IOLLU_HILLS_220050000,
/* 318 */   BALTASAR_HILL_VILLAGE_220050000,
/* 319 */   WAILING_CLIFFS_220050000,
/* 320 */   BUBU_VILLAGE_220050000,
/* 321 */   SUDORVILLE_220050000,
/* 322 */   BUBU_CHANS_HIDEOUT_220050000,
/* 323 */   HAVENJARK_FARM_220050000,
/* 324 */   HAVENJARK_CEMETERY_220050000,
/* 325 */   BRUSTHONIN_MITHRIL_MINE_220050000,
/* 326 */   PERPET_FALLS_220050000,
/* 327 */   IOLLU_FOREST_220050000,
/* 328 */   CAROBIAN_COAST_220050000,
/* 329 */   CANYON_SCOUT_POST_220050000,
/* 330 */   POLLUTED_WASTE_220050000,
/* 331 */   THE_NORTHWEALD_220050000,
/* 332 */   THE_GOLDEN_COAST_220050000,
/* 333 */   STRAGGLERS_SHELTER_220050000,
/* 334 */   THE_LEGEND_SHRINE_220050000,
/* 335 */   THE_COASTAL_PASS_220050000,
/* 336 */   THE_SAPLANDS_220050000,
/* 337 */   ADMA_STRONGHOLD_220050000,
/* 338 */   CAYRON_HILL_220050000,
/* 339 */   ADMA_PLAINS_220050000,
/* 340 */   SHAMANS_HOUSE_220050000,
/* 341 */   VIGRID_PLAINS_220050000,
/* 342 */   Q2092,
/*     */ 
/*     */   
/* 345 */   KIDORUNS_CAMPSITE_220040000,
/* 346 */   CHAOS_PASS_220040000,
/* 347 */   GIANTS_VALLEY_220040000,
/* 348 */   CHAOS_BRAMBLES_220040000,
/* 349 */   ANAIR_ICE_LAKE_220040000,
/* 350 */   ANAIR_HARBOR_220040000,
/* 351 */   ALQUIMIA_APPROACH_220040000,
/* 352 */   LEPHARIST_BARRACKS_220040000,
/* 353 */   THE_LONE_COTTAGE_220040000,
/* 354 */   HOARFROST_OUTPOST_220040000,
/* 355 */   MALEK_PASS_220040000,
/* 356 */   FANG_TROLL_ENCAMPMENT_220040000,
/* 357 */   FROST_SPIRIT_VALLEY_220040000,
/* 358 */   BERITRAS_WEAPON_220040000,
/* 359 */   BLACK_PLAINS_220040000,
/* 360 */   ABANDONED_CAMPSITE_220040000,
/* 361 */   BESFER_GHOST_VILLAGE_220040000,
/* 362 */   KURNGALFBERG_220040000,
/* 363 */   MALEK_MINE_WORKSHOP_220040000,
/* 364 */   GLACIER_PEAKS_220040000,
/* 365 */   MINE_PORT_220040000,
/* 366 */   HOARFROST_FORTRESS_220040000,
/* 367 */   HOARFROST_SHELTER_220040000,
/* 368 */   MALEK_DIGGINGS_220040000,
/* 369 */   ALQUIMIA_CASTLE_GATE_220040000,
/* 370 */   RED_MANE_CAVERN_220040000,
/* 371 */   ROAD_TO_MORHEIM_220040000,
/* 372 */   BELUSLAN_FORTRESS_220040000,
/* 373 */   MOSBEAR_SNOWFIELD_220040000,
/* 374 */   GHOST_VILLAGE_OBSERVATION_POST_220040000,
/* 375 */   ALUKINAS_PALACE_220040000,
/* 376 */   ALQUIMIA_STRONGHOLD_220040000,
/* 377 */   BESFER_REFUGEE_CAMP_220040000,
/* 378 */   HUNIBOR_ICE_GATE_220040000,
/* 379 */   THE_WHISPERING_FOREST_220040000,
/* 380 */   MAHISHAS_NEST_220040000,
/* 381 */   ANAIR_LIGHTHOUSE_220040000,
/* 382 */   CAMP_KISTENIAN_220040000,
/* 383 */   BAKARMA_BARRACKS_220040000,
/* 384 */   MIST_VALLEY_220040000,
/* 385 */   MAMUT_GRAVEYARD_220040000,
/* 386 */   THE_SACRED_ORCHARD_220040000,
/* 387 */   BELUSLAN_OBSERVATORY_220040000,
/* 388 */   MALEK_MINE_220040000,
/* 389 */   BELUSLANS_ROOF_220040000,
/*     */ 
/*     */   
/* 392 */   PRIMUM_LANDING_400010000,
/* 393 */   EASTERN_SHARD_OF_LATESRAN_1251_G_400010000,
/* 394 */   ROAH_FORTRESS_400010000,
/* 395 */   ISLE_OF_REPROACH_400010000,
/* 396 */   MIREN_ISLAND_400010000,
/* 397 */   SIELS_LEFT_WING_1131_E_400010000,
/* 398 */   KYSIS_ISLE_400010000,
/* 399 */   NORTHERN_RIDGE_400010000,
/* 400 */   EYE_OF_RESHANTA_ENTRANCE_400010000,
/* 401 */   LAKE_ASTERIA_1251_B_400010000,
/* 402 */   DIVINE_FORTRESS_400010000,
/* 403 */   TIGRAKI_ISLAND_400010000,
/* 404 */   RUSSET_PLAZA_400010000,
/* 405 */   TEMINON_TRAINING_CAMP_400010000,
/* 406 */   EASTERN_RIDGE_400010000,
/* 407 */   MIREN_FORTRESS_400010000,
/* 408 */   MUD_FALLS_400010000,
/* 409 */   KYSIS_FORTRESS_400010000,
/* 410 */   RESHANTA_400010000,
/* 411 */   GARDEN_OF_THE_DEAD_400010000,
/* 412 */   LAKE_ASTERIA_Z_1250_400010000,
/* 413 */   GARCIKHAN_STRONGHOLD_400010000,
/* 414 */   WEATHERED_CRAG_400010000,
/* 415 */   ASTERIA_FORTRESS_400010000,
/* 416 */   WESTERN_SHARD_OF_LATESRAN_1141_H_400010000,
/* 417 */   SHADE_ISLAND_400010000,
/* 418 */   RUINS_OF_ROAH_Z_1211_400010000,
/* 419 */   LATIS_PLAZA_400010000,
/* 420 */   WESTERN_SHARD_OF_LATESRAN_1211_H_400010000,
/* 421 */   EASTERN_SHARD_OF_LATESRAN_1132_F_400010000,
/* 422 */   WESTERN_RIDGE_400010000,
/* 423 */   TWILIGHT_BATTLEFIELD_400010000,
/* 424 */   CLOCKTOWER_PLAIN_400010000,
/* 425 */   WINGS_OF_SIEL_ARCHIPELAGO_Z_1131_400010000,
/* 426 */   TEMINON_FORTRESS_400010000,
/* 427 */   COLLAPSED_ANCIENT_TEMPLE_400010000,
/* 428 */   TEMINON_WHARF_400010000,
/* 429 */   DRAKENFALL_400010000,
/* 430 */   DRAKENWRECK_400010000,
/* 431 */   PRIMUM_PLAZA_400010000,
/* 432 */   ISLE_OF_SILENCE_400010000,
/* 433 */   ISLAND_OF_EXILE_400010000,
/* 434 */   ISLE_OF_DISGRACE_400010000,
/* 435 */   EYE_OF_RESHANTA_400010000,
/* 436 */   SWORDS_EDGE_400010000,
/* 437 */   RUINS_OF_ROAH_1211_B_400010000,
/* 438 */   MIRAGE_CAVE_400010000,
/* 439 */   WAREHOUSE_CONSTRUCTION_SITE_400010000,
/* 440 */   SIELS_RIGHT_WING_1132_E_400010000,
/* 441 */   PRIMUM_FORTRESS_400010000,
/* 442 */   ZEPHYR_STREAM_400010000,
/* 443 */   SIELS_WESTERN_FORTRESS_400010000,
/* 444 */   SULFUR_SWAMP_400010000,
/* 445 */   NOBELIUM_FRAGMENT_400010000,
/* 446 */   FORSAKEN_ISLE_400010000,
/* 447 */   BROKEN_NOBELIUM_400010000,
/* 448 */   SOUL_STREAM_400010000,
/* 449 */   ISLE_OF_ROOTS_400010000,
/* 450 */   SULFUR_FORTRESS_400010000,
/* 451 */   TEMINONS_LEAP_400010000,
/* 452 */   KRAKONS_DISPUTE_400010000,
/* 453 */   SIELS_LEFT_WING_1131_B_400010000,
/* 454 */   THE_SHATTERED_TEMPLE_400010000,
/* 455 */   BLACK_CLOUD_ISLAND_400010000,
/* 456 */   SOUTHERN_RIDGE_400010000,
/* 457 */   SULFUR_ARCHIPELAGO_400010000,
/* 458 */   WINGS_OF_SIEL_ARCHIPELAGO_Z_1132_400010000,
/* 459 */   SIELS_RIGHT_WING_1132_B_400010000,
/* 460 */   PRIMUM_WHARF_400010000,
/* 461 */   SULFUR_FLOW_400010000,
/* 462 */   GRAVE_OF_STEEL_400010000,
/* 463 */   LEIBO_ISLAND_400010000,
/* 464 */   GRAVE_OF_CLAWS_400010000,
/* 465 */   DRYROCK_400010000,
/* 466 */   KROTAN_ROCK_400010000,
/* 467 */   HEART_OF_SIEL_400010000,
/* 468 */   KROTAN_REFUGE_400010000,
/* 469 */   RED_HASIA_SCOUT_POST_400010000,
/* 470 */   PRIMUM_TRAINING_CAMP_400010000,
/* 471 */   TEMINON_LANDING_400010000,
/* 472 */   BLOODBURN_REACH_400010000,
/* 473 */   THE_FLINDERS_400010000,
/* 474 */   SOLITARY_ISLAND_400010000,
/* 475 */   ASTERIA_PLAIN_400010000,
/* 476 */   SIELS_EASTERN_FORTRESS_400010000,
/*     */   
/* 478 */   LABORATORY_OF_HEWAHEWA,
/* 479 */   FOREST_OF_VENGEFUL_SPIRIT,
/* 480 */   SCARS_HIDEOUT,
/* 481 */   THE_WILDERNESS,
/* 482 */   RUKHA_PLANTATION,
/* 483 */   DARKSPORE_ROAD,
/* 484 */   DESOLATE_AKARIOS_VILLAGE,
/* 485 */   DRANA_HARVESTING_AREA,
/* 486 */   TELEPATHY_CONTROL_ROOM,
/* 487 */   MARABATAS_LAKE,
/* 488 */   ROOM_OF_DIMENSION,
/* 489 */   ANUHART_LEGION_BASE,
/*     */   
/* 491 */   THE_BRIG,
/* 492 */   DREDGION,
/* 493 */   BARRACKS,
/* 494 */   ESCAPE_HATCH,
/* 495 */   GRAVITY_CONTROL,
/* 496 */   WEAPONS_DECK,
/* 497 */   THE_BRIDGE,
/* 498 */   SECONDARY_BRIG,
/* 499 */   DROP_WAITING_ZONE,
/* 500 */   LOADING_ZONE,
/* 501 */   READY_ROOM_1,
/* 502 */   SECONDARY_ESCAPE_HATCH,
/* 503 */   ENGINE_ROOM,
/* 504 */   BACKUP_ARMORY,
/* 505 */   COMMAND_ZONE,
/* 506 */   SPECIAL_ZONE,
/* 507 */   LOWER_WEAPONS_DECK,
/* 508 */   CAPTAINS_CABIN,
/* 509 */   READY_ROOM_2,
/* 510 */   AUXILLARY_POWER,
/* 511 */   PRIMARY_ARMORY,
/*     */   
/* 513 */   NOCHSANA_FORTRESS_GATE,
/* 514 */   NOCHSANA_TRAINING_CAMP,
/* 515 */   NOCHSANA_TRAINING_FORTRESS,
/*     */   
/* 517 */   Q1091,
/*     */   
/* 519 */   DF4_SZ_A2,
/* 520 */   DF4_SZ_A6_Q1,
/* 521 */   DF4_SZ_C1_Q1,
/* 522 */   DF4_SZ_B6_Q1,
/* 523 */   DF4_SZ_C2,
/* 524 */   DF4_SZ_B5_Gate_1,
/* 525 */   DF4_SZ_OP,
/* 526 */   DF4_SZ_A1,
/* 527 */   DF4_SZ_A1_Q3,
/* 528 */   DF4_SZ_A4_1,
/* 529 */   DF4_SZ_C2_2,
/* 530 */   DF4_SZ_A4_2,
/* 531 */   DF4_SZ_C1_Cave,
/* 532 */   DF4_SZ_C3,
/* 533 */   DF4_SZ_C4_Q1,
/* 534 */   DF4_SZ_D,
/* 535 */   DF4_SZ_B4,
/* 536 */   DF4_SZ_B6_3,
/* 537 */   DF4_SZ_C5,
/* 538 */   DF4_SZ_A2_Q1,
/* 539 */   DF4_SZ_A4_Q1,
/* 540 */   DF4_SZ_B5,
/* 541 */   DF4_SZ_B2,
/* 542 */   DF4_SZ_A3_Gate,
/* 543 */   DF4_SZ_B3,
/* 544 */   DF4_SZ_C6_2,
/* 545 */   DF4_SZ_A2_Under,
/* 546 */   DF4_SZ_B6,
/* 547 */   DF4_SZ_C1_Fire,
/* 548 */   DF4_SZ_A1_Q1,
/* 549 */   DF4_SZ_B2_Q1,
/* 550 */   DF4_SZ_C6,
/* 551 */   DF4_SZ_C5_Lab,
/* 552 */   DF4_SZ_C3_Q1,
/* 553 */   DF4_SZ_B2_Od,
/* 554 */   DF4_SZ_C2_3,
/* 555 */   DF4_SZ_C4_Q2,
/* 556 */   DF4_SZ_B3_Gate,
/* 557 */   DF4_SZ_A3,
/* 558 */   DF4_SZ_A5_Under,
/* 559 */   DF4_SZ_A1_Q4,
/* 560 */   DF4_SZ_C5_Q1,
/* 561 */   DF4_SZ_A1_Q2,
/* 562 */   DF4_SZ_3021_A,
/* 563 */   DF4_SZ_C1,
/* 564 */   DF4_SZ_C5_Lab_2,
/* 565 */   DF4_SZ_C4_2,
/* 566 */   DF4_SZ_A5_Gate,
/* 567 */   DF4_SZ_3011_A,
/* 568 */   DF4_SZ_A5,
/* 569 */   DF4_SZ_B1,
/* 570 */   DF4_SZ_B6_2,
/* 571 */   DF4_SZ_B4_Castle,
/* 572 */   DF4_SZ_Tw,
/* 573 */   DF4_SZ_B3_Q1,
/* 574 */   DF4_SZ_C4,
/* 575 */   DF4_SZ_A6,
/* 576 */   DF4_SZ_B5_Ruin,
/* 577 */   DF4_SZ_B5_Gate_2,
/* 578 */   DF4_SZ_C3_UnderPass_In,
/* 579 */   DF4_SZ_D_Od;
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\zone\ZoneName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */