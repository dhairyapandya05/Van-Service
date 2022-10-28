package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.login;

public class settings extends AppCompatActivity {
TextView Theme,Noti,Chatbot,Hands,Language,About;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        Theme=findViewById(R.id.theme);
//        Noti=findViewById(R.id.notifications);
//        Hands=findViewById(R.id.hands);
//        Language =findViewById(R.id.language);
//        About=findViewById(R.id.about);


//        Thread td = new Thread() {
//            public void run() {
//                try {
//                    sleep(2750);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    Intent intent = new Intent(settings.this, login.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };
//        td.start();


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
//            Toast.makeText(this, "This is Home ", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Home ");
            startActivity(new Intent(getApplicationContext(),settings.class));
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
            i.putExtra("usertype","Customer");
            startActivity(i);
            finish();
            finish();

        }
        if(itemid==R.id.m4)
        {
            startActivity(new Intent(getApplicationContext(), aboutpage.class));
        }
        return super.onOptionsItemSelected(item);
    }
}