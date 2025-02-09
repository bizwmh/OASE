@ECHO OFF

@REM ---------------------------------------------------------------------------
@REM Copyright by Wolfgang Mueller-Haas
@REM ---------------------------------------------------------------------------

setlocal

@REM ---------------------------------------------------------------------------
@REM Initialize basic parameters
@REM ---------------------------------------------------------------------------

set USER_ARGS=%*
set "CURRENT_DIR=%cd%"
cd ..
set "PARENT_DIR=%cd%"
cd %CURRENT_DIR%

@REM ---------------------------------------------------------------------------
@REM Try to find OASE_HOME
@REM ---------------------------------------------------------------------------

if exist "%OASE_HOME%\bin\Console.bat" goto :SETUP_JVM
if not "%OASE_HOME%" == "" goto BAD_HOME
if exist "%CURRENT_DIR%\bin\Console.bat" (
	set OASE_HOME=%CURRENT_DIR%"
	goto :SETUP_JVM
)
if exist "%PARENT_DIR%\bin\Console.bat" (
	set "OASE_HOME=%PARENT_DIR%"
	goto :SETUP_JVM
)

@REM ---------------------------------------------------------------------------
:BAD_HOME
@REM ---------------------------------------------------------------------------

echo OASE_HOME is set to %OSGI_HOME%
echo OASE_HOME environment variable is not set or points to the wrong directory.
echo Please set OASE_HOME correctly and launch CAR OSGi again.
set ERRORLEVEL=1

goto EXIT

@REM ---------------------------------------------------------------------------
:SETUP_JVM
@REM ---------------------------------------------------------------------------

set JAVA=java
if exist "%JAVA_HOME%\bin\java.exe" (
	set JAVA="%JAVA_HOME%\bin\java.exe"
	if exist "%JAVA_HOME%\bin\server\jvm.dll" (
		set "JAVA_OPTS=-server"
	)
)
set JAVA_OPTS=%JAVA_OPTS% -Xms32M -Xmx128M

if "%1" == "debug" (
   set JAVA_OPTS=%JAVA_OPTS% -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005
   shift)

@REM ---------------------------------------------------------------------------
@REM Setup Classpath
@REM ---------------------------------------------------------------------------

set "CLASSPATH=lib\*"
set "CLASSPATH=%CLASSPATH%;configuration"

@REM ---------------------------------------------------------------------------
@REM Set user vars
@REM ---------------------------------------------------------------------------

@REM set USER_ARGS=%*

@REM ---------------------------------------------------------------------------
@REM LAUNCH CAR OSGi
@REM ---------------------------------------------------------------------------

echo OASE_HOME      is set to %OASE_HOME%
echo JAVA           is set to %JAVA%
echo JAVA_OPTS      is set to %JAVA_OPTS%
echo CLASSPATH      is set to %CLASSPATH%
echo USER_ARGS      is set to %USER_ARGS%
echo:

cd %OASE_HOME%

@REM ---------------------------------------------------------------------------
:LAUNCH
@REM ---------------------------------------------------------------------------

%JAVA% %JAVA_OPTS% -cp %CLASSPATH% biz.car.osgi.launch.Main %USER_ARGS%

@REM ---------------------------------------------------------------------------
:EXIT
@REM ---------------------------------------------------------------------------
cd %CURRENT_DIR%
endlocal

exit /b %ERRORLEVEL%