package edu.utsa.cs3443.photoshopwizards;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.photoshopwizards.model.Image;

/**
 * The MainActivity class serves as a menu to either create a canvas or edit a single image
 * @author
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int[] samples;

    /**
     * initializes the screen
     * @param savedInstanceState, used for designating the instance (Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] buttons = {R.id.MainCreate, R.id.MainEdit, R.id.downloadButton};
        samples = new int[] {R.drawable.blast, R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog_background, R.drawable.dragon,
                R.drawable.knight, R.drawable.mage, R.drawable.picture_magic_logo, R.drawable.skeleton};

        setupButtons(buttons);


    }

    /**
     * initializes an array of button objects
     * @param buttonIDs, refers to multiple button object (int[])
     */
    private void setupButtons(int[] buttonIDs){
        for (int id : buttonIDs){
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

    /**
     * Saves the sample images to the gallery
     * @param drawableIDs, refers to multiple drawable IDs
     */
    private void downloadSamples(int[] drawableIDs){
        ImageView view = findViewById(R.id.MainWizard);

        for(int id : drawableIDs){
            view.setImageResource(id);
            try {
                Image.saveImage(this, view.getDrawable());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        view.setImageResource(R.drawable.picture_magic_logo);

    }


    /**
     * reacts to user click input
     * @param view, object for determining where the user clicked (view)
     */
    @Override
    public void onClick(View view) {
        launchActivity(view);
    }

    /**
     * changes screen based on given view
     * @param view, object for determining where the user clicked (view)
     */
    private void launchActivity(View view) {
        if (view.getId() == R.id.MainCreate){
            Intent intent = new Intent(this, LayersActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.MainEdit){
            Intent intent = new Intent(this, EditImageActivity.class);
            intent.putExtra("source","MainActivity");
            startActivity(intent);
        }
        if(view.getId() == R.id.downloadButton){
            downloadSamples(samples);
        }
    }

    /**
     * prevents the user from using the back button to access other activities
     */
    @Override
    public void onBackPressed() {
        if (true) {
            return;
        }
        super.onBackPressed();
    }
}
