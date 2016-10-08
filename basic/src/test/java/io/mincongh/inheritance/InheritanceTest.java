package io.mincongh.inheritance;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Inheritance test.
 *
 * @author Mincong Huang
 */
public class InheritanceTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @Test
    public void testConstructionOrder() {
        @SuppressWarnings("unused")
        Parent parent = new Child();
        String expected = "Parent\nChild\n";
        assertEquals("Parent's constructor is called first", expected, out.toString());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
