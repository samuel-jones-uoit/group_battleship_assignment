package battleship;

public class Coordinates {
    private static final char[] rowChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    private int row;
    private int column;
    public Coordinates(int row, int column){
        this.row = row;
        this.column = column;
    }

    public Coordinates(char row, int column){
        this.row = convertCharToRow(row);
        this.column = column;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public static int convertCIndex(int index){
        return index + 1;
    }

    public static int convertPIndex(int index){
        return index - 1;
    }
    public static char convertRowToChar(int row){
        return rowChars[row];
    }
    public static int convertCharToRow(char row){
        for (int i = 0; i < rowChars.length; i++){
            if (rowChars[i] == row){
                return i;
            }
        }
        // not found
        return -1;
    }
}
