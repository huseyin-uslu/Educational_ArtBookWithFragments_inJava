package com.firstprojects.artbook_with_navigation_and_fragments.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.firstprojects.artbook_with_navigation_and_fragments.MainActivity;
import com.firstprojects.artbook_with_navigation_and_fragments.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class SecondFragment extends Fragment {



    public SecondFragment() {
        // Required empty public constructor
    }



    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
if(getArguments() != null) {
    index = SecondFragmentArgs.fromBundle(getArguments()).getİd() + 1;
    checkview = SecondFragmentArgs.fromBundle(getArguments()).getİsItnew();
}
if(checkview) {
    return inflater.inflate(R.layout.fragment_second, container, false);
}else {
    View view = inflater.inflate(R.layout.fragment_second_secondview, container, false);

    return view;
}


    }
    int index;
    boolean checkview;
    EditText editText_name;
    EditText editText_place;
    EditText editText_date;
    TextView textView_name,textView_place,textView_date;
    ImageView imageView;
    Button button;
    Bitmap bitmap;
    SQLiteDatabase sqlite;
    byte[] bytes;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.layout_fragment2nd_ViewGroup_imageView_selectedImage);

        if(checkview) {
            imageView.setClickable(true);
        editText_name = view.findViewById(R.id.layout_fragment2nd_ViewGroup_editText_picturesName);
        editText_place = view.findViewById(R.id.layout_fragment2nd_ViewGroup_editText_place);
        editText_date = view.findViewById(R.id.layout_fragment2nd_ViewGroup_editText_date);
        button = view.findViewById(R.id.layout_fragment2nd_ViewGroup_button_save);
        //USING SQLITEDATABASE
        try {
            sqlite = requireActivity().openOrCreateDatabase("Arts", Context.MODE_PRIVATE, null);
            sqlite.execSQL("CREATE TABLE IF NOT EXISTS pictures(id INTEGER PRIMARY KEY,name VANCHAR,place VANCHAR,date VANCHAR,image BLOB)");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("YOU'VE GOT A PROBLEM = " +e.getLocalizedMessage());
        }

            imageView.setClickable(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageSelectImage(v);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setClickable(true);

                    saveButton(v);

                }
            });
        }else {
            imageView.setClickable(false);
            textView_name = view.findViewById(R.id.layout_fragment2nd_ViewGroup_textView_picturesNametextView);
            textView_place = view.findViewById(R.id.layout_fragment2nd_ViewGroup_textView_placeName);
            textView_date = view.findViewById(R.id.layout_fragment2nd_ViewGroup_textView_date);
            try {
                sqlite = requireActivity().openOrCreateDatabase("Arts", Context.MODE_PRIVATE, null);
                sqlite.execSQL("CREATE TABLE IF NOT EXISTS pictures(id INTEGER PRIMARY KEY,name VANCHAR,place VANCHAR,date VANCHAR,image BLOB)");
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("YOU'VE GOT A PROBLEM = " +e.getLocalizedMessage());
            }
            Cursor cursor = sqlite.rawQuery("SELECT * FROM pictures WHERE id = ?",new String[] {String.valueOf(index)});
            int nameIX = cursor.getColumnIndex("name");
            int placeIX = cursor.getColumnIndex("place");
            int dateIX = cursor.getColumnIndex("date");
            int imageIX = cursor.getColumnIndex("image");

            while(cursor.moveToNext()) {
                textView_name.setText(cursor.getString(nameIX));
                textView_place.setText(cursor.getString(placeIX));
                textView_date.setText(cursor.getString(dateIX));
                byte[] bytes = cursor.getBlob(imageIX);
                bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
            }

        }

    }

    public void saveButton(View view) {
        //Get Data from 2nd Fragment
       if(bitmap != null) {
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           boolean checkCompress = bitmap.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
           if(checkCompress) {
               bytes = byteArrayOutputStream.toByteArray();
               System.out.println("SUCCESFULL!");
           }

       }

    if(editText_name.getText() != null && editText_place.getText() != null && editText_date.getText() != null) {
        try {
    SQLiteStatement sqLiteStatement = sqlite.compileStatement("INSERT INTO pictures(name,place,date,image) VALUES(?,?,?,?)");
    sqLiteStatement.bindString(1, editText_name.getText().toString());
    sqLiteStatement.bindString(2, editText_place.getText().toString());
    sqLiteStatement.bindString(3, editText_date.getText().toString());
    sqLiteStatement.bindBlob(4, bytes);
    sqLiteStatement.execute();
    System.out.println("SUCCESFULL SAVED");
}catch (Exception e) {
    e.printStackTrace();
    System.out.println("YOU'VE GOT A PROBLEM = " + e.getLocalizedMessage());
}}else {
        Toast.makeText(requireActivity().getApplicationContext(), "FILL BLANKS", Toast.LENGTH_LONG).show();
        }

        //
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        NavDirections navDirections = SecondFragmentDirections.actionSecondFragmentToFirstFragment();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        navController.navigate(navDirections);
    }
    public void onImageSelectImage(View view) {
        try{
        if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == -1 && data !=null) { //resultcode -1 = RESULT_OK

            Uri dataUri = data.getData();

            if(Build.VERSION.SDK_INT >= 28) {
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(),dataUri);
                try {
                    bitmap = ImageDecoder.decodeBitmap(source);
                    bitmap = SmallerImage(bitmap,500);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),dataUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap = SmallerImage(bitmap,500);
                imageView.setImageBitmap(bitmap);
            }
            imageView.setClickable(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults.length > 0){
        Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentToGallery,2);
    }}

    public Bitmap SmallerImage(Bitmap bitmap,int maxSize) {
        int height = bitmap.getHeight();
        int width  = bitmap.getWidth();
        float rate = (float)height / (float)width;
        if(rate < 1) {
            width = maxSize;
            height = (int)(maxSize * rate);
        }else {
            height = maxSize;
            width = (int)(maxSize / rate);
        }
       bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
        return bitmap;
    }
}