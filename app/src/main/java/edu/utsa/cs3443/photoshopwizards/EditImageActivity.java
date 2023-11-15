package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EditImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);


        int[] buttonIDs = {R.id.EditImageBack, R.id.FlipHorizontal, R.id.FlipVertical, R.id.InvertColor, R.id.Grayscale};
        setupButtons(buttonIDs);

        mainImage = findViewById(R.id.EditingImage);
        mainImage.setImageResource(R.drawable.dog_canvas);

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
            startActivity(intent);
        }

        if (view.getId() == R.id.FlipHorizontal)
        {
            mainImage.setImageResource(R.drawable.dog1_flipped_horizontal);
        }
        if (view.getId() == R.id.Grayscale)
        {
            mainImage.setImageResource(R.drawable.dog1_grayscale);
        }

    }
}
