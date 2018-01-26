package nl.astraeus.jdbc;

/**
 * User: rnentjes
 * Date: 26-1-18
 * Time: 11:08
 */
public class Parameter {

    public interface DisplayFunction {
        String display(Object o);
    }

    private static DisplayFunction valueOf = new DisplayFunction() {
        public String display(Object o) {
            return String.valueOf(o);
        }
    };

    private static DisplayFunction label(final String label) {
        return new DisplayFunction() {
            public String display(Object o) {
                return label;
            }
        };
    }

    public enum ParameterType {
        NULL(new DisplayFunction() {
            public String display(Object o) {
                return "NULL";
            }
        }),
        BOOLEAN(valueOf),
        INT(valueOf),
        LONG(valueOf),
        STRING(new DisplayFunction() {
            public String display(Object o) {
                return (String)o;
            }
        }),
        BLOB(valueOf),
        CLOB(valueOf),
        BYTE(valueOf),
        SHORT(valueOf),
        FLOAT(valueOf),
        DOUBLE(valueOf),
        BIG_DECIMAL(valueOf),
        BYTES(new DisplayFunction() {
            public String display(Object o) {
                return "byte["+((byte[])o).length+"]";
            }
        }),
        DATE(valueOf),
        TIME(valueOf),
        TIMESTAMP(valueOf),
        ASCII_STR(label("")),
        UNICODE_STR(label("")),
        BIN_STR(label("")),
        OBJECT(valueOf),
        CHAR_STR(label("")),
        REF(valueOf),
        ARRAY(valueOf),
        URL(valueOf),
        ROW_ID(valueOf),
        NSTRING(valueOf),
        NCHAR_STR(label("")),
        NCLOB(label("")),
        SQLXML(label(""));

        private DisplayFunction display;

        ParameterType(DisplayFunction display) {
            this.display = display;
        }

        public String toString(Object o) {
            return display.display(o);
        }
    }

    private int index;
    private ParameterType type;
    private Object value;

    public Parameter(
            int index,
            ParameterType type
    ) {
        this(index, type, null);
    }

    public Parameter(
            int index,
            ParameterType type,
            Object value
    ) {
        this.index = index;
        this.type = type;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public ParameterType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public String getDisplayValue() {
        return type.display.display(value);
    }
}
