@rem ***************************************************************************
@rem Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
@rem
@rem All rights reserved worldwide. This program and the accompanying materials
@rem are made available for under the terms of the GNU General Public License 
@rem (GPL) version 3 that accompanies this distribution and is available at 
@rem http://www.gnu.org/licenses/gpl-3.0.html.
@rem ***************************************************************************
@echo off

set lib=
if not "%ALF_LIB%" == "" set lib=-l %ALF_LIB%

java -jar dist/alf.jar %lib% %*
