package adinh03.calpoly.edu.todorecyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
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

public class MyAdapter extends RecyclerView.Adapter<EntryViewHolder>
{
   private ArrayList<EntryList> mEntry;
   private boolean mTwoPane;
   private FragmentManager mManager;

   public MyAdapter(ArrayList<EntryList> entry, boolean twoPane, FragmentManager manager)
   {
      //ask Tony if its a good idea to pass the bool and the manager
      //how to do select
      //on swipe delete and deleting
      // additonal field 
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
   public void onBindViewHolder(EntryViewHolder holder, int position)
   {
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
