package com.aionemu.gameserver.model;





















public enum EmotionType
{
  UNK(-1),
  SELECT_TARGET(0),
  JUMP(1),
  SIT(2),
  STAND(3),
  CHAIR_SIT(4),
  CHAIR_UP(5),
  START_FLYTELEPORT(6),
  LAND_FLYTELEPORT(7),
  FLY(11),
  LAND(12),
  DIE(16),
  RESURRECT(17),
  EMOTE(19),
  END_DUEL(20),
  ATTACKMODE(22),
  NEUTRALMODE(23),
  WALK(24),
  RUN(25),
  SWITCH_DOOR(29),
  START_EMOTE(30),
  OPEN_PRIVATESHOP(31),
  CLOSE_PRIVATESHOP(32),
  START_EMOTE2(33),
  POWERSHARD_ON(34),
  POWERSHARD_OFF(35),
  ATTACKMODE2(36),
  NEUTRALMODE2(37),
  START_LOOT(38),
  END_LOOT(39),
  START_QUESTLOOT(40),
  END_QUESTLOOT(41);
  
  private int id;

  
  EmotionType(int id) {
    this.id = id;
  }

  
  public int getTypeId() {
    return this.id;
  }


  
  public static EmotionType getEmotionTypeById(int id) {
    for (EmotionType emotionType : values()) {
      
      if (emotionType.getTypeId() == id)
        return emotionType; 
    } 
    return UNK;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\EmotionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
