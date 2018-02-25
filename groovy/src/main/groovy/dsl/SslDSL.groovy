package dsl

import kernel.units.Duration
import kernel.units.Frequency
import kernel.units.TimeUnit
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.WhileStatement
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.syntax.Types

final class SslDSL {
    private GroovyShell shell
    private CompilerConfiguration configuration
    private SslBinding binding
    private SslBaseScript basescript

    SslDSL() {
        binding = new SslBinding()
        binding.setModel(new SslModel())
        configuration = getDSLConfiguration()
        configuration.setScriptBaseClass("dsl.SslBaseScript")
        shell = new GroovyShell(configuration)

        binding.setVariable("s", new Duration(1, TimeUnit.Second))
        binding.setVariable("min", new Duration(1, TimeUnit.Minute))
        binding.setVariable("h", new Duration(1, TimeUnit.Hour))
        binding.setVariable("d", new Duration(1, TimeUnit.Day))
        binding.setVariable(ScriptTransformer.LINE_COUNT_VARIABLE_NAME, 1)
    }


    private static CompilerConfiguration getDSLConfiguration() {
        def secure = new SecureASTCustomizer()

        secure.with {
            closuresAllowed = true
            methodDefinitionAllowed = true
            importsWhitelist = [

            ]

            staticImportsWhitelist = []
            staticStarImportsWhitelist = []
            tokensWhitelist = [
                    Types.PLUS_PLUS,
                    Types.PLUS_EQUAL,
                    Types.DIVIDE,
                    Types.PLUS,
                    Types.MINUS,
                    Types.MULTIPLY,
                    Types.POWER,
                    Types.EQUAL,
                    Types.MOD,
                    Types.COMPARE_EQUAL,
                    Types.COMPARE_NOT_EQUAL,
                    Types.COMPARE_LESS_THAN,
                    Types.COMPARE_LESS_THAN_EQUAL,
                    Types.COMPARE_GREATER_THAN,
                    Types.COMPARE_GREATER_THAN_EQUAL,
                    Types.ASSIGN,
                    Types.POWER,
                    Types.LEFT_SQUARE_BRACKET,
                    Types.RIGHT_SQUARE_BRACKET,
                    Types.POWER
            ].asImmutable()
            //types allowed to be used  (including primitive types)
            constantTypesClassesWhiteList = [
                    int,
                    Integer,
                    Number,
                    Integer.TYPE,
                    double,
                    Double,
                    Double.TYPE,
                    String,
                    Object,
                    BigDecimal
            ].asImmutable()
            //classes who are allowed to be receivers of method calls
            receiversClassesWhiteList = [
                    int, Number, Integer, String, Object
            ].asImmutable()
        }

        List<Class> statementBlacklist = new ArrayList<>()
        statementBlacklist.add(WhileStatement)
        statementBlacklist.add(ForStatement)
        secure.setStatementsBlacklist(statementBlacklist)

        Number.metaClass {
            getS { -> new Duration(delegate as double, TimeUnit.Second) }
            getMin { -> new Duration(delegate as double, TimeUnit.Minute) }
            getH { -> new Duration(delegate as double, TimeUnit.Hour) }
            getD { -> new Duration(delegate as double, TimeUnit.Day) }
        }

        Number.metaClass.div = { Duration d -> new Frequency(delegate as int, d) }

        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(secure)

        return configuration
    }

    void eval(File scriptFile) {
        List<String> scriptStrings = scriptFile.readLines()

        String evaluate = ScriptTransformer.evaluate(scriptStrings)

        try {
            Script script = shell.parse(evaluate)
            binding.setScript(script)
            script.setBinding(binding)

            script.setProperty("name", scriptFile.getName())
            script.run()
        } catch (MultipleCompilationErrorsException se) {
            println("Security Error : You are using forbidden keywords")
            println(se.getErrorCollector().getException(0).localizedMessage)
            System.exit(0)
        }


    }

}
