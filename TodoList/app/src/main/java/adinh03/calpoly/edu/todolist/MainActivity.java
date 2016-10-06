package adinh03.calpoly.edu.todolist;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private LinearLayout mList;
   private Button submitButton;
   private EditText editText;
   private LayoutInflater inflater;
   private ArrayList<EntryList> entryList;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      editText = (EditText) findViewById(R.id.entryText);
      submitButton = (Button) findViewById(R.id.submitButton);
      inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      mList = (LinearLayout) findViewById(R.id.list_layout);
      entryList = (ArrayList<EntryList>) getLastCustomNonConfigurationInstance();

      if (entryList == null) {
         entryList = new ArrayList<EntryList>();
      } else {
         //repopulate the todo list
         repopulateEntry();

      }

      submitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            String entry = editText.getText().toString();
            if (!entry.isEmpty()) {
               addListEntry(entry);
            }
            editText.setText("");
         }

      });

      editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

         @Override
         public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
               String entry = editText.getText().toString();
               if (!entry.isEmpty()) {
                  addListEntry(entry);
               }

               editText.setText("");
            }
            return true;
         }
      });


   }

   void addListEntry(String newText) {
      LayoutInflater inflater = LayoutInflater.from(this);
      View v = inflater.inflate(R.layout.todoentry, mList, false);
      TextView addText = (TextView) v.findViewById(R.id.newText);
      CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
      addText.setText(newText);
      mList.addView(v);
   }

   void repopulateEntry() {
      LayoutInflater inflater;
      View v;

      for (int i = 0; i < entryList.size(); i++) {

         inflater = LayoutInflater.from(this);
         v = inflater.inflate(R.layout.todoentry, mList, false);
         TextView addText = (TextView) v.findViewById(R.id.newText);
         CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
         addText.setText(entryList.get(i).getAddText());
         checkBox.setChecked(entryList.get(i).isChecked());
         Log.d("DEBUG", "text = " + addText.getText() + " and check is: " + checkBox.isChecked());
         mList.addView(v);
      }
      entryList.clear();
   }

   void saveToEntryList() {
      for (int i = 0; i < ((ViewGroup) mList).getChildCount(); i++) {
         View nextChild = ((ViewGroup) mList).getChildAt(i);
         TextView addText = (TextView) nextChild.findViewById(R.id.newText);
         CheckBox checkBox = (CheckBox) nextChild.findViewById(R.id.checkBox);
         entryList.add(new EntryList(addText.getText().toString(), checkBox.isChecked()));

      }

   }

   @Override
   public Object onRetainCustomNonConfigurationInstance() {
      //save the current state of all checkboxes
      saveToEntryList();
      return entryList;
   }
}
