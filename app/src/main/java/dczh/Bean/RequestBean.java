package dczh.Bean;

public class RequestBean {
    HeadBean requestHead;

    public HeadBean getRequestHead() {
        return requestHead;
    }

    public void setRequestHead(HeadBean requestHead) {
        this.requestHead = requestHead;
    }

    public BodyBean getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(BodyBean requestBody) {
        this.requestBody = requestBody;
    }

    BodyBean requestBody;


}
