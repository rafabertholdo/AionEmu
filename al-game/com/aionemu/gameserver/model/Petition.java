package com.aionemu.gameserver.model;






















public class Petition
{
  private final int petitionId;
  private final int playerObjId;
  private final PetitionType type;
  private final String title;
  private final String contentText;
  private final String additionalData;
  private final PetitionStatus status;
  
  public Petition(int petitionId) {
    this.petitionId = petitionId;
    this.playerObjId = 0;
    this.type = PetitionType.INQUIRY;
    this.title = "";
    this.contentText = "";
    this.additionalData = "";
    this.status = PetitionStatus.PENDING;
  }

  
  public Petition(int petitionId, int playerObjId, int petitionTypeId, String title, String contentText, String additionalData, int petitionStatus) {
    this.petitionId = petitionId;
    this.playerObjId = playerObjId;
    switch (petitionTypeId) {
      case 256:
        this.type = PetitionType.CHARACTER_STUCK; break;
      case 512: this.type = PetitionType.CHARACTER_RESTORATION; break;
      case 768: this.type = PetitionType.BUG; break;
      case 1024: this.type = PetitionType.QUEST; break;
      case 1280: this.type = PetitionType.UNACCEPTABLE_BEHAVIOR; break;
      case 1536: this.type = PetitionType.SUGGESTION; break;
      case 65280: this.type = PetitionType.INQUIRY; break;
      default: this.type = PetitionType.INQUIRY; break;
    } 
    this.title = title;
    this.contentText = contentText;
    this.additionalData = additionalData;
    switch (petitionStatus) {
      case 0:
        this.status = PetitionStatus.PENDING; return;
      case 1: this.status = PetitionStatus.IN_PROGRESS; return;
      case 2: this.status = PetitionStatus.REPLIED; return;
    }  this.status = PetitionStatus.PENDING;
  }


  
  public int getPlayerObjId() {
    return this.playerObjId;
  }

  
  public int getPetitionId() {
    return this.petitionId;
  }

  
  public PetitionType getPetitionType() {
    return this.type;
  }

  
  public String getTitle() {
    return this.title;
  }

  
  public String getContentText() {
    return this.contentText;
  }

  
  public String getAdditionalData() {
    return this.additionalData;
  }

  
  public PetitionStatus getStatus() {
    return this.status;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\Petition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
