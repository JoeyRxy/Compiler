package mine.compiler;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        String s1 = "Hello world";
        String s2 = "ello world";
        assertTrue(s1 == s2);
    }

    @Test
    public void testCharAt() throws IOException {
        InputStreamReader in = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("leipzig1M.txt"));
        StringBuilder builder = new StringBuilder();
        int len;
        char[] b = new char[10240];
        while ((len = in.read(b)) != -1) {
            builder.append(b, 0, len);
        }
        b = null;
        in.close();
        String str = builder.toString();
        builder = null;
        char[] c = new char[str.length()];
        long start = System.currentTimeMillis();
        for (int i = 0; i < c.length; i++) {
            c[i] = str.charAt(i);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");
        byte[] _t = new byte[str.length()];
        start = System.currentTimeMillis();
        byte[] bytes = str.getBytes();
        for (int i = 0; i < c.length; i++) {
            _t[i] = bytes[i];
        }
        end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");
    }

    @Test
    public void testIsLetter() {
        List<Character> letter = new LinkedList<>();
        for (char c = 0; c < 128; ++c)
            if (Character.isLetter(c))
                letter.add(c);

        System.out.println(letter.size());
        System.out.println(letter);
    }
}
