package dhairyapandya.com.vanservice2.customer.availabledrivers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.miscellaneous.RecyclerViewInterface;

public class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder>{
private final RecyclerViewInterface recyclerViewInterface;

    ArrayList<modledriversdetails> userarraylist;

    public myadapter(ArrayList<modledriversdetails> userarraylist,RecyclerViewInterface recyclerViewInterface) {
        this.userarraylist = userarraylist;
        this.recyclerViewInterface = recyclerViewInterface;
    }




    @NonNull
    @Override
    public myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowavailabledrivers,parent,false);
        return new MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.MyViewHolder holder, int position) {
        modledriversdetails abc =userarraylist.get(position);
        holder.name.setText(abc.getName());
        holder.charge.setText("₹ "+abc.getCost());
        holder.email.setText(abc.getMailID());
        holder.vehical.setText(abc.getVehical());
        holder.mobile.setText(abc.getMobileNumber());
        holder.plateno.setText(abc.getPlatenumberofvehical());
        holder.color.setText(abc.getColorofVehical());
        holder.modle.setText(abc.getModleofvehical());


//holder.commers.get()

    }

    @Override
    public int getItemCount() {
        return userarraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,charge,email,mobile,vehical,plateno,color,modle;
        List<String> commers;
        public MyViewHolder(@NonNull View itemView , RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name=itemView.findViewById(R.id.drivername);
            charge=itemView.findViewById(R.id.priceperyear);
            email=itemView.findViewById(R.id.email);
            mobile=itemView.findViewById(R.id.mobile);
            vehical=itemView.findViewById(R.id.vehical);
            plateno=itemView.findViewById(R.id.platenumber);
            color=itemView.findViewById(R.id.color);
            modle= itemView.findViewById(R.id.modlenumber);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onitemclick(pos);
                        }
                    }
                }
            });
        }
    }
}
