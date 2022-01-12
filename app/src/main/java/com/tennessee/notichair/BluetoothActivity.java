package com.tennessee.notichair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class BluetoothActivity extends AppCompatActivity {
    Button mBtnBluetoothOn;
    Button mBtnBluetoothOff;
    Button mBtnConnect;

    CardView sensor0, sensor1, sensor2, sensor3, sensor4, sensor5, sensor6, sensor7, sensor8, sensor9, sensor10;
    CardView sensor11, sensor12, sensor13, sensor14, sensor15, sensor16, sensor17, sensor18, sensor19, sensor20;
    CardView sensor21, sensor22, sensor23, sensor24, sensor25, sensor26, sensor27, sensor28, sensor29, sensor30;

    CircleImageView image_posture;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bluetooth);

        // mTvSendData = findViewById(R.id.tvSendData);
        mBtnBluetoothOn = findViewById(R.id.btnBluetoothOn);
        mBtnBluetoothOff = findViewById(R.id.btnBluetoothOff);
        mBtnConnect = findViewById(R.id.btnConnect);

        sensor0 = findViewById(R.id.sensor0);
        sensor1 = findViewById(R.id.sensor1);
        sensor2 = findViewById(R.id.sensor2);
        sensor3 = findViewById(R.id.sensor3);
        sensor4 = findViewById(R.id.sensor4);
        sensor5 = findViewById(R.id.sensor5);
        sensor6 = findViewById(R.id.sensor6);
        sensor7 = findViewById(R.id.sensor7);
        sensor8 = findViewById(R.id.sensor8);
        sensor9 = findViewById(R.id.sensor9);
        sensor10 = findViewById(R.id.sensor10);
        sensor11 = findViewById(R.id.sensor11);
        sensor12 = findViewById(R.id.sensor12);
        sensor13 = findViewById(R.id.sensor13);
        sensor14 = findViewById(R.id.sensor14);
        sensor15 = findViewById(R.id.sensor15);
        sensor16 = findViewById(R.id.sensor16);
        sensor17 = findViewById(R.id.sensor17);
        sensor18 = findViewById(R.id.sensor18);
        sensor19 = findViewById(R.id.sensor19);
        sensor20 = findViewById(R.id.sensor20);
        sensor21 = findViewById(R.id.sensor21);
        sensor22 = findViewById(R.id.sensor22);
        sensor23 = findViewById(R.id.sensor23);
        sensor24 = findViewById(R.id.sensor24);
        sensor25 = findViewById(R.id.sensor25);
        sensor26 = findViewById(R.id.sensor26);
        sensor27 = findViewById(R.id.sensor27);
        sensor28 = findViewById(R.id.sensor28);
        sensor29 = findViewById(R.id.sensor29);
        sensor30 = findViewById(R.id.sensor30);

        image_posture = findViewById(R.id.image_posture);


        //   mBtnSendData = findViewById(R.id.btnSendData);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        mBtnBluetoothOn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOn();
            }
        });
        mBtnBluetoothOff.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOff();
            }
        });
        mBtnConnect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPairedDevices();
            }
        });
        /*mBtnSendData.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mThreadConnectedBluetooth != null) {
                    mThreadConnectedBluetooth.write(mTvSendData.getText().toString());
                    mTvSendData.setText("");
                }
            }
        });*/
        mBluetoothHandler = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void handleMessage(android.os.Message msg) {
                if (msg.what == BT_MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String[] seStr = readMessage.split(",");
                    int[] seInt = new int[31];
                    for (int i = 0; i < 31; i++) {
                        try {
                            seInt[i] = Integer.parseInt(seStr[i]);
                        }catch (NumberFormatException e){
                            seInt[i] = 0;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    if (seInt[0] < 20) {
                        sensor0.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[0] >= 20 && seInt[0] < 70) {
                        sensor0.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[0] >= 70 && seInt[0] < 120) {
                        sensor0.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[0] >= 120 && seInt[0] < 170) {
                        sensor0.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor0.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[1] < 20) {
                        sensor1.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[1] >= 20 && seInt[1] < 70) {
                        sensor1.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[1] >= 70 && seInt[1] < 120) {
                        sensor1.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[1] >= 120 && seInt[1] < 170) {
                        sensor1.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor1.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[2] < 20) {
                        sensor2.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[2] >= 20 && seInt[2] < 70) {
                        sensor2.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[2] >= 70 && seInt[2] < 120) {
                        sensor2.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[2] >= 120 && seInt[2] < 170) {
                        sensor2.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor2.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[3] < 20) {
                        sensor3.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[3] >= 20 && seInt[3] < 70) {
                        sensor3.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[3] >= 70 && seInt[3] < 120) {
                        sensor3.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[3] >= 120 && seInt[3] < 170) {
                        sensor3.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor3.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[4] < 20) {
                        sensor4.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[4] >= 20 && seInt[4] < 70) {
                        sensor4.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[4] >= 70 && seInt[4] < 120) {
                        sensor4.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[4] >= 120 && seInt[4] < 170) {
                        sensor4.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor4.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[5] < 20) {
                        sensor5.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[5] >= 20 && seInt[5] < 70) {
                        sensor5.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[5] >= 70 && seInt[5] < 120) {
                        sensor5.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[5] >= 120 && seInt[5] < 170) {
                        sensor5.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor5.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[6] < 20) {
                        sensor6.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[6] >= 20 && seInt[6] < 70) {
                        sensor6.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[6] >= 70 && seInt[6] < 120) {
                        sensor6.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[6] >= 120 && seInt[6] < 170) {
                        sensor6.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor6.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[7] < 20) {
                        sensor7.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[7] >= 20 && seInt[7] < 70) {
                        sensor7.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[7] >= 70 && seInt[7] < 120) {
                        sensor7.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[7] >= 120 && seInt[7] < 170) {
                        sensor7.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor7.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[8] < 20) {
                        sensor8.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[8] >= 20 && seInt[8] < 70) {
                        sensor8.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[8] >= 70 && seInt[8] < 120) {
                        sensor8.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[8] >= 120 && seInt[8] < 170) {
                        sensor8.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor8.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[9] < 20) {
                        sensor9.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[9] >= 20 && seInt[9] < 70) {
                        sensor9.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[9] >= 70 && seInt[9] < 120) {
                        sensor9.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[9] >= 120 && seInt[9] < 170) {
                        sensor9.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor9.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[10] < 20) {
                        sensor10.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[10] >= 20 && seInt[10] < 70) {
                        sensor10.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[10] >= 70 && seInt[10] < 120) {
                        sensor10.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[10] >= 120 && seInt[10] < 170) {
                        sensor10.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor10.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[11] < 20) {
                        sensor11.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[11] >= 20 && seInt[11] < 70) {
                        sensor11.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[11] >= 70 && seInt[11] < 120) {
                        sensor11.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[11] >= 120 && seInt[11] < 170) {
                        sensor11.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor11.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[12] < 20) {
                        sensor12.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[12] >= 20 && seInt[12] < 70) {
                        sensor12.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[12] >= 70 && seInt[12] < 120) {
                        sensor12.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[12] >= 120 && seInt[12] < 170) {
                        sensor12.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor12.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[13] < 20) {
                        sensor13.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[13] >= 20 && seInt[13] < 70) {
                        sensor13.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[13] >= 70 && seInt[13] < 120) {
                        sensor13.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[13] >= 120 && seInt[13] < 170) {
                        sensor13.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor13.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[14] < 20) {
                        sensor14.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[14] >= 20 && seInt[14] < 70) {
                        sensor14.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[14] >= 70 && seInt[14] < 120) {
                        sensor14.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[14] >= 120 && seInt[14] < 170) {
                        sensor14.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor14.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[15] < 20) {
                        sensor15.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[15] >= 20 && seInt[15] < 70) {
                        sensor15.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[15] >= 70 && seInt[15] < 120) {
                        sensor15.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[15] >= 120 && seInt[15] < 170) {
                        sensor15.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor15.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[16] < 20) {
                        sensor16.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[16] >= 20 && seInt[16] < 70) {
                        sensor16.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[16] >= 70 && seInt[16] < 120) {
                        sensor16.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[16] >= 120 && seInt[16] < 170) {
                        sensor16.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor16.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[17] < 20) {
                        sensor17.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[17] >= 20 && seInt[17] < 70) {
                        sensor17.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[17] >= 70 && seInt[17] < 120) {
                        sensor17.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[17] >= 120 && seInt[17] < 170) {
                        sensor17.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor17.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[18] < 20) {
                        sensor18.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[18] >= 20 && seInt[18] < 70) {
                        sensor18.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[18] >= 70 && seInt[18] < 120) {
                        sensor18.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[18] >= 120 && seInt[18] < 170) {
                        sensor18.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor18.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[19] < 20) {
                        sensor19.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[19] >= 20 && seInt[19] < 70) {
                        sensor19.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[19] >= 70 && seInt[19] < 120) {
                        sensor19.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[19] >= 120 && seInt[19] < 170) {
                        sensor19.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor19.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[20] < 20) {
                        sensor20.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[20] >= 20 && seInt[20] < 70) {
                        sensor20.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[20] >= 70 && seInt[20] < 120) {
                        sensor20.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[20] >= 120 && seInt[20] < 170) {
                        sensor20.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor20.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[21] < 20) {
                        sensor21.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[21] >= 20 && seInt[21] < 70) {
                        sensor21.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[21] >= 70 && seInt[21] < 120) {
                        sensor21.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[21] >= 120 && seInt[21] < 170) {
                        sensor21.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor21.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[22] < 20) {
                        sensor22.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[22] >= 20 && seInt[22] < 70) {
                        sensor22.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[22] >= 70 && seInt[22] < 120) {
                        sensor22.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[22] >= 120 && seInt[22] < 170) {
                        sensor22.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor22.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[23] < 20) {
                        sensor23.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[23] >= 20 && seInt[23] < 70) {
                        sensor23.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[23] >= 70 && seInt[23] < 120) {
                        sensor23.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[23] >= 120 && seInt[23] < 170) {
                        sensor23.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor23.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[24] < 20) {
                        sensor24.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[24] >= 20 && seInt[24] < 70) {
                        sensor24.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[24] >= 70 && seInt[24] < 120) {
                        sensor24.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[24] >= 120 && seInt[24] < 170) {
                        sensor24.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor24.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[25] < 20) {
                        sensor25.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[25] >= 20 && seInt[25] < 70) {
                        sensor25.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[25] >= 70 && seInt[25] < 120) {
                        sensor25.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[25] >= 120 && seInt[25] < 170) {
                        sensor25.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor25.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[26] < 20) {
                        sensor26.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[26] >= 20 && seInt[26] < 70) {
                        sensor26.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[26] >= 70 && seInt[26] < 120) {
                        sensor26.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[26] >= 120 && seInt[26] < 170) {
                        sensor26.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor26.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[27] < 20) {
                        sensor27.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[27] >= 20 && seInt[27] < 70) {
                        sensor27.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[27] >= 70 && seInt[27] < 120) {
                        sensor27.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[27] >= 120 && seInt[27] < 170) {
                        sensor27.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor27.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[28] < 20) {
                        sensor28.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[28] >= 20 && seInt[28] < 70) {
                        sensor28.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[28] >= 70 && seInt[28] < 120) {
                        sensor28.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[28] >= 120 && seInt[28] < 170) {
                        sensor28.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor28.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[29] < 20) {
                        sensor29.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[29] >= 20 && seInt[29] < 70) {
                        sensor29.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[29] >= 70 && seInt[29] < 120) {
                        sensor29.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[29] >= 120 && seInt[29] < 170) {
                        sensor29.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor29.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    if (seInt[30] < 20) {
                        sensor30.setCardBackgroundColor(Color.parseColor("#3F0099"));
                    } else if (seInt[30] >= 20 && seInt[30] < 70) {
                        sensor30.setCardBackgroundColor(Color.parseColor("#660058"));
                    } else if (seInt[30] >= 70 && seInt[30] < 120) {
                        sensor30.setCardBackgroundColor(Color.parseColor("#870073"));
                    } else if (seInt[30] >= 120 && seInt[30] < 170) {
                        sensor30.setCardBackgroundColor(Color.parseColor("#99004C"));
                    } else {
                        sensor30.setCardBackgroundColor(Color.parseColor("#AA1212"));
                    }

                    //set image_posture

                    if (seInt[1] > 70 && seInt[6] > 100 && seInt[10] > 70 && seInt[20] < 30 && seInt[24] < 30 && seInt[30] < 30){
                        image_posture.setImageResource(R.drawable.left_posture);
                        image_posture.setBorderColor(getColor(R.color.red));
                        Toast.makeText(getApplicationContext(),"자세가 올바르지 않습니다!",Toast.LENGTH_SHORT).show();
                    } else if (seInt[30] > 70 && seInt[24] > 100 && seInt[20] > 70 && seInt[1] < 30 && seInt[6] < 30 && seInt[10] < 30){
                        image_posture.setImageResource(R.drawable.right_posture);
                        image_posture.setBorderColor(getColor(R.color.red));
                        Toast.makeText(getApplicationContext(),"자세가 올바르지 않습니다!",Toast.LENGTH_SHORT).show();
                    } else if (seInt[12] > 100 && seInt[18] > 100 && seInt[4] < 30 && seInt[26] < 30){
                        image_posture.setImageResource(R.drawable.front_posture);
                        image_posture.setBorderColor(getColor(R.color.red));
                        Toast.makeText(getApplicationContext(),"자세가 올바르지 않습니다!",Toast.LENGTH_SHORT).show();
                    } else if (seInt[4] > 100 && seInt[26] > 100 && seInt[12] < 30 && seInt[18] < 30){
                        image_posture.setImageResource(R.drawable.back_posture);
                        image_posture.setBorderColor(getColor(R.color.red));
                        Toast.makeText(getApplicationContext(),"자세가 올바르지 않습니다!",Toast.LENGTH_SHORT).show();
                    } else {
                        image_posture.setImageResource(R.drawable.nice_posture);
                        image_posture.setBorderColor(getColor(R.color.green));
                    }
                }
            }
        };
    };


    void bluetoothOn() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }

    void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();

                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    void connectSelectedDevice(String selectedDeviceName) {
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }

    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}