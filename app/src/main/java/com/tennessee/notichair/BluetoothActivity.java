package com.tennessee.notichair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {
    TextView mTvBluetoothStatus;
    Button mBtnBluetoothOn;
    Button mBtnBluetoothOff;
    Button mBtnConnect;

    ImageView sensor0, sensor1, sensor2, sensor3, sensor4, sensor5, sensor6, sensor7, sensor8, sensor9, sensor10;
    ImageView sensor11, sensor12, sensor13, sensor14, sensor15, sensor16, sensor17, sensor18, sensor19, sensor20;
    ImageView sensor21, sensor22, sensor23, sensor24, sensor25, sensor26, sensor27, sensor28, sensor29, sensor30;

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
        setContentView(R.layout.activity_bluetooth);

        mTvBluetoothStatus = findViewById(R.id.tvBluetoothStatus);
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
                        sensor0.setImageResource(R.drawable.blue);
                    } else if (seInt[0] >= 20 && seInt[0] < 150) {
                        sensor0.setImageResource(R.drawable.green);
                    } else {
                        sensor0.setImageResource(R.drawable.red);
                    }

                    if (seInt[1] < 20) {
                        sensor1.setImageResource(R.drawable.blue);
                    } else if (seInt[1] >= 20 && seInt[1] < 150) {
                        sensor1.setImageResource(R.drawable.green);
                    } else {
                        sensor1.setImageResource(R.drawable.red);
                    }

                    if (seInt[2] < 20) {
                        sensor2.setImageResource(R.drawable.blue);
                    } else if (seInt[2] >= 20 && seInt[2] < 150) {
                        sensor2.setImageResource(R.drawable.green);
                    } else {
                        sensor2.setImageResource(R.drawable.red);
                    }

                    if (seInt[3] < 20) {
                        sensor3.setImageResource(R.drawable.blue);
                    } else if (seInt[3] >= 20 && seInt[3] < 150) {
                        sensor3.setImageResource(R.drawable.green);
                    } else {
                        sensor3.setImageResource(R.drawable.red);
                    }

                    if (seInt[4] < 20) {
                        sensor4.setImageResource(R.drawable.blue);
                    } else if (seInt[4] >= 20 && seInt[4] < 150) {
                        sensor4.setImageResource(R.drawable.green);
                    } else {
                        sensor4.setImageResource(R.drawable.red);
                    }

                    if (seInt[5] < 20) {
                        sensor5.setImageResource(R.drawable.blue);
                    } else if (seInt[5] >= 20 && seInt[5] < 150) {
                        sensor5.setImageResource(R.drawable.green);
                    } else {
                        sensor5.setImageResource(R.drawable.red);
                    }

                    if (seInt[6] < 20) {
                        sensor6.setImageResource(R.drawable.blue);
                    } else if (seInt[6] >= 20 && seInt[6] < 150) {
                        sensor6.setImageResource(R.drawable.green);
                    } else {
                        sensor6.setImageResource(R.drawable.red);
                    }

                    if (seInt[7] < 20) {
                        sensor7.setImageResource(R.drawable.blue);
                    } else if (seInt[7] >= 20 && seInt[7] < 150) {
                        sensor7.setImageResource(R.drawable.green);
                    } else {
                        sensor7.setImageResource(R.drawable.red);
                    }

                    if (seInt[8] < 20) {
                        sensor8.setImageResource(R.drawable.blue);
                    } else if (seInt[8] >= 20 && seInt[8] < 150) {
                        sensor8.setImageResource(R.drawable.green);
                    } else {
                        sensor8.setImageResource(R.drawable.red);
                    }

                    if (seInt[9] < 20) {
                        sensor9.setImageResource(R.drawable.blue);
                    } else if (seInt[9] >= 20 && seInt[9] < 150) {
                        sensor9.setImageResource(R.drawable.green);
                    } else {
                        sensor9.setImageResource(R.drawable.red);
                    }

                    if (seInt[10] < 20) {
                        sensor10.setImageResource(R.drawable.blue);
                    } else if (seInt[10] >= 20 && seInt[10] < 150) {
                        sensor10.setImageResource(R.drawable.green);
                    } else {
                        sensor10.setImageResource(R.drawable.red);
                    }

                    if (seInt[11] < 20) {
                        sensor11.setImageResource(R.drawable.blue);
                    } else if (seInt[11] >= 20 && seInt[11] < 150) {
                        sensor11.setImageResource(R.drawable.green);
                    } else {
                        sensor11.setImageResource(R.drawable.red);
                    }
                    if (seInt[12] < 20) {
                        sensor12.setImageResource(R.drawable.blue);
                    } else if (seInt[12] >= 20 && seInt[12] < 150) {
                        sensor12.setImageResource(R.drawable.green);
                    } else {
                        sensor12.setImageResource(R.drawable.red);
                    }

                    if (seInt[13] < 20) {
                        sensor13.setImageResource(R.drawable.blue);
                    } else if (seInt[13] >= 20 && seInt[13] < 150) {
                        sensor13.setImageResource(R.drawable.green);
                    } else {
                        sensor13.setImageResource(R.drawable.red);
                    }

                    if (seInt[14] < 20) {
                        sensor14.setImageResource(R.drawable.blue);
                    } else if (seInt[14] >= 20 && seInt[14] < 150) {
                        sensor14.setImageResource(R.drawable.green);
                    } else {
                        sensor14.setImageResource(R.drawable.red);
                    }

                    if (seInt[15] < 20) {
                        sensor15.setImageResource(R.drawable.blue);
                    } else if (seInt[15] >= 20 && seInt[15] < 150) {
                        sensor15.setImageResource(R.drawable.green);
                    } else {
                        sensor15.setImageResource(R.drawable.red);
                    }

                    if (seInt[16] < 20) {
                        sensor16.setImageResource(R.drawable.blue);
                    } else if (seInt[16] >= 20 && seInt[16] < 150) {
                        sensor16.setImageResource(R.drawable.green);
                    } else {
                        sensor16.setImageResource(R.drawable.red);
                    }

                    if (seInt[17] < 20) {
                        sensor17.setImageResource(R.drawable.blue);
                    } else if (seInt[17] >= 20 && seInt[17] < 150) {
                        sensor17.setImageResource(R.drawable.green);
                    } else {
                        sensor17.setImageResource(R.drawable.red);
                    }

                    if (seInt[18] < 20) {
                        sensor18.setImageResource(R.drawable.blue);
                    } else if (seInt[18] >= 20 && seInt[18] < 150) {
                        sensor18.setImageResource(R.drawable.green);
                    } else {
                        sensor18.setImageResource(R.drawable.red);
                    }

                    if (seInt[19] < 20) {
                        sensor19.setImageResource(R.drawable.blue);
                    } else if (seInt[19] >= 20 && seInt[19] < 150) {
                        sensor19.setImageResource(R.drawable.green);
                    } else {
                        sensor19.setImageResource(R.drawable.red);
                    }

                    if (seInt[20] < 20) {
                        sensor20.setImageResource(R.drawable.blue);
                    } else if (seInt[20] >= 20 && seInt[20] < 150) {
                        sensor20.setImageResource(R.drawable.green);
                    } else {
                        sensor20.setImageResource(R.drawable.red);
                    }

                    if (seInt[21] < 20) {
                        sensor21.setImageResource(R.drawable.blue);
                    } else if (seInt[21] >= 20 && seInt[21] < 150) {
                        sensor21.setImageResource(R.drawable.green);
                    } else {
                        sensor21.setImageResource(R.drawable.red);
                    }

                    if (seInt[22] < 20) {
                        sensor22.setImageResource(R.drawable.blue);
                    } else if (seInt[22] >= 20 && seInt[22] < 150) {
                        sensor22.setImageResource(R.drawable.green);
                    } else {
                        sensor22.setImageResource(R.drawable.red);
                    }
                    if (seInt[23] < 20) {
                        sensor23.setImageResource(R.drawable.blue);
                    } else if (seInt[23] >= 20 && seInt[23] < 150) {
                        sensor23.setImageResource(R.drawable.green);
                    } else {
                        sensor23.setImageResource(R.drawable.red);
                    }
                    if (seInt[24] < 20) {
                        sensor24.setImageResource(R.drawable.blue);
                    } else if (seInt[24] >= 20 && seInt[24] < 150) {
                        sensor24.setImageResource(R.drawable.green);
                    } else {
                        sensor24.setImageResource(R.drawable.red);
                    }
                    if (seInt[25] < 20) {
                        sensor25.setImageResource(R.drawable.blue);
                    } else if (seInt[25] >= 20 && seInt[25] < 150) {
                        sensor25.setImageResource(R.drawable.green);
                    } else {
                        sensor25.setImageResource(R.drawable.red);
                    }
                    if (seInt[26] < 20) {
                        sensor26.setImageResource(R.drawable.blue);
                    } else if (seInt[26] >= 20 && seInt[26] < 150) {
                        sensor26.setImageResource(R.drawable.green);
                    } else {
                        sensor26.setImageResource(R.drawable.red);
                    }
                    if (seInt[27] < 20) {
                        sensor27.setImageResource(R.drawable.blue);
                    } else if (seInt[27] >= 20 && seInt[27] < 150) {
                        sensor27.setImageResource(R.drawable.green);
                    } else {
                        sensor27.setImageResource(R.drawable.red);
                    }

                    if (seInt[28] < 20) {
                        sensor28.setImageResource(R.drawable.blue);
                    } else if (seInt[28] >= 20 && seInt[28] < 150) {
                        sensor28.setImageResource(R.drawable.green);
                    } else {
                        sensor28.setImageResource(R.drawable.red);
                    }

                    if (seInt[29] < 20) {
                        sensor29.setImageResource(R.drawable.blue);
                    } else if (seInt[29] >= 20 && seInt[29] < 150) {
                        sensor29.setImageResource(R.drawable.green);
                    } else {
                        sensor29.setImageResource(R.drawable.red);
                    }

                    if (seInt[30] < 20) {
                        sensor30.setImageResource(R.drawable.blue);
                    } else if (seInt[30] >= 20 && seInt[30] < 150) {
                        sensor30.setImageResource(R.drawable.green);
                    } else {
                        sensor30.setImageResource(R.drawable.red);
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
                mTvBluetoothStatus.setText("활성화");
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
            mTvBluetoothStatus.setText("비활성화");
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
                    mTvBluetoothStatus.setText("활성화");
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                    mTvBluetoothStatus.setText("비활성화");
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