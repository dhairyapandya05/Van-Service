package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
//                                SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
//                                editor.putString("Name", Name.getText().toString());
//                                editor.putString("Mobile Number", Mobile.getText().toString());
//                                editor.putString("Mail ID", Editedemail);
//                                editor.putString("Password", password);
                                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(editprofile.this).edit();
                                edit.putString("Name",Name.getText().toString());
//                                edit.putString(Settings.PREF_PASSWORD+"",txtpass);
                                edit.apply();
//                                editor.apply();


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

        //profile picture change
//        Profilepic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(editprofile.this, "Profile Pic clicked", Toast.LENGTH_SHORT).show();
//                Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent,1212);
//            }
//        });
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


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1212)
//        {
//            if(resultCode== Activity.RESULT_OK){
//                Uri imageUri=data.getData();
//
//                //this is the risky code
//
////                Intent i = new Intent(this, profilepage.class);
////                i.putExtra("imagePath", imageUri);
////                startActivity(i);
//                //Risky code ends
////                Uri selectedImage = data.getData();
//
////                Intent i = new Intent(this, profilepage.class);
////                i.putExtra("imagePath", selectedImage);
//                Profilepic.setImageURI(imageUri);
////                Glide.with(editprofile.this).load(imageUri).into(Profilepic);
//
//                uploadImageToFirebase(imageUri);
////                startActivity(i);
////                Uri uri = data.getData();
////                Intent intent=new Intent(editprofile.this,profilepage.class);
////                intent.putExtra("imageUri", uri.toString());
////                startActivity(intent);
////                Uri selectedImage = data.getData();
//////                Intent intent = new Intent(this, profilepage.class);
////                i.putExtra("imagePath", selectedImage);
////                startActivity(i);
//
////                    Intent intent =new Intent(editprofile.this,profilepage.class);
////                    intent.putExtra("xyz",Profilepic.);
////                    startActivity(intent);
//
////                Intent i = new Intent(this, profilepage.class);
////                Bitmap b=imageUri; // your bitmap
////                ByteArrayOutputStream bs = new ByteArrayOutputStream();
////                b.compress(Bitmap.CompressFormat.PNG, 50, bs);
////                i.putExtra("profileimage", bs.toByteArray());
////                startActivity(i);
//
////                Intent intent= new Intent(this, profilepage.class);
//////                intent.putExtra("image", Profilepic.getAvatar_id());
////                startActivity(intent);
//
//
////                Intent i = new Intent(this, profilepage.class);
////                Bitmap b; // your bitmap
////                ByteArrayOutputStream bs = new ByteArrayOutputStream();
//////                bs.compress(Bitmap.CompressFormat.PNG, 50, bs);
////                i.putExtra("byteArray", bs.toByteArray());
////                startActivity(i);
//
//            }
//        }
//    }

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
                        Profilepic.setImageURI(uri);
//                        Glide.with(editprofile.this).load(uri).into(Profilepic);
                        Toast.makeText(editprofile.this, "Sucesss Mil gai bhai .... Congo ", Toast.LENGTH_SHORT).show();

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
}