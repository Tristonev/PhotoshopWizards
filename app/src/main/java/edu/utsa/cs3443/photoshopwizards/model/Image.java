package edu.utsa.cs3443.photoshopwizards.model;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;

public abstract class Image {

    private Bitmap bitmap;

    public Image(){}

    public Image(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public static void saveImage(Activity activity, Drawable image) throws Exception{

        Uri images;
        ContentResolver contentResolver = activity.getContentResolver();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }
        else
        {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri uri = contentResolver.insert(images,contentValues);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) image;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Objects.requireNonNull(outputStream);

    }

    public static void saveImageTest(Activity activity, PhotoEditor canvas) throws Exception{

        Uri images;
        ContentResolver contentResolver = activity.getContentResolver();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }
        else
        {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri uri = contentResolver.insert(images,contentValues);

        final Bitmap[] get = new Bitmap[1];
        canvas.saveAsBitmap(new OnSaveBitmap() {
            @Override
            public void onBitmapReady(@Nullable Bitmap bitmap) {
                get[0] = bitmap;
            }

            @Override
            public void onFailure(@Nullable Exception e) {

            }
        });
        Bitmap bitmap = get[0];

        OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Objects.requireNonNull(outputStream);

    }

    public static Uri saveImageToFile(Activity activity, Drawable image){
        File file = new File(activity.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BitmapDrawable bitmapDrawable = (BitmapDrawable) image;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos);
        byte[] bitmapdata = bos.toByteArray();


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Uri uri = Uri.fromFile(file);
        return uri;
    }

}
