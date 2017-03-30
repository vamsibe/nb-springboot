package af;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.TreeSet;

import static org.junit.Assert.fail;

/**
 * Base test class to factor out parsing methods.
 *
 * @author Alessandro Falappa
 */
public class TestBase {

    protected void parseNoMatch(String input) {
        try {
            tokenize(input);
            try (StringReader sr = new StringReader(input)) {
                BootCfgParser cp = new BootCfgParser(sr);
                cp.enable_tracing();
                cp.parse();
                fail("Parsed while it should not");
            }
        } catch (ParseException | TokenMgrError ex) {
            // should fail
            System.out.println(ex.getMessage());
            System.out.println("OK");
        }
    }

    protected void parseMatch(String input) {
        try {
            tokenize(input);
            try (StringReader sr = new StringReader(input)) {
                BootCfgParser cp = new BootCfgParser(sr);
                cp.enable_tracing();
                cp.parse();
                listPropsOrdered(cp.getParsedProps());
            }
        } catch (ParseException | TokenMgrError ex) {
            fail(ex.getMessage());
        }
    }

    protected void tokenize(String input) {
        try (StringReader sr = new StringReader(input)) {
            SimpleCharStream stream = new SimpleCharStream(sr);
            BootCfgParserTokenManager bcptm = new BootCfgParserTokenManager(stream);
            Token tk;
            while ((tk = bcptm.getNextToken()).kind != BootCfgParserConstants.EOF) {
                System.out.printf("Token %2d %s char '%s'%n", tk.kind, BootCfgParserConstants.tokenImage[tk.kind], tk.toString());
            }
        }
        System.out.println("Done");
    }

    protected String readResource(String name) throws IOException, URISyntaxException {
        byte[] encoded = Files.readAllBytes(Paths.get(TestBase.class.getResource(name).toURI()));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    protected void listPropsOrdered(Properties p) {
        if (p.isEmpty()) {
            System.out.println("No properties");
        } else {
            System.out.println("Listing properties ordered by key in [key] -> [value] format:");
            TreeSet<Object> sortedKeys = new TreeSet<>(p.keySet());
            for (Object k : sortedKeys) {
                System.out.printf("[%s] -> [%s]%n", k.toString(), p.get(k).toString());
            }
        }
    }

}
