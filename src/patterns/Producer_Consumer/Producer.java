package patterns.Producer_Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Producer {
    private JLabel status;
    JFrame frame;
    JButton button;
    private DataQueue dataQueue;

    int iterator=0;

    Producer(DataQueue dataQueue){
        this.dataQueue=dataQueue;
        initJframe();
    }


    private synchronized void pullRequest() throws InterruptedException {
        this.status.setText("Pushing request...");

        this.button.setEnabled(false);
        this.button.repaint();
        this.frame.revalidate();
        status.paintImmediately(status.getVisibleRect());
        this.button.paintImmediately(this.button.getVisibleRect());

        this.dataQueue.addRequest("ReqFromProducer "+iterator++);

        button.setEnabled(true);
        this.status.setText("FREE");
    }


    private void initJframe(){
         frame = new JFrame("Producer");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

         status = new JLabel("init");
         button = new JButton();
        button.setText("make request");
        Producer cntxt=this;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {cntxt.pullRequest();
                } catch (InterruptedException ex) {ex.printStackTrace();}
            }
        });

        panel.add(status);
        panel.add(button);

        frame.add(panel);

        int x=(int) MouseInfo.getPointerInfo().getLocation().getX()-220;
        int y=(int) MouseInfo.getPointerInfo().getLocation().getY();

        frame.setLocation(x, y);
        frame.setAlwaysOnTop(true);
        frame.setSize(200, 100);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
