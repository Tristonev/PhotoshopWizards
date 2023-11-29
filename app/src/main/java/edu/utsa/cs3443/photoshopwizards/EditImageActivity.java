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
    private int ptrImage;
    private Bitmap editBit;
    private Bitmap[] storeBit;
    private EditImage editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        int[] buttonIDs = {R.id.EditImageBack, R.id.FlipHorizontal, R.id.FlipVertical, R.id.InvertColor, R.id.Grayscale, R.id.Restore};
        setupButtons(buttonIDs);

        loadExtra();

        mainImage = findViewById(R.id.EditingImage);
        mainImage.setImageBitmap(editBit);
        editImage = new EditImage(editBit);

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
            storeBit[ptrImage] = editImage.getNewImg();
            intent.putExtra("image1",storeBit[0]);
            intent.putExtra("image2",storeBit[1]);
            intent.putExtra("image3",storeBit[2]);
            intent.putExtra("background",storeBit[3]);
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

    private void loadExtra(){
        Bundle extras =getIntent().getExtras();
        storeBit = new Bitmap[4];
        if(extras != null){
            storeBit[0] = extras.getParcelable("image1");
            storeBit[1] = extras.getParcelable("image2");
            storeBit[2] = extras.getParcelable("image3");
            storeBit[3] = extras.getParcelable("background");
            ptrImage = extras.getInt("ptrImage");
            editBit = storeBit[ptrImage];
        }
    }
}
