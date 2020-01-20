package dczh.Adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import dczh.Bean.DevBean;
import dczh.MyApplication;
import dczh.cablevideoproject.R;

public class DevAdapter extends BaseAdapter<DevBean>  {

    public DevAdapter(List<DevBean> list)
    {
        super(R.layout.list_dev, list);
    }

    protected void convert(final BaseHolder holder, DevBean dev, final int position)
    {
        super.convert(holder, dev, position);

        TextView tx_dev_name = holder.getView(R.id.tx_dev_name);
        tx_dev_name.setText(dev.getNme());
        TextView tx_dev_alarm= holder.getView(R.id.tx_dev_alarm);
        if (dev.getAlm_cnt()>0){
            tx_dev_alarm.setVisibility(View.VISIBLE);
            String alm_cnt = MyApplication.getInstance().getString(R.string.string_alm_count);
            String salm_cnt = String.format(alm_cnt,""+dev.getAlm_cnt()) ;
            tx_dev_alarm.setText(salm_cnt);
            tx_dev_alarm.setTextColor(Color.rgb(255,0,0));
        }
        else{
            tx_dev_alarm.setText("历史报警");
            tx_dev_alarm.setTextColor(Color.rgb(0,0,0));
        }

        tx_dev_alarm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                int n = holder.getLayoutPosition();

                if (mAlarmClickListener != null)
                {
                    mAlarmClickListener.clickListener(n);
                }
            }
        });


    }
    public void setmAlarmClickListener(AlarmClickListener listener)
    {
        mAlarmClickListener = listener;
    }


    public interface AlarmClickListener
    {


        void clickListener(int position);
    }

    AlarmClickListener mAlarmClickListener;



}

