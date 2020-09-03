package mine.compiler.lexer;

public class Type extends Token {

    public final String type;

    public Type(String type) {
        super(Tag.TYPE);
        this.type = new String(type);
    }

    public String getType() {
        return type;
    }

}