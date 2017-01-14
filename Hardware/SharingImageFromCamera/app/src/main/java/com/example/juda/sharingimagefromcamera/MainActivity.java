package com.example.juda.sharingimagefromcamera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    public static final String OUTPUTFILE_URI = "outputfileUri";
    public static final String FILE = "file";
    public static final int REQUEST_CODE = 123;
    Uri outputFileUri;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //saving the file details for any orientation device change
        if(savedInstanceState != null){
            outputFileUri = Uri.parse(savedInstanceState.getString(OUTPUTFILE_URI));
            file = new File(savedInstanceState.getString(FILE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(OUTPUTFILE_URI, outputFileUri.toString());
        outState.putString(FILE, file.toString());

    }

    public void btnTakePhoto(View view) {//taking a picture

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//opening the default camera
        File externalStorageDirectory = Environment.getExternalStorageDirectory();//file's directory
        file = new File(externalStorageDirectory, "MyPhoto.jpg");//creation of the new file
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);//set the image file name
        startActivityForResult(intent, REQUEST_CODE);//starting the activity for the result of the photo
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//here we are handling the result of our intent
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(file.exists()){
                new AsyncTask<Void, Void, Boolean>(){

                    @Override
                    protected Boolean doInBackground(Void... params) {
                        HttpURLConnection urlConnection = null;
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        try{
                            URL url = new URL("http://10.0.2.2:8080/MainServlet");
                            InputStream fileInputStream = new FileInputStream(file);
                            byte[] buffer = new byte[1024];
                            int actuallyReadFromFile;
                            while((actuallyReadFromFile = fileInputStream.read(buffer)) != -1){
                                outputStream.write(buffer, 0, actuallyReadFromFile);
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                    }
                }.execute();
            }
        }
    }
}
