package dczh.Adapter;


import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import dczh.Bean.ImageBean;
import dczh.GlideApp;
import dczh.MyApplication;
import dczh.Util.TimeUtil;
import dczh.cablevideoproject.R;

public class AlarmImageAdapter extends BaseAdapter<ImageBean>
{

    public AlarmImageAdapter(List<ImageBean> list)
    {
        super(R.layout.adatper_image, list);
    }

    protected void convert(BaseHolder holder, ImageBean alarmBean, final int position)
    {
        super.convert(holder, alarmBean, position);
        ImageView imageView = holder.getView(R.id.imageView);
        TextView textView = holder.getView(R.id.tx_date);

        Date date =  TimeUtil.parse(alarmBean.getNme());
        String dateFormatStr = TimeUtil.formatDate(date);
        textView.setText(dateFormatStr);


        //textView.setText(alarmBean.getNme());
        GlideApp.with(MyApplication.getInstance()).asBitmap()
                .load(alarmBean.getUrl())
                .centerCrop()
                //.placeholder(R.drawable.imagethumb_cn)//zhbzhb
                .placeholder(R.drawable.imagethumb)//zhbzhb
                .into(imageView);

    }





}



