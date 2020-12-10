package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.gameobjects.Letter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javolution.util.FastMap;























public class Mailbox
{
  private Map<Integer, Letter> mails = (Map<Integer, Letter>)new FastMap();





  
  public void putLetterToMailbox(Letter letter) {
    this.mails.put(Integer.valueOf(letter.getObjectId()), letter);
  }






  
  public Collection<Letter> getLetters() {
    SortedSet<Letter> letters = new TreeSet<Letter>(new Comparator<Letter>()
        {
          
          public int compare(Letter o1, Letter o2)
          {
            if (o1.getTimeStamp().getTime() > o2.getTimeStamp().getTime())
              return 1; 
            if (o1.getTimeStamp().getTime() < o2.getTimeStamp().getTime()) {
              return -1;
            }
            return (o1.getObjectId() > o2.getObjectId()) ? 1 : -1;
          }
        });

    
    for (Letter letter : this.mails.values())
    {
      letters.add(letter);
    }
    return letters;
  }







  
  public Letter getLetterFromMailbox(int letterObjId) {
    return this.mails.get(Integer.valueOf(letterObjId));
  }






  
  public boolean haveUnread() {
    for (Letter letter : this.mails.values()) {
      
      if (letter.isUnread()) {
        return true;
      }
    } 
    return false;
  }





  
  public int getFreeSlots() {
    return 65536 - this.mails.size();
  }





  
  public boolean haveFreeSlots() {
    return (this.mails.size() < 100);
  }




  
  public void removeLetter(int letterId) {
    this.mails.remove(Integer.valueOf(letterId));
  }






  
  public int size() {
    return this.mails.size();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Mailbox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
