package adinh03.calpoly.edu.todolistview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anthony on 10/10/16.
 */

public class MyAdapter extends BaseAdapter{
   private ArrayList<EntryList> mEntry;

   public MyAdapter(ArrayList<EntryList> entry) {
      mEntry = entry;
   }
   @Override
   public int getCount() {
      return mEntry.size();
   }

   @Override
   public Object getItem(int i) {
      return mEntry.get(i);
   }

   @Override
   public long getItemId(int i) {
      return 0;
   }

   @Override
   public View getView(int i, View view, final ViewGroup viewGroup) {
      //Log.d("DEBUG", "IN GET VIEW");
      final EntryList entry = (EntryList) getItem(i);

      if (view == null) {
         //inflate logic
         LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
         view = inflater.inflate(R.layout.todoentry, viewGroup, false);
      }

      TextView addText = (TextView) view.findViewById(R.id.newText);
      CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
      checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            entry.setChecked(isChecked);
         }
      });
      addText.setText(entry.getAddText());
      checkBox.setChecked(entry.isChecked());
      //Log.d("DEBUG", addText.getText().toString());
      view.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
            AlertDialog.Builder alert = new AlertDialog.Builder(viewGroup.getContext());
            alert.setMessage("Do you really want to delete ( " + entry.getAddText()
                  + " ) 4 ever?");
            alert.setCancelable(true);
            alert.setPositiveButton("Yup", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                  mEntry.remove(entry);
                  notifyDataSetChanged();
               }
            });
            alert.setNegativeButton("Pls No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                  dialogInterface.cancel();
               }
            });
            AlertDialog dialog = alert.create();
            dialog.show();

            return true;
         }
      });
      return view;
   }
}
