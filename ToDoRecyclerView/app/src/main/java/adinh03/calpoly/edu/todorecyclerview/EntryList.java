package adinh03.calpoly.edu.todorecyclerview;

/**
 * Created by Anthony on 10/17/16.
 */

public class EntryList
{
   private String addText;
   private boolean isChecked;
   private String mKey;
   private String descText;
   private boolean isSelected;

   public EntryList()
   {

   }

   public EntryList(String addText, boolean isChecked, String key, String descText, boolean
         isSelected)
   {
      this.addText = addText;
      this.isChecked = isChecked;
      mKey = key;
      this.descText = descText;
      this.isSelected = isSelected;
   }

   public String getAddText()
   {
      return addText;
   }

   public void setAddText(String addText)
   {
      this.addText = addText;
   }

   public boolean isChecked()
   {
      return isChecked;
   }

   public void setChecked(boolean checked)
   {
      isChecked = checked;
   }

   public void setKey(String key)
   {
      mKey = key;
   }

   public String getKey()
   {
      return mKey;
   }

   public String getDescText()
   {
      return descText;
   }

   public void setDescText(String descText)
   {
      this.descText = descText;
   }

   public boolean isSelected()
   {
      return isSelected;
   }

   public void setSelected(boolean isSelected)
   {
      this.isSelected = isSelected;
   }
}
