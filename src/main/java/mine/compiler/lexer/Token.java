package mine.compiler.lexer;

/**
 * Token
 */
abstract public class Token {

    private final Tag tag;

    public Token(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }
}