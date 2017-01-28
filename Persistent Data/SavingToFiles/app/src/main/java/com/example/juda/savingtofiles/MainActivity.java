package com.example.juda.savingtofiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OutputStream outputStream = null;
        try{
            //saving to the "data" directory
            //outputStream = openFileOutput("myfile.txt", MODE_PRIVATE);

            //saving to "cache" directory
            File cacheDir = getCacheDir();
            File file = new File(cacheDir.getAbsolutePath(), "myfile.txt");
            outputStream = new FileOutputStream(file);

            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            outputStream.write("hello".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
