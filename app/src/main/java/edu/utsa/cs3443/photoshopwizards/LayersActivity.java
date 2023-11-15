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
    private boolean editClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layers);
        int[] buttonIDs = {R.id.LayersAdd, R.id.LayersEdit, R.id.LayersRemove, R.id.LayersSwap, R.id.LayersBack};
        setupButtons(buttonIDs);

        ImageView layer1 = findViewById(R.id.LayerImage1);
        ImageView layer2 = findViewById(R.id.LayerImage2);
        ImageView layer3 = findViewById(R.id.LayerImage3);
        ImageView layer4 = findViewById(R.id.LayerImage4);

        layer1.setImageResource(R.drawable.dog_background);
        if (invertDog == true) {
            layer2.setImageResource(R.drawable.dog1_inverted);
        }
        else {
            layer2.setImageResource(R.drawable.dog1);
        }
        layer3.setImageResource(R.drawable.dog2);
        layer4.setImageResource(R.drawable.dog3);

        layer2.setOnClickListener(this);

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

        if (view.getId() == R.id.LayersEdit){
            editClicked = true;
            TextView text = findViewById(R.id.ChangeText);
            text.setText("Choose a layer to edit");


        }
        if(view.getId() == R.id.LayersBack){
            Intent intent = new Intent(this, LoadCanvasActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.LayerImage2 && editClicked == true ){
            invertDog = true;
            Intent intent = new Intent(this, EditImageActivity.class);
            startActivity(intent);
        }

    }
}