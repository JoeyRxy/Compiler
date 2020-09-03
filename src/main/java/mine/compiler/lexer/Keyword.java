package mine.compiler.lexer;

public class Keyword extends Token {
    private final String keyword;

    public Keyword(String keyword) {
        super(Tag.KEYWORD);
        this.keyword = new String(keyword);
    }

    public String getKeyword() {
        return keyword;
    }
}