package main.input;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int initialX = -1;
    private int initialY = -1;
    private int scroll = 0;

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public int getScroll() {
        return this.scroll;
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    public void resetButton() {
        this.mouseB = -1;
    }

    public ClickType getMouseB() {
        switch(this.mouseB) {
            case 1:
                return ClickType.LEFT_CLICK;
            case 2:
                return ClickType.SCROLL_CLICK;
            case 3:
                return ClickType.RIGHT_CLICK;
            default:
                return ClickType.UNDEFINED;
        }
    }

    public void resetScroll(){
        this.scroll = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        resetButton();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(this.getMouseB() == ClickType.UNDEFINED) {
            this.mouseX = e.getX();
            this.mouseY = e.getY();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.scroll = e.getWheelRotation();
    }
}
