package adinh03.calpoly.edu.todolistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private ListView mListView;
   private Button submitButton;
   private EditText editText;
   private MyAdapter adapter;
   private ArrayList<EntryList> entryList;
   private ShareActionProvider mShareActionProvider;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      editText = (EditText) findViewById(R.id.entryText);
      editText.clearFocus();

      submitButton = (Button) findViewById(R.id.submitButton);

      mListView = (ListView) findViewById(R.id.listView);

      entryList = (ArrayList<EntryList>) getLastCustomNonConfigurationInstance();
      if (entryList == null)
         entryList = new ArrayList<>();

      adapter = new MyAdapter(entryList);

      mListView.setAdapter(adapter);

      submitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            String entry = editText.getText().toString();
            if (!entry.trim().isEmpty()) {
               addListEntry(entry);
            }
            editText.setText("");
         }

      });

      editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

         @Override
         public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN ||
                  i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT) {
               String entry = editText.getText().toString();
               if (!entry.trim().isEmpty()) {
                  addListEntry(entry);
               }

               editText.setText("");
            }
            return true;
         }
      });



   }

   String FormatStringsForIntent() {
      StringBuilder retString = new StringBuilder();

      for (int i = 0; i < entryList.size(); i++) {
         retString.append(entryList.get(i).getAddText() + "   " +
               Integer.toString(entryList.get(i).isChecked() ? 1 : 0) + "\n");
      }

      return retString.toString();
   }

   void addListEntry(String newText) {
      entryList.add(new EntryList(newText, false));
      //Log.d("DEBUG", "BEFORE NOTIFY SET CHANGED");
      adapter.notifyDataSetChanged();
   }

   @Override
   public Object onRetainCustomNonConfigurationInstance() {
      return entryList;
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      //return super.onCreateOptionsMenu(menu);
      getMenuInflater().inflate(R.menu.list_menu, menu);
      MenuItem item = menu.findItem(R.id.menu_share);
      item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
         @Override
         public boolean onMenuItemClick(MenuItem menuItem) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String formatedString = FormatStringsForIntent();
            // Log.d("DEBUG", formatedString);
            sendIntent.putExtra(Intent.EXTRA_TEXT,  formatedString);
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent, "Pick what to send with");
            startActivity(sendIntent);
            return true;
         }
      });
      return true;

   }

}
