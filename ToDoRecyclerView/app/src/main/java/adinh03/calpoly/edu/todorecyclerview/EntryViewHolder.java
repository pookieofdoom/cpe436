package adinh03.calpoly.edu.todorecyclerview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anthony on 10/17/16.
 */

public class EntryViewHolder extends RecyclerView.ViewHolder {
   private TextView addText;
   private CheckBox checkBox;
   private DatabaseReference mDatabase;

   public EntryViewHolder(final View itemView) {
      super(itemView);
      addText = (TextView) itemView.findViewById(R.id.newText);
      checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
      mDatabase = FirebaseDatabase.getInstance().getReference("entries");
      itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent i = new Intent(itemView.getContext(), DetailActivity.class);
            i.putExtra("key", getAdapterPosition());
            ((Activity)itemView.getContext()).startActivityForResult(i, 1);
         }
      });

   }

   public void bind(final EntryList entry) {
      checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            entry.setChecked(isChecked);
            mDatabase.child(Integer.toString(getAdapterPosition())).setValue(entry);

         }
      });
      addText.setText(entry.getAddText());
      checkBox.setChecked(entry.isChecked());
   }

}
