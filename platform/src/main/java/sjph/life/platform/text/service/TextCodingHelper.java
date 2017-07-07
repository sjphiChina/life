package sjph.life.platform.text.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Using {@link URLEncoder} for encoding and {@link URLDecoder} for decoding based on character set
 * specified.
 * 
 * @author shaohuiguo
 *
 */
public class TextCodingHelper {

    /**
     * @param text
     * @param encodingSet
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeText(String text, String encodingSet)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(text, encodingSet);
    }

    /**
     * @param text
     * @param decodingSet
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeText(String text, String decodingSet)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(text, decodingSet);
    }
}
