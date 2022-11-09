package dhairyapandya.com.vanservice2.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.driver.viewpageradapter.ViewPagerAdapter;
import dhairyapandya.com.vanservice2.driver.viewpageradapter.tripprogress;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;
import dhairyapandya.com.vanservice2.miscellaneous.aboutpage;
import dhairyapandya.com.vanservice2.miscellaneous.profile;
import dhairyapandya.com.vanservice2.miscellaneous.settings;

public class drivershomepage extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    BottomNavigationView BNV;
    ReviewManager manager;
    ReviewInfo reviewInfo;
NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivershomepage);

        tabLayout = findViewById(R.id.tabs);
        viewPager2 = findViewById(R.id.view_pager);
        BNV = findViewById(R.id.bottomnav);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        if (position == 0)
                            tab.setText("Comming");
                        if (position == 1)
                            tab.setText("Registered");
                    }
                }).attach();

        BNV.setSelectedItemId(R.id.studentslist);
        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tripprogress:
                        startActivity(new Intent(getApplicationContext(), tripprogress.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.studentslist:

                        return true;




                }
                return false;
            }
        });

    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid=item.getItemId();
        if(itemid==R.id.m1)
        {

            startActivity(new Intent(getApplicationContext(), settings.class));
        }
        if(itemid==R.id.m2)
        {

            startActivity(new Intent(getApplicationContext(), profile.class));

        }

        if(itemid==R.id.m3)
        {


            manager= ReviewManagerFactory.create(drivershomepage.this);
            com.google.android.play.core.tasks.Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                @Override
                public void onComplete(@NonNull Task<ReviewInfo> task) {
                    if(task.isSuccessful()){
                        reviewInfo=task.getResult();
                        com.google.android.play.core.tasks.Task<Void> flow = manager.launchReviewFlow(drivershomepage.this,reviewInfo);
                        flow.addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(drivershomepage.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

        return super.onOptionsItemSelected(item);
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