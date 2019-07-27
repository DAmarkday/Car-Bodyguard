package com.example.car.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.car.MainActivity;
import com.example.car.R;

public class SecondFragment extends Fragment {

    View mRoot = null;
    WebView webview = null;
    Button btn_back = null;
//    ProgressBar progressBar = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_second, container, false);
            initView();
        }
        return mRoot;
    }

    private void initView() {
        webview = mRoot.findViewById(R.id.webview);
        btn_back = mRoot.findViewById(R.id.btn_back);
//        progressBar= mRoot.findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//版本问题，防止安卓解析https网址时无法解析http的网络图片，解决图片不显示
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoBack()){
                    webview.goBack();
                }else {
                    Toast.makeText(getContext(), "已返回最上层页面", Toast.LENGTH_SHORT).show();
                }

            }
        });
        webview.getSettings().setBlockNetworkImage(false);
        webview.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法,防止利用系统自带的浏览器打开页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });



//        进度条设置
//        webview.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                // TODO 自动生成的方法存根
//
//                if(newProgress==100){
//                    progressBar.setVisibility(View.GONE);
//                }
//                else{
//                    progressBar.setVisibility(View.VISIBLE);
//                    progressBar.setProgress(newProgress);
//                }
//
//            }});


        webview.loadUrl("https://m.gasgoo.com/");//加载网址
    }

}
