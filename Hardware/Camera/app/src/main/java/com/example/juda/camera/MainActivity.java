package com.example.juda.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String OUTPUT_FILE_URI = "outputFileUri";
    public static final String FILE = "file";
    public static final int REQUEST_CODE = 123;
    Uri outputFileUri;
    File file;

    //int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){

            //counter = savedInstanceState.getInt("counter");
            //((EditText)findViewById(R.id.txt)).setText(savedInstanceState.getString("txt"));


            outputFileUri = Uri.parse(savedInstanceState.getString(OUTPUT_FILE_URI));//address for interface of URI
            file = new File(savedInstanceState.getString(FILE));
        }
        //Log.d("Juda", String.valueOf(counter++));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //savedInstanceState.putInt("counter", counter);
        //savedInstanceState.putString("txt",
        //        ((EditText)findViewById(R.id.txt)).getText().toString());

        //if I'll turn the screen, then the information of these keys will save.
        savedInstanceState.putString(OUTPUT_FILE_URI, outputFileUri.toString());
        savedInstanceState.putString(FILE, file.toString());
    }


    public void btnTakePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//Intent who will present our photo
        File extStrgDir = Environment.getExternalStorageDirectory();//file refers to file or to folder (directory)
        Log.d("Juda", extStrgDir.toString());
        file = new File(extStrgDir, "MyPhoto.jpg");//creation of a new file for my photo
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showImage();
            }
        }
    }

    private void showImage(){
        ImageView imgPhoto = (ImageView)findViewById(R.id.imgPhoto);
        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
        imgPhoto.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if (file.exists()){
                Log.d("Juda", "file exista at " + outputFileUri.toString());
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                }else {
                    showImage();
                }
            }else Log.d("Juda", "file doesn't exist");
        }
    }
}
