package edu.utsa.cs3443.photoshopwizards.model;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

/**
 * Abstract class that represents an image in the program
 * @author
 */
public abstract class Image {

    private Bitmap bitmap;

    /**
     * Initializes the image object
     */
    public Image(){}

    /**
     * Initializes the image object with a bitmap object
     * @param bitmap, bitmap representing the image (Bitmap)
     */
    public Image(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    /**
     * Returns object bitmap
     * @return Bitmap, object bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Sets a new object bitmap
     * @param bitmap, new object bitmap (Bitmap)
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Saves a given image to the device's gallery
     * @param activity, context for the program (Activity)
     * @param image, image to be saved (Drawable)
     * @throws Exception
     */
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
        outputStream.close();


    }

    /**
     * Stores a screenshot of a given view as a bitmap
     * @param view, view to be screenshot (View)
     * @return Bitmap, screenshot of the given view
     */
    public static Bitmap takeCanvasScreenshot(View view){
        Bitmap newBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Drawable background = view.getBackground();

        if(background != null){
            background.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);
        return newBitmap;
    }

    /**
     * Saves an image as a file
     * @param activity, context for the program (Activity)
     * @param image, image to be saved (Drawable)
     * @return Uri, link to the saved file
     */
    public static Uri saveImageToFile(Activity activity, Drawable image){
        File file = new File(activity.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BitmapDrawable bitmapDrawable = (BitmapDrawable) image;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , byteStream);
        byte[] bitmapdata = byteStream.toByteArray();


        FileOutputStream fileStream = null;
        try {
            fileStream = new FileOutputStream(file);
            fileStream.write(bitmapdata);

            fileStream.flush();
            fileStream.close();
            byteStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Uri uri = Uri.fromFile(file);
        return uri;
    }

}
