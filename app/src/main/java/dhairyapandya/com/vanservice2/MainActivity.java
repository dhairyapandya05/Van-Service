package dhairyapandya.com.vanservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup RG;
    ImageButton Next;

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
                String name = gen.getText().toString();

                if (name.equals("Customer")) {
                    Intent intent = new Intent(MainActivity.this,register.class);
                    intent.putExtra("usertype","Customer");
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Customer", Toast.LENGTH_SHORT).show();
                } else if (name.equals("Driver")) {
                    Intent intent = new Intent(MainActivity.this,register.class);
                    intent.putExtra("usertype","Driver");
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Driver", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "An error occured Please try after some time", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}