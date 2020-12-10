package com.aionemu.gameserver.model.geometry;

import com.aionemu.gameserver.utils.MathUtil;
import java.awt.Point;

public class CylinderArea extends AbstractArea {
  private final int centerX;
  private final int centerY;
  private final int radius;

  public CylinderArea(Point center, int radius, int minZ, int maxZ) {
    this(center.x, center.y, radius, minZ, maxZ);
  }

  public CylinderArea(int x, int y, int radius, int minZ, int maxZ) {
    super(minZ, maxZ);
    this.centerX = x;
    this.centerY = y;
    this.radius = radius;
  }

  public boolean isInside2D(int x, int y) {
    return (MathUtil.getDistance(this.centerX, this.centerY, x, y) < this.radius);
  }

  public double getDistance2D(int x, int y) {
    if (isInside2D(x, y)) {
      return 0.0D;
    }

    return Math.abs(MathUtil.getDistance(this.centerX, this.centerY, x, y) - this.radius);
  }

  public double getDistance3D(int x, int y, int z) {
    if (isInside3D(x, y, z)) {
      return 0.0D;
    }
    if (isInsideZ(z)) {
      return getDistance2D(x, y);
    }

    if (z < getMinZ()) {
      return MathUtil.getDistance(this.centerX, this.centerY, getMinZ(), x, y, z);
    }

    return MathUtil.getDistance(this.centerX, this.centerY, getMaxZ(), x, y, z);
  }

  public Point getClosestPoint(int x, int y) {
    if (isInside2D(x, y)) {
      return new Point(x, y);
    }

    int vX = x - this.centerX;
    int vY = y - this.centerY;
    double magV = MathUtil.getDistance(this.centerX, this.centerY, x, y);
    double pointX = this.centerX + vX / magV * this.radius;
    double pointY = this.centerY + vY / magV * this.radius;
    return new Point((int) Math.round(pointX), (int) Math.round(pointY));
  }
}
