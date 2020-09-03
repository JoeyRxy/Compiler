package mine.compiler.lexer;

import java.util.Objects;

/**
 * Word
 */
public class Word extends Token {
    public final String lexeme;

    public Word(Tag tag, String lexeme) {
        super(tag);
        this.lexeme = new String(lexeme);
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexeme);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Word other = (Word) obj;
        return Objects.equals(lexeme, other.lexeme);
    }

}