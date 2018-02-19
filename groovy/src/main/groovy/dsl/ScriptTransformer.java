package dsl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ScriptTransformer {

    private ScriptTransformer(){}

    private static final String BEGIN_MACROS_LINE_PATTERN = "//#define";
    private static final String MACROS_SEPARATOR_PATTERN = "=>";
    public static String evaluate(List<String> lines) {
        Map<String, String> macros = new HashMap<>();

        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            if (line.startsWith(BEGIN_MACROS_LINE_PATTERN)) {
                String[] split = line.replaceAll(BEGIN_MACROS_LINE_PATTERN + "\\s+", "").split(MACROS_SEPARATOR_PATTERN);
                String macroName = split[0];
                String macroExpression = split[1];
                macros.put(macroName, macroExpression + " ");
            }
        }

        for (String line : lines) {
            if (!line.startsWith(BEGIN_MACROS_LINE_PATTERN)) {
                for (Map.Entry<String, String> entry : macros.entrySet()) {
                    line = line.replaceAll(entry.getKey(), entry.getValue());
                }
                result.append(line).append("\n");
            }
        }


        return result.toString();
    }
}
