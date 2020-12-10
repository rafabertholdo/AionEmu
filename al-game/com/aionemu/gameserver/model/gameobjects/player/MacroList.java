package com.aionemu.gameserver.model.gameobjects.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;













public class MacroList
{
  private static final Logger logger = Logger.getLogger(MacroList.class);



  
  private final Map<Integer, String> macrosses;




  
  public MacroList() {
    this.macrosses = new HashMap<Integer, String>();
  }






  
  public MacroList(Map<Integer, String> arg) {
    this.macrosses = arg;
  }






  
  public Map<Integer, String> getMacrosses() {
    return Collections.unmodifiableMap(this.macrosses);
  }











  
  public synchronized boolean addMacro(int macroPosition, String macroXML) {
    if (this.macrosses.containsKey(Integer.valueOf(macroPosition))) {
      
      logger.warn("Trying to add macro with already existing order.");
      return false;
    } 
    
    this.macrosses.put(Integer.valueOf(macroPosition), macroXML);
    return true;
  }








  
  public synchronized boolean removeMacro(int macroPosition) {
    String m = this.macrosses.remove(Integer.valueOf(macroPosition));
    if (m == null) {
      
      logger.warn("Trying to remove non existing macro.");
      return false;
    } 
    return true;
  }






  
  public int getSize() {
    return this.macrosses.size();
  }




  
  public Set<Map.Entry<Integer, String>> entrySet() {
    return Collections.unmodifiableSet(getMacrosses().entrySet());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\MacroList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
