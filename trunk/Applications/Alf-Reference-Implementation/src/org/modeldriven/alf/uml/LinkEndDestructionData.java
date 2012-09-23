package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.LinkEndData;

public interface LinkEndDestructionData extends LinkEndData {
	public boolean getIsDestroyDuplicates();

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates);

	public InputPin getDestroyAt();

	public void setDestroyAt(InputPin destroyAt);
}
