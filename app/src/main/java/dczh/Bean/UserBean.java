package dczh.Bean;

import java.io.Serializable;

public class UserBean implements Serializable {
    public int uid;
    public String dev;
    public String[] getDeviceListCode(){
        if (dev.length()>0){
            return dev.split(",");
        }
        else{
            return null;
        }
    }
}
