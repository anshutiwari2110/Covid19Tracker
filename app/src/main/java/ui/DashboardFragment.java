package ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.anshutiwari.covid19tracker.R;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import adapter.SliderAdapter;


public class DashboardFragment extends Fragment {
    public static String toll_free_helpline = "1075";
    public static String email_for_help = "ncov2019@gov.in";
    public static String self_assessment_test = "https://www.apollo247.com/covid19/scan/";
    Button mCall,mEmail,mMoveToNation,mMoveToState,mVaccination;
    ImageView mSelfTest;
    SliderView sliderView;

    int[] images = {R.drawable.myth_1,R.drawable.myth_2,R.drawable.myth_3,R.drawable.myth_4,R.drawable.myth_5,R.drawable.myth_6, R.drawable.myth_7, R.drawable.myth_8, R.drawable.myth_9, R.drawable.myth_10, R.drawable.myth_11};
    SliderAdapter sliderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mCall = view.findViewById(R.id.button);
        mEmail = view.findViewById(R.id.button2);
        mMoveToNation = view.findViewById(R.id.button4);
        mMoveToState = view.findViewById(R.id.button3);
        mVaccination = view.findViewById(R.id.button5);
        mSelfTest = view.findViewById(R.id.imageView13);
        sliderView = view.findViewById(R.id.slider_myth);

        sliderAdapter = new SliderAdapter(images,getActivity());
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

        Bitmap icon = BitmapFactory.decodeResource(getResources(),images[0]);


        mMoveToState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),StateStatistcActivity.class));
            }
        });
        mVaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),VaccineStatsActivity.class));
            }
        });

        mSelfTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(self_assessment_test));
                startActivity(intent);
            }
        });

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = "tel:" + toll_free_helpline;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(call));
                startActivity(callIntent);
            }
        });

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:" + email_for_help));
                startActivity(intent);
            }
        });

        mMoveToNation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),StatisticsActivity.class));
            }
        });
        return view;
    }

}