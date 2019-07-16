package dczh.Bean;

import java.io.Serializable;

public class DevBean implements Serializable {
    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public DevBean(String devCode, String devName) {
        this.devCode = devCode;
        this.devName = devName;
    }

    private String devCode;
    private String devName;
}
