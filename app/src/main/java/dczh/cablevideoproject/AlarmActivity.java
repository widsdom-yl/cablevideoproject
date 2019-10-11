package dczh.cablevideoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.List;

import dczh.Adapter.AlarmImageAdapter;
import dczh.Adapter.BaseAdapter;
import dczh.Bean.DevBean;
import dczh.Bean.ImageBean;
import dczh.Util.Config;
import dczh.Util.GsonUtil;
import dczh.View.LoadingDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AlarmActivity extends BaseAppCompatActivity implements BaseAdapter.OnItemClickListener {
    DevBean dev;
    RecyclerView alarmRecyclerView;
    AlarmImageAdapter adapter;
    List<ImageBean> alarmList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Bundle bundle = this.getIntent().getExtras();
        alarmRecyclerView = findViewById(R.id.alarmListView);
        alarmRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //http://106.14.10.237/svr/alarm_list.php?dev=BRAINSZZ201900001
        if(bundle != null){
            dev = (DevBean) bundle.getSerializable("param1");
            requestAlarmImageArray();
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            // setCustomTitle(devName, true);
            actionBar.setTitle(dev.getNme());
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish(); // back button
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void requestAlarmImageArray() {

        if (lod == null)
        {
            lod = new LoadingDialog(this);
        }
        lod.dialogShow();


        OkHttpClient client = new OkHttpClient();




        MediaType mediaType = MediaType.parse("application/data");
        final Request request = new Request.Builder()
                .url(Config.serverUrl+"alm_list.php?dev="+dev.getDev())
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
                            List<ImageBean> lists = GsonUtil.parseJsonArrayWithGson(res, ImageBean[].class);
                            Log.e(tag,"alarm list count is :"+lists.size());
                            alarmList = lists;
                            if(lists.size()>0){
                                if(adapter == null){
                                    adapter = new AlarmImageAdapter(alarmList);
                                    alarmRecyclerView.setAdapter(adapter);
                                    adapter.setOnItemClickListener(AlarmActivity.this);
                                }
                                else{
                                    adapter.resetMList(alarmList);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                    }
                });
            }
        });




    }
    final  String tag = "AlarmActivity";
    LoadingDialog lod;

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this,MediaDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("param1",alarmList.get(position).getUrl());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
