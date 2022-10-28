package dhairyapandya.com.vanservice2.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.driver.viewpageradapter.ViewPagerAdapter;
import dhairyapandya.com.vanservice2.driver.viewpageradapter.tripprogress;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.miscellaneous.aboutpage;
import dhairyapandya.com.vanservice2.miscellaneous.profile;
import dhairyapandya.com.vanservice2.miscellaneous.settings;

public class drivershomepage extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    BottomNavigationView BNV;

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
//                        String tabTitles[] = new String[]{"Friends", "Suggested Friends"};
//                        tab.setText("Tab " + (position + 1));
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
//                                BNV.setSelectedItemId(R.id.stocksbtn);

                        return true;

                    case R.id.studentslist:
//                        startActivity(new Intent(getApplicationContext(),home.class));
//                        overridePendingTransition(0,0);
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
//            Toast.makeText(this, "This is Home ", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Home ");
            startActivity(new Intent(getApplicationContext(), settings.class));
        }
        if(itemid==R.id.m2)
        {
//            Toast.makeText(this, "This is Insert", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Insert ");
            startActivity(new Intent(getApplicationContext(), profile.class));

        }

        if(itemid==R.id.m3)
        {
//            Toast.makeText(this, "This is Insert", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Insert ");
//            startActivity(new Intent(getApplicationContext(),Profile.class));
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), login.class);
            i.putExtra("usertype","Driver");
            startActivity(i);
            finish();

        }
        if(itemid==R.id.m4)
        {
            startActivity(new Intent(getApplicationContext(), aboutpage.class));
        }
        return super.onOptionsItemSelected(item);
    }


}