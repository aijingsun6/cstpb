/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.util;

import org.apache.commons.lang3.StringUtils;

/**
 * NamedTools
 *
 * @author alking
 * @since 2023/10/17
 */
public class NamedTools {

    public static String fullName(String prefix, String name) {
        return StringUtils.isEmpty(prefix) ? name : prefix + "." + name;
    }

    public static String simpleName(String name) {
        int last = name.lastIndexOf('.');
        if (last < 0) {
            return name;
        }
        return name.substring(last + 1);
    }

    public static String trimName(String name) {
        if (name.startsWith(".")) {
            return name.substring(1);
        }
        return name;
    }
}
