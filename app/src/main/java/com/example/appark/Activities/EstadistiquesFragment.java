package com.example.appark.Activities;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Crea set de dades (ara random, despres en base a dades del firebase)
public class EstadistiquesFragment extends Fragment {

   private LineChart linechart;
   private LineDataSet lineDataSet;
   private BarChart barchart;
   private BarDataSet barDataSet;
   private PieChart piechart;
   private PieDataSet pieDataSet;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        List<String> barris=new ArrayList<String>();
        barris.addAll(Arrays.asList("Eixample", "Sarrià", "Gracia", "Horta", "Sagrada Familia", "Sant Gervasi", "Poblenou", "Raval", "Sant Marti"));

        for (int i = 0; i<8; i++){
            float y = (int) (Math.random()*8)+1;
            float x = (int) (Math.random()*8)+1;
            float z = (int) (Math.random()*8)+1;

            pieEntries.add(new PieEntry(z,barris.get(i)));
            lineEntries.add(new Entry((float) i,(float)y));
            barEntries.add(new BarEntry((float) i, (float)x));
        }
        pieDataSet=new PieDataSet(pieEntries, "Exemple zones");
        barDataSet=new BarDataSet(barEntries, "Exemple aparcaments");
        lineDataSet=new LineDataSet(lineEntries,"Exemple temps");
        return inflater.inflate(R.layout.fragment_estadistiques, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        // Ligar los graficos con al XML
        linechart=view.findViewById(R.id.linechart);
        barchart=view.findViewById(R.id.barchart);
        piechart=view.findViewById(R.id.piechart);

        piechart.setUsePercentValues(true); // Establecer valores como porcentaje
        piechart.setEntryLabelColor(Color.BLACK); // Establecer el color de la etiqueta dibujada
        piechart.setEntryLabelTextSize(10f); // Establece el tamaño de fuente para dibujar Label

        // Unir set de datos a los graficos
        barchart.setData(generateBarData());
        linechart.setData(generateLineData());
        piechart.setData(generatePieData());
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
        PieData p= new PieData(pieDataSet);
        //getView().findViewById(R.id.textView4).setVisibility(View.INVISIBLE);

        return p;
    }

}