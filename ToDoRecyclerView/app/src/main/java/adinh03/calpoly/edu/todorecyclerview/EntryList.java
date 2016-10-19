package adinh03.calpoly.edu.todorecyclerview;

/**
 * Created by Anthony on 10/17/16.
 */

public class EntryList {
   private String addText;
   private boolean isChecked;

   public EntryList(String addText, boolean isChecked){
      this.addText = addText;
      this.isChecked = isChecked;
   }

   public String getAddText() {
      return addText;
   }

   public void setAddText(String addText) {
      this.addText = addText;
   }

   public boolean isChecked() {
      return isChecked;
   }

   public void setChecked(boolean checked) {
      isChecked = checked;
   }
}
