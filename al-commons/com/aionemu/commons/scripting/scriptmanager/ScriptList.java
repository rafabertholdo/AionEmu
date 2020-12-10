package com.aionemu.commons.scripting.scriptmanager;

import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javolution.text.TextBuilder;

































@XmlRootElement(name = "scriptlist")
@XmlAccessorType(XmlAccessType.NONE)
public class ScriptList
{
  @XmlElement(name = "scriptinfo", type = ScriptInfo.class)
  private Set<ScriptInfo> scriptInfos;
  
  public Set<ScriptInfo> getScriptInfos() {
    return this.scriptInfos;
  }







  
  public void setScriptInfos(Set<ScriptInfo> scriptInfos) {
    this.scriptInfos = scriptInfos;
  }



  
  public String toString() {
    TextBuilder tb = TextBuilder.newInstance();
    
    tb.append("ScriptList");
    tb.append("{scriptInfos=").append(this.scriptInfos);
    tb.append('}');
    
    String toString = tb.toString();
    
    TextBuilder.recycle(tb);
    
    return toString;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\scriptmanager\ScriptList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
