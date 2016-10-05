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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.entryText);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        String entry;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entry = editText.getText().toString();
                if (!entry.isEmpty())
                {
                   Log.d("DEBUG", entry);

                   Log.d("DEBUG", "I made it here");

                }
            }
        });

       LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       LinearLayout list = (LinearLayout) findViewById(R.id.list);
       TextView temp = (TextView)inflater.inflate(R.layout.todoentry, null);
       //list.addView(temp);
       list.addView(temp, LinearLayout.LayoutParams.MATCH_PARENT);
       Log.d("DEBUG", list.getChildAt(0).toString());
    }
}
