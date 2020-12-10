/*     */ package com.aionemu.gameserver.restrictions;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.lang.ArrayUtils;
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
/*     */ public final class RestrictionsManager
/*     */ {
/*     */   private enum RestrictionMode
/*     */     implements Comparator<Restrictions>
/*     */   {
/*  59 */     isRestricted,
/*  60 */     canAttack,
/*  61 */     canAffectBySkill,
/*  62 */     canUseSkill,
/*  63 */     canChat,
/*  64 */     canInviteToGroup,
/*  65 */     canInviteToAlliance,
/*  66 */     canChangeEquip,
/*  67 */     canTrade,
/*  68 */     canUseWarehouse,
/*  69 */     canUseItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Method METHOD;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RestrictionMode() {
/*     */       for (Method method : Restrictions.class.getMethods()) {
/*     */         if (name().equals(method.getName())) {
/*     */           this.METHOD = method;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       throw new InternalError();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     private static final RestrictionMode[] VALUES = values();
/*     */     private boolean equalsMethod(Method method) { if (!this.METHOD.getName().equals(method.getName()))
/*     */         return false;  if (!this.METHOD.getReturnType().equals(method.getReturnType()))
/*     */         return false; 
/* 104 */       return Arrays.equals((Object[])this.METHOD.getParameterTypes(), (Object[])method.getParameterTypes()); } private static RestrictionMode parse(Method method) { for (RestrictionMode mode : VALUES) {
/*     */         
/* 106 */         if (mode.equalsMethod(method)) {
/* 107 */           return mode;
/*     */         }
/*     */       } 
/* 110 */       return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(Restrictions o1, Restrictions o2) {
/* 116 */       return Double.compare(getPriority(o2), getPriority(o1));
/*     */     } static {
/*     */     
/*     */     }
/*     */     private double getPriority(Restrictions restriction) {
/* 121 */       RestrictionPriority a1 = getMatchingMethod((Class)restriction.getClass()).<RestrictionPriority>getAnnotation(RestrictionPriority.class);
/* 122 */       if (a1 != null) {
/* 123 */         return a1.value();
/*     */       }
/* 125 */       RestrictionPriority a2 = restriction.getClass().<RestrictionPriority>getAnnotation(RestrictionPriority.class);
/* 126 */       if (a2 != null) {
/* 127 */         return a2.value();
/*     */       }
/* 129 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     private Method getMatchingMethod(Class<? extends Restrictions> clazz) {
/* 134 */       for (Method method : clazz.getMethods()) {
/*     */         
/* 136 */         if (equalsMethod(method)) {
/* 137 */           return method;
/*     */         }
/*     */       } 
/* 140 */       throw new InternalError();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 145 */   private static final Restrictions[][] RESTRICTIONS = new Restrictions[RestrictionMode.VALUES.length][0];
/*     */ 
/*     */   
/*     */   public static synchronized void activate(Restrictions restriction) {
/* 149 */     for (Method method : restriction.getClass().getMethods()) {
/*     */       
/* 151 */       RestrictionMode mode = RestrictionMode.parse(method);
/*     */       
/* 153 */       if (mode != null)
/*     */       {
/*     */         
/* 156 */         if (method.getAnnotation(DisabledRestriction.class) == null) {
/*     */ 
/*     */           
/* 159 */           Restrictions[] restrictions = RESTRICTIONS[mode.ordinal()];
/*     */           
/* 161 */           if (!ArrayUtils.contains((Object[])restrictions, restriction)) {
/* 162 */             restrictions = (Restrictions[])ArrayUtils.add((Object[])restrictions, restriction);
/*     */           }
/* 164 */           Arrays.sort(restrictions, mode);
/*     */           
/* 166 */           RESTRICTIONS[mode.ordinal()] = restrictions;
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized void deactivate(Restrictions restriction) {
/* 172 */     for (RestrictionMode mode : RestrictionMode.VALUES) {
/*     */       
/* 174 */       Restrictions[] restrictions = RESTRICTIONS[mode.ordinal()];
/*     */       int index;
/* 176 */       while ((index = ArrayUtils.indexOf((Object[])restrictions, restriction)) != -1) {
/* 177 */         restrictions = (Restrictions[])ArrayUtils.remove((Object[])restrictions, index);
/*     */       }
/* 179 */       RESTRICTIONS[mode.ordinal()] = restrictions;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 186 */     activate(new PlayerRestrictions());
/*     */     
/* 188 */     activate(new ShutdownRestrictions());
/*     */     
/* 190 */     activate(new PrisonRestrictions());
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
/*     */   public static boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
/* 207 */     if (player == null) {
/* 208 */       return true;
/*     */     }
/* 210 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.isRestricted.ordinal()]) {
/*     */       
/* 212 */       if (!restrictions.isRestricted(player, callingRestriction)) {
/* 213 */         return false;
/*     */       }
/*     */     } 
/* 216 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canAttack(Player player, VisibleObject target) {
/* 227 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canAttack.ordinal()]) {
/*     */       
/* 229 */       if (!restrictions.canAttack(player, target)) {
/* 230 */         return false;
/*     */       }
/*     */     } 
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canAffectBySkill(Player player, VisibleObject target) {
/* 244 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canAffectBySkill.ordinal()]) {
/*     */       
/* 246 */       if (!restrictions.canAffectBySkill(player, target)) {
/* 247 */         return false;
/*     */       }
/*     */     } 
/* 250 */     return true;
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
/*     */   public static boolean canUseSkill(Player player, Skill skill) {
/* 262 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canUseSkill.ordinal()]) {
/*     */       
/* 264 */       if (!restrictions.canUseSkill(player, skill)) {
/* 265 */         return false;
/*     */       }
/*     */     } 
/* 268 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canChat(Player player) {
/* 278 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canChat.ordinal()]) {
/*     */       
/* 280 */       if (!restrictions.canChat(player)) {
/* 281 */         return false;
/*     */       }
/*     */     } 
/* 284 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canInviteToGroup(Player player, Player target) {
/* 295 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canInviteToGroup.ordinal()]) {
/*     */       
/* 297 */       if (!restrictions.canInviteToGroup(player, target)) {
/* 298 */         return false;
/*     */       }
/*     */     } 
/* 301 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canInviteToAlliance(Player player, Player target) {
/* 312 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canInviteToAlliance.ordinal()]) {
/*     */       
/* 314 */       if (!restrictions.canInviteToAlliance(player, target)) {
/* 315 */         return false;
/*     */       }
/*     */     } 
/* 318 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canChangeEquip(Player player) {
/* 328 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canChangeEquip.ordinal()]) {
/*     */       
/* 330 */       if (!restrictions.canChangeEquip(player)) {
/* 331 */         return false;
/*     */       }
/*     */     } 
/* 334 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canTrade(Player player) {
/* 345 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canTrade.ordinal()]) {
/*     */       
/* 347 */       if (!restrictions.canTrade(player)) {
/* 348 */         return false;
/*     */       }
/*     */     } 
/* 351 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canUseWarehouse(Player player) {
/* 362 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canUseWarehouse.ordinal()]) {
/*     */       
/* 364 */       if (!restrictions.canUseWarehouse(player)) {
/* 365 */         return false;
/*     */       }
/*     */     } 
/* 368 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canUseItem(Player player) {
/* 378 */     for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canUseItem.ordinal()]) {
/*     */       
/* 380 */       if (!restrictions.canUseItem(player))
/* 381 */         return false; 
/*     */     } 
/* 383 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\restrictions\RestrictionsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */