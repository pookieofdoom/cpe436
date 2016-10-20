package adinh03.calpoly.edu.todorecyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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

public class DetailActivity extends AppCompatActivity {
   private EditText mEditText;
   private CheckBox mCheckBox;
   private Button mSubmitButton;
   private ImageView mImageView;
   private int imageId;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail);

      mEditText = (EditText) findViewById(R.id.editText);
      mCheckBox = (CheckBox) findViewById(R.id.detailBox);
      mSubmitButton = (Button) findViewById(R.id.changeButton);
      mImageView = (ImageView) findViewById(R.id.imageDescription);

      final int index = getIntent().getIntExtra("key", -1);
      imageId = index;
      Log.d("DEBUG", "this is index " + index);
      mEditText.setText(StaticEntryList.getInstance().getEntry(index).getAddText());

      mCheckBox.setChecked(StaticEntryList.getInstance().getEntry(index).isChecked());

      try {
         File input = new File(getFilesDir() + "/savedImage" + imageId);
         Log.d("DEBUG", input.getAbsolutePath());
         Bitmap b = BitmapFactory.decodeStream(new FileInputStream(input));
         mImageView.setImageBitmap(b);
      }
      catch (FileNotFoundException e) {
         Log.d("DEBUG", "FILE NOT FOUND");

      }

      mSubmitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            StaticEntryList.getInstance().getEntry(index).setAddText(mEditText.getText().toString());
            StaticEntryList.getInstance().getEntry(index).setChecked(mCheckBox.isChecked());
            Intent i = new Intent();
            i.putExtra("key2", index);
            setResult(RESULT_OK, i);
            finish();
         }
      });


      mImageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            imageIntent.setType("image/*");
            startActivityForResult(imageIntent, 2);
         }
      });

      mImageView.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
            //bug where it will treat it as a click right after, so drag your finger up first
            mImageView.setImageResource(0);
            return false;
         }
      });

   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //super.onActivityResult(requestCode, resultCode, data);
      if (resultCode == RESULT_OK) {
         if (requestCode == 2) {
            Log.d("DEBUG", "Picked an image");
            Uri uri = data.getData();
            mImageView.setImageURI(uri);
            try {
               //getContentResolver().openOutputStream(uri);
               File internalStorageDir = getFilesDir();
               File writeTo = new File(internalStorageDir, "savedImage" + imageId);
               OutputStream os = null;
               os = new FileOutputStream(writeTo);
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
               os.flush();
               os.close();

            }
            catch (FileNotFoundException e) {
               e.printStackTrace();
            }
            catch (IOException e) {
               e.printStackTrace();
            }

         }
      }
      else {
         //remove image when pressing back
         mImageView.setImageResource(0);
      }
   }

}
