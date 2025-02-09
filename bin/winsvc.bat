@echo off
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

rem ---------------------------------------------------------------------------
rem NT Service Install/Uninstall script
rem
rem Usage: service.bat install/remove [service_name [--rename]] [--user username]
rem
rem Options
rem install                 Install the service using default settings.
rem remove                  Remove the service from the system.
rem
rem service_name (optional) The name to use for the service. If not specified,
rem                         Tomcat10 is used as the service name.
rem
rem --rename     (optional) Rename tomcat10.exe and tomcat10w.exe to match
rem                         the non-default service name.
rem
rem username     (optional) The name of the OS user to use to install/remove
rem                         the service (not the name of the OS user the
rem                         service will run as). If not specified, the current
rem                         user is used.
rem ---------------------------------------------------------------------------

setlocal

set "SELF=%~dp0%winsvc.bat"
set SERVICE_NAME=%DEFAULT_SERVICE_NAME%

set "CURRENT_DIR=%cd%"

rem Parse the arguments
if "x%1x" == "xx" goto displayUsage
set SERVICE_CMD=%1
shift
if "x%1x" == "xx" goto checkEnv
:checkUser
if "x%1x" == "x/userx" goto runAsUser
if "x%1x" == "x--userx" goto runAsUser
set SERVICE_NAME=%1
shift
if "x%1x" == "xx" goto checkEnv
if "x%1x" == "x--renamex" (
    set RENAME=%1
    shift
)
if "x%1x" == "xx" goto checkEnv
goto checkUser
:runAsUser
shift
if "x%1x" == "xx" goto displayUsage
set SERVICE_USER=%1
shift
runas /env /savecred /user:%SERVICE_USER% "%COMSPEC% /K \"%SELF%\" %SERVICE_CMD% %SERVICE_NAME%"
exit /b 0

rem Check the environment
:checkEnv

rem Guess OASE_HOME if not defined
if not "%OASE_HOME%" == "" goto gotHome
set "OASE_HOME=%cd%"
if exist "%OASE_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe" goto gotHome
if exist "%OASE_HOME%\bin\%SERVICE_NAME%.exe" goto gotHome
rem CD to the upper dir
cd ..
set "OASE_HOME=%cd%"
:gotHome
if exist "%OASE_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe" (
    set "EXECUTABLE=%OASE_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe"
    goto okHome
)
if exist "%OASE_HOME%\bin\%SERVICE_NAME%.exe" (
    set "EXECUTABLE=%OASE_HOME%\bin\%SERVICE_NAME%.exe"
    goto okHome
)
if "%DEFAULT_SERVICE_NAME%"== "%SERVICE_NAME%" (
    echo The file %DEFAULT_SERVICE_NAME%.exe was not found...
) else (
    echo Neither the %DEFAULT_SERVICE_NAME%.exe file nor the %SERVICE_NAME%.exe file was found...
)
echo Either the OASE_HOME environment variable is not defined correctly or
echo the incorrect service name has been used.
echo Both the OASE_HOME environment variable and the correct service name
echo are required to run this program.
exit /b 1
:okHome
cd "%CURRENT_DIR%"

rem Make sure prerequisite environment variables are set
if not "%JAVA_HOME%" == "" goto gotJdkHome
if not "%JRE_HOME%" == "" goto gotJreHome
echo Neither the JAVA_HOME nor the JRE_HOME environment variable is defined
echo Service will try to guess them from the registry.
goto okJavaHome
:gotJreHome
if not exist "%JRE_HOME%\bin\java.exe" goto noJavaHome
goto okJavaHome
:gotJdkHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome
rem Java 9 has a different directory structure
if exist "%JAVA_HOME%\jre\bin\java.exe" goto preJava9Layout
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not "%JRE_HOME%" == "" goto okJavaHome
set "JRE_HOME=%JAVA_HOME%"
goto okJavaHome
:preJava9Layout
if not "%JRE_HOME%" == "" goto okJavaHome
set "JRE_HOME=%JAVA_HOME%\jre"
goto okJavaHome
:noJavaHome
echo The JAVA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
echo NB: JAVA_HOME should point to a JDK not a JRE
exit /b 1
:okJavaHome
if not "%OASE_BASE%" == "" goto gotBase
set "OASE_BASE=%OASE_HOME%"
:gotBase

rem Java 9 no longer supports the java.endorsed.dirs
rem system property. Only try to use it if
rem JAVA_ENDORSED_DIRS was explicitly set
rem or OASE_HOME/endorsed exists.
set ENDORSED_PROP=ignore.endorsed.dirs
if "%JAVA_ENDORSED_DIRS%" == "" goto noEndorsedVar
set ENDORSED_PROP=java.endorsed.dirs
goto doneEndorsed
:noEndorsedVar
if not exist "%OASE_HOME%\endorsed" goto doneEndorsed
set ENDORSED_PROP=java.endorsed.dirs
:doneEndorsed

rem Process the requested command
if /i %SERVICE_CMD% == install goto doInstall
if /i %SERVICE_CMD% == remove goto doRemove
if /i %SERVICE_CMD% == uninstall goto doRemove
echo Unknown parameter "%SERVICE_CMD%"
:displayUsage
echo.
echo Usage: service.bat install/remove [service_name [--rename]] [--user username]
exit /b 1

:doRemove
rem Remove the service
echo Removing the service '%SERVICE_NAME%' ...
echo Using OASE_BASE:      "%OASE_BASE%"

"%EXECUTABLE%" //DS//%SERVICE_NAME% ^
    --LogPath "%OASE_HOME%\log"
if not errorlevel 1 goto removed
echo Failed removing '%SERVICE_NAME%' service
exit /b 1
:removed
echo The service '%SERVICE_NAME%' has been removed
if exist "%OASE_HOME%\bin\%SERVICE_NAME%.exe" (
    rename "%SERVICE_NAME%.exe" "%DEFAULT_SERVICE_NAME%.exe"
    rename "%SERVICE_NAME%w.exe" "%DEFAULT_SERVICE_NAME%w.exe"
)
exit /b 0

:doInstall
rem Install the service
echo Installing the service '%SERVICE_NAME%' ...
echo Using OASE_HOME:       "%OASE_HOME%"
echo Using OASE_BASE:       "%OASE_BASE%"
echo Using JAVA_HOME:       "%JAVA_HOME%"
echo Using JRE_HOME:        "%JRE_HOME%"

rem Try to use the server jvm
set "JVM=%JRE_HOME%\bin\server\jvm.dll"
if exist "%JVM%" goto foundJvm
rem Try to use the client jvm
set "JVM=%JRE_HOME%\bin\client\jvm.dll"
if exist "%JVM%" goto foundJvm
echo Warning: Neither 'server' nor 'client' jvm.dll was found at JRE_HOME.
set JVM=auto
:foundJvm
echo Using JVM:             "%JVM%"

if "%SERVICE_STARTUP_MODE%" == "" set SERVICE_STARTUP_MODE=auto
if "%JvmMs%" == "" set JvmMs=128
if "%JvmMx%" == "" set JvmMx=256

if exist "%OASE_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe" (
    if "x%RENAME%x" == "x--renamex" (
        rename "%DEFAULT_SERVICE_NAME%.exe" "%SERVICE_NAME%.exe"
        rename "%DEFAULT_SERVICE_NAME%w.exe" "%SERVICE_NAME%w.exe"
        set "EXECUTABLE=%OASE_HOME%\bin\%SERVICE_NAME%.exe"
    )
)

"%EXECUTABLE%" //IS//%SERVICE_NAME% ^
    --Description "Open Application Service Engine" ^
    --DisplayName "OASE" ^
    --Install "%EXECUTABLE%" ^
    --LogPath "%OASE_HOME%\log" ^
    --StdOutput auto ^
    --StdError auto ^
    --Classpath "%CLASSPATH%" ^
    --Jvm "%JVM%" ^
    --StartMode jvm ^
    --StopMode jvm ^
    --StartPath "%OASE_HOME%" ^
    --StopPath "%OASE_HOME%" ^
    --StartClass biz.car.osgi.launch.Main ^
    --StopClass biz.car.osgi.launch.Main ^
    --StartMethod main ^
    --StopMethod stop ^
    --JvmOptions "%JvmArgs%" ^
    --Startup "%SERVICE_STARTUP_MODE%" ^
    --JvmMs "%JvmMs%" ^
    --JvmMx "%JvmMx%"
if not errorlevel 1 goto installed
echo Failed installing '%SERVICE_NAME%' service
exit /b 1
:installed
echo The service '%SERVICE_NAME%' has been installed.
exit /b 0