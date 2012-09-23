package org.modeldriven.alf.uml;

import java.util.List;

public interface Comment {
	public List<Element> getAnnotatedElement();

	public void addAnnotatedElement(Element annotatedElement);

	public String getBody();

	public void setBody(String body);
}
