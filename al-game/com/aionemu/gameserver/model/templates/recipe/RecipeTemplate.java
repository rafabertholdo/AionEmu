package com.aionemu.gameserver.model.templates.recipe;

import com.aionemu.gameserver.skillengine.model.learn.SkillRace;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;














































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecipeTemplate")
public class RecipeTemplate
{
  protected List<Component> component;
  protected List<ComboProduct> comboproduct;
  @XmlAttribute
  protected int tasktype;
  @XmlAttribute
  protected int componentquantity;
  @XmlAttribute
  protected int quantity;
  @XmlAttribute
  protected int productid;
  @XmlAttribute
  protected int autolearn;
  @XmlAttribute
  protected int dp;
  @XmlAttribute
  protected int skillpoint;
  @XmlAttribute
  protected SkillRace race;
  @XmlAttribute
  protected int skillid;
  @XmlAttribute
  protected int itemid;
  @XmlAttribute
  protected int nameid;
  @XmlAttribute
  protected int id;
  
  public List<Component> getComponent() {
    if (this.component == null) {
      this.component = new ArrayList<Component>();
    }
    return this.component;
  }
  
  public Integer getComboProduct() {
    if (this.comboproduct == null)
    {
      return null;
    }
    return Integer.valueOf(((ComboProduct)this.comboproduct.get(0)).getItemid());
  }







  
  public Integer getTasktype() {
    return Integer.valueOf(this.tasktype);
  }








  
  public Integer getComponentquantity() {
    return Integer.valueOf(this.componentquantity);
  }








  
  public Integer getQuantity() {
    return Integer.valueOf(this.quantity);
  }








  
  public Integer getProductid() {
    return Integer.valueOf(this.productid);
  }








  
  public Integer getAutolearn() {
    return Integer.valueOf(this.autolearn);
  }








  
  public Integer getDp() {
    return Integer.valueOf(this.dp);
  }








  
  public Integer getSkillpoint() {
    return Integer.valueOf(this.skillpoint);
  }








  
  public SkillRace getRace() {
    return this.race;
  }








  
  public Integer getSkillid() {
    return Integer.valueOf(this.skillid);
  }








  
  public Integer getItemid() {
    return Integer.valueOf(this.itemid);
  }




  
  public int getNameid() {
    return this.nameid;
  }








  
  public Integer getId() {
    return Integer.valueOf(this.id);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\recipe\RecipeTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
