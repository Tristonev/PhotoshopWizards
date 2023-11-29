package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.photoshopwizards.model.EditImage;

public class EditImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mainImage;
    private int imageId;
    private EditImage editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        int[] buttonIDs = {R.id.EditImageBack, R.id.FlipHorizontal, R.id.FlipVertical, R.id.InvertColor, R.id.Grayscale, R.id.Restore};
        setupButtons(buttonIDs);

        Bundle extras =getIntent().getExtras();
        if(extras != null){
            imageId = extras.getInt("imageId");
        }
        mainImage = findViewById(R.id.EditingImage);
        mainImage.setImageResource(imageId);
        editImage = new EditImage(this, imageId);

    }

    private void setupButtons(int[] buttonIDs){
        for (int id : buttonIDs){
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        System.out.println("Hello");

        if (view.getId() == R.id.EditImageBack){
            Intent intent = new Intent(this, LayersActivity.class);
            intent.putExtra("editedLayer",editImage.getNewImg());
            startActivity(intent);
        }

        if (view.getId() == R.id.FlipHorizontal)
        {
            editImage.flipX();
            mainImage.setImageBitmap(editImage.getNewImg());
            //mainImage.setImageResource(R.drawable.dog1_flipped_horizontal);
        }
        if (view.getId() == R.id.Grayscale)
        {
            editImage.greyscale();
            mainImage.setImageBitmap(editImage.getNewImg());
            //mainImage.setImageResource(R.drawable.dog1_grayscale);
        }

        if (view.getId() == R.id.FlipVertical)
        {
            editImage.flipY();
            mainImage.setImageBitmap(editImage.getNewImg());
            //mainImage.setImageResource(R.drawable.dog1_flipped_vertically);
        }

        if (view.getId() == R.id.InvertColor)
        {
            editImage.invertColor();
            mainImage.setImageBitmap(editImage.getNewImg());
            //mainImage.setImageResource(R.drawable.dog1_inverted);
        }

        if(view.getId() == R.id.Restore)
        {
            editImage.loadDefault();
            mainImage.setImageBitmap(editImage.getNewImg());
            //mainImage.setImageResource(R.drawable.dog1);
        }
    }
}
