package adinh03.calpoly.edu.todolistview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
   public View getView(int i, View view, ViewGroup viewGroup) {
      EntryList entry = (EntryList) getItem(i);

      if (view == null) {
         //inflate logic
         LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
         view = inflater.inflate(R.layout.todoentry, viewGroup, false);
      }
      TextView addText = (TextView) view.findViewById(R.id.newText);
      CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
      addText.setText(entry.getAddText());
      return view;
   }
}
