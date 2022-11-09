package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import dhairyapandya.com.vanservice2.R;

public class changingtheme extends AppCompatActivity {
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    Button Changetheme;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkeditem;
    private String selected;
    private final String CHECKEDITEM = "checked_item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changingtheme);
        getSupportActionBar().setTitle("Theme");




        sharedPreferences = this.getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        switch (getCheckeditem()){
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

//                Intent i = new Intent(changingtheme.this, changingtheme.class);
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(i);
//                overridePendingTransition(0, 0);

                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

//                Intent j = new Intent(changingtheme.this, changingtheme.class);
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(j);
//                overridePendingTransition(0, 0);

                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//                Intent k = new Intent(changingtheme.this, changingtheme.class);
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(k);
//                overridePendingTransition(0, 0);

                break;

        }
        Changetheme=findViewById(R.id.changethemebtn);
        Changetheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });
    }
    //Trial from web to refresh the activity
//    private final Runnable m_Runnable = new Runnable()
//    {
//        public void run()
//
//        {
//            Toast.makeText(changingtheme.this,"in runnable",Toast.LENGTH_SHORT).show();
//
//            changingtheme.this.mHandler.postDelayed(m_Runnable,2000);
//        }
//
//    };

    private void showdialog() {
        String[] themes = this.getResources().getStringArray(R.array.theme);
        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(changingtheme.this);
        builder.setTitle("Select Theme");
        builder.setSingleChoiceItems(R.array.theme, getCheckeditem(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected=themes[i];
                checkeditem = i;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(selected ==null){
                    selected=themes[i];
                    checkeditem=i;
                }

                setCheckeditem(checkeditem);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog =builder.create();
        dialog.show();

    }



    private int getCheckeditem(){
        return sharedPreferences.getInt(CHECKEDITEM,0);
    }
    private void setCheckeditem(int i){
        editor.putInt(CHECKEDITEM,i);
        editor.apply();
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