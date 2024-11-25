package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dhairyapandya.com.vanservice2.R;

public class editprofile extends AppCompatActivity {
    EditText Name,Mobile;
    Button Save;
    String usertype;
    String downloadUrl;
    private Uri imageUri;
    ImageView Profilepic;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private FirebaseStorage storage;
    FirebaseUser user;
    StorageReference storageReference;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Name=findViewById(R.id.ename);
        Mobile=findViewById(R.id.emobilenumber);
        Save=findViewById(R.id.save);
        Profilepic=findViewById(R.id.profilepicture);
        fStore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
//        user=fAuth.getCurrentUser();
//        storageReference= FirebaseStorage.getInstance().getReference();

        SharedPreferences prefs = getSharedPreferences("Van Service users data", MODE_PRIVATE);

        usertype = prefs.getString("Use type", "Customer");
        String uid=fAuth.getCurrentUser().getUid();

        
        //getting the data from the previous activity
        Intent data= getIntent();
        String fullName = data.getStringExtra("fullname");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        Name.setText(fullName);
        Mobile.setText(phone);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Name.getText().toString().isEmpty() ||Mobile.getText().toString().isEmpty()){
                    return;
                }

                Map<String, Object> updates = new HashMap<>();
                updates.put("Name", Name.getText().toString()); // Example: String field
                updates.put("MobileNumber", Mobile.getText().toString());

                fStore.collection(usertype)
                        .document(uid)
                        .update(updates) // Update fields only
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Firestore", "Document updated successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Firestore", "Error updating document: " + e.getMessage());
                            }
                        });


                imagePart();


                SharedPreferences.Editor editor = getSharedPreferences("Van Service users data", MODE_PRIVATE).edit();
                editor.putString("Name", Name.getText().toString());
                editor.putString("Mobile Number", Mobile.getText().toString());
                editor.putString("image Url", downloadUrl);
                editor.apply();
                Log.e("Status", "Data added to shared preference");
                navigatetoProfile();
            }

            private void imagePart() {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.man);
                Bitmap bitmap=getBitmapFromImageView(Profilepic);
                // Compress the bitmap to a byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Use PNG if lossless quality is needed
                byte[] imageData = baos.toByteArray();

                // Reference to Firebase Storage (same path as the existing image)
                String imagePath = usertype+"/"+uid+"/profilePhoto.png"; // Keep the same path to replace
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference().child(imagePath);

                // Upload the new image to Firebase Storage (replaces the existing one)
                UploadTask uploadTask = storageRef.putBytes(imageData);

                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    // Get the updated download URL
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        downloadUrl = uri.toString();

                        // Update Firestore with the new URL
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//                        String documentId = "uid"; // Replace with your document ID
                        firestore.collection(usertype)
                                .document(uid)
                                .update("imageUrl", downloadUrl) // Update only the URL field
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "Image URL updated successfully!");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Failed to update Firestore", e);
                                });

                    }).addOnFailureListener(e -> {
                        Log.e("FirebaseStorage", "Failed to get download URL", e);
                    });
                }).addOnFailureListener(e -> {
                    Log.e("FirebaseStorage", "Image upload failed", e);
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

    }

    private void navigatetoProfile() {
// Check if the image is set in ImageView
        Drawable drawable = Profilepic.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            // Create an Intent to open Activity 2
            Intent intent = new Intent(getApplicationContext(), profile.class);

            // Put the Bitmap into the Intent as a Parcelable
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            intent.putExtra("imageBitmap", byteArray); // Pass byte[] as extra
            startActivity(intent);
            finish();
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            // Get the image URI
            Uri imageUri = data.getData();

            // Display the selected image in Profilepic
            Profilepic.setImageURI(imageUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            // Handle error
        } else {
            // User cancelled the picker
        }
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

    // Method to convert ImageView to Bitmap
    private Bitmap getBitmapFromImageView(ImageView imageView) {
        // Get the drawable from ImageView
        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof BitmapDrawable) {
            // If drawable is already a BitmapDrawable, extract the Bitmap
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            // For other types of Drawables, create a Bitmap manually
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);

            // Draw the drawable onto the Bitmap
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }
    }

}