package dsl

import kernel.structural.laws.LawType
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.syntax.Types
import units.Duration
import units.Frequency
import units.TimeUnit

class SslDSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private SslBinding binding
	private SslBaseScript basescript

	SslDSL() {
		binding = new SslBinding()
		binding.setModel(new SslModel(binding))
		configuration = getDSLConfiguration()
		configuration.setScriptBaseClass("dsl.SslBaseScript")
		shell = new GroovyShell(configuration)

        for (LawType type : LawType.values()) {
            binding.setVariable(type.toString(), type.toString())
        }
        binding.setVariable("s", new Duration(1, TimeUnit.Second))
        binding.setVariable("min", new Duration(1, TimeUnit.Minute))
        binding.setVariable("h", new Duration(1, TimeUnit.Hour))
        binding.setVariable("d", new Duration(1, TimeUnit.Day))
	}


	private static CompilerConfiguration getDSLConfiguration() {
		def secure = new SecureASTCustomizer()
		secure.with {
			//disallow closure creation
			closuresAllowed = false
			//disallow method definitions
			methodDefinitionAllowed = true
			//empty white list => forbid imports
			importsWhitelist = [
				'java.lang.*'
			]

			staticImportsWhitelist = []
			staticStarImportsWhitelist= []
			//language tokens disallowed
//			tokensBlacklist= []
			//language tokens allowed
			tokensWhitelist= [Types.DIVIDE]
			//types allowed to be used  (including primitive types)
			constantTypesClassesWhiteList= [
				int, Integer, Number, Integer.TYPE, String, Object, BigDecimal
			]
			//classes who are allowed to be receivers of method calls
			receiversClassesWhiteList= [
				int, Number, Integer, String, Object
			]
		}

        Number.metaClass {
            getS { -> new Duration(delegate as double, TimeUnit.Second)}
            getMin { -> new Duration(delegate as double, TimeUnit.Minute)}
            getH { -> new Duration(delegate as double, TimeUnit.Hour)}
            getD { -> new Duration(delegate as double, TimeUnit.Day)}




        }
        Number.metaClass.div = { Duration d -> new Frequency(delegate as int, d)}


		
		def configuration = new CompilerConfiguration()
		configuration.addCompilationCustomizers(secure)
		
		return configuration
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)
		
		binding.setScript(script)
		script.setBinding(binding)
		
		script.run()

	}

	SslBinding getBinding() {
		return binding
	}
}
