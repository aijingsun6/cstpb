/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic;

/**
 * Mode
 *
 * @author alking
 * @since 2023/10/17
 */
public enum Mode {
    NORMAL("normal"),
    STRICT("strict");
    private final String value;

    Mode(String value) {
        this.value = value;
    }
}
