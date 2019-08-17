package dczh.cablevideoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import dczh.Adapter.BaseAdapter;
import dczh.Adapter.DevAdapter;
import dczh.Bean.DevBean;

public class VideoListActivity extends AppCompatActivity implements BaseAdapter.OnItemClickListener, DevAdapter.AlarmClickListener {
    RecyclerView imageRecyclerView;
    DevAdapter adapter ;
    List<DevBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.string_cable_video);
        setContentView(R.layout.activity_video_list);
        imageRecyclerView = findViewById(R.id.list_dev);
        imageRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
    }
    void initData(){
        list = new ArrayList<>();
        DevBean bean1 = new DevBean("BRAINSZZ201900001","110kV新城7HB线至城东变电缆1号-001#监控点");
        DevBean bean2 = new DevBean("BRAINSZZ201900002","设备2");
        DevBean bean3 = new DevBean("BRAINSZZ201900003","设备3");
        DevBean bean4 = new DevBean("BRAINSZZ201900004","设备4");
        DevBean bean5 = new DevBean("BRAINSZZ201900005","设备5");
        DevBean bean6 = new DevBean("BRAINSZZ201900006","设备6");
        DevBean bean7 = new DevBean("BRAINSZZ201900007","设备7");
        DevBean bean8 = new DevBean("BRAINSZZ201900008","110kV新城7HB线至城东变电缆1号-002#监控点");
        DevBean bean9 = new DevBean("BRAINSZZ201900009","设备9");
        DevBean bean10 = new DevBean("BRAINSZZ201900010","设备10");

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);
        list.add(bean9);
        list.add(bean10);
        adapter = new DevAdapter(list);
        imageRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setmAlarmClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        DevBean dev = list.get(position);
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
        DevBean dev = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("param1",dev);
        Intent intent = new Intent(this,AlarmActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}
