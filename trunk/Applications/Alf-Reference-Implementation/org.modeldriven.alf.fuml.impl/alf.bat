@rem ***************************************************************************
@rem Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
@rem All rights reserved worldwide. This program and the accompanying materials
@rem are made available for under the terms of the GNU General Public License 
@rem (GPL) version 3 that accompanies this distribution and is available at 
@rem http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
@rem contact Model Driven Solutions.
@rem ***************************************************************************
@echo off

set lib=
if not "%ALF_LIB%" == "" set lib=-l %ALF_LIB%

set cp=-cp
set cp=%cp% lib\alf.jar
set cp=%cp%;lib\fuml.jar
set cp=%cp%;lib\log4j.jar
set cp=%cp%;lib\commons-logging.jar

java %cp% org.modeldriven.alf.fuml.impl.execution.Alf %lib% %*
