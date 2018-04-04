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
     * @param text the content to be encoded
     * @param encodingSet the character set
     * @return the encoded text
     * @throws UnsupportedEncodingException if cannot encode
     */
    public static String encodeText(String text, String encodingSet)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(text, encodingSet);
    }

    /**
     * @param text the content to be decoded
     * @param decodingSet the character set
     * @return the decoded text
     * @throws UnsupportedEncodingException if cannot decode
     */
    public static String decodeText(String text, String decodingSet)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(text, decodingSet);
    }
}
