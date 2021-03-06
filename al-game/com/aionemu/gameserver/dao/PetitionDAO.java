package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.Petition;
import java.util.Set;

public abstract class PetitionDAO implements DAO {
  public abstract int getNextAvailableId();

  public abstract void insertPetition(Petition paramPetition);

  public abstract void deletePetition(int paramInt);

  public abstract Set<Petition> getPetitions();

  public abstract Petition getPetitionById(int paramInt);

  public abstract void setReplied(int paramInt);

  public final String getClassName() {
    return PetitionDAO.class.getName();
  }
}
