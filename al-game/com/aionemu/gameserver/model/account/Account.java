package com.aionemu.gameserver.model.account;

import com.aionemu.gameserver.model.gameobjects.player.Storage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Account implements Iterable<PlayerAccountData> {
  private final int id;
  private String name;
  private byte accessLevel;
  private byte membership;
  private AccountTime accountTime;
  private Map<Integer, PlayerAccountData> players = new HashMap<Integer, PlayerAccountData>();

  private Storage accountWarehouse;

  public Account(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AccountTime getAccountTime() {
    return this.accountTime;
  }

  public void setAccountTime(AccountTime accountTime) {
    this.accountTime = accountTime;
  }

  public byte getAccessLevel() {
    return this.accessLevel;
  }

  public void setAccessLevel(byte accessLevel) {
    this.accessLevel = accessLevel;
  }

  public byte getMembership() {
    return this.membership;
  }

  public void setMembership(byte membership) {
    this.membership = membership;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Account)) {
      return false;
    }

    Account account = (Account) o;

    return (this.id == account.id);
  }

  public int hashCode() {
    return this.id;
  }

  public PlayerAccountData getPlayerAccountData(int chaOid) {
    return this.players.get(Integer.valueOf(chaOid));
  }

  public void addPlayerAccountData(PlayerAccountData accPlData) {
    this.players.put(Integer.valueOf(accPlData.getPlayerCommonData().getPlayerObjId()), accPlData);
  }

  public Storage getAccountWarehouse() {
    return this.accountWarehouse;
  }

  public void setAccountWarehouse(Storage accountWarehouse) {
    this.accountWarehouse = accountWarehouse;
  }

  public int size() {
    return this.players.size();
  }

  public ArrayList<PlayerAccountData> getSortedAccountsList() {
    ArrayList<PlayerAccountData> list = new ArrayList<PlayerAccountData>();
    list.addAll(this.players.values());
    Collections.sort(list, new Comparator<PlayerAccountData>() {
      public int compare(PlayerAccountData x, PlayerAccountData y) {
        Timestamp t1 = x.getPlayerCommonData().getLastOnline();
        Timestamp t2 = y.getPlayerCommonData().getLastOnline();
        if (t2 == null)
          return 1;
        if (t1 == null)
          return -1;
        return y.getPlayerCommonData().getLastOnline().compareTo(x.getPlayerCommonData().getLastOnline());
      }
    });
    return list;
  }

  public Iterator<PlayerAccountData> iterator() {
    return this.players.values().iterator();
  }
}
