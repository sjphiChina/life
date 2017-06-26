package sjph.life.test.basic;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author shaohuiguo
 *
 */
public class GeneralTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // TODO Auto-generated method stub
        String post = "中文加上English，看看如何转化";
        ByteBuffer array = Charset.forName("UTF-8").encode(post);
        byte[] ptext;
        try {
            ptext = post.getBytes("UTF-8");

            System.out.println(post);
            System.out.println(new String(ptext));
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String encodedString = URLEncoder.encode(post, "UTF-8");
        String decodedString = URLDecoder.decode(encodedString, "UTF-8");
        System.out.println(encodedString);
        System.out.println(decodedString);
        // String encodedContent = new String(array, Charsets.UTF_8);

        // System.out.println(encodedContent);

        String xmlstring = "中文加上English，看看如何转化";
        String utf8string = convertToUTF8(xmlstring);
        System.out.println(utf8string);
        // for (int i = 0; i < utf8string.length(); ++i) {
        // System.out.printf("%X", (int) utf8string.charAt(i));
        // }

        String newStr = convertFromUTF8(utf8string);
        System.out.println(newStr);
    }

    // convert from UTF-8 -> internal Java String format
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    // convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        }
        catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}
