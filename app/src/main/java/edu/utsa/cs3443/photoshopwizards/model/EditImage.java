package edu.utsa.cs3443.photoshopwizards.model;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class EditImage extends Image {
    private Bitmap defaultBitmap;
    private Bitmap newBitmap;

    public EditImage(Activity activity, int imageId){
        Resources resource = activity.getResources();
        defaultBitmap = BitmapFactory.decodeResource(resource, imageId);
        newBitmap = BitmapFactory.decodeResource(resource, imageId);
    }

    public EditImage(Bitmap bit){
        defaultBitmap = bit;
        newBitmap = bit;
    }

    public void flipX(){
        Matrix matrix = new Matrix();
        matrix.postScale(-1 , 1, newBitmap.getWidth() / 2f, newBitmap.getHeight() / 2f);
        newBitmap = Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.getWidth(), newBitmap.getHeight(), matrix, true);
    }

    public void flipY(){
        Matrix matrix = new Matrix();
        matrix.postScale(1 , -1, newBitmap.getWidth() / 2f, newBitmap.getHeight() / 2f);
        newBitmap = Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.getWidth(), newBitmap.getHeight(), matrix, true);
    }

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

    public void loadDefault(){
        newBitmap = defaultBitmap;
    }

    public Bitmap getDefaultImg() {
        return defaultBitmap;
    }

    public void setDefaultImg(Bitmap defaultImg) {
        this.defaultBitmap = defaultImg;
    }

    public Bitmap getNewImg() {
        return newBitmap;
    }

    public void setNewImg(Bitmap newImg) {
        this.newBitmap = newImg;
    }
}
