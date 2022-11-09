package dhairyapandya.com.vanservice2.driver.viewpageradapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.driver.commingadapter;
import dhairyapandya.com.vanservice2.driver.commingadapter;
import dhairyapandya.com.vanservice2.driver.commingmodle;


public class Comming extends Fragment {
    ImageView img;
    ShimmerFrameLayout shimmerFrameLayout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore dbcustomersdetails = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Drivers");
private FirebaseAuth fAuth=FirebaseAuth.getInstance();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rec;
    commingadapter abc;
    ArrayList<commingmodle> userarraylist;
    private String mParam1;
    private String mParam2;

    public Comming() {

    }


    public static Comming newInstance(String param1, String param2) {
        Comming fragment = new Comming();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int k = 0;

        View view =inflater.inflate(R.layout.fragment_comming, container, false);

        img=view.findViewById(R.id.nodata);
        rec = view.findViewById(R.id.recviewc);
        rec.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userarraylist = new ArrayList<>();
        abc = new commingadapter(userarraylist);
        rec.setAdapter(abc);
        db = FirebaseFirestore.getInstance();
        dbcustomersdetails = FirebaseFirestore.getInstance();





        String did = fAuth.getCurrentUser().getUid();
        db.collection("Drivers").document(did).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        List<String> group = (List<String>) documentSnapshot.get("Commingstudents");

                        //code for extracting the list from the database
                        for ( int p = 0; p < group.size(); p++) {

                            // Print all elements of List
                            Toast.makeText(view.getContext(), group.get(p).toString(), Toast.LENGTH_SHORT).show();
//            yae ek particular student ki id hai ....
                            dbcustomersdetails.collection("Customer")
                                    .whereEqualTo("uid",group.get(p))
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                                            for (DocumentSnapshot d : list) {
                                                commingmodle obj = d.toObject(commingmodle.class);
                                                userarraylist.add(obj);
                                            }

                                            abc.showshimmer=false;
                                            abc.notifyDataSetChanged();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(view.getContext(), "Andar kae database mae kuch loccha ho gaya hai marae bhai ", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                        }

                        Log.e("Status",documentSnapshot.getData().get("Commingstudents").toString());
//        }
                        if(group.isEmpty())
                        {
                            abc.showshimmer=false;
                            img.setVisibility(View.VISIBLE);
                            abc.notifyDataSetChanged();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Some failure happened bro", Toast.LENGTH_SHORT).show();

                    }
                });



        return view;
    }



}