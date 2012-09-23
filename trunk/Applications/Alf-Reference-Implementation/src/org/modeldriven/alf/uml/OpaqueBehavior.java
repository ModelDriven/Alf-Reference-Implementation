package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.Behavior;

public interface OpaqueBehavior extends Behavior {
	public List<String> getBody();

	public void addBody(String body);

	public List<String> getLanguage();

	public void addLanguage(String language);
}
