/**
 * \brief
 * @author Jignesh Chudasama
 */

/**
 * This class contains all the methods needed to generate and solve a sudoku.
 */

public class Smethods 
{
	public static void start(byte[][] sudoku)
	{
	int count = 0;
	for(count = 0; count < 729; count++)
		sudoku[count][0] = (byte) (1 + (count % 9));
	}//end of start. 1-9 in 81 spots
	
	public static void trysudoku(byte[][] sudoku, byte startstep)
	{
		java.util.Random generator = new java.util.Random(System.currentTimeMillis());
		byte step = startstep;
		int trys = 0;  //total of the tries to get a complete solution
		do    //keep random try's until have a complete random sudoku
		{
			
		trys += 1;  //count the tries
		boolean noblanks = true; //blank = 000000000 sudoku in a dead end
		step = startstep;
		
	    while((step < 81) && (noblanks))  //try a random selection run
		{
		byte number = (byte) generator.nextInt(9);  // 0 to 8
		byte position = (byte) generator.nextInt(81);  //0 to 80
		step = Smethods.select(sudoku,number,position,step); //do we have a step
		
		boolean standalone = false;
		do   //check to find any numbers which stand alone and must be selected
		{
		standalone = false;
		byte count;  //counter for position 0 to 80
		byte incount;  //counter for number 0 to 8
		for(count = 0; count < 81; count++)
		{
			byte nzeros = 0; //we start with no zero count
			for(incount = 0; incount < 9; incount++)
			{
			if(sudoku[count * 9 + incount][step] == 0)
				nzeros += 1;  //count zeros
			else
				number = (byte) (sudoku[count * 9 + incount][step] - 1);  //Offset to 0-8
			if(nzeros == 9)
				noblanks = false;  //we have a blank a dead end
			}//end of checked number			
			if((nzeros == 8) && (number < 10))
    			{
			step = Smethods.select(sudoku,number,count,step);  //we have a step
			standalone = true;
			}//end of select stand alone
			}//end of checked 81 numbers
		}//end of select stand alone numbers
		while(standalone);
		
		}//end of a random try
	    MySudoku.step = step;
		}//end of try until we have a complete sudoku
		while((step != 81) && (trys < 500));  //should never need more than 500
				
	}//end of try sudoku
	
	public static byte select(byte[][] sudoku, byte number, byte position, byte step)
	{
	if((sudoku[position*9 + number][step] == 0) || (sudoku[position*9 + number][step] > 9))
		return step;//end of number not possible or is selected
	
    step += 1; // we can select so write this step to the sudoku array
	int count = 0;
	for(count = 0; count < 729; count++)
		sudoku[count][step] = sudoku[count][step - 1]; //copy existing to next step
	for(count = 0; count < 9; count++)
		sudoku[position*9 + count][step] = 0;	//Can't select any in box
	
	byte row =   (byte) (position/9);
	for(count = 0; count < 9; count++)
		sudoku[row * 81 + count * 9 + number][step] = 0; //horizontal row
	
	byte column =   (byte) (position%9);
	for(count = 0; count < 9; count++)
		sudoku[column * 9 + count * 81 + number][step] = 0;  //vertical row
	
	int brow =  (position/27)*243; //row block 0f 3
	column = (byte) (((position%9)/3)*27);  //Column block of 3
	byte incount;
	for(incount = 0; incount < 3; incount++)
	{
	for(count = 0; count < 3; count++)
		sudoku[brow + column + count * 9 + incount * 81 + number ][step] = 0;  //box of 3 x 3
	}//end of 3 x 3 box
	//we have selected one number
	sudoku[position*9 + number][step] = (byte) (number + 11); //selected now 11 to 19
	return step;
	}//end of select a number
	
}//end of class

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
