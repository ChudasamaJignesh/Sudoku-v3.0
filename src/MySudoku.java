/**
 * \brief
 * @author Jignesh Chudasama
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
/**
 * This class contains the "public static void main(String[] args)"
 * This class generates the  display window.
 * The centre panel has the buttons and the sudoku display
 */

public class MySudoku 
{
	public static byte[][] sudoku = new byte[729][82];  //global array for sudoku solution
	public static byte step = 0; //global variable for solution step
	
	private static final int WindowWidth = 777; //its 777 pixels wide
	private static final int WindowHeight = 636; //its 636 pixels high 
	
    public static void ShowGUI() 
	 {
    	    Smethods.start(sudoku); //start array at step 0 has no numbers selected
    	   
		    final byte border = 14;  //border for display
		    JFrame f = new JFrame("Sudoku v3.0");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        BufferedImage image = null;
	            try {
					image = ImageIO.read(new File("sudoku.png"));
				} catch (IOException e) {
				}//end of try/catch
	        f.setResizable(false);  //not to be resized
	        f.setIconImage(image);
		    f.setSize(WindowWidth, WindowHeight);  //size fixed by size of display and borders
		    f.setLocation(0, 0); //start top left
		    f.setLayout(new BorderLayout());  //north south east west and centre		   
		    
		    f.add(new SPanel(new Dimension(WindowWidth,border)),  BorderLayout.NORTH);
		    f.add(new SPanel(new Dimension(WindowWidth,border)),  BorderLayout.SOUTH);
		    f.add(new SPanel(new Dimension(border,WindowHeight)),   BorderLayout.EAST);
		    f.add(new SPanel(new Dimension(0,WindowHeight)),   BorderLayout.WEST);  //set the borders

	        DisplayPanel dp =new  DisplayPanel();
	        dp.setBackground(Color.WHITE);  //set the background of the sudoku display black
	        f.add(dp, BorderLayout.CENTER);  //add the sudoku display panel
	      
	        f.setVisible(true);	
	 }//end of show gui method

	/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	ShowGUI(); 
            }			
        });  //end of run()
    }//end of main
    */
}//end of MySudoku class

/* -- Concept Understanding 
A sudoku is a special arrangement of a 9 X 9 box of the numbers 123456789.

641.597.238    each row has 123456789
273.681.945 
589.324.716    each column has 123456789
........... 
836.952.471    each 3X3 box has 123456789
127.438.569 
954.716.382
........... 
492.863.157    when we start with a blank sudoku each number can be 12345678.
318.275.694    So to describe a sudoku we must have 9x9x9 = 729 characters.
765.149.823

In the program we hold a sudoku step in:   byte sudoku[729][82];  //sudoku array

If we print out the start sudoku at step 0 we will see:


123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
 
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
 
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789


The numbering for this array is as follows:

X...0.........1.........2..........3........4.........5........6..........7.........8....   Y
                                                                                            .
0                                                                                       80  .
.                                                                                       .   .
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   0  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   1  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   2  
                                                                                            .
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   3    
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   4  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   5  
                                                                                            . 
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   6  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   7  
123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789   8
.                                                                                        .  .
648                                                                                      728



This program works by selecting a random X(0 to 8) a random Y(0 to 8)
and a random number(1 to 9).

The very first number will fit into this X and Y position.
To show that it has been selected this number has 10 added to it so it will be 11 to 19.
Each other number in that position will be set to zero because it can no longer be selected.

So if the selected number is 5 we will see:  0000150000 instead of 123456789.

Next we need to set each number 5 in the selected X row to 0 to show it can no longer be selected.
We need to repeat for the selected Y column and the selected 3X3 box.

With subsequent numbers we have to check that it has not been selected(11 to 19) or that
it cannot be selected(0).

As we get further along in the process we will find that a number will stand alone.
That is it will be the only one which can be selected. This could be 00005000 showing that
only the 5 can be selected.
The program will select any number which stands alone.

On almost every try with random numbers we will get to the stage when one number has no
selections possible. It will be 000000000.
We call this a blank and we have reached a dead end. This sudoku cannot be solved.

We start again from the start sudoku.
Eventually we will get to 81 steps, a complete sudoku without blanks.
This will be a completely random sudoku solution.

*/
