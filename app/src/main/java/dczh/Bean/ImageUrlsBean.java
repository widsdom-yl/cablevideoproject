package dczh.Bean;

import java.util.Collections;
import java.util.List;

public class ImageUrlsBean {
    private List<String> downloadUrls;
    public void setDownloadUrls(List<String> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }
    public List<String> getDownloadUrls() {
        return downloadUrls;
    }
    public List<String> getReverseDownloadUrls() {
        Collections.reverse(downloadUrls);
        return downloadUrls;
    }

}
