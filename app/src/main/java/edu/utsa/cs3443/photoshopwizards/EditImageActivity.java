package edu.utsa.cs3443.photoshopwizards;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

/**
 * the EditImageActivity class edits an image based on user input
 * @author Ryan Johnson, vkg540
 */
public class EditImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mainImage;
    private int ptrImage;
    private Bitmap editBit;
    private Uri[] storeUri;
    private EditImage editImage;
    private String source;
    private ImageView handler;
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

    /**
     * initializes the screen and checks for files passed through intent
     * @param savedInstanceState, used for designating the instance (Bundle)
     */
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

    /**
     * initializes an array of button objects
     * @param buttonIDs, refers to multiple button object (int[])
     */
    private void setupButtons(int[] buttonIDs){
        for (int id : buttonIDs){
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

    /**
     * reacts to user click input
     * @param view, object for determining where the user clicked (view)
     */
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.EditImageBack){
            if(source.equals("LayersActivity")){
                Intent intent = new Intent(this, LayersActivity.class);
                handler.setImageBitmap(editImage.getNewImg());
                storeUri[ptrImage] = Image.saveImageToFile(this, handler.getDrawable());
                intent.putExtra("uri1", storeUri[0]);
                intent.putExtra("uri2", storeUri[1]);
                intent.putExtra("uri3", storeUri[2]);
                intent.putExtra("uriB", storeUri[3]);
                startActivity(intent);
            }else if(source.equals("MainActivity")){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
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

    /**
     * loads data sent through the intent
     */
    private void loadExtra(){
        Bundle extras =getIntent().getExtras();
        handler = findViewById(R.id.saveViewEdit);
        BitmapDrawable bitmapDrawable;
        storeUri = new Uri[4];
        source = "";
        ptrImage = 0;
        if(extras != null){
            storeUri[0] = extras.getParcelable("uri1");
            storeUri[1] = extras.getParcelable("uri2");
            storeUri[2] = extras.getParcelable("uri3");
            storeUri[3] = extras.getParcelable("uriB");
            ptrImage = extras.getInt("ptrImage");
            source = extras.getString("source");
        }
        switch(ptrImage){
            case 1:
                handler.setImageURI(storeUri[0]);
                break;
            case 2:
                handler.setImageURI(storeUri[1]);
                break;
            case 3:
                handler.setImageURI(storeUri[2]);
                break;
            case 4:
                handler.setImageURI(storeUri[3]);
                break;
        }
        bitmapDrawable = (BitmapDrawable) handler.getDrawable();
        editBit = bitmapDrawable.getBitmap();
        handler.setImageBitmap(null);
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
