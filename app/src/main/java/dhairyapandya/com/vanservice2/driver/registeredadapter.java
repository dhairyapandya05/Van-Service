package dhairyapandya.com.vanservice2.driver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import dhairyapandya.com.vanservice2.R;

public class registeredadapter extends RecyclerView.Adapter<registeredadapter.MyViewHolderregister>{
    ArrayList<registeredmodle> userarraylist;
    public boolean showshimmer=true;

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
        if(showshimmer){
            holder.cName.setText(null);
            holder.ctxt.setText(null);
            holder.btxt.setText(null);
            holder.etxt.setText(null);
            holder.mtxt.setText(null);
            holder.city.setText(null);
            holder.phno.setText(null);
            holder.email.setText(null);
            holder.boardingpt.setText(null);
            holder.shimmerFrameLayout.startShimmer();
        }
        else{
            registeredmodle abc =userarraylist.get(position);
            holder.ctxt.setText("City");
            holder.btxt.setText("Boarding Pt");
            holder.etxt.setText("Email");
            holder.mtxt.setText("Mobile");
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.cName.setBackground(null);
            holder.cName.setText(abc.getName());

            holder.lincity.setBackground(null);
            holder.city.setText(abc.getCity());

            holder.linmob.setBackground(null);
            holder.phno.setText(abc.getMobileNumber());

            holder.linearemail.setBackground(null);
            holder.email.setText(abc.getMailID());

            holder.linboard.setBackground(null);
            holder.boardingpt.setText(abc.getBoardingPoint());
        }

    }

    @Override
    public int getItemCount() {
        int shimmeritemcount=5;
        return showshimmer?shimmeritemcount:userarraylist.size();
    }

    public class MyViewHolderregister extends RecyclerView.ViewHolder {
        TextView cName,email,boardingpt,city,phno;
        TextView ctxt,btxt,mtxt,etxt;
        ShimmerFrameLayout shimmerFrameLayout;
LinearLayout lincity,linboard,linearemail,linmob;
        public MyViewHolderregister(@NonNull View itemView) {
            super(itemView);
            ctxt=itemView.findViewById(R.id.citytxt);
            btxt=itemView.findViewById(R.id.boardingpttxt);
            mtxt=itemView.findViewById(R.id.mobiletxt);
            etxt=itemView.findViewById(R.id.emailtxt);
            shimmerFrameLayout=itemView.findViewById(R.id.shimmerlayout);
            cName=itemView.findViewById(R.id.customername);
            email=itemView.findViewById(R.id.email);
            boardingpt=itemView.findViewById(R.id.boardingpt);
            city=itemView.findViewById(R.id.city);
            phno=itemView.findViewById(R.id.mobile);
lincity=itemView.findViewById(R.id.linearcity);
linboard=itemView.findViewById(R.id.linboardingpt);
linearemail=itemView.findViewById(R.id.linemail);
linmob=itemView.findViewById(R.id.linmobile);


        }
    }
}
