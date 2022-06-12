package com.example.appark.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appark.Activities.src.Location;
import com.example.appark.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PaginaPrincipalFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {
    public static final String TAG = "PaginaPrincipalFragment";

    private UiSettings mUiSettings;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMapç;
    private FloatingActionButton mAddFab, mAddHistorial, mAddCotxe;
    private TextView addHistorialText, addCotxeText;
    private Boolean isAllFabsVisible;
    private Animation fabOpen, fabClose, rotateForward, rotateBackward;
    private SeekBar seekBar;
    private ArrayList<Location> ubis;
    private PaginaPrincipalViewModel viewModel;
    //private FusedLocationProviderClient client;
    LocationManager locationManager;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private int placesTotals;
    private int placesLliures;

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
            }

            public void onStartTrackingTouch(SeekBar seekBar) { //Al començar a arrossegar
                //progressChangedValue = seekBar.getProgress();
                //Toast.makeText(getView().getContext(), progressChangedValue + " Km de radi", Toast.LENGTH_SHORT).show();
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                progressChangedValue = seekBar.getProgress();
                Toast.makeText(getView().getContext(), progressChangedValue + " Km de radi", Toast.LENGTH_SHORT).show();
            }
        });
        ubis = new ArrayList<>();

        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

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


        TextView titleUbicacio = popup.findViewById(R.id.titleUbicacio);
        titleUbicacio.setText(marker.getTitle());
        TextView tilePlacesLliures = popup.findViewById(R.id.titlePlacesLliures);
        tilePlacesLliures.setText("Places lliures: " + marker.getTag().toString());


        Button reserveButton = popup.findViewById(R.id.reserve_button);
        reserveButton.setOnClickListener((v) -> {
            viewModel.createEstacionament(marker.getTitle());
            marker.setTag((Integer) marker.getTag() - 1);
            Toast.makeText(getActivity(), "Aparcament reservat: tens 15 minuts per arribar a la plaça", Toast.LENGTH_LONG).show();
            popupWindow.dismiss();
        });
        Button iniciarRuta = popup.findViewById(R.id.ruta_button);
        iniciarRuta.setOnClickListener((v) -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + marker.getPosition().latitude + "," +
                    marker.getPosition().longitude + marker.getTitle());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            this.getContext().startActivity(mapIntent);
        });
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAPA", "maps");
        mMapç = googleMap;
        mMapç.setOnMarkerClickListener(this);
        mMapç.setOnInfoWindowClickListener(this);
        mUiSettings = mMapç.getUiSettings();
        mMapç.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMapç.setIndoorEnabled(false);

        for (int i = 0; i < 100000; i++); //delay

        mUiSettings.setZoomGesturesEnabled(true);   //Activa doble tap per fer zoom
        mUiSettings.setMapToolbarEnabled(true);


        showUbis();

        if(mMapç != null){
            if (ContextCompat.checkSelfPermission(this.getContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }

            if (!mLocationPermissionGranted) {
                Toast.makeText(this.getActivity(), "The user has not granted location permission.", Toast.LENGTH_LONG).show();
                Log.d(TAG, "errorMessage");
                return;
            }
            if(mLocationPermissionGranted){
                mMapç.setMyLocationEnabled(true); //permitimos seguimeinto localizacion

                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull android.location.Location location) {
                        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMapç.addMarker(new MarkerOptions().position(myLocation).title("Mi posicion"));
                        mMapç.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));  //Activem un zoom inicial a la primera ubicacio
                    }
                };
            }
        }


    }

    public void showUbis() {
        Log.d(TAG, "showUbis");
        for(Location u: ubis) {
            Marker marker = mMapç.addMarker(new MarkerOptions().position(u.getLatLng()).title(u.getNom()));
            marker.setTag(u.getPlacesLliures());
        }
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(getActivity()).get(PaginaPrincipalViewModel.class);

        final Observer<ArrayList<Location>> observer = new Observer<ArrayList<Location>>() {
            @Override
            public void onChanged(ArrayList<Location> latLngLocationList) {
                //Log.d("OnChanged", "maps");
                ubis = latLngLocationList;
            }
        };

        viewModel.getUbicacions().observe(getViewLifecycleOwner(), observer);

    }
}