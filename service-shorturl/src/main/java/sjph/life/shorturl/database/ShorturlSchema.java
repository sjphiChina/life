package sjph.life.shorturl.database;

public enum ShorturlSchema {

    ID,
    SHORTURL,
    LONGURL,
    CREATED_DT
    ;
    public static final String TABLE_NAME = "/* ?. */" + "shorturl";

    public static final String FAKE_RECORD_VALUE = "-1";
}
