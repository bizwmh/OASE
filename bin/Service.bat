@REM @ECHO OFF

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
	goto :SETUP_JAVA_HOME
)
if exist "%PARENT_DIR%\bin\Console.bat" (
	set "OASE_HOME=%PARENT_DIR%"
	goto :SETUP_JAVA_HOME
)

@REM ---------------------------------------------------------------------------
:BAD_HOME
@REM ---------------------------------------------------------------------------

echo OASE_HOME is set to %OSGI_HOME%
echo OASE_HOME environment variable is not set or points to the wrong directory.
echo Please set OASE_HOME correctly and launch OASE again.
set ERRORLEVEL=1

goto EXIT

@REM ---------------------------------------------------------------------------
:SETUP_JAVA_HOME
@REM ---------------------------------------------------------------------------

set JAVA=java
if exist "%JAVA_HOME%\bin\java.exe" (
	set JAVA="%JAVA_HOME%\bin\java.exe"
	if exist "%JAVA_HOME%\bin\server\jvm.dll" (
		set "JAVA_OPTS=-server"
	)
)

@REM ---------------------------------------------------------------------------
@REM Setup Classpath
@REM ---------------------------------------------------------------------------

set "CLASSPATH=lib\*"
set "CLASSPATH=%CLASSPATH%;configuration"

:homeOK
cd %OASE_HOME%
set DEFAULT_SERVICE_NAME=OASE

echo OASE_HOME              set to %OASE_HOME%
echo DEFAULT_SERVICE_NAME   set to %DEFAULT_SERVICE_NAME%
echo CLASSPATH              set to %CLASSPATH%

call bin\winsvc.bat %USER_ARGS%

cd %CURRENT_DIR%
pause
exit /b 0
