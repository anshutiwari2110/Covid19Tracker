package ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anshutiwari.covid19tracker.R;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    static final float END_SCALE = 0.7f;
    public static String toll_free_helpline = "1075";
    public static String email_for_help = "ncov2019@gov.in";
    public static String self_assessment_test = "https://www.apollo247.com/covid19/scan/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1001);
            }
        }

    }

    public void moveStateStats(View view){
        startActivity(new Intent(DashboardActivity.this,StateStatistcActivity.class));
    }

    public void moveNationalStats(View view){
        startActivity(new Intent(DashboardActivity.this,StatisticsActivity.class));
    }

    public void callForHelp(View view){
        String call = "tel:" + toll_free_helpline;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(call));
        startActivity(callIntent);
    }

    public void emailForHelp(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("mailto:" + email_for_help));
        startActivity(intent);

    }

    public void selfTest(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(self_assessment_test));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DashboardActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DashboardActivity.this, "User Denied Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

}