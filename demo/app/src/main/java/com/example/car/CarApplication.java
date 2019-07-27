package com.example.car;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class CarApplication extends Application {

    private String localIp ;
    private String serverIp ;
    private SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();
        localIp = localIP(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("serverip", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setServerIp(sharedPreferences.getString("serverip","192.168.1.1"));
        Log.d("Setting:", "onCreate: "+serverIp);
    }

    public String getLocalIp() {
        return localIp;
    }

    private String localIP(Context context){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address))
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
        editor.putString("serverip",serverIp);
        editor.commit();
    }



}
