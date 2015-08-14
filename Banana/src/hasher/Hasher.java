package hasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Hasher {
    private String md5(TreeMap<String, String> params) {
        MessageDigest digest;
        try {

         Set<String> keys = new TreeSet<String>(params.keySet());
         StringBuilder sb = new StringBuilder();
         String secret = "sinnes";
         sb.append(secret).append(":");
         Iterator<String> it = keys.iterator();
         while (it.hasNext()) {
          String key = it.next();
          sb.append(params.get(key).replace("%20", " "));
          if (it.hasNext()) {
           sb.append(":");
          }
         }
         String in = sb.toString();
         System.out.println("string for hash " + in);
         digest = MessageDigest.getInstance("MD5");
         digest.reset();
         MessageDigest md5 = MessageDigest.getInstance("MD5");
         md5.reset();
         return toHexString(md5.digest(in.getBytes()));
        } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
        }
        return null;
       }

       public static String toHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
         sb.append(toHex(bytes[i] >> 4));
         sb.append(toHex(bytes[i]));
        }

        return sb.toString();
       }

       private static char toHex(int nibble) {
        final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
          '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        return hexDigit[nibble & 0xF];
       }
public static void main(String[] args) {
           Hasher hasher = new Hasher();
           TreeMap tm = new TreeMap<String, String>();
           //tm.put("session.id", "kbyyqsnpjb");
           int i=0;
           while ( i < args.length) {
               tm.put(args[i],args[i+1]);
               i=i+2;
           }
          // tm.put("ean"," 40 0222 1016552");
           
          //tm.put("login","calle.billger@allyouneed.com");
           //tm.put("password", "123qweasd");
           
           System.out.print(hasher.md5(tm));
           
       }       

}

