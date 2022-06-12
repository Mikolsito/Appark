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

import com.example.appark.Activities.src.EstacioEst;
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
    private ArrayList<EstacioEst> parkings;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ubis = new ArrayList<>();
        parkings=new ArrayList<>();
        setLiveDataObservers();
        List<String> barris=new ArrayList<String>();
        barris.addAll(Arrays.asList("Eixample", "Sarrià", "Gracia", "Horta", "Sagrada Familia", "Sant Gervasi", "Poblenou", "Raval", "Sant Marti", "Sant Andreu"));
        return inflater.inflate(R.layout.fragment_estadistiques, container, false);

    }

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

        final Observer<ArrayList<EstacioEst>> observer2=new Observer<ArrayList<EstacioEst>>() {
            @Override
            public void onChanged(ArrayList<EstacioEst> estacionaments) {
                parkings=estacionaments;
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
                        case (1):
                            EstacionamentsPerDia[6]+=1;
                            break;
                        case (2):
                            EstacionamentsPerDia[0]+=1;
                            break;
                        case (3):
                            EstacionamentsPerDia[1]+=1;
                            break;
                        case (4):
                            EstacionamentsPerDia[2]+=1;
                            break;
                        case (5):
                            EstacionamentsPerDia[3]+=1;
                            break;
                        case (6):
                            EstacionamentsPerDia[4]+=1;
                            break;
                        case (7):
                            EstacionamentsPerDia[5]+=1;
                            break;
                    }
                }
                for(int yu=0;yu<EstacionamentsPerDia.length;yu++){
                    barEntries.add(new BarEntry((float) yu,(float)EstacionamentsPerDia[yu]));
                }
                //Genera el nou BarChart amb
                barDataSet=new BarDataSet(barEntries,"Exemple dies");
                barchart.setData(generateBarData());
                barchart.invalidate();
                //Genera el nou Linechart amb les hores aparcades per l'usuari
                lineDataSet2=new LineDataSet(lineEntries2, "Exemple temps");
                LineData ll= new LineData(lineDataSet2);
                linechart.setData(ll);
                linechart.invalidate();

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