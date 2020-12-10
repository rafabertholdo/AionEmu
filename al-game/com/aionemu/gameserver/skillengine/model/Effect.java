/*     */ package com.aionemu.gameserver.skillengine.model;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*     */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*     */ import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_ACTIVATION;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
/*     */ import com.aionemu.gameserver.skillengine.effect.Effects;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Future;
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
/*     */ public class Effect
/*     */ {
/*     */   private SkillTemplate skillTemplate;
/*     */   private int skillLevel;
/*     */   private int duration;
/*     */   private int endTime;
/*     */   private Creature effected;
/*     */   private Creature effector;
/*  53 */   private Future<?> checkTask = null;
/*  54 */   private Future<?> task = null;
/*  55 */   private Future<?>[] periodicTasks = null;
/*  56 */   private Future<?> mpUseTask = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int reserved1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int reserved2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int reserved3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private SpellStatus spellStatus = SpellStatus.NONE;
/*     */   
/*  86 */   private AttackStatus attackStatus = AttackStatus.NORMALHIT;
/*     */   
/*     */   private int shieldDefense;
/*     */   
/*     */   private boolean addedToController;
/*     */   
/*     */   private AttackCalcObserver[] attackStatusObserver;
/*     */   private AttackCalcObserver[] attackShieldObserver;
/*     */   private boolean launchSubEffect = true;
/*  95 */   private Effect subEffect = null;
/*     */ 
/*     */   
/*     */   private boolean isStopped;
/*     */ 
/*     */   
/*     */   private ItemTemplate itemTemplate;
/*     */ 
/*     */   
/*     */   private int tauntHate;
/*     */ 
/*     */   
/*     */   private int effectHate;
/*     */ 
/*     */   
/* 110 */   private Collection<EffectTemplate> sucessEffects = (new FastList()).shared();
/*     */ 
/*     */ 
/*     */   
/*     */   private ActionObserver[] actionObserver;
/*     */ 
/*     */ 
/*     */   
/*     */   public Effect(Creature effector, Creature effected, SkillTemplate skillTemplate, int skillLevel, int duration) {
/* 119 */     this.effector = effector;
/* 120 */     this.effected = effected;
/* 121 */     this.skillTemplate = skillTemplate;
/* 122 */     this.skillLevel = skillLevel;
/* 123 */     this.duration = duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public Effect(Creature effector, Creature effected, SkillTemplate skillTemplate, int skillLevel, int duration, ItemTemplate itemTemplate) {
/* 128 */     this(effector, effected, skillTemplate, skillLevel, duration);
/* 129 */     this.itemTemplate = itemTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEffectorId() {
/* 137 */     return this.effector.getObjectId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillId() {
/* 145 */     return this.skillTemplate.getSkillId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillSetException() {
/* 153 */     return this.skillTemplate.getSkillSetException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStack() {
/* 161 */     return this.skillTemplate.getStack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillLevel() {
/* 169 */     return this.skillLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillStackLvl() {
/* 177 */     return this.skillTemplate.getLvl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillType getSkillType() {
/* 186 */     return this.skillTemplate.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDuration() {
/* 194 */     return this.duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDuration(int newDuration) {
/* 202 */     this.duration = newDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getEffected() {
/* 210 */     return this.effected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getEffector() {
/* 218 */     return this.effector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPassive() {
/* 226 */     return this.skillTemplate.isPassive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTask(Future<?> task) {
/* 234 */     this.task = task;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> getPeriodicTask(int i) {
/* 242 */     return this.periodicTasks[i - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPeriodicTask(Future<?> periodicTask, int i) {
/* 251 */     if (this.periodicTasks == null)
/* 252 */       this.periodicTasks = (Future<?>[])new Future[4]; 
/* 253 */     this.periodicTasks[i - 1] = periodicTask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> getMpUseTask() {
/* 261 */     return this.mpUseTask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMpUseTask(Future<?> mpUseTask) {
/* 269 */     this.mpUseTask = mpUseTask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReserved1() {
/* 277 */     return this.reserved1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReserved1(int reserved1) {
/* 285 */     this.reserved1 = reserved1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReserved2() {
/* 293 */     return this.reserved2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReserved2(int reserved2) {
/* 301 */     this.reserved2 = reserved2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReserved3() {
/* 309 */     return this.reserved3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReserved3(int reserved3) {
/* 317 */     this.reserved3 = reserved3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttackStatus getAttackStatus() {
/* 325 */     return this.attackStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttackStatus(AttackStatus attackStatus) {
/* 333 */     this.attackStatus = attackStatus;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EffectTemplate> getEffectTemplates() {
/* 338 */     return this.skillTemplate.getEffects().getEffects();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood() {
/* 343 */     Effects effects = this.skillTemplate.getEffects();
/* 344 */     return (effects != null && effects.isFood());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isToggle() {
/* 350 */     return (this.skillTemplate.getActivationAttribute() == ActivationAttribute.TOGGLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTargetSlot() {
/* 355 */     return this.skillTemplate.getTargetSlot().ordinal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttackCalcObserver getAttackStatusObserver(int i) {
/* 364 */     return this.attackStatusObserver[i - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttackStatusObserver(AttackCalcObserver attackStatusObserver, int i) {
/* 372 */     if (this.attackStatusObserver == null)
/* 373 */       this.attackStatusObserver = new AttackCalcObserver[4]; 
/* 374 */     this.attackStatusObserver[i - 1] = attackStatusObserver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttackCalcObserver getAttackShieldObserver(int i) {
/* 383 */     return this.attackShieldObserver[i - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttackShieldObserver(AttackCalcObserver attackShieldObserver, int i) {
/* 391 */     if (this.attackShieldObserver == null)
/* 392 */       this.attackShieldObserver = new AttackCalcObserver[4]; 
/* 393 */     this.attackShieldObserver[i - 1] = attackShieldObserver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLaunchSubEffect() {
/* 401 */     return this.launchSubEffect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLaunchSubEffect(boolean launchSubEffect) {
/* 409 */     this.launchSubEffect = launchSubEffect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getShieldDefense() {
/* 417 */     return this.shieldDefense;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShieldDefense(int shieldDefense) {
/* 425 */     this.shieldDefense = shieldDefense;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpellStatus getSpellStatus() {
/* 433 */     return this.spellStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpellStatus(SpellStatus spellStatus) {
/* 441 */     this.spellStatus = spellStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Effect getSubEffect() {
/* 449 */     return this.subEffect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubEffect(Effect subEffect) {
/* 457 */     this.subEffect = subEffect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsEffectId(int effectId) {
/* 467 */     for (EffectTemplate template : this.sucessEffects) {
/*     */       
/* 469 */       if (template.getEffectid() == effectId)
/* 470 */         return true; 
/*     */     } 
/* 472 */     return false;
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
/*     */   public void initialize() {
/* 489 */     if (this.skillTemplate.getEffects() == null) {
/*     */       return;
/*     */     }
/* 492 */     boolean isDmgEffect = false;
/*     */     
/* 494 */     for (EffectTemplate template : getEffectTemplates()) {
/*     */       
/* 496 */       template.calculate(this);
/* 497 */       if (template instanceof com.aionemu.gameserver.skillengine.effect.DamageEffect && !(template instanceof com.aionemu.gameserver.skillengine.effect.DamageOverTimeEffect))
/*     */       {
/* 499 */         isDmgEffect = true;
/*     */       }
/*     */     } 
/*     */     
/* 503 */     for (EffectTemplate template : this.sucessEffects) {
/*     */       
/* 505 */       template.calculateSubEffect(this);
/* 506 */       template.calculateHate(this);
/*     */     } 
/*     */     
/* 509 */     if (isDmgEffect) {
/*     */       
/* 511 */       if (getAttackStatus() == AttackStatus.RESIST || getAttackStatus() == AttackStatus.DODGE) {
/*     */         
/* 513 */         this.sucessEffects.clear();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 518 */       if (this.sucessEffects.size() != getEffectTemplates().size())
/*     */       {
/* 520 */         for (EffectTemplate template : this.sucessEffects)
/*     */         {
/* 522 */           if (template instanceof com.aionemu.gameserver.skillengine.effect.DamageEffect && !(template instanceof com.aionemu.gameserver.skillengine.effect.DamageOverTimeEffect))
/*     */             continue; 
/* 524 */           this.sucessEffects.remove(template);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 530 */     else if (this.sucessEffects.size() != getEffectTemplates().size()) {
/*     */       
/* 532 */       this.sucessEffects.clear();
/* 533 */       if (getSkillType() == SkillType.MAGICAL) {
/* 534 */         setAttackStatus(AttackStatus.RESIST);
/*     */       } else {
/* 536 */         setAttackStatus(AttackStatus.DODGE);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEffect() {
/* 545 */     if (this.skillTemplate.getEffects() == null || this.sucessEffects.isEmpty()) {
/*     */       return;
/*     */     }
/* 548 */     for (EffectTemplate template : this.sucessEffects) {
/*     */       
/* 550 */       template.applyEffect(this);
/* 551 */       template.startSubEffect(this);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 557 */     if (this.effectHate != 0) {
/* 558 */       this.effector.getController().broadcastHate(this.effectHate);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEffect(boolean restored) {
/* 569 */     if (this.sucessEffects.isEmpty()) {
/*     */       return;
/*     */     }
/* 572 */     for (EffectTemplate template : this.sucessEffects)
/*     */     {
/* 574 */       template.startEffect(this);
/*     */     }
/*     */     
/* 577 */     if (isToggle() && this.effector instanceof Player)
/*     */     {
/* 579 */       activateToggleSkill();
/*     */     }
/*     */     
/* 582 */     if (!restored)
/* 583 */       this.duration = getEffectsDuration(); 
/* 584 */     if (this.duration == 0) {
/*     */       return;
/*     */     }
/* 587 */     this.endTime = (int)System.currentTimeMillis() + this.duration;
/*     */     
/* 589 */     this.task = ThreadPoolManager.getInstance().scheduleEffect(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 594 */             Effect.this.endEffect();
/*     */           }
/*     */         },  this.duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void activateToggleSkill() {
/* 604 */     PacketSendUtility.sendPacket((Player)this.effector, (AionServerPacket)new SM_SKILL_ACTIVATION(getSkillId(), true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deactivateToggleSkill() {
/* 612 */     PacketSendUtility.sendPacket((Player)this.effector, (AionServerPacket)new SM_SKILL_ACTIVATION(getSkillId(), false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void endEffect() {
/* 622 */     if (this.isStopped) {
/*     */       return;
/*     */     }
/* 625 */     for (EffectTemplate template : this.sucessEffects)
/*     */     {
/* 627 */       template.endEffect(this);
/*     */     }
/*     */     
/* 630 */     if (isToggle() && this.effector instanceof Player)
/*     */     {
/* 632 */       deactivateToggleSkill();
/*     */     }
/* 634 */     stopTasks();
/* 635 */     this.effected.getEffectController().clearEffect(this);
/* 636 */     this.isStopped = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopTasks() {
/* 644 */     if (this.task != null) {
/*     */       
/* 646 */       this.task.cancel(true);
/* 647 */       this.task = null;
/*     */     } 
/*     */     
/* 650 */     if (this.checkTask != null) {
/*     */       
/* 652 */       this.checkTask.cancel(true);
/* 653 */       this.checkTask = null;
/*     */     } 
/*     */     
/* 656 */     if (this.periodicTasks != null)
/*     */     {
/* 658 */       for (Future<?> periodicTask : this.periodicTasks) {
/*     */         
/* 660 */         if (periodicTask != null) {
/*     */           
/* 662 */           periodicTask.cancel(true);
/* 663 */           periodicTask = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 668 */     if (this.mpUseTask != null) {
/*     */       
/* 670 */       this.mpUseTask.cancel(true);
/* 671 */       this.mpUseTask = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getElapsedTime() {
/* 681 */     int elapsedTime = this.endTime - (int)System.currentTimeMillis();
/* 682 */     return (elapsedTime > 0) ? elapsedTime : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentTime() {
/* 692 */     return this.duration - getElapsedTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPvpDamage() {
/* 702 */     return this.skillTemplate.getPvpDamage();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemTemplate getItemTemplate() {
/* 707 */     return this.itemTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToEffectedController() {
/* 715 */     if (!this.addedToController) {
/*     */       
/* 717 */       this.effected.getEffectController().addEffect(this);
/* 718 */       this.addedToController = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEffectHate() {
/* 727 */     return this.effectHate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEffectHate(int effectHate) {
/* 735 */     this.effectHate = effectHate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTauntHate() {
/* 743 */     return this.tauntHate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTauntHate(int tauntHate) {
/* 751 */     this.tauntHate = tauntHate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionObserver getActionObserver(int i) {
/* 760 */     return this.actionObserver[i - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActionObserver(ActionObserver observer, int i) {
/* 768 */     if (this.actionObserver == null)
/* 769 */       this.actionObserver = new ActionObserver[4]; 
/* 770 */     this.actionObserver[i - 1] = observer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSucessEffect(EffectTemplate effect) {
/* 775 */     this.sucessEffects.add(effect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<EffectTemplate> getSuccessEffect() {
/* 783 */     return this.sucessEffects;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAllEffectToSucess() {
/* 788 */     for (EffectTemplate template : getEffectTemplates())
/*     */     {
/* 790 */       this.sucessEffects.add(template);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEffectsDuration() {
/* 796 */     int duration = 0;
/* 797 */     for (EffectTemplate template : this.sucessEffects) {
/*     */       
/* 799 */       int effectDuration = template.getDuration();
/* 800 */       if (template.getRandomTime() > 0)
/* 801 */         effectDuration -= Rnd.get(template.getRandomTime()); 
/* 802 */       duration = (duration > effectDuration) ? duration : effectDuration;
/*     */     } 
/* 804 */     if (this.effected instanceof Player && this.skillTemplate.getPvpDuration() != 0)
/* 805 */       duration = duration * this.skillTemplate.getPvpDuration() / 100; 
/* 806 */     return duration;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\Effect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */