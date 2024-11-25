package dhairyapandya.com.vanservice2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import dhairyapandya.com.vanservice2.customer.locationdetails;
import dhairyapandya.com.vanservice2.customer.usershomepage;
import dhairyapandya.com.vanservice2.driver.drivershomepage;
import dhairyapandya.com.vanservice2.driver.vehicaldetails;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;

public class login extends AppCompatActivity {
    EditText username, password;
    TextView forgotpassword;
    ImageButton signupbutton;
    Button register;
    SignInButton btn;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    String usertype;
    private static final int RC_SIGN_IN = 1001;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(Exception.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (Exception e) {
                Log.e("Google Sign-In", "Error: " + e.getMessage());
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // Navigate to the next activity
                        navigationLogic();
                    } else {
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.rethinkmedium);
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
        usertype = prefs.getString("Use type", "Customer"); //"Blank Name" the default value.



        // google signin walla btn clicked
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Replace with your Firebase client ID
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });



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
                                navigationLogic();



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
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
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                            }
                        }).setNegativeButton("Cancel", null).setView(view).create().show();
            }
        });
    }

    private void navigationLogic() {
        if (usertype.equals("Driver")) {
            Intent driverIntent = new Intent(login.this, drivershomepage.class);
            startActivity(driverIntent);
            finish();
        } else if (usertype.equals("Customer")) {
            Intent customerIntent = new Intent(login.this, usershomepage.class);
            startActivity(customerIntent);
            finish();
        } else {
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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