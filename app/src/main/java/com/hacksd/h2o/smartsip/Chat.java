package com.hacksd.h2o.smartsip;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import me.aflak.bluetooth.Bluetooth;

public class Chat extends AppCompatActivity implements Bluetooth.CommunicationCallback {
    private String name;
    private Bluetooth b;
    private EditText message;
    private Button send;
    private TextView text;
    private ScrollView scrollView;
    TextView volumeText;
    TextView tempText;

    FirebaseFirestore mFireStore;
    TextView nickName;
    String docPath;
    String waterName;
    TextView waterLeft;
    TextView timeWaterText;
    int timeWater;
    TextView recAmount;
    int waterComplete;
    Date date = new Date();
    TextView cups;
    Button cupButton;

    private boolean registered = false;

    public String waterVolume = "";
    public String waterTemp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        volumeText = (TextView) findViewById(R.id.volume_text);
        tempText = findViewById(R.id.temp_Text);

        text.setMovementMethod(new ScrollingMovementMethod()); // Allows you to scroll.
        send.setEnabled(false);

        b = new Bluetooth(this);
        b.enableBluetooth();

        b.setCommunicationCallback(this);

        int pos = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(pos).getName();

        Display(" Connecting...");
        b.connectToDevice(b.getPairedDevices().get(pos));
/*
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                message.setText("");
                b.send(msg);
                Display("You: " + msg);
            }
        });

*/
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered = true;


        //From home activity
        //mFireStore = FirebaseFirestore.getInstance();
/*
        docPath = getIntent().getExtras().getString("path");
        Log.d("Home", "BEEEEEEEEEEEEEEEEEPPPPPPPPPPPPPPP!!!!!! " + docPath);
        waterLeft = (TextView) findViewById(R.id.waterLeft_text);

        waterName = getIntent().getExtras().getString("nickName");
        Log.d("Home", "BEEEEEEEEEEEEEEEEEPPPPPPPPPPPPPPP!!!!!! " + waterName);
        nickName = (TextView) findViewById(R.id.w_nickname_text);
        nickName.setText(waterName);
        */
/*
        timeWaterText = findViewById(R.id.t_water_text);
        recAmount = findViewById(R.id.rec_water_text);
        cups = findViewById(R.id.cups_water);
        cupButton = findViewById(R.id.add_cup_button);

        cupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateWaterIntake(1);
            }
        });
        */


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registered) {
            unregisterReceiver(mReceiver);
            registered = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.close:
                b.removeCommunicationCallback();
                b.disconnect();
                Intent intent = new Intent(this, Select.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.rate:
                Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Display(final String s) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int sLength = s.length();
                if (sLength > 8) {
                    // Confirm we are looking at the right data flow.
                    if ((s.charAt(6)) == ' ') {
                        // Probably better implementation but hey, it works.
                        // NOTE: API outputs HC-06: before every data output.
                        if ((s.charAt(7)) == 't') {
                            waterTemp = getValueFromTerminal(sLength, s);
                            text.append("Temperature Variable is: " + waterTemp + "\n");
                            tempText.setText(waterTemp + " F");
                            waterTemp = "";
                        } else if ((s.charAt(7)) == 'd') {
                            waterVolume = getValueFromTerminal(sLength, s);
                            text.append("Volume Variable is: " + waterVolume + "\n");
                            volumeText.setText(waterVolume + " fld.oz");
                            //howFull()
                            //checkEmptyCup();
                            waterVolume = "";

                        }
                        // Extract temperature string here.
                        // Clear string.
                    }

                }
                b.send("String.\n");

                //b.send("string send");
                //text.append(s + "\n");
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public String getValueFromTerminal(int sLength, String s) {
        int i = 8;
        String returnValue = "";
        // Be sure not to read unallocated values.
        while (!(i == sLength)) {
            char c = s.charAt(i);
            String sTemp = Character.toString(c);
            returnValue += sTemp;
            i++;
        }
        return returnValue;
    }


    @Override
    public void onConnect(BluetoothDevice device) {
        Display("Connected to " + device.getName() + " - " + device.getAddress());
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                send.setEnabled(true);
            }
        });
    }

    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        Display("Disconnected!");
        Display(" Connecting again...");
        b.connectToDevice(device);
    }

    @Override
    public void onMessage(String message) {
        Display(name + ": " + message);
    }

    @Override
    public void onError(String message) {
        Display("Error: " + message);
    }

    @Override
    public void onConnectError(final BluetoothDevice device, String message) {
        Display("Error: " + message);
        Display("Trying again in 3 sec.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.connectToDevice(device);
                    }
                }, 2000);
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Intent intent1 = new Intent(Chat.this, Select.class);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        if (registered) {
                            unregisterReceiver(mReceiver);
                            registered = false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        if (registered) {
                            unregisterReceiver(mReceiver);
                            registered = false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        }
    };

    public void getConsumedWater() {
        String documentPath = "waterIntake/" + docPath;
        DocumentReference mDocRef = FirebaseFirestore.getInstance().document(documentPath);

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String amountWater = documentSnapshot.getString("consumedWater");
                    Log.d("DEMO", "WATEEEEEEEEEEEEEEERRRRRR:" + amountWater);
                    waterLeft.setText(amountWater);

                    String twater = documentSnapshot.getString("time");
                    String recwater = documentSnapshot.getString("dailyIntake");
                    timeWaterText.setText(twater);
                    recAmount.setText(recwater);

                }
            }
        });

        waterComplete = Integer.parseInt(waterLeft.getText().toString());
        cups.setText(((Integer.parseInt(recAmount.getText().toString())) - waterComplete));

    }


    public void UpdateWaterIntake(int numCups){
        waterComplete +=  numCups;
        waterLeft.setText(waterComplete);
        timeWater = (int) date.getTime();
        cups.setText(((Integer.parseInt(recAmount.getText().toString())) - waterComplete));
    }

    public int CheckTime(){
        int howLong = (int)date.getTime() - timeWater;
        howLong = (int) ((howLong/ 1000) /60);
        return howLong;
    }

    public int howFull(){
      int max = 16; // oz
      int v = Integer.parseInt( volumeText.getText().toString());
      if (v >= 13)
      {
          return 100;
      }
      else if( v >= 9 && v < 13 )
      {
          return 75;
      }
      else if ( v >= 5 && v < 9)
      {
          return 50;
      }
      else if (v >= 2 && v < 5){
          return 25;
      }
      else return 0;
    };

    public void checkEmptyCup(){
        if (howFull() == 0)
        {
            UpdateWaterIntake(1);
        }
    }


}
