package com.example.appark.Activities;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.RegisterActivityViewModel;
import com.example.appark.Activities.src.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.example.appark.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Crea set de dades (ara random, despres en base a dades del firebase)
public class EstadistiquesFragment extends Fragment {
    private static final String TAG = "EstadistiquesFragmentDB";
    public static ArrayList<Pair<String, Long>> PlacesBarri = new ArrayList<Pair<String, Long>>();
   private LineChart linechart;
   public Bundle instance;
   private LineDataSet lineDataSet;
   private BarChart barchart;
   private BarDataSet barDataSet;
   private PieChart piechart;
   private int Escounter;
   private PieDataSet pieDataSet;
    private PieDataSet pieDataSet2;
    long places;
   EstadistiquesViewModel viewModel;
    private ArrayList<Location> ubis;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        Escounter=0;
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        ubis = new ArrayList<>();
        setLiveDataObservers();
        List<String> barris=new ArrayList<String>();
        barris.addAll(Arrays.asList("Eixample", "Sarrià", "Gracia", "Horta", "Sagrada Familia", "Sant Gervasi", "Poblenou", "Raval", "Sant Marti"));
        //PlacesBarri = new ArrayList<Pair<String, Long>>();
        //viewModel = new ViewModelProvider(this).get(EstadistiquesViewModel.class);
        //viewModel.getPlacesBarrisDB();
       // setLiveDataObservers();
       /* FirebaseFirestore db = FirebaseFirestore.getInstance();
        //String barri = barris.get(0);
        String barri = "Eixample";
        db.collection("Ubicacions")
                .whereEqualTo("barri", barri)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Logging the ID of your desired document & the document data itself
                                Log.d(TAG, document.get("places") + " => " + document.get("placeslliures"));
                                //long places=0;
                                long placeslliures=0;
                                places=(long) document.get("places");
                                placeslliures=(long) document.get("placeslliures");
                                //Pair<String, Long> pair = new Pair<String, Long>(barri, (places - placeslliures));
                                //PlacesBarri.add(pair);
                                pieEntries.add(new PieEntry((int)places,"Eixample"));
                                pieDataSet=new PieDataSet(pieEntries, "Exemple zones");

                                PieData pp= new PieData(pieDataSet);
                                piechart.setData(pp);
                            }
                            //Map<String, Object> data = task.getResult().getDocuments().iterator().next().getData();
                            //int places = (int) data.get("places");
                            //int placeslliures = (int) data.get("placeslliures");



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

*/

        for (int i = 0; i< 8; i++){
            float y = (int) (Math.random()*8)+1;
            float x = (int) (Math.random()*8)+1;
            long z =  (int) (Math.random()*8)+1;//PlacesBarri.get(0).second;
            //Integer Splaces=ubis.get(0).getPlaces();
            //Log.d(TAG, ((Splaces.toString())));
            //long places=0;
            pieEntries.add(new PieEntry((float)z,"Eixample"));
            lineEntries.add(new Entry((float) i,(float)y));
            barEntries.add(new BarEntry((float) i, (float)x));
        }
        //User user=new User("hola", "u@u.com", "pwd");
        //String id= "5";
        //viewModel.createPositionDB(id,user,345, 345);
        pieDataSet=new PieDataSet(pieEntries, "Exemple zones");
        barDataSet=new BarDataSet(barEntries, "Exemple aparcaments");
        lineDataSet=new LineDataSet(lineEntries,"Exemple temps");
        return inflater.inflate(R.layout.fragment_estadistiques, container, false);
    }
/*
    private void setLiveDataObservers() {
        viewModel = new ViewModelProvider(this).get(EstadistiquesViewModel.class);


        final Observer<ArrayList<Pair<String, Long>>> observer = new Observer<ArrayList<Pair<String, Long>>>() {
            @Override
            public void onChanged(ArrayList<Pair<String, Long>> pairs) {
                viewModel.getPlacesBarrisDB().observe(this, observer);
            }


        };
        viewModel.getPlacesBarrisDB().observe(getViewLifecycleOwner(), observer);
    }
*/
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        instance=savedInstanceState;
        // Ligar los graficos con el XML
        linechart=view.findViewById(R.id.linechart);
        barchart=view.findViewById(R.id.barchart);
        piechart=view.findViewById(R.id.piechart);

        piechart.setUsePercentValues(false); // Establecer valores como porcentaje
        piechart.setEntryLabelColor(Color.BLACK); // Establecer el color de la etiqueta dibujada
        piechart.setEntryLabelTextSize(10f); // Establece el tamaño de fuente para dibujar Label

        // Unir set de datos a los graficos
        barchart.setData(generateBarData());
        linechart.setData(generateLineData());
        //piechart.setData(generatePieData());
       /* while (ubis.isEmpty()) {
            if(ubis.isEmpty()){

            }
            else{
                String Splaces = ubis.get(0).getNom();
                Log.d(TAG, ((Splaces.toString())));
            }

        }
            /*
       viewModel = new ViewModelProvider(this).get(EstadistiquesViewModel.class);
        //User user=new User("hola", "u@u.com", "pwd");
        //String id= "7";
        //viewModel.createPositionDB(id,user,30, 45);
        viewModel.getPlacesBarrisDB();
        ArrayList<PieEntry> pieEntries2 = new ArrayList<PieEntry>();
        for (int j=0; j<1; j++){
            pieEntries2.add(new PieEntry(PlacesBarri.get(j).second,PlacesBarri.get(j).first));
        }
        pieDataSet2=new PieDataSet(pieEntries2, "Exemple zones 2");
        pieDataSet2.setColors(ColorTemplate.PASTEL_COLORS);
        PieData pp= new PieData(pieDataSet2);
        piechart.setData(pp);
          */



    }

    private BarData generateBarData() {
        barDataSet.setColor(Color.rgb(60, 220, 78));
        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.rgb(60, 220, 78));
        barDataSet.setValueTextSize(10f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.35f; // x2 dataset

        BarData d = new BarData(barDataSet);
        d.setBarWidth(barWidth);
        return d;
    }
    private LineData generateLineData(){
        lineDataSet.setColor(Color.rgb(60, 220, 78));
        lineDataSet.setValueTextColor(Color.rgb(60, 220, 78));
        lineDataSet.setValueTextSize(10f);
        LineData l=new LineData(lineDataSet);
        l.setHighlightEnabled(true);
        return l;
    }

    private PieData generatePieData(){
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData p= new PieData(pieDataSet2);
        //getView().findViewById(R.id.textView4).setVisibility(View.INVISIBLE);

        return p;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(getActivity()).get(EstadistiquesViewModel.class);

        final Observer<ArrayList<Location>> observer = new Observer<ArrayList<Location>>() {
            @Override
            public void onChanged(ArrayList<Location> latLngLocationList) {
                Log.d("OnChanged", "maps");
                ubis = latLngLocationList;
                ArrayList<PieEntry> pieEntries2 = new ArrayList<PieEntry>();
                for (int h=0;h< ubis.size();h++){

                    pieEntries2.add(new PieEntry(ubis.get(h).getPlacesLliures(),"Eixample"));

                }
                pieDataSet2=new PieDataSet(pieEntries2, "Exemple zones 2");
                piechart.setData(generatePieData());
                Log.d("Barri", ubis.get(0).getBarri());
                piechart.invalidate();

            }
        };
        viewModel.getUbicacions().observe(getViewLifecycleOwner(), observer);
    }

}