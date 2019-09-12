/*******************************************************************************
 * Copyright (c) 2018-2019 Data Access Technologies, Inc. (Model Driven Solutions)
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.parser;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public interface Parser {

  public void setFileName(String fileName);

  public String getFileName();

  public void setTabSize(int tabSize);
  
  public int getTabSize();
  
  public void provideInfo(ParsedElement element, boolean fromNextToken);

  /**
   * Parses the next unit definition, even if there are errors. 
   * 
   * Use {@link #getProblems()} to query any errors that may have arisen.
   * 
   * @param     eof     whether EOF is expected after the unit 
   * @return    the parsed unit
   * 
   * @see #getProblems()
   */
  public UnitDefinition parseUnitDefinition(boolean eof);
  
  /**
   * Parses the next sequence of statements, even if there are errors. 
   * 
   * Use {@link #getProblems()} to query any errors that may have arisen.
   * 
   * @param     eof     whether EOF is expected after the statement sequence 
   * @return    the parsed statement sequence
   * 
   * @see #getProblems()
   */
  public Block parseStatementSequence(boolean eof);
  
  
  /**
   * Parses the next expression, even if there are errors. 
   * 
   * Use {@link #getProblems()} to query any errors that may have arisen.
   * 
   * @param     eof     whether EOF is expected after the expression 
   * @return    the parsed expression
   * 
   * @see #getProblems()
   */
  public Expression parseExpression(boolean eof);
  
  /**
   * Parses the next type name (qualified name, possibly including template bindings, or "any"), 
   * even if there are errors. 
   * 
   * Use {@link #getProblems()} to query any errors that may have arisen.
   * 
   * @return    the parsed type name
   * 
   * @see #getProblems()
   */
  public QualifiedName parseTypeName();

  
  /**
   * Returns a list of problems collected by this parser since the last "parse" operation.
   * 
   * @see #parseUnitDefinition(boolean)
   * @see #parseStatementSequence(boolean)
   * @see #parseUnitDefinition(boolean)
   * 
   * @return the errors collected since the last call to a parse operation.
   */
  public Collection<SourceProblem> getProblems();
}