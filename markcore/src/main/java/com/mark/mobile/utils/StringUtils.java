package com.mark.mobile.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

public class StringUtils {


    /**
     * 验证号码有是否有效
     *
     * @param number
     * @return
     */
    public static boolean isAvailablePhoneNumber(String number) {
        return StringUtils.isNotEmpty(number) && number.length() == 11 && PhoneNumberUtils.isGlobalPhoneNumber(number) &&
                startsWith(number) && isNumeric(number);
    }

    private static boolean startsWith(String number) {
        //13[0~9]、147、15[0~9]、18[0~9]
        if (number.startsWith("13")
                || number.startsWith("15")
                || number.startsWith("18")
                || number.startsWith("170")
                || number.startsWith("14")) {
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[0-9]*"); 
        return pattern.matcher(str).matches();    
     } 
    
    public static String emptyIfNull(String s) {
        return s == null ? "" : s;
    }

    public static String dateonly2str(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String date2str(Date date) {
        try {
            if (date == null)
                return "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    public static String datetime2str(Date date) throws Exception {
        if (date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String datetime3str(Date date) throws Exception {
        String r = "";
        if (date == null)
            return "";
        Date loginTime = new Date();
        long loginYear = loginTime.getYear();
        // 数据库里的真实时间
        int year = (date.getYear() + 1900);
        int month = date.getMonth() + 1;
        int _date = date.getDate();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        if (date.getYear() == loginYear) {
            if (date.getMonth() == loginTime.getMonth()) {
                if (date.getDate() == loginTime.getDate()) {
                    r = timeRange(hours) + " " + hours + ":" + minutesR(minutes);
                } else {
                    r = month + "月" + _date + "日 " + timeRange(hours) + " " + hours + ":" + minutesR(minutes);
                }
            } else {
                r = month + "月" + _date + "日 " + timeRange(hours) + " " + hours + ":" + minutesR(minutes);
            }

        } else {

            r = year + "年" + month + "月" + _date + "日 " + timeRange(hours) + " " + hours + ":" + minutesR(minutes);

        }
        return r;
    }

    private static String minutesR(int minutes) {
        if ((minutes + "").length() == 1)
            return "0" + minutes;
        else
            return minutes + "";
    }

    private static String timeRange(int hours) {
        if (hours == 0)
            return "凌晨";
        else if (hours > 0 && hours < 12)
            return "上午";
        else if (hours == 12)
            return "中午";
        else if (hours > 12 && hours < 18)
            return "下午";
        else
            return "晚上";
    }

    public static String date2str(Date date, String style) throws Exception {
        if (date == null || style == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return sdf.format(date);
    }

    /**
     * @param str
     * @return
     * @throws Exception
     */
    public static Date str2date(String str) throws Exception {
        if (str == null || str.length() == 0)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.parse(str);
    }

    public static Timestamp date2Timestamp(String str) throws Exception {
        if (str == null || str.length() == 0)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = sdf.parse(str);
        return new Timestamp(date.getTime());
    }

    public static Date str2datetime(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(str);
    }

    public static Date str2datetimenoss(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(str);
    }

    public static Date str2date(String str, String style) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return sdf.parse(str);
    }

    public static Date data2date(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.parse(sdf.format(date));
    }

    public static String long2str(long l) throws Exception {
        Long ll = l;
        return ll.toString();
    }

    public static String int2str(int i) throws Exception {
        Integer ii = i;
        return ii.toString();
    }

    /**
     * 字符串转换成十六进制字符串
     */

    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();

        StringBuilder sb = new StringBuilder("");

        byte[] bs = str.getBytes();

        int bit;

        for (int i = 0; i < bs.length; i++) {

            bit = (bs[i] & 0x0f0) >> 4;

            sb.append(chars[bit]);

            bit = bs[i] & 0x0f;

            sb.append(chars[bit]);

        }

        return sb.toString();

    }

    /**
     * 十六进制转换字符串
     */

    public static String hexStr2Str(String hexStr) {

        String str = "0123456789ABCDEF";

        char[] hexs = hexStr.toCharArray();

        byte[] bytes = new byte[hexStr.length() / 2];

        int n;

        for (int i = 0; i < bytes.length; i++) {

            n = str.indexOf(hexs[2 * i]) * 16;

            n += str.indexOf(hexs[2 * i + 1]);

            bytes[i] = (byte) (n & 0xff);

        }

        return new String(bytes);

    }

    /**
     * bytes转换成十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    /**
     * String的字符串转换成unicode的String
     */
    public static String stringToUnicode(String strText) throws Exception {
        char c;
        String strRet = "";
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                strRet += "\\u" + strHex;
            } else {
                // 低位在前面补00
                strRet += "\\u00" + strHex;
            }
        }
        return strRet;
    }

    /**
     * unicode的String转换成String的字符串
     */
    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    public static boolean stringToBool(String s) {
        if (s.toUpperCase().compareTo("FALSE") == 0)
            return false;
        else if (s.length() == 0)
            return false;
        else if (s.compareTo("0") == 0)
            return false;
        return true;
    }

    public static int stringToInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long stringToLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double stringToDouble(String s) {
        if (s == null || s.length() == 0)
            return 0;
        try {
            double d = Double.parseDouble(s);
            return d;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String doubleToString(double d) {
        String s = Double.toString(d);
        while (s.length() > 1 && (s.charAt(s.length() - 1) == '0' || s.charAt(s.length() - 1) == '.')) {
            s = s.substring(0, s.length() - 2);
        }
        return s;
    }

    public static String currencyToString(double d) {
        String s = String.format("%.2f", d);
        return s;
    }

    public static String getStringKey(int index, int pos) {
        Integer i = index;
        Integer p = pos;
        return i.toString() + "," + p.toString();
    }

    public static String getStringKey(long id, int pos) {
        Long l = id;
        Integer p = pos;
        return l.toString() + "," + p.toString();
    }

    public static boolean isEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.trim().length() > 0));
    }

    public static boolean isNotEmptyString(String str) {
        return ((str != null) && (str.trim().length() > 0) && (!str.equals("")));
    }

    public static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null)
            return true;
        if (s1 == null && s2 != null && s2.length() == 0)
            return true;
        if (s2 == null && s1 != null && s1.length() == 0)
            return true;
        if (s1 != null && s2 != null && s1.equals(s2))
            return true;
        return false;
    }

    public static String Md5(String XmlString) {
        StringBuffer result = new StringBuffer();
        byte[] data = XmlString.getBytes();

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] re = md.digest(data);
            for (int i = 0; i < re.length; i++) {
                result.append(byteHEX(re[i]));
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return result.toString();
    }

    /**
     * byteHEX()，用来把一个byte类型的数转换成十六进制的ASCII表示　因为java中的byte的toString无法实现这一点，我们又没有C语言中的 sprintf(outbuf,"%02X",ib)
     * @param ib
     * @return
     */
    private static String byteHEX(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }


    public static String getMD5Str(String str) {
        return StringUtils.getMD5string(str)+".png";
    }
    
    /**
     * MD5加密
     */
    public static String getMD5string(String str){
        if (str == null) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        // 16位加密，从第9位到25位
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
    }
    
	public static String stringToMD5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}
    
    
    /**
     * 是否包含除汉字，数字，字母，下划线以外的其他字符
     * @param string
     * @return
     */
	public static boolean isConSpeCharacters(String string) {
		/** 这个正则表达式用来判断是否为中文 */
		String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+{1}";

		/** 此正则表达式判断单词字符是否为：[a-zA-Z_0-9] **/
		String username = "^\\w+{1}";

		/** 此正则表达式将上面二者结合起来进行判断，中文、大小写字母和数字 **/
		String all = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{1,10}{1}";// {2,10}表示字符的长度是2-10
		Pattern pattern = Pattern.compile(all);
		return   !pattern.matcher(string).matches();
	}
	
	public static boolean isConSpe(String string){
		String username = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
		
		Pattern pattern = Pattern.compile(username);
		return  !pattern.matcher(string).matches();
	}
	
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
        	Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n+$");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
    }
	
    public static String convertAndReplace(String source){
        String formatString = "[\"]\\d{4}[\\-]\\d{2}[\\-]\\d{2} \\d{2}[\\:]\\d{2}[\\:]\\d{2}[\"]";
        
        Pattern pattern = Pattern.compile(formatString);
        Matcher matcher = pattern.matcher(source);
        
        while(matcher.find()){
            String find = matcher.group();
            int len = find.length();
            find = find.substring(1, len-1);
            Calendar cal = CalendarUtils.YYYYMMDDHHMMSSToCalendar(find);
            //cal.add(Calendar.MONTH, 1);
            if(cal!=null){
                long time = cal.getTimeInMillis();
                String targetString = "\""+String.valueOf(time)+"\"";
                source=source.replaceFirst(formatString, targetString);
            }
        }
        
        return source;
    }
    
    public static String delDotAtFirst(String source){
    	if(source==null){
    		return null;
    	}
    	if(source.startsWith(".")){
    		return source.substring(1, source.length());
    	}
    	return source;
    }
    
    /**
     * 判断source是否为无效，如果字符串为null时也为无效
     * @param source
     * @return
     */
    public static boolean isEmptyNotIgnoreNull(String source){
    	if(TextUtils.isEmpty(source) || "null".equals(source)){
    		return true;
    	}
    	return false;
    }
    
    
    /**
     * 获取文件扩展名
     * @param path 文件全路径
     */
    public static String getExtensionFromPath(String path){
    	String extension = "";

    	int i = path.lastIndexOf('.');
    	if (i >= 0) {
    	    extension = path.substring(i+1);
    	}
    	
    	return extension;
    }
    
    public static String setDefaultNumber(int value){
    	if(value!=0){
    		return String.valueOf(value);
    	}
    	return "";
    }
    public static String setDefaultNumber(double value){
    	if(value!=0){
    		return String.valueOf(value);
    	}
    	return "";
    }
    public static String setDefaultNumber(float value){
    	if(value!=0){
    		return String.valueOf(value);
    	}
    	return "";
    }
    public static String setDefaultNumber(long value){
    	if(value!=0){
    		return String.valueOf(value);
    	}
    	return "";
    }
    
    public static String setDefaultNumber(String string){
    	if(StringUtils.isEmptyNotIgnoreNull(string)){
    		return "";
    	}
    	
    	int value = 0;
    	try{
    		value = Integer.valueOf(string);
    	}catch(NumberFormatException e){
    	}
    	
		if(value==0){
			return "";
		}
		
		return string;
    }
    
    public static String getFromByDefault(String value){
    	String result = "未填写";
    	if(!TextUtils.isEmpty(value)){
    		result = value;
    	}
    	return result;

    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmail(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
