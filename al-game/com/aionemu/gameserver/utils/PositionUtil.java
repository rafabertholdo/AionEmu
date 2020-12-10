package com.aionemu.gameserver.utils;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;




























public class PositionUtil
{
  private static final float MAX_ANGLE_DIFF = 90.0F;
  
  public static boolean isBehindTarget(VisibleObject object1, VisibleObject object2) {
    float angleObject1 = MathUtil.calculateAngleFrom(object1, object2);
    float angleObject2 = MathUtil.convertHeadingToDegree(object2.getHeading());
    float angleDiff = angleObject1 - angleObject2;
    
    if (angleDiff <= -270.0F) angleDiff += 360.0F; 
    if (angleDiff >= 270.0F) angleDiff -= 360.0F; 
    return (Math.abs(angleDiff) <= 90.0F);
  }







  
  public static boolean isInFrontOfTarget(VisibleObject object1, VisibleObject object2) {
    float angleObject2 = MathUtil.calculateAngleFrom(object2, object1);
    float angleObject1 = MathUtil.convertHeadingToDegree(object2.getHeading());
    float angleDiff = angleObject1 - angleObject2;
    
    if (angleDiff <= -270.0F) angleDiff += 360.0F; 
    if (angleDiff >= 270.0F) angleDiff -= 360.0F; 
    return (Math.abs(angleDiff) <= 90.0F);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\PositionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
