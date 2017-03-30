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
package com.github.alexfalappa.nbspringboot.cfgeditor.syntax;

import org.netbeans.editor.Syntax;
import org.netbeans.editor.TokenID;

import static org.netbeans.editor.Syntax.INIT;

/**
 *
 * @author Alessandro Falappa
 */
public class CfgPropertiesSyntax extends Syntax {

    // Internal states
    private static final int ISI_LINE_COMMENT = 2; // inside line comment
    private static final int ISI_KEY = 3; // inside a key
    private static final int ISI_KEY_A_BSLASH = 4; // inside a key after backslash
    private static final int ISI_EQUAL = 5; // inside an equal sign
    private static final int ISI_EQUAL2 = 6; // after key but not yet value or equal Note: EQUAL2 was revised
    private static final int ISI_VALUE = 7; // inside a value
    private static final int ISI_VALUE_A_BSLASH = 8; // inside a value after backslash
    private static final int ISI_VALUE_AT_NL = 9; // inside a value at new line
    private static final int ISI_EQUAL_AT_NL = 10; // between key and not yet value at new line

    public CfgPropertiesSyntax() {
        tokenContextPath = CfgPropertiesTokenContext.contextPath;
    }

    @Override
    protected TokenID parseToken() {
        char actChar;

        while (offset < stopOffset) {
            actChar = buffer[offset];

            switch (state) {
                case INIT:
                    switch (actChar) {
                        case '\n':
                            offset++;
                            return CfgPropertiesTokenContext.EOL;
                        case '\t':
                        case '\f':
                        case ' ':
                            offset++;
                            return CfgPropertiesTokenContext.TEXT;
                        case '#':
                        case '!':
                            state = ISI_LINE_COMMENT;
                            break;
                        case '=': // in case the key is an empty string (first non-white is '=' or ':')
                        case ':':
                            state = ISI_EQUAL;
                            return CfgPropertiesTokenContext.TEXT;
                        case '\\': // when key begins with escape
                            state = ISI_KEY_A_BSLASH;
                            break;
                        default:
                            state = ISI_KEY;
                            break;
                    }
                    break; // end state INIT

                case ISI_LINE_COMMENT:
                    switch (actChar) {
                        case '\n':
                            state = INIT;
                            return CfgPropertiesTokenContext.LINE_COMMENT;
                    }
                    break; // end state ISI_LINE_COMMENT

                case ISI_KEY:
                    switch (actChar) {
                        case '\n':
                            state = INIT;
                            return CfgPropertiesTokenContext.KEY;
                        case '\\':
                            state = ISI_KEY_A_BSLASH;
                            break;
                        case '=':
                        case ':':
                        case ' ': // the whitspaces after key
                        case '\t':
                            state = ISI_EQUAL;
                            return CfgPropertiesTokenContext.KEY;
                    }
                    break; // end state ISI_KEY

                case ISI_KEY_A_BSLASH:
                    switch (actChar) {
                        case '\n':
                            state = INIT;
                            return CfgPropertiesTokenContext.KEY;
                        default:
                            state = ISI_KEY;
                    }
                    break; // end state ISI_KEY_A_BSLASH

                case ISI_EQUAL:
                    switch (actChar) {
                        case '=':
                        case ':':
                            offset++;
                            state = ISI_VALUE;
                            return CfgPropertiesTokenContext.EQ;
                        case ' ': // whitespaces also separates key from value: note which whitespaces can do that
                        case '\t':
                            break;
                        case '\\': // in case of alone '\\' line continuation character
                            state = ISI_EQUAL2;
                            break;
                        case '\n':
                            state = INIT;
                            return CfgPropertiesTokenContext.EQ;
                        default:
                            state = ISI_VALUE;
                    }
                    break; // end state ISI_KEY

                // only for case the last "\\" continuation char is but was not startes value yet (still can appear : or = char)
                case ISI_EQUAL2:
                    switch (actChar) {
                        case '\n':
                            state = ISI_EQUAL_AT_NL;
                            return CfgPropertiesTokenContext.EQ; // PENDING
                        default:
                            state = ISI_VALUE;
                    }
                    break; // end state ISI_EQUAL_A_BSLASH

                // in case of end of line
                case ISI_EQUAL_AT_NL:
                    switch (actChar) {
                        case '\n':
                            offset++;
                            state = ISI_EQUAL;
                            return CfgPropertiesTokenContext.EOL;
                        default:
                            throw new Error("Something smells 4");
                    }

// this previous version of ISI_EQUAL2 is needless because ':=' is not separator the second = char belongs to the value already
//            case ISI_EQUAL2:
//                switch (actChar) {
//                case '\n':
//                    state = INIT;
//                    return EQ;
//                case '=':
//                case ':':
//                    offset++;
//                    state = ISI_VALUE;
//                    return EQ;
//                default:
//                    state = ISI_VALUE;
//                    return EQ;
//                }
                //break; // end state ISI_KEY
                case ISI_VALUE:
                    switch (actChar) {
                        case '\n':
                            state = INIT;
                            return CfgPropertiesTokenContext.VALUE;
                        case '\\':
                            state = ISI_VALUE_A_BSLASH;
                            break;
                    }
                    break; // end state ISI_KEY

                case ISI_VALUE_A_BSLASH:
                    switch (actChar) {
                        case '\n':
                            state = ISI_VALUE_AT_NL;
                            return CfgPropertiesTokenContext.VALUE;
                        default:
                            state = ISI_VALUE;
                    }
                    break; // end state ISI_KEY

                case ISI_VALUE_AT_NL:
                    switch (actChar) {
                        case '\n':
                            offset++;
                            state = ISI_VALUE;
                            return CfgPropertiesTokenContext.EOL;
                        default:
                            throw new Error("Something smells 2");
                    }
                //break; // end state ISI_KEY

                default:
                    throw new Error("Unhandled state " + state);

            } // end of the outer switch statement

            offset = ++offset;

        } // end of while loop

        /* At this stage there's no more text in the scanned buffer. */
        if (lastBuffer || !lastBuffer) {
            switch (state) {
                case ISI_LINE_COMMENT:
                    return CfgPropertiesTokenContext.LINE_COMMENT;
                case ISI_KEY:
                case ISI_KEY_A_BSLASH:
                    return CfgPropertiesTokenContext.KEY;
                case ISI_EQUAL:
                case ISI_EQUAL2:
                    return CfgPropertiesTokenContext.EQ;
                case ISI_VALUE:
                case ISI_VALUE_A_BSLASH:
                    return CfgPropertiesTokenContext.VALUE;
                case ISI_VALUE_AT_NL:
                case ISI_EQUAL_AT_NL: // TEMP
                    throw new Error("Something smells 3");
            }
        }

        return null;

    } // parseToken

    public String getStateName(int stateNumber) {
        switch (stateNumber) {
            case ISI_LINE_COMMENT:
                return "ISI_LINE_COMMENT";
            case ISI_KEY:
                return "ISI_KEY";
            case ISI_KEY_A_BSLASH:
                return "ISI_KEY_A_BSLASH";
            case ISI_EQUAL:
                return "ISI_EQUAL";
            case ISI_EQUAL2:
                return "ISI_EQUAL2";
            case ISI_EQUAL_AT_NL:
                return "ISI_EQUAL_AT_NL";
            case ISI_VALUE:
                return "ISI_VALUE";
            case ISI_VALUE_A_BSLASH:
                return "ISI_VALUE_A_BSLASH";
            case ISI_VALUE_AT_NL:
                return "ISI_VALUE_AT_NL";
            default:
                return super.getStateName(stateNumber);
        }
    }

}
