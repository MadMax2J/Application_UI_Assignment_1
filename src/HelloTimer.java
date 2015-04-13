import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class HelloTimer extends Applet implements ActionListener {

    // The "applet state" that advances up every second.
    int x = 0;
    Timer timer;

    // Register mouse listener here. Mouse listeners can be the
    // same class as the applet if the listener methods
    // are added.
    public void init() {
        // Create javax.swing.Timer that fires action events every second
        // The second parameter is the action listener. As this applet implements
        // ActionListener, we can pass "this" here.
        timer = new Timer(1000, this);
        timer.start();
    }

    // This method is mandatory, but can be empty.
    public void stop() {}

    // Print a message on the screen (timeRemaining=20, y=10).
    public void paint(Graphics g) {
        g.drawString("Counting: "+x, 20, 10);
    }

    // This method is called by the timer.
    public void actionPerformed(ActionEvent e) {
        x = x + 1;
        repaint(10); // Repaint in 10 ms;
    }
}