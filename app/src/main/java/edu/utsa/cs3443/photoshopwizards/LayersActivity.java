package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LayersActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean invertDog;
    private static boolean dragon;
    private boolean buttonClicked;
    private int[] buttonIDs;
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

        layer1.setImageResource(R.drawable.dog_background);
        if (invertDog) {
            layer2.setImageResource(R.drawable.dog1_inverted);
        }
        else {
            layer2.setImageResource(R.drawable.dog1);
        }
        layer3.setImageResource(R.drawable.dog2);
        if (dragon){
            layer4.setImageResource(R.drawable.dragon);
        }
        else {
            layer4.setImageResource(R.drawable.dog3);
        }

        layer1.setOnClickListener(this);
        layer2.setOnClickListener(this);
        layer3.setOnClickListener(this);
        layer4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        System.out.println("Hello");

        if (view.getId() == R.id.LayersEdit){
            buttonClicked = true;
            TextView text = findViewById(R.id.ChangeText);
            text.setText("Choose a layer to edit");

        }
        if (view.getId() == R.id.LayersAdd){
            dragon = true;
            setContentView(R.layout.activity_load_canvas);
            TextView text = findViewById(R.id.LoadCanvasText);
            text.setText("Choose an image to add");

            ImageView canvas1 = findViewById(R.id.Canvas1);
            ImageView canvas2 = findViewById(R.id.Canvas2);
            ImageView canvas3 = findViewById(R.id.Canvas3);
            ImageView canvas4 = findViewById(R.id.Canvas4);
            ImageView canvas5 = findViewById(R.id.Canvas5);
            ImageView canvas6 = findViewById(R.id.Canvas6);
            ImageView canvas7 = findViewById(R.id.Canvas7);
            ImageView canvas8 = findViewById(R.id.Canvas8);
            canvas1.setImageResource(R.drawable.dog1);
            canvas2.setImageResource(R.drawable.dog2);
            canvas3.setImageResource(R.drawable.dog3);
            canvas4.setImageResource(R.drawable.mage);
            canvas5.setImageResource(R.drawable.knight);
            canvas6.setImageResource(R.drawable.dragon);

            canvas6.setOnClickListener(this);

            canvas7.setImageResource(R.drawable.skeleton);
            canvas8.setImageResource(R.drawable.blast);

        }
        if (view.getId() == R.id.Canvas6){

            setContentView(R.layout.activity_layers);
            setupImages();
            setupButtons(buttonIDs);

        }

        if (view.getId() == R.id.LayersRemove){
            buttonClicked = true;
            TextView text = findViewById(R.id.ChangeText);
            text.setText("Choose a layer to remove");
        }
        if (view.getId() == R.id.LayerImage4 && buttonClicked)
        {
            ImageView layer4 = findViewById(R.id.LayerImage4);
            layer4.setImageResource(R.drawable.white);
            TextView text = findViewById(R.id.ChangeText);
            text.setText("Choose an option");
        }

        if(view.getId() == R.id.LayersBack){
            Intent intent = new Intent(this, LoadCanvasActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.LayerImage2 && buttonClicked ){
            invertDog = true;
            buttonClicked = false;
            Intent intent = new Intent(this, EditImageActivity.class);
            startActivity(intent);
        }

    }
}