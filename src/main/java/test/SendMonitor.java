package test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by lyd on 2018/5/29.
 * 发送http请求到nginx
 */
public class SendMonitor {
    public static final Logger logger = Logger.getGlobal();
    //定义一个队列用于存储url
    public static final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    //创建一个单利对象
    private static SendMonitor monitor = null;

    //获取实例
    public static SendMonitor getSendMonitor() {
        if (monitor == null) {
            //加锁，防止两个线程同时获取实例
            synchronized (SendMonitor.class) {
                if (monitor == null) {
                    monitor = new SendMonitor();

                    //创建一个独立线程
                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //操作发送url
//                           requestUrl();
                            SendMonitor.getSendMonitor();
                            requestUrl();
                        }
                    });
//                    th.setDaemon(true);  //挂载在后台启动，一般用于服务端
                    //开启线程
                    th.start();  //非后台挂载运行

                }
            }
        }
        return monitor;
    }

    //将url添加队列
    public static void addUrlToQueue(String url) {
//        SendMonitor.queue.add(url);
        try {
            queue.put(url);
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "添加url到队列中异常", e);
        }
    }

    /**
     * 消费队列中的url
     */
    public static void requestUrl() {
        try {
            String url = queue.take();
//            String url = queue.poll();
            HttpUrlUtil.sendUrl(url);
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "获取队列中url异常", e);
        }
    }


    /**
     * 该类用于发送http请求
     */
    public static class HttpUrlUtil {

        /**
         * 真正发送url的方法
         *
         * @param url
         */
        public static void sendUrl(String url) {
            HttpURLConnection conn = null;
            InputStream is = null;
            try {
                //创建URL对象
                URL u = new URL(url);
                try {
                    conn = (HttpURLConnection) u.openConnection();
                    //为连接设置请求方法
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);

                    //发送
                    is = conn.getInputStream();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "获取连接异常");
                } finally {
                    conn.disconnect();
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            //do nothing
                        }
                    }
                }
            } catch (MalformedURLException e) {
                logger.log(Level.WARNING, "url的异常", e);
            }
        }
    }
}
