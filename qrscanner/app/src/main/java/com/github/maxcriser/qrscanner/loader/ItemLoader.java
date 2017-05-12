package com.github.maxcriser.qrscanner.loader;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.CursorLoader;

import com.github.maxcriser.qrscanner.Core;
import com.github.maxcriser.qrscanner.database.DatabaseHelper;

import java.lang.reflect.AnnotatedElement;

import static com.github.maxcriser.qrscanner.database.Sql.getSqlAllItems;
import static com.github.maxcriser.qrscanner.database.Sql.getSqlWithQuery;

public class ItemLoader extends CursorLoader {

    private final String SQL_WITH_QUERY;
    private final String SQL_ALL_ITEMS;
    private final String mQuery;
    private final DatabaseHelper db;

    public ItemLoader(final Context context, final String pQuery, final AnnotatedElement modelClass, final Application app) {
        super(context);
        this.SQL_WITH_QUERY = getSqlWithQuery(modelClass);
        this.SQL_ALL_ITEMS = getSqlAllItems(modelClass);
        this.mQuery = pQuery;
        this.db = ((Core) app).getDatabaseHelper(context);
    }

    @Override
    public Cursor loadInBackground() {
        final SQLiteDatabase database = db.getReadableDatabase();
        if (mQuery == null || mQuery.isEmpty()) {
            return database.rawQuery(SQL_ALL_ITEMS, null);
        } else {
            return database.rawQuery(SQL_WITH_QUERY, new String[]{"%" + mQuery + "%"});
        }
    }
}