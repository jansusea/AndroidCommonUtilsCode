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
     * 返回某个字符串的md5值
     * @param str
     * @return 小写md5值
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
     * MD5数字签名
     * @param src
     * @return
     * @throws Exception
     */
    public String md5Digest(String src) throws Exception {
        // 定义数字签名方法, 可用：MD5, SHA-1
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(src.getBytes(UTF8));
        return this.byte2HexStr(b);
    }

    /**
     * 字节数组转化为大写16进制字符串
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

	public static String formatTime2HMS(long time) {
		String a = null;

		Date date = new Date(time);

		if (time < 60) {
			a = time + "秒";
		} else if (time < 3600) {
			a = (time / 60) + "分" + (time % 60) + "秒";
		} else{
			long h = time / 3600;
			long m = (time - time / 3600 * 3600) / 60;
			long s = time - h * 3600 - m * 60;
			a = h + "时" + m + "分" + s + "秒";
		}
		return a;
	}

    /**
     * 匹配手机
     *
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number){
		/*		三大运营商号段： 在百度百科上查询结果
		中国移动号段：134、135、136、137、138、139、150、151、152、157、158、159、147、182、183、184、187、188、1705[1]  、178
		中国联通号段：130、131、132、145（145属于联通无线上网卡号段）、155、156、185、186 、176、1709[1]  、176
		中国电信号段：133 、153 、180 、181 、189、1700[1]  、177*/
        Pattern p = Pattern.compile("^((1[358][0-9])|(14[57])|(17[03678]))\\d{8}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

	private static String formatTime(String time) {
		String formatTime = null;
		if (time == null || time.equals("")) {
			return formatTime;
		}
		long originTime = Long.valueOf(time);

		long now = System.currentTimeMillis();
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTimeInMillis(now);

		int year = nowCalendar.get(Calendar.YEAR);
		int month = nowCalendar.get(Calendar.MONTH);
		int day = nowCalendar.get(Calendar.DAY_OF_MONTH);
		nowCalendar.set(year, month, day, 0, 0, 0);
		long today = nowCalendar.getTime().getTime();
		
		if(originTime > today){
			// get hh:mm
			SimpleDateFormat s = new SimpleDateFormat("hh:mm");
			formatTime = s.format(new Date(originTime));
			return formatTime;
		}
		
		nowCalendar.set(year, month, day - 1, 0, 0, 0);
		long yesterday = nowCalendar.getTime().getTime();
		if(originTime > yesterday){
			// yesterday
			formatTime = "昨天";
			return formatTime;
		}

		nowCalendar.set(year, 0, 1, 0, 0, 0);
		long thisYear = nowCalendar.getTime().getTime();
		if(originTime > thisYear){
			// this year,   mm:dd
			SimpleDateFormat s = new SimpleDateFormat("MM月dd日");
			formatTime = s.format(new Date(originTime));
			return formatTime;
		}
		
		// year年mm月dd日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		formatTime = sdf.format(new Date(originTime));
		return formatTime;

	}
}
