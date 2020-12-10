package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;





















public class _1039SomethingInTheWater
  extends QuestHandler
{
  private static final int questId = 1039;
  private static final int[] mob_ids = new int[] { 210946, 210947 };

  
  public _1039SomethingInTheWater() {
    super(Integer.valueOf(1039));
  }


  
  public void register() {
    this.qe.setQuestItemIds(182201009).add(1039);
    this.qe.setNpcQuestData(203946).addOnTalkEvent(1039);
    this.qe.setNpcQuestData(203705).addOnTalkEvent(1039);
    this.qe.addQuestLvlUp(1039);
    for (int mob_id : mob_ids) {
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1039);
    }
  }

  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1039);
    boolean lvlCheck = QuestService.checkLevelRequirement(1039, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201009)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.MYSTIC_SPRING_OF_AGAIRON))
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(1039);
    if (qs == null)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.removeItemFromInventory(player, item);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201010, 1)));
            qs.setQuestVar(2);
            _1039SomethingInTheWater.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1039);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    switch (targetId) {
      
      case 210946:
        if (qs.getQuestVarById(1) == 2 && qs.getQuestVarById(2) == 3 && var == 4) {
          
          qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        } 
        if (qs.getQuestVarById(1) <= 2 && var == 4) {
          
          qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
          updateQuestStatus(player, qs);
          return true;
        } 
        break;
    } 
    switch (targetId) {
      
      case 210947:
        if (qs.getQuestVarById(1) == 3 && qs.getQuestVarById(2) == 2 && var == 4) {
          
          qs.setQuestVarById(2, qs.getQuestVarById(2) + 1);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        } 
        if (qs.getQuestVarById(2) <= 2 && var == 4) {
          
          qs.setQuestVarById(2, qs.getQuestVarById(2) + 1);
          updateQuestStatus(player, qs);
          return true;
        }  break;
    } 
    return false;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1039);
    if (qs == null) {
      return false;
    }
    if (targetId == 203946) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 10000) {
          
          qs.setQuestVar(1);
          ItemService.addItems(player, Collections.singletonList(new QuestItems(182201009, 1)));
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        if (env.getDialogId().intValue() == 10002) {
          
          qs.setQuestVar(4);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.REWARD)
      {
        return defaultQuestEndDialog(env);
      }
    }
    else if (targetId == 203705) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        if (env.getDialogId().intValue() == 10001) {
          
          qs.setQuestVar(3);
          updateQuestStatus(player, qs);
          ItemService.decreaseItemCountByItemId(player, 182201010, 1L);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
    } 
    
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1039SomethingInTheWater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
