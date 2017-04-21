package com.github.maxcriser.qrscanner.ui;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.maxcriser.qrscanner.Core;
import com.github.maxcriser.qrscanner.R;
import com.github.maxcriser.qrscanner.adapter.ItemAdapter;
import com.github.maxcriser.qrscanner.adapter.SampleFragmentPagerAdapter;
import com.github.maxcriser.qrscanner.async.OnResultCallback;
import com.github.maxcriser.qrscanner.database.DatabaseHelper;
import com.github.maxcriser.qrscanner.database.models.ItemModel;
import com.github.maxcriser.qrscanner.loader.ItemLoader;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private RecyclerView recyclerItems;
    private static final int LOADER_ID = 1;
    private ItemAdapter adapter;

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

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
//        return new ItemLoader(this, "", ItemModel.class, getApplication());
        return null;
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
//        adapter = new ItemAdapter(data, this, R.layout.item_database);
//    //    recyclerItems.setAdapter(adapter);
//        recyclerItems.swapAdapter(adapter, true);
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {
//        recyclerItems.swapAdapter(null, true);
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
//
//        viewPager.findViewById(R.id.linear_database);
//
//        recyclerItems = (RecyclerView) viewPager.findViewById(R.id.recycler_view);
//
//        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
//
//        recyclerItems.setHasFixedSize(true);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//        recyclerItems.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        final LoaderManager supportLoaderManager = getSupportLoaderManager();
//        if (supportLoaderManager.getLoader(LOADER_ID) != null) {
//            supportLoaderManager.getLoader(LOADER_ID).forceLoad();
//        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DatabaseHelper dbHelper;
//        dbHelper = ((Core) getApplication()).getDatabaseHelper(this);
//
//        final ContentValues newItem = new ContentValues();
//        newItem.put(ItemModel.ID, (Integer) null);
//        newItem.put(ItemModel.TEXT, "TEST");
//        newItem.put(ItemModel.CODE_FORMAT, "CODE TEST");
//
//        dbHelper.insert(ItemModel.class, newItem, new OnResultCallback<Long, Void>() {
//
//            @Override
//            public void onSuccess(final Long pLong) {
//                Toast.makeText(MainActivity.this, "ADDED", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(final Exception pE) {
//                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onProgressChanged(final Void pVoid) {
//
//            }
//        });

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
