package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LayersActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean buttonClicked;
    private boolean editClicked;
    private boolean removeClicked;
    private int[] buttonIDs;
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    private Bitmap background;
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
        ImageView layer1 = findViewById(R.id.LayerImage1);
        ImageView layer2 = findViewById(R.id.LayerImage2);
        ImageView layer3 = findViewById(R.id.LayerImage3);
        ImageView layer4 = findViewById(R.id.LayerImage4);

        //ONLY PLACEHOLDER
        layer1.setImageResource(R.drawable.dog_background);
        layer2.setImageResource(R.drawable.dog1);
        layer3.setImageResource(R.drawable.dog2);
        layer4.setImageResource(R.drawable.dog3);

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
                editClicked = false;
                buttonClicked = false;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose an option");
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
                removeClicked = false;
                buttonClicked = false;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose an option");
            }else{
                removeClicked = true;
                buttonClicked = true;
                TextView text = findViewById(R.id.ChangeText);
                text.setText("Choose a layer to remove");
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
                sendToEdit(0);
            }
        }
        if(view.getId() == R.id.LayerImage2){
            if(editClicked){
                sendToEdit(1);
            }
        }
        if(view.getId() == R.id.LayerImage3){
            if(editClicked){
                sendToEdit(2);
            }
        }
        if(view.getId() == R.id.LayerImage4){
            if(editClicked){
                sendToEdit(3);
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


}