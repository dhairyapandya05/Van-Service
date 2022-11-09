package dhairyapandya.com.vanservice2.miscellaneous;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;

import dhairyapandya.com.vanservice2.R;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!NetworkUtil.isconnectedtointernet(context)){
            //Internet is not connedcted
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.offlinedialog,null);
            builder.setView(layout_dialog);

            Button btnretry = layout_dialog.findViewById(R.id.btnRetry);
            //Show dialog
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawableResource(R.color.mycolor);
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);
            btnretry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }
}
