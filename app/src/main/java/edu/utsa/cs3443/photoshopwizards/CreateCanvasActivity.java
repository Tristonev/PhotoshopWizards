package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.photoshopwizards.model.EditImage;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class CreateCanvasActivity extends AppCompatActivity implements View.OnClickListener {

    PhotoEditorView canvasView;
    PhotoEditor edit;
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
        canvasView.getSource().setImageBitmap(background);

        edit = new PhotoEditor.Builder(this,canvasView).build();

        if(image3 != null){
            edit.addImage(image3);
        }
        if(image2 != null){
            edit.addImage(image2);
        }
        if(image1 != null){
            edit.addImage(image1);
        }
    }

    @Override
    public void onClick(View view) {
        System.out.println("Hello");

        if(view.getId() == R.id.CanvasBack){
            Intent intent = new Intent(this, LayersActivity.class);
            intent.putExtra("image1",image1);
            intent.putExtra("image2",image2);
            intent.putExtra("image3",image3);
            startActivity(intent);
        }
    }

    private void setupButtons(int[] buttonIDs) {
        for (int id : buttonIDs) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }
}
