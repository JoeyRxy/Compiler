package mine.compiler.lexer;

import java.util.Hashtable;

/**
 * Lexer
 */
public class Lexer {

	public int line = 1;
	private Hashtable<String, Word> words = new Hashtable<>();

	private int idx;
	private String expr;

	void reserve(Word word) { // 保留字
		words.put(word.lexeme, word);
	}

	public Lexer(String expr) {
		this.expr = new String(expr);
		reserve(new Word(Tag.TRUE, "true"));
		reserve(new Word(Tag.FALSE, "false"));
		reserve(new Word(Tag.TYPE, "int"));
		reserve(new Word(Tag.TYPE, "double"));
		reserve(new Word(Tag.TYPE, "bool"));
		reserve(new Word(Tag.TYPE, "char"));
		reserve(new Word(Tag.TYPE, "long"));
		reserve(new Word(Tag.TYPE, "void"));
		reserve(new Word(Tag.KEYWORD, "for"));
		reserve(new Word(Tag.KEYWORD, "while"));
		reserve(new Word(Tag.KEYWORD, "continue"));
		reserve(new Word(Tag.KEYWORD, "break"));
		reserve(new Word(Tag.KEYWORD, "if"));
		reserve(new Word(Tag.KEYWORD, "else"));
		reserve(new Word(Tag.KEYWORD, "return"));
	}

	private void skipUseless() {
		do {// 空格等
			if (expr.charAt(idx) == ' ' || expr.charAt(idx) == '\t')
				++idx;
			else if (expr.charAt(idx) == '\n') {
				++line;
				++idx;
			} else if (expr.charAt(idx) == '/') {// comments
				if (expr.charAt(++idx) == '/') {// line comment
					while (expr.charAt(++idx) != '\n')
						;
					++line;
					++idx;
				} else if (expr.charAt(idx) == '*') {
					++idx;
					do {
						if (expr.charAt(++idx) == '\n')
							++line;
					} while (!(expr.charAt(idx) == '*' && expr.charAt(idx + 1) == '/'));
				} else
					throw new Error(new StringBuilder("Wrong input: ").append(expr.charAt(idx - 1))
							.append(expr.charAt(idx)).toString());
			} else
				break;
		} while (true);
	}

	/**
	 * originally {@code scan()}
	 */
	private Token nextToken() {
		skipUseless();
		if (Character.isDigit(expr.charAt(idx))) {// 数字
			double v = 0;
			do {
				v = 10 * v + Character.digit(expr.charAt(idx), 10);
				++idx;
			} while (Character.isDigit(expr.charAt(idx)));
			if (expr.charAt(idx) == '.') {
				double frac = 0.1;
				while (Character.isDigit(expr.charAt(++idx))) {
					v = v + frac * Character.digit(expr.charAt(idx), 10);
					frac /= 10;
				}
			}
			return new Num(v);
		}
		if (expr.charAt(idx) == '.') {// .24
			double v = 0, frac = 0.1;
			while (Character.isDigit(++idx)) {
				v = v + frac * Character.digit(expr.charAt(idx), 10);
				frac /= 10;
			}
			return new Num(v);
		}
		// 负数如何识别
		if (Character.isLetter(expr.charAt(idx))) {// 变量（标识符）：未支持下划线
			StringBuilder b = new StringBuilder();
			do {
				b.append(expr.charAt(idx));
				++idx;
			} while (Character.isLetterOrDigit(expr.charAt(idx)));
			String s = b.toString();
			Word w = words.get(s);
			if (w != null)
				return w;

			w = new Word(Tag.ID, s);
			words.put(s, w);
			return w;
		}
		// operator
		String optrStr = String.valueOf(expr.charAt(idx));
		String optrStr_ = new StringBuilder(expr.charAt(idx)).append(expr.charAt(idx + 1)).toString();
		if (Optr.optrSet.contains(optrStr)) {
			++idx;
			return new Optr(optrStr);
		} else if (Optr.optrSet.contains(optrStr_)) {
			idx = idx + 2;
			return new Optr(optrStr_);
		} else
			throw new Error("Illegal character: " + expr.charAt(idx));
	}
}
