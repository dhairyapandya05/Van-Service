package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.RatingView;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.login;

public class settings extends AppCompatActivity {
TextView Theme,Noti,Chatbot,Hands,Language,About;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);




        //Theme work goe here
        Theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), changingtheme.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid=item.getItemId();
        if(itemid==R.id.m1)
        {
            startActivity(new Intent(getApplicationContext(), RatingView.class));
        }
        if(itemid==R.id.m2)
        {
            startActivity(new Intent(getApplicationContext(),settings.class));

        }

        if(itemid==R.id.m3)
        {
            //Profile
            startActivity(new Intent(getApplicationContext(),profile.class));
        }

        return super.onOptionsItemSelected(item);
    }
}