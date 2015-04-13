import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Random;

public class NumGuess extends Applet {
    /**
     * Applet Pics from https://www.cs.colostate.edu/~cs192/Labs/AppletExamples.html
     *
     */
    private static final long serialVersionUID = 774062063722281324L;

    final Color PINK4 = new Color ( 255, 128, 128 ) ;
    final Color GREEN2 = new Color ( 0, 208, 0 ) ;
    final Color LTBLUE = new Color ( 35, 206, 255 ) ;
    final Color REDBROWN = new Color ( 182, 100, 110 ) ;
    final Color DARKBROWN = new Color ( 150, 70, 80 ) ;
    final Color DARKGOLD = new Color ( 240, 220, 0 ) ;
    final int FIGUREHEIGHT = getHeight();
    final int FIGUREWIDTH  = getWidth();
    final int DELTA_Y = 50;

    int difficultyLevel = 0;
    int upperLimit;
    int picChoice;
    Label lblDifficultyLevel;
    Label lblInput;
    TextField txtInput;
    int randomNumber;
    int userGuess;
    int guessesTaken;
    int timeRemaining = 10;
    Timer timer;
    boolean playerHasLost = false;
    boolean gameWon = false;

    public NumGuess() {

        lblDifficultyLevel = new Label();
        lblInput = new Label();
        txtInput = new TextField(10);

        startNextGame();

        add(lblDifficultyLevel);
        add(lblInput);
        add(txtInput);

        txtInput.addActionListener(new TextInputListener());

        // Create javax.swing.Timer that fires action events every second
        // The second parameter is the action listener. As this applet implements
        // ActionListener, we can pass "this" here.
        timer = new Timer(1000, new TimerListener());
        timer.start();
    }

    public void startNextGame() {
        difficultyLevel++;
        upperLimit = difficultyLevel * 100;
        Random r = new Random();
        randomNumber = r.nextInt(upperLimit) + 1;
        picChoice = r.nextInt(4) + 1;   //Randomly picks one of 4 pictures


        lblDifficultyLevel.setText("Difficulty Level: " + difficultyLevel + " !");
        lblInput.setText("I'm thinking of a number between 1 and " + upperLimit + "! Take a Guess...");
        txtInput.setText(" ");			 	//clear data entry field
        txtInput.setText("");               //clear data entry field

        guessesTaken = 0;
        if (timer != null) {
            timer.restart();
        }
    }



    public void paint( Graphics graphics )
    {
        if(!playerHasLost) {

            if (guessesTaken == 0) { //New Game
                setBackground(Color.WHITE);
            }

            if (picChoice == 1) {
                drawStickman(guessesTaken, graphics);
            } else if (picChoice == 2) {
                drawSnowman(guessesTaken, graphics);
            } else if (picChoice == 3) {
                drawCar(guessesTaken, graphics);
            } else if (picChoice == 4) {
                drawFaceWithHat(guessesTaken, graphics);
            }

            Color originalColor = graphics.getColor();

            if (timeRemaining > 3) {
                graphics.setColor(Color.GREEN);
            } else {
                graphics.setColor(Color.RED);
            }
            graphics.drawString("Time Remaining: " + timeRemaining, 0, getHeight() - 5);

            graphics.setColor(originalColor);

        }else{
            setBackground(Color.RED);
            graphics.setFont(new Font("default", Font.BOLD, 50));
            graphics.drawString("You Lose!!!", getWidth()/2, getHeight()/2);
            lblDifficultyLevel.setVisible(false);
            lblInput.setVisible(false);
            txtInput.setVisible(false);
            showStatus("Goodbye!!");
        }
    }

    public class TimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            timeRemaining--;
            if(timeRemaining < 1){
                guessesTaken++;
                showStatus("You ran out of time!!    You have " + (10 - guessesTaken) + " guesses remaining.");	 	//show result
                timeRemaining = 10;
            }
            if (guessesTaken > 9){
                playerHasLost = true;
                timeRemaining = 20;

            }
            repaint(10); // Repaint in 10 ms;
        }
    }

    public class TextInputListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //Stick some Input validation in here!

            guessesTaken++;
            userGuess = Integer.parseInt(e.getActionCommand()); //get number

            if(userGuess < randomNumber){
                showStatus("Your guess of " + userGuess + " is too Low.         You have " + (10 - guessesTaken) + " guesses remaining.");	 	//show result

            }else if(userGuess > randomNumber){
                showStatus("Your guess of " + userGuess + " is too High.        You have " + (10 - guessesTaken) + " guesses remaining.");	 	//show result

            }else{ //Well, if it isn't too Low and it isn't too High, then it must be equal, right!!
                showStatus("Well done!    You are right.    My number was " + randomNumber + " and you got it in " + guessesTaken + " guesses!");	 	//show result
                gameWon = true;
                timer.stop();
                startNextGame();
            }


            //Reset TextField... Yes, it requires both of these!!!
            txtInput.setText(" ");			 	//clear data entry field
            txtInput.setText("");               //clear data entry field
            timeRemaining = 10;

            if (guessesTaken > 9){
                playerHasLost = true;
                timeRemaining = 20;

            }

            repaint();

        }
    }


    private void drawFaceWithHat(int guessesTaken, Graphics graphics) {

        if(guessesTaken > 0){   //After 1st Guess
            // background for figure
            graphics.setColor ( Color.white ) ;
            graphics.fillRect ( 0, DELTA_Y, FIGUREWIDTH, FIGUREHEIGHT ) ;
        }

        if(guessesTaken > 1) {   //After 2nd Guess
            // outline of face
            graphics.setColor(Color.black);
            graphics.drawOval(20, 54 + DELTA_Y, 166, 166);
        }

        if(guessesTaken > 2) {   //After 3rd Guess
            // mouth
            graphics.setColor(PINK4);
            graphics.fillOval(91, 160 + DELTA_Y, 24, 24);
        }
        if(guessesTaken > 3) {   //After 4th Guess
            //  two eyes
            graphics.setColor(LTBLUE);
            graphics.fillOval(66, 108 + DELTA_Y, 16, 16);
        }
        if(guessesTaken > 4) {   //After 5th Guess
            graphics.setColor(LTBLUE);
            graphics.fillOval(124, 108 + DELTA_Y, 16, 16);
        }
        if(guessesTaken > 5) {   //After 6th Guess
            // hat
            graphics.setColor(GREEN2);
            graphics.fillRect(20, 35 + DELTA_Y, 166, 42);
        }
        if(guessesTaken > 6) {   //After 7th Guess
            graphics.setColor(GREEN2);
            graphics.fillRect(62, 15 + DELTA_Y, 83, 22);
        }
        if(guessesTaken > 7) {   //After 8th Guess
            // nose
            graphics.setColor(Color.black);
            graphics.drawLine(103, 125 + DELTA_Y, 103, 151 + DELTA_Y);
            graphics.fillOval(96, 150 + DELTA_Y, 3, 3);
            graphics.fillOval(107, 150 + DELTA_Y, 3, 3);
        }
        if(guessesTaken > 8) {   //After 9th Guess
            // eyebrows
            graphics.setColor(Color.black);
            graphics.drawLine(74, 100 + DELTA_Y, 84, 104 + DELTA_Y);
            graphics.drawLine(74, 100 + DELTA_Y, 59, 110 + DELTA_Y);
        }
        if(guessesTaken > 9) {   //After 10th Guess
            graphics.drawLine(132, 100 + DELTA_Y, 122, 104 + DELTA_Y);
            graphics.drawLine(132, 100 + DELTA_Y, 147, 110 + DELTA_Y);
        }
    }

    private void drawCar(int guessesTaken, Graphics graphics) {

         if (guessesTaken > 0) {   //After 10th Guess
            // background for figure
            graphics.setColor(Color.white);
            graphics.fillRect(0, DELTA_Y, FIGUREWIDTH, FIGUREHEIGHT);
            graphics.setColor(Color.black);
            // front tire
            graphics.setColor(Color.black);
            graphics.fillOval(20, 170 + DELTA_Y, 100, 100);
            graphics.setColor(Color.white);
            graphics.fillOval(30, 180 + DELTA_Y, 80, 80);
            graphics.setColor(Color.black);
            graphics.drawOval(40, 190 + DELTA_Y, 60, 60);
        }
        if (guessesTaken > 1) {   //After 10th Guess
            // back tire
            graphics.setColor(Color.black);
            graphics.fillOval(270, 170 + DELTA_Y, 100, 100);
            graphics.setColor(Color.white);
            graphics.fillOval(280, 180 + DELTA_Y, 80, 80);
            graphics.setColor(Color.black);
            graphics.drawOval(290, 190 + DELTA_Y, 60, 60);
        }
        if (guessesTaken > 2) {   //After 10th Guess
            // car hood
            graphics.setColor(DARKBROWN);
            graphics.fillRect(10, 113 + DELTA_Y, 122, 12);
            graphics.setColor(REDBROWN);
            graphics.fillRect(10, 123 + DELTA_Y, 122, 82);
        }
        if (guessesTaken > 3) {   //After 10th Guess
            // car hood ornament
            graphics.setColor(DARKGOLD);
            graphics.fillOval(10, 105 + DELTA_Y, 10, 10);

            //  car window
            graphics.setColor(LTBLUE);
            graphics.fillRect(130, 15 + DELTA_Y, 130, 100);
        }
        if (guessesTaken > 4) {   //After 10th Guess
            // car door
            graphics.setColor(REDBROWN);
            graphics.fillRect(130, 113 + DELTA_Y, 130, 92);

            // car backseat
            graphics.setColor(REDBROWN);
            graphics.fillRect(258, 15 + DELTA_Y, 122, 190);
        }
        if (guessesTaken > 5) {   //After 10th Guess
            // car trunk
            graphics.setColor(REDBROWN);
            graphics.fillRect(378, 80 + DELTA_Y, 57, 125);
        }
        if (guessesTaken > 6) {   //After 10th Guess
            // car running board
            graphics.setColor(DARKBROWN);
            graphics.fillRect(118, 205 + DELTA_Y, 154, 10);
        }
        if (guessesTaken > 7) {   //After 10th Guess
            // visor
            graphics.setColor(Color.black);
            graphics.drawLine(131, 15 + DELTA_Y, 110, 30 + DELTA_Y);
            graphics.drawLine(131, 16 + DELTA_Y, 110, 31 + DELTA_Y);
            graphics.drawLine(131, 17 + DELTA_Y, 110, 32 + DELTA_Y);
        }
        if (guessesTaken > 8) {   //After 10th Guess
            // door handle
            graphics.setColor(Color.black);
            graphics.drawLine(145, 125 + DELTA_Y, 170, 125 + DELTA_Y);
            graphics.drawLine(145, 124 + DELTA_Y, 170, 124 + DELTA_Y);
            graphics.drawLine(145, 123 + DELTA_Y, 170, 123 + DELTA_Y);
        }
        if (guessesTaken > 9) {   //After 10th Guess
            ////????
        }
    }

    private void drawStickman(int guessesTaken, Graphics graphics) {
        if(guessesTaken > 0) {   //After 10th Guess
            setBackground(Color.black);
            graphics.setColor(Color.red);
        }
        // the syntax of drawRect is (x1,y1,w,h)
        //    where (x1,y1) is position of the top left corner and
        //    w and h are width and height respectively

        if(guessesTaken > 1) {   //After 10th Guess
            // draw a boundary
            graphics.drawRect(5, 5 + DELTA_Y, 190, 190);
        }
        // the syntax of drawOval is (x1,y1,w,h)
        //    where (x1,y1) is the top left corner  and
        //    (w,h) is the width and height of the bounding rectangle

        if(guessesTaken > 2) {   //After 10th Guess
            // the head
            graphics.drawOval(90, 60 + DELTA_Y, 20, 20);
        }
        // the syntax of drawLine is (x1,y1,x2,y2);
        // to draw a line from point (x1,y1) to (x2,y2)

        if(guessesTaken > 3) {   //After 10th Guess
            // the body
            graphics.drawLine(100, 80 + DELTA_Y, 100, 120 + DELTA_Y);
        }
        if(guessesTaken > 4) {   //After 10th Guess
            // the hands
            graphics.drawLine(100, 100 + DELTA_Y, 80, 100 + DELTA_Y);
        }
        if(guessesTaken > 5) {   //After 10th Guess
            graphics.drawLine(100, 100 + DELTA_Y, 120, 75 + DELTA_Y);
        }
        if(guessesTaken > 6) {   //After 10th Guess
            // the legs
            graphics.drawLine(100, 120 + DELTA_Y, 85, 135 + DELTA_Y);
        }
        if(guessesTaken > 7) {   //After 10th Guess
            graphics.drawLine(100, 120 + DELTA_Y, 115, 135 + DELTA_Y);
        }
        if(guessesTaken > 8) {   //After 10th Guess
            // the greeting
            graphics.drawString("Hi there", 20, 180 + DELTA_Y);
        }
        if(guessesTaken > 9) {   //After 10th Guess
            graphics.drawString("You Lose", 20, 200 + DELTA_Y);
        }
    }

    private void drawSnowman(int guessesTaken, Graphics graphics) {
        int middle = 150;	// middle of the snowman
        int top = 50 + DELTA_Y;		// top of the snowman

        if(guessesTaken > 0) {   //After 10th Guess
            setBackground(Color.cyan);
        }
        if(guessesTaken > 1) {   //After 10th Guess
            // color the ground
            graphics.setColor(Color.blue);
            // the ground is a blue rectangle
            graphics.fillRect(1, 175, 300, 50);
        }

        if(guessesTaken > 2) {   //After 10th Guess
            //  draw three large snowballs to make up snowman
            graphics.setColor(Color.white);
        }
        if(guessesTaken > 3) {   //After 10th Guess
            // draw head
            graphics.fillOval(middle - 20, top, 40, 40);
        }
        if(guessesTaken > 4) {   //After 10th Guess
            // draw middle (upper torso)
            graphics.fillOval(middle - 35, top + 35, 70, 50);
        }
        if(guessesTaken > 5) {   //After 10th Guess
            // draw base (lower torso)
            graphics.fillOval(middle - 50, top + 80, 100, 60);
        }
        if(guessesTaken > 6) {   //After 10th Guess
            //  draw in features of snowman
            graphics.setColor(Color.black);
            //  draw eyes
            // draw left eye
            graphics.fillOval(middle - 10, top + 10, 5, 5);
            // draw right eye
            graphics.fillOval(middle + 5, top + 10, 5, 5);

        }
        if(guessesTaken > 7) {   //After 10th Guess
            // draw mouth
            graphics.drawArc(middle - 10, top + 20, 20, 10, 190, 160);
        }
        if(guessesTaken > 8) {   //After 10th Guess
            //  draw arms
            // draw left arm
            graphics.drawLine(middle - 25, top + 60, middle - 50, top + 40);
            // draw right arm
            graphics.drawLine(middle + 25, top + 60, middle + 55, top + 60);
        }
        if(guessesTaken > 9) {   //After 10th Guess

            //  draw hat
            // draw brim of hat
            graphics.drawLine(middle - 20, top + 5, middle + 20, top + 5);
            // draw top of hat
        }


    }

}