package edu.utsa.cs3443.photoshopwizards;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;

import edu.utsa.cs3443.photoshopwizards.model.EditImage;
import edu.utsa.cs3443.photoshopwizards.model.Image;

public class EditImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mainImage;
    private int ptrImage;
    private Bitmap editBit;
    private Bitmap[] storeBit;
    private EditImage editImage;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                //Calls back once the user has selected an image
                //PhotoPicker
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    String path = uri.getPath();

                    Context context = getBaseContext();
                    try {
                        editBit = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                        editImage = new EditImage(editBit);
                        mainImage = findViewById(R.id.EditingImage);
                        mainImage.setImageBitmap(editBit);

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if(editBit == null)
                    {
                        Log.d("nullBitmap", "bitmap is null");
                    }
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        int[] buttonIDs = {R.id.EditImageBack, R.id.FlipHorizontal, R.id.FlipVertical,
                R.id.InvertColor, R.id.Grayscale, R.id.Restore, R.id.SaveImage};
        setupButtons(buttonIDs);

        loadExtra();

        if(editBit != null) {
            mainImage = findViewById(R.id.EditingImage);
            mainImage.setImageBitmap(editBit);
            editImage = new EditImage(editBit);
        }else{
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        }



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
        }
        if (view.getId() == R.id.Grayscale)
        {
            editImage.greyscale();
            mainImage.setImageBitmap(editImage.getNewImg());
        }

        if (view.getId() == R.id.FlipVertical)
        {
            editImage.flipY();
            mainImage.setImageBitmap(editImage.getNewImg());
        }

        if (view.getId() == R.id.InvertColor)
        {
            editImage.invertColor();
            mainImage.setImageBitmap(editImage.getNewImg());
        }

        if(view.getId() == R.id.Restore)
        {
            editImage.loadDefault();
            mainImage.setImageBitmap(editImage.getNewImg());
        }

        if(view.getId() == R.id.SaveImage){
            try {
                Image.saveImage(this, mainImage.getDrawable());
                Toast.makeText(this,"Image Saved!",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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
