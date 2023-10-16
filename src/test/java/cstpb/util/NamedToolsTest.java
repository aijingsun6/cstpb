/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * NamedToolsTest
 *
 * @author alking
 * @since 2023/10/17
 */
public class NamedToolsTest {

    @Test
    public void simpleNameTest() {
        String name = "a.b.c.ddd";
        Assert.assertEquals("ddd", NamedTools.simpleName(name));
    }
}
