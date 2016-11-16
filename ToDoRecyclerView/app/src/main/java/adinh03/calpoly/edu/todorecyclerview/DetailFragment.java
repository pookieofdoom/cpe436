package adinh03.calpoly.edu.todorecyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Anthony on 11/7/16.
 */

public class DetailFragment extends ContractFragment<DetailFragment.FragmentInterface>
{
   public static DetailFragment newInstance(int index)
   {
      DetailFragment fragment = new DetailFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("key", index);
      fragment.setArguments(bundle);
      return fragment;
   }

   public interface FragmentInterface
   {
      void submitChanges(int index);
      void deleteEntry(int index);
   }

   private EditText mEditText;
   private CheckBox mCheckBox;
   private Button mSubmitButton;
   private ImageView mImageView;
   private EditText mDesc;
   private int imageId;
   private int index;

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setHasOptionsMenu(true);
   }

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
         Bundle savedInstanceState)
   {
      //return super.onCreateView(inflater, container, savedInstanceState);
      View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
      mEditText = (EditText) rootView.findViewById(R.id.editText);
      mCheckBox = (CheckBox) rootView.findViewById(R.id.detailBox);
      mSubmitButton = (Button) rootView.findViewById(R.id.changeButton);
      mImageView = (ImageView) rootView.findViewById(R.id.imageDescription);
      mDesc = (EditText) rootView.findViewById(R.id.editDesc);

      //final int index = getIntent().getIntExtra("key", -1);
      //get index using fragment contract
      index = this.getArguments().getInt("key");
      imageId = index;
      //Log.d("DEBUG", "this is index " + index);
      mEditText.setText(StaticEntryList.getInstance().getEntry(index).getAddText());

      mCheckBox.setChecked(StaticEntryList.getInstance().getEntry(index).isChecked());

      if (!StaticEntryList.getInstance().getEntry(index).getDescText().equals(""))
      {
         mDesc.setText(StaticEntryList.getInstance().getEntry(index).getDescText());
      }

      try
      {
         File input = new File(getContext().getFilesDir() + "/savedImage" + imageId);
         Bitmap b = BitmapFactory.decodeStream(new FileInputStream(input));
         mImageView.setImageBitmap(b);
      } catch (FileNotFoundException e)
      {
         //do nothing
      }

      mSubmitButton.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            StaticEntryList.getInstance().getEntry(index).setAddText(mEditText.getText().toString
                  ());
            StaticEntryList.getInstance().getEntry(index).setChecked(mCheckBox.isChecked());
            StaticEntryList.getInstance().getEntry(index).setDescText(mDesc.getText().toString());
            getContract().submitChanges(index);

         }
      });


      mImageView.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            imageIntent.setType("image/*");
            startActivityForResult(imageIntent, 2);
         }
      });

      mImageView.setOnLongClickListener(new View.OnLongClickListener()
      {
         @Override
         public boolean onLongClick(View v)
         {
            //bug where it will treat it as a click right after, so drag your finger up first
            mImageView.setImageResource(0);
            return false;
         }
      });
      return rootView;
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      super.onActivityResult(requestCode, resultCode, data);
      if (resultCode == RESULT_OK)
      {
         if (requestCode == 2)
         {
            Uri uri = data.getData();
            mImageView.setImageURI(uri);
            try
            {
               //getContentResolver().openOutputStream(uri);
               File internalStorageDir = getContext().getFilesDir();
               File writeTo = new File(internalStorageDir, "savedImage" + imageId);
               OutputStream os = null;
               os = new FileOutputStream(writeTo);
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver
                     (), uri);
               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
               os.flush();
               os.close();

            } catch (FileNotFoundException e)
            {
               e.printStackTrace();
            } catch (IOException e)
            {
               e.printStackTrace();
            }

         }
      }
      else
      {
         //remove image when pressing back
         File input = new File(getContext().getFilesDir() + "/savedImage" + imageId);
         input.delete();
         mImageView.setImageResource(0);
      }
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
   {
      //inflater.inflate(R.menu.list_menu, menu);
      //
      super.onCreateOptionsMenu(menu, inflater);
      menu.findItem(R.id.menu_remove).setVisible(true);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      switch (item.getItemId())
      {
         case R.id.menu_share:
            return false;
         case R.id.menu_remove:
            getContract().deleteEntry(index);
            return true;
         default:
            break;
      }
      return false;
   }
}
