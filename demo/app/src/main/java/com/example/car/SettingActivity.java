package com.example.car;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener {

    private CarApplication app;

    private ImageButton back ;
    private TextView localIP ;
    private EditText serverIP ;
    private Button save ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        app = (CarApplication) getApplication();

        initView();
        initListener();

        if (app.getLocalIp() != null)
            localIP.setText(app.getLocalIp());

        serverIP.setText(app.getServerIp());
        serverIP.setSelection(app.getServerIp().length());

    }

    private void initView() {
        back = findViewById(R.id.title_left);
        back.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        localIP = findViewById(R.id.localIp);
        serverIP = findViewById(R.id.serverIP);
        save = findViewById(R.id.save);
    }

    private void initListener() {
        back.setOnClickListener(this);
        serverIP.addTextChangedListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String serip = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                String toaststr = "服务器IP设置错误!!!";
                String serip = serverIP.getText().toString();
                Log.d("Setting:",serip);
                String[] s = serip.split("\\.");
                Log.d("SettingSize:",s.length+"");
                if (s.length == 4) {
                    Toast.makeText(this,"4",Toast.LENGTH_LONG);
                    for (int i = 0; i < 4; i++) {
                        int a = Integer.parseInt(s[i]);
                        if (a < 0 && a > 255) {
                            break;
                        }
                    }
                    app.setServerIp(serip);
                    toaststr = "保存成功!!!";
                }
                Toast.makeText(this, toaststr, Toast.LENGTH_SHORT).show();
                break;
            case R.id.title_left:
                finish();
                break;
        }
    }
}
