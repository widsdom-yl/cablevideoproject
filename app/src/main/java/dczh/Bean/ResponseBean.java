package dczh.Bean;

public class ResponseBean {
    public Object getResponseHead() {
        return responseHead;
    }

    public void setResponseHead(Object responseHead) {
        this.responseHead = responseHead;
    }



    Object responseHead;

    public ImageUrlsBean getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(ImageUrlsBean responseBody) {
        this.responseBody = responseBody;
    }

    ImageUrlsBean responseBody;
}
