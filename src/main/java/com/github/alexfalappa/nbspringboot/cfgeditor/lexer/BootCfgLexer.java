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

import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.parboiled.Parboiled;

import com.github.alexfalappa.nbspringboot.cfgeditor.parser.BootCfgParser;

/**
 *
 * @author Alessandro Falappa
 */
public class BootCfgLexer implements Lexer<BootCfgTokenId> {

    private final LexerRestartInfo<BootCfgTokenId> info;
    private BootCfgParser cfgParser;

    BootCfgLexer(LexerRestartInfo<BootCfgTokenId> info) {
        this.info = info;
        cfgParser = Parboiled.createParser(BootCfgParser.class);
    }

    @Override
    public Token<BootCfgTokenId> nextToken() {
        final LexerInput input = info.input();
        if (input.readLength() < 1) {
            return null;
        }
        System.out.println(input.readLength());
        System.out.println(input.readText());
        return info.tokenFactory().createToken(BootCfgLanguageHierarchy.getToken(14));
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

}
