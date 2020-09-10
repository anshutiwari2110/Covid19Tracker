package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anshutiwari.covid19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.StateAdapter;
import model.StatesModel;

public class StateStatistcActivity extends AppCompatActivity {

    private RecyclerView mRcStatesStat;
    ArrayList<StatesModel> statesModels = new ArrayList<>();
    StateAdapter stateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_statistc);

        getStatesStats();

    }

    private void getStatesStats() {
        RequestQueue queue = Volley.newRequestQueue(StateStatistcActivity.this);
        String url = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("regionData");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        StatesModel statesModel = new StatesModel();
                        JSONObject object = jsonArray.getJSONObject(i);
                        statesModel.setStateName(object.optString("region"));
                        statesModel.setStateActive(object.optString("newInfected"));
                        statesModel.setStateRecovered(object.optString("recovered"));
                        statesModel.setStateDeath(object.optString("deceased"));
                        statesModel.setStateCases(object.optString("totalInfected"));
                        statesModels.add(statesModel);
                    }

                    mRcStatesStat.setLayoutManager(new LinearLayoutManager(StateStatistcActivity.this,RecyclerView.VERTICAL,false));
                    stateAdapter = new StateAdapter(StateStatistcActivity.this, statesModels);
                    mRcStatesStat.setAdapter(stateAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onResponse", error.toString());
            }
        });
        queue.add(request);
    }
}