import javax.swing.*;
import java.awt.*;

public class Display extends Canvas implements Runnable{

    private Thread thread;
    private JFrame frame;
    private Dimension windowSize;
    private static String title = ":)";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private boolean isRunning = false;

    public Display(){
        this.frame = new JFrame();
        this.windowSize = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(windowSize);
    }

    public static void main(String[] args) {
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setVisible(true);

        display.start();
    }

    public synchronized void start(){
        isRunning = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }

    public synchronized void stop() throws InterruptedException {
        isRunning = false;
        this.thread.join();
    }

    @Override
    public void run() {

    }
}
