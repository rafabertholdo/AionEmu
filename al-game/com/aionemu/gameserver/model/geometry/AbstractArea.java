package com.aionemu.gameserver.model.geometry;

import java.awt.Point;

public abstract class AbstractArea implements Area {
  private final int minZ;
  private final int maxZ;

  protected AbstractArea(int minZ, int maxZ) {
    if (minZ > maxZ) {
      throw new IllegalArgumentException("minZ(" + minZ + ") > maxZ(" + maxZ + ")");
    }
    this.minZ = minZ;
    this.maxZ = maxZ;
  }

  public boolean isInside2D(Point point) {
    return isInside2D(point.x, point.y);
  }

  public boolean isInside3D(Point3D point) {
    return isInside3D(point.getX(), point.getY(), point.getZ());
  }

  public boolean isInside3D(int x, int y, int z) {
    return (isInsideZ(z) && isInside2D(x, y));
  }

  public boolean isInsideZ(Point3D point) {
    return isInsideZ(point.getZ());
  }

  public boolean isInsideZ(int z) {
    return (z >= getMinZ() && z <= getMaxZ());
  }

  public double getDistance2D(Point point) {
    return getDistance2D(point.x, point.y);
  }

  public double getDistance3D(Point3D point) {
    return getDistance3D(point.getX(), point.getY(), point.getZ());
  }

  public Point getClosestPoint(Point point) {
    return getClosestPoint(point.x, point.y);
  }

  public Point3D getClosestPoint(Point3D point) {
    return getClosestPoint(point.getX(), point.getY(), point.getZ());
  }

  public Point3D getClosestPoint(int x, int y, int z) {
    int zCoord;
    Point closest2d = getClosestPoint(x, y);

    if (isInsideZ(z)) {

      zCoord = z;
    } else if (z < getMinZ()) {

      zCoord = getMinZ();
    } else {

      zCoord = getMaxZ();
    }

    return new Point3D(closest2d.x, closest2d.y, zCoord);
  }

  public int getMinZ() {
    return this.minZ;
  }

  public int getMaxZ() {
    return this.maxZ;
  }
}
