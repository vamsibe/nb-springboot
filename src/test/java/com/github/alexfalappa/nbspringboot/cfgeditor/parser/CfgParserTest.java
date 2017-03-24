package com.github.alexfalappa.nbspringboot.cfgeditor.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Test;

import static java.nio.charset.Charset.defaultCharset;

/**
 *
 * @author Alessandro Falappa
 */
@Ignore
public class CfgParserTest {

    @Test
    public void testComments() throws ParseException, URISyntaxException, IOException {
        System.out.println("-- Comments");
        BufferedReader bf = Files.newBufferedReader(Paths.get(getClass().getResource("/comment.properties").toURI()), defaultCharset());
        BootCfgParser cp = new BootCfgParser(bf);
        cp.parse();
    }
}
