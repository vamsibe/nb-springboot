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

import com.github.alexfalappa.nbspringboot.cfgeditor.jcc.SimpleCharStream2;

import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

import com.github.alexfalappa.nbspringboot.cfgeditor.jcc.BootCfgParserTokenManager;
import com.github.alexfalappa.nbspringboot.cfgeditor.jcc.Token;

/**
 *
 * @author Alessandro Falappa
 */
public class BootCfgLexer implements Lexer<BootCfgTokenId> {

    private final LexerRestartInfo<BootCfgTokenId> info;
    private BootCfgParserTokenManager cfgParserTokenManager;

    BootCfgLexer(LexerRestartInfo<BootCfgTokenId> info) {
        this.info = info;
        SimpleCharStream2 stream = new SimpleCharStream2(info.input());
        cfgParserTokenManager = new BootCfgParserTokenManager(stream);
    }

    @Override
    public org.netbeans.api.lexer.Token<BootCfgTokenId> nextToken() {
        Token token = cfgParserTokenManager.getNextToken();
        if (info.input().readLength() < 1) {
            return null;
        }
        return info.tokenFactory().createToken(BootCfgLanguageHierarchy.getToken(token.kind));
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

}
