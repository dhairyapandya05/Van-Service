package dhairyapandya.com.vanservice2.driver.viewpageradapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.driver.registeredadapter;
import dhairyapandya.com.vanservice2.driver.registeredmodle;


public class Registered extends Fragment {

    RecyclerView rec;
    registeredadapter abc;
    ArrayList<registeredmodle> userarraylist;
    FirebaseFirestore db;
    public Registered() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_registered, container, false);
        // Inflate the layout for this fragment
        rec = view.findViewById(R.id.recview);
        db = FirebaseFirestore.getInstance();
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
//        Context context=getApplicationContext();
        userarraylist = new ArrayList<>();
        abc = new registeredadapter(userarraylist);
//EventChangeLishner();
        rec.setAdapter(abc);

        db.collection("Customer").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            registeredmodle obj = d.toObject(registeredmodle.class);
                            userarraylist.add(obj);
                        }
                        abc.notifyDataSetChanged();
                    }
                });

        return view;
    }
}