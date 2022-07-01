package main;

import main.point.PointThree;
import main.shapes.Polygons;
import main.shapes.Tetrahedron;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Display extends JFrame implements Runnable{

    private Thread thread;
    private static String title = ":)";
    public static final int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int IMAGE_WIDTH = 800;
    public static final int IMAGE_HEIGHT = 450;
    private volatile boolean isRunning = false;

    private BufferedImage image;

    //used for showing the ticks and frames each second on the screen
    private int finalTicks = 0;
    private int finalFrames = 0;

    private Tetrahedron tetra;

    public Display() {
        thread = new Thread(this);
        image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        JFrameInit();
        init();
        start();
    }

    public void JFrameInit(){
        setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        setTitle(title);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
    }

    public static void main(String[] args) {
        new Display();
    }

    public synchronized void start() {
        isRunning = true;
        this.thread.start();
    }

    public synchronized void stop() throws InterruptedException {
        isRunning = false;
        this.thread.join();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double NanosPerTick = 1000000000D / 60D;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / NanosPerTick;
            lastTime = now;
            boolean shouldRender = true; //if false, frame rate locks to the tick count, if not - vice versa

            while(delta >= 1){
                ticks++;
                update();
                delta -= 1;
                shouldRender = true;
            }

            try {
                //can increase the sleep rate to lower the fps and processing power
                Thread.sleep(7);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if(shouldRender){
                frames++;
                render();
            }

            if(System.currentTimeMillis() - lastTimer >= 1000){
                lastTimer += 1000;
                finalTicks = ticks;
                finalFrames = frames;
                System.out.println(ticks + " ticks, " + frames + " frames per second");
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        image.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_SMOOTH);
        g.setColor(Color.black);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        tetra.render(g);

        g.dispose();
        bs.show();

    }

    private void init(){
        int s = 100;
        PointThree p1 = new PointThree(s/2, -s/2, -s/2);
        PointThree p2 = new PointThree(s/2, s/2, -s/2);
        PointThree p3 = new PointThree(s/2, s/2, s/2);
        PointThree p4 = new PointThree(s/2, -s/2, s/2);
        PointThree p5 = new PointThree(-s/2, -s/2, -s/2);
        PointThree p6 = new PointThree(-s/2, s/2, -s/2);
        PointThree p7 = new PointThree(-s/2, s/2, s/2);
        PointThree p8 = new PointThree(-s/2, -s/2, s/2);
        this.tetra = new Tetrahedron(
                    new Polygons(Color.RED, p1, p2, p3, p4),
                    new Polygons(Color.YELLOW, p5, p6, p7, p8),
                    new Polygons(Color.BLUE, p1, p2, p6, p5),
                    new Polygons(Color.CYAN, p1, p5, p8, p4),
                    new Polygons(Color.GREEN, p2, p6, p7, p3),
                    new Polygons(Color.MAGENTA, p4, p3, p7, p8)
                );
        this.tetra.rotate(true, 0, 0, 0);
    }

    private void update(){
        this.tetra.rotate(true, 1, 1, 1);
    }
}
