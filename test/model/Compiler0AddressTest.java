package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for 0-Address expressions
 *
 * @author dorianreineccius
 */
public class Compiler0AddressTest {
    Compiler c = new Compiler();

    @Before
    public void init() {
        try {
            c.load(getClass().getResourceAsStream("/rules/three.rules"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A little load-write test for 0-address expressions
     */
    @Test
    public void loadWriteTest() {
        String source = "ρ(a) := 42\n" +
                "α := ρ(a)\n" +
                "ρ(b) := 42\n" +
                "return";
        Program p = c.compile(source);
        VM vm = new VM(p);
        vm.loadProgam();
        for (int i = 0; i < 3; i++) {
            vm.step();
        }
        Assert.assertEquals(42, (int) vm.getMemory().get("a"));
        Assert.assertEquals(42, (int) vm.getRegister()[0]);
        Assert.assertEquals(42, (int) vm.getMemory().get("b"));
    }
}
