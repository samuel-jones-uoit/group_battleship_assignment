package battleship;
/**
 *   Class Name: Coordinates
 *   Class Description:
 *   Coordinates for the battleship board
 */
public class Coordinates {
    private static final char[] rowChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    private final int row;
    private final int column;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   int row:
     *      row index
     *   int column:
     *      column index
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public Coordinates(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   char row:
     *      row name
     *   int column:
     *      column index
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public Coordinates(char row, int column){
        this.row = convertCharToRow(row);
        this.column = column;
    }

    /**
     *   Method Name: getRow
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: int
     */
    public int getRow(){
        return this.row;
    }

    /**
     *   Method Name: getColumn
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: int
     */
    public int getColumn(){
        return this.column;
    }

    /**
     *   Method Name: convertRowToChar
     *   Method Parameters:
     *   int row:
     *      row index
     *   Method Description:
     *   Return corresponding character
     *   Method Return: char
     */
    public static char convertRowToChar(int row){
        return rowChars[row];
    }

    /**
     *   Method Name: convertCharToRow
     *   Method Parameters:
     *   char row:
     *      row name
     *   Method Description:
     *   Return corresponding row index
     *   Method Return: int
     */
    public static int convertCharToRow(char row){
        // find the index by searching
        for (int i = 0; i < rowChars.length; i++){
            if (rowChars[i] == row){
                return i;
            }
        }
        // not found
        return -1;
    }

    /**
     *   Method Name: fromString
     *   Method Parameters:
     *   String coordinates:
     *      Coordinates represented in a string
     *   Method Description:
     *   Create coordinates from a string
     *   Method Return: Coordinates
     */
    public static Coordinates fromString(String coordinates){
        return new Coordinates(Coordinates.convertCharToRow(coordinates.charAt(0)), Integer.parseInt(coordinates.substring(1)));
    }

    /**
     *   Method Name: toString
     *   Method Parameters: None
     *   Method Description:
     *   Create a string representation of coordinates
     *   Method Return: String
     */
    public String toString(){
        String newString = "";
        newString += convertRowToChar(this.row);
        newString += Integer.toString(this.column);
        return newString;
    }
}
