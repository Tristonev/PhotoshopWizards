package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadCanvasActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean invertDog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_canvas);

        int[] imageIDs = {R.id.Canvas1, R.id.Canvas2, R.id.Canvas3, R.id.Canvas4, R.id.Canvas5, R.id.Canvas6, R.id.Canvas7, R.id.Canvas8,
                R.id.Canvas9, R.id.Canvas10, R.id.Canvas11, R.id.Canvas12};
        setupImages(imageIDs);

        ImageView canvas1 = findViewById(R.id.Canvas1);
        ImageView canvas2 = findViewById(R.id.Canvas2);
        ImageView canvas3 = findViewById(R.id.Canvas3);
        ImageView canvas4 = findViewById(R.id.Canvas4);
        ImageView canvas5 = findViewById(R.id.Canvas5);
        ImageView canvas6 = findViewById(R.id.Canvas6);

        if(invertDog == true)
        {
            canvas1.setImageResource(R.drawable.invert_canvas);
        }
        else {
            canvas1.setImageResource(R.drawable.dog_canvas);
        }
        canvas2.setImageResource(R.drawable.dog_canvas2);
        canvas3.setImageResource(R.drawable.skele_canvas);
        canvas4.setImageResource(R.drawable.skele_canvas2);
        canvas5.setImageResource(R.drawable.fantasy_canvas);
        canvas6.setImageResource(R.drawable.fantasy_canvas2);


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
            invertDog = true;
            Intent intent = new Intent(this, LayersActivity.class);
            startActivity(intent);

    }



}

