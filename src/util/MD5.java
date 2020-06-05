package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
public class MD5 {

    public static String encode(String data) throws Exception {
        String md5Result = null;
        if(null == data){
            return md5Result;
        }
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] b = md.digest();
            int i;
            StringBuffer sb = new StringBuffer("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            md5Result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Result;
    }
}
