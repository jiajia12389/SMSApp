package sg.edu.rp.c346.smsapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText to,content;
    BroadcastReceiver br;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        to = findViewById(R.id.to);
        content = findViewById(R.id.content);
        btn = findViewById(R.id.button);

        br = new MessageReceiver();

        checkPermission();

        IntentFilter filter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        this.registerReceiver(br, filter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(to.getText().toString(), null, content.getText().toString(), null, null);
                Toast.makeText(MainActivity.this,"Message Sent", Toast.LENGTH_LONG).show();
                content.setText("");
            }
        });
    }

    private void checkPermission() {
        int permissionSendSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int permissionRecvSMS = ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);

        if (permissionSendSMS != PackageManager.PERMISSION_GRANTED &&permissionRecvSMS != PackageManager.PERMISSION_GRANTED) {
            String[] permissionNeeded = new String[]{Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissionNeeded, 1);
        }
    }
}
