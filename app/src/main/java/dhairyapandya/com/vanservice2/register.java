package dhairyapandya.com.vanservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.customer.locationdetails;
import dhairyapandya.com.vanservice2.driver.vehicaldetails;

public class register extends AppCompatActivity {
    public static final String TAG = "TAG";
    //    Spinner spn;
//    String country[]={"Select your Pick up point","Canal","Santram","College road","Vanyvad","Peplag chokdi"};
//    String pickuppoint;
    EditText Name, Mobilenumber, Emailaddress, Password, Confirmpassword;
    Button Signupbutton, login;
    FirebaseAuth fAuth;
//    FirebaseFirestore fstore;


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
//        fstore=FirebaseFirestore.getInstance();
//        spn=findViewById(R.id.spinner);
//Code for  the spinner work
//        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item,country);
//        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spn.setAdapter(adp);


//        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i==0)
//                {
////                    txt.setText("");
//                    Toast.makeText(register.this, "Please Select a valid area", Toast.LENGTH_SHORT).show();
//                }
//                else {
////                    txt.setText(country[i]);
//                    pickuppoint=country[i];
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        //code for sending user to login screen

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            usertype = extras.getString("usertype");
//            if (usertype.equals("Driver") || usertype.equals("Customer")) {
//                Toast.makeText(this, "All right", Toast.LENGTH_SHORT).show();
//
//            } else {
//                //never expect code to come here
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//            //The key argument here must match that used in the other activity
//        }

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




                //Lagta nahi hai nichae waalae code ki zarurat padaegi ....

                //if user is already logged in then directly to main activity
//                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                    startActivity(new Intent(getApplicationContext(), usershomepage.class));
//                    finish();
//                }

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

//                if (usertype.equals("Customer")) {
//                    Intent i = new Intent(register.this, locationdetails.class);
//                    i.putExtra("Name", name);
//                    i.putExtra("Mobile Number", mobilenumber);
//                    i.putExtra("Mail ID", email);
//                    i.putExtra("Password", password);
//                    i.putExtra("typeofuser", usertype);
//                    startActivity(i);
//                } else if (usertype.equals("Driver")) {
//                    Intent i = new Intent(register.this, vehicaldetails.class);
//                    i.putExtra("Name", name);
//                    i.putExtra("Mobile Number", mobilenumber);
//                    i.putExtra("Mail ID", email);
//                    i.putExtra("Password", password);
//                    i.putExtra("typeofuser", usertype);
//                    startActivity(i);
//                } else {
//                    Toast.makeText(register.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
//                }
                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Storing the data in the firestore
//                        userID=fAuth.getCurrentUser().getUid();
//                        DocumentReference documentReference=fstore.collection("users").document(userID);
//                        Map<String,Object> user = new HashMap<>();
//                        user.put("Name",name);
//                        user.put("Mobile Number",mobilenumber);
//                        user.put("Mail ID",email);
//                        user.put("Password",password);
//
////                        user.put("Pickup point",pickuppoint);
//
//                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d(TAG,"onSucess: user Profile is created for "+userID);
//                            }
//                        });

//STORING THE DATA TO THE SHARED PREFERENCE
                        //DO THE WORK OF SHARED PREFERENCE
                        SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                        editor.putString("Name", name);
                        editor.putString("Mobile Number", mobilenumber);
                        editor.putString("Mail ID", email);
                        editor.putString("Password", password);
                        editor.apply();
                        Toast.makeText(register.this, "Data added from shared preference", Toast.LENGTH_SHORT).show();

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
}