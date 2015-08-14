package Stringstuff2;

public class PasswordReplace {

    /**
     * @param args
     */
    public static void main(String[] args) {
        StringBuilder sbInputStream = new StringBuilder("{\"loginName\":\"boom\",\"password\":\"sdfsdfg234234234dfgdfgdfgdfg\"}");
        System.out.println("Before: " + sbInputStream.toString());
        int startPassword = sbInputStream.indexOf("\"password\":\"") + 12;
        int endPassword = sbInputStream.indexOf("\"", startPassword);
        sbInputStream.replace(startPassword, endPassword, "");
        System.out.println("After:  " + sbInputStream.toString());
    }

}
