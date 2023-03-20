package patterns.Producer_Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Consumer implements Runnable {
    private DataQueue dataQueue;
    private JTextArea logStatus;
    private JLabel status;

    Consumer(DataQueue dataQueue){
        this.dataQueue=dataQueue;
        initJframe();
    }


    private void initJframe(){
        JFrame frame = new JFrame("Consumer");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        status=new JLabel("init just");
        logStatus = new JTextArea("init");

        JScrollPane scroller = new JScrollPane(logStatus, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(status);
        panel.add(scroller);

        frame.add(panel);

        int x=(int) MouseInfo.getPointerInfo().getLocation().getX()+220;
        int y=(int) MouseInfo.getPointerInfo().getLocation().getY();

        frame.setLocation(x, y);
        frame.setSize(330, 300);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void makeWork(String data) throws InterruptedException {
        status.setText("make work on data "+data);
        Thread.sleep(2000);
        logStatus.setText(data+"\n"+logStatus.getText());
    }
    private String pullRequest() throws InterruptedException {
        status.setText("Waiting reqs");
        String data= this.dataQueue.pullRequest();

        return data;
    }

    @Override
    public void run() {
        while (true){
            try {String data=pullRequest();
                makeWork(data);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
