package com.github.alexfalappa.nbspringboot.cfgeditor.parser;

import java.io.StringReader;

import static org.junit.Assert.fail;

/**
 * Base test class to factor out parsing methods.
 *
 * @author Alessandro Falappa
 */
public class TestBase {

    protected void parseNoMatch(String input) {
        try {
            StringReader sr = new StringReader(input);
            BootCfgParser cp = new BootCfgParser(sr);
            cp.parse();
            fail("Parsed while it should not");
        } catch (ParseException ex) {
            // should fail
            System.out.println("OK");
        }
    }

    protected void parseMatch(String input) {
        try {
            StringReader sr = new StringReader(input);
            BootCfgParser cp = new BootCfgParser(sr);
            cp.parse();
            System.out.println("OK");
        } catch (ParseException ex) {
            fail(ex.getMessage());
        }
    }

}
