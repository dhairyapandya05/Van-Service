package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import dhairyapandya.com.vanservice2.MainActivity;
import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.RatingView;
import dhairyapandya.com.vanservice2.customer.availabledrivers.availabledrivers;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.resetpassword;

public class profile extends AppCompatActivity {
    TextView Name,Email,Verification;
    String userid;
    FirebaseAuth fAuth;
//    ReviewManager manager;
//    ReviewInfo reviewInfo;
    CardView bot;
    FirebaseFirestore fStore;
    CardView Resetpassword,Editprofile,logoutbtn;
    Button Verifibut;
    ImageView Profilepicture,Trial;
    StorageReference storageReference;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bot=findViewById(R.id.chatbot);
        logoutbtn=findViewById(R.id.logout);
        Verification=findViewById(R.id.verifyemail);
        Verifibut=findViewById(R.id.verify);
        Name=findViewById(R.id.profileName);
        Email=findViewById(R.id.profileEmail);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userid=fAuth.getCurrentUser().getUid();
        Resetpassword=findViewById(R.id.resetpassword);
        Editprofile=findViewById(R.id.editprofile);
        Profilepicture=findViewById(R.id.profilepicture);
        storageReference= FirebaseStorage.getInstance().getReference();
        int progilephoto=R.drawable.man;

//        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
//
//        byte[] byteArray = getIntent().getByteArrayExtra("imageBitmap");
//
//        if (byteArray != null) {
//            // Convert the byte[] back to Bitmap
//            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//
//            // Set the Bitmap to the ImageView
//            Profilepicture.setImageBitmap(bitmap);
//        } else {
//            Profilepicture.setImageResource(R.drawable.man);
//        }

//code for email verification
        //Code for Email Verification Button
        //        Code to verify the email address
        SharedPreferences prefgs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String verifyemail = prefgs.getString("emailverified", "0");

        if ((!fAuth.getCurrentUser().isEmailVerified())&&verifyemail.equals("0")) {  //if email is not verified
            Verifibut.setVisibility(View.VISIBLE);
            Verification.setVisibility(View.VISIBLE);
        }
        Verifibut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Verification.setVisibility(View.GONE);
                        Verifibut.setVisibility(View.GONE);
                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(profile.this).edit();
                        edit.putString("emailverified","1");
                        edit.apply();

                    }
                });
            }
        });

//        Storingdatatofirestore
        //do the work of shared preference
        //DO THE WORK OF SHARED PREFERENCE
        SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();

        editor.putInt("Profilepicurl", progilephoto);
        editor.apply();
        //uploading image to firestore database
        Uri uri = Uri.parse("https://avatars.dicebear.com/api/miniavs/:dhairyapandya25@gmail.com.png");
        uploadImageToFirebase(uri);

        //Chatbot is selected
        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl="https://wa.me/+917834811114?text=Hi";
                Intent intentt = new Intent(Intent.ACTION_VIEW);
                intentt.setData(Uri.parse(wpurl));
                startActivity(intentt);
            }
        });

logoutbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
    }
});

        //        Code For reseting the password
        Resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), resetpassword.class));
            }
        });

        //CODE FOR GETTING THE DETAILS FROM SHARED PREFERENCE
        SharedPreferences preffs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String Namee = preffs.getString("Name", "XXX");
        String usertype=preffs.getString("Use type","Customer");
//        String imageuri=preffs.getString("image Url",null);
        String Mobileno = preffs.getString("Mobile Number", "XXXX XXX XXX");
        String email = preffs.getString("Mail ID", "abc@gmail.com");
        Name.setText(Namee);
        Email.setText(email);
//        if (imageuri != null) {
//            Glide.with(this).load(imageuri).into(Profilepicture); // Load the image from URL
//        }else{
//            Profilepicture.setImageResource(R.drawable.man);
//        }

        // Retrieve the image byte[] from Intent
        byte[] byteArray = getIntent().getByteArrayExtra("imageBitmap");

        if (byteArray != null) {
            // Convert the byte[] back to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            // Set the Bitmap to the ImageView
            Profilepicture.setImageBitmap(bitmap);
        } else {
            Profilepicture.setImageResource(R.drawable.man);
        }
        // Get the image URI from the Intent
//        String imageUrl = getIntent().getStringExtra("imageUrl");
//
//        if (imageUrl != null) {
//            Uri imageUri = Uri.parse(imageUrl);
//
//            // Set the image URI to the ImageView
//            Profilepicture.setImageURI(imageUri);
//        } else {
//            // Handle the case where no image URL was passed
//        }


        //Code for edit profile
        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(view.getContext(), editprofile.class);
                i.putExtra("fullname",Name.getText().toString());
                i.putExtra("email",Email.getText().toString());
                i.putExtra("phone",Mobileno);
                startActivity(i);
            }
        });



    }

    //uploading the image to firebase Storage
    private void uploadImageToFirebase(Uri imageUri) {
        //logic to upload image to firebase storage
        StorageReference fileRef =storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/Van Service.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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

            startActivity(new Intent(getApplicationContext(), settings.class));

        }

        if(itemid==R.id.m3)
        {

            startActivity(new Intent(getApplicationContext(),profile.class));


        }

        return super.onOptionsItemSelected(item);
    }

//    private void raingspart() {
//        manager= ReviewManagerFactory.create(profile.this);
//        com.google.android.play.core.tasks.Task<ReviewInfo> request = manager.requestReviewFlow();
//        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
//            @Override
//            public void onComplete(@NonNull Task<ReviewInfo> task) {
//                if(task.isSuccessful()){
//                    reviewInfo=task.getResult();
//                    com.google.android.play.core.tasks.Task<Void> flow = manager.launchReviewFlow(profile.this,reviewInfo);
//                    flow.addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//
//                        }
//                    });
//                }
//                else{
//                }
//            }
//        });
//    }


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
    }
}