package nulla;

import java.text.SimpleDateFormat;

public class NullaCheck {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public static void main (String... args) {
        System.out.println("Convert a null date: " + sdf.format(null));
    }
    
}
