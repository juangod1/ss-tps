package util;

public class Util {
    public static int naturalModulus(int number, int modulus){
        return (number%modulus)+(number<0?modulus:0);
    }

    public static boolean[][][] clone3dArray(boolean[][][] arr, int boardHeight, int boardWidth, int boardDepth){
        boolean[][][] newArray = new boolean[boardHeight][boardWidth][boardDepth];

        for(int i=0;i<boardHeight;i++){
            for(int j=0;j<boardWidth;j++){
                for (int k=0;k<boardDepth;k++){
                    newArray[i][j][k] = arr[i][j][k];
                }
            }
        }
    return newArray;
    }
}
