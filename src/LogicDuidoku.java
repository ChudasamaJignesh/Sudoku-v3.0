
public class LogicDuidoku {
    private static final int SIZE = 4;
    private int[][] board;
    private int x;
    private int y;


    public LogicDuidoku(){
        board= new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

    }


    public String getHelpClassicSudoku(int x,int y){
        StringBuilder possible= new StringBuilder();
        for(int i=1;i<5;i++){
            if (isOk(x, y, i))
                possible.append(i).append(",");
        }
        return possible.toString();

    }



    private boolean isInRow(int row, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == number)
                return true;

        return false;
    }

    private boolean isInCol(int col, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[i][col] == number)
                return true;

        return false;
    }

    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 2;
        int c = col - col % 2;

        for (int i = r; i < r + 2; i++)
            for (int j = c; j < c + 2; j++)
                if (board[i][j] == number)
                    return true;

        return false;
    }

    private boolean isOk(int row, int col, int number) {
        return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
    }

    public boolean addMove(int row, int col, int number){
        if( isOk(row,col,number) && number<5 && number>0 && board[row][col]==0){
            board[row][col]=number;
            return true;
        }else {
            //pcMove();
            return false;
        }
    }

    public boolean pcMove() {
        //boolean b = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int num = 1; num <= SIZE; num++) {
                    if (isOk(i, j, num)  && board[i][j] == 0) {
                        board[i][j] = num;
                        x=i;
                        y=j;
                        return true;

                    }

                }

            }

        }

        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean Check() {
        boolean b = false;
        boolean solved=true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int num = 1; num <= SIZE; num++) {
                    if (isOk(i, j, num) && board[i][j] == 0) {
                        solved=false;
                        b = true;
                        break;
                    }

                }
                if(b)
                    break;
            }
            if(b)
                break;
        }
        return solved;
    }

    public int[][] getBoard() {
        return board;
    }
}
