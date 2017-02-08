package edu.byu.rpg.junitTest;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Andrew on 2/7/2017.
 */
public class JunitUnitTest {
        @Test
        public void thisAlwaysPasses()
        {
            System.out.println("hi");
            assertTrue(true);
        }

        @Test
        @Ignore
        public void thisIsIgnored() {
        }
}
