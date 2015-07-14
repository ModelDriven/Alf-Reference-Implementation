@rem ***************************************************************************
@rem Copyright 2015 Data Access Technologies, Inc. (Model Driven Solutions)
@rem
@rem All rights reserved worldwide. This program and the accompanying materials
@rem are made available for under the terms of the GNU General Public License 
@rem (GPL) version 3 that accompanies this distribution and is available at 
@rem http://www.gnu.org/licenses/gpl-3.0.html.
@rem ***************************************************************************
@echo off

set lib=./UML/Libraries
if not "%UML_LIB%" == "" set lib=%UML_LIB%

java -jar dist/alf-eclipse.jar -l %UML_LIB% %*
