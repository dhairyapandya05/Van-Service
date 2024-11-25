package dhairyapandya.com.vanservice2.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.driver.vehicaldetails;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;
import dhairyapandya.com.vanservice2.register;

public class locationdetails extends AppCompatActivity {
    Spinner city, boarding;
    ImageButton next, back;
    ArrayList<String> arrlistcity,anandpt,nadiadpt,ahmedabadpt,vadodarapt;
ArrayAdapter<String> adpt;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userselectedcity="Anand";
    String userselectedboardingpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationdetail);
        //adding the values in the arraylists
        arrlistcity=new ArrayList<>();
        arrlistcity.add("Anand");
        arrlistcity.add("Nadiad");
        arrlistcity.add("Ahmedabad");
        arrlistcity.add("Vadodara");
        anandpt=new ArrayList<>();
        anandpt.add("Central Bus Station");
        anandpt.add("City Bus Depot");
        anandpt.add("Makarpura Central Bus Station");
        anandpt.add("Ashoka Temple");
        anandpt.add("Vinayak Guest House");


        nadiadpt=new ArrayList<>();
        nadiadpt.add("Central Bus Station");
        nadiadpt.add("Makarpura Central");
        nadiadpt.add("City Bus Depot");
        nadiadpt.add("Nadiad Bus Station");
        nadiadpt.add("Dashrath Bus Station");
        nadiadpt.add("Vinayak City");
        nadiadpt.add("Nadiad city bus station");
        nadiadpt.add("Makarpura Naka");
        nadiadpt.add("Central Bus Depot");

        vadodarapt=new ArrayList<>();
        vadodarapt.add("Central Bus Station Vadodara");
        vadodarapt.add("Makarpura Central Bus Station Vadodara");
        vadodarapt.add("City Bus Depot Vadodara");
        vadodarapt.add("Vadodara Bus Station");
        vadodarapt.add("Dashrath Bus Station Vadodara");
        vadodarapt.add("Vinayak City Bus Service Vadodara");
        vadodarapt.add("Vadodara city bus station");
        vadodarapt.add("Makarpura Naka Vadodara");
        vadodarapt.add("Central Bus Depot Vadodara");

        ahmedabadpt=new ArrayList<>();
        ahmedabadpt.add("Central Bus Station Ahmedabad");
        ahmedabadpt.add("Makarpura Central Bus Station Ahmedabad");
        ahmedabadpt.add("City Bus Depot Ahmedabad");
        ahmedabadpt.add("Ahmedabad Bus Station");
        ahmedabadpt.add("Dashrath Bus Station Ahmedabad");
        ahmedabadpt.add("Vinayak City Bus Service Ahmedabad");
        ahmedabadpt.add("Ahmedabad city bus station");
        ahmedabadpt.add("Makarpura Naka Ahmedabad");
        ahmedabadpt.add("Central Bus Depot Ahmedabad");

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        next = findViewById(R.id.next);
        back = findViewById(R.id.previous);

        city = findViewById(R.id.cityspinner);

        boarding = findViewById(R.id.boardingpointspinner);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validate data
                if (userselectedcity.isEmpty()) {
                }if (userselectedboardingpoint.isEmpty()) {
                }


                //STORE DETAILS IN SHARED PREFERENCES
                SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                editor.putString("Selected City", userselectedcity);
                editor.putString("Selected Boarding Point", userselectedboardingpoint);
                editor.apply();


                //move to see the available drivers in the list
                Intent i = new Intent(locationdetails.this, availabledrivers.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(locationdetails.this, register.class));
            }
        });


//spinner work
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrlistcity);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adp);
        city.setPrompt("Select your City");

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                userselectedcity = arrlistcity.get(i);

                if (userselectedcity.toString().equals("Anand")) {
                    adpt = new ArrayAdapter(locationdetails.this, android.R.layout.simple_spinner_item, anandpt);

                } else if (userselectedcity.toString().equals("Nadiad")) {
                    adpt = new ArrayAdapter(locationdetails.this, android.R.layout.simple_spinner_item, nadiadpt);

                } else if (userselectedcity.toString().equals("Ahmedabad")) {
                    adpt = new ArrayAdapter(locationdetails.this, android.R.layout.simple_spinner_item, ahmedabadpt);

                } else if (userselectedcity.toString().equals("Vadodara")) {
                    adpt = new ArrayAdapter(locationdetails.this, android.R.layout.simple_spinner_item, vadodarapt);

                }
                else{
                }
                adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                boarding.setAdapter(adpt);
                boarding.setPrompt("Select your Boarding Point");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });







        boarding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (userselectedcity.equals("Anand")) {
                    userselectedboardingpoint = anandpt.get(i);

                } else if (userselectedcity.equals("Nadiad")) {
                    userselectedboardingpoint = nadiadpt.get(i);


                } else if (userselectedcity.equals("Ahmedabad")) {

                    userselectedboardingpoint = ahmedabadpt.get(i);

                } else if (userselectedcity.equals("Vadodara")) {
                    userselectedboardingpoint = vadodarapt.get(i);

                } else {
                }

                }
//            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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