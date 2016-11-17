package adinh03.calpoly.edu.todorecyclerview;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anthony on 10/17/16.
 */

public class EntryViewHolder extends RecyclerView.ViewHolder
{
   private TextView addText;
   public CheckBox checkBox;
   private DatabaseReference mDatabase;
   private boolean mTwoPane;
   private FragmentManager mManager;
   //private View mView;

   public EntryViewHolder(final View itemView, final boolean twoPane, final FragmentManager manager)
   {
      super(itemView);
      addText = (TextView) itemView.findViewById(R.id.newText);
      checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
      mDatabase = FirebaseDatabase.getInstance().getReference("entries");
      //itemView.setClickable(true);
      mTwoPane = twoPane;
      mManager = manager;

   }

   public void bind(final EntryList entry)
   {
      if (mTwoPane)
         itemView.setSelected(entry.isSelected());



      addText.setText(entry.getAddText());
      checkBox.setChecked(entry.isChecked());
   }


}
