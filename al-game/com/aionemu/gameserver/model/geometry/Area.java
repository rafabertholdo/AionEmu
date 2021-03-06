package com.aionemu.gameserver.model.geometry;

import java.awt.Point;

public interface Area {
  boolean isInside2D(Point paramPoint);

  boolean isInside2D(int paramInt1, int paramInt2);

  boolean isInside3D(Point3D paramPoint3D);

  boolean isInside3D(int paramInt1, int paramInt2, int paramInt3);

  boolean isInsideZ(Point3D paramPoint3D);

  boolean isInsideZ(int paramInt);

  double getDistance2D(Point paramPoint);

  double getDistance2D(int paramInt1, int paramInt2);

  double getDistance3D(Point3D paramPoint3D);

  double getDistance3D(int paramInt1, int paramInt2, int paramInt3);

  Point getClosestPoint(Point paramPoint);

  Point getClosestPoint(int paramInt1, int paramInt2);

  Point3D getClosestPoint(Point3D paramPoint3D);

  Point3D getClosestPoint(int paramInt1, int paramInt2, int paramInt3);

  int getMinZ();

  int getMaxZ();
}
