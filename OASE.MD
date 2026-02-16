# OASE – OSGi Application Server Environment

## Overview

OASE is a lightweight development and runtime platform for OSGi-based applications. It provides application developers with a fully preconfigured infrastructure – embedded web server, database, Identity & Access Management, HTTP client, configuration management, and hot deployment – so that developers can focus on their business logic.

The platform is based on the Eclipse Equinox OSGi framework and selectively uses Apache Felix services (SCR, ConfigAdmin, EventAdmin, HTTP Jetty) as a pragmatic mix of both ecosystems.

Total size of the runtime environment: approx. 19 MB.

---

## Common Application Runtime (CAR)

The heart of OASE is **CAR** – the Common Application Runtime. CAR forms the abstraction layer between the OSGi framework and the applications running on top of it.

CAR consists of three artifacts:

| Artifact | Location | Size | Purpose |
|---|---|---|---|
| `car-2.0.0.jar` | `lib/` | ~65 KB | Core API and utilities (`biz.car.*`) |
| `car.osgi-2.0.0.jar` | `lib/` | ~33 KB | OSGi launcher and framework bootstrap |
| `oase.car-2.0.0.jar` | `bundles/01_API/` | ~4 KB | API bundle for the OSGi service registry |

The CAR libraries in `lib/` are deliberately not installed as OSGi bundles. Instead, they are loaded via the system classloader and made available to the framework through `org.osgi.framework.system.packages.extra`. This ensures that all bundles share the same CAR classes – without classloader conflicts.

The exported CAR packages are:

- `biz.car` – Core classes (SYS, CAR, XLogger, XRuntimeException)
- `biz.car.config` – Configuration access (ACS, XConfig)
- `biz.car.util` – Utility functions (SFI, Delay, XTimestamp)
- `biz.car.io` – I/O abstraction (DirectoryWatcher, DirectoryListener)
- `biz.car.csv` – CSV processing

Additionally, **SLF4J** and **Typesafe Config** are exported as system packages, so that all bundles use a unified logging and configuration instance.

---

## Directory Structure

```
OASE/
├── bin/                    Startup scripts and Windows service executables
├── bundles/                Install area – bundle repository and deployment target
│   ├── 01_API/             Start level 1 – APIs and interfaces
│   ├── 02_CM/              Start level 2 – Configuration Management
│   ├── 03_SERVICE/         Start level 3 – Service layer
│   ├── 04_DB/              Start level 4 – Database
│   ├── 05_WEB/             Start level 5 – Web server and HTTP
│   └── *.jar               Start level 10 (default) – Management tools
├── bundle-cache/           OSGi storage area – internal framework cache
├── configuration/          Configuration files
├── H2/                     H2 database files
├── lib/                    Launcher classpath (Equinox, CAR, Logback, Config)
├── log/                    Log and diagnostic files
└── workspace/              OSGi data area – application-specific data
```

### Separation of Concerns

The platform cleanly separates four filesystem areas:

| Area | Directory | Framework Property | Purpose |
|---|---|---|---|
| Install Area | `bundles/` | `osgi.install.area` | Bundle repository; monitored by the WatchService |
| Storage Area | `bundle-cache/` | `org.osgi.framework.storage` | Internal framework cache for bundle states and classloaders |
| Data Area | `workspace/` | `framework.data.area` | Application-specific data area |
| Configuration | `configuration/` | `framework.configuration.area` | Central configuration files |

---

## Bundle Layering

The platform bundles are organized in numbered subdirectories within the install area. The leading number in the directory name defines the OSGi start level:

### Start Level 1 – API

Fundamental interfaces and dependencies: OSGi service APIs (UserAdmin, Preferences, SCR Component), Commons FileUpload/IO, and the `oase.car` API bundle.

### Start Level 2 – Configuration Management

Apache Felix ConfigAdmin and MetaType for centralized, declarative configuration management of all bundles.

### Start Level 3 – Services

Apache Felix SCR (Declarative Services), EventAdmin for asynchronous event handling, Equinox UserAdmin and Preferences for user management and settings.

### Start Level 4 – Database

H2 (version 2.2.224) as an embedded database. The IAM database (`H2/IAM.mv.db`) provides Identity & Access Management.

### Start Level 5 – Web

Apache Felix HTTP Jetty as an embedded web server, and `car.http-1.0.0.jar` – an abstracted HTTP client interface based on OkHttp3.

### Start Level 10 (Default) – Management

JARs located directly under `bundles/` receive the default start level 10: Apache Felix WebConsole with plugins (DS, Memory, PackageAdmin), Gogo Shell, Equinox Console, and a custom `oase.webconsole`. These tools start last, once all platform services are already available.

Application developers integrate their own bundles by creating a directory such as `06_MYAPP` – the start level is automatically derived from the directory name.

Subdirectories without a leading number are ignored by the deployment process and can be used for other purposes.

---

## Launcher (car.osgi)

### Startup Sequence

The launcher in `biz.car.osgi.Launcher` implements the complete startup sequence as a `Runnable`:

1. **Load system properties** – Logback configuration (`logback.configurationFile`)
2. **Load framework properties** – with fallback cascade (see below)
3. **Create data area** – `workspace/` via `mkdirs()`
4. **Register shutdown hook** – clean shutdown on VM exit
5. **Build framework configuration** – key mapping from CAR to Equinox properties
6. **Initialize framework** – Equinox instance via `ServiceLoader<FrameworkFactory>`
7. **Register listeners** – framework, bundle, and service listeners for diagnostics
8. **Reconcile install area** – synchronize and start bundles
9. **Activate hot deployment** – WatchService on the install area (if configured)
10. **Start framework** – blocks until shutdown; supports framework updates via restart loop

### Properties Loading with Fallback

The configuration follows a three-tier override pattern based on Typesafe Config:

1. **Embedded defaults** (`framework.default.properties` in resources)
2. **Application properties** (via `ACS.parseResource()`)
3. **Filesystem fallback** (external `.properties` file)

Each tier overrides the previous one using `Config.withFallback()`. Users only need to specify the properties they want to change – everything else falls back to the defaults.

### Framework Abstraction via Key Mapping

A central design feature: OASE defines its own clean property nomenclature and maps it at runtime to the Equinox-specific keys. The mapping is defined in `Equinox.properties`:

| CAR Property | Equinox Property |
|---|---|
| `bundle.startLevel` | `osgi.bundles.defaultStartLevel` |
| `framework.startLevel` | `org.osgi.framework.startlevel.beginning` |
| `framework.storage.area` | `org.osgi.framework.storage` |
| `framework.data.area` | `osgi.instance.area` |
| `framework.install.area` | `osgi.install.area` |
| `framework.console` | `osgi.console` |
| `bundle.cache.clean` | `org.osgi.framework.storage.clean` |

Switching the OSGi framework (e.g. from Equinox to Felix) only requires a new mapping file – the application code and the `framework.properties` remain untouched.

---

## Hot Deployment

### Architecture

The `Deployer` implements the `DirectoryListener` interface from CAR and uses a `DirectoryWatcher` (based on Java's `WatchService`) that recursively monitors the entire install area.

### Debouncing

File changes do not trigger an immediate reconciliation. Instead, the deployer uses a `ScheduledExecutorService` with a 1-second delay. If additional events arrive in the meantime, the timer is reset. This way, batch changes (e.g. multiple JARs copied simultaneously) are processed in a single cycle.

### Reconciliation Cycle

The deployment cycle follows the standard OSGi procedure:

1. **Reconcile** – `InstallArea` compares the JARs on the filesystem with the installed bundles. Three cases: new JAR → `installBundle()`, JAR newer than bundle → `update()`, bundle without JAR → `uninstall()`.
2. **Refresh** – `FrameworkWiring.refreshBundles()` re-resolves package dependencies and cleans up stale classloaders.
3. **Wait** – A `CountDownLatch` waits for the `PACKAGES_REFRESHED` event (timeout: 30 seconds).
4. **Start** – All newly installed and updated bundles are started; fragment bundles are skipped.

### Start Level Assignment

The `InstallArea.install()` method extracts the start level from the file path using a regex (`/\d{2}`). A JAR under `bundles/03_SERVICE/` receives start level 3; a JAR directly under `bundles/` receives the configured default (10).

### Dynamic Directory Detection

When a new subdirectory is created in the install area, the deployer detects it via `ENTRY_CREATE` and automatically registers the directory with the WatchService. New application directories are thus included in hot deployment without a restart.

---

## Observability

### Logging

OASE uses Logback via SLF4J with two separate log channels:

- **Application logger** (`SYS.LOG`) – for framework lifecycle messages (start, stop, errors)
- **Diagnostic logger** (`DIAG.LOG`) – for detailed framework, bundle, and service events

The name of the diagnostic logger is configurable via `reference.conf` (default: `car.osgi.diagnose`).

### Framework Diagnostics

At startup, `FrameworkDiagnose.accept()` writes a complete snapshot to the diagnostic file: all framework properties, and for each installed bundle the ID, state, version, start level, timestamp of the last modification, and location.

### Event Listeners

Three listeners log all runtime events via the diagnostic logger:

- **XFrameworkListener** – Framework events (STARTED, ERROR, PACKAGES REFRESHED, etc.)
- **XBundleListener** – Bundle events (installed, started, stopped, updated, etc.), excluding the system bundle
- **XServiceListener** – Service events (REGISTERED, CHANGED, REMOVING)

The event types are mapped from integer values to readable strings via `BND.conf`.

---

## Configuration

### framework.properties

The central configuration file. All properties are optional – unset values fall back to the defaults in `framework.default.properties`.

Key configurable parameters:

| Property | Default | Description |
|---|---|---|
| `bundle.startLevel` | 10 | Default start level for new bundles |
| `framework.startLevel` | 10 | Framework start level (upper bound) |
| `framework.install.area` | `bundles` | Path to the install area |
| `framework.storage.area` | `bundle-cache` | Path to the framework cache |
| `framework.data.area` | `workspace` | Path to the data area |
| `framework.hotdeploy.enabled` | `true` | Hot deployment on/off |
| `framework.console` | (enabled) | Gogo Console (optionally with port for Telnet) |
| `org.osgi.service.http.port` | 8282 | Port of the embedded web server |

### system.properties

System properties for the JVM, in particular the path to the Logback configuration.

### Classloader Configuration

`org.osgi.framework.bundle.parent = ext` sets the extension classloader as the parent for bundles. This makes the JARs from `lib/` (loaded via the classpath) visible to all bundles.

---

## Windows Service Integration

OASE is operated as a Windows service via Apache Commons Daemon (Procrun):

| File | Function |
|---|---|
| `OASE.exe` | Service runner (Procrun) |
| `OASEw.exe` | GUI monitor for the service |
| `Service.bat` | Service installation and management |
| `winsvc.bat` | Configuration of service parameters |
| `Console.bat` | Console startup for development |

The entry points `Main.main()` and `Main.stop()` are called by Procrun. The launcher runs in its own thread; a shutdown hook ensures that the framework and WatchService are shut down cleanly on VM exit.

---

## Platform Services for Application Developers

In summary, OASE provides the following services:

- **CAR API** – Unified abstraction layer for configuration, logging, I/O, and utilities
- **Identity & Access Management** – UserAdmin service with H2-persisted IAM database
- **Configuration Management** – Felix ConfigAdmin and MetaType for declarative bundle configuration
- **Event System** – Felix EventAdmin for asynchronous communication between bundles
- **HTTP Client** – Abstracted interface via OkHttp3, provided as an OSGi service
- **Web Server** – Embedded Jetty via Felix HTTP Service
- **Database** – Embedded H2 database
- **Hot Deployment** – File-based deployment with automatic reconciliation
- **Management Console** – Felix WebConsole and Gogo Shell for runtime inspection

Developers program against the CAR APIs and the OSGi service registry. The infrastructure is fully provided and managed by the platform.
