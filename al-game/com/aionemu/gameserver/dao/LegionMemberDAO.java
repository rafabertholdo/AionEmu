package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.legion.LegionMember;
import com.aionemu.gameserver.model.legion.LegionMemberEx;
import java.util.ArrayList;

public abstract class LegionMemberDAO implements IDFactoryAwareDAO {
  public abstract boolean isIdUsed(int paramInt);

  public abstract boolean saveNewLegionMember(LegionMember paramLegionMember);

  public abstract void storeLegionMember(int paramInt, LegionMember paramLegionMember);

  public abstract LegionMember loadLegionMember(int paramInt);

  public abstract LegionMemberEx loadLegionMemberEx(int paramInt);

  public abstract LegionMemberEx loadLegionMemberEx(String paramString);

  public abstract ArrayList<Integer> loadLegionMembers(int paramInt);

  public abstract void deleteLegionMember(int paramInt);

  public final String getClassName() {
    return LegionMemberDAO.class.getName();
  }
}
