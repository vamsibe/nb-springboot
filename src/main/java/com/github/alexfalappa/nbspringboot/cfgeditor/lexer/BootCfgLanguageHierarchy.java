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

    private static final List<BootCfgTokenId> tokens;
    private static final Map<Integer, BootCfgTokenId> idToToken = new HashMap<>();

    public enum TkName {
        EOF, WHITESPACE, COMMENT, KEY, DOT, BRACKET, INTEGER, SEPARATOR, VALUE, UNEXPECTED
    };

    static {
        tokens = Arrays.asList(new BootCfgTokenId(TkName.EOF.name(), "whitespace", 0),
                new BootCfgTokenId(TkName.WHITESPACE.name(), "whitespace", 1),
                new BootCfgTokenId(TkName.COMMENT.name(), "comment", 2),
                new BootCfgTokenId(TkName.KEY.name(), "keyword", 3),
                new BootCfgTokenId(TkName.SEPARATOR.name(), "separator", 4),
                new BootCfgTokenId(TkName.DOT.name(), "operator", 5),
                new BootCfgTokenId(TkName.BRACKET.name(), "operator", 6),
                new BootCfgTokenId(TkName.VALUE.name(), "literal", 7),
                new BootCfgTokenId(TkName.INTEGER.name(), "number", 8),
                new BootCfgTokenId(TkName.UNEXPECTED.name(), "errors", 9)
        );
        for (BootCfgTokenId token : tokens) {
            idToToken.put(token.ordinal(), token);
        }
    }

    static synchronized BootCfgTokenId getToken(int id) {
        return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<BootCfgTokenId> createTokenIds() {
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
