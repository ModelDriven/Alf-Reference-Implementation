package org.modeldriven.alf.fuml.impl.uml;

public class Model extends Package implements org.modeldriven.alf.uml.Model {
    // NOTE: The fUML subset does not include Model, so a regular Package is
    // used as the base for the Model interface.
    
    public Model() {
        this(new fUML.Syntax.Classes.Kernel.Package());
    }

    public Model(fUML.Syntax.Classes.Kernel.Package base) {
        super(base);
    }


}
