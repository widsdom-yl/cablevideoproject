package dczh.Bean;

import java.io.Serializable;

public class DevBean implements Serializable {


    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getNme() {
        return nme;
    }

    public void setNme(String nme) {
        this.nme = nme;
    }

    private String dev;
    private String nme;
}
