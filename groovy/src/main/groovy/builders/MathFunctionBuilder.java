package builders;

import dsl.SslModel;
import kernel.structural.laws.MathFunctionLaw;
import kernel.structural.laws.MathFunctionReturnType;

import java.util.Map;

public class MathFunctionBuilder extends LawBuilder<MathFunctionLaw> {

    private MathFunctionReturnType returnType;
    private Map<String, String> mapExpressionsConditions;

    public MathFunctionBuilder(String lawName) {
        super(lawName);
    }

    public MathFunctionBuilder itReturns(String returnType) {
        this.returnType = MathFunctionReturnType.valueOf(returnType);
        return this;
    }

    public MathFunctionBuilder withExpressions(Map<String, String> expressions) {
        this.mapExpressionsConditions = expressions;
        return this;
    }

    @Override
    public MathFunctionLaw build() {
        MathFunctionLaw law = new MathFunctionLaw();
        law.setName(getLawName());
        law.setDomain(returnType);
        mapExpressionsConditions.forEach(law::addFunction);
        return law;
    }

    @Override
    public void validate(SslModel model) {

    }
}
