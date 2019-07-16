package dczh.Bean;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static dczh.Util.SignUtil.encryptSHA;


public class HeadBean {
    String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }




    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public void fillSignature(){
        if (systemCode.length()==0 || timestamp == 0 || nonce == 0){
            return;
        }
        //vdt-web/api
//        String encodeStr = "POST/vdt-web/api/file/getDownloadUrlListByDevice?systemCode="+systemCode+"&timestamp="+timestamp+"&nonec="+nonce;
//        System.out.println(encodeStr);
//        Log.e("tag",encodeStr);
//        try {
//            String ret = sha256(encodeStr,"zz123456");
//            setSignature(ret);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        //public static String encryptSHA(String method, String resource, String systemCode, long utcTime, int nonce,String secretKey) throws NoSuchAlgorithmException {
        //
//String signature = encryptSHA("POST", new URL(httpUrl).getPath(), timestamp, nonce);
        try {
            String ret1 = encryptSHA("POST","/vdt-web/api/file/getDownloadUrlListByDevice",systemCode,timestamp,nonce,"zz123456");
            setSignature(ret1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public int generateRamdomNumber(){

        Random rand = new Random();
      //  int randNumber =rand.nextInt(MAX - MIN + 1) + MIN; // randNumber 将被赋值为一个 MIN 和 MAX 范围内的随机数
        return  rand.nextInt(10000) ;
    }
    String systemCode;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    Long timestamp;

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    int nonce;
    String signature;
}
