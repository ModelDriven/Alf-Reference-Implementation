@rem ***************************************************************************
@rem Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
@rem All rights reserved worldwide. This program and the accompanying materials
@rem are made available for under the terms of the GNU General Public License 
@rem (GPL) version 3 that accompanies this distribution and is available at 
@rem http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
@rem contact Model Driven Solutions.
@rem ***************************************************************************
ECHO ON

java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example1a.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example1b.alf

java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2a.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2b.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2c.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2d.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2e.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2f.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2g.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example2h.alf

java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3a.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3b.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3c.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3d.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3e.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3f.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3g.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3h.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3i.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Example3j.alf

java -cp bin org.modeldriven.alf.parser.AlfParser tests\ActiveClass_Order.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\ActiveClass_ProcessQueue.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\ActiveClass_ProcessQueueImpl.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\ActiveClass_Receptions.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Activity_execute.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Activity_Expressions.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Activity_getNodeActivations.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Activity_Order_Behavior.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Activity_Statements.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Assoc_Selection.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_Constructors.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_Destructors.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_Initialization.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_Operations.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_Properties.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_Redefinitions.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_Singleton.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Class_SuperConstructors.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Datatype_Complex.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Enum_TrafficLightColor.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Package_Ordering.alf
java -cp bin org.modeldriven.alf.parser.AlfParser tests\Signal_SubmitCharge.alf

ECHO ON
