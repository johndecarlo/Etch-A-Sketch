/**
 * John DeCarlo
 * EtchSketch.java
 * Driver that runs our program
 * June 11th, 2019
 */

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class EtchSketchDriver {
   
   public static EtchSketch screen; //Screen for our Etch-A-Sketch
   public static JLabel message;  //Message that includes instructions
   
   public static void main(String[]args) {
      JFrame sketch = new JFrame("Etch-A-Sketch");    //Create our JFrame
      screen = new EtchSketch();                //Create a scrabbleBoard
      screen.setLayout(null);                   //Set the screen layout to null
      message = new JLabel("", SwingConstants.CENTER);   //Initialze our message JLabel
      Font font = new Font("",Font.BOLD,35);    //Initialize font2
      message.setBounds(150, 710, 700, 50);     //Set the bounds of message JLabel
      message.setFont(font);                    //Set the font of message   
      message.setForeground(Color.white);       //Set the color of text for message to white
      screen.add(message);                      //Add message to the screen
      
      sketch.setSize(1015, 850);			      //Size of game window
      sketch.setLocation(250, 10);				//location of game window on the screen
      sketch.setResizable(false);            //Cannot change size of the screen
      sketch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit when close out 
      sketch.setContentPane(screen);         //Set contentPanel to our board
      sketch.setVisible(true);               //Make the screen visible
      sketch.addKeyListener(new listen());	//Get input from the keyboard
   }
   
   //Method that sets the message of our output text
   public static void setOutputText(String text) {
      message.setText("<html>" + text + "</html>");   //Set the text of our JLabel
   }
      
    //Listener for our mouse Key Listener
   public static class listen implements KeyListener {
      
      public void keyTyped(KeyEvent e) { }
         
      public void keyPressed(KeyEvent e) {
         screen.processUserInput(e.getKeyCode());  //Call processUserInput from EtchSketch.java
      }
      
      public void keyReleased(KeyEvent e) { }
   }
  
}