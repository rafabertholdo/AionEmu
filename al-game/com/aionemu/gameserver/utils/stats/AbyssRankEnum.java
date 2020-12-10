package com.aionemu.gameserver.utils.stats;




















public enum AbyssRankEnum
{
  GRADE9_SOLDIER(1, 120, 24, 0),
  GRADE8_SOLDIER(2, 168, 37, 1200),
  GRADE7_SOLDIER(3, 235, 58, 4220),
  GRADE6_SOLDIER(4, 329, 91, 10990),
  GRADE5_SOLDIER(5, 461, 143, 23500),
  GRADE4_SOLDIER(6, 645, 225, 42780),
  GRADE3_SOLDIER(7, 903, 356, 69700),
  GRADE2_SOLDIER(8, 1264, 561, 105600),
  GRADE1_SOLDIER(9, 1770, 885, 150800),
  STAR1_OFFICER(10, 2124, 1195, 214100, 1000),
  STAR2_OFFICER(11, 2549, 1616, 278700, 700),
  STAR3_OFFICER(12, 3059, 2184, 344500, 500),
  STAR4_OFFICER(13, 3671, 2949, 411700, 300),
  STAR5_OFFICER(14, 4405, 3981, 488200, 100),
  GENERAL(15, 5286, 5374, 565400, 30),
  GREAT_GENERAL(16, 6343, 7258, 643200, 10),
  COMMANDER(17, 7612, 9799, 721600, 3),
  SUPREME_COMMANDER(18, 9134, 13229, 800700, 1);

  
  private int id;

  
  private int pointsGained;

  
  private int pointsLost;
  
  private int required;
  
  private int quota;

  
  AbyssRankEnum(int id, int pointsGained, int pointsLost, int required) {
    this.id = id;
    this.pointsGained = pointsGained;
    this.pointsLost = pointsLost;
    this.required = required;
    this.quota = 0;
  }










  
  AbyssRankEnum(int id, int pointsGained, int pointsLost, int required, int quota) {
    this.id = id;
    this.pointsGained = pointsGained;
    this.pointsLost = pointsLost;
    this.required = required;
    this.quota = quota;
  }




  
  public int getId() {
    return this.id;
  }




  
  public int getPointsLost() {
    return this.pointsLost;
  }




  
  public int getPointsGained() {
    return this.pointsGained;
  }




  
  public int getRequired() {
    return this.required;
  }




  
  public int getQuota() {
    return this.quota;
  }





  
  public static AbyssRankEnum getRankById(int id) {
    for (AbyssRankEnum rank : values()) {
      
      if (rank.getId() == id)
        return rank; 
    } 
    throw new IllegalArgumentException("Invalid abyss rank provided");
  }





  
  public static AbyssRankEnum getRankForAp(int ap) {
    AbyssRankEnum r = GRADE9_SOLDIER; AbyssRankEnum[] arr$; int len$, i$;
    for (arr$ = values(), len$ = arr$.length, i$ = 0; i$ < len$; ) { AbyssRankEnum rank = arr$[i$];
      
      if (rank.getRequired() <= ap) {
        r = rank;
        i$++;
      }  }
    
    return r;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\AbyssRankEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
