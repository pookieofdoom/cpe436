package adinh03.calpoly.edu.todorecyclerview;

/**
 * Created by Anthony on 10/17/16.
 */

public class EntryList {
   private String addText;
   private boolean isChecked;
   private String mKey;
   private String descText;

   public EntryList(){

   }
   public EntryList(String addText, boolean isChecked, String key, String descText){
      this.addText = addText;
      this.isChecked = isChecked;
      mKey = key;
      this.descText = descText;
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

   public void setKey(String key) { mKey = key; }

   public String getKey() { return mKey; }

   public String getDescText()
   {
      return descText;
   }

   public void setDescText(String descText)
   {
      this.descText = descText;
   }

}
