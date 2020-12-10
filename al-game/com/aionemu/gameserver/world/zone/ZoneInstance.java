package com.aionemu.gameserver.world.zone;

import com.aionemu.gameserver.model.templates.zone.Point2D;
import com.aionemu.gameserver.model.templates.zone.ZoneTemplate;
import java.util.Collection;

























public class ZoneInstance
{
  private int corners;
  private float[] xCoordinates;
  private float[] yCoordinates;
  private ZoneTemplate template;
  private Collection<ZoneInstance> neighbors;
  
  public ZoneInstance(ZoneTemplate template) {
    this.template = template;
    this.corners = template.getPoints().getPoint().size();
    this.xCoordinates = new float[this.corners];
    this.yCoordinates = new float[this.corners];
    for (int i = 0; i < this.corners; i++) {
      
      Point2D point = template.getPoints().getPoint().get(i);
      this.xCoordinates[i] = point.getX();
      this.yCoordinates[i] = point.getY();
    } 
  }




  
  public int getCorners() {
    return this.corners;
  }




  
  public float[] getxCoordinates() {
    return this.xCoordinates;
  }




  
  public float[] getyCoordinates() {
    return this.yCoordinates;
  }




  
  public Collection<ZoneInstance> getNeighbors() {
    return this.neighbors;
  }





  
  public void setNeighbors(Collection<ZoneInstance> neighbours) {
    this.neighbors = neighbours;
  }




  
  public ZoneTemplate getTemplate() {
    return this.template;
  }






  
  public float getTop() {
    return this.template.getPoints().getTop();
  }






  
  public float getBottom() {
    return this.template.getPoints().getBottom();
  }






  
  public boolean isBreath() {
    return this.template.isBreath();
  }






  
  public int getPriority() {
    return this.template.getPriority();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\zone\ZoneInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
