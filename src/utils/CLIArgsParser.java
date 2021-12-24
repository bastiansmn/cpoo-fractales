package utils;

import utils.complex.Complex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class CLIArgsParser {

    public static class Option {
        private final boolean required;
        private final String name;
        private final String alias;
        private Object value;
        private final Object fallbackValue;

        public Option(String name, String alias, boolean required, Object fallbackValue) {
            this(name, alias, required, null, fallbackValue);
        }

        public Option(String name, Object fallbackValue) {
            this(name, null, false, null, fallbackValue);
        }

        public Option(String name, String alias, Object fallbackValue) {
            this(name, alias, false, null, fallbackValue);
        }

        public Option(String name, boolean required, Object fallbackValue) {
            this(name, null, required, null, fallbackValue);
        }

        public Option(String name, String alias, boolean required) {
            this(name, alias, required, null, null);
        }

        public Option(String name) {
            this(name, null, false, null, null);
        }

        public Option(String name, String alias) {
            this(name, alias, false, null, null);
        }

        public Option(String name, boolean required) {
            this(name, null, required, null, null);
        }

        private Option(String name, String alias, boolean required, Object value, Object fallbackValue) {
            this.name = name;
            this.alias = alias;
            this.required = required;
            this.value = value;
            this.fallbackValue = required
                    ? Objects.requireNonNull(fallbackValue)
                    : fallbackValue;
        }

        public String getAlias() {
            return alias;
        }

        public String getName() {
            return name;
        }

        public boolean isRequired() {
            return required;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        private Object getFallbackValue() {
            return fallbackValue;
        }

        @Override
        public String toString() {
            if (this.value != null) {
                return "Option{" +
                        "name='" + name + '\'' +
                        ", value='" + value + '\'' +
                        '}';
            }
            return "Option{" +
                    "name='" + name + '\'' +
                    ", alias='" + alias + '\'' +
                    ", required=" + required +
                    '}';
        }

    }

    private final LinkedHashMap<String, Option> providedOptions = new LinkedHashMap<>();
    private final List<Option> acceptedOptions;

    public CLIArgsParser(List<Option> acceptedOptions) {
        this.acceptedOptions = acceptedOptions;
    }

    public void parse(String[] args) {
        List<String> temp = List.of(args);
        LinkedHashMap<String, String> givenOptions = new LinkedHashMap<>();
        temp.forEach((String arg) -> {
            String[] opt = arg.split("=");
            if (opt.length != 2)
                throw new IllegalArgumentException(String.format("'%s' argument is not a valid argument", arg));
            givenOptions.put(opt[0], opt[1]);
        });
        // Check if we provided all required options.
        this.getRequiredOptions().forEach(o -> {
            if (!(givenOptions.containsKey(o.getName()) || givenOptions.containsKey(o.getAlias())))
                throw new IllegalArgumentException(String.format("Argument '%s | %s' is missing", o.getName(), o.getAlias()));
        });
        // Parsing only accepted options
        List<String> strName = this.acceptedOptions.stream().map(Option::getName).toList();
        List<String> strAlias = this.acceptedOptions.stream().map(Option::getAlias).toList();
        givenOptions.forEach((key, val) -> {
            if ( strName.contains(key) || strAlias.contains(key)) {
                var value = tryParse(val);
                assert value != null;

                String finalKey = key;
                List<Option> opt = this.acceptedOptions.stream().filter(o -> o.getAlias().equals(finalKey)).toList();
                if (opt.size() == 1)
                    key = opt.get(0).getName();

                this.providedOptions.put(key, new Option(
                        key,
                        key,
                        false,
                        value,
                        value
                ));
            } else {
                System.err.printf("Option '%s' is not a valid option. Continuing...\n", key);
            }
        });
        // Add fallbackValue à value
        this.acceptedOptions.forEach(o -> {
            if (!(givenOptions.containsKey(o.getName()) || givenOptions.containsKey(o.getAlias()))) {
                // Options that we can have, but we didn't provide
                this.providedOptions.put(o.getName() , new Option(
                        o.getName(),
                        o.getAlias(),
                        false,
                        o.getFallbackValue(),
                        o.getFallbackValue()
                ));
            }
        });
    }

    private Object tryParse(String val) {
        // TODO essayer de rendre le parse générique
        try {
            return Interval.parse(val);
        } catch (Exception ignore) {}
        try {
            return Complex.parse(val);
        } catch (Exception ignore) {}
        try {
            return Integer.parseInt(val);
        } catch (Exception ignore) {}
        try {
            return Double.parseDouble(val);
        } catch (Exception ignore) {}
        return val;
    }

    public List<Option> getRequiredOptions() {
        return this.acceptedOptions
                .stream()
                .filter(Option::isRequired)
                .toList();
    }

    public LinkedHashMap<String, Option> getProvidedOptions() {
        return providedOptions;
    }

    public String getUsage() {
        return """
               Usage :
                  ./Fractale -c="[re]+[im]i" <-s=500> <-f=2> <-b=0.2> <-r=[100; 360]>
                  
                  Where <...> is optional, [...] is a value you need to enter.
               """;
    }

}
