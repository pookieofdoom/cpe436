package adinh03.calpoly.edu.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ImageView iv = (ImageView) findViewById(R.id.image1);
        //Picasso.with(this).load(R.drawable.anthonydinhimage).into(iv);
    }
}
