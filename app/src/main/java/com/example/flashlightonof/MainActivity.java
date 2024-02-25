package com.example.flashlightonof;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashOn = false;
    private ImageView flashImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем менеджер камеры
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            // Получаем идентификатор камеры
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        flashImageView = findViewById(R.id.flashlight_imageview);

        flashImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    // Выключаем фонарик
                    turnOffFlashLight();
                } else {
                    // Включаем фонарик
                    turnOnFlashLight();
                }
            }
        });
    }

    private void turnOnFlashLight() {
        try {
            cameraManager.setTorchMode(cameraId, true);
            isFlashOn = true;
            flashImageView.setImageResource(R.drawable.ic_flashlight_on);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffFlashLight() {
        try {
            cameraManager.setTorchMode(cameraId, false);
            isFlashOn = false;
            flashImageView.setImageResource(R.drawable.ic_flashlight_off);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFlashOn) {
            turnOffFlashLight();
        }
    }
}