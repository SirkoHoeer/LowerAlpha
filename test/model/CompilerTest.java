package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dorianreineccius
 */
public class CompilerTest {
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
     * This tests, whether alpha-expressions get parsed.
     */
    @Test
    public void compileAlphaExpressions() {
        String source = "" +
                "ρ(i):=0\nρ(j):=0#init them for testing purposes\n" +
                "α:=0\n" +
                "α:=α+0\n" +
                "α:=ρ(i)\n" +
                "α:=α+ρ(i)\n" +
                "α:=ρ(i)+ρ(j)\n" +
                "return";
        Program p = c.compile(source);
    }

    /**
     * This tests, whether rho-expressions get parsed.
     */
    @Test
    public void compileRhoExpressions() {
        String source = "" +
                "α:=0#init α for testing purposes\n" +
                "ρ(i):=α\n" +
                "ρ(j):=α+0\n" +
                "ρ(i):=ρ(j)\n" +
                "ρ(i):=ρ(j)+0\n" +
                "ρ(i):=ρ(j)+α\n" +
                "ρ(i):=ρ(ρ(j))\n" +
                "ρ(ρ(i)):=ρ(j)\n" +
                "return";
        Program p = c.compile(source);
    }

    /**
     * This tests, whether jumping statements get parsed.
     */
    @Test
    public void compileOtherExpressions() {
        String source = "" +
                "label: α:=0#init them for testing purposes\n" +
                "if α = 0 then goto label\n" +
                "goto label\n" +
                "call label\n" +
                "return";
        Program p = c.compile(source);
    }

    /**
     * This tests, whether the registers α,α0,α1,α2 works, if used all of them.
     */
    @Test
    public void registerConfusingTest() {
        String source = "ρ(i) := 42\n" +
                "ρ(j) := 42\n" +
                "ρ(k) := 42\n" +
                "ρ(l) := 42\n" +
                "α2 := 2\n" +
                "α1 := 1\n" +
                "α0 := 0\n" +
                "α := -1\n" +
                "ρ(i) := α\n" +
                "ρ(j) := α0\n" +
                "ρ(k) := α1\n" +
                "ρ(l) := α2\n" +
                "return";
        Program p = c.compile(source);
        VM vm = new VM(p);
        vm.loadProgam();
        for (int i = 0; i < 12; i++) {
            vm.step();
        }
        Assert.assertEquals(-1, (int) vm.getMemory().get("i"));
        Assert.assertEquals(0, (int) vm.getMemory().get("j"));
        Assert.assertEquals(1, (int) vm.getMemory().get("k"));
        Assert.assertEquals(2, (int) vm.getMemory().get("l"));
    }
}
