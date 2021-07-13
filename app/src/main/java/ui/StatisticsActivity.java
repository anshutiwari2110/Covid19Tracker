package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anshutiwari.covid19tracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.CustomMarkerView;
import api.API_Client;
import api.APIInterface;
import model.OverallStats;
import model.Result;
import model.TestingDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsActivity extends AppCompatActivity {

    private TextView mTvTotalCases;
    private TextView mTvActiveCases;
    private TextView mTvRecoveredCases;
    private TextView mTvDeathCases;
    private TextView mTvTestedAsOf;
    private TextView mTvDailyRTCPR;
    private TextView mTvDailySample;
    private TextView mTvTestPositivity;
    private TextView mTvTotalSample;
    ProgressDialog progressDialog;
    BarChart barChart;
    LineChart lineChart,deathLineChart;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> labelsName;
    ArrayList<OverallStats> overallStats = new ArrayList<>();

    ArrayList<Entry> dailyCases;
    ArrayList<Entry> dailyDeath;
    ArrayList<Entry> dailyRecovered;
    static ArrayList<String> labelDate = new ArrayList<>();

    double ac, dc, rc, tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //findViewByIds of the views
        mTvActiveCases = findViewById(R.id.tv_active_case_no);
        mTvTotalCases = findViewById(R.id.tv_total_case_no);
        mTvRecoveredCases = findViewById(R.id.tv_recovered_case_no);
        mTvDeathCases = findViewById(R.id.tv_death_case_no);
        mTvTestPositivity = findViewById(R.id.tv_positivity_rate);
        mTvTotalSample = findViewById(R.id.tv_total_sample);
        mTvDailyRTCPR = findViewById(R.id.tv_rt_cpr);
        mTvDailySample = findViewById(R.id.tv_daily_sample);
        mTvTestedAsOf = findViewById(R.id.tv_tested_as_of);
        barChart = findViewById(R.id.chart_stats);
        lineChart = findViewById(R.id.line_chart);
        deathLineChart = findViewById(R.id.line_chart_death);
        barEntries = new ArrayList<>();
        labelsName = new ArrayList<>();

        dailyCases = new ArrayList<>();
        dailyDeath = new ArrayList<>();
        dailyRecovered = new ArrayList<>();

        progressDialog = new ProgressDialog(StatisticsActivity.this);
        progressDialog.setMessage("Getting Data...");
        getData();
        getDailyConfirmedCases();
        getTestingData();

    }

    private void getDailyDeath() {
        LineDataSet set2;
        set2 = new LineDataSet(dailyDeath, "Daily Death cases");
        set2.setColor(Color.RED);
        set2.setDrawCircles(false);
        set2.setLineWidth(1f);
        set2.setDrawValues(false);

        LineData lineData = new LineData(set2);
        XAxis xAxis = deathLineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelDate));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(10);
        xAxis.setLabelRotationAngle(90);
        Description description = new Description();
        description.setText("Daily Death of COVID-19 cases");
        description.setTextColor(Color.BLUE);
        deathLineChart.setDescription(description);
        deathLineChart.setData(lineData);
        deathLineChart.animate();
        deathLineChart.invalidate();
        deathLineChart.setTouchEnabled(true);
        IMarker markerView = new CustomMarkerView(getApplicationContext(),R.layout.custom_marker_view_layout);
        deathLineChart.setMarker(markerView);
    }

    private void getChart() {
        for (int i = 0; i < 4; i++) {
            String label = overallStats.get(i).getLabels();
            float cases = overallStats.get(i).getCases();

            barEntries.add(new BarEntry(i, (float) cases));
            labelsName.add(label);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Cases in lakhs");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        Description description = new Description();
        description.setText("Nation-wide COVID-19 cases");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.4f);

        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsName.size());
        xAxis.setLabelRotationAngle(0);

        //barChart.setDrawBarShadow(true);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.animateY(5000);
        barChart.animateX(2000);
        barChart.invalidate();

    }

    private void getDailyConfirmedCases() {
        RequestQueue queue = Volley.newRequestQueue(StatisticsActivity.this);
        String url = "https://api.covid19india.org/data.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("cases_time_series");
                    int j = 0;
                    for (int i = jsonArray.length()-100; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        float fCases = Float.parseFloat(object.optString("dailyconfirmed"));
                        float fRecovered = Float.parseFloat(object.optString("dailyrecovered"));
                        float fDeath = Float.parseFloat(object.optString("dailydeceased"));
                        dailyCases.add(new Entry(j,fCases));
                        dailyDeath.add(new Entry(j,fDeath));
                        dailyRecovered.add(new Entry(j,fRecovered));
                        labelDate.add(object.optString("dateymd"));
                        j++;
                        getLineChart();
                        getDailyDeath();
                    }


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

    private void getTestingData() {
        RequestQueue queue = Volley.newRequestQueue(StatisticsActivity.this);
        String url = "https://api.covid19india.org/data.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tested");

                    for (int i = jsonArray.length() - 1; i < jsonArray.length(); i++) {

                        TestingDataModel testingDataModel = new TestingDataModel();
                        JSONObject object = jsonArray.getJSONObject(i);

                        testingDataModel.setDailyRTPCR(object.optString("dailyrtpcrsamplescollectedicmrapplication"));
                        testingDataModel.setTestedAsOf(object.optString("testedasof"));
                        testingDataModel.setDailySampleReport(object.optString("samplereportedtoday"));
                        testingDataModel.setTotalSampleTested(object.optString("totalsamplestested"));
                        testingDataModel.setTestPositivityRate(object.optString("totalrtpcrsamplescollectedicmrapplication"));

                        mTvTestedAsOf.setText(testingDataModel.getTestedAsOf());
                        mTvDailyRTCPR.setText(testingDataModel.getDailyRTPCR());
                        mTvDailySample.setText(testingDataModel.getDailySampleReport());
                        mTvTotalSample.setText(testingDataModel.getTotalSampleTested());
                        mTvTestPositivity.setText(testingDataModel.getTestPositivityRate());

                    }


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

    private void getLineChart() {
        LineDataSet set1,set2;
        set1 = new LineDataSet(dailyCases, "Daily Positive cases");
        set1.setColor(Color.BLUE);
        set1.setDrawCircles(false);
        set1.setLineWidth(1f);
        set1.setDrawValues(false);

        set2 = new LineDataSet(dailyRecovered, "Daily Recovered cases");
        set2.setColor(Color.GREEN);
        set2.setDrawCircles(false);
        set2.setLineWidth(1f);
        set2.setDrawValues(false);

        LineData lineData = new LineData(set1,set2);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelDate));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(11);
        xAxis.setLabelRotationAngle(90);
        Description description = new Description();
        description.setText("Daily Confirm COVID-19 cases");
        description.setTextColor(Color.BLUE);
        lineChart.setDescription(description);
        lineChart.setData(lineData);
        lineChart.animate();
        lineChart.invalidate();
        lineChart.setTouchEnabled(true);
        IMarker markerView = new CustomMarkerView(getApplicationContext(),R.layout.custom_marker_view_layout);
        lineChart.setMarker(markerView);
    }

    private void getData() {

        progressDialog.show();
        APIInterface apiInterface = API_Client.getClient().create(APIInterface.class);
        Call<Result> getData = apiInterface.getTotalOfIndia();
        getData.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();

                String totalCases = responseValue.totalCases;
                String activeCases = responseValue.activeCases;
                String deaths = responseValue.deaths;
                String recoveredCases = responseValue.recovered;
                ac = Double.parseDouble(activeCases);
                dc = Double.parseDouble(deaths);
                rc = Double.parseDouble(recoveredCases);
                tc = Double.parseDouble(totalCases);

                statsOnCases((float) ac / 100000, (float) rc / 100000, (float) dc / 100000, (float) tc / 100000);
                getChart();

                mTvTotalCases.setText(totalCases);
                mTvActiveCases.setText(activeCases);
                mTvDeathCases.setText(deaths);
                mTvRecoveredCases.setText(recoveredCases);
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                progressDialog.hide();
                Toast.makeText(StatisticsActivity.this, "Not Available", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void statsOnCases(float ac, float rc, float dc, float tc) {
        overallStats.clear();
        overallStats.add(new OverallStats("Total Cases", tc));
        overallStats.add(new OverallStats("Active Cases", ac));
        overallStats.add(new OverallStats("Recovered\nCases", rc));
        overallStats.add(new OverallStats("Death\nCases", dc));
    }

    public void moveToHome(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }
}