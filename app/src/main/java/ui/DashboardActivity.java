package ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anshutiwari.covid19tracker.R;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    FrameLayout frameLayout;
    Class aClass;
    static final float END_SCALE = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sNavigationDrawer = findViewById(R.id.drawer_dashboard);
        frameLayout = findViewById(R.id.fl_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1001);
            }
        }

        List<MenuItem> itemList = new ArrayList<>();
        itemList.add(new MenuItem("Home",R.drawable.covid_stats));
        itemList.add(new MenuItem("News",R.drawable.corona_updates));

        sNavigationDrawer.setMenuItemList(itemList);
        sNavigationDrawer.setAppbarTitleTV("Home");
        sNavigationDrawer.setAppbarTitleTextColor(getResources().getColor(R.color.White));
        aClass = DashboardFragment.class;
        openFragment();
        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                switch (position){
                    case 0 : aClass = DashboardFragment.class;
                             break;
                    case 1 : aClass = NewsFragment.class;
                             break;
                }
            }
        });

        sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
            @Override
            public void onDrawerOpening() {

            }

            @Override
            public void onDrawerClosing() {
                openFragment();
            }

            @Override
            public void onDrawerOpened() {

            }

            @Override
            public void onDrawerClosed() {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void openFragment() {
        try {
            Fragment fragment = (Fragment) aClass.newInstance();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in,android.R.anim.fade_out
            ).replace(R.id.fl_home,fragment).commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
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