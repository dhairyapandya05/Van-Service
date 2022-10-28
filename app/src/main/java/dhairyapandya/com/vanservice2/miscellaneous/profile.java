package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.login;
import dhairyapandya.com.vanservice2.resetpassword;

public class profile extends AppCompatActivity {
    TextView Name,Email,Mobilenumber,Verification;
    String userid;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button Resetpassword,Verifibut,Editprofile;
    ImageView Profilepicture,Trial;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Verification=findViewById(R.id.verifyemail);
        Verifibut=findViewById(R.id.verify);
        Name=findViewById(R.id.nameview);
        Email=findViewById(R.id.emailview);
        Mobilenumber=findViewById(R.id.Mobilenumberview);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userid=fAuth.getCurrentUser().getUid();
        Resetpassword=findViewById(R.id.resetpassword);
        Editprofile=findViewById(R.id.editprofile);
        Profilepicture=findViewById(R.id.profilepicture);
        storageReference= FirebaseStorage.getInstance().getReference();

        String imgurl="https://avatars.dicebear.com/api/miniavs/:dhairyapandya25@gmail.com.png";
        Glide.with(this).load(imgurl).into(Profilepicture);
//        Storingdatatofirestore
        //do the work of shared preference
        //DO THE WORK OF SHARED PREFERENCE
        SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
        editor.putString("Profilepicurl", imgurl);
        editor.apply();
        //uploading image to firestore database
        Uri uri = Uri.parse("https://avatars.dicebear.com/api/miniavs/:dhairyapandya25@gmail.com.png");
        uploadImageToFirebase(uri);



        //        Code For reseting the password
        Resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), resetpassword.class));
            }
        });

        //CODE FOR GETTING THE DETAILS FROM SHARED PREFERENCE
        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);
        String Namee = prefs.getString("Name", "XXX");
        String Mobileno = prefs.getString("Mobile Number", "XXXX XXX XXX");
        String email = prefs.getString("Mail ID", "abc@gmail.com");
        Name.setText(Namee);
        Mobilenumber.setText(Mobileno);
        Email.setText(email);


        //Code for edit profile
        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(view.getContext(), editprofile.class);
                i.putExtra("fullname",Name.getText().toString());
                i.putExtra("email",Email.getText().toString());
                i.putExtra("phone",Mobilenumber.getText().toString());
                startActivity(i);
            }
        });
    }

    //uploading the image to firebase
    private void uploadImageToFirebase(Uri imageUri) {
        //logic to upload image to firebase storage
        StorageReference fileRef =storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/Van Service.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(profilepage.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                        Glide.with(profile.this).load(uri).into(Profilepic);
                        Toast.makeText(profile.this, "Image uploaded sucessfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profile.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();

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
            startActivity(new Intent(getApplicationContext(), settings.class));
        }
        if(itemid==R.id.m2)
        {
//            Toast.makeText(this, "This is Insert", Toast.LENGTH_SHORT).show();
//            txt.setText("This is Insert ");
            startActivity(new Intent(getApplicationContext(),profile.class));

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