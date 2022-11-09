package dhairyapandya.com.vanservice2.miscellaneous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

import dhairyapandya.com.vanservice2.R;


public class aboutpage extends AppCompatActivity {
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutpage);
        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        fancyAboutPage.setCover(R.drawable.aboutdisplaypicture); //Pass your cover image
        fancyAboutPage.setName("Van Service");
        fancyAboutPage.setDescription("Van service is a Self Regulated Android Application | Android App and Software Developer.");
        fancyAboutPage.setAppIcon(R.mipmap.ic_launcher); //Pass your app icon image
        fancyAboutPage.setAppName("Van Service");
        fancyAboutPage.setVersionNameAsAppSubTitle("1.2.0");
        fancyAboutPage.setAppDescription("Van Service is an Android Application that aims to cater the needs of the common men\n\n" +
                "It helps to migerate from one place to another (College/School to Home ) with proper protocols and safety!\n\n"+
                "It aims to make travelling easy and thus helping the students as well as students to migerate from one place to anoher with an ease.");
        fancyAboutPage.addEmailLink("20ce069@charusat.edu.in");     //Add your email id
        fancyAboutPage.addFacebookLink("https://www.facebook.com/shashanksinghal02");  //Add your facebook address url
        fancyAboutPage.addTwitterLink("https://twitter.com/shashank020597");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/shashank-singhal-a87729b5/");
        fancyAboutPage.addGitHubLink("https://github.com/Shashank02051997");

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
}