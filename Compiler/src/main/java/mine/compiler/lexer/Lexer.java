package mine.compiler.lexer;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Lexer
 */
public class Lexer {

    public int line = 1;
    private char peek = ' ';
    private Hashtable<String, Word> words = new Hashtable<>();

    void reserve(Word t) {
        words.put(t.lexeme, t);
    }

    public Lexer() {
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
    }

    public Token scan() throws IOException {
        do {
            if (peek == ' ' || peek == '\t')
                continue;
            else if (peek == '\n')
                line++;
            else
                break;
        } while (true);
        if (Character.isDigit(peek)) {// 数字
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                peek = (char) System.in.read();
            } while (Character.isDigit(peek));
            return new Num(v);
        }
        if (Character.isLetter(peek)) {// 变量（标识符）
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                peek = (char) System.in.read();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            Word w = words.get(s);
            if (w != null)
                return w;

            w = new Word(Tag.ID, s);
            words.put(s, w);
            return w;
        }
        Token t = new Token(peek);
        peek = ' ';
        return t;
    }
}