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
            /** End of File. */
            new BootCfgTokenId("EOF", "whitespace", 0),
            /** RegularExpression Id. */
            new BootCfgTokenId("WHITESPACE", "whitespace", 1),
            /** RegularExpression Id. */
            new BootCfgTokenId("BLANK_LINE", "whitespace", 2),
            /** RegularExpression Id. */
            new BootCfgTokenId("COMMENT_LINE1", "comment", 3),
            /** RegularExpression Id. */
            new BootCfgTokenId("COMMENT_LINE2", "comment", 4),
            /** RegularExpression Id. */
            new BootCfgTokenId("O_EQUAL", "keyword", 5),
            /** RegularExpression Id. */
            new BootCfgTokenId("O_COLON", "keyword", 6),
            /** RegularExpression Id. */
            new BootCfgTokenId("O_DOT", "operator", 7),
            /** RegularExpression Id. */
            new BootCfgTokenId("O_OPENBRACKET", "operator", 8),
            /** RegularExpression Id. */
            new BootCfgTokenId("O_CLOSEBRACKET", "operator", 9),
            /** RegularExpression Id. */
            new BootCfgTokenId("DIGIT", "literal", 10),
            /** RegularExpression Id. */
            new BootCfgTokenId("NONZERO_DIGIT", "literal", 11),
            /** RegularExpression Id. */
            new BootCfgTokenId("INTEGER", "literal", 12),
            /** RegularExpression Id. */
            new BootCfgTokenId("DIGITS", "literal", 13),
            /** RegularExpression Id. */
            new BootCfgTokenId("STRING", "literal", 14),
            /** RegularExpression Id. */
            new BootCfgTokenId("ALLOWABLE_CHARACTERS", "literal", 15),});
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
