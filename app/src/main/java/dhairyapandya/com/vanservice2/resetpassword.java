package dhairyapandya.com.vanservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dhairyapandya.com.vanservice2.miscellaneous.profile;

public class resetpassword extends AppCompatActivity {
    EditText password,reenterpassword;
    Button Reset;
    FirebaseUser user;
    FirebaseAuth fAuth;
    Button Resetpasswordusingmail;
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
                                Toast.makeText(resetpassword.this, "Reset link Sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(resetpassword.this, "Error Reset link not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(resetpassword.this,"Password field required",Toast.LENGTH_SHORT).show();
                }
//                if(reenterpassword.getText().toString().isEmpty())
//                {
//                    Toast.makeText(resetpassword.this,"Password field required",Toast.LENGTH_SHORT).show();
//                }
                if(!(reenterpassword.getText().toString().trim().equals(password.getText().toString().trim().isEmpty())))
                {
//                    Toast.makeText(resetpassword.this,"Password do not match",Toast.LENGTH_SHORT).show();
                }
                user.updatePassword(password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(resetpassword.this,"Password Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), profile.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(resetpassword.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}