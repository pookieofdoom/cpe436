package adinh03.calpoly.edu.todorecyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anthony on 10/17/16.
 */

public class MyAdapter extends RecyclerView.Adapter<EntryViewHolder>{
   private ArrayList<EntryList> mEntry;

   public MyAdapter(ArrayList<EntryList> entry) {
      mEntry = entry;
   }

   @Override
   public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new EntryViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
   }

   @Override
   public void onBindViewHolder(EntryViewHolder holder, int position) {
      holder.bind(mEntry.get(position));
   }

   @Override
   public int getItemCount() {
      return mEntry.size();
   }

   @Override
   public int getItemViewType(int position) {
      return R.layout.todoentry;
   }
}
