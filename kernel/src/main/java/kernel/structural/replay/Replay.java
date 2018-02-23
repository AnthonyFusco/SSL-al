package kernel.structural.replay;

import kernel.structural.laws.DataSource;
import kernel.visitor.Visitable;

public interface Replay extends DataSource, Visitable {

    String getExecutableName();
}
