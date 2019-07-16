package dczh.Adapter;

import android.widget.TextView;

import java.util.List;

import dczh.Bean.DevBean;
import dczh.cablevideoproject.R;

public class DevAdapter extends BaseAdapter<DevBean>
{

    public DevAdapter(List<DevBean> list)
    {
        super(R.layout.list_dev, list);
    }

    protected void convert(BaseHolder holder, DevBean dev, final int position)
    {
        super.convert(holder, dev, position);

        TextView tx_dev_name = holder.getView(R.id.tx_dev_name);
        tx_dev_name.setText(dev.getDevName());

    }





}

