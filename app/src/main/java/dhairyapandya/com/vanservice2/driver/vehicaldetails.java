package dhairyapandya.com.vanservice2.driver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.locationdetails;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;

public class vehicaldetails extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    Spinner cityroute, typeofvehicle;
    private static final String TAG = "OK";
    String selectedcity, selectedvehicle, did;
    String Name, Mobileno, email, password, usertype;
    ImageButton next, back;
    Button stops;
    String[] citylist = {"Select your route City", "Anand", "Ahmedabad", "Nadiad", "Vadodara"};
    String[] vehiclelist = {"Select type of vehicle", "van", "Bus", "Riksha"};
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<String> stopslist = new ArrayList<String>();
    EditText placeofliving, modle, colour, registrationplate, cost;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicaldetails);
        cityroute = findViewById(R.id.cityroute);
        typeofvehicle = findViewById(R.id.typeofvehical);
        modle = findViewById(R.id.modleno);
        colour = findViewById(R.id.colorofthevehicle);
        registrationplate = findViewById(R.id.platenumber);
        back = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        cost = findViewById(R.id.costt);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Array commingcustomersarraylist[] = {};
        Array registeredstudentslist[] = {};


        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String Name = prefs.getString("Name", "XXX");
        String usertype = prefs.getString("Use type", "Driver");
        String Mobileno = prefs.getString("Mobile Number", "XXXX XXX XXX");
        String email = prefs.getString("Mail ID", "abc@gmail.com");
        String password = prefs.getString("Password", "******");
        Log.e("Status", "Data received from shared preference");




        //typeof vehicle
        //spinner work
        ArrayAdapter adp1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vehiclelist);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeofvehicle.setAdapter(adp1);


        typeofvehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(vehicaldetails.this, "Please Select a valid Vehicle", Toast.LENGTH_SHORT).show();
                } else {
                    selectedvehicle = vehiclelist[i];

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //spinner work
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, citylist);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityroute.setAdapter(adp);


        cityroute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(vehicaldetails.this, "Please Select a valid city", Toast.LENGTH_SHORT).show();
                } else {
                    selectedcity = citylist[i];
                    if (selectedcity.equals("Anand")) {
                        listItems = getResources().getStringArray(R.array.anandstops);

                    } else if (selectedcity.equals("Nadiad")) {
                        listItems = getResources().getStringArray(R.array.nadiadstops);


                    } else if (selectedcity.equals("Ahmedabad")) {
                        listItems = getResources().getStringArray(R.array.ahmedabadastops);

                    } else if (selectedcity.equals("Vadodara")) {
                        listItems = getResources().getStringArray(R.array.vadodarastops);

                    } else {
//                        Toast.makeText(vehicaldetails.this, "Gadbad hai bhai code mae", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        stops = findViewById(R.id.selectstops);
        listItems = getResources().getStringArray(R.array.vadodarastops);
        checkedItems = new boolean[listItems.length];
        stops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(vehicaldetails.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < mUserItems.size(); i++) {
                            stopslist.add(listItems[mUserItems.get(i)]);

                        }
                        Toast.makeText(vehicaldetails.this, stopslist.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Data validation
                if (selectedcity.isEmpty()) {
                    Toast.makeText(vehicaldetails.this, "Select Appropriate city", Toast.LENGTH_SHORT).show();
                }if (selectedvehicle.isEmpty()) {
                    Toast.makeText(vehicaldetails.this, "Select Appropriate vehicle", Toast.LENGTH_SHORT).show();
                }if (colour.getText().toString().isEmpty()) {
                    Toast.makeText(vehicaldetails.this, "Enter Appropriate Color", Toast.LENGTH_SHORT).show();
                }if (modle.getText().toString().isEmpty()) {
                    Toast.makeText(vehicaldetails.this, "Enter Appropriate Modle", Toast.LENGTH_SHORT).show();
                }if (registrationplate.getText().toString().isEmpty()) {
                    Toast.makeText(vehicaldetails.this, "Enter Appropriate PLante number", Toast.LENGTH_SHORT).show();
                }if (cost.getText().toString().isEmpty()) {
                    Toast.makeText(vehicaldetails.this, "Enter Appropriate Cost", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(vehicaldetails.this, "Data validated", Toast.LENGTH_SHORT).show();



                //putting data to firestore
                did = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("Drivers").document(did);
                Map<String, Object> user = new HashMap<>();
                user.put("Name", Name);
                user.put("MobileNumber", Mobileno);
                user.put("MailID", email);
                user.put("Time Driver Log in :", FieldValue.serverTimestamp());
                user.put("Password", password);
                user.put("City", selectedcity);
                user.put("Vehical", selectedvehicle);
                user.put("Typeofuser", usertype);
                user.put("Modleofvehical", modle.getText().toString());
                user.put("Platenumberofvehical", registrationplate.getText().toString());
                user.put("Colorofvehical", colour.getText().toString());
                user.put("BusStopsSelectedbydriver", stopslist);
                user.put("Cost", cost.getText().toString());
                user.put("Did", did);
                user.put("Commingstudents", Arrays.asList(commingcustomersarraylist));
                user.put("Registeredstudents", Arrays.asList(registeredstudentslist));


                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                        editor.putString("City", selectedcity);
                        editor.putString("Vehical", selectedvehicle);
                        editor.putString("Color of vehical", colour.getText().toString());
                        editor.putString("Modle of vehical", modle.getText().toString());
                        editor.putString("Plate number of vehical", registrationplate.getText().toString());
                        editor.putString("BusStops Selected by driver", stopslist.toString());
                        editor.putString("Cost", cost.getText().toString());
                        editor.putString("Did", did);
                        editor.apply();
                        Log.e("Status", "Data added to shared preference");

                        Log.d(TAG, "onSucess: user Profile is created for " + did);
                        Toast.makeText(getApplicationContext(), "Ho gaya ", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(vehicaldetails.this, drivershomepage.class);
                        startActivity(i);


                    }
                });


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