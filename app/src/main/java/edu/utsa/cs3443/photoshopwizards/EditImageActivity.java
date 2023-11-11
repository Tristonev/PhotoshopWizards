package edu.utsa.cs3443.photoshopwizards;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class EditImageActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);


    }

    @Override
    public void onClick(View view) {
        System.out.println("Hello");
    }
}
