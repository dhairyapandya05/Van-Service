package dhairyapandya.com.vanservice2.driver.viewpageradapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dhairyapandya.com.vanservice2.MainActivity;
import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;


public class tripprogress extends AppCompatActivity {
    BottomNavigationView BNV;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripprogress);

        BNV = findViewById(R.id.bottomnav);

        BNV.setSelectedItemId(R.id.tripprogress);
        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.studentslist:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.tripprogress:

                        return true;




                }
                return false;
            }
        });

    }
    @Override
    protected void onStart() {
        IntentFilter filter =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver,filter);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkChangeReceiver);
        super.onDestroy();
    }

}