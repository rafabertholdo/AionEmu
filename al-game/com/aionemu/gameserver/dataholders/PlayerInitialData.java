package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import gnu.trove.THashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;










@XmlRootElement(name = "player_initial_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerInitialData
{
  @XmlElement(name = "player_data")
  private List<PlayerCreationData> dataList = new ArrayList<PlayerCreationData>();
  
  @XmlElement(name = "elyos_spawn_location", required = true)
  private LocationData elyosSpawnLocation;
  
  @XmlElement(name = "asmodian_spawn_location", required = true)
  private LocationData asmodianSpawnLocation;
  
  private THashMap<PlayerClass, PlayerCreationData> data = new THashMap();

  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (PlayerCreationData pt : this.dataList)
    {
      this.data.put(pt.getRequiredPlayerClass(), pt);
    }
    
    this.dataList.clear();
    this.dataList = null;
  }

  
  public PlayerCreationData getPlayerCreationData(PlayerClass cls) {
    return (PlayerCreationData)this.data.get(cls);
  }

  
  public int size() {
    return this.data.size();
  }

  
  public LocationData getSpawnLocation(Race race) {
    switch (race) {
      
      case ASMODIANS:
        return this.asmodianSpawnLocation;
      case ELYOS:
        return this.elyosSpawnLocation;
    } 
    throw new IllegalArgumentException();
  }



  
  public static class PlayerCreationData
  {
    @XmlAttribute(name = "class")
    private PlayerClass requiredPlayerClass;


    
    @XmlElement(name = "items")
    private ItemsType itemsType;



    
    PlayerClass getRequiredPlayerClass() {
      return this.requiredPlayerClass;
    }

    
    public List<ItemType> getItems() {
      return Collections.unmodifiableList(this.itemsType.items);
    }
    
    static class ItemsType {
      @XmlElement(name = "item")
      public List<PlayerInitialData.PlayerCreationData.ItemType> items = new ArrayList<PlayerInitialData.PlayerCreationData.ItemType>();
    }

    
    public static class ItemType
    {
      @XmlAttribute(name = "id")
      @XmlIDREF
      public ItemTemplate template;
      
      @XmlAttribute(name = "count")
      public int count;

      
      public ItemTemplate getTemplate() {
        return this.template;
      }

      
      public int getCount() {
        return this.count;
      }


      
      public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ItemType");
        sb.append("{template=").append(this.template);
        sb.append(", count=").append(this.count);
        sb.append('}');
        return sb.toString();
      }
    }
  }



  
  public static class LocationData
  {
    @XmlAttribute(name = "map_id")
    private int mapId;


    
    @XmlAttribute(name = "x")
    private float x;

    
    @XmlAttribute(name = "y")
    private float y;

    
    @XmlAttribute(name = "z")
    private float z;

    
    @XmlAttribute(name = "heading")
    private byte heading;


    
    public int getMapId() {
      return this.mapId;
    }

    
    public float getX() {
      return this.x;
    }

    
    public float getY() {
      return this.y;
    }

    
    public float getZ() {
      return this.z;
    }

    
    public byte getHeading() {
      return this.heading;
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\PlayerInitialData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
