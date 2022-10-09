package dhairyapandya.com.vanservice2;

import static dhairyapandya.com.vanservice2.register.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class locationdetails extends AppCompatActivity {
Spinner city,boarding;
Button next,back;
String boardinglist[]={"Select your boarding point","Point A","Point B","Point C"};
    String citylist[]={"Select your city","Anand","Nadiad","Ahmedabad","Vadodara"};
String Selectedcity,selectedboardingpoint,boardingpoint,Name,Mobileno,email,password,Typeofuser;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationdetail);
        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
next = findViewById(R.id.next);
back=findViewById(R.id.previous);
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //store data to firestore and move to next activity
    }
});

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(locationdetails.this,register.class));
    }
});
        city=findViewById(R.id.cityspinner);
        boarding=findViewById(R.id.boardingpointspinner);
//spinner work
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item,citylist);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adp);


        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
//                    txt.setText("");
                    Toast.makeText(locationdetails.this, "Please Select a valid area", Toast.LENGTH_SHORT).show();
                }
                else {
//                    txt.setText(country[i]);
                     Selectedcity = citylist[i];
                     if(Selectedcity.equals("Anand")){
                         String boardinglist[]={"Select boarding point","Anandpoint A","Anandpoint B","Anandpoint C","Anandpoint D"};

                     }
                     else if(Selectedcity.equals("Nadiad"))
                     {
                         String boardinglist[]={"Select boarding point","Nadiadpoint A","Nadiadpoint B","Nadiadpoint C","Nadiadpoint D"};

                     }
                     else if(Selectedcity.equals("Ahmedabad"))
                     {
                         String boardinglist[]={"Select boarding point","Ahmedabadpoint A","Ahmedabadpoint B","Ahmedabadpoint C","Ahmedabadpoint D"};

                     }
                     else if(Selectedcity.equals("Vadodara"))
                     {
                         String boardinglist[]={"Select boarding point","Vadodarapoint A","Vadodarapoint B","Vadodarapoint C","Vadodarapoint D"};

                     }
                     else{
                         Toast.makeText(locationdetails.this, "Gadbad hai bhai code mae", Toast.LENGTH_SHORT).show();
                     }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        String boardinglist[]={};

        ArrayAdapter adpt = new ArrayAdapter(this, android.R.layout.simple_spinner_item,boardinglist);
        adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boarding.setAdapter(adpt);


        boarding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
//                    txt.setText("");
                    Toast.makeText(locationdetails.this, "Please Select a valid area", Toast.LENGTH_SHORT).show();
                }
                else {
//                    txt.setText(country[i]);
                    selectedboardingpoint = boardinglist[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });















        //getting data fro previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             Name = extras.getString("Name");
             Mobileno = extras.getString("Mobile Number");
             email = extras.getString("Mail ID");
             password = extras.getString("Password");
             Typeofuser = extras.getString("typeofuser");

            //The key argument here must match that used in the other activity
        }
//sending data to firebase
        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Storing the data in the firestore
                        String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference=fstore.collection(Typeofuser).document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Name",Name);
                        user.put("Mobile Number",Mobileno);
                        user.put("Mail ID",email);
                        user.put("Password",password);
                        user.put("Type of user",Typeofuser);
                        user.put("City",Selectedcity);
                        user.put("Boarding Point",selectedboardingpoint);


//                        user.put("Pickup point",pickuppoint);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"onSucess: user Profile is created for "+userID);
                            }
                        });
                        //sending the user in the main activity
                        startActivity(new Intent(getApplicationContext(), availabledrivers.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(locationdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}