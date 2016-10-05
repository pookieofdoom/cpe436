package adinh03.calpoly.edu.todolist;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
   private LinearLayout list;
   private Button submitButton;
   private EditText editText;
   private LayoutInflater inflater;
   private TextView addText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       editText = (EditText) findViewById(R.id.entryText);
       submitButton = (Button) findViewById(R.id.submitButton);
       inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       String entry;
       list = (LinearLayout) findViewById(R.id.list);
       submitButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             String entry = editText.getText().toString();
             editText.setText("");
             if (!entry.isEmpty()) {
                LinearLayout temp = (LinearLayout) inflater.inflate(R.layout.todoentry, null);
                addText = (TextView) temp.findViewById(R.id.newText);
                addText.setText(entry);
                list.addView(temp);
             }
          }

       });
    }

}
