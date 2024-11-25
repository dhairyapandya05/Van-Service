package dhairyapandya.com.vanservice2.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.RatingView;
import dhairyapandya.com.vanservice2.driver.viewpageradapter.tripprogress;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;
import dhairyapandya.com.vanservice2.miscellaneous.profile;
import dhairyapandya.com.vanservice2.miscellaneous.settings;

public class drivershomepage extends AppCompatActivity {
//    TabLayout tabLayout;
    BottomNavigationView BNV;
    TextView totalactiveStrength;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid();

    private static final String TAG = "FirestoreExample";
    private FirebaseFirestore db;
NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivershomepage);
        totalactiveStrength=findViewById(R.id.numberTotalStudents);
        BNV = findViewById(R.id.bottomnav);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Driver").document(userId);

        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        if (document.exists()) {
                            // Get the 'CommingStudentsSize' field
                            Long comingStudentsSize = document.getLong("CommingStudentsSize");

                            // If the value is not null, use it
                            if (comingStudentsSize != null) {
                                // Use the value
                                Log.d("Firestore", "CommingStudentsSize: " + comingStudentsSize);
                                totalactiveStrength.setText(comingStudentsSize.toString());
                            } else {
                                Log.d("Firestore", "CommingStudentsSize field not found.");
                            }
                        } else {
                            Log.d("Firestore", "No such document.");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error getting document: ", e);
                    }
                });


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
            startActivity(new Intent(getApplicationContext(), RatingView.class));

        }
        if(itemid==R.id.m2)
        {


            startActivity(new Intent(getApplicationContext(), settings.class));

        }

        if(itemid==R.id.m3)
        {
            startActivity(new Intent(getApplicationContext(), profile.class));

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