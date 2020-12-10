package quest.beluslan;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _2053AMissingFather extends QuestHandler {
  private static final int questId = 2053;
  private static final int[] npc_ids = new int[] { 204707, 204749, 204800, 700359, 730108 };

  public _2053AMissingFather() {
    super(Integer.valueOf(2053));
  }

  public void register() {
    this.qe.setQuestEnterZone(ZoneName.MALEK_MINE_220040000).add(2053);
    this.qe.addQuestLvlUp(2053);
    this.qe.setQuestItemIds(182204305).add(2053);
    for (int npc_id : npc_ids)
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2053);
    this.deletebleItems = new int[] { 182204307, 182204305 };
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2053);
    boolean lvlCheck = QuestService.checkLevelRequirement(2053, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(2500);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(2053);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 204707) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 204707) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10005:
          if (var == 5) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 204749) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182204305, 1)));
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 730108) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        case 10004:
          if (var == 4) {
            
            qs.setQuestVarById(0, 5);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
            return true;
          } 
          break;
      } 
    } else if (targetId == 204800) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
        case 10006:
          if (var == 6) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 700359 && var == 7) {
      
      if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182204307) == 0L) {
        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                ItemService.removeItemFromInventoryByItemId(player, 182204307);
                qs.setStatus(QuestStatus.REWARD);
                _2053AMissingFather.this.updateQuestStatus(player, qs);
              }
            }3000L);
      } 
    } 
    return false;
  }

  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.MALEK_MINE_220040000)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2053);
    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 3)
      return false;
    qs.setQuestVarById(0, 4);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182204305) {
      return false;
    }
    final QuestState qs = player.getQuestStateList().getQuestState(2053);
    if (qs == null || qs.getQuestVarById(0) != 2)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.removeItemFromInventoryByItemId(player, 182204305);
            qs.setQuestVarById(0, 3);
            _2053AMissingFather.this.updateQuestStatus(player, qs);
          }
        }1000L);
    return true;
  }
}
