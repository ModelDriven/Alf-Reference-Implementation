/*******************************************************************************
 * Copyright (c) 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.parser;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public interface Parser {

  public void setFileName(String fileName);

  public String getFileName();

  public void setTabSize(int tabSize);
  
  public int getTabSize();
  
  public void provideInfo(ParsedElement element, boolean fromNextToken);

  public UnitDefinition UnitDefinition() throws ParseException;
  public UnitDefinition UnitDefinitionEOF() throws ParseException;
  
  public Block StatementSequence() throws ParseException;
  public Block StatementSequenceEOF() throws ParseException;
  
  public Expression Expression() throws ParseException;
  public Expression ExpressionEOF() throws ParseException;

}