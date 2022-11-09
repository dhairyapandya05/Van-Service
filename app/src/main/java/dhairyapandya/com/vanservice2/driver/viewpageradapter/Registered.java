package dhairyapandya.com.vanservice2.driver.viewpageradapter;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.customer.availabledrivers.modledriversdetails;
import dhairyapandya.com.vanservice2.customer.availabledrivers.myadapter;
import dhairyapandya.com.vanservice2.driver.registeredadapter;
import dhairyapandya.com.vanservice2.driver.registeredmodle;


public class Registered extends Fragment {
    ImageView img;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore dbcustomersdetails = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Drivers");
    private FirebaseAuth fAuth=FirebaseAuth.getInstance();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RecyclerView rec;
    registeredadapter abc;
    ArrayList<registeredmodle> userarraylist;
    public Registered() {
        // Required empty public constructor
    }

    public static Registered newInstance(String param1, String param2) {
        Registered fragment = new Registered();
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

        View view =inflater.inflate(R.layout.fragment_registered, container, false);

        img=view.findViewById(R.id.nodata);
        rec = view.findViewById(R.id.recview);
        rec.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userarraylist = new ArrayList<>();
        abc = new registeredadapter(userarraylist);
        rec.setAdapter(abc);
        db = FirebaseFirestore.getInstance();
        dbcustomersdetails = FirebaseFirestore.getInstance();




        String did = fAuth.getCurrentUser().getUid();
        db.collection("Drivers").document(did).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {

        List<String> group = (List<String>) documentSnapshot.get("Registeredstudents");

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
                            Toast.makeText(view.getContext(), list.toString()+"fdfdfd", Toast.LENGTH_SHORT).show();

                            for (DocumentSnapshot d : list) {
                                registeredmodle obj = d.toObject(registeredmodle.class);
                                userarraylist.add(obj);
                            }
                            if(userarraylist.isEmpty())
                            {
//                                abc.showshimmer=false;
                                rec.setVisibility(View.GONE);
                                img.setVisibility(View.VISIBLE);
//                                abc.notifyDataSetChanged();

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
        if(group.isEmpty())
        {
            abc.showshimmer=false;
            img.setVisibility(View.VISIBLE);
            abc.notifyDataSetChanged();

        }

//        }

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