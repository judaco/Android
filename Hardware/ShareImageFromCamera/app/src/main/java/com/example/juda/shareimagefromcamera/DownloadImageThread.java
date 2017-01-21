package com.example.juda.shareimagefromcamera;

/**
 * Created by Juda on 21/01/2017.
 */

import android.os.Environment;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

        import static com.example.juda.shareimagefromcamera.MainActivity.BASE_URL;

public class DownloadImageThread extends Thread {


    private File fileDownloadedImage;
    private boolean go = true;
    private int currentVersion = 0;
    private NewImageListener listener;



    public DownloadImageThread(File fileDownloadedImage, NewImageListener listener){
        this.fileDownloadedImage = fileDownloadedImage;
        this.listener = listener;
    }

    @Override
    public void run() {
        while (go){
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;

            boolean shouldDownloadImage = false;
            try{
                URL url = new URL(BASE_URL + "?version");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[8];
                int acutallyRead = inputStream.read(buffer);
                if(acutallyRead != -1){
                    String versionResponseFromServer = new String(buffer, 0, acutallyRead);
                    int serverVersion = Integer.valueOf(versionResponseFromServer);
                    if(serverVersion > currentVersion) {
                        shouldDownloadImage = true;
                        currentVersion = serverVersion;
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if(urlConnection != null)
                    urlConnection.disconnect();
            }
            inputStream = null;
            urlConnection = null;
            OutputStream fileOutputStream = null;
            if(shouldDownloadImage) {
                try {
                    URL url = new URL(BASE_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int acutallyRead;
                    fileOutputStream = new FileOutputStream(fileDownloadedImage);
                    while ((acutallyRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, acutallyRead);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null)
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (fileOutputStream != null)
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
                if(listener != null){
                    listener.onNewImage();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopDownloading(){
        go = false;
        interrupt();
    }

    static public interface NewImageListener{
        void onNewImage();
    }

}
