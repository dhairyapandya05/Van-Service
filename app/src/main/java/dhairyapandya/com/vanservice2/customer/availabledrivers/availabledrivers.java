package dhairyapandya.com.vanservice2.customer.availabledrivers;

import static androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//import com.cunoraz.tagview.TagView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
//import com.ns.developer.tagview.entity.Tag;
//import com.ns.developer.tagview.widget.TagCloudLinkView;

import java.util.ArrayList;
import java.util.List;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.RatingView;
import dhairyapandya.com.vanservice2.customer.selecteddriverconfirmation;
import dhairyapandya.com.vanservice2.miscellaneous.NetworkChangeReceiver;
import dhairyapandya.com.vanservice2.miscellaneous.RecyclerViewInterface;
import dhairyapandya.com.vanservice2.miscellaneous.aboutpage;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.miscellaneous.profile;
import dhairyapandya.com.vanservice2.miscellaneous.settings;

public class availabledrivers extends AppCompatActivity implements RecyclerViewInterface {
    static int i = 0;
//    ReviewManager manager;
//    ReviewInfo reviewInfo;
    ImageView img;
ActionBar actionBar;

NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    RecyclerView recview;
    myadapter abc;
    ArrayList<modledriversdetails> userarraylist;
    ArrayList<String> filterlist = new ArrayList<String>();

    FirebaseFirestore db;

    String userselectedcity, userselectedboardingpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availabledrivers);

actionBar=getSupportActionBar();
//actionBar.
        img=findViewById(R.id.nodata);
        recview = findViewById(R.id.recycler1);
        db = FirebaseFirestore.getInstance();
        recview.setLayoutManager(new LinearLayoutManager(this));
        userarraylist = new ArrayList<>();
        abc = new myadapter(userarraylist, this);
        recview.setAdapter(abc);

        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);

         userselectedcity = prefs.getString("Selected City", "default user selected city");
         userselectedboardingpoint = prefs.getString("Selected Boarding Point", "default user selected boarding point");

        //working with tags
        if (!"defaultcity".equals(userselectedcity) && userselectedcity.equals("Anand")) {
            List<String> taglist = new ArrayList<String>();
            taglist.add("Central Bus Station");
            taglist.add("City Bus Depot");
            taglist.add("Makarpura Central Bus Station");
            taglist.add("Ashoka Temple");
            taglist.add("Vinayak Guest House");

            setTag(taglist); //Pass tag list to show tag view.

        } else if (!"defaultcity".equals(userselectedcity) && userselectedcity.equals("Nadiad")) {
            List<String> taglist = new ArrayList<String>();
            taglist.add("Central Bus Station");
            taglist.add("Makarpura Central");
            taglist.add("City Bus Depot");
            taglist.add("Nadiad Bus Station");
            taglist.add("Dashrath Bus Station");
            taglist.add("Vinayak City");
            taglist.add("Nadiad city bus station");
            taglist.add("Makarpura Naka");
            taglist.add("Central Bus Depot");
            setTag(taglist); //Pass tag list to show tag view.
        } else if (!"defaultcity".equals(userselectedcity) && userselectedcity.equals("Vadodara")) {
            List<String> taglist = new ArrayList<String>();
            taglist.add("Central Bus Station");
            taglist.add("Makarpura Central");
            taglist.add("City Bus Depot");
            taglist.add("Vadodara Bus Station");
            taglist.add("Dashrath Bus Station");
            taglist.add("Vinayak City");
            taglist.add("Vadodara city bus station");
            taglist.add("Makarpura Naka");
            taglist.add("Central Bus Depot");
            setTag(taglist); //Pass tag list to show tag view.
        } else if (!"defaultcity".equals(userselectedcity) && userselectedcity.equals("Ahmedabad")) {
            List<String> taglist = new ArrayList<String>();
            taglist.add("Central Bus Station");
            taglist.add("Makarpura Central");
            taglist.add("City Bus Depot");
            taglist.add("Ahmedabad Bus Station");
            taglist.add("Dashrath Bus Station");
            taglist.add("Vinayak City");
            taglist.add("Ahmedabad city bus station");
            taglist.add("Makarpura Naka");
            taglist.add("Central Bus Depot");
            setTag(taglist); //Pass tag list to show tag view.
        }

        db.collection("Driver")
                .whereEqualTo("City", userselectedcity)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            modledriversdetails obj = d.toObject(modledriversdetails.class);
                            userarraylist.add(obj);
                        }
                        abc.showshm = false;
                        abc.notifyDataSetChanged();


                        if(userarraylist.isEmpty())
                        {
                            abc.showshm=false;
                            img.setVisibility(View.VISIBLE);
                            abc.notifyDataSetChanged();

                        }

                    }
                });




        //putting datato firestore
    }

    private void setTag(List<String> tagList) {
        final ChipGroup chipGroup = findViewById(R.id.tag_group);
        for (int index = 0; index < tagList.size(); index++) {
            final String tagName = tagList.get(index);
            final Chip chip = new Chip(this);
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            Log.e("Status", userselectedboardingpoint+"dcvfdcxvcvc");
            if (tagName.toString().equals(userselectedboardingpoint)) {
                filterlist.add(userselectedboardingpoint);
                chip.onScreenStateChanged(DEFAULT_KEYS_DISABLE);
//                chip.setBackgroundColor(Color.parseColor("#00ff00"));
                chip.setChipBackgroundColorResource(R.color.purple_700);
                chip.setTextColor(Color.parseColor("#ffffff"));
            }
            chip.setCloseIconEnabled(false);

            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterlist.add(chip.getText().toString());
                    chip.setChipBackgroundColorResource(R.color.purple_700);
                    chip.setTextColor(Color.parseColor("#ffffff"));
                    i = 1;

                }
            });

            chipGroup.addView(chip);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        if (itemid == R.id.m1) {

            startActivity(new Intent(getApplicationContext(), RatingView.class));
        }
        if (itemid == R.id.m2) {

            startActivity(new Intent(getApplicationContext(), settings.class));

        }

        if (itemid == R.id.m3) {
            startActivity(new Intent(getApplicationContext(), profile.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onitemclick(int position) {

        //STORE DETAILS IN SHARED PREFERENCES
        SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
        String drivername = userarraylist.get(position).getName();
        String did = userarraylist.get(position).getuid();

        Log.e("Driver Part", "did:" + did);
        editor.putString("selecteddrivername", drivername);
        editor.putString("did", did);
        editor.apply();

        Intent i = new Intent(availabledrivers.this, selecteddriverconfirmation.class);
        startActivity(i);
//        onclick valla kaam
    }

    @Override
    protected void onStart() {
        IntentFilter filter =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver,filter);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkChangeReceiver);
        super.onDestroy();
//        unregisterNetwork();
    }
}