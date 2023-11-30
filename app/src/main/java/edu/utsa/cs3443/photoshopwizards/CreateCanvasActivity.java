package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.Toast;

import edu.utsa.cs3443.photoshopwizards.model.Image;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_canvas);

        int[] buttonIDs = {R.id.CanvasBack, R.id.Save};
        setupButtons(buttonIDs);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            background = extras.getParcelable("background");
            image1 = extras.getParcelable("image1");
            image2 = extras.getParcelable("image2");
            image3 = extras.getParcelable("image3");
        }

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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.CanvasBack){
            Intent intent = new Intent(this, LayersActivity.class);
            intent.putExtra("image1",image1);
            intent.putExtra("image2",image2);
            intent.putExtra("image3",image3);
            intent.putExtra("background",background);
            startActivity(intent);
        }else if(view.getId() == R.id.Save){
            try {
                Image.saveImageTest(this, canvas);
                Toast.makeText(view.getContext(),"Canvas saved!",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setupButtons(int[] buttonIDs) {
        for (int id : buttonIDs) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }
}
