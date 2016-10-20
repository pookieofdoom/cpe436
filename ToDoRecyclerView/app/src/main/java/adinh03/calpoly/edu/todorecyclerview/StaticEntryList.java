package adinh03.calpoly.edu.todorecyclerview;

import java.util.ArrayList;

/**
 * Created by Anthony on 10/19/16.
 */

public class StaticEntryList {
   private  ArrayList<EntryList> entry;

   public void setEntry(ArrayList<EntryList> entry) {
      this.entry = entry;
   }

   public EntryList getEntry(int index) {
      return entry.get(index);
   }

   private static final StaticEntryList staticEntryList = new StaticEntryList();
   public static StaticEntryList getInstance() {
      return staticEntryList;
   }
}
