package dczh.Util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUtil {
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
    /**
     * 对输入参数进sha265处理
     *
     * @param method
     * @param resource
     * @param utcTime
     * @param nonce
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encryptSHA(String method, String resource, String systemCode, long utcTime, int nonce,String secretKey) throws NoSuchAlgorithmException {
        StringBuilder srcStr = new StringBuilder();

        srcStr.append(method);
        srcStr.append(resource);
        srcStr.append("?systemCode=");
        srcStr.append(systemCode);
        srcStr.append("&timestamp=");
        srcStr.append(utcTime);
        srcStr.append("&nonce=");
        srcStr.append(nonce);
        // srcStr.append("&secretKey=");
        // srcStr.append(secretKey);

        Log.e("tag","res is :"+srcStr.toString());
        // 请求方法 +请求路径 + ?+系统编码+utc时间戳+随机正整数
        String signature = sha256(srcStr.toString(), secretKey);

        return signature;
    }

//    public  static String sha256(String input, String salt) throws NoSuchAlgorithmException {
//        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
//        mDigest.update(salt.getBytes());
//        byte[] result = mDigest.digest(input.getBytes());
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < result.length; i++) {
//            String cal = Integer.toString((result[i] & 0xff) + 0x100, 16);
//            sb.append(cal.substring(1));
//        }
////        return sb.toString();
//        return byte2Hex(result);
//    }
    /**
     * 输入字符串使用sha265进行处理
     *
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public  static String sha256(String input, String salt) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");

        mDigest.update(salt.getBytes());//更新密钥
        byte[] result = mDigest.digest(input.getBytes());//digest里面如果有参数，也是和update一样，更新密钥
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
