package dczh.cablevideoproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import dczh.Bean.ResponseBean;
import dczh.Bean.UserBean;
import dczh.Manager.AccountManager;
import dczh.Util.Config;
import dczh.Util.GsonUtil;
import dczh.View.LoadingDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//api  接口
//https://www.showdoc.cc/317380933238324?page_id=3066526587074786

public class LoginActivity extends BaseAppCompatActivity {

    private final static String tag = "LoginActivity";
    LoadingDialog lod;
    String UserName = null;
    String Password = null;
    boolean IsAutoLogin = false;
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    AppCompatCheckBox checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            setCustomTitle(getString(R.string.action_sign_in), false);
        }


        IsAutoLogin = AccountManager.getInstance().getIsRemeberAccount();



        mUserView = (AutoCompleteTextView) findViewById(R.id.user_textview);
        mPasswordView = (EditText) findViewById(R.id.editText_password);

        checkbox = findViewById(R.id.checkbox);
        checkbox.setChecked(IsAutoLogin);


        if (IsAutoLogin){
            UserName = AccountManager.getInstance().getDefaultUsr();
            mUserView.setText(UserName);
            Password = AccountManager.getInstance().getDefaultPwd();
            mPasswordView.setText(Password);
        }

        Button signInButton = (Button) findViewById(R.id.login_in_button);


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                IsAutoLogin = isChecked;
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });

    }

    private void attemptLogin()
    {


        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String mUser = mUserView.getText().toString();
        String mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(mUser))
        {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mPassword))
        {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            login(mUser,mPassword);

        }
    }





    public void login(final String mUserName, final String mPassword) {
        hintKeyBoard();
        if (lod == null)
        {
            lod = new LoadingDialog(this);
        }
        lod.dialogShow();


        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("user", mUserName)
                .add("pass", mPassword)
                .add("token", AccountManager.getInstance().getDeviceToken())
                .build();

        final Request request = new Request.Builder()
                .url(Config.serverUrl+"login.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                lod.dismiss();
                Toast.makeText(LoginActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.e(tag,"res is "+res);
                final ResponseBean model  = GsonUtil.parseJsonWithGson(res,ResponseBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //
                        lod.dismiss();
                        if (model != null && model.error_code==0){
                            String body = new Gson().toJson(model.data);
                            AccountManager.getInstance().saveAccount(mUserName, mPassword, IsAutoLogin);
                            UserBean userBean = new Gson().fromJson(body,UserBean.class);
//
//                            String token = model.getData().getToken();
                            Intent intent = new Intent(LoginActivity.this, VideoListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("param",userBean);
                            intent.putExtras(bundle);

                            startActivity(intent);
                            LoginActivity.this.finish();
                            Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, getString(R.string.error_login_failed), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });




    }
    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


}
