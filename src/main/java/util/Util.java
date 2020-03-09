package util;

public class Util {
    public static int naturalModulus(int number, int modulus){
        return (number%modulus)+(number<0?modulus:0);
    }
}
