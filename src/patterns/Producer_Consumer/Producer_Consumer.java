package patterns.Producer_Consumer;

public class Producer_Consumer {
    public static void main(String[] args) {
        DataQueue dataQueue=new DataQueue();
        Producer producer=new Producer(dataQueue);
        Consumer consumer=new Consumer(dataQueue);
        new Thread(consumer).start();
    }
}
