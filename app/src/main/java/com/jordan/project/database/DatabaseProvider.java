package com.jordan.project.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class DatabaseProvider extends ContentProvider {

    public static final String AUTHOR = "safari_jordan";

    private static final String LOG_TAG = "DatabaseProvider";

    private static final int USER_INFO_ID = 0;
    private static final int USER_INFO_ALL = 1;
    private static final int MOTION_DETAIL_ID = 2;
    private static final int MOTION_DETAIL_ALL = 3;
    private static final int REACH_DETAIL_ID = 4;
    private static final int REACH_DETAIL_ALL = 5;
    private static final int BLUETOOTH_LIST_ID = 6;
    private static final int BLUETOOTH_LIST_ALL = 7;
    private static final int MOTION_BLUETOOTH_DATA_ID = 8;
    private static final int MOTION_BLUETOOTH_DATA_ALL = 9;
    private static final int MOTION_LIST_ID = 10;
    private static final int MOTION_LIST_ALL = 11;


    private static final UriMatcher sURLMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        sURLMatcher.addURI(AUTHOR, DatabaseObject.USER_INFO + "/*",
                USER_INFO_ID);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.USER_INFO,
                USER_INFO_ALL);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.MOTION_DETAIL + "/*",
                MOTION_DETAIL_ID);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.MOTION_DETAIL,
                MOTION_DETAIL_ALL);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.REACH_DETAIL + "/*",
                REACH_DETAIL_ID);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.REACH_DETAIL,
                REACH_DETAIL_ALL);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.BLUETOOTH_LIST + "/*",
                BLUETOOTH_LIST_ID);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.BLUETOOTH_LIST,
                BLUETOOTH_LIST_ALL);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.MOTION_BLUETOOTH_DATA + "/*",
                MOTION_BLUETOOTH_DATA_ID);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.MOTION_BLUETOOTH_DATA,
                MOTION_BLUETOOTH_DATA_ALL);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.MOTION_LIST + "/*",
                MOTION_LIST_ID);
        sURLMatcher.addURI(AUTHOR, DatabaseObject.MOTION_LIST,
                MOTION_LIST_ALL);
    }

    private DatabaseOpenHelper dbOpenHelper;

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        android.util.Log.e(LOG_TAG, "onCreate==========");
        dbOpenHelper = DatabaseOpenHelper.getInstance(getContext());
        DatabaseUtils.setAppContext(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        Cursor cursor = null;

        int result = sURLMatcher.match(uri);
        if (result == UriMatcher.NO_MATCH)
            return cursor;

        String table = null;
        String where = null;

        switch (result) {
            case USER_INFO_ID:
                table = DatabaseObject.USER_INFO;
                where = createWhere(
                        DatabaseObject.UserInfoTable.FIELD_USER_INFO_ID, uri);
                break;
            case USER_INFO_ALL:
                table = DatabaseObject.USER_INFO;
                break;
            case MOTION_DETAIL_ID:
                table = DatabaseObject.MOTION_DETAIL;
                where = createWhere(
                        DatabaseObject.MotionDetailTable.FIELD_MOTION_DETAIL_ID, uri);
                break;
            case MOTION_DETAIL_ALL:
                table = DatabaseObject.MOTION_DETAIL;
                break;
            case REACH_DETAIL_ID:
                table = DatabaseObject.REACH_DETAIL;
                where = createWhere(
                        DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_ID, uri);
                break;
            case REACH_DETAIL_ALL:
                table = DatabaseObject.REACH_DETAIL;
                break;
            case BLUETOOTH_LIST_ID:
                table = DatabaseObject.BLUETOOTH_LIST;
                where = createWhere(
                        DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_ID, uri);
                break;
            case BLUETOOTH_LIST_ALL:
                table = DatabaseObject.BLUETOOTH_LIST;
                break;
            case MOTION_BLUETOOTH_DATA_ID:
                table = DatabaseObject.MOTION_BLUETOOTH_DATA;
                where = createWhere(
                        DatabaseObject.MotionBluetoothDataTable.FIELD_ID, uri);
                break;
            case MOTION_BLUETOOTH_DATA_ALL:
                table = DatabaseObject.MOTION_BLUETOOTH_DATA;
                break;
            case MOTION_LIST_ID:
                table = DatabaseObject.MOTION_LIST;
                where = createWhere(
                        DatabaseObject.MotionListTable.FIELD_MOTION_LIST_ID, uri);
                break;
            case MOTION_LIST_ALL:
                table = DatabaseObject.MOTION_LIST;
                break;
            default:
                throw new IllegalStateException("Unrecognized URI:" + uri);
        }

        if (!TextUtils.isEmpty(where)) {
            selection = where + selection;
        }

        cursor = dbOpenHelper.getReadableDatabase().query(table, projection,
                selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        int result = sURLMatcher.match(uri);
        String table = null;
        switch (result) {
            case USER_INFO_ID:
            case USER_INFO_ALL:
                table = DatabaseObject.USER_INFO;
                break;
            case MOTION_DETAIL_ID:
            case MOTION_DETAIL_ALL:
                table = DatabaseObject.MOTION_DETAIL;
                break;
            case REACH_DETAIL_ID:
            case REACH_DETAIL_ALL:
                table = DatabaseObject.REACH_DETAIL;
                break;
            case BLUETOOTH_LIST_ID:
            case BLUETOOTH_LIST_ALL:
                table = DatabaseObject.BLUETOOTH_LIST;
                break;
            case MOTION_BLUETOOTH_DATA_ID:
            case MOTION_BLUETOOTH_DATA_ALL:
                table = DatabaseObject.MOTION_BLUETOOTH_DATA;
                break;
            case MOTION_LIST_ID:
            case MOTION_LIST_ALL:
                table = DatabaseObject.MOTION_LIST;
                break;
            default:
                throw new IllegalStateException("Unrecognized URI:" + uri);
        }

        long result_id = dbOpenHelper.getWritableDatabase().insert(table,
                null, values);
        if (result > 0) {
            Uri result_uri = DatabaseUtils.createReultUri(AUTHOR, table,
                    result_id);
            getContext().getContentResolver().notifyChange(result_uri, null);
            return result_uri;
        } else {
            return null;
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        int result = sURLMatcher.match(uri);
        if (result == UriMatcher.NO_MATCH)
            return 0;
        String table = null;
        String where = null;

        switch (result) {
            case USER_INFO_ID:
                table = DatabaseObject.USER_INFO;
                where = createWhere(
                        DatabaseObject.UserInfoTable.FIELD_USER_INFO_ID, uri);
                break;
            case USER_INFO_ALL:
                table = DatabaseObject.USER_INFO;
                break;
            case MOTION_DETAIL_ID:
                table = DatabaseObject.MOTION_DETAIL;
                where = createWhere(
                        DatabaseObject.MotionDetailTable.FIELD_MOTION_DETAIL_ID, uri);
                break;
            case MOTION_DETAIL_ALL:
                table = DatabaseObject.MOTION_DETAIL;
                break;
            case REACH_DETAIL_ID:
                table = DatabaseObject.REACH_DETAIL;
                where = createWhere(
                        DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_ID, uri);
                break;
            case REACH_DETAIL_ALL:
                table = DatabaseObject.REACH_DETAIL;
                break;
            case BLUETOOTH_LIST_ID:
                table = DatabaseObject.BLUETOOTH_LIST;
                where = createWhere(
                        DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_ID, uri);
                break;
            case BLUETOOTH_LIST_ALL:
                table = DatabaseObject.BLUETOOTH_LIST;
                break;
            case MOTION_BLUETOOTH_DATA_ID:
                table = DatabaseObject.MOTION_BLUETOOTH_DATA;
                where = createWhere(
                        DatabaseObject.MotionBluetoothDataTable.FIELD_ID, uri);
                break;
            case MOTION_BLUETOOTH_DATA_ALL:
                table = DatabaseObject.MOTION_BLUETOOTH_DATA;
                break;
            case MOTION_LIST_ID:
                table = DatabaseObject.MOTION_LIST;
                where = createWhere(
                        DatabaseObject.MotionListTable.FIELD_MOTION_LIST_ID, uri);
                break;
            case MOTION_LIST_ALL:
                table = DatabaseObject.MOTION_LIST;
                break;
            default:
                throw new IllegalStateException("Unrecognized URI:" + uri);
        }

        if (!TextUtils.isEmpty(where)) {
            selection = where + selection;
        }
        int delete_result = dbOpenHelper.getWritableDatabase().delete(table,
                selection, selectionArgs);
        if (delete_result > 0) {
            Uri result_uri = DatabaseUtils.createReultUri(AUTHOR, table, -1);
            getContext().getContentResolver().notifyChange(result_uri, null);
        }
        return delete_result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        int result = sURLMatcher.match(uri);

        if (result == UriMatcher.NO_MATCH)
            return 0;

        String table = null;
        String where = null;

        switch (result) {
            case USER_INFO_ID:
                table = DatabaseObject.USER_INFO;
                where = createWhere(
                        DatabaseObject.UserInfoTable.FIELD_USER_INFO_ID, uri);
                break;
            case USER_INFO_ALL:
                table = DatabaseObject.USER_INFO;
                break;
            case MOTION_DETAIL_ID:
                table = DatabaseObject.MOTION_DETAIL;
                where = createWhere(
                        DatabaseObject.MotionDetailTable.FIELD_MOTION_DETAIL_ID, uri);
                break;
            case MOTION_DETAIL_ALL:
                table = DatabaseObject.MOTION_DETAIL;
                break;
            case REACH_DETAIL_ID:
                table = DatabaseObject.REACH_DETAIL;
                where = createWhere(
                        DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_ID, uri);
                break;
            case REACH_DETAIL_ALL:
                table = DatabaseObject.REACH_DETAIL;
                break;
            case BLUETOOTH_LIST_ID:
                table = DatabaseObject.BLUETOOTH_LIST;
                where = createWhere(
                        DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_ID, uri);
                break;
            case BLUETOOTH_LIST_ALL:
                table = DatabaseObject.BLUETOOTH_LIST;
                break;
            case MOTION_BLUETOOTH_DATA_ID:
                table = DatabaseObject.MOTION_BLUETOOTH_DATA;
                where = createWhere(
                        DatabaseObject.MotionBluetoothDataTable.FIELD_ID, uri);
                break;
            case MOTION_BLUETOOTH_DATA_ALL:
                table = DatabaseObject.MOTION_BLUETOOTH_DATA;
                break;
            case MOTION_LIST_ID:
                table = DatabaseObject.MOTION_LIST;
                where = createWhere(
                        DatabaseObject.MotionListTable.FIELD_MOTION_LIST_ID, uri);
                break;
            case MOTION_LIST_ALL:
                table = DatabaseObject.MOTION_LIST;
                break;
            default:
                throw new IllegalStateException("Unrecognized URI:" + uri);
        }

        if (!TextUtils.isEmpty(where)) {
            selection = where + selection;
        }

        int update_result = dbOpenHelper.getWritableDatabase().update(table, values,
                selection, selectionArgs);
        if (update_result > 0) {
            Uri result_uri = DatabaseUtils.createReultUri(AUTHOR, table, -1);
            getContext().getContentResolver().notifyChange(result_uri, null);
        }
        return update_result;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        // TODO Auto-generated method stub
        return super.bulkInsert(uri, values);
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    private String createWhere(String fieldName, Uri uri) {
        try {
            String id = uri.getPathSegments().get(1);
            Integer.parseInt(id);
            String where = " " + fieldName + " = " + id + " ";
            return where;
        } catch (NumberFormatException e) {
            android.util.Log.e(LOG_TAG, "id must be Integer: " + uri);
            return null;
        }
    }

    public static int getUriType(Uri uri) {
        return sURLMatcher.match(uri);
    }


}
