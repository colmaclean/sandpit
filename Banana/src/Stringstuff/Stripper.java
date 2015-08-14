package Stringstuff;

public class Stripper {
    public static void main (String[] args) {
        String newUrl = "static.preprod.productpictures/6e/large/10359/869/2/lavera-Body-SPA-Badeoel-Rose-Garden-100-ml.jpg";
        System.out.println("Before: " + newUrl);
        newUrl = newUrl.substring(newUrl.indexOf("productpictures/"));
        System.out.println("After:  " + newUrl);
    }
}
