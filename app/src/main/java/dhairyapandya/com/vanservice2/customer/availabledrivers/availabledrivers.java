package dhairyapandya.com.vanservice2.customer.availabledrivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.selecteddriverconfirmation;
import dhairyapandya.com.vanservice2.miscellaneous.RecyclerViewInterface;
import dhairyapandya.com.vanservice2.miscellaneous.aboutpage;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.miscellaneous.profile;
import dhairyapandya.com.vanservice2.miscellaneous.settings;

public class availabledrivers extends AppCompatActivity implements RecyclerViewInterface {
    //    String Name, Mobileno, email, password, Typeofuser, City, Boardingpt;
    RecyclerView recview;
    myadapter abc;
    ArrayList<modledriversdetails> userarraylist;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availabledrivers);
        recview = findViewById(R.id.recycler1);
        db = FirebaseFirestore.getInstance();
        recview.setLayoutManager(new LinearLayoutManager(this));
//        Context context=getApplicationContext();
        userarraylist = new ArrayList<>();
        abc = new myadapter(userarraylist, this);
//EventChangeLishner();
        recview.setAdapter(abc);

        db.collection("Drivers").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            modledriversdetails obj = d.toObject(modledriversdetails.class);
                            userarraylist.add(obj);
                        }
                        abc.notifyDataSetChanged();
                    }
                });


        //getting the data from previous activity
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            Name = extras.getString("Name");
//            Mobileno = extras.getString("Mobile Number");
//            email = extras.getString("Mail ID");
//            password = extras.getString("Password");
//            Typeofuser = extras.getString("typeofuser");
//            City = extras.getString("SelectedCity");
//            Boardingpt = extras.getString("Selectedboardingpoint");
//            //The key argument here must match that used in the other activity
//        }

        //putting datato firestore
    }

//    private void EventChangeLishner() {
//        db.collection("Drivers")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error!=null)
//                        {
//                            Toast.makeText(availabledrivers.this, error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        for (DocumentChange dc :value.getDocumentChanges()){
//                            if (dc.getType()==DocumentChange.Type.ADDED){
//                                userarraylist.add(dc.getDocument().toObject(modledriversdetails.class));
//                            }
//                            abc.notifyDataSetChanged();
//                        }
//                    }
//                });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        if (itemid == R.id.m1) {
//            Toast.makeText(this, "This is Home ", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Home ");
            startActivity(new Intent(getApplicationContext(), settings.class));
        }
        if (itemid == R.id.m2) {
//            Toast.makeText(this, "This is Insert", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Insert ");
            startActivity(new Intent(getApplicationContext(), profile.class));

        }

        if (itemid == R.id.m3) {
//            Toast.makeText(this, "This is Insert", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Insert ");
//            startActivity(new Intent(getApplicationContext(),Profile.class));
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), login.class);
            i.putExtra("usertype", "Customer");
            startActivity(i);
            finish();
            finish();

        }

        if (itemid == R.id.m4) {
            startActivity(new Intent(getApplicationContext(), aboutpage.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onitemclick(int position) {
//        Toast.makeText(this, "Ho gayaaaa", Toast.LENGTH_SHORT).show();

        //STORE DETAILS IN SHARED PREFERENCES
        SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
        String drivername = userarraylist.get(position).getName();
        String did = userarraylist.get(position).getDid();
        Toast.makeText(this, "Bhai did ki value " + did, Toast.LENGTH_SHORT).show();
        editor.putString("selecteddrivername", drivername);
        editor.putString("driversuniqueid", did);
        editor.apply();

        Intent i = new Intent(availabledrivers.this, selecteddriverconfirmation.class);
//        i.putExtra("selecteddrivername",userarraylist.get(position).getName());
        startActivity(i);
//        onclick valla kaam
    }
}