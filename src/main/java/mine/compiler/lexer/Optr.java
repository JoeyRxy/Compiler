package mine.compiler.lexer;

import java.util.HashSet;
import java.util.Set;

public class Optr extends Token {
    public static Set<String> optrSet;
    static {
        optrSet = new HashSet<>();
        optrSet.add("=");

        optrSet.add("+");
        optrSet.add("-");
        optrSet.add("*");
        optrSet.add("/");
        optrSet.add("%");
        // todo: what about "(", ")", "{", "}"?
        optrSet.add(">");
        optrSet.add(">=");
        optrSet.add("<");
        optrSet.add("<=");
        optrSet.add("==");
        optrSet.add("!=");
        optrSet.add("!");
        optrSet.add("&&");
        optrSet.add("||");
        //
        optrSet.add("~");
        optrSet.add("!");
        optrSet.add("&");
        optrSet.add("|");
        optrSet.add("<<");
        optrSet.add(">>");
        optrSet.add("^");
    }
    public final String opt;

    public Optr(String opt) {
        super(Tag.OPTR);
        if (!optrSet.contains(opt))
            throw new Error("unsupported operator!");
        this.opt = new String(opt);
    }

    public String getOpt() {
        return opt;
    }

}