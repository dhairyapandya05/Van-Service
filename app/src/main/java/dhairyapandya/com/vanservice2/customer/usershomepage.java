package dhairyapandya.com.vanservice2.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ncorti.slidetoact.SlideToActView;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;
import dhairyapandya.com.vanservice2.miscellaneous.aboutpage;
import dhairyapandya.com.vanservice2.miscellaneous.profile;
import dhairyapandya.com.vanservice2.miscellaneous.settings;

public class usershomepage extends AppCompatActivity {
    SlideToActView sta;
    FirebaseFirestore fstore;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    ReviewManager manager;
    ReviewInfo reviewInfo;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usershomepage);
        fstore = FirebaseFirestore.getInstance();
         sta = (SlideToActView) findViewById(R.id.example);
        fAuth = FirebaseAuth.getInstance();
        String userid=fAuth.getUid();//customers uid

//         getting the details from the shared getSharedPreferences
        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String selecteddriversuniqueid = prefs.getString("driversuniqueid", "DRIVERS UNIQUE ID");

        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                DocumentReference abc = fstore.collection("Drivers").document(selecteddriversuniqueid);
                abc.update("Commingstudents", FieldValue.arrayRemove(userid));
//                Toast.makeText(usershomepage.this, "HHHHHHHHHHHHH", Toast.LENGTH_SHORT).show();
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


            manager= ReviewManagerFactory.create(usershomepage.this);
            com.google.android.play.core.tasks.Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                @Override
                public void onComplete(@NonNull Task<ReviewInfo> task) {
                    if(task.isSuccessful()){
                        reviewInfo=task.getResult();
                        com.google.android.play.core.tasks.Task<Void> flow = manager.launchReviewFlow(usershomepage.this,reviewInfo);
                        flow.addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(usershomepage.this, "Error", Toast.LENGTH_SHORT).show();
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