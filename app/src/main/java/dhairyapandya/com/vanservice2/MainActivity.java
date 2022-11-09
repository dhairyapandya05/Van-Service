package dhairyapandya.com.vanservice2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.customer.locationdetails;
import dhairyapandya.com.vanservice2.customer.usershomepage;
import dhairyapandya.com.vanservice2.driver.drivershomepage;
import dhairyapandya.com.vanservice2.driver.vehicaldetails;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;

public class MainActivity extends AppCompatActivity {
    RadioGroup RG;
    public static int UPDATE_CODE=22;
    AppUpdateManager appUpdateManager;

    ImageButton Next;
String usertype;
    RadioButton gen, customer, driver;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inappupdate();
        Next = findViewById(R.id.nxtbtn);
        RG = findViewById(R.id.radgrp);
        customer = (RadioButton) findViewById(R.id.Customer);
        driver = (RadioButton) findViewById(R.id.Driver);

        //Check the users entered data


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioID = RG.getCheckedRadioButtonId();
                gen = findViewById(radioID);
                 usertype = gen.getText().toString();

                 //do the work of shared preference
                //DO THE WORK OF SHARED PREFERENCE
                SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                editor.putString("Use type", usertype);
                editor.apply();
//                Toast.makeText(MainActivity.this, "Data added from shared preference", Toast.LENGTH_SHORT).show();

                if (usertype.equals("Customer")) {
                    Intent intent = new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Customer", Toast.LENGTH_SHORT).show();
                } else if (usertype.equals("Driver")) {
                    Intent intent = new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Driver", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "An error occured Please try after some time", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void inappupdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> task = appUpdateManager.getAppUpdateInfo();
        task.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE))
                {
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE,MainActivity.this,UPDATE_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                        Log.e("Update Error"," Yae error hai : "+e.toString());
                    }
                }

            }
        });
        appUpdateManager.registerListener(listener);
    }
    InstallStateUpdatedListener listener = installState -> {
        if(installState.installStatus()== InstallStatus.DOWNLOADED){
            popup();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==UPDATE_CODE){
            if(resultCode !=RESULT_OK)
            {
                Log.e("Update Stutus","App updated sucessfully");
            }
        }
    }

    private void popup() {
            Snackbar snacbar =Snackbar.make(findViewById(android.R.id.content),
                    "App Updare Almost Done",
                    Snackbar.LENGTH_INDEFINITE);
            snacbar.setAction("Reload", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appUpdateManager.completeUpdate();
                }
            });
            snacbar.setTextColor(Color.parseColor("#FCE302"));
            snacbar.show();


    }

    @Override
    protected void onStart() {

        IntentFilter filter =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver,filter);
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
            String name = prefs.getString("Use type", "Customer"); //"Blank Name" the default value.


//            Toast.makeText(this, "Data retreved from shared preference", Toast.LENGTH_SHORT).show();

            if (name.equals("Customer")) {
                SharedPreferences preffs = getSharedPreferences("Van Service users data", MODE_PRIVATE);


                String selecteddriversuniqueid = preffs.getString("driversuniqueid", "drivverdefaultselectedid");
                String selectedcity = preffs.getString("Selected City", "defaultselectedcity");
                String boardingpt = preffs.getString("Selected Boarding Point", "defaultselectedboardingpoint");
//                String driverid = preffs.getString("driversuniqueid", "defaultselectedboardingpoint");
                Log.e("Status",selectedcity+"Selected City");
                Log.e("Status",boardingpt+"Selected Boarding Point");
                Log.e("Status",selecteddriversuniqueid+"Selected Unique id");
                if(!(selectedcity.equals("defaultselectedcity") || boardingpt.equals("defaultselectedboardingpoint")||selecteddriversuniqueid.equals("drivverdefaultselectedid")))
                {
                    startActivity(new Intent(getApplicationContext(), usershomepage.class));
                    finish();
                }
                else {

                    startActivity(new Intent(getApplicationContext(), locationdetails.class));
                    finish();
                }
            } else if (name.equals("Driver")) {
                SharedPreferences prefffs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
                String city = prefffs.getString("City", "defaultselectedcitydriver");
                String vehical = prefffs.getString("Vehical", "defaultvehical");
                String color = prefffs.getString("Color of vehical", "colordefault");
                String modle = prefffs.getString("Modle of vehical", "modledefault");
                String platenumber = prefffs.getString("Plate number of vehical", "defaultplate");
                String busstopsselected = prefffs.getString("BusStops Selected by driver", "defaultstop");
                String cost = prefffs.getString("Cost", "defaultcost");
                String did = prefffs.getString("Did", "defaultdid");

                if(!"defaultselectedcitydriver".equals(city)&&
                        !"defaultvehical".equals(vehical)&&
                        !"colordefault".equals(color)&&
                        !"modledefault".equals(modle)&&
                        !"defaultplate".equals(platenumber)&&
                        !"defaultstop".equals(busstopsselected)&&
                        !"defaultcost".equals(cost)&&
                        !"defaultdid".equals(did))
                {
                    startActivity(new Intent(getApplicationContext(), drivershomepage.class));
                    finish();
                }
                else {


                    startActivity(new Intent(getApplicationContext(), vehicaldetails.class));
                    finish();
                }

            } else {
                Toast.makeText(MainActivity.this, "An error occured Please try after some time", Toast.LENGTH_SHORT).show();
            }


        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(networkChangeReceiver);
        super.onDestroy();
    }


}