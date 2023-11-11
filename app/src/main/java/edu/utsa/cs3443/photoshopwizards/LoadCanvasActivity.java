package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadCanvasActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_canvas);

        int[] imageIDs = {R.id.Canvas1, R.id.Canvas2, R.id.Canvas3, R.id.Canvas4, R.id.Canvas5, R.id.Canvas6, R.id.Canvas7, R.id.Canvas8,
                R.id.Canvas9, R.id.Canvas10, R.id.Canvas11, R.id.Canvas12};
        setupImages(imageIDs);


    }

    private void setupImages(int[] imageIDs){
        for (int id : imageIDs){
            ImageView canvas = findViewById(id);
            canvas.setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View view) {
        System.out.println("Hello");
        launchActivity(view);
    }


    private void launchActivity(View view) {

            Intent intent = new Intent(this, LayersActivity.class);
            startActivity(intent);

    }



}

