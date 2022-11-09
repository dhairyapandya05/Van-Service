package dhairyapandya.com.vanservice2.customer.availabledrivers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import dhairyapandya.com.vanservice2.R;
import dhairyapandya.com.vanservice2.miscellaneous.RecyclerViewInterface;

public class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder>{
private final RecyclerViewInterface recyclerViewInterface;

    ArrayList<modledriversdetails> userarraylist;
public boolean showshm=true;
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
        if(showshm)
        {
            holder.name.setText(null);
            holder.charge.setText(null);
            holder.email.setText(null);
            holder.vehical.setText(null);
            holder.mobile.setText(null);
            holder.plateno.setText(null);
            holder.color.setText(null);
            holder.modle.setText(null);

            holder.etxt.setText(null);
            holder.vtxt.setText(null);
            holder.motxt.setText(null);
            holder.ptxt.setText(null);
            holder.ctxt.setText(null);
            holder.mtxt.setText(null);
            holder.shm.startShimmer();
        }

        else{
            modledriversdetails abc =userarraylist.get(position);
            holder.name.setBackground(null);
            holder.name.setText(abc.getName());
            holder.charge.setBackground(null);
            holder.charge.setText("₹ "+abc.getCost());
            holder.L1.setBackground(null);
            holder.email.setText(abc.getMailID());
            holder.L2.setBackground(null);

            holder.shm.stopShimmer();
            holder.shm.setShimmer(null);

            holder.vehical.setText(abc.getVehical());
            holder.L3.setBackground(null);

            holder.mobile.setText(abc.getMobileNumber());
            holder.L4.setBackground(null);

            holder.plateno.setText(abc.getPlatenumberofvehical());
            holder.L5.setBackground(null);

            holder.color.setText(abc.getColorofVehical());
            holder.L6.setBackground(null);

            holder.modle.setText(abc.getModleofvehical());
        }






//holder.commers.get()

    }

    @Override
    public int getItemCount() {
        int shimmerint=5;
        return showshm?shimmerint:userarraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,charge,email,mobile,vehical,plateno,color,modle;
        LinearLayout L1,L2,L3,L4,L5,L6;
        TextView etxt,mtxt,vtxt,ptxt,ctxt,motxt;
        ShimmerFrameLayout shm;
        List<String> commers;
        public MyViewHolder(@NonNull View itemView , RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            etxt=itemView.findViewById(R.id.emailtxt);
            mtxt=itemView.findViewById(R.id.mobiletxt);
            vtxt=itemView.findViewById(R.id.vehicaltxt);
            ptxt=itemView.findViewById(R.id.platenumbertxt);
            ctxt=itemView.findViewById(R.id.colortxt);
            motxt=itemView.findViewById(R.id.modletxt);
            L1=itemView.findViewById(R.id.l1);
            L2=itemView.findViewById(R.id.l2);
            L3=itemView.findViewById(R.id.l3);
            L4=itemView.findViewById(R.id.l4);
            L5=itemView.findViewById(R.id.l5);
            L6=itemView.findViewById(R.id.l6);
            name=itemView.findViewById(R.id.drivername);
            charge=itemView.findViewById(R.id.priceperyear);
            email=itemView.findViewById(R.id.email);
            mobile=itemView.findViewById(R.id.mobile);
            vehical=itemView.findViewById(R.id.vehical);
            plateno=itemView.findViewById(R.id.platenumber);
            color=itemView.findViewById(R.id.color);
            modle= itemView.findViewById(R.id.modlenumber);
shm=itemView.findViewById(R.id.shimmerlayoutdriversdetails);


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
