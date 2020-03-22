package stone;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 肖皓星
 * @email xiaohaoxing@outlook.com
 */
public class Lexer {
    public static String regexPat = "\\s*((//.*)|[0-9]+|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")";
    private Pattern pattern = Pattern.compile(regexPat);
    private ArrayList<Token> queue = new ArrayList<>();
    private boolean hasMore;
    private LineNumberReader reader;

    public Lexer(Reader r) {
        hasMore = true;
        reader = new LineNumberReader(r);
    }

    public Token read() throws ParseException {
        if (fillQueue(0)) {
            return queue.get(0);
        } else {
            return Token.EOF;
        }
    }

    public Token peek(int i) throws ParseException {
        if (fillQueue(i)) {
            return queue.get(i);
        } else {
            return Token.EOF;
        }
    }

    private boolean fillQueue(int i) throws ParseException {
        while (i >= queue.size()) {
            if (hasMore) {
                readLine();
            } else {
                return false;
            }
        }
        return true;
    }

    protected void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        int lineNum = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                addToken(lineNum, matcher);
                pos = matcher.end();
            } else {
                throw new ParseException("bad token at line " + lineNum);
            }
        }
        queue.add(new IdToken(lineNum, Token.EOL));
    }

    protected void addToken(int lineNum, Matcher matcher) {
        String m = matcher.group(1);
        if (m != null) {
            // if not a space
            if (matcher.group(2) == null) {
                // if not a comment
                Token token;
                if (matcher.group(3) != null) {
                    token = new NumToken(lineNum, Integer.parseInt(m));
                } else if (matcher.group(4) != null) {
                    token = new StrToken(lineNum, toStringLiteral(m));
                } else {
                    token = IdToken(lineNum, m);
                }
                queue.add(token);
            }
        }
    }

    protected String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\') {
                    c = s.charAt(++i);
                } else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    protected static class NumToken extends Token {
        private int value;

        protected NumToken(int line, int v) {
            super(line);
            value = v;
        }

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public String getText() {
            return Integer.toString(value);
        }

        @Override
        public int getNumber() {
            return value;
        }
    }

    protected static class IdToken extends Token {
        private String text;

        protected IdToken(int line, String id) {
            super(line);
            text = id;
        }

        @Override
        public boolean isIdentifier() {
            return true;
        }

        @Override
        public String getText() {
            return text;
        }
    }

    protected static class StrToken extends Token {
        private String literal;

        protected StrToken(int line, String str) {
            super(line);
            literal = str;
        }

        @Override
        public boolean isString() {
            return true;
        }

        @Override
        public String getText() {
            return literal;
        }
    }

}
