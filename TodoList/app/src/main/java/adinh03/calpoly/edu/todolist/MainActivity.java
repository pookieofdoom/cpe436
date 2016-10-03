package adinh03.calpoly.edu.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
                }
            }
        });

    }
}
