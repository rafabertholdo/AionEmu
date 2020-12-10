package quest.verteron;

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
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _1019FlyingReconnaissance extends QuestHandler {
  private static final int questId = 1019;

  public _1019FlyingReconnaissance() {
    super(Integer.valueOf(1019));
  }

  public void register() {
    this.qe.addQuestLvlUp(1019);
    this.qe.setNpcQuestData(203146).addOnTalkEvent(1019);
    this.qe.setQuestEnterZone(ZoneName.TURSIN_OUTPOST).add(1019);
    this.qe.setNpcQuestData(203098).addOnTalkEvent(1019);
    this.qe.setNpcQuestData(203147).addOnTalkEvent(1019);
    this.qe.setNpcQuestData(210158).addOnAttackEvent(1019);
    this.qe.setNpcQuestData(700037).addOnTalkEvent(1019);
    this.qe.setQuestItemIds(182200023).add(1019);
    this.qe.setNpcQuestData(210697).addOnKillEvent(1019);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1019);
    if (qs == null) {
      return false;
    }
    final int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203146:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            case 10000:
              if (var == 0) {
                
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200505, 1))))
                  return true; 
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203098:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            case 10001:
              if (var == 2) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203147:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 3)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438); 
              if (var == 5)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
            case 10002:
            case 10003:
              if (var == 3 || var == 5) {
                
                if (var == 5 && 
                  !ItemService.addItems(player, Collections.singletonList(new QuestItems(182200023, 1))))
                  return true; 
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
          } 
          break;
        case 700037:
          if (env.getDialogId().intValue() == -1) {
            
            if (var < 6 || var >= 9)
              return false; 
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    Npc npc = (Npc)player.getTarget();
                    if (npc == null || npc.getObjectId() != targetObjectId)
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    
                    npc.getController().onDie(null);
                    qs.setQuestVarById(0, var + 1);
                    _1019FlyingReconnaissance.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 
          return false;
      } 

    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203098 && env.getDialogId().intValue() == -1) {
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
      }
      return defaultQuestEndDialog(env);
    } 
    
    return false;
  }

  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.TURSIN_OUTPOST)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1019);
    if (qs == null)
      return false;
    if (zoneName == ZoneName.TURSIN_OUTPOST_ENTRANCE)
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 18));
    if (qs.getQuestVarById(0) == 1) {

      qs.setQuestVarById(0, 2);
      updateQuestStatus(player, qs);
      return true;
    }
    return false;
  }

  public boolean onAttackEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1019);
    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 4)
      return false;
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (targetId != 210158)
      return false;
    if (MathUtil.getDistance(env.getVisibleObject(), 1552.74F, 1160.36F, 114.0F) < 6.0D) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 13));
      ((Npc) env.getVisibleObject()).getController().onDie(null);
      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
      updateQuestStatus(player, qs);
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182200023)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.TURSIN_TOTEM_POLE))
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(1019);
    if (qs == null)
      return false; 
    int var = qs.getQuestVars().getQuestVars();
    if (var != 9)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.removeItemFromInventory(player, item);
            qs.setQuestVarById(0, 10);
            _1019FlyingReconnaissance.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1019);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 210158 && var == 1)
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 22));
    qs.setStatus(QuestStatus.REWARD);
    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
    updateQuestStatus(player, qs);

    if (targetId == 210697) {
      if (var == 10) {

        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        return true;
      }
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    boolean lvlCheck = QuestService.checkLevelRequirement(1019, player.getCommonData().getLevel());
    QuestState qs = player.getQuestStateList().getQuestState(1019);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
