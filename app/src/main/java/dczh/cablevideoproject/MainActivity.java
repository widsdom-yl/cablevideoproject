package dczh.cablevideoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
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
/*通道设备图片列表*/
public class MainActivity extends AppCompatActivity implements BaseAdapter.OnItemClickListener {
    RecyclerView imageRecyclerView;
    AlarmImageAdapter adapter;
    DevBean dev;
    List<ImageBean> imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setCustomTitle(getString(R.string.string_cable_video),false);


        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            dev = (DevBean) bundle.getSerializable("param");
            getSupportActionBar().setTitle(dev.getNme());
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);


        imageRecyclerView = findViewById(R.id.imageListView);

        imageRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestImage();




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

    public void requestImage() {

        if (lod == null)
        {
            lod = new LoadingDialog(this);
        }
        lod.dialogShow();


        OkHttpClient client = new OkHttpClient();




        MediaType mediaType = MediaType.parse("application/data");
        final Request request = new Request.Builder()
                .url(Config.serverUrl+"pic_list.php?dev="+dev.getDev())
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
                            Log.e(tag,"list count is :"+lists.size());
                            imageList = lists;
                            if(lists.size()>0){
                                if(adapter == null){
                                    adapter = new AlarmImageAdapter(imageList);
                                    imageRecyclerView.setAdapter(adapter);
                                    adapter.setOnItemClickListener(MainActivity.this);
                                }
                                else{
                                    adapter.resetMList(imageList);
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
        Intent intent = new Intent(this,MediaDetailActivity.class);
        Bundle bundle = new Bundle();
        if (imageList.size()>0){
            bundle.putString("param1",imageList.get(position).getUrl());
        }
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onLongClick(View view, int position) {

    }
    final static String tag = "MainActivity";
    LoadingDialog lod;
}
