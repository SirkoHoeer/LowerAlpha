package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for 2-Address expressions
 *
 * @author dorianreineccius
 */
public class Compiler2AddressTest {
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
     * This tests ρ(ρ(i)) being written and read.
     */
    @Test
    public void compileIndirectExpressions() {
        String source = "ρ(i) := 1\n" +
                "ρ(j) := 2\n" +
                "ρ(ρ(i)) := ρ(j)\n" +
                "α := ρ(ρ(i))\n" +
                "return";
        Program p = c.compile(source);
        VM vm = new VM(p);
        vm.loadProgam();
        vm.step();
        vm.step();
        vm.step();
        Assert.assertEquals(2, (int) vm.getMemory().get("1"));
        vm.step();
        Assert.assertEquals(2, (int) vm.getRegister()[0]);

    }
}
