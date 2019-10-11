package dczh.cablevideoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dczh.Adapter.BaseAdapter;
import dczh.Adapter.DevAdapter;
import dczh.Bean.DevBean;
import dczh.Bean.UserBean;
import dczh.Util.Config;
import dczh.Util.GsonUtil;
import dczh.View.LoadingDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoListActivity extends AppCompatActivity implements BaseAdapter.OnItemClickListener, DevAdapter.AlarmClickListener {
    RecyclerView mRecyclerView;
    DevAdapter adapter ;
    UserBean userBean;
    List<DevBean> devList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.string_devlist);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            userBean = (UserBean) bundle.getSerializable("param");
           // getSupportActionBar().setTitle(dev.getDevName());
        }
        setContentView(R.layout.activity_video_list);
        mRecyclerView = findViewById(R.id.list_dev);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestDevlist();
    }

    public void requestDevlist() {

        if (lod == null)
        {
            lod = new LoadingDialog(this);
        }
        lod.dialogShow();


        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(Config.serverUrl+"dev_list.php")
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.e(tag,"res is "+res);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //
                        lod.dismiss();
                        {
                            // String body = new Gson().toJson(res);
                            List<DevBean> lists = GsonUtil.parseJsonArrayWithGson(res, DevBean[].class);
                            Log.e(tag,"list count is :"+lists.size());

                            if(lists.size()>0 && userBean.getDeviceListCode().length >0){
                                Collections.reverse(lists);
                                devList.clear();
                                Iterator<DevBean> iter = lists.iterator();
                                while (iter.hasNext()) {
                                    DevBean s = iter.next();
                                    if(userBean.dev.contains(s.getDev())){
                                        devList.add(s);
                                    }
                                }

                                if(adapter == null){
                                    adapter = new DevAdapter(devList);;
                                    mRecyclerView.setAdapter(adapter);
                                    adapter.setOnItemClickListener(VideoListActivity.this);
                                    adapter.setmAlarmClickListener(VideoListActivity.this);
                                }
                                else{
                                    adapter.resetMList(devList);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                    }
                });
            }
        });




    }


    @Override
    public void onItemClick(View view, int position) {
        DevBean dev = devList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("param",dev);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void clickListener(int position) {
        DevBean dev = devList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("param1",dev);
        Intent intent = new Intent(this,AlarmActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    final static String tag = "VideoListActivity";
    LoadingDialog lod;
}
