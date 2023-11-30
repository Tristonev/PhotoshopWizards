package edu.utsa.cs3443.photoshopwizards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileNotFoundException;

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
                //Calls back after the user selects a media item or closes the
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

    private void setupButtons(int[] buttonIDs){
        for (int id : buttonIDs){
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

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

    @Override
    public void onClick(View view) {
        System.out.println("Hello");

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
            Intent intent = new Intent(this, LoadCanvasActivity.class);
            startActivity(intent);
        }

        if(view.getId() == R.id.LayersCreate){
            Intent intent = new Intent(this, LayersActivity.class);
            intent.putExtra("image1",image1);
            intent.putExtra("image2",image2);
            intent.putExtra("image3",image3);
            intent.putExtra("background",background);
            startActivity(intent);
        }

        if(view.getId() == R.id.LayerImage1){
            if(editClicked){
                if(image1 == null){
                    Toast.makeText(this,"This image is empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    sendToEdit(0);
                }
            }
            if(removeClicked){
                if(image1 == null){
                    Toast.makeText(this,"This image is already empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    image1 = null;
                    layer1.setImageResource(R.drawable.image_placeholder);
                    reset();
                }
            }
            if(swapClicked){
                swapId = 1;
                swapClicked = false;
                swapImageClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a second layer");
            }
            if(swapImageClicked){
                image1 = swaping(image1);
                bitmapOnLayer(image1,layer1);
            }
            if(addClicked){
                if(image1 != null){
                    Toast.makeText(this,"There is already an image in this layer",Toast.LENGTH_SHORT).show();
                    reset();
                }
                else{
                    layerPicked = 1;
                    pickMedia.launch(new PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                            .build());
                }
            }
        }
        if(view.getId() == R.id.LayerImage2){
            if(editClicked){
                if(image2 == null){
                    Toast.makeText(this,"This image is empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    sendToEdit(1);
                }
            }
            if(removeClicked){
                if(image2 == null){
                    Toast.makeText(this,"This image is already empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    image2 = null;
                    layer2.setImageResource(R.drawable.image_placeholder);
                    reset();
                }
            }
            if(swapClicked){
                swapId = 2;
                swapClicked = false;
                swapImageClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a second layer");
            }
            if(swapImageClicked){
                image2 = swaping(image2);
                bitmapOnLayer(image2,layer2);
            }
            if(addClicked) {
                if (image2 != null) {
                    Toast.makeText(this, "There is already an image in this layer", Toast.LENGTH_SHORT).show();
                    reset();
                } else {
                    layerPicked = 2;
                    pickMedia.launch(new PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                            .build());
                }
            }
        }
        if(view.getId() == R.id.LayerImage3){
            if(editClicked){
                if(image3 == null){
                    Toast.makeText(this,"This image is empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    sendToEdit(2);
                }
            }
            if(removeClicked){
                if(image3 == null){
                    Toast.makeText(this,"This image is already empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    image3 = null;
                    layer3.setImageResource(R.drawable.image_placeholder);
                    reset();
                }
            }
            if(swapClicked){
                swapId = 3;
                swapClicked = false;
                swapImageClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a second layer");
            }
            if(swapImageClicked){
                image3 = swaping(image3);
                bitmapOnLayer(image3,layer3);
            }
            if(addClicked) {
                if (image3 != null) {
                    Toast.makeText(this, "There is already an image in this layer", Toast.LENGTH_SHORT).show();
                    reset();
                } else {
                    layerPicked = 3;
                    pickMedia.launch(new PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                            .build());
                }
            }
        }
        if(view.getId() == R.id.LayerImage4){
            if(editClicked){
                if(background == null){
                    Toast.makeText(this,"This image is empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    sendToEdit(3);
                }
            }
            if(removeClicked){
                if(background == null){
                    Toast.makeText(this,"This image is already empty",Toast.LENGTH_SHORT).show();
                    reset();
                }else {
                    background = null;
                    layer4.setImageResource(R.drawable.image_placeholder);
                    reset();
                }
            }
            if(swapClicked){
                swapId = 4;
                swapClicked = false;
                swapImageClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a second layer");
            }
            if(swapImageClicked){
                background = swaping(background);
                bitmapOnLayer(background,layer4);
            }
            if(addClicked) {
                if (background != null) {
                    Toast.makeText(this, "There is already an image in this layer", Toast.LENGTH_SHORT).show();
                    reset();
                } else {
                    layerPicked = 4;
                    pickMedia.launch(new PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                            .build());
                }
            }
        }
    }

    private void sendToEdit(int ptr){
        Intent intent = new Intent(this, EditImageActivity.class);
        intent.putExtra("image1",image1);
        intent.putExtra("image2",image2);
        intent.putExtra("image3",image3);
        intent.putExtra("background",background);
        intent.putExtra("ptrImage",ptr);
        startActivity(intent);
    }

    private Bitmap swaping(Bitmap toSwap){
        Bitmap temp = toSwap;
        switch(swapId){
            case 1:
                toSwap = image1;
                image1 = temp;
                bitmapOnLayer(image1,layer1);
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

    private void bitmapOnLayer(Bitmap bit, ImageView layer){
        if(bit != null){
            layer.setImageBitmap(bit);
        }else{
            layer.setImageResource(R.drawable.image_placeholder);
        }
    }



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