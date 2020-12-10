package com.aionemu.commons.database.dao;































public class DAOException
  extends RuntimeException
{
  private static final long serialVersionUID = 7637014806313099318L;
  
  public DAOException() {}
  
  public DAOException(String message) {
    super(message);
  }






  
  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }





  
  public DAOException(Throwable cause) {
    super(cause);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\dao\DAOException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
