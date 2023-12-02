package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;


import edu.utsa.cs3443.photoshopwizards.model.Image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

/**
 * The CreateCanvasActivity class displays the selected background and loads the selected images for the user to resize
 * and reposition
 * @author Ryan Johnson, vkg540
 */
public class CreateCanvasActivity extends AppCompatActivity implements View.OnClickListener {

    PhotoEditorView canvasView;
    PhotoEditor canvas;
    private Bitmap background;
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    private Uri backgroundUri;
    private Uri image1Uri;
    private Uri image2Uri;
    private Uri image3Uri;
    private ImageView handler;

    /**
     * initializes the screen and checks for files passed through intent
     * @param savedInstanceState, used for designating the instance (Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_canvas);

        int[] buttonIDs = {R.id.CanvasBack, R.id.Save};
        setupButtons(buttonIDs);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            backgroundUri = extras.getParcelable("uriB");
            image1Uri = extras.getParcelable("uri1");
            image2Uri = extras.getParcelable("uri2");
            image3Uri = extras.getParcelable("uri3");
        }
        Bitmap bitmap;
        BitmapDrawable bitmapDrawable;

        handler = findViewById(R.id.saveView);

        if(backgroundUri != null){
            handler.setImageURI(backgroundUri);
            bitmapDrawable = (BitmapDrawable) handler.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            background = bitmap;
        }

        if(image1Uri != null){
            handler.setImageURI(image1Uri);
            bitmapDrawable = (BitmapDrawable) handler.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            image1 = bitmap;
        }

        if(image2Uri != null){
            handler.setImageURI(image2Uri);
            bitmapDrawable = (BitmapDrawable) handler.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            image2 = bitmap;
        }

        if(image3Uri != null){
            handler.setImageURI(image3Uri);
            bitmapDrawable = (BitmapDrawable) handler.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            image3 = bitmap;
        }

        handler.setImageBitmap(null);

        canvasView = findViewById(R.id.MainCanvas);
        if(background != null) {
            canvasView.getSource().setImageBitmap(background);
        }
        //Having a transparent image as the default background would be good

        canvas = new PhotoEditor.Builder(this,canvasView).build();

        if(image3 != null){
            canvas.addImage(image3);
        }
        if(image2 != null){
            canvas.addImage(image2);
        }
        if(image1 != null){
            canvas.addImage(image1);
        }


    }

    /**
     * reacts to user click input
     * @param view, The view that was clicked (View)
     */
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.CanvasBack){
            Intent intent = new Intent(this, LayersActivity.class);
            intent.putExtra("uri1", image1Uri);
            intent.putExtra("uri2", image2Uri);
            intent.putExtra("uri3", image3Uri);
            intent.putExtra("uriB", backgroundUri);
            startActivity(intent);
        }else if(view.getId() == R.id.Save){
            try {

                ConstraintLayout layout = findViewById(R.id.ConstraintCanvas);
                ImageView testDragon = findViewById(R.id.saveView);
                Bitmap screenshot = Image.takeCanvasScreenshot(layout);
                testDragon.setImageBitmap(screenshot);

                int heightRemoved = screenshot.getHeight() - canvasView.getHeight();
                int widthRemoved = screenshot.getWidth() - (canvasView.getWidth() - 2 * (int) testDragon.getX());

                Bitmap finalImage = Bitmap.createBitmap(screenshot, (int) testDragon.getX(), (int) testDragon.getY(),
                        screenshot.getWidth() - widthRemoved, screenshot.getHeight() - heightRemoved);
                testDragon.setImageBitmap(finalImage);

                Image.saveImage(this, testDragon.getDrawable());
                Toast.makeText(view.getContext(),"Canvas saved!",Toast.LENGTH_SHORT).show();
                testDragon.setImageBitmap(null);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * initializes an array of button objects
     * @param buttonIDs, refers to multiple button object (int[])
     */
    private void setupButtons(int[] buttonIDs) {
        for (int id : buttonIDs) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

    /**
     * prevents the user from using the back button to access other activities
     */
    @Override
    public void onBackPressed() {
        if (true) {
            return;
        }
        super.onBackPressed();
    }
}
