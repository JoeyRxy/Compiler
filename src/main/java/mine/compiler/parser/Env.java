package mine.compiler.parser;

import java.util.HashMap;

import mine.compiler.lexer.Word;

/**
 * 符号表
 */
public class Env {
    private HashMap<Word, Symbol> map;
    protected Env prev;

    public Env(Env pEnv) {
        map = new HashMap<>();
        prev = pEnv;
    }

    public void put(Word identifier, Symbol symbol) {
        map.put(identifier, symbol);
    }

    public Symbol get(Word identifier) {
        return map.get(identifier);
    }

}