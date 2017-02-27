package sentiment;

/**
 * Description
 *
 * @author Ryan
 */

public class SentiWord {
    private final String word;
    private final POS pos;

    public SentiWord(String word, POS pos) {
        this.word = word;
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }

    public POS getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "SentiWord{" +
               "word='" + word + '\'' +
               ", pos=" + pos +
               "(" + (pos != null ? pos.token(): "") + ")}";
    }
}
