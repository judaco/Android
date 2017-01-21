package com.example.juda.shareimagefromcamera;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity implements DownloadImageThread.NewImageListener {

    public static final String OUTPUTFILE_URI = "outputfileUri";
    public static final String FILE = "file";
    public static final int REQUEST_CODE = 123;
    //public static final String BASE_URL = "http://10.0.2.2:8080/MainServlet";
    public static final String BASE_URL = "http://192.168.1.104:8080/MainServlet";
    Uri outputFileUri;
    DownloadImageThread thread;
    File file;
    File fileDownloadedImage;
    Handler handler = new Handler();
    ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPhoto = (ImageView)findViewById(R.id.imgPhoto);

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

    public void btnTakePhoto(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        file = new File(externalStorageDirectory, "MyPhoto.jpg");
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                uploadImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                    return;
                }
            }
            uploadImage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fileDownloadedImage = new File(getCacheDir(), "DownloadImage.jpg");
        thread = new DownloadImageThread(fileDownloadedImage, this);
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(thread != null){
            thread.stopDownloading();
            thread = null;
        }

    }

    private void uploadImage() {
        if(file.exists()){
            new AsyncTask<Void, Void, Boolean>(){

                @Override
                protected Boolean doInBackground(Void... params) {
                    Boolean success = false;
                    HttpURLConnection urlConnection = null;
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try{
                        URL url = new URL(BASE_URL);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setUseCaches(false);
                        urlConnection.setDoOutput(true);
                        urlConnection.connect();
                        outputStream = urlConnection.getOutputStream();
                        InputStream fileInputStream = new FileInputStream(file);
                        byte[] buffer = new byte[1024];
                        int actuallyReadFromFile;
                        while((actuallyReadFromFile = fileInputStream.read(buffer)) != -1){
                            outputStream.write(buffer, 0, actuallyReadFromFile);
                        }
                        inputStream = urlConnection.getInputStream();
                        int actuallyRead = inputStream.read(buffer);
                        String responseFromServer = new String(buffer, 0, actuallyRead);
                        if(responseFromServer.equals("ok"))
                            success = true;
                        else{
                            Log.d("Juda", "responseFromServer="+responseFromServer);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return success;
                }

                @Override
                protected void onPostExecute(Boolean success) {
                    Log.d("Juda", "success="+success);
                    if(success)
                        Toast.makeText(MainActivity.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "error uploading", Toast.LENGTH_SHORT).show();
                }
            }.execute();
        }
    }

    @Override
    public void onNewImage() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeFile(fileDownloadedImage.toString());
                imgPhoto.setImageBitmap(bitmap);
            }
        });
    }
}
