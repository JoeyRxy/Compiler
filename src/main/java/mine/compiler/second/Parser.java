package mine.compiler.second;

import java.io.*;

/**
 * Parser
 */
public class Parser {

    static int lookahead;

    public Parser() throws IOException {
        lookahead = System.in.read();
    }

    public void expr() throws IOException {
        term();
        while (true) {
            if (lookahead == '+') {
                match('+');
                term();
                System.out.print('+');
            } else if (lookahead == '-') {
                match('-');
                term();
                System.out.print('-');
            } else
                return;
        }
    }

    public void term() throws IOException {
        if (Character.isDigit(lookahead)) {
            System.out.print((char) lookahead);
            match(lookahead);
        } else
            throw new Error("syntex error");
    }

    private void match(int t) throws IOException {
        if (lookahead == t)
            lookahead = System.in.read();
        else
            throw new Error("syntex error");
    }

    public static void main(String[] args) throws IOException {
        Parser t = new Parser();
        t.expr();
        System.out.println();
    }
}