package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] buttons = {R.id.MainCreate, R.id.MainEdit};

        setupButtons(buttons);

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
        launchActivity(view);
    }


    private void launchActivity(View view) {
        if (view.getId() == R.id.MainCreate){
            Intent intent = new Intent(this, LayersActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.MainEdit){
            Intent intent = new Intent(this, EditImageActivity.class);
            startActivity(intent);
        }
    }

}
