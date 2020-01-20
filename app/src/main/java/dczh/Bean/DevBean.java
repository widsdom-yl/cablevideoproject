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

    public int getAlm_cnt() {
        return alm_cnt;
    }

    public void setAlm_cnt(int alm_cnt) {
        this.alm_cnt = alm_cnt;
    }

    private int alm_cnt;
}
