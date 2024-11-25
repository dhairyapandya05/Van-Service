package dhairyapandya.com.vanservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;
import dhairyapandya.com.vanservice2.miscellaneous.profile;

public class resetpassword extends AppCompatActivity {
    EditText password,reenterpassword;
    Button Reset;
    FirebaseUser user;
    FirebaseAuth fAuth;
    Button Resetpasswordusingmail;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        getSupportActionBar().setTitle("Reset Password");
        password=findViewById(R.id.textinputedittextpassword);
        reenterpassword=findViewById(R.id.textinputedittextrepassword);
        Reset=findViewById(R.id.reset);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fAuth=FirebaseAuth.getInstance();
        Resetpasswordusingmail=findViewById(R.id.emailpasswordreset);

        //reset password using email
        Resetpasswordusingmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetmail=new EditText(view.getContext());
                final AlertDialog.Builder passwordresetDialog=new AlertDialog.Builder(view.getContext());
                passwordresetDialog.setTitle("Reset Password ?");
                passwordresetDialog.setMessage("Enter your Email to receive Email reset link");
                passwordresetDialog.setView(resetmail);

                passwordresetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Extract the email and send reset link
                        String mail=resetmail.getText().toString().trim();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                });
                passwordresetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close the dialog
                    }
                });
                passwordresetDialog.create().show();


            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((password.getText().toString().trim().isEmpty())||reenterpassword.getText().toString().trim().isEmpty())
                {
                }

                if(!(reenterpassword.getText().toString().trim().equals(password.getText().toString().trim().isEmpty())))
                {
                }
                user.updatePassword(password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(getApplicationContext(), profile.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
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