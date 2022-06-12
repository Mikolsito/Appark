package com.example.appark.Activities;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
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
import java.util.Calendar;
import java.util.List;

//Crea set de dades (ara random, despres en base a dades del firebase)
public class EstadistiquesFragment extends Fragment {
    private static final String TAG = "EstadistiquesFragmentDB";
   private LineChart linechart;
   public Bundle instance;
   private LineDataSet lineDataSet;
   private LineDataSet lineDataSet2;
   private BarChart barchart;
   private BarDataSet barDataSet;
   private PieChart piechart;
   private PieDataSet pieDataSet;
   private PieDataSet pieDataSet2;
   EstadistiquesViewModel viewModel;
    private ArrayList<Location> ubis;
    private ArrayList<Estacionament> parkings;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        ArrayList<BarEntry> barEntries2 = new ArrayList<BarEntry>();
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        ubis = new ArrayList<>();
        parkings=new ArrayList<>();
        setLiveDataObservers();
        List<String> barris=new ArrayList<String>();
        barris.addAll(Arrays.asList("Eixample", "Sarrià", "Gracia", "Horta", "Sagrada Familia", "Sant Gervasi", "Poblenou", "Raval", "Sant Marti", "Sant Andreu"));
        //PlacesBarri = new ArrayList<Pair<String, Long>>();
        //viewModel = new ViewModelProvider(this).get(EstadistiquesViewModel.class);
        //viewModel.getPlacesBarrisDB();
       // setLiveDataObservers();
   /*    FirebaseFirestore db = FirebaseFirestore.getInstance();
        //String barri = barris.get(0);
        String barri = "Eixample";
        db.collection("Estacionaments")
                //.whereEqualTo("User_email", MainActivity.currentUser.getMail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Logging the ID of your desired document & the document data itself
                                Log.d(TAG, document.get("dataInicia") + " => " + document.get("tempsAparcat"));
                                float temps=0;
                                temps=(float) document.get("tempsAparcat");
                                //Pair<String, Long> pair = new Pair<String, Long>(barri, (places - placeslliures));
                                //PlacesBarri.add(pair);
                                lineEntries2.add(new Entry((float)count, temps));
                                count+=1;
                                lineDataSet2=new LineDataSet(lineEntries2, "Exemple temps");

                                LineData ll= new LineData(lineDataSet2);


                            }
                            linechart.setData(generateLineData());
                            linechart.invalidate();
                            //Map<String, Object> data = task.getResult().getDocuments().iterator().next().getData();
                            //int places = (int) data.get("places");
                            //int placeslliures = (int) data.get("placeslliures");



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        //linechart.invalidate();
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
            //barEntries.add(new BarEntry((float) i, (float)x));
        }
        //User user=new User("hola", "u@u.com", "pwd");
        //String id= "5";
        //viewModel.createPositionDB(id,user,345, 345);
        //pieDataSet=new PieDataSet(pieEntries, "Exemple zones");
        barDataSet=new BarDataSet(barEntries, "Exemple aparcaments");
        lineDataSet=new LineDataSet(lineEntries,"Exemple temps");
        return inflater.inflate(R.layout.fragment_estadistiques, container, false);
    }
/*
    private void setLiveDataObservers2() {
        viewModel = new ViewModelProvider(this).get(EstadistiquesViewModel.class);


        final Observer<ArrayList<Estacionament>> observer = new Observer<ArrayList<Estacionament>>() {
            @Override
            public void onChanged(ArrayList<Estacionament> parkings) {
                viewModel.getPlacesBarrisDB().observe(this, observer);
            }


        };
        viewModel.getPlacesBarrisDB().observe(getViewLifecycleOwner(), observer);
    }
*/
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        //instance=savedInstanceState;
        // Ligar los graficos con el XML
        linechart=view.findViewById(R.id.linechart);
        barchart=view.findViewById(R.id.barchart);
        piechart=view.findViewById(R.id.piechart);

        piechart.setUsePercentValues(false); // Establecer valores como porcentaje
        piechart.setEntryLabelColor(Color.BLACK); // Establecer el color de la etiqueta dibujada
        piechart.setEntryLabelTextSize(10f); // Establece el tamaño de fuente para dibujar Label

        // Unir set de datos a los graficos
        //barchart.setData(generateBarData());
        //linechart.setData(generateLineData());
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
        lineDataSet2.setColor(Color.rgb(60, 220, 78));
        lineDataSet2.setValueTextColor(Color.rgb(60, 220, 78));
        lineDataSet2.setValueTextSize(10f);
        LineData l=new LineData(lineDataSet2);
        l.setHighlightEnabled(true);
        return l;
    }

    private PieData generatePieData(){
        pieDataSet2.setColors(ColorTemplate.PASTEL_COLORS);
        PieData p= new PieData(pieDataSet2);
        //getView().findViewById(R.id.textView4).setVisibility(View.INVISIBLE);

        return p;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(getActivity()).get(EstadistiquesViewModel.class);

        final Observer<ArrayList<Estacionament>> observer2=new Observer<ArrayList<Estacionament>>() {
            @Override
            public void onChanged(ArrayList<Estacionament> estacionaments) {
                Log.d("OnChangedopo", estacionaments.toString());
                parkings=estacionaments;
                Log.d("OnChangedopo", parkings.get(0).getUserEmail());
                Log.d("OnChangedopAAA", MainActivity.currentUser.getMail());
                ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();
                ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
                Calendar cal=Calendar.getInstance();

                int[] EstacionamentsPerDia = {0, 0, 0, 0, 0, 0, 0};
                for (int f=0; f<parkings.size();f++){
                    if(parkings.get(f).getUserEmail().equals(MainActivity.currentUser.getMail())) {
                        lineEntries2.add(new Entry((float) f, parkings.get(f).getTempsAparcat()));
                    }
                    cal.setTime(parkings.get(f).getDataInici());
                    Integer actualDay=cal.get(Calendar.DAY_OF_WEEK);
                    switch (actualDay){
                        case 1:
                            EstacionamentsPerDia[6]+=1;
                        case 2:EstacionamentsPerDia[0]+=1;
                        case 3:EstacionamentsPerDia[1]+=1;
                        case 4:EstacionamentsPerDia[2]+=1;
                        case 5:EstacionamentsPerDia[3]+=1;
                        case 6:EstacionamentsPerDia[4]+=1;
                        case 7:EstacionamentsPerDia[5]+=1;
                    }

                    Log.d("LOLOLOLAAA", (actualDay.toString()));
                }
                for(int yu=0;yu<EstacionamentsPerDia.length;yu++){
                    barEntries.add(new BarEntry((float) yu, (float)EstacionamentsPerDia[yu]));
                }
                barDataSet=new BarDataSet(barEntries,"Exemple dies");
                barchart.setData(generateBarData());
                barchart.invalidate();
                //Genera el nou Linechart amb les hores aparcades per l'usuari
                lineDataSet2=new LineDataSet(lineEntries2, "Exemple temps");
                LineData ll= new LineData(lineDataSet2);
                linechart.setData(ll);
                linechart.invalidate();

                //barDataSet2=new BarDataSet()
            }
        };

        final Observer<ArrayList<Location>> observer = new Observer<ArrayList<Location>>() {
            @Override
            public void onChanged(ArrayList<Location> latLngLocationList) {
                Log.d("OnChanged", "maps");
                ubis = latLngLocationList;
                ArrayList<PieEntry> pieEntries2 = new ArrayList<PieEntry>();
                List<String> barris=new ArrayList<String>();
                barris.addAll(Arrays.asList("Eixample", "Sarrià", "Gracia", "Horta", "Sagrada Familia", "Sant Gervasi", "Poblenou", "Raval", "Sant Marti", "Sant Andreu"));
                int[] pBarris = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                //Calculem quantes places lliures hi ha a cada barri sumant les de totes les ubis de cada barri
                for (int h=0;h< ubis.size();h++){
                    switch (ubis.get(h).getBarri()){
                        case "Eixample":
                            pBarris[0]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Sarrià":
                            pBarris[1]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Gracia":
                            pBarris[2]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Horta":
                            pBarris[3]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Sagrada Familia":
                            pBarris[4]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Sant Gervasi":
                            pBarris[5]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Poblenou":
                            pBarris[6]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Raval":
                            pBarris[7]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Sant Marti":
                            pBarris[8]+=ubis.get(h).getPlacesLliures();
                            break;
                        case "Sant Andreu":
                            pBarris[9]+=ubis.get(h).getPlacesLliures();
                            break;
                    }

                }
                //Agafem les places lliures totals de cada barri i les afegim al grafic
                for (int k=0;k<=9;k++){
                    if(pBarris[k]!=0){
                        pieEntries2.add(new PieEntry(pBarris[k],barris.get(k)));
                    }
                }
                pieDataSet2=new PieDataSet(pieEntries2, "Exemple zones 2");
                piechart.setData(generatePieData());
                Log.d("Barri", ubis.get(0).getBarri());
                piechart.invalidate();
            }
        };
        viewModel.getUbicacions().observe(getViewLifecycleOwner(), observer);
        viewModel.getEstacionaments().observe(getViewLifecycleOwner(),observer2);
    }

}