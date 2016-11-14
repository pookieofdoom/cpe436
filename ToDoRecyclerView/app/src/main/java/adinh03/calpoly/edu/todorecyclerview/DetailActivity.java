package adinh03.calpoly.edu.todorecyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DetailActivity extends AppCompatActivity implements DetailFragment.FragmentInterface{

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail);
      Bundle bundle = new Bundle();
      final int index = getIntent().getIntExtra("key", -1);
      bundle.putInt("key", index);

      FragmentManager manager = getSupportFragmentManager();
      DetailFragment fragment = (DetailFragment) manager.findFragmentById(R.id.fragment_detail);


      if (fragment == null)
      {
         FragmentTransaction fragmentTransaction = manager.beginTransaction();
         fragment = DetailFragment.newInstance(index);
         fragmentTransaction.add(R.id.fragment_detail, fragment);
         fragmentTransaction.addToBackStack(null);
         fragmentTransaction.commit();
      }
      //720 dp for fragment

   }


   @Override
   public void submitChanges(int index)
   {
      Intent i = new Intent();
      i.putExtra("key2", index);
      //this will be in the detail activtiy, this will change to fragment contract
      setResult(RESULT_OK, i);
      finish();
   }
}
