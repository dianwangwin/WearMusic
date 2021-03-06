package cn.wearbbs.music.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import cn.wearbbs.music.R;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.Map;

import cn.wearbbs.music.api.CommentApi;
import cn.wearbbs.music.util.UserInfoUtil;

public class ReplyActivity extends SlideBackActivity {
    String id;
    String cookie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (!AppCenter.isConfigured()) {
            AppCenter.start(getApplication(), "9250a12d-0fa9-4292-99fc-9d09dcc32012", Analytics.class, Crashes.class);
        }
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }
    public void send(View view) throws InterruptedException {
        EditText editText = findViewById(R.id.et_reply);
        cookie = UserInfoUtil.getUserInfo(this,"cookie");
        Map maps = new CommentApi().reply(id,editText.getText().toString(),cookie);
        if(maps.get("code").toString().equals("200")){
            Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,maps.get("msg").toString(),Toast.LENGTH_SHORT).show();
        }
    }
}