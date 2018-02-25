package com.unice.dsl.engine

import org.apache.commons.lang3.StringUtils

final class ScriptTransformer {
    static final String LINE_COUNT_VARIABLE_NAME = "LINECOUNTNAME"
    private static final String BEGIN_MACROS_LINE_PATTERN = "//#define"
    private static final String MACROS_SEPARATOR_PATTERN = "=>"

    private ScriptTransformer() {
        //ignore
    }

    static String evaluate(List<String> lines) {
        Map<String, String> macros = new HashMap<>()

        StringBuilder result = new StringBuilder()
        for (String line : lines) {
            if (line.startsWith(BEGIN_MACROS_LINE_PATTERN)) {
                String[] split = line.replaceAll(BEGIN_MACROS_LINE_PATTERN + "\\s+", "").split(MACROS_SEPARATOR_PATTERN)
                String macroName = split[0]
                String macroExpression = split[1]
                macros.put(macroName, macroExpression + " ")
            }
        }

        int openBracketNumber = 0
        int lineCountBuffer = 0
        for (String line : lines) {
            if (!line.startsWith(BEGIN_MACROS_LINE_PATTERN)) {
                for (Map.Entry<String, String> entry : macros.entrySet()) {
                    line = line.replaceAll(entry.getKey(), entry.getValue())
                }
            }

            if (!line.startsWith("//") && (line.contains("{") || line.contains("}"))) {
                openBracketNumber += StringUtils.countMatches(line, "{")
                openBracketNumber -= StringUtils.countMatches(line, "}")
                lineCountBuffer++
                result.append(line).append("\n")
            } else if (openBracketNumber == 0) {
                int toAdd = lineCountBuffer == 0 ? 1 : lineCountBuffer + 1
                result.append(line).append("\n")
                        .append(LINE_COUNT_VARIABLE_NAME + "+=").append(toAdd).append("\n")
                lineCountBuffer = 0
            } else {
                lineCountBuffer++
                result.append(line).append("\n")
            }
        }
        return result.toString()
    }
}
