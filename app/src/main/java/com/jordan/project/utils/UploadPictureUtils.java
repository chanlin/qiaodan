package com.jordan.project.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.jordan.project.JordanApplication;

import java.io.File;

public class UploadPictureUtils {

	public static final int REQUEST_IMAGE = 100;
	private static String mPath;

	public static final int NONE = 101;
	public static final int PHOTOHRAPH = 102;
	public static final int PHOTOZOOM = 103;
	public static final int ZOOMOK = 104;

	public static final String IMAGE_UNSPECIFIED = "image/*";

	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/jordan.jpg";// temp
	// file
	public static Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);// The Uri to
	// store the big
	// bitmap

	public static final String IMAGE_FILE_PATH = Environment.getExternalStorageDirectory() + File.separator +"jordan.jpg";

	public static final String HTTP_DOWNLOAD_PATH_BASE = Environment.getExternalStorageDirectory() + File.separator + "attchment";
	public static void setPhoto(Activity activity) {
		Log.e("Photo", "setPhoto");
//		mPath = Environment.getExternalStorageDirectory() + File.separator
//				+ "xs.jpg";
//		Intent takePictureFromCameraIntent = new Intent(
//				MediaStore.ACTION_IMAGE_CAPTURE);
//		takePictureFromCameraIntent.putExtra(
//				android.provider.MediaStore.EXTRA_OUTPUT,
//				Uri.fromFile(new File(mPath)));
//		activity.startActivityForResult(takePictureFromCameraIntent, PHOTOHRAPH);

		File photo_dir = new File(HTTP_DOWNLOAD_PATH_BASE);
		if(!photo_dir.exists()){
			photo_dir.mkdirs();
		}
		
		Intent takePictureFromCameraIntent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		File pictureFile = new File(
                HTTP_DOWNLOAD_PATH_BASE
                + File.separator
                + "jordan_"
                + JordanApplication.getUsername(activity) + ".jpg");
        Uri path = Uri.fromFile(pictureFile);
        
		takePictureFromCameraIntent.putExtra(
				MediaStore.EXTRA_OUTPUT,
				path);
		activity.startActivityForResult(takePictureFromCameraIntent, PHOTOHRAPH);
	}


	public static void setManually(Activity activity) {
		Log.e("Photo", "setManually");
		callGalleryForInputImage(REQUEST_IMAGE, activity);
	}

	public static void callGalleryForInputImage(int nRequestCode,
			Activity activity) {
		Intent galleryIntent;
		galleryIntent = new Intent();
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
		galleryIntent.setType(IMAGE_UNSPECIFIED);
		activity.startActivityForResult(galleryIntent, nRequestCode);
	}

	public static void startPhotoZoom(Uri uri, Activity activity) {
		Log.e("Photo", "startPhotoZoom");
		//		Intent intent = new Intent("com.android.camera.action.CROP");
		//		intent.setDataAndType(uri, "image/*");
		//		intent.putExtra("crop", "true");
		//		// aspectX aspectY �ǿ�ߵı���
		//		intent.putExtra("aspectX", 1);
		//		intent.putExtra("aspectY", 1);
		//		// outputX outputY �ǲü�ͼƬ���
		//		intent.putExtra("outputX", 600);
		//		intent.putExtra("outputY", 600);
		//		intent.putExtra("return-data", false);
		//		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		//		intent.putExtra("noFaceDetection", false);
		//		activity.startActivityForResult(intent, ZOOMOK);       
		if (uri == null) {  
			Log.i("tag", "The uri is not exist.");  
			return;  
		}  

		Intent intent = new Intent("com.android.camera.action.CROP");  
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			String url=getPath(activity,uri);  
			intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");  
		}else{  
			intent.setDataAndType(uri, "image/*");  
		}  

		// 设置裁剪  
		intent.putExtra("crop", "true");  
		// aspectX aspectY 是宽高的比例  
		intent.putExtra("aspectX", 1);  
		intent.putExtra("aspectY", 1);  
		// outputX outputY 是裁剪图片宽高  
		intent.putExtra("outputX", 200);  
		intent.putExtra("outputY", 200);  
		intent.putExtra("return-data", true);  
		activity.startActivityForResult(intent, ZOOMOK);  
		Log.e("Photo", "startPhotoZoom-over");


	}
	//
	//
	// public static String save(String path,Bitmap bm) {
	//
	// try {
	//
	// File f = new File(path);
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	//
	// FileOutputStream fos = new FileOutputStream(new File(
	// com.crf.util.PictureUtil.getAlbumDir(),
	// "small01_" + f.getName()));
	// int options = 100;
	// while (baos.toByteArray().length / 1024 > 80 && options != 10) {
	// baos.reset();
	// bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
	// options -= 30;
	// }
	// fos.write(baos.toByteArray());
	// fos.close();
	// baos.close();
	// // bm.compress(Bitmap.CompressFormat.JPEG, 70, fos);
	// return "small01_" + f.getName();
	// // Toast.makeText(this, "�������!", Toast.LENGTH_SHORT).show();
	//
	// } catch (Exception e) {
	// Log.e("error", "error", e);
	// return "";
	// }
	// }

	public static final String getPicDir(Activity ctx, int type){
		String current_dir = "";
		//        HttpManager hm = ((XSApplication) ctx.getApplication()).getManager();
		//        if(type == UploadDatabaseConfig.UploadConfig.PIC_TYPE_PERSONAL){
		//            current_dir = hm.getCurrentUserPersonalDir();
		//        }else if(type == UploadDatabaseConfig.UploadConfig.PIC_TYPE_CAR){
		//            current_dir = hm.getCurrentUserCarDir();
		//        }else if(type == UploadDatabaseConfig.UploadConfig.PIC_TYPE_TEAM){
		//            current_dir = hm.getCurrentUserTeamDir();
		//        }else if(type == UploadDatabaseConfig.UploadConfig.PIC_TYPE_RECORD){
		//            current_dir = hm.getCurrentUserRecordDir();
		//        }
		//        android.util.Log.e("SlashInfo", "getPicDir::result= " + current_dir);
		return current_dir;
	}




	//以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...  
	@SuppressLint("NewApi")  
	public static String getPath(final Context context, final Uri uri) {  

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;  

		// DocumentProvider  
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {  
			// ExternalStorageProvider  
			if (isExternalStorageDocument(uri)) {  
				final String docId = DocumentsContract.getDocumentId(uri);  
				final String[] split = docId.split(":");  
				final String type = split[0];  

				if ("primary".equalsIgnoreCase(type)) {  
					return Environment.getExternalStorageDirectory() + "/" + split[1];  
				}  

			}  
			// DownloadsProvider  
			else if (isDownloadsDocument(uri)) {  
				final String id = DocumentsContract.getDocumentId(uri);  
				final Uri contentUri = ContentUris.withAppendedId(  
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  

				return getDataColumn(context, contentUri, null, null);  
			}  
			// MediaProvider  
			else if (isMediaDocument(uri)) {  
				final String docId = DocumentsContract.getDocumentId(uri);  
				final String[] split = docId.split(":");  
				final String type = split[0];  

				Uri contentUri = null;  
				if ("image".equals(type)) {  
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
				} else if ("video".equals(type)) {  
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
				} else if ("audio".equals(type)) {  
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
				}  

				final String selection = "_id=?";  
				final String[] selectionArgs = new String[] {  
						split[1]  
				};  

				return getDataColumn(context, contentUri, selection, selectionArgs);  
			}  
		}  
		// MediaStore (and general)  
		else if ("content".equalsIgnoreCase(uri.getScheme())) {  
			// Return the remote address  
			if (isGooglePhotosUri(uri))  
				return uri.getLastPathSegment();  

			return getDataColumn(context, uri, null, null);  
		}  
		// File  
		else if ("file".equalsIgnoreCase(uri.getScheme())) {  
			return uri.getPath();  
		}  

		return null;  
	}  

	/** 
	 * Get the value of the data column for this Uri. This is useful for 
	 * MediaStore Uris, and other file-based ContentProviders. 
	 * 
	 * @param context The context. 
	 * @param uri The Uri to query. 
	 * @param selection (Optional) Filter used in the query. 
	 * @param selectionArgs (Optional) Selection arguments used in the query. 
	 * @return The value of the _data column, which is typically a file path. 
	 */  
	public static String getDataColumn(Context context, Uri uri, String selection,  
			String[] selectionArgs) {  

		Cursor cursor = null;  
		final String column = "_data";  
		final String[] projection = {  
				column  
		};  

		try {  
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,  
					null);  
			if (cursor != null && cursor.moveToFirst()) {  
				final int index = cursor.getColumnIndexOrThrow(column);  
				return cursor.getString(index);  
			}  
		} finally {  
			if (cursor != null)  
				cursor.close();  
		}  
		return null;  
	}  



	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is ExternalStorageProvider. 
	 */  
	public static boolean isExternalStorageDocument(Uri uri) {  
		return "com.android.externalstorage.documents".equals(uri.getAuthority());  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is DownloadsProvider. 
	 */  
	public static boolean isDownloadsDocument(Uri uri) {  
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is MediaProvider. 
	 */  
	public static boolean isMediaDocument(Uri uri) {  
		return "com.android.providers.media.documents".equals(uri.getAuthority());  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is Google Photos. 
	 */  
	public static boolean isGooglePhotosUri(Uri uri) {  
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
	}  


}
