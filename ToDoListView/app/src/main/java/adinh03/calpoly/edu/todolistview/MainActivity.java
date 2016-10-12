package adinh03.calpoly.edu.todolistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private ListView mListView;
   private Button submitButton;
   private EditText editText;
   private MyAdapter adapter;
   private ArrayList<EntryList> entryList;

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
                  i == EditorInfo.IME_ACTION_DONE) {
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

   void addListEntry(String newText) {
      entryList.add(new EntryList(newText, false));
      //Log.d("DEBUG", "BEFORE NOTIFY SET CHANGED");
      adapter.notifyDataSetChanged();
   }

   @Override
   public Object onRetainCustomNonConfigurationInstance() {
      return entryList;
   }

}
