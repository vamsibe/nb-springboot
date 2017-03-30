/*
 * Copyright 2017 Alessandro Falappa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.alexfalappa.nbspringboot.cfgeditor.lexer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author Alessandro Falappa
 */
public class BootCfgLanguageHierarchy extends LanguageHierarchy<BootCfgTokenId> {

    private static List<BootCfgTokenId> tokens;
    private static Map<Integer, BootCfgTokenId> idToToken;

    private static void init() {
        tokens = Arrays.<BootCfgTokenId>asList(new BootCfgTokenId[]{
            new BootCfgTokenId("EOF", "whitespace", 0),
            new BootCfgTokenId("HEXDIGIT", "number", 1),
            new BootCfgTokenId("WHITESPACE", "whitespace", 2),
            new BootCfgTokenId("EOL", "whitespace", 3),
            new BootCfgTokenId("COMMENT_START", "comment", 4),
            new BootCfgTokenId("COMMENT_CHAR", "comment", 5),
            new BootCfgTokenId("COMMENT_EOL", "whitespace", 6),
            new BootCfgTokenId("KEY_START", "keyword", 7),
            new BootCfgTokenId("DIGIT", "number", 8),
            new BootCfgTokenId("NONZERO_DIGIT", "number", 9),
            new BootCfgTokenId("DIGITS", "number", 10),
            new BootCfgTokenId("KEY_INTEGER", "keyword", 11),
            new BootCfgTokenId("KEY_ESC_UNICODE", "keyword", 12),
            new BootCfgTokenId("KEY_ESC_SPACE", "keyword", 13),
            new BootCfgTokenId("KEY_ESC_LF", "keyword", 14),
            new BootCfgTokenId("KEY_ESC_TAB", "keyword", 15),
            new BootCfgTokenId("KEY_ESC_EQUAL", "keyword", 16),
            new BootCfgTokenId("KEY_ESC_COLON", "keyword", 17),
            new BootCfgTokenId("KEY_ESC_POUND", "keyword", 18),
            new BootCfgTokenId("KEY_ESC_EXCL", "keyword", 19),
            new BootCfgTokenId("KEY_ESC_BACKSLASH", "keyword", 20),
            new BootCfgTokenId("KEY_DOT", "keyword", 21),
            new BootCfgTokenId("KEY_OBRACKET", "separator", 22),
            new BootCfgTokenId("KEY_CBRACKET", "separator", 23),
            new BootCfgTokenId("KEY_SEPARATOR", "separator", 24),
            new BootCfgTokenId("KEY_CHAR", "keyword", 25),
            new BootCfgTokenId("KEY_WHITESPACE", "keyword", 26),
            new BootCfgTokenId("KEY_EOL", "whitespace", 27),
            new BootCfgTokenId("SEP_WHITESPACE", "whitespace", 28),
            new BootCfgTokenId("SEP_VAL_START", "literal", 29),
            new BootCfgTokenId("VAL_START", "literal", 30),
            new BootCfgTokenId("VAL_ESC_UNICODE", "literal", 31),
            new BootCfgTokenId("VAL_ESC_SPACE", "literal", 32),
            new BootCfgTokenId("VAL_ESC_EOL", "literal", 33),
            new BootCfgTokenId("VAL_ESC_EQUAL", "literal", 34),
            new BootCfgTokenId("VAL_ESC_COLON", "literal", 35),
            new BootCfgTokenId("VAL_ESC_POUND", "literal", 36),
            new BootCfgTokenId("VAL_ESC_EXCL", "literal", 37),
            new BootCfgTokenId("VAL_ESC_LF", "literal", 38),
            new BootCfgTokenId("VAL_ESC_TAB", "literal", 39),
            new BootCfgTokenId("VAL_ESC_BACKSLASH", "literal", 40),
            new BootCfgTokenId("VAL_ESC_MALFORMED", "literal", 41),
            new BootCfgTokenId("VAL_WHITESPACE", "literal", 42),
            new BootCfgTokenId("VAL_CHAR", "literal", 43),
            new BootCfgTokenId("VAL_EOL", "whitespace", 44)
        });
        idToToken = new HashMap<>();
        for (BootCfgTokenId token : tokens) {
            idToToken.put(token.ordinal(), token);
        }
    }

    static synchronized BootCfgTokenId getToken(int id) {
        if (idToToken == null) {
            init();
        }
        return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<BootCfgTokenId> createTokenIds() {
        if (tokens == null) {
            init();
        }
        return tokens;
    }

    @Override
    protected synchronized Lexer<BootCfgTokenId> createLexer(LexerRestartInfo<BootCfgTokenId> info) {
        return new BootCfgLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/application+properties";
    }
}
