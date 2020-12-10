package com.aionemu.gameserver.questEngine.model;






















public class QuestVars
{
  private Integer[] questVars = new Integer[5];


  
  public QuestVars() {}

  
  public QuestVars(int var) {
    setVar(var);
  }





  
  public int getVarById(int id) {
    return this.questVars[id].intValue();
  }





  
  public void setVarById(int id, int var) {
    this.questVars[id] = Integer.valueOf(var);
  }




  
  public int getQuestVars() {
    int var = 0;
    var |= this.questVars[4].intValue();
    for (int i = 3; i >= 0; i--) {
      
      var <<= 6;
      var |= this.questVars[i].intValue();
    } 
    return var;
  }

  
  public void setVar(int var) {
    for (int i = 0; i < 5; i++) {

      
      this.questVars[i] = Integer.valueOf(var & 0x3F);
      var >>= 6;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\QuestVars.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
