package test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by lyd on 2018/5/29.
 * 将下单后的oid通过sdk来支付或者退款，然后将日志信息产生日志nginx中
 */
public class AnalysticLogSdk {
    public static final Logger logger = Logger.getGlobal();
    //定义请求的url
    public static final String url = "http://192.168.5.101/index.html";
    //平台名称
    public static final String platformName = "java";

    public static final String sdkName = "java_sdk";
    //版本号
    public static final String ver = "1";

    /**
     * 成功支付事件，如果返回true则表示支持成功，否则失败
     *
     * @param oid
     * @param u_mid
     * @return
     */
    public static boolean ChargeSuccess(String oid, String u_mid) {
        if (isNotEmpty(oid) && isNotEmpty(u_mid)) {
            try {
                //拼接请求的参数  http://192.168.216.111/demo.html？pl=
                Map<String, String> params = new HashMap<String, String>();
                //将sdk发送的参数放到map中
                params.put("u_mid", u_mid);
                params.put("oid", oid);
                params.put("pl", platformName);
                params.put("sdk", sdkName);
                params.put("en", "e_cs");
                //构造请求的url
                String requestUrl = buildUrl(params);

                //将url添加到队列中
                SendMonitor.getSendMonitor();
                SendMonitor.addUrlToQueue(requestUrl);
                return true;
            } catch (Exception e) {
                logger.log(Level.WARNING, "支付异常", e);
            }
        } else {
            System.out.println("请你检测oid或者u_mid，二者必须不能为空。");
        }
        return false;
    }


    /**
     * 成功退款事件
     *
     * @param oid
     * @param u_mid
     * @return
     */
    public static boolean ChargeRefund(String oid, String u_mid) {
        if (isNotEmpty(oid) && isNotEmpty(u_mid)) {
            try {
                //拼接请求的参数  http://192.168.216.111/demo.html？pl=
                Map<String, String> params = new HashMap<String, String>();
                //将sdk发送的参数放到map中
                params.put("u_mid", u_mid);
                params.put("oid", oid);
                params.put("pl", platformName);
                params.put("sdk", sdkName);
                params.put("en", "e_cr");
                //构造请求的url
                String requestUrl = buildUrl(params);
                //将url添加到队列中
                SendMonitor.getSendMonitor().addUrlToQueue(requestUrl);
                return true;
            } catch (Exception e) {
                logger.log(Level.WARNING, "支付异常", e);
            }
        } else {
            System.out.println("请你检测oid或者u_mid，二者必须不能为空。");
        }
        return false;
    }

    /**
     * 构建url
     *
     * @param params
     * @return
     */
    private static String buildUrl(Map<String, String> params) {
        //http://192.168.216.111/index.html？pl=12&ver=1
        StringBuffer sb = new StringBuffer();
        if (!params.isEmpty()) {
            sb.append(url + "?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.log(Level.WARNING, "参数值编码异常", e);
                }
                sb.append("&");
            }
        }
        System.out.println(sb.toString());
        return sb == null ? null : sb.toString().substring(0, sb.length() - 1);
    }


    /**
     * 判断字符串是否为空，为空返回true，反之返回false
     *
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if (input == null || input.equals("") || input.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }


}
