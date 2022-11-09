package dhairyapandya.com.vanservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.customer.locationdetails;
import dhairyapandya.com.vanservice2.customer.usershomepage;
import dhairyapandya.com.vanservice2.driver.drivershomepage;
import dhairyapandya.com.vanservice2.driver.vehicaldetails;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;

public class login extends AppCompatActivity {
    String usetp;
    EditText username, password;
    TextView forgotpassword;
    ImageButton signupbutton;
    Button register;
    SignInButton btn;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    public static final int GOOGLE_CODE = 10005;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        signupbutton = findViewById(R.id.SignupButton);
        forgotpassword = findViewById(R.id.forgotpassword);
        register = findViewById(R.id.REGISTER);
        firebaseAuth = FirebaseAuth.getInstance();
        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();
        btn = findViewById(R.id.signinwalla);


//RETREVE DATA FROM THE SHARED PREFERENCE
        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String usertype = prefs.getString("Use type", "Customer"); //"Blank Name" the default value.



        //register Button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), register.class));
            }
        });

        //Signupbutton
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract the data
                //validate the data

                if (username.getText().toString().isEmpty()) {
                    username.setError("Username is missing");
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Password is missing");
                    return;
                }

                //make users entry in the application
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //login is sucessful
                                if (usertype.equals("Driver")) {
                                    Intent in = new Intent(login.this, drivershomepage.class);
                                    startActivity(in);
                                    finish();
                                } else if (usertype.equals("Customer")) {
                                    Intent i = new Intent(login.this, usershomepage.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                }



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


//        code for forgot password

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start alert dialog
                View view = inflater.inflate(R.layout.reset_pop, null);
                reset_alert.setTitle("Reset Forgot Password ?").setMessage("Enter Your Email to get password reset link.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //validate the email Address
                                EditText email = view.findViewById(R.id.reset_email_pop);
                                if (email.getText().toString().isEmpty()) {
                                    email.setError("Required Field");
                                    return;
                                }

                                //set the reset link
                                firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(login.this, "Reset Email Sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        }).setNegativeButton("Cancel", null).setView(view).create().show();
            }
        });
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