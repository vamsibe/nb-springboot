package com.github.alexfalappa.nbspringboot.cfgeditor.parser;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Alessandro Falappa
 */
@Ignore
public class AdvancedTest extends TestBase {

    @Test
    public void testSingleEqualContinuation1() throws URISyntaxException, IOException {
        System.out.println("\n-- single equal continuation 1");
        parseMatch("key=val\\\non next line");
    }

    @Test
    public void testSingleEqualContinuation2() throws URISyntaxException, IOException {
        System.out.println("\n-- single equal continuation 2");
        parseMatch(" anotherkey = slash before\\\\\n"
                + "\\and after continuation");
    }

    @Test
    public void testSingleEqualContinuation3() throws URISyntaxException, IOException {
        System.out.println("\n-- single equal continuation 3");
        parseMatch("key2=value\\\n"
                + "  with space at start of continuation");
    }

    @Test
    public void testMultipleEqualContinuation() throws URISyntaxException, IOException {
        System.out.println("\n-- multiple equal continuation");
        parseMatch("key1=first\\\n"
                + "value\n"
                + "key2=second\\\n"
                + "value");
    }

}
