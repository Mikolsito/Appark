package com.example.appark.Activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appark.Activities.src.Location;
import com.example.appark.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaginaPrincipalFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private UiSettings mUiSettings;
    private GoogleMap mMap;
    private FloatingActionButton mAddFab, mAddHistorial, mAddCotxe;
    private TextView addHistorialText, addCotxeText;
    private Boolean isAllFabsVisible;
    private Animation fabOpen, fabClose, rotateForward, rotateBackward;
    private SeekBar seekBar;
    private ArrayList<Location> ubis;
    private PaginaPrincipalViewModel viewModel;

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
        ubis = new ArrayList<>();
        ubis.add(new Location("xd", 30, 45, 23, 9, "Eixample"));
        setLiveDataObservers();


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

        mAddCotxe.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        //Per futures implementacions
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        View popup = getLayoutInflater().inflate(R.layout.popup_layout, null);
        PopupWindow popupWindow = new PopupWindow(popup, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(getView().findViewById(R.id.map), Gravity.CENTER, 0, 0);

        /* Trobar items afegits al layout
        // TextInputLayout saveDescr = popup.findViewById(R.id.note_description);
        //Button saveButton = popup.findViewById(R.id.save_button);
        saveButton.setOnClickListener((v) -> {
            String text = saveDescr.getEditText().getText().toString();
            Toast.makeText(getView().getContext(), "Ubicació Guardada", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });
         */
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mUiSettings = mMap.getUiSettings();
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubis.get(0).getLatLng(), 20));  //Activem un zoom inicial a la primera ubicacio
        mUiSettings.setZoomGesturesEnabled(true);   //Activa doble tap per fer zoom
        showUbis();
    }

    public void showUbis() {
        for(Location u: ubis) {
            mMap.addMarker(new MarkerOptions().position(u.getLatLng()));
        }
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(getActivity()).get(PaginaPrincipalViewModel.class);

        final Observer<ArrayList<Location>> observer = new Observer<ArrayList<Location>>() {
            @Override
            public void onChanged(ArrayList<Location> latLngLocationList) {
                Log.d("PAGINA PRINCIPAL FRAGMENT", "OnChangedListener");
                ubis = latLngLocationList;
            }
        };
        viewModel.getUbicacions().observe(getViewLifecycleOwner(), observer);
    }
}