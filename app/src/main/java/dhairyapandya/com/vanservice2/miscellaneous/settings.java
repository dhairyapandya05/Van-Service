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

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.login;

public class settings extends AppCompatActivity {
TextView Theme,Noti,Chatbot,Hands,Language,About;
    ReviewManager manager;
    ReviewInfo reviewInfo;
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

            startActivity(new Intent(getApplicationContext(),settings.class));
        }
        if(itemid==R.id.m2)
        {

            startActivity(new Intent(getApplicationContext(), profile.class));

        }

        if(itemid==R.id.m3)
        {


            manager= ReviewManagerFactory.create(settings.this);
            com.google.android.play.core.tasks.Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                @Override
                public void onComplete(@NonNull Task<ReviewInfo> task) {
                    if(task.isSuccessful()){
                        reviewInfo=task.getResult();
                        com.google.android.play.core.tasks.Task<Void> flow = manager.launchReviewFlow(settings.this,reviewInfo);
                        flow.addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(settings.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}