package com.example.juda.flash;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    public static final int REQUEST_CAMERA_AND_FLASHLIGHT = 123;
    public static final int REQUEST_CAMERA_ONLY = 124;
    public static final int REQUEST_FLASHLIGHT_ONLY = 125;

    boolean isOn = false;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView)findViewById(R.id.surface1);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean hasPermission = true;
        if(requestCode == REQUEST_CAMERA_AND_FLASHLIGHT){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED)
                hasPermission = false;

        }
        else if(requestCode == REQUEST_FLASHLIGHT_ONLY ||
                requestCode == REQUEST_CAMERA_ONLY) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                hasPermission = false;
        }
        else
            hasPermission = false;

        if(hasPermission){
            if(camera == null)
                camera = Camera.open();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED){
                if(checkSelfPermission(Manifest.permission.FLASHLIGHT) !=
                        PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.FLASHLIGHT}, REQUEST_CAMERA_AND_FLASHLIGHT);
                }else{
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_ONLY);
                }

            }else{
                if(checkSelfPermission(Manifest.permission.FLASHLIGHT) !=
                        PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.FLASHLIGHT}, REQUEST_FLASHLIGHT_ONLY);
                }
            }
        }

        if(camera == null)
            camera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.release();
        camera = null;
    }

    public void btnToggleFlashlight(View view) {
        if(camera == null)
            return;
        if(isOn){
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
        }else{
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        isOn = !isOn;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
