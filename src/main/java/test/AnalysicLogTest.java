package test;

/**
 * Created by lyd on 2018/5/29.
 * 打包在服务端运行：java -classpath /root/GP1704_JavaSDK-0.1.jar edu.qianfeng.AnalysicLogTest
 */
public class AnalysicLogTest {
    public static void main(String[] args) {
//        System.out.println(AnalysticLogSdk.ChargeSuccess("123460","D9122AFE-9FF6-4F6E-BE7D-47CB7FE83B83"));
        System.out.println(AnalysticLogSdk.ChargeRefund("123460", "D9122AFE-9FF6-4F6E-BE7D-47CB7FE83B83"));
    }
}
