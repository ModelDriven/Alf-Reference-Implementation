package org.modeldriven.alf.uml;


public interface LinkEndDestructionData extends LinkEndData {
	public boolean getIsDestroyDuplicates();

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates);

	public InputPin getDestroyAt();

	public void setDestroyAt(InputPin destroyAt);
}
