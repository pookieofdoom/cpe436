package adinh03.calpoly.edu.todorecyclerview;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Anthony on 10/17/16.
 */

public class MyAdapter extends RecyclerView.Adapter<EntryViewHolder>
{
   private ArrayList<EntryList> mEntry;
   private boolean mTwoPane;
   private FragmentManager mManager;
   private int lastPosition = -1;
   private View lastView;
   private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("entries");

   public MyAdapter(ArrayList<EntryList> entry, boolean twoPane, FragmentManager manager)
   {
      //ask Tony if its a good idea to pass the bool and the manager
      //1.how to do select -> just change the background.. add description + isChecked in entry
      // and do it onbindviewholder
      //3.on swipe delete and deleting -> call finish on the activity -> menu stuff for deleting
      // -> doesn't deal with backstack
      mEntry = entry;
      mTwoPane = twoPane;
      mManager = manager;

   }

   @Override
   public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   {
      return new EntryViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,
            parent, false), mTwoPane, mManager);
   }

   @Override
   public void onBindViewHolder(final EntryViewHolder holder, final int position)
   {
      holder.itemView.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            if (mTwoPane)
            {

               if (lastPosition != -1 && lastPosition != position && lastView != null
                     && lastPosition < mEntry.size())
               {
                  //Log.d("DEBUG2", "THIS IS LAST POSITION " + lastPosition);
                  mEntry.get(lastPosition).setSelected(false);
                  mDatabase.child(mEntry.get(lastPosition).getKey()).setValue(mEntry.get
                        (lastPosition));
                  lastView.setSelected(mEntry.get(lastPosition).isSelected());
               }

               mEntry.get(position).setSelected(true);
               v.setSelected(mEntry.get(position).isSelected());
               lastPosition = position;
               lastView = v;


               StaticEntryList.getInstance().setEntry(mEntry);
               mDatabase.child(mEntry.get(position).getKey()).setValue(mEntry.get(position));

               //do fragment stuff here
               FragmentTransaction fragmentTransaction = mManager.beginTransaction();

               DetailFragment fragment = DetailFragment.newInstance(position);
               fragmentTransaction.replace(R.id.fragment_detail, fragment);
               //fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();


            }
            else
            {
               Intent i = new Intent(holder.itemView.getContext(), DetailActivity.class);
               i.putExtra("key", position);
               ((Activity) holder.itemView.getContext()).startActivityForResult(i, 1);
            }

         }
      });
      holder.checkBox.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            mEntry.get(position).setChecked(!mEntry.get(position).isChecked());
            mDatabase.child(mEntry.get(position).getKey()).setValue(mEntry.get(position));
            if (mTwoPane)
            {
               if (lastPosition != -1 && lastPosition != position && lastView != null
                     && lastPosition < mEntry.size())
               {
                  //Log.d("DEBUG2", "THIS IS LAST POSITION " + lastPosition);
                  mEntry.get(lastPosition).setSelected(false);
                  mDatabase.child(mEntry.get(lastPosition).getKey()).setValue(mEntry.get
                        (lastPosition));
                  lastView.setSelected(mEntry.get(lastPosition).isSelected());
               }

               mEntry.get(position).setSelected(true);
               v.setSelected(mEntry.get(position).isSelected());
               lastPosition = position;
               lastView = v;


               StaticEntryList.getInstance().setEntry(mEntry);
               mDatabase.child(mEntry.get(position).getKey()).setValue(mEntry.get(position));
               //do fragment stuff here
               FragmentTransaction fragmentTransaction = mManager.beginTransaction();
               //Log.d("DEBUG2", "this is position " + getAdapterPosition());
               DetailFragment fragment = DetailFragment.newInstance(position);
               fragmentTransaction.replace(R.id.fragment_detail, fragment);
               //fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
            }
         }
      });

      holder.bind(mEntry.get(position));
   }

   @Override
   public int getItemCount()
   {
      return mEntry.size();
   }

   @Override
   public int getItemViewType(int position)
   {
      return R.layout.todoentry;
   }
}
