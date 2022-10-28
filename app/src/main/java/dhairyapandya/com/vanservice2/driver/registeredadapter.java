package dhairyapandya.com.vanservice2.driver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dhairyapandya.com.vanservice2.R;

public class registeredadapter extends RecyclerView.Adapter<registeredadapter.MyViewHolderregister>{
    ArrayList<registeredmodle> userarraylist;

    public registeredadapter(ArrayList<registeredmodle> userarraylist) {
        this.userarraylist = userarraylist;
    }

    @NonNull
    @Override
    public registeredadapter.MyViewHolderregister onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.registeredstudentssinglelayout,parent,false);
        return new MyViewHolderregister(view);
    }

    @Override
    public void onBindViewHolder(@NonNull registeredadapter.MyViewHolderregister holder, int position) {
        registeredmodle abc =userarraylist.get(position);
        holder.cName.setText(abc.getName());
        holder.city.setText("₹ "+abc.getCity());
        holder.phno.setText(abc.getMobileNumber());
        holder.email.setText(abc.getMailID());
        holder.boardingpt.setText(abc.getBoardingPoint());
    }

    @Override
    public int getItemCount() {
        return userarraylist.size();
    }

    public class MyViewHolderregister extends RecyclerView.ViewHolder {
        TextView cName,email,boardingpt,city,phno;
        public MyViewHolderregister(@NonNull View itemView) {
            super(itemView);
            cName=itemView.findViewById(R.id.customername);
            email=itemView.findViewById(R.id.email);
            boardingpt=itemView.findViewById(R.id.boardingpt);
            city=itemView.findViewById(R.id.city);
            phno=itemView.findViewById(R.id.mobile);

        }
    }
}
