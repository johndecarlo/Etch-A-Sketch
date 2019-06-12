/**
 * John DeCarlo
 * EtchSketch.java
 * June 11th, 2019
 */
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EtchSketch extends JPanel {
   
   private ImageIcon sketch_bottom = new ImageIcon("images/sketch_bottom.png");              //Bottom of Etch-A-Sketch
   private ImageIcon sketch_bottom_left = new ImageIcon("images/sketch_bottom_left.png");    //Bottom left of Etch-A-Sketch
   private ImageIcon sketch_bottom_right = new ImageIcon("images/sketch_bottom_right.png");  //Bottom right of Etch-A-Sketch
   private ImageIcon sketch_left = new ImageIcon("images/sketch_left.png");                  //Left side of Etch-A-Sketch
   private ImageIcon sketch_right = new ImageIcon("images/sketch_right.png");                //Right side of Etch-A-Sketch
   private ImageIcon sketch_top_left = new ImageIcon("images/sketch_top_left.png");          //Top left of Etch-A-Sketch
   private ImageIcon sketch_top_right = new ImageIcon("images/sketch_top_right.png");        //Top right of Etch-A-Sketch
   private ImageIcon sketch_top = new ImageIcon("images/sketch_top.png");                    //Top of Etch-A-Sketch
   
   private ImageIcon white_pixel = new ImageIcon("images/white_pixel.png");  //White Pixel
   private ImageIcon black_pixel = new ImageIcon("images/black_pixel.png");  //Black Pixel
   
   private static int[][] pixel_board;       //Pixel board that will display our drawn picture
   private static int locationX;       //Column that our drawing tool is located under
   private static int locationY;       //Row that our drawing tool is located under
  
   private static File sketch_file; //File that we are creating when we want to save a sketch
   private static File sketches;    //Directory that holds all our sketches (16 max)
   private static int num_files;    //Number of files that we have saved
   private static int file_access;  //Number of files that we can access
   
   
   //Constructor for our Etch-A-Sketch
   public EtchSketch() {
      pixel_board = new int[120][160];   //Initalize board of pixels
      locationX = 80;      //Initial x location is middle of columns
      locationY = 60;      //Initial y location is middle of columns
      num_files = 0;       //We initally have no files created
      file_access = 0;     //We will access sketch0.txt first if it exists
      sketches = new File("sketches"); //Create the sketches file to hold our sketches
      if(!sketches.exists())     //If the sketches file doesn't exist
         sketches.mkdir();       //Make the directory "sketches"
   }
   
   //Get the number of rows in our drawing board
   public int getRows() {
      return pixel_board.length; //Return num of rows (120)
   }
   
   //Get the number of columns in our drawing board
   public int getColumns() {
      return pixel_board[0].length; //Return num of columns (160)
   }
   
   //Get the X location of our drawing tool
   public int getLocationX() {
      return locationX; //Return location X
   }
   
   //Get the Y location of our drawing tool
   public int getLocationY() {
      return locationY; //Return location Y
   }
   
   //Move our drawing tool to the left
   public void left() {
      locationX--;   //Decrease location X by 1
   }
   
   //Move our drawing tool to the right
   public void right() {
      locationX++;   //Increase location X by 1
   }
   
   //Move our drawing tool up
   public void up() {
      locationY--;   //Decrease location Y by 1
   }
   
   //Move our drawing tool down
   public void down() {
      locationY++;   //Increase location Y by 1
   }
   
   //Set the value of whatever pixel we input
   public void setValue(int value, int r, int c) {
      pixel_board[r][c] = value; //Set the value of the pixel at (r, c)
   }
   
   //Paint objects onto the JPanel
   public void paintComponent(Graphics g) {
      super.paintComponent(g); 	//Call super method
      showPixelBoard(g);         //Paint out the black and white pixels on the board
      showBorder(g);             //Paint out the red Etch-A-Sketch Border
   }
   
   //Paint out the red Etch-A-Sketch Border
   public void showBorder(Graphics g) {
      g.drawImage(sketch_top_left.getImage(), 0, 0, 100, 100, null); //Draw Top-Left of Etch-A-Sketch
      g.drawImage(sketch_top.getImage(), 100, 0, 800, 100, null); //Draw Top of Etch-A-Sketch
      g.drawImage(sketch_top_right.getImage(), 900, 0, 100, 100, null); //Draw Top-Right of Etch-A-Sketch
      g.drawImage(sketch_left.getImage(), 0, 100, 100, 600, null); //Draw Left Border of Etch-A-Sketch
      g.drawImage(sketch_right.getImage(), 900, 100, 100, 600, null); //Draw Right Border of Etch-A-Sketch
      g.drawImage(sketch_bottom_left.getImage(), 0, 700, 100, 100, null); //Draw Top-Left of Etch-A-Sketch
      g.drawImage(sketch_bottom.getImage(), 100, 700, 800, 100, null); //Draw Top of Etch-A-Sketch
      g.drawImage(sketch_bottom_right.getImage(), 900, 700, 100, 100, null); //Draw Top-Right of Etch-A-Sketch
   }
   
   //Paint out the black and white pixels on the board
   public void showPixelBoard(Graphics g) {
      for(int r = 0; r < getRows(); r++) {   //For the rows in pixel_board
         for(int c = 0; c < getColumns(); c++) {   //For the columns in pixel_board
            if(pixel_board[r][c] == 1)    //If pixel is equal to 1
               g.drawImage(black_pixel.getImage(), 100+(c*5), 100+(r*5), 5, 5, null);  //Display a black pixel
            else
               g.drawImage(white_pixel.getImage(), 100+(c*5), 100+(r*5), 5, 5, null);  //Display a white pixel
         }
      }
   }
   
   //Process the Key input we give the computer
   public void processUserInput(int k) {
      if(k==KeyEvent.VK_UP && getLocationY() - 1 >= 0) {                   //If we press the up key, we draw up a space
         up();    //Move up
      } else if(k==KeyEvent.VK_DOWN && getLocationY() + 1 < getRows()) {   //If we press the down key, we draw down a space
         down();  //Move down
      } else if(k==KeyEvent.VK_LEFT && getLocationX() - 1 >= 0) {          //If we press the left key, we draw left of the space
         left();  //Move left
      } else if(k==KeyEvent.VK_RIGHT && getLocationX() + 1 < getColumns()) {  //If we press the right key, we draw right of the space
         right(); //Move right
      } else if(k==KeyEvent.VK_C) {       //Clear the board and start again
         for(int r = 0; r < getRows(); r++) {   //For the num of rows in pixel_board
            for(int c = 0; c < getColumns(); c++) {   //For the num of columns in pixel_board
               pixel_board[r][c] = 0;     //Set pixel_board at (r, c) to 0
            } 
         } 
         EtchSketchDriver.setOutputText("Board cleared");   //Set message to board cleared
      } else if(k==KeyEvent.VK_E) {    //If we press the E key, we want to open a saved sketch and edit it
         try {
            if(sketches.list().length > 0) { //If there is a file to access in the sketches directory
               boolean exists = new File("sketches/sketch"+Integer.toString(file_access)+".txt").exists();  //Check to see if the file exists
               while(exists == false) {            //While we can't find a instance of a file that exists
                  file_access++;                   //Increment the file access counter
                  sketch_file = new File("sketches/sketch"+Integer.toString(file_access)+".txt");  //Try to access a new file that is incremented
                  exists = sketch_file.exists();   //Get the exists boolean check again         
                  if(file_access == 15) {          //If the file_access counter is 15
                     file_access = -1;             //Set equal to -1 so it will be set to 0
                  }
               }
               Scanner sc = new Scanner(new File("sketches/sketch"+Integer.toString(file_access)+".txt"));  //Initialize scanner
               for(int r = 0; r < getRows(); r++) {         //For the num of rows in pixel_board
                  for(int c = 0; c < getColumns(); c++) {   //For the num of columns in pixel_board
                     if(sc.hasNext())           //If the line we are on in the scanner has a value at next
                        pixel_board[r][c] = Integer.parseInt(sc.next());   //Set the pixel board to the value we have at next
                     else                       
                        pixel_board[r][c] = 0;  //Else set the value to zero so we dont get any weird values
                  }
                  if(sc.hasNextLine())    //If the scanner has a new line to read in 
                     sc.nextLine();       //Get the next line, or else set to zero
               } 
               EtchSketchDriver.setOutputText("Accessed sketch"+file_access+".txt");   //Set message text to sketch we accessed
               if(file_access == 15)   //If we just accessed sketch #15
                  file_access = -1;    //Set equal to 0 so it will start at the beginning
               else
                  file_access++;       //Increment file_access value
               repaint();              //Repaint our Etch-A-Sketch
            } else {
               EtchSketchDriver.setOutputText("You have no sketches to edit.");  //State that there are no sketches to edit
            }
         } catch (FileNotFoundException e) { }  //Catch a FileNotFoundException
      } else if(k==KeyEvent.VK_S) {    //If we press the S key, we want to save a sketch we have drawn
         try {
            if(sketches.list().length < 16) {   //If the size of the sketches directory is smaller than 16 objects
               sketch_file = new File("sketches/sketch"+Integer.toString(num_files)+".txt");    //Create a new sketch_file
               boolean created = sketch_file.createNewFile();     //Boolean check for if we were able to create the sketch_file
               while(created == false) {                          //While we haven't created the new text file
                  num_files++;                        //Increment the num_files value
                  sketch_file = new File("sketches/sketch"+Integer.toString(num_files)+".txt"); //Set sketch_files to a new file
                  created = sketch_file.createNewFile();    //Boolean check for if we were able to create the sketch_file
                  if(num_files == 15 && created == false) { //If the num of files is 15 and we still havent created a new file
                     num_files = -1;      //Set num_files to -1 so it will be set to 0
                  }
               }
               PrintWriter pr = new PrintWriter(sketch_file);  //Initialze printWriter to read in file
               for(int r = 0; r < getRows(); r++) {         //For the num of rows in pixel_board
                  for(int c = 0; c < getColumns(); c++) {   //For the num of columns in pixel_board
                     pr.print(Integer.toString(pixel_board[r][c]) + " ");  //Read in value from pixel_board 
                  }
                  pr.println();  //Print out a new line into the file
               } 
               pr.close(); //Close the printWriter
               EtchSketchDriver.setOutputText("Sketch saved as sketch"+num_files+".txt");    //Message out that we've saved the sketch 
            } else {
               EtchSketchDriver.setOutputText("You already have 16 sketches saved.");  //Print out that we have the sketch saved
            } 
         } catch (IOException e) { }   //Throw IOException
      }
      setValue(1, getLocationY(), getLocationX()); //Set the location of our drawing tool to 1
      repaint();			   //Repaint our Etch-A-Sketch
   }
}