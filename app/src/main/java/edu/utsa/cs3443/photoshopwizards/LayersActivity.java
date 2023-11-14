package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LayersActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layers);
        int[] buttonIDs = {R.id.LayersAdd, R.id.LayersEdit, R.id.LayersRemove, R.id.LayersSwap, R.id.LayersBack};
        setupButtons(buttonIDs);


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
            Intent intent = new Intent(this, EditImageActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.LayersBack){
            Intent intent = new Intent(this, LoadCanvasActivity.class);
            startActivity(intent);
        }

    }
}