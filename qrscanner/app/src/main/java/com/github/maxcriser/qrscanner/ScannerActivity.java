package com.github.maxcriser.qrscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private ZXingScannerView mZXingScannerView;

    @Override
    public final void handleResult(final Result pResult) {
        Toast.makeText(this, pResult.getText(), Toast.LENGTH_SHORT).show();
    }

    public void onScanClicked(final View view) {
        mZXingScannerView = new ZXingScannerView(this);
        setContentView(mZXingScannerView);
        mZXingScannerView.setResultHandler(this);
        mZXingScannerView.startCamera();
        mZXingScannerView.toggleFlash();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Application need access to the camera", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mZXingScannerView != null) {
            mZXingScannerView.stopCamera();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mZXingScannerView != null) {
            mZXingScannerView.startCamera();
        }
    }
}