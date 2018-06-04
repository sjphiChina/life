package sjph.life.util.time;

/**
 * @author Shaohui Guo
 *
 */
public enum LifeDateFormat {

    //@formatter:off
    YMDHMSAZ("yyyy-MM-dd HH:mm:ss a, z"),
    YMDHMSA("yyyy-MM-dd HH:mm:ss a"),
    YMDHMS("yyyy-MM-dd HH:mm:ss"),
    YMD("yyyy-MM-dd"),
    HMS("HH:mm:ss");
    //@formatter:on

    private String format;

    private LifeDateFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
