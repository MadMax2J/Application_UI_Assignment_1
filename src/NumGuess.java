import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Random;

//Addition program - Addition.java
public class NumGuess extends Applet implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 774062063722281324L;
    int difficultyLevel = 2;
    Label lblInput;
    TextField txtInput;
    int randomNumber;
    int userGuess;
    int sum;
    int guessesTaken;

    public NumGuess() {
        int upperLimit = difficultyLevel * 100;
        Random r = new Random();
        randomNumber = r.nextInt(upperLimit) + 1;

        lblInput = new Label("Hi. I'm thinking of a number between 1 and " + upperLimit + "! Take a Guess..." );
        txtInput = new TextField(10);
        add(lblInput);
        add(txtInput);
        sum = 0;
        guessesTaken = 0;
        txtInput.addActionListener(this);
    }
    public void actionPerformed (ActionEvent e) {
        guessesTaken++;
        userGuess = Integer.parseInt(e.getActionCommand()); //get number

        if(userGuess < randomNumber){
            showStatus("Your guess of " + userGuess + " is too Low.");	 	//show result

        }else if(userGuess > randomNumber){
            showStatus("Your guess of " + userGuess + " is too High.");	 	//show result

        }else{ //Well, if it isn't too Low and it isn't too High, then it must be equal, right!!
            showStatus("Well done! You are right. My number was " + randomNumber + "!");	 	//show result

        }



        //sum = sum + userGuess;			 	//add number to sum
        //showStatus(Integer.toString(sum));	 	//show result



        //Reset TextField... Yes, it requires both of these!!!
        txtInput.setText(" ");			 	//clear data entry field
        txtInput.setText("");			 	//clear data entry field
        repaint();

    }
    public void paint( Graphics g )
    {
        if(userGuess != 0){
            if(userGuess > 100){
                g.setColor( Color.pink );
                g.fillOval( 20, 10, 100, 50 );
                g.setColor( Color.yellow );
                g.fillRect( 100, 10, 100, 50 );
                g.setColor( Color.orange );
                g.fillRect( 190, 10, 80, 50 );
            }else{
                // set XOR mode to yellow
                g.setXORMode( Color.yellow );
                g.fillOval( 180, 25, 60, 20 );
                g.setColor( Color.blue );
                g.fillArc( 150, 20, 20, 20, 0, 360 );
                g.setColor( Color.red );
                g.fillRect( 120, 25, 20, 20 );
            }
        }
    }
}