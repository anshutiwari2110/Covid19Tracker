package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshutiwari.covid19tracker.R;

import java.util.ArrayList;

import model.StatesModel;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateHolder> {

    Context context;
    ArrayList<StatesModel> statesModels;

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

        if (currentModel!= null){
            holder.mTvStateName.setText(currentModel.getStateName());
            holder.mTvStateConfirmed.setText(currentModel.getStateActive());
            holder.mTvStateDeath.setText(currentModel.getStateDeath());
            holder.mTvStateRecovered.setText(currentModel.getStateRecovered());
            holder.mTvStateActive.setText(currentModel.getStateCases());
            holder.mTvLastUpdated.setText("Last Updated Time: "+currentModel.getLastUpdated()+" IST");
//            holder.mTvIncStatesConfirmed.setText(" [+" + currentModel.getIncStateConfirmed() + " ]");
//            holder.mTvIncStateDeath.setText(" [+" + currentModel.getIncStateDeath() + " ]");
//            holder.mTvIncStatesRecovered.setText("[+" +currentModel.getIncStateRecovered()+" ]");
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
        private TextView mTvIncStateDeath;
        private TextView mTvIncStatesConfirmed;
        private TextView mTvIncStatesRecovered;
        private TextView mTvLastUpdated;
        public StateHolder(@NonNull View itemView) {
            super(itemView);

            mTvStateActive = itemView.findViewById(R.id.tv_state_active);
            mTvStateConfirmed = itemView.findViewById(R.id.tv_state_confirmed);
            mTvStateDeath = itemView.findViewById(R.id.tv_state_death);
            mTvStateRecovered = itemView.findViewById(R.id.tv_state_recovered);
            mTvStateName = itemView.findViewById(R.id.tv_states_name);
//            mTvIncStatesConfirmed = itemView.findViewById(R.id.tv_inc_state_confirmed);
//            mTvIncStateDeath = itemView.findViewById(R.id.tv_inc_state_death);
//            mTvIncStatesRecovered = itemView.findViewById(R.id.tv_inc_state_recovered);
            mTvLastUpdated = itemView.findViewById(R.id.tv_last_updated);
        }
    }
}
