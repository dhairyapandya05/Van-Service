package dhairyapandya.com.vanservice2.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.register;

public class locationdetails extends AppCompatActivity {
    Spinner city, boarding;
    Button next, back;
    String citylist[] = {"Anand", "Nadiad", "Ahmedabad", "Vadodara"};
    String Selectedcity, selectedboardingpoint, boardingpoint, Name, Mobileno, email, password, Typeofuser;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String[] listItems = {};
    String userselectedcity, userselectedboardingpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationdetail);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        next = findViewById(R.id.next);
        back = findViewById(R.id.previous);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //STORE DETAILS IN SHARED PREFERENCES
                SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                editor.putString("Selected City", Selectedcity);
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
        city = findViewById(R.id.cityspinner);
        city.setPrompt("Select your City");

        boarding = findViewById(R.id.boardingpointspinner);
        city.setPrompt("Select your Boarding Point");

//spinner work
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, citylist);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adp);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0) {
////                    txt.setText("");
//                    Toast.makeText(locationdetails.this, "Please Select a valid city", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    txt.setText(country[i]);
                    userselectedcity = citylist[i];
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(locationdetails.this, "Please select a valid city", Toast.LENGTH_SHORT).show();
            }
        });


        String boardinglist[]={};

        ArrayAdapter adpt = new ArrayAdapter(this, android.R.layout.simple_spinner_item, boardinglist);
        adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boarding.setAdapter(adpt);


        boarding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0) {
////                    txt.setText("");
//                    Toast.makeText(locationdetails.this, "Please Select a valid area", Toast.LENGTH_SHORT).show();
//                }
//                else {
                    if (userselectedcity.equals("Anand")) {
//                        listItems = new String[]{"Central Bus Station Anand", "Central Bus Depot Anand", "Makarpura Naka Anand", "Anand city bus station", "Vinayak City Bus Service Anand", "Dashrath Bus Station Anand"};
                        String boardinglist[] = {"Central Bus Station Anand", "Central Bus Depot Anand", "Makarpura Naka Anand", "Anand city bus station", "Vinayak City Bus Service Anand", "Dashrath Bus Station Anand", "Anand Bus Station", "Central Bus Station Anand", "City Bus Depot Anand", "Makarpura Central Bus Station Anand"};
                        selectedboardingpoint = boardinglist[i];

                    } else if (userselectedcity.equals("Nadiad")) {
                        String boardinglist[] = {"Central Bus Station Nadiad", "Central Bus Depot Nadiad", "Makarpura Naka Nadiad", "Nadiad city bus station", "Vinayak City Bus Service Nadiad", "Dashrath Bus Station Nadiad", "Nadiad Bus Station", "Central Bus Station Nadiad", "City Bus Depot Nadiad", "Makarpura Central Bus Station Nadiad"};
                        selectedboardingpoint = boardinglist[i];


                    } else if (userselectedcity.equals("Ahmedabad")) {
//                        listItems = getResources().getStringArray(R.array.ahmedabadastops);
//                        Log.e("Status",listItems.toString());
                        String boardinglist[] = {"Central Bus Station Ahmedabadad", "Central Bus Depot Ahmedabadad", "Makarpura Naka Ahmedabadad", "Ahmedabadad city bus station", "Vinayak City Bus Service Ahmedabadad", "Dashrath Bus Station Ahmedabadad", "Ahmedabadad Bus Station", "Central Bus Station Ahmedabadad", "City Bus Depot Ahmedabadad", "Makarpura Central Bus Station Ahmedabadad"};
                        selectedboardingpoint = boardinglist[i];

                    } else if (userselectedcity.equals("Vadodara")) {
                        listItems = getResources().getStringArray(R.array.vadodarastops);
                        String boardinglist[] = {"Central Bus Station Vadodara", "Central Bus Depot Vadodara", "Makarpura Naka Vadodara", "Vadodara city bus station", "Vinayak City Bus Service Vadodara", "Dashrath Bus Station Vadodara", "Vadodara Bus Station", "Central Bus Station Vadodara", "City Bus Depot Vadodara", "Makarpura Central Bus Station Vadodara"};
                        selectedboardingpoint = boardinglist[i];

                    } else {
                        Toast.makeText(locationdetails.this, "Gadbad hai bhai code mae", Toast.LENGTH_SHORT).show();
                    }

//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //getting data fro previous activity
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//             Name = extras.getString("Name");
//             Mobileno = extras.getString("Mobile Number");
//             email = extras.getString("Mail ID");
//             password = extras.getString("Password");
//             Typeofuser = extras.getString("typeofuser");
//
//            //The key argument here must match that used in the other activity
//        }
//sending data to firebase
//        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        //Storing the data in the firestore
//                        String userID = fAuth.getCurrentUser().getUid();
//                        DocumentReference documentReference=fstore.collection(Typeofuser).document(userID);
//                        Map<String,Object> user = new HashMap<>();
//                        user.put("Name",Name);
//                        user.put("Mobile Number",Mobileno);
//                        user.put("Mail ID",email);
//                        user.put("Password",password);
//                        user.put("Type of user",Typeofuser);
//                        user.put("City",Selectedcity);
//                        user.put("Boarding Point",selectedboardingpoint);
//
//
////                        user.put("Pickup point",pickuppoint);
//
//                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d(TAG,"onSucess: user Profile is created for "+userID);
//                            }
//                        });
//                        //sending the user in the main activity
//                        startActivity(new Intent(getApplicationContext(), availabledrivers.class));
//                        finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(locationdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

    }
}