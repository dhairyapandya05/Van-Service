package dhairyapandya.com.vanservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.customer.locationdetails;
import dhairyapandya.com.vanservice2.driver.vehicaldetails;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;

public class register extends AppCompatActivity {
    public static final String TAG = "TAG";

    EditText Name, Mobilenumber, Emailaddress, Password, Confirmpassword;
    ImageButton Signupbutton;
        Button login;
    FirebaseAuth fAuth;
NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Mobilenumber = findViewById(R.id.Mobilenumber);
        Emailaddress = findViewById(R.id.Email);
        Name = findViewById(R.id.Name);
        Password = findViewById(R.id.password);
        Confirmpassword = findViewById(R.id.Cpassword);
        Signupbutton = findViewById(R.id.SignupButton);
        login = findViewById(R.id.REGISTER);

        fAuth = FirebaseAuth.getInstance();





        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String usertype = prefs.getString("Use type", "Customer"); //"Blank Name" the default value.

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
            }
        });
        //register button code
        Signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String mobilenumber = Mobilenumber.getText().toString();
                String email = Emailaddress.getText().toString();
                String password = Password.getText().toString();
                String confirmpasword = Confirmpassword.getText().toString();






                if (name.isEmpty()) {
                    Name.setError("Name is required");
                    return;
                }

                if (mobilenumber.isEmpty()) {
                    Mobilenumber.setError("Mobile is required");
                    return;
                }
                if (email.isEmpty()) {
                    Emailaddress.setError("Email is required");
                    return;
                }
                if (password.isEmpty()) {
                    Password.setError("Password is required");
                    return;
                }
                if (confirmpasword.isEmpty()) {
                    Confirmpassword.setError("Confirm Password is required");
                    return;
                }
                if (!password.equals(confirmpasword)) {
                    Confirmpassword.setError("Password do not match");
                    return;
                }
                // data is validated
                Toast.makeText(register.this, "Data Validated", Toast.LENGTH_SHORT).show();


                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Storing the data in the firestore


//STORING THE DATA TO THE SHARED PREFERENCE
                        //DO THE WORK OF SHARED PREFERENCE
                        SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                        editor.putString("Name", name);
                        editor.putString("Mobile Number", mobilenumber);
                        editor.putString("Mail ID", email);
                        editor.putString("Password", password);
                        editor.apply();
//                        Toast.makeText(register.this, "Data added from shared preference", Toast.LENGTH_SHORT).show();

                        //sending the user in the main activity

                        if (usertype.equals("Driver")) {
                            Log.e("Driver Section","Line number 194");

                            Intent in = new Intent(register.this, vehicaldetails.class);
                            startActivity(in);
                        } else if (usertype.equals("Customer")) {
                            Log.e("Customer Section","Line number 207");

                            Intent i = new Intent(register.this, locationdetails.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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