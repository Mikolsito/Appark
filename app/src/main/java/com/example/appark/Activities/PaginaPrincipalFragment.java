package com.example.appark.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appark.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PaginaPrincipalFragment extends Fragment {

    FloatingActionButton mAddFab, mAddHistorial, mAddCotxe;
    TextView addHistorialText, addCotxeText;
    Boolean isAllFabsVisible;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    SeekBar seekBar;
    GoogleMap map;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        View view = inflater.inflate(R.layout.fragment_paginaprincipal, container, false);
        mAddFab = (FloatingActionButton) view.findViewById(R.id.add_fab);
        mAddHistorial = (FloatingActionButton) view.findViewById(R.id.add_historial_fab);
        mAddCotxe = (FloatingActionButton) view.findViewById(R.id.add_cotxe_fab);
        addHistorialText = (TextView) view.findViewById(R.id.text_historial);
        addCotxeText = (TextView) view.findViewById(R.id.tornar_cotxe);
        mAddHistorial.setVisibility(View.GONE);
        mAddCotxe.setVisibility(View.GONE);
        addHistorialText.setVisibility(View.GONE);
        addCotxeText.setVisibility(View.GONE);
        isAllFabsVisible = false;
        seekBar =(SeekBar) view.findViewById(R.id.SeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                Toast.makeText(getView().getContext(), progressChangedValue + " Km de radi", Toast.LENGTH_SHORT).show();
            }

            public void onStartTrackingTouch(SeekBar seekBar) { //Al començar a arrossegar

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Animacions
        fabOpen = AnimationUtils.loadAnimation(view.getContext(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(view.getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(view.getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(view.getContext(),R.anim.rotate_backward);

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
                        Toast.makeText(getView().getContext(), "Falta implementar aquesta funcionalitat", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getView().getContext(), "Falta implementar aquesta funcionalitat", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
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