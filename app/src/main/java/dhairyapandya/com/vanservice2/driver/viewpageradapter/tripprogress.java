package dhairyapandya.com.vanservice2.driver.viewpageradapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dhairyapandya.com.vanservice2.MainActivity;
import dhairyapandya.com.vanservice2.R;

public class tripprogress extends AppCompatActivity {
    BottomNavigationView BNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripprogress);
        BNV = findViewById(R.id.bottomnav);

        BNV.setSelectedItemId(R.id.studentslist);
        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.studentslist:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
//                                BNV.setSelectedItemId(R.id.stocksbtn);

                        return true;

                    case R.id.tripprogress:
//                        startActivity(new Intent(getApplicationContext(),home.class));
//                        overridePendingTransition(0,0);
                        return true;




                }
                return false;
            }
        });

    }
}