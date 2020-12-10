package com.aionemu.commons.database.dao;

public interface DAO {
  String getClassName();

  boolean supports(String paramString, int paramInt1, int paramInt2);
}
