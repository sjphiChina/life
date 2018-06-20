package sjph.life.shorturl.model;

import java.io.Serializable;

public class Shorturl implements Serializable {

    private long   id;
    private String shorturl;
    private String longurl;

    /**
     * 
     */
    public Shorturl() {
    }

    /**
     * @param shorturl
     * @param longurl
     */
    public Shorturl(String shorturl, String longurl) {
        this.shorturl = shorturl;
        this.longurl = longurl;
    }

    /**
     * @param id
     * @param shorturl
     * @param longurl
     */
    public Shorturl(long id, String shorturl, String longurl) {
        this.id = id;
        this.shorturl = shorturl;
        this.longurl = longurl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShorturl() {
        return shorturl;
    }

    public void setShorturl(String shorturl) {
        this.shorturl = shorturl;
    }

    public String getLongurl() {
        return longurl;
    }

    public void setLongurl(String longurl) {
        this.longurl = longurl;
    }

}
