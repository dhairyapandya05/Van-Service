package dhairyapandya.com.vanservice2.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;

public class selecteddriverconfirmation extends AppCompatActivity {
Button next,prev;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;

    String userID;
    private static final String TAG = "OK";
TextView drivernaam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecteddriverconfirmation);
        next = findViewById(R.id.next);
        prev=findViewById(R.id.previous);
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        drivernaam=findViewById(R.id.drivername);
        ArrayList<String> commingcustomersarraylist = new ArrayList<String>();
        //Getting the data from shared preference
        userID=fAuth.getCurrentUser().getUid();
        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String userName = prefs.getString("Name", "XXX");
        String usertype = prefs.getString("Use type", "Customer");
        String userMobileno = prefs.getString("Mobile Number", "XXXX XXX XXX");
        String useremail = prefs.getString("Mail ID", "abc@gmail.com");
        String userpassword = prefs.getString("Password", "******");
        String driversname = prefs.getString("selecteddrivername", "default driversname");
        String userselectedcity = prefs.getString("Selected City", "default user selected city");
        String userselectedboardingpoint = prefs.getString("Selected City", "default user selected boardingpoint");
        String selecteddriversuniqueid = prefs.getString("driversuniqueid", "DRIVERS UNIQUE ID");
//        Toast.makeText(this, userName+"line 53", Toast.LENGTH_SHORT).show();
        String userid=fAuth.getUid();//customers uid
        drivernaam.setText(driversname);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adding user to the drivers database
                //putting data to firestore


                //Storeing the customers data to firestore database
                DocumentReference documentReference = fstore.collection("Customer").document(userid);
                Map<String, Object> user = new HashMap<>();
                user.put("Name", userName);
                user.put("MobileNumber", userMobileno);
                user.put("MailID", useremail);
                user.put("Typeofuser", usertype);
                user.put("Password", userpassword);
                user.put("City", userselectedcity);
                user.put("BoardingPoint", userselectedboardingpoint);
                user.put("uid", userid);
                user.put("NameofthfeDriver", driversname);
                user.put("Driversuniqueid",selecteddriversuniqueid);



//                        user.put("Pickup point",pickuppoint);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {



                        Log.d(TAG, "onSucess: user Profile is created for " + userid);

                        //taking a field from database
//                        //Map to add user to array
//final Map<String, Object> addUserToArrayMap = new HashMap<>();
//addUserToArrayMap.put("Comming", FieldValue.arrayUnion(commingcustomersarraylist));

//                        Toast.makeText(selecteddriverconfirmation.this, "Before"+commingcustomersarraylist.toString(), Toast.LENGTH_SHORT).show();
//                        commingcustomersarraylist.add(userName);
//                        Toast.makeText(selecteddriverconfirmation.this, "After"+commingcustomersarraylist.toString(), Toast.LENGTH_SHORT).show();
                        //updating the comming students array object in Drivers database
                        //Drivers STUFF
                        Toast.makeText(selecteddriverconfirmation.this, selecteddriversuniqueid, Toast.LENGTH_SHORT).show();
                        DocumentReference abc = fstore.collection("Drivers").document(selecteddriversuniqueid);
                        abc.update("Commingstudents", FieldValue.arrayUnion(userName));
                        Toast.makeText(getApplicationContext(), "Update ho gaya ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(selecteddriverconfirmation.this, usershomepage.class));
//                        startActivity(i);


                    }
                });

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selecteddriverconfirmation.this, availabledrivers.class));

            }
        });
    }
}