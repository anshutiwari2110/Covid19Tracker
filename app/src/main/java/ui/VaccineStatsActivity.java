package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anshutiwari.covid19tracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.SliderAdapter;

import static ui.StatisticsActivity.labelDate;

public class VaccineStatsActivity extends AppCompatActivity {

    BarChart vaccineChart;
    BarChart fullyVaccinatedChart;
    TextView mTvTotalVaccine,mTvTotalRegister,mTvFirstDose,mTvSecondDose;
    ArrayList<Float> firstDose;
    ArrayList<Float> secondDose;
    ArrayList<BarEntry> firstBar;
    ArrayList<BarEntry> secondBar;
    SliderView sliderView;
    String totalVaccine,firstDostVaccine,secondDoseVaccine,totalRegister;
    String updateTime;

    int[] images = {R.drawable.vaccine_1,R.drawable.vaccine_2,R.drawable.vaccine_3,R.drawable.vaccine_4,R.drawable.vaccine_5,R.drawable.vaccine_6, R.drawable.vaccine_7};
    SliderAdapter sliderAdapter;
    ArrayList<String> labelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_stats);

        vaccineChart = findViewById(R.id.vaccine_chart_first_dose);
        fullyVaccinatedChart = findViewById(R.id.vaccine_chart_both_dose);
        mTvTotalVaccine = findViewById(R.id.tv_total_vaccine_no);
        mTvFirstDose = findViewById(R.id.tv_first_dose_no);
        mTvSecondDose = findViewById(R.id.tv_second_dose_no);
        mTvTotalRegister = findViewById(R.id.tv_total_register_no);
        sliderView = findViewById(R.id.slider_memories);

        sliderAdapter = new SliderAdapter(images,VaccineStatsActivity.this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

        firstDose = new ArrayList<>();
        secondDose = new ArrayList<>();
        firstBar = new ArrayList<>();
        secondBar = new ArrayList<>();
        labelName = new ArrayList<>();
        labelName.add("Frontline Worker");
        labelName.add("Healthcare Worker");
        labelName.add("Above 60 age");
        labelName.add("Above 45 age");
        getVaccineStats();

    }

    private void getVaccineStats() {
        RequestQueue queue = Volley.newRequestQueue(VaccineStatsActivity.this);
        String url = "https://api.covid19india.org/data.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tested");

                    for (int i = jsonArray.length()-3; i == jsonArray.length()-3; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        float fFirstFront = Float.parseFloat(object.optString("frontlineworkersvaccinated1stdose"));
                        float fFirstHealth = Float.parseFloat(object.optString("healthcareworkersvaccinated1stdose"));
                        float fFirstAbove60 = Float.parseFloat(object.optString("over60years1stdose"));
                        float fFirstAbove45 = Float.parseFloat(object.optString("over45years1stdose"));
                        float fSecondFront = Float.parseFloat(object.optString("frontlineworkersvaccinated2nddose"));
                        float fSecondHealth = Float.parseFloat(object.optString("healthcareworkersvaccinated2nddose"));
                        float fSecondAbove45 = Float.parseFloat(object.optString("over45years2nddose"));
                        float fSecondAbove60 = Float.parseFloat(object.optString("over60years2nddose"));

                        totalVaccine = (object.optString("totaldosesadministered"));
                        totalRegister = (object.optString("totalindividualsregistered"));
                        firstDostVaccine = (object.optString("firstdoseadministered"));
                        secondDoseVaccine = (object.optString("seconddoseadministered"));

                        mTvTotalVaccine.setText(totalVaccine);
                        mTvFirstDose.setText(firstDostVaccine);
                        mTvSecondDose.setText(secondDoseVaccine);
                        mTvTotalRegister.setText(totalRegister);

                        firstDose.add(fFirstFront);
                        firstDose.add(fFirstHealth);
                        firstDose.add(fFirstAbove60);
                        firstDose.add(fFirstAbove45);

                        secondDose.add(fSecondFront);
                        secondDose.add(fSecondHealth);
                        secondDose.add(fSecondAbove60);
                        secondDose.add(fSecondAbove45);

                        updateTime = object.optString("updatetimestamp");
                    }

                    getBarChart(updateTime);
                    getFullyVaccinatedBarChart();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onResponse", error.toString());
            }
        });
        queue.add(request);
    }

    private void getFullyVaccinatedBarChart(){
        for (int i = 0; i < 4; i++) {
            float cases = secondDose.get(i);
            secondBar.add(new BarEntry(i, (float) cases));

        }


        BarDataSet barDataSet = new BarDataSet(secondBar, "Vaccinated with first dose");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.4f);
        fullyVaccinatedChart.setData(barData);
        fullyVaccinatedChart.getAxisLeft().setDrawGridLines(false);
        fullyVaccinatedChart.getAxisRight().setDrawLabels(false);
        Description description = new Description();
        description.setText(updateTime);
        fullyVaccinatedChart.setDescription(description);
        XAxis xAxis = fullyVaccinatedChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelName));
        xAxis.setTextSize(0.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(0f);
        xAxis.setLabelCount(labelName.size());
        xAxis.setLabelRotationAngle(270);

        YAxis yAxis = fullyVaccinatedChart.getAxis(YAxis.AxisDependency.RIGHT);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawGridLines(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        fullyVaccinatedChart.animateY(5000);
        fullyVaccinatedChart.animateX(2000);

        fullyVaccinatedChart.invalidate();
    }
    private void getBarChart(String update) {
        for (int i = 0; i < 4; i++) {
            String label = labelName.get(i);
            float cases = firstDose.get(i);

            firstBar.add(new BarEntry(i, (float) cases));

        }


        BarDataSet barDataSet = new BarDataSet(firstBar, "Vaccinated with first dose");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.4f);
        vaccineChart.setData(barData);
        vaccineChart.getAxisLeft().setDrawGridLines(false);
        Description description = new Description();
        description.setText(update);
        vaccineChart.setDescription(description);
        XAxis xAxis = vaccineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelName));
        xAxis.setTextSize(0.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(0f);
        xAxis.setLabelCount(labelName.size());
        xAxis.setLabelRotationAngle(270);
        vaccineChart.getAxisRight().setDrawLabels(false);

        YAxis yAxis = vaccineChart.getAxis(YAxis.AxisDependency.RIGHT);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawGridLines(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        vaccineChart.animateY(5000);
        vaccineChart.animateX(2000);
       // vaccineChart.groupBars(-1,0.25f,0.05f);
        vaccineChart.invalidate();

    }

}