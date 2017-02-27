package model;

/**
 * Description
 *
 * @author Ryan
 */

public enum Feeds {
    TOP(null), LATEST("tweets"), PHOTOS("images"), VIDEOS("videos");

    private final String str;

    Feeds(String str) {
        this.str = str;
    }

    public String token() {
        return str;
    }
}
