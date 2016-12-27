import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shu on 2016/6/6.
 */
public class CommonUtils {

    /**
     * ����ĳ���ַ�����md5ֵ
     * @param str
     * @return Сдmd5ֵ
     */
    public static String md5(String str){
        if (TextUtils.isEmpty(str)){
            return null;
        }
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] b = md5.digest(str.getBytes("UTF-8"));

            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i < b.length;i++){
                String s = Integer.toHexString(b[i] & 0xFF);
                if (s.length() < 2){
                    s = "0" + s;
                }
                stringBuilder.append(s);
            }
            result = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static final String UTF8 = "utf-8";

    /**
     * MD5����ǩ��
     * @param src
     * @return
     * @throws Exception
     */
    public String md5Digest(String src) throws Exception {
        // ��������ǩ������, ���ã�MD5, SHA-1
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(src.getBytes(UTF8));
        return this.byte2HexStr(b);
    }

    /**
     * �ֽ�����ת��Ϊ��д16�����ַ���
     * @param b
     * @return
     */
    private String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s.toUpperCase());
        }
        return sb.toString();
    }

    public static boolean checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    public static String generateRandom(){
        String stringRandom = null;
        Random random = new Random(System.currentTimeMillis());
        int number = random.nextInt(10000);
        if (number < 10){
            stringRandom = "000" + number;
        } else if (number < 100){
            stringRandom = "00" + number;
        } else if (number < 1000){
            stringRandom = "0" + number;
        } else{
            stringRandom = number + "";
        }
        return stringRandom;
    }

    /**
     * ƥ���ֻ�
     *
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number){
		/*		������Ӫ�̺ŶΣ� �ڰٶȰٿ��ϲ�ѯ���
		�й��ƶ��ŶΣ�134��135��136��137��138��139��150��151��152��157��158��159��147��182��183��184��187��188��1705[1]  ��178
		�й���ͨ�ŶΣ�130��131��132��145��145������ͨ�����������ŶΣ���155��156��185��186 ��176��1709[1]  ��176
		�й����źŶΣ�133 ��153 ��180 ��181 ��189��1700[1]  ��177*/
        Pattern p = Pattern.compile("^((1[358][0-9])|(14[57])|(17[03678]))\\d{8}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }
}
