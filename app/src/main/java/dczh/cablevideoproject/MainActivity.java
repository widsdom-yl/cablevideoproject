package dczh.cablevideoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dczh.Adapter.BaseAdapter;
import dczh.Adapter.CableImageAdapter;
import dczh.Bean.BodyBean;
import dczh.Bean.DevBean;
import dczh.Bean.HeadBean;
import dczh.Bean.ImageUrlsBean;
import dczh.Bean.RequestBean;
import dczh.Bean.ResponseBean;
import dczh.Util.GsonUtil;
import dczh.Util.UTCUtil;
import dczh.View.LoadingDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements BaseAdapter.OnItemClickListener {
    RecyclerView imageRecyclerView;
    CableImageAdapter adapter;
    DevBean dev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setCustomTitle(getString(R.string.string_cable_video),false);


        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            dev = (DevBean) bundle.getSerializable("param");
            getSupportActionBar().setTitle(dev.getDevName());
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);
        // String ret = sha256("POST/vdt-web/serv?systemCode =IOTDCZHCW&timestamp=1543497503&nonec=12346","DCZH");

        imageRecyclerView = findViewById(R.id.imageListView);

        imageRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CableImageAdapter(new ArrayList<String>());
        adapter.setOnItemClickListener(this);
        imageRecyclerView.setAdapter(adapter);
        requestImage();
//        new Handler().postDelayed(new Runnable()
//        {
//            public void run()
//            {
//                requestImage();
//            }
//        }, 6 * 1000);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.action_refresh:
                requestImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void requestImage(){
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.dialogShow();
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        RequestBean requestBean = new RequestBean();
        BodyBean body = new BodyBean();
        HeadBean head = new HeadBean();
        head.setMethod("GENERAL_GET_FILES_DOWNLOAD_BY_DEV");
        head.setSystemCode("zz");
        head.setNonce(head.generateRamdomNumber());
        head.setTimestamp(UTCUtil.getUTC());
       // head.setTimestamp(System.currentTimeMillis()-16*3600);
        head.fillSignature();
        body.setDeviceCode(dev.getDevCode());

        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateEnd = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateEnd);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR_OF_DAY, -48);// before 48 hour
        //body.setEndTime(format.format(dateEnd));
        body.setStartTime(format.format(cal.getTime()));
        cal.add(Calendar.HOUR_OF_DAY, 62);// before 48 hour
        body.setEndTime(format.format(cal.getTime()));
        requestBean.setRequestBody(body);
        requestBean.setRequestHead(head);

        //使用Gson将对象转换为json字符串
        Gson gson = new Gson();

        String json = gson.toJson(requestBean);
        Log.e("tag",json);
        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        Request request = new Request.Builder()
                .url("http://58.240.19.74:12003/vdt-web/api/file/getDownloadUrlListByDevice")//请求的url
                .post(requestBody)
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("连接失败");
                loadingDialog.dismiss();

            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();
               System.out.println(responseStr);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //
                        loadingDialog.dismiss();
                            String res =responseStr;
                            res = res.replaceAll("\\u003d","");
                            res = res.replaceAll("\\u0026","&");
                            final ResponseBean responseBean  = GsonUtil.parseJsonWithGson(res,ResponseBean.class);
                            ImageUrlsBean responseBody = responseBean.getResponseBody();

                            //ImageUrlsBean imageUrlsBean = GsonUtil.parseJsonWithGson(responseBody.toString(),ImageUrlsBean.class);
                            Log.e("tag","imageUrlsBean image count is "+responseBody.getDownloadUrls().size());
                            adapter.resetMList(responseBody.getReverseDownloadUrls());
                            adapter.notifyDataSetChanged();

                    }
                });


            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }
    LoadingDialog loadingDialog;
}
