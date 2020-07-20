package IO;

public class TestMyThread{
    public static void main(String[] args) {
        System.out.println("main");
        a a = new a();
        a.start();
        for (int i = 0; i < 101; i++) {
            System.out.println("jishu"+i);
        }
    }
}
class a extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 101; i+=2) {
            System.out.println("偶数"+i);
        }
    }
}
