package dhairyapandya.com.vanservice2.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;

public class selecteddriverconfirmation extends AppCompatActivity {
    ImageButton next, prev;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    String userName, usertype, userMobileno, useremail, userpassword, driversname, userselectedcity, userselectedboardingpoint, selecteddriversuniqueid, timein;
    String userID;

    private static final String TAG = "OK";
    TextView drivernaam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecteddriverconfirmation);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.previous);
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        drivernaam = findViewById(R.id.tvDriverSelectedMessage);
        ArrayList<String> commingcustomersarraylist = new ArrayList<String>();
        //Getting the data from shared preference
        userID = fAuth.getCurrentUser().getUid();
        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        userName = prefs.getString("Name", "XXX");
        usertype = prefs.getString("Use type", "Customer");
        userMobileno = prefs.getString("Mobile Number", "XXXX XXX XXX");
        useremail = prefs.getString("Mail ID", "abc@gmail.com");
        userpassword = prefs.getString("Password", "******");
        driversname = prefs.getString("selecteddrivername", "default driversname");
        userselectedcity = prefs.getString("Selected City", "default user selected city");
        userselectedboardingpoint = prefs.getString("Selected Boarding Point", "default user selected boardingpoint");
        selecteddriversuniqueid = prefs.getString("did", "DRIVERS UNIQUE ID");
        timein = FieldValue.serverTimestamp().toString();
        String userid = fAuth.getUid();//customers uid
        String message = "You have selected " + driversname + " as your driver.";

        // Create a SpannableString
        SpannableString spannableMessage = new SpannableString(message);

        // Make the driver's name bold
        int startIndex = message.indexOf(driversname);
        int endIndex = startIndex + driversname.length();
// Make the driver's name bold
        spannableMessage.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Add color to the driver's name
        spannableMessage.setSpan(new ForegroundColorSpan(Color.rgb(255, 160, 0)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the styled text to the TextView
        drivernaam.setText(spannableMessage);

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
                user.put("Time Customer Log in :", timein);
                user.put("Typeofuser", usertype);
                user.put("Password", userpassword);
                user.put("City", userselectedcity);
                user.put("BoardingPoint", userselectedboardingpoint);
                user.put("uid", userid);
                user.put("imageUrl","https://firebasestorage.googleapis.com/v0/b/van-service-75a7d.appspot.com/o/General%2Fman.png?alt=media&token=22d98403-9810-4b06-a605-797092e91553");
                user.put("NameofthfeDriver", driversname);
                user.put("Driversuniqueid", selecteddriversuniqueid);


                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSucess: user Profile is created for " + userid);
                        //updating the comming students array object in Drivers database
                        //Drivers STUFF
//                        DocumentReference abc = fstore.collection("Driver").document(selecteddriversuniqueid);
//                        abc.update("Registeredstudents", FieldValue.arrayUnion(userid));
//                        abc.update("Commingstudents", FieldValue.arrayUnion(userid));
                        startActivity(new Intent(selecteddriverconfirmation.this, usershomepage.class));
                        addData();

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

    private void addData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwqK5mCnS1bN8yOy6sdEVHTyiTLUOTgHK43mDIo5hsBiy9KRZM8YuJfbrJFgQdQHzfB/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Status", "Google Sheet mae bhi upload ho gaya");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "addStudent");
                params.put("uniqueid", userID);
                params.put("name", userName);
                params.put("mailid", useremail);
                params.put("mobile", userMobileno);
                params.put("typeofuser", usertype);
                params.put("city", userselectedcity);
                params.put("timestamp", timein);
                return params;
            }
        };
        int socketTimeout = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkChangeReceiver);
        super.onDestroy();
    }
}