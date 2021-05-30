package com.swetabh.runtimepermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity{
    private ActivityResultLauncher requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cameraBtn = findViewById(R.id.button_open_camera);
        
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCameraPreview();
            }
        });
    }

    private void showCameraPreview() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,getString(R.string.camera_permission_granted),
                    Toast.LENGTH_SHORT).show();
            startCamera();
        }
        else {
            requestCameraPermission();
        }
    }

    private void startCamera() {
        Toast.makeText(this,getString(R.string.show_camera),
                Toast.LENGTH_SHORT).show();
    }

    private void requestCameraPermission() {
        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
            Toast.makeText(this,getString(R.string.camera_access_required),
                    Toast.LENGTH_SHORT).show();
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            Toast.makeText(this,getString(R.string.camera_permission_not_available),
                    Toast.LENGTH_SHORT).show();
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    public MainActivity(){
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean isGranted) {
                        if(isGranted){
                            Toast.makeText(MainActivity.this,getString(R.string.camera_permission_granted),
                                    Toast.LENGTH_SHORT).show();
                            startCamera();
                        }
                        else{
                            Toast.makeText(MainActivity.this,getString(R.string.camera_permission_denied),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}