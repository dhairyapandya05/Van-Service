package dhairyapandya.com.vanservice2.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.miscellaneous.aboutpage;
import dhairyapandya.com.vanservice2.miscellaneous.profile;
import dhairyapandya.com.vanservice2.miscellaneous.settings;

public class usershomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usershomepage);
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
//            Toast.makeText(this, "This is Home ", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Home ");
            startActivity(new Intent(getApplicationContext(), settings.class));
        }
        if(itemid==R.id.m2)
        {
//            Toast.makeText(this, "This is Insert", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Insert ");
            startActivity(new Intent(getApplicationContext(), profile.class));

        }

        if(itemid==R.id.m3)
        {
//            Toast.makeText(this, "This is Insert", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Insert ");
//            startActivity(new Intent(getApplicationContext(),Profile.class));
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), login.class);
            i.putExtra("usertype","Driver");
            startActivity(i);
            finish();

        }
        if(itemid==R.id.m4)
        {
            startActivity(new Intent(getApplicationContext(), aboutpage.class));
        }
        return super.onOptionsItemSelected(item);
    }
}