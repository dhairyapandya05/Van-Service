package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import dhairyapandya.com.vanservice2.R;

public class editprofile extends AppCompatActivity {
    EditText Name,Email,Mobile;
    Button Save;
    ImageView Profilepic;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Name=findViewById(R.id.ename);
        Email=findViewById(R.id.eemailaddress);
        Mobile=findViewById(R.id.emobilenumber);
        Save=findViewById(R.id.save);
        Profilepic=findViewById(R.id.profilepicture);
        fStore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();

        //getting the data from the previous activity
        Intent data= getIntent();
        String fullName = data.getStringExtra("fullname");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        Name.setText(fullName);
        Email.setText(email);
        Mobile.setText(phone);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Name.getText().toString().isEmpty() ||Email.getText().toString().isEmpty() || Mobile.getText().toString().isEmpty()){
                    Toast.makeText(editprofile.this, "Empty Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Editedemail=Email.getText().toString();
                user.updateEmail(Editedemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef=fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited=new HashMap<>();
                        edited.put("Mail ID",Editedemail);
                        edited.put("Mobile Number",Mobile.getText().toString());
                        edited.put("Name",Name.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Also update it in shared preference

                                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(editprofile.this).edit();
                                edit.putString("Name",Name.getText().toString());
                                edit.apply();


                                Toast.makeText(editprofile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), profile.class));
                                finish();
                            }
                        });


                        Toast.makeText(editprofile.this, "Profile changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        Profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(editprofile.this)
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                        .compress(15)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)
                        //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        Log.d("Bhaiusercreatehogaya","onCreate : "+fullName+" "+email+" "+phone);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        Profilepic.setImageURI(uri);

    }




    private void uploadImageToFirebase(Uri imageUri) {
        //logic to upload image to firebase storage
        StorageReference fileRef =storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/Van Service.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Profilepic.setImageURI(uri);
//                        Toast.makeText(editprofile.this, "Sucesss Mil gai bhai .... Congo ", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(editprofile.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

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
    }
}