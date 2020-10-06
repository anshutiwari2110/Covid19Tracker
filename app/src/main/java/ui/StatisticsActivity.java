package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import api.APIClient;
import api.API_Interface;
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
    private TextView mTvTestPerMillion;
    private TextView mTvTestPositivity;
    private TextView mTvTotalSample;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //findViewByIds of the views
        mTvActiveCases = findViewById(R.id.tv_active_case_no);
        mTvTotalCases = findViewById(R.id.tv_total_case_no);
        mTvRecoveredCases = findViewById(R.id.tv_recovered_case_no);
        mTvDeathCases = findViewById(R.id.tv_death_case_no);
        mTvTestPerMillion = findViewById(R.id.tv_test_per_million);
        mTvTestPositivity = findViewById(R.id.tv_positivity_rate);
        mTvTotalSample = findViewById(R.id.tv_total_sample);
        mTvDailyRTCPR = findViewById(R.id.tv_rt_cpr);
        mTvDailySample = findViewById(R.id.tv_daily_sample);
        mTvTestedAsOf = findViewById(R.id.tv_tested_as_of);
        progressDialog = new ProgressDialog(StatisticsActivity.this);
        progressDialog.setMessage("Getting Data...");
        getData();

        getTestingData();

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
                    for (int i =jsonArray.length()-1; i < jsonArray.length(); i++) {

                        TestingDataModel testingDataModel = new TestingDataModel();
                        JSONObject object = jsonArray.getJSONObject(i);

                        testingDataModel.setDailyRTPCR(object.optString("dailyrtpcrtests"));
                        testingDataModel.setTestedAsOf(object.optString("testedasof"));
                        testingDataModel.setDailySampleReport(object.optString("samplereportedtoday"));
                        testingDataModel.setTotalSampleTested(object.optString("totalsamplestested"));
                        testingDataModel.setTestPositivityRate(object.optString("testpositivityrate"));
                        testingDataModel.setTestPerMillion(object.optString("testspermillion"));

                        mTvTestedAsOf.setText(testingDataModel.getTestedAsOf());
                        mTvDailyRTCPR.setText(testingDataModel.getDailyRTPCR());
                        mTvDailySample.setText(testingDataModel.getDailySampleReport());
                        mTvTestPerMillion.setText(testingDataModel.getTestPerMillion());
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

    private void getData() {

        progressDialog.show();
        API_Interface apiInterface = APIClient.getClient().create(API_Interface.class);
        Call<Result> getData = apiInterface.getTotalOfIndia();
        getData.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();

                String totalCases = responseValue.totalCases;
                String activeCases = responseValue.activeCases;
                String deaths = responseValue.deaths;
                String recoveredCases = responseValue.recovered;

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

    public void moveToHome(View view) {
        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
    }
}