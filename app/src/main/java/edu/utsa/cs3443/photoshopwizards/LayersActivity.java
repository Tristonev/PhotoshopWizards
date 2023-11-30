package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LayersActivity extends AppCompatActivity implements View.OnClickListener {

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
    private int swapId;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layers);
        buttonIDs = new int[]{R.id.LayersAdd, R.id.LayersEdit, R.id.LayersRemove, R.id.LayersSwap, R.id.LayersBack, R.id.LayersCreate};
        setupButtons(buttonIDs);

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

        //ONLY PLACEHOLDER
        layer1.setImageResource(R.drawable.image_placeholder);
        layer2.setImageResource(R.drawable.image_placeholder);
        layer3.setImageResource(R.drawable.image_placeholder);
        layer4.setImageResource(R.drawable.image_placeholder);

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
            buttonClicked = true;
            setContentView(R.layout.activity_load_canvas);
            TextView text = findViewById(R.id.LoadCanvasText);
            text.setText("Choose an image to add");

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
        buttonClicked = false;
        swapId = 0;
        TextView text = findViewById(R.id.ChangeText);
        text.setText("Choose an option");
    }


}