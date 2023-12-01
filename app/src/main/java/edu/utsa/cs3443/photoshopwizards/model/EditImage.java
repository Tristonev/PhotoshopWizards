package edu.utsa.cs3443.photoshopwizards.model;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Object class that represents an image to be edited
 * @author Ryan Johnson, vkg540
 */
public class EditImage extends Image {
    private Bitmap defaultBitmap;
    private Bitmap newBitmap;

    public EditImage(Activity activity, int imageId){
        Resources resource = activity.getResources();
        defaultBitmap = BitmapFactory.decodeResource(resource, imageId);
        newBitmap = BitmapFactory.decodeResource(resource, imageId);
    }

    /**
     * Initializes bitmaps representing the image
     * @param bit, bitmap object of the image
     */
    public EditImage(Bitmap bit){
        defaultBitmap = bit;
        newBitmap = bit;
    }

    /**
     * Flips the object bitmap horizontally
     */
    public void flipX(){
        Matrix matrix = new Matrix();
        matrix.postScale(-1 , 1, newBitmap.getWidth() / 2f, newBitmap.getHeight() / 2f);
        newBitmap = Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.getWidth(), newBitmap.getHeight(), matrix, true);
    }

    /**
     * Flips the object bitmap vertically
     */
    public void flipY(){
        Matrix matrix = new Matrix();
        matrix.postScale(1 , -1, newBitmap.getWidth() / 2f, newBitmap.getHeight() / 2f);
        newBitmap = Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.getWidth(), newBitmap.getHeight(), matrix, true);
    }

    /**
     * inverts the color on the object bitmap
     */
    public void invertColor(){
        int length = newBitmap.getWidth()*newBitmap.getHeight();

        int[] pix = new int[length];
        newBitmap.getPixels(pix,0,newBitmap.getWidth(),0,0,newBitmap.getWidth(),newBitmap.getHeight());

        int r, g, b;

        for (int i = 0; i < length; i++) {
            r = (pix[i] >> 16) & 0xff;
            g = (pix[i] >> 8) & 0xff;
            b = pix[i] & 0xff;

            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            pix[i] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

        Bitmap bm = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), newBitmap.getConfig());
        bm.setPixels(pix, 0, newBitmap.getWidth(), 0, 0, newBitmap.getWidth(), newBitmap.getHeight());
        newBitmap = bm;
    }

    /**
     * Makes the object bitmap black and white
     */
    public void greyscale(){
        int length = newBitmap.getWidth()*newBitmap.getHeight();

        int[] pix = new int[length];
        newBitmap.getPixels(pix,0,newBitmap.getWidth(),0,0,newBitmap.getWidth(),newBitmap.getHeight());

        int r, g, b, I;

        for (int i = 0; i < length; i++) {
            r = (pix[i] >> 16) & 0xff;
            g = (pix[i] >> 8) & 0xff;
            b = pix[i] & 0xff;

            I = (int)(0.299 * r + 0.587 * g + 0.114 * b);
            r = I;
            g = I;
            b = I;

            pix[i] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

        Bitmap bm = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), newBitmap.getConfig());
        bm.setPixels(pix, 0, newBitmap.getWidth(), 0, 0, newBitmap.getWidth(), newBitmap.getHeight());
        newBitmap = bm;
    }

    /**
     * loads the original object bitmap
     */
    public void loadDefault(){
        newBitmap = defaultBitmap;
    }

    /**
     * returns the original object bitmap
     * @return Bitmap, the original object bitmap
     */
    public Bitmap getDefaultImg() {
        return defaultBitmap;
    }

    /**
     * sets the original object bitmap
     * @param defaultImg, the new original object bitmap (Bitmap)
     */
    public void setDefaultImg(Bitmap defaultImg) {
        this.defaultBitmap = defaultImg;
    }

    /**
     * returns the edited object bitmap
     * @return Bitmap, the object bitmap
     */
    public Bitmap getNewImg() {
        return newBitmap;
    }

    /**
     * sets the edited object bitmap
     * @param newImg, the new object bitmap (Bitmap)
     */
    public void setNewImg(Bitmap newImg) {
        this.newBitmap = newImg;
    }
}
