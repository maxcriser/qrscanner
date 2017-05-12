package com.github.maxcriser.qrscanner.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.maxcriser.qrscanner.R;
import com.github.maxcriser.qrscanner.adapter.SampleFragmentPagerAdapter;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    public final void handleResult(final Result pResult) {
        Toast.makeText(this, pResult.getText(), Toast.LENGTH_SHORT).show();
    }

    public void onScanClicked(final View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        } else {
            startScanner();
        }
    }

    public void onSettingsClicked(final View view) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_settings, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.settings);
        dialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {

            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {

            }
        });

        final EditText password = (EditText) dialogView.findViewById(R.id.password);
        final AppCompatImageView eyePassword = (AppCompatImageView) dialogView.findViewById(R.id.eye_password);

        eyePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (password.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    eyePassword.setImageResource(R.drawable.eye_off);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    eyePassword.setImageResource(R.drawable.eye_on);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Login");
//        // this is set the view from XML inside AlertDialog
//        alert.setView(R.layout.fragment_settings);
//        // disallow cancel of AlertDialog on click of back button and outside touch
//        alert.setCancelable(false);
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(final DialogInterface dialog, final int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(final DialogInterface dialog, final int which) {
//                Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_SHORT).show();
//            }
//        });
//        final AlertDialog dialog = alert.create();
//        dialog.show();
    }

    private void startScanner() {
        startActivity(new Intent(this, ScannerActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initViews() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final SampleFragmentPagerAdapter sampleFragmentPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), this, getApplication(), getSupportLoaderManager());
        viewPager.setAdapter(sampleFragmentPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.need_access_to_the_camera, Toast.LENGTH_SHORT).show();
        } else {
            startScanner();
        }
    }
}
