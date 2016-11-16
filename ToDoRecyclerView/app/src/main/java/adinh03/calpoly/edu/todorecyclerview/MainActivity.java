package adinh03.calpoly.edu.todorecyclerview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

// trouble deleting stuff from top of list
public class MainActivity extends AppCompatActivity implements DetailFragment.FragmentInterface
{
   private RecyclerView mListView;
   private Button submitButton;
   private EditText editText;
   private MyAdapter myAdapter;
   private ArrayList<EntryList> entryList;
   private DatabaseReference mDatabase;
   private boolean mTwoPane;
   private FragmentManager mManager;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      editText = (EditText) findViewById(R.id.entryText);
      editText.clearFocus();

      submitButton = (Button) findViewById(R.id.submitButton);

      mListView = (RecyclerView) findViewById(R.id.recyclerView);
      mListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
            false));

      if (findViewById(R.id.fragment_detail) != null)
      {
         mTwoPane = true;
         mManager = getSupportFragmentManager();
      }
      else
      {
         mTwoPane = false;
         mManager = null;
      }

      entryList = (ArrayList<EntryList>) getLastCustomNonConfigurationInstance();
      if (entryList == null)
         entryList = new ArrayList<>();

      StaticEntryList.getInstance().setEntry(entryList);
      mDatabase = FirebaseDatabase.getInstance().getReference("entries");
      final ValueEventListener databaseListener = new ValueEventListener()
      {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot)
         {
            entryList.clear();
            //repopulating entryList
            if (entryList.isEmpty())
            {
               //Log.d("DEBUG", "REPOPULATING DATA FROM FIREBASE");
               for (DataSnapshot postSnapShot : dataSnapshot.getChildren())
               {
                  //Log.d("DEBUG", "Key is: " + postSnapShot.getKey());
                  //Log.d("DEBUG", "child count is" + Long.toString(postSnapShot.getChildrenCount
                  // ()));
                  //Log.d("Debug", "child is " + postSnapShot.getValue(EntryList.class)
                  // .getAddText());
                  EntryList a = postSnapShot.getValue(EntryList.class);
                  entryList.add(a);

               }

            }
            //updating values in the list
            else
            {
               //Log.d("DEBUG", "UPDATING VALUE FROM FIREBAE");
               for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
               {
                  //Log.d("DEBUG", postSnapshot.getValue(EntryList.class).getAddText());
                  entryList.set(Integer.parseInt(postSnapshot.getKey()), postSnapshot.getValue
                        (EntryList.class));

               }
            }


            myAdapter.notifyDataSetChanged();

         }


         @Override
         public void onCancelled(DatabaseError databaseError)
         {

         }
      };

      mDatabase.addValueEventListener(databaseListener);

      //mDatabase.child("entries").child("0").push().setValue("dafaq");

      myAdapter = new MyAdapter(entryList, mTwoPane, mManager);

      mListView.setAdapter(myAdapter);

      submitButton.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            String entry = editText.getText().toString();
            if (!entry.trim().isEmpty())
            {
               addListEntry(entry);
            }
            editText.setText("");
         }

      });

      editText.setOnEditorActionListener(new EditText.OnEditorActionListener()
      {

         @Override
         public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
         {
            if (i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN ||
                  i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || keyEvent
                  .getAction() == KeyEvent.KEYCODE_DPAD_CENTER || keyEvent.getAction() ==
                  KeyEvent.KEYCODE_ENTER)
            {
               String entry = editText.getText().toString();
               if (!entry.trim().isEmpty())
               {
                  addListEntry(entry);
               }

               editText.setText("");
            }
            return true;
         }
      });


      ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT)
      {
         @Override
         public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                               RecyclerView.ViewHolder target)
         {
            return false;
         }

         @Override
         public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction)
         {
            final int index = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.RIGHT)
            {
               //need to delete the internal storage and rename all the files inside
               //Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
               deleteEntry(index);
            }

         }


      };

      ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
      itemTouchHelper.attachToRecyclerView(mListView);
   }


   void addListEntry(String newText)
   {


      String key = mDatabase.push().getKey();
      entryList.add(new EntryList(newText, false, key, "", false));
      mDatabase.child(key).setValue(entryList.get(entryList.size() - 1));
      myAdapter.notifyDataSetChanged();
   }

   public Object onRetainCustomNonConfigurationInstance()
   {
      return entryList;
   }

   String FormatStringsForIntent()
   {
      StringBuilder retString = new StringBuilder();

      for (int i = 0; i < entryList.size(); i++)
      {
         retString.append(entryList.get(i).getAddText() + "   " +
               Integer.toString(entryList.get(i).isChecked() ? 1 : 0) + "\n");
      }

      return retString.toString();
   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      super.onCreateOptionsMenu(menu);
      getMenuInflater().inflate(R.menu.list_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      //return super.onOptionsItemSelected(item);
      switch (item.getItemId())
      {
         case R.id.menu_share:

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String formatedString = FormatStringsForIntent();
            // Log.d("DEBUG", formatedString);
            sendIntent.putExtra(Intent.EXTRA_TEXT, formatedString);
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent, "Pick what to send with");

            startActivity(sendIntent);

            return true;
         case R.id.menu_remove:
            return false;
         default:
            break;
      }
      return false;
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      super.onActivityResult(requestCode, resultCode, data);

      if (resultCode == RESULT_OK)
      {
         if (requestCode == 1)
         {
            int index = data.getIntExtra("key2", -1);
            //Log.d("DEBUG", "main activity got values " + StaticEntryList.getInstance()
            // .getEntry(index).getAddText() + " back.");
            entryList.set(index, StaticEntryList.getInstance().getEntry(index));
            String key = entryList.get(index).getKey();
            mDatabase.child(key).setValue(entryList.get(index));
            myAdapter.notifyItemChanged(index);
         }

      }
      else if (resultCode == 69)
      {
         deleteEntry(data.getIntExtra("key2", -1));
      }
   }


   @Override
   public void submitChanges(int index)
   {
      //notify adapter changes here
      entryList.set(index, StaticEntryList.getInstance().getEntry(index));
      String key = entryList.get(index).getKey();
      mDatabase.child(key).setValue(entryList.get(index));
      myAdapter.notifyItemChanged(index);

   }

   @Override
   public void deleteEntry(final int index)
   {
      AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
      alert.setMessage("Do you really want to delete this 4 ever?");
      alert.setCancelable(true);
      alert.setPositiveButton("Delete", new DialogInterface.OnClickListener()
      {
         @Override
         public void onClick(DialogInterface dialogInterface, int i)
         {
            String key = entryList.get(index).getKey();
            entryList.remove(index);
            StaticEntryList.getInstance().setEntry(entryList);
            myAdapter.notifyItemRemoved(index);
            File input = new File(getFilesDir() + "/savedImage" + index);
            input.delete();
            //change the index number
            //Log.d("DEBUG", "index is " + index);
            for (int num = index + 1; num < entryList.size() + 1; num++)
            {
               //Log.d("DEBUG", "IM UPDATING THE SAVED IMAGES");
               File fixFileName = new File(getFilesDir() + "/savedImage" + num);
               File newFileName = new File(getFilesDir() + "/savedImage" + (num - 1));
               fixFileName.renameTo(newFileName);
            }
            //remove from firebase but update the keys
            mDatabase.child(key).removeValue();

            if (mTwoPane && getSupportFragmentManager().findFragmentById(R.id
                  .fragment_detail) != null)
            {
               getSupportFragmentManager().beginTransaction().remove
                     (getSupportFragmentManager()
                           .findFragmentById(R.id.fragment_detail)).commit();
            }

         }
      });
      alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
      {
         @Override
         public void onClick(DialogInterface dialogInterface, int i)
         {
            dialogInterface.cancel();
            myAdapter.notifyItemChanged(index);
         }
      });
      AlertDialog dialog = alert.create();
      dialog.show();
   }
}
