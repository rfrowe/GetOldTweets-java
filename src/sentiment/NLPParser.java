package sentiment;

import java.io.StringReader;
import java.util.*;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.Tree;

/**
 * Description
 *
 * @author Ryan
 */

public class NLPParser {
    private static NLPParser SINGLETON;
    private final static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";

    public static NLPParser getSingleton() {
        if(SINGLETON == null) {
            synchronized (NLPParser.class) {
                if (SINGLETON == null) {
                    SINGLETON = new NLPParser();
                }
            }
        }

        return SINGLETON;
    }
    private final TokenizerFactory<CoreLabel> tokenizerFactory =
            PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");


    private final LexicalizedParser parser =
            LexicalizedParser.loadModel(PCG_MODEL);

    public Tree parseTree(String str) {
        return parser.apply(tokenize(str));
    }

    private List<CoreLabel> tokenize(String str) {
        Tokenizer<CoreLabel> tokenizer = tokenizerFactory.getTokenizer(new StringReader(str));
        return tokenizer.tokenize();
    }

    public List<SentiWord> parse(String text) {
        Tree tree = parseTree(text);

        List<Tree> leaves = tree.getLeaves();

        List<SentiWord> words = new ArrayList<SentiWord>();

        for(Tree leaf : leaves) {
            Tree parent = leaf.parent(tree);

            POS pos = null;

            try {
                switch(parent.label().value().charAt(0)) {
                    case 'J':
                        pos = POS.ADJECTIVE;
                        break;
                    case 'V':
                        pos = POS.VERB;
                        break;
                    case 'N':
                        pos = POS.NOUN;
                        break;
                    case 'R':
                        pos = POS.ADVERB;
                }
            } catch (NullPointerException e) {
                // Do nothing
            }

            words.add(new SentiWord(leaf.label().value(), pos));
        }

        return words;
    }

    public static void main(String[] args) {
        String str = "The Lying NYTimes is at it again! Don’t believe a word. Sad!";
        NLPParser parser = new NLPParser();
        Tree tree = parser.parseTree(str);

        List<Tree> leaves = tree.getLeaves();
        // Print words and Pos Tags
        for(Tree leaf : leaves) {
            Tree parent = leaf.parent(tree);
            System.out.print(leaf.label().value() + "-" + parent.label().value() + " ");

            System.out.println();
        }
        System.out.println();

        List<SentiWord> l = parser.parse(str);

        for(SentiWord s : l) {
            System.out.println(s);
        }
    }
}
