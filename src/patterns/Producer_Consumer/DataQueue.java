package patterns.Producer_Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DataQueue {

    private BlockingQueue<String> blockingQueue;
    private JProgressBar pbar;
    
    private final int maxCapacity=10;

    DataQueue() {
        blockingQueue= new ArrayBlockingQueue<>(maxCapacity);
        initJframe();
    }

    void addRequest(String data) throws InterruptedException {
        blockingQueue.put(data);
    }


    String pullRequest() throws InterruptedException {
        return blockingQueue.take();
    }


    private void initJframe() {

        JFrame frame = new JFrame("Queue");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        pbar=new JProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(maxCapacity);

        panel.add(pbar);

        frame.add(panel);
        frame.setSize(220, 80);

        int x=(int) MouseInfo.getPointerInfo().getLocation().getX();
        int y=(int) MouseInfo.getPointerInfo().getLocation().getY();

        frame.setLocation(x, y);
//        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        DataQueue ctxt=this;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ctxt.pbar.setValue(ctxt.blockingQueue.size());
            }
        };
        new Timer(10, taskPerformer).start();
    }


//    ArrayBlockingQueue — очередь, реализующая классический кольцевой буфер;
//    LinkedBlockingQueue — односторонняя очередь на связанных узлах;
//    LinkedBlockingDeque — двунаправленная очередь на связанных узлах;
//    SynchronousQueue — блокирующую очередь без емкости (операция добавления одного потока находится в ожидании соответствующей операции удаления в другом потоке);
//    LinkedTransferQueue — реализация очереди на основе интерфейса TransferQueue;
//    DelayQueue — неограниченная блокирующая очередь, реализующая интерфейс Delayed;
//    PriorityBlockingQueue — реализация очереди на основе интерфейса PriorityQueue.
}
