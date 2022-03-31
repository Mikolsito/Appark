package com.example.appark;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.appark.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends FragmentActivity {

    FloatingActionButton mAddFab, mAddHistorial, mAddCotxe;
    TextView addHistorialText, addCotxeText;
    Boolean isAllFabsVisible;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddFab = findViewById(R.id.add_fab);
        mAddHistorial = findViewById(R.id.add_historial_fab);
        mAddCotxe = findViewById(R.id.add_cotxe_fab);
        addHistorialText = findViewById(R.id.historial);
        addCotxeText = findViewById(R.id.tornar_cotxe);
        mAddHistorial.setVisibility(View.GONE);
        mAddCotxe.setVisibility(View.GONE);
        addHistorialText.setVisibility(View.GONE);
        addCotxeText.setVisibility(View.GONE);
        isAllFabsVisible = false;
        //Animacions
        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        mAddFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Primer veiem l'animació
                        animateFab();
                        if (!isAllFabsVisible) {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs VISIBLE.
                            mAddHistorial.show();
                            mAddCotxe.show();
                            addHistorialText.setVisibility(View.VISIBLE);
                            addCotxeText.setVisibility(View.VISIBLE);

                            // make the boolean variable true as
                            // we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = true;
                        } else {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs GONE.
                            mAddHistorial.hide();
                            mAddCotxe.hide();
                            addHistorialText.setVisibility(View.GONE);
                            addCotxeText.setVisibility(View.GONE);

                            // make the boolean variable false
                            // as we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });

        mAddCotxe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Falta implementar aquesta funcionalitat", Toast.LENGTH_SHORT).show();
                    }
                });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mAddHistorial.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Falta implementar aquesta funcionalitat", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void animateFab(){
        if (isAllFabsVisible){
            mAddFab.startAnimation(rotateBackward);
            mAddHistorial.startAnimation(fabClose);
            mAddCotxe.startAnimation(fabClose);
            mAddHistorial.setClickable(false);
            mAddCotxe.setClickable(false);
        }else {
            mAddFab.startAnimation(rotateForward);
            mAddHistorial.startAnimation(fabOpen);
            mAddCotxe.startAnimation(fabOpen);
            mAddHistorial.setClickable(true);
            mAddCotxe.setClickable(true);
        }
    }

}