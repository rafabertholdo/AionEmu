package com.aionemu.gameserver.model.geometry;

import java.awt.Point;
import java.io.Serializable;


















































public class Point3D
  implements Cloneable, Serializable
{
  private int x;
  private int y;
  private int z;
  
  public Point3D() {}
  
  public Point3D(Point point, int z) {
    this(point.x, point.y, z);
  }







  
  public Point3D(Point3D point) {
    this(point.getX(), point.getY(), point.getZ());
  }











  
  public Point3D(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }






  
  public int getX() {
    return this.x;
  }







  
  public void setX(int x) {
    this.x = x;
  }






  
  public int getY() {
    return this.y;
  }







  
  public void setY(int y) {
    this.y = y;
  }






  
  public int getZ() {
    return this.z;
  }







  
  public void setZ(int z) {
    this.z = z;
  }









  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof Point3D)) {
      return false;
    }
    Point3D point3D = (Point3D)o;
    
    return (this.x == point3D.x && this.y == point3D.y && this.z == point3D.z);
  }














  
  public int hashCode() {
    int result = this.x;
    result = 31 * result + this.y;
    result = 31 * result + this.z;
    return result;
  }









  
  public Point3D clone() throws CloneNotSupportedException {
    return new Point3D(this);
  }







  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Point3D");
    sb.append("{x=").append(this.x);
    sb.append(", y=").append(this.y);
    sb.append(", z=").append(this.z);
    sb.append('}');
    return sb.toString();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\geometry\Point3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
