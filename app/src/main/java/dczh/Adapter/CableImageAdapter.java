package dczh.Adapter;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import dczh.GlideApp;
import dczh.MyApplication;
import dczh.Util.TimeUtil;
import dczh.cablevideoproject.R;

public class CableImageAdapter extends BaseAdapter<String>
{

    public CableImageAdapter(List<String> list)
    {
        super(R.layout.adatper_image, list);
    }

    protected void convert(BaseHolder holder, String path, final int position)
    {
        super.convert(holder, path, position);
        ImageView imageView = holder.getView(R.id.imageView);
        TextView textView = holder.getView(R.id.tx_date);
        int index =  path.indexOf(".jpg");

        String dateStr = path.substring(index-17,index-3);
        //2019-07-17 04:15:30
        Date date =  TimeUtil.parse(dateStr);
        String dateFormatStr = TimeUtil.formatDate(date);
        textView.setText(dateFormatStr);
        GlideApp.with(MyApplication.getInstance()).asBitmap()
                .load(path)
                .centerCrop()
                //.placeholder(R.drawable.imagethumb_cn)//zhbzhb
                .placeholder(R.drawable.imagethumb)//zhbzhb
                .into(imageView);

    }





}

