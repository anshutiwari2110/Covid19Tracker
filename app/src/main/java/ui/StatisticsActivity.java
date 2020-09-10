package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anshutiwari.covid19tracker.R;

import api.APIClient;
import api.API_Interface;
import model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsActivity extends AppCompatActivity {

    private TextView mTvTotalCases;
    private TextView mTvTest;
    private TextView mTvCritical;
    private TextView mTvActiveCases;
    private TextView mTvIncActiveCases;
    private TextView mTvRecoveredCases;
    private TextView mTvIncRecoveredCases;
    private TextView mTvDeathCases;
    private TextView mTvIncDeathCases;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //findViewByIds of the views
        mTvActiveCases = findViewById(R.id.tv_active_case_no);
        mTvTotalCases = findViewById(R.id.tv_total_case_no);
        mTvTest = findViewById(R.id.tv_total_test_no);
        mTvCritical = findViewById(R.id.tv_total_critical_no);
        mTvIncActiveCases = findViewById(R.id.tv_active_case_inc_no);
        mTvRecoveredCases = findViewById(R.id.tv_recovered_case_no);
        mTvIncRecoveredCases = findViewById(R.id.tv_recovered_case_inc_no);
        mTvDeathCases = findViewById(R.id.tv_death_case_no);
        mTvIncDeathCases = findViewById(R.id.tv_death_case_inc_no);
        progressDialog = new ProgressDialog(StatisticsActivity.this);


        progressDialog.setMessage("Getting Data...");
        getData();
    }

    private void getData() {

        progressDialog.show();
        API_Interface apiInterface = APIClient.getClient().create(API_Interface.class);
        Call<Result> getData = apiInterface.getTotalOfIndia();
        getData.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();
                String totalCases = responseValue.getCases();
                mTvTotalCases.setText(totalCases);
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                progressDialog.hide();
                Toast.makeText(StatisticsActivity.this, "Not Available", Toast.LENGTH_SHORT).show();
            }
        });
/*
        getData.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result val = response.body();
                String totalCases = val.cases.toString();
                mTvTotalCases.setText(totalCases);
                mTvActiveCases.setText(val.active.toString());
                mTvTest.setText(val.tests.toString());
                progressDialog.hide();

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                progressDialog.hide();
                Toast.makeText(DashboardActivity.this, "No Result found", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }
}