package dczh.Adapter;



        import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dczh.Bean.AlarmBean;
import dczh.GlideApp;
import dczh.MyApplication;
import dczh.cablevideoproject.R;

public class AlarmImageAdapter extends BaseAdapter<AlarmBean>
{

    public AlarmImageAdapter(List<AlarmBean> list)
    {
        super(R.layout.adatper_image, list);
    }

    protected void convert(BaseHolder holder, AlarmBean alarmBean, final int position)
    {
        super.convert(holder, alarmBean, position);
        ImageView imageView = holder.getView(R.id.imageView);
        TextView textView = holder.getView(R.id.tx_date);

        textView.setText(alarmBean.getNme());
        GlideApp.with(MyApplication.getInstance()).asBitmap()
                .load(alarmBean.getUrl())
                .centerCrop()
                //.placeholder(R.drawable.imagethumb_cn)//zhbzhb
                .placeholder(R.drawable.imagethumb)//zhbzhb
                .into(imageView);

    }





}



