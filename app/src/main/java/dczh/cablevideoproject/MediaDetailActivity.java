package dczh.cablevideoproject;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.github.chrisbanes.photoview.PhotoView;

import dczh.GlideApp;
import dczh.MyApplication;

public class MediaDetailActivity extends BaseAppCompatActivity {

    String imagePath;
    PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);

            setCustomTitle("", true);
        }
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
        {
            imagePath = (String) bundle.getString("param1","");
            Log.e("tag",imagePath);

        }
        initView();
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

    void initView()
    {
        imageView = findViewById(R.id.detail_image);

        GlideApp.with(MyApplication.getInstance()).asBitmap()
                .load(imagePath)
                .fitCenter()
                //.placeholder(R.drawable.imagethumb_cn)//zhbzhb
                .placeholder(R.drawable.imagethumb)//zhbzhb
                .into(imageView);


    }
}
