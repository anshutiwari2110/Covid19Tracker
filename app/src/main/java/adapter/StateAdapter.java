package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshutiwari.covid19tracker.R;

import java.util.ArrayList;
import java.util.HashMap;

import model.StatesModel;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateHolder> {

    Context context;
    ArrayList<StatesModel> statesModels;
    HashMap<String,String> chatBot = new HashMap<>();
    HashMap<String,String> hospitalBeds = new HashMap<>();
    public void addInMap(){
        chatBot.put("Andaman and Nicobar Islands","https://api.whatsapp.com/send/?phone=919013151515");
        chatBot.put("Andhra Pradesh","https://api.whatsapp.com/send/?phone=918297104104&text=Hi&app_absent=0");
        chatBot.put("Delhi","https://api.whatsapp.com/send/?phone=918800007722");
        chatBot.put("Gujarat","https://api.whatsapp.com/send/?phone=917433000104");
        chatBot.put("Karnataka","https://api.whatsapp.com/send/?phone=918750971717");
        chatBot.put("Kerala","https://api.whatsapp.com/send/?phone=919072220183");
        chatBot.put("Maharashtra","https://api.whatsapp.com/send/?phone=912026127394");
        chatBot.put("Mizoram","https://api.whatsapp.com/send/?phone=9366331931");
        chatBot.put("Madhya Pradesh","https://api.whatsapp.com/send/?phone=917834980000");
        chatBot.put("Odisha","https://api.whatsapp.com/send/?phone=919337929000");
        chatBot.put("Punjab","https://api.whatsapp.com/send/?phone=917380173801");
        chatBot.put("Rajasthan","https://api.whatsapp.com/send/?phone=911412225624");
        chatBot.put("Telangana","https://api.whatsapp.com/send/?phone=919000658658");
        chatBot.put("Uttar Pradesh","https://api.whatsapp.com/send/?phone=919454441036");
        chatBot.put("West Bengal","https://api.whatsapp.com/send/?phone=918697245555&text=Hi&app_absent=0");

    }

    public void addHospitalBeds(){
        hospitalBeds.put("Andhra Pradesh","http://dashboard.covid19.ap.gov.in/ims/hospbed_reports/");
        hospitalBeds.put("Bihar","http://covid19health.bihar.gov.in/dailydashboard/bedsoccupied");
        hospitalBeds.put("Chhattisgarh","https://cg.nic.in/health/covid19/RTPBedAvailable.aspx");
        hospitalBeds.put("Dadra and Nagar Haveli and Daman and Diu","https://covidfacility.dddgov.in/");
        hospitalBeds.put("Delhi","https://coronabeds.jantasamvad.org/");
        hospitalBeds.put("Goa","https://goaonline.gov.in/beds");
        hospitalBeds.put("Gujarat","https://gujcovid19.gujarat.gov.in/");
        hospitalBeds.put("Haryana","https://coronaharyana.in/");
        hospitalBeds.put("Himachal Pradesh","http://covidcapacity.hp.gov.in/index.php");
        hospitalBeds.put("Jammu and Kashmir","https://covidrelief.jk.gov.in/Beds");
        hospitalBeds.put("Kerala","https://covid19jagratha.kerala.nic.in/home/addHospitalDashBoard");
        hospitalBeds.put("Madhya Pradesh","http://sarthak.nhmmp.gov.in/covid/facility-bed-occupancy-dashboard/");
        hospitalBeds.put("Maharashtra","https://arogya.maharashtra.gov.in/1177/Dedicated-COVID-Facilities-Status");
        hospitalBeds.put("Manipur","The COVID-19 helpline for" + "Manipur is 1800-3453-818 and" + "0385-2411668.");
        hospitalBeds.put("Meghalaya","https://meghealth.in/MeghCare.html");
        hospitalBeds.put("Odisha","https://statedashboard.odisha.gov.in/");
        hospitalBeds.put("Puducherry","https://covid19dashboard.py.gov.in/BedAvailabilityDetails");
        hospitalBeds.put("Nagaland","COVID-19 State War Room:0370-2270033,+91 8929407417 (24x7),Health Helpline: 1800 345 0019");
        hospitalBeds.put("Rajasthan","https://covidinfo.rajasthan.gov.in/Covid-19hospital-wisebedposition-wholeRajasthan.aspx");
        hospitalBeds.put("Sikkim","https://www.covid19sikkim.org/");
        hospitalBeds.put("Tamil Nadu","https://tncovidbeds.tnega.org/");
        hospitalBeds.put("Telangana","http://164.100.112.24/SpringMVC/Hospital_Beds_Statistic_Dashboard.htm");
        hospitalBeds.put("Tripura","https://covid19.tripura.gov.in/bed_availability_status.html");
        hospitalBeds.put("Uttar Pradesh","https://beds.dgmhup-covid19.in/EN/covid19bedtrack");
        hospitalBeds.put("Uttarakhand","https://covid19.uk.gov.in/bedssummary.aspx");
        hospitalBeds.put("West Bengal","https://excise.wb.gov.in/chms/Public/Page/CHMS_Public_Covid_Hospitals.aspx");

    }

    public StateAdapter(Context context, ArrayList<StatesModel> statesModels) {
        this.context = context;
        this.statesModels = statesModels;
    }

    @NonNull
    @Override
    public StateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StateHolder(LayoutInflater.from(context).inflate(R.layout.cell_states_stats,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StateHolder holder, int position) {
        StatesModel currentModel = statesModels.get(position);
        addInMap();
        addHospitalBeds();
        if (currentModel!= null){
            holder.mTvStateName.setText(currentModel.getStateName());
            holder.mTvStateConfirmed.setText(currentModel.getStateActive());
            holder.mTvStateDeath.setText(currentModel.getStateDeath());
            holder.mTvStateRecovered.setText(currentModel.getStateRecovered());
            holder.mTvStateActive.setText(currentModel.getStateCases());
            holder.mTvLastUpdated.setText("Last Updated Time: "+currentModel.getLastUpdated()+" IST");
            if(chatBot.get(currentModel.getStateName()) != null){
                holder.mLlWhatsappBot.setVisibility(View.VISIBLE);
                String chatBotLink = chatBot.get(currentModel.getStateName());
                holder.mLlWhatsappBot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(chatBotLink));
                        context.startActivity(intent);
                    }
                });
            }else{
                holder.mLlWhatsappBot.setVisibility(View.GONE);
            }

            if(hospitalBeds.get(currentModel.getStateName()) != null){
                holder.mLlHospitalBed.setVisibility(View.VISIBLE);
                String hospitalPage = hospitalBeds.get(currentModel.getStateName());
                holder.mLlHospitalBed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(hospitalPage));
                        context.startActivity(intent);
                    }
                });
            }else{
                holder.mLlHospitalBed.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return statesModels.size();
    }

    public class StateHolder extends RecyclerView.ViewHolder{
        private TextView mTvStateActive;
        private TextView mTvStateConfirmed;
        private TextView mTvStateDeath;
        private TextView mTvStateRecovered;
        private TextView mTvStateName;
        LinearLayout mLlWhatsappBot;
        ImageView mIvWhatsappBot;

        RelativeLayout mLlHospitalBed;
        TextView mIvHospitalBed;
        private TextView mTvLastUpdated;
        public StateHolder(@NonNull View itemView) {
            super(itemView);

            mTvStateActive = itemView.findViewById(R.id.tv_state_active);
            mTvStateConfirmed = itemView.findViewById(R.id.tv_state_confirmed);
            mTvStateDeath = itemView.findViewById(R.id.tv_state_death);
            mTvStateRecovered = itemView.findViewById(R.id.tv_state_recovered);
            mTvStateName = itemView.findViewById(R.id.tv_states_name);
            mLlWhatsappBot = itemView.findViewById(R.id.ll_whatsapp_bot);
            mLlHospitalBed = itemView.findViewById(R.id.ll_hospital_beds);
            mIvHospitalBed = itemView.findViewById(R.id.iv_hospital_bed);
            mIvWhatsappBot = itemView.findViewById(R.id.iv_whatsapp_bot);
            mTvLastUpdated = itemView.findViewById(R.id.tv_last_updated);
        }
    }
}
