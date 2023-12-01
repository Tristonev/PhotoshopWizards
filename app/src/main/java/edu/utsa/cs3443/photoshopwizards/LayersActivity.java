package edu.utsa.cs3443.photoshopwizards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.utsa.cs3443.photoshopwizards.model.Image;

/**
 * The LayersActivity manages individual layers for the canvas
 * @author Ryan Johnson, vkg540
 * @author
 */
public class LayersActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean addClicked;
    private boolean buttonClicked;
    private boolean editClicked;
    private boolean removeClicked;
    private boolean swapClicked;
    private boolean swapImageClicked;
    private int[] buttonIDs;
    private ImageView layer1;
    private ImageView layer2;
    private ImageView layer3;
    private ImageView layer4;
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    private Bitmap background;
    private Bitmap photoPickerBitmap;
    private int layerPicked;
    private int swapId;

    //Made into an attribute because it will throw an exception if put inside a function while being called from a button
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                //Calls back once the user has selected an image
                //PhotoPicker
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    String path = uri.getPath();

                    Context context = getBaseContext();
                    try {
                        photoPickerBitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                        switch(layerPicked){
                            case 1:
                                layer1.setImageBitmap(photoPickerBitmap);
                                image1 = photoPickerBitmap;
                                break;
                            case 2:
                                layer2.setImageBitmap(photoPickerBitmap);
                                image2 = photoPickerBitmap;
                                break;
                            case 3:
                                layer3.setImageBitmap(photoPickerBitmap);
                                image3 = photoPickerBitmap;
                                break;
                            case 4:
                                layer4.setImageBitmap(photoPickerBitmap);
                                background = photoPickerBitmap;
                                break;
                            default:
                                Toast.makeText(this,"Invalid Layer Selected",Toast.LENGTH_SHORT).show();
                        }
                        reset();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if(photoPickerBitmap == null)
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
        setContentView(R.layout.activity_layers);
        buttonIDs = new int[]{R.id.LayersAdd, R.id.LayersEdit, R.id.LayersRemove, R.id.LayersSwap, R.id.LayersBack, R.id.LayersCreate};
        setupButtons(buttonIDs);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            image1 = extras.getParcelable("image1");
            image2 = extras.getParcelable("image2");
            image3 = extras.getParcelable("image3");
            background = extras.getParcelable("background");
        }

        setupImages();
    }

    private void createFileTest(){
        File file = new File(getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Made it here");
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
     * initializes each layer
     */
    private void setupImages()
    {
        layer1 = findViewById(R.id.LayerImage1);
        layer2 = findViewById(R.id.LayerImage2);
        layer3 = findViewById(R.id.LayerImage3);
        layer4 = findViewById(R.id.LayerImage4);

        bitmapOnLayer(image1, layer1);
        bitmapOnLayer(image2, layer2);
        bitmapOnLayer(image3, layer3);
        bitmapOnLayer(background, layer4);

        layer1.setOnClickListener(this);
        layer2.setOnClickListener(this);
        layer3.setOnClickListener(this);
        layer4.setOnClickListener(this);

    }

    /**
     * reacts to user click input and responds based on context
     * @param view, object for determining where the user clicked (view)
     */
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.LayersEdit){
            if(editClicked || buttonClicked){
                reset();
            }else{
                editClicked = true;
                buttonClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a layer to edit");
            }

        }
        if (view.getId() == R.id.LayersAdd){
            if(addClicked || buttonClicked){
                reset();
            }
            addClicked = true;
            buttonClicked = true;
            TextView text = findViewById(R.id.ChangeText);
            text.setText("Choose a layer to add to");

        }

        if (view.getId() == R.id.LayersRemove){
            if(removeClicked || buttonClicked){
                reset();
            }else{
                removeClicked = true;
                buttonClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a layer to remove");
            }

        }

        if(view.getId() == R.id.LayersSwap){
            if(swapClicked || buttonClicked){
                reset();
            }else{
                swapClicked = true;
                buttonClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a layer to swap");
            }
        }


        if(view.getId() == R.id.LayersBack){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if(view.getId() == R.id.LayersCreate){
            if(image1 == null && image2 == null && image3 == null && background == null){
                Toast.makeText(view.getContext(),"You can't make an image with nothing!",Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this, CreateCanvasActivity.class);
                intent.putExtra("uri1", Image.saveImageToFile(this, layer1.getDrawable()));
                intent.putExtra("uri2", Image.saveImageToFile(this, layer2.getDrawable()));
                intent.putExtra("uri3", Image.saveImageToFile(this, layer3.getDrawable()));
                intent.putExtra("uriB", Image.saveImageToFile(this, layer4.getDrawable()));
                startActivity(intent);
            }
        }

        if(view.getId() == R.id.LayerImage1){
            image1 = layerClicked(image1, layer1, 1);
        }
        if(view.getId() == R.id.LayerImage2){
            image2 = layerClicked(image2, layer2, 2);
        }
        if(view.getId() == R.id.LayerImage3){
            image3 = layerClicked(image3, layer3, 3);
        }
        if(view.getId() == R.id.LayerImage4) {
            background = layerClicked(background, layer4, 4);
        }
    }

    /**
     * Handles cases where an image layer is clicked
     * @param imageX, bitmap of image clicked (Bitmap)
     * @param layerX, view of image clicked (ImageView)
     * @param ptr, represents the image clicked (int)
     * @return Bitmap, bitmap of image clicked after potential changes
     */
    private Bitmap layerClicked(Bitmap imageX, ImageView layerX, int ptr){
        if(editClicked){
            if(imageX == null){
                Toast.makeText(this,"This image is empty",Toast.LENGTH_SHORT).show();
                reset();
            }else {
                sendToEdit(ptr-1);
            }
        }
        if(removeClicked){
            if(imageX == null){
                Toast.makeText(this,"This image is already empty",Toast.LENGTH_SHORT).show();
                reset();
            }else {
                imageX = null;
                layerX.setImageResource(R.drawable.image_placeholder);
                reset();
            }
        }
        if(swapImageClicked){
            imageX = swapping(imageX);
            bitmapOnLayer(imageX,layerX);
            reset();
        }
        if(swapClicked){
            swapId = ptr;
            swapClicked = false;
            swapImageClicked = true;
            TextView text = findViewById(R.id.ChangeText);
            text.setText("Choose a second layer");
        }
        if(addClicked){
            if(imageX != null){
                Toast.makeText(this,"There is already an image in this layer",Toast.LENGTH_SHORT).show();
                reset();
            }
            else{
                layerPicked = ptr;
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        }
        return imageX;
    }

    /**
     * prepares layers to be sent to EditImageActivity
     * @param ptr, represents image to be edited (int)
     */
    private void sendToEdit(int ptr){
        Intent intent = new Intent(this, EditImageActivity.class);
        intent.putExtra("uri1", Image.saveImageToFile(this, layer1.getDrawable()));
        intent.putExtra("uri2", Image.saveImageToFile(this, layer2.getDrawable()));
        intent.putExtra("uri3", Image.saveImageToFile(this, layer3.getDrawable()));
        intent.putExtra("uriB", Image.saveImageToFile(this, layer4.getDrawable()));
        intent.putExtra("ptrImage",ptr);
        intent.putExtra("source","LayersActivity");
        startActivity(intent);
    }

    /**
     * swaps two layers based on previous input
     * @param toSwap, image to be swapped (Bitmap)
     * @return Bitmap, image after swap
     */
    private Bitmap swapping(Bitmap toSwap){
        Bitmap temp = toSwap;
        switch(swapId){
            case 1:
                toSwap = image1;
                image1 = temp;
                bitmapOnLayer(image1,layer1);
                break;
            case 2:
                toSwap = image2;
                image2 = temp;
                bitmapOnLayer(image2,layer2);
                break;
            case 3:
                toSwap = image3;
                image3 = temp;
                bitmapOnLayer(image3,layer3);
                break;
            case 4:
                toSwap = background;
                background = temp;
                bitmapOnLayer(background,layer4);
                break;
        }
        return toSwap;
    }

    /**
     * loads a bitmap onto a view
     * @param bit, bitmap to be loaded (Bitmap)
     * @param layer, view to be changed (ImageView)
     */
    private void bitmapOnLayer(Bitmap bit, ImageView layer){
        if(bit != null){
            layer.setImageBitmap(bit);
        }else{
            layer.setImageResource(R.drawable.image_placeholder);
        }
    }

    /**
     * resets context of which buttons were clicked
     */
    private void reset(){
        editClicked = false;
        removeClicked = false;
        swapClicked = false;
        swapImageClicked = false;
        addClicked = false;
        buttonClicked = false;
        swapId = 0;
        layerPicked = 0;
        TextView text = findViewById(R.id.ChangeText);
        text.setText("Choose an option");
    }


}