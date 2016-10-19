package adinh03.calpoly.edu.todorecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Anthony on 10/17/16.
 */

public class EntryViewHolder extends RecyclerView.ViewHolder {
   private TextView addText;
   private CheckBox checkBox;

   public EntryViewHolder(View itemView) {
      super(itemView);
      addText = (TextView) itemView.findViewById(R.id.newText);
      checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
   }

   public void bind(final EntryList entry) {
      checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            entry.setChecked(isChecked);
         }
      });
      addText.setText(entry.getAddText());
      checkBox.setChecked(entry.isChecked());
   }
}
