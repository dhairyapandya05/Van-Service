package dhairyapandya.com.vanservice2.miscellaneous;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        int connectionType = NetworkUtil.getConnectivityStatus(context);
        Log.e("network reciever", "network reciever");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
//                Toast.makeText(context, "Not connected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, offlinepage.class);
                context.startActivity(i);

            } else {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            }
            if (connectionType==NetworkUtil.TYPE_MOBILE){
                Toast.makeText(context, "Mobile data", Toast.LENGTH_SHORT).show();
            }else if (connectionType==NetworkUtil.TYPE_WIFI){
                Toast.makeText(context, "Wifi", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
