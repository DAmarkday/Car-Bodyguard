package com.example.car.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.car.R;

import java.net.URISyntaxException;

public class ThirdFramgent extends Fragment {

    View mRoot = null;
    EditText edittext;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_third, container, false);
            initView();
        }
        return mRoot;
    }

    private void initView() {
        edittext = mRoot.findViewById(R.id.des);
        btn = mRoot.findViewById(R.id.change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String des = edittext.getText().toString();
                String apkstr = "com.autonavi.minimap" ;
                if (checkApkExist(getContext(),apkstr)){
                    if (!des.isEmpty() && des != null  ){
                        Intent intent = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=高德地图&lat="+ "&dev=0"));
                        intent.setPackage("com.autonavi.minimap");
                        intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords="+des));
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(getContext(), "请安装高德地图", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
