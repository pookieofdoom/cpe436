package adinh03.calpoly.edu.todorecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

public class DetailActivity extends AppCompatActivity implements DetailFragment.FragmentInterface{

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail);
      final int index = getIntent().getIntExtra("key", -1);

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

   @Override
   public void deleteEntry(int index)
   {
      Intent i = new Intent();
      i.putExtra("key2", index);
      //this will be in the detail activtiy, this will change to fragment contract
      setResult(69, i);
      finish();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      super.onCreateOptionsMenu(menu);
      getMenuInflater().inflate(R.menu.list_menu, menu);
      return true;
   }
}
