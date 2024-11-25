package dhairyapandya.com.vanservice2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dhairyapandya.com.vanservice2.customer.usershomepage;
import dhairyapandya.com.vanservice2.driver.drivershomepage;

public class ConfirmationActivity extends AppCompatActivity {
Button goToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation);
        goToHome=findViewById(R.id.homeButton);

        //Shared preferences work
        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String usertype = prefs.getString("Use type", "Customer");

        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usertype=="Customer"){
                    startActivity(new Intent(getApplicationContext(), usershomepage.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(), drivershomepage.class));
                    finish();
                }
            }
        });
    }
}