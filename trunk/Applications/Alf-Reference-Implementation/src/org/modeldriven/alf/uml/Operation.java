package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.BehavioralFeature;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Parameter;
import org.modeldriven.alf.uml.Type;

public interface Operation extends BehavioralFeature {
	public boolean getIsQuery();

	public void setIsQuery(boolean isQuery);

	public boolean getIsOrdered();

	public boolean getIsUnique();

	public int getLower();

	public int getUpper();

	public Class getClass_();

	public List<Operation> getRedefinedOperation();

	public void addRedefinedOperation(Operation redefinedOperation);

	public Type getType();

	public List<Parameter> getOwnedParameter();

	public void addOwnedParameter(Parameter ownedParameter);

    public boolean isConstructor();

    public boolean isDestructor();
}
