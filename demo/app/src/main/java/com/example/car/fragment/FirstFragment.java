package com.example.car.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car.CarApplication;
import com.example.car.R;
import com.example.car.activity.MainActivity;
import com.example.car.activity.SettingActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class FirstFragment extends Fragment implements View.OnClickListener {

    View mRoot = null;
    private ImageButton setting;
    private boolean socketStatus = false;
    private Socket socket = null;
    String Cip;
    private InputStream inputStream = null;
    public byte[] line;
    private OutputStream outputStream = null;
    private Button connect_btn;
    private Button park_btn;
    private Button gps_btn;
    private String data;
    int x=0;
    private boolean aBoolean = true;  //默认未连接
    private boolean parkBoolean = true; //默认未刹车
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_first, container, false);
            initView();
        }
        return mRoot;
    }

    private void initView() {
        setting = mRoot.findViewById(R.id.title_left);
        connect_btn = mRoot.findViewById(R.id.start_btn);
        park_btn = mRoot.findViewById(R.id.use_btn);
        gps_btn = mRoot.findViewById(R.id.request_gps);
        textView = mRoot.findViewById(R.id.gps_get);
        setting.setOnClickListener(this);
        connect_btn.setOnClickListener(this);
        park_btn.setOnClickListener(this);
       gps_btn.setOnClickListener(this);
        park_btn.setEnabled(false);
        gps_btn.setEnabled(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.start_btn:
                if(aBoolean)
                    Connect();
                else
                    reConnect();
                break;
            case R.id.use_btn:
                if (parkBoolean)
                    send("suo");
                else
                    send("no");//解除刹车
                break;
            case R.id.request_gps:
                send("T");
                getGPS();
                break;
            default:
                break;
        }
    }







    public void Connect() {
        Cip = MainActivity.returnIp();
        if (Cip.equals("none") == true) {
            Toast.makeText(getContext(), "请重新设置ip地址", Toast.LENGTH_SHORT).show();
        } else {
                //String E = port.getText().toString();
                // final int  portE = Integer.parseInt(E);
//        ip="172.24.190.245";
                Toast.makeText(getContext(), "正在连接，请稍后。。。", Toast.LENGTH_SHORT).show();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        if (!socketStatus) {

                            try {
                                socket = new Socket(Cip, 80);
                                if (socket == null) {
                                } else {
                                    socketStatus = true;
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity().getApplicationContext(), "已成功连接", Toast.LENGTH_SHORT).show();
                                                aBoolean = false;
                                                connect_btn.setText("断开连接");
                                                park_btn.setEnabled(true);
                                                gps_btn.setEnabled(true);

                                            }
                                        });
                                    }
                                }).start();


                            } catch (IOException e) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity().getApplicationContext(), "连接错误，请重新连接", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).start();

                                e.printStackTrace();
                            }

                        }

                    }
                };
                thread.start();


        }
    }

    public void send(String order){

        try{
            outputStream = socket.getOutputStream();}
        catch(Exception e){
            e.printStackTrace();
        }
        data = order;
        if (data.equals("suo")){
                    x=1;//制动
        }
        else if(data.equals("no")){
                    x=2;//解除制动
        }
        if(data == null){
            Toast.makeText(getContext(),"please input Sending Data",Toast.LENGTH_SHORT).show();
        }else {
            //在后面加上 '\0' ,是为了在服务端方便我们去解析；
            data = data + '\0';
        }

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                if(socketStatus){
                    try {
                        outputStream.write(data.getBytes());
                        // outputStream.flush();//清空流，关闭流
                        outputStream.close();



                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(),"执行成功",Toast.LENGTH_SHORT).show();
                                        if (x==1){
                                            park_btn.setText("制动");
                                        }
                                        else if (x==2){
                                            park_btn.setText("解除制动");
                                        }



                                    }
                                });
                            }
                        }).start();
                    } catch (IOException e) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(),"执行失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();
                        e.printStackTrace();
                    }

                }

            }
        };
        thread.start();

    }

    /*当客户端界面返回时，关闭相应的socket资源*/

    //    public void onBackPressed() {
//        super.onBackPressed();
    /*关闭相应的资源*/
    public void reConnect(){
        try {
            // outputStream.close();
            socket.close();
            Toast.makeText(getContext(),"连接已断开",Toast.LENGTH_SHORT).show();
            park_btn.setEnabled(false);
            gps_btn.setEnabled(false);
            connect_btn.setText("请求连接");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    public void getGPS(){
        textView.setText("正在获取位置......");
        try{
            inputStream = socket.getInputStream();}
        catch(Exception e){
            e.printStackTrace();
        }
        line = new byte[1024];
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while(inputStream.read(line)!=-1){
                        Looper.prepare();//子线程加载toast
                        textView.setText(Arrays.toString(line));      //显示返回的位置
                        //将读取到指令a的2条返回
                        Looper.loop();
                    }
                    inputStream.close();//清空流，关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        };
        thread.start();


    }


}
