package dhairyapandya.com.vanservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.driver.drivershomepage;

public class MainActivity extends AppCompatActivity {
    RadioGroup RG;
    ImageButton Next;
String usertype;
    RadioButton gen, customer, driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Next = findViewById(R.id.nxtbtn);
        RG = findViewById(R.id.radgrp);
        customer = (RadioButton) findViewById(R.id.Customer);
        driver = (RadioButton) findViewById(R.id.Driver);

        //Check the users entered data


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioID = RG.getCheckedRadioButtonId();
                gen = findViewById(radioID);
                 usertype = gen.getText().toString();

                 //do the work of shared preference
                //DO THE WORK OF SHARED PREFERENCE
                SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                editor.putString("Use type", usertype);
                editor.apply();
                Toast.makeText(MainActivity.this, "Data added from shared preference", Toast.LENGTH_SHORT).show();

                if (usertype.equals("Customer")) {
                    Intent intent = new Intent(MainActivity.this,login.class);
//                    intent.putExtra("usertype","Customer");
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Customer", Toast.LENGTH_SHORT).show();
                } else if (usertype.equals("Driver")) {
                    Intent intent = new Intent(MainActivity.this,login.class);
//                    intent.putExtra("usertype","Driver");
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Driver", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "An error occured Please try after some time", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
            String name = prefs.getString("Use type", "Customer"); //"Blank Name" the default value.
            Toast.makeText(this, "Data retreved from shared preference", Toast.LENGTH_SHORT).show();

            if (name.equals("Customer")) {
                startActivity(new Intent(getApplicationContext(), availabledrivers.class));
                finish();
            } else if (name.equals("Driver")) {
                startActivity(new Intent(getApplicationContext(), drivershomepage.class));
                finish();

            } else {
                Toast.makeText(MainActivity.this, "An error occured Please try after some time", Toast.LENGTH_SHORT).show();
            }

//            status=0;
//            Intent i = new Intent(login.this,profilefragment.class);
//            i.putExtra("STATUS",status);
//            startActivity(i);
        }
    }
}