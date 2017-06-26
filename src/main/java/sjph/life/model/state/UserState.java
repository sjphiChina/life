package sjph.life.model.user;

/**
 * @author shaoguo
 *
 */
public enum UserState {

    //@formatter:off
    ACTIVE('A', "ACTIVE"),
    INACTIVE('I', "INACTIVE"),
    DELETED('D', "DELETED"),
    BLOCKED('B', "BLOCKED");
    //@formatter:on

    // Fields
    private char   charValue;
    private String description;

    private UserState(char charValue, String description) {
        this.charValue = charValue;
        this.description = description;
    }

    /**
     * @return
     */
    public char getCharValue() {
        return charValue;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

}
