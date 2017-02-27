package sentiment;

/**
 * Description
 *
 * @author Ryan
 */

public enum POS {
    ADJECTIVE("a"), NOUN("n"), ADVERB("r"), VERB("v");

    private String str;

    POS(String str) {
        this.str = str;
    }

    public String token() {
        return str;
    }
}
