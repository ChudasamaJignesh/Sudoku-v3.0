/**
 * \brief
 * @author Jignesh Chudasama
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * This class creates the center panel.
 * It has 6 buttons on the left and a paint region for the sudoku display.
 */

public class DisplayPanel extends JPanel implements ActionListener //create display panel
{
    private static final long serialVersionUID = 1L;
    private int DisplayWidth = 557; //sudoku display its 557 pixels wide
    private int DisplayHeight = 580; //sudoku display its 580 pixels high
    private int ButtonsWidth = 200; //button panel its 200 pixels wide
    private final Color WS = new Color(0xf5, 0xf5, 0xf5);  //White Smoke 
    private final Color MB = new Color(0x00, 0x00, 0xcd);  //Medium blue
    private final Color P = new Color(0x80,0, 0x80);  //purple blank number 
   
    public DisplayPanel()   //construct the sudoku display panel
    {       
        addMouseListener(new MouseAdapter() //we listen for mouse clicks on this panel
        {
            public void mousePressed(MouseEvent e) 
            {           	
                selectNumber(e.getX(),e.getY());   //the called method on mouse click         	
            }//end of mouse select
        });//end of mouse listener
        this.setLayout(new BorderLayout());
        
        JPanel pb = new JPanel();   //create the button panel
        pb.setPreferredSize(new Dimension(ButtonsWidth,DisplayHeight));
        pb.setBackground(WS);
        
        FlowLayout FL = new FlowLayout();
        FL.setVgap(55);
        FL.setHgap(100);  //set the flow layout to give  symmetric display
        pb.setLayout(FL);
        SButton SS = new SButton(" Solved Sudoku ", "SS");
        SS.addActionListener(this);
        pb.add(SS);
        SButton GBS = new SButton(" Undo ", "GBS");
        GBS.addActionListener(this);
        pb.add(GBS);  
        SButton ES = new SButton(" Easy ", "ES");
        ES.addActionListener(this);
        pb.add(ES);
        SButton MS = new SButton(" Medium ", "MS");
        MS.addActionListener(this);
        pb.add(MS);
        SButton HS = new SButton(" Hard ", "HS");
        HS.addActionListener(this);
        pb.add(HS);
        SButton CS = new SButton(" Custom Sudoku", "CS");
        CS.addActionListener(this);
        pb.add(CS);
        
        this.add(pb,BorderLayout.WEST); //add the push button panel to the display panel       
    }//end of constructor
       
    private void selectNumber(int x, int y)   //called method on mouse click
    {
    	int NumberPosition[] = {3,63,124,187,248,309,372,433,494}; //number position
    	final byte pSNumberY = 19;  //the  spacing for the numbers
    	if( x < ButtonsWidth + NumberPosition[0])
    		return; //exit method if the mouse not in the sudoku area
    	x -= ButtonsWidth - NumberPosition[0];  //reset x value
    	
    	byte count;
    	byte Xposition = 0; //the position of the selected box 0 - 8 in X
    	for(count = 0; count < 9; count++) //check to find position
    	{
    		if(x > NumberPosition[count])
    			Xposition = count;  //it must be this(or the next)
    	}//end of get x position
    	
    	byte Yposition = 0; //the position of the selected box 0 - 8 in X
    	for(count = 0; count < 9; count++) //check to find position
    	{
    		if(y > NumberPosition[count])
    			Yposition = count;  //it must be this(or the next)
    	}//end of get y position
    	byte position = (byte) (Xposition + Yposition*9); //the number position 0 - 80
    	
    	byte Xnumber = 0; //the number in x position 123 or 456 or 789
    	x -=  NumberPosition[Xposition];  //reset the x
    	for(count = 0; count < 3; count++) //check to find position
    	{
    		if(x >  pSNumberY*count)
    			Xnumber = count;  //it must be this(or the next)
    	}//end get the x number
    	
    	byte Ynumber = 0; //the number in x position 123 or 456 or 789
    	y -=  NumberPosition[Yposition];  //reset the x
    	for(count = 0; count < 3; count++) //check to find position
    	{
    		if(y >  pSNumberY*count)
    			Ynumber = count;  //it must be this(or the next)
    	}//end get y number
    	byte number = (byte) (Xnumber + Ynumber*3); //get the selected number 0 - 8
    	
    	MySudoku.step = Smethods.select(MySudoku.sudoku, number, position, MySudoku.step);
        repaint(ButtonsWidth,0, DisplayWidth,DisplayHeight);  //call the paint method
                 
    }//end of select number
    
    public Dimension getPreferredSize()  //set the preferred size of display panel
    {
        return new Dimension(DisplayWidth + ButtonsWidth,DisplayHeight);
    }//end of get dimension of DisplayPanel(button panel + sudoku display)
    
     protected void paintComponent(Graphics g)  //called whenever the display panel needs painting
    {
    	final byte Foot = 24; //the height of the foot for sudoku
     	final byte NumberX = 11;  //the X offset for the string selected display
     	final byte NumberY = 54;  //the Y offset for the string selected display
     	final byte blanksize = 59;  //the size of the can't select square
     	final byte pNumberX = 4;  //the X offset for the string pencil display
     	final byte pNumberY = 18;  //the Y offset for the string pencil display
     	final byte pSNumberX = 20;  //the X spacing for the string pencil display
     	final byte pSNumberY = 19;  //the Y spacing for the string pencil display
     	final int FootMessageX = 96;  //the X offset for foot message
     	final int FootMessageY = 574;  //the X offset for foot message
     	final int FootNumberX = 211;  //X offset for step number
     	final int FootNumberY = 574;  //Y offset for step number
     	//Grid lines for the display
     	int BigLines[] = {0, 184, 369, 554, 577};  //block of 3 x 3 numbers  3 pixels wide
     	int SmallLines[] = {62, 123, 247, 308, 432, 493};  //each number   1 pixel wide
     	int NumberPosition[] = {3,63,124,187,248,309,372,433,494}; //number display
     	Font fontSelected = new Font("SansSerif", Font.ROMAN_BASELINE, 70);  //selected number
        Font fontFoot = new Font("SansSerif", Font.ROMAN_BASELINE, 20);  //the foot text
        Font fontPencil = new Font("SansSerif", Font.ROMAN_BASELINE, 20);  //pencil lines
     	
        super.paintComponent(g); //paint the component's JPanel     
        g.setColor(MB);
        g.setFont(fontPencil);
        //g.drawString(String.valueOf(numberops.number),squareX,squareY); //the sudoku step number
        
      //horizontal lines
        byte count;  //counter for position 0 to 80
        for(count = 0; count < 5; count++)
        g.fillRect(0, BigLines[count], DisplayWidth + ButtonsWidth, 3);
        for(count = 0; count < 6; count++)
        g.drawLine(0, SmallLines[count], DisplayWidth + ButtonsWidth, SmallLines[count]);
      //vertical lines
        g.fillRect(BigLines[0] + ButtonsWidth , 0, 3, DisplayHeight);
        g.fillRect(BigLines[1] + ButtonsWidth , 0, 3, DisplayHeight - Foot);
        g.fillRect(BigLines[2] + ButtonsWidth , 0, 3, DisplayHeight - Foot);
        g.fillRect(BigLines[3] + ButtonsWidth , 0, 3, DisplayHeight);
        for(count = 0; count < 6; count++)
        g.drawLine(SmallLines[count] + ButtonsWidth, 0, SmallLines[count] + ButtonsWidth, DisplayHeight -Foot);
        //foot text
        g.setFont(fontFoot);
        g.drawString(" Step No: ", FootMessageX + ButtonsWidth, FootMessageY);
        g.drawString(String.valueOf(MySudoku.step), FootNumberX + ButtonsWidth, FootNumberY);
        byte numbercount;
        for(numbercount = 0; numbercount < 81; numbercount++)
        {
        g.setColor(MB);  //reset color to MB
        byte zeros = 0; //count the number of zeros in the number(9 pencils numbers)
        byte outercount;  //outside counter
        for(outercount = 0; outercount < 3; outercount++)
        {
        for(count = 0; count < 3; count++)
        {
        byte pencilnumber = MySudoku.sudoku[count + outercount*3 + numbercount*9][ MySudoku.step];
        if(pencilnumber > 0)    //do we display this letter
        {
        if(pencilnumber < 10)
        {
        g.setFont(fontPencil);
        g.drawString(String.valueOf(pencilnumber ), NumberPosition[numbercount%9] + (count*pSNumberX) + pNumberX + ButtonsWidth, NumberPosition[numbercount/9] + outercount*pSNumberY + pNumberY);
        } //draw the pencil number
        else
        {
        g.setFont(fontSelected);
        g.drawString(String.valueOf(pencilnumber - 10), NumberPosition[numbercount%9] + ButtonsWidth + NumberX, NumberPosition[numbercount/9] + NumberY);
        } //draw the selected number
        }//end of we display this number
        else
        	zeros += 1; //we have a zero don't display and count it
        }//end of draw 3 pencil count
        }//end of draw  9 pencils outercount
        if(zeros == 9)
        {
        	g.setColor(P);  //show purple square can't select any number
        	g.fillRect(NumberPosition[numbercount%9] + ButtonsWidth, NumberPosition[numbercount/9], blanksize, blanksize);
        }
        }//end of draw the 81 number positions numbercount
    }//end of paint sudoku display  

	
	public void actionPerformed(ActionEvent e)  //call method for push button selected
	{		
		if(e.getActionCommand() == "CS")
		MySudoku.step = 0;   //nothing is selected
		else if(e.getActionCommand() == "HS")
		{
			 Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
			 MySudoku.step = 25;  //need to fill in 56 steps
		}
		else if(e.getActionCommand() == "MS")
		{
			 Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
			 MySudoku.step = 35;  //need to fill in 46 steps
		}
		else if(e.getActionCommand() == "ES")
		{
			 Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
			 MySudoku.step = 45;  //need to fill in 36 steps
		}	
		else if(e.getActionCommand() == "SS")
		{
			Smethods.trysudoku(MySudoku.sudoku, MySudoku.step);  //solve this one
		}
		else if(e.getActionCommand() == "GBS")
		{
			if(MySudoku.step > 0)
			MySudoku.step -= 1; //go back 1 step
		}
	    
		repaint(ButtonsWidth,0, DisplayWidth,DisplayHeight);  //call the paint method
	}//end of button pressed method
	
}//end of my display panel class
 
 
/* -- Understanding of project.
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
 
 


