# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**OASE** (Open Application Service Engine) v2.0.0 — an OSGi 8.0.0 application built on the **CAR** (Common Application Runtime, `biz.car`) framework. All modules are OSGi bundles built with bnd-maven-plugin and deployed to `C:/IDE/XENV/OASE/bundles/`.

## Build Commands

```bash
# Build all modules
mvn clean package

# Build and deploy to XENV (copies JARs to C:/IDE/XENV/OASE/bundles/)
mvn clean install

# Build a specific module (with dependencies)
mvn -am -pl oase.car clean install
```

Java 17 required (`maven.compiler.release=17`). No test suite exists in this repository.

## Running

```batch
# Interactive console mode
bin\Console.bat

# With remote debug on port 5005
bin\Console.bat debug

# Windows service management
bin\winsvc.bat install [service_name]
bin\winsvc.bat remove [service_name]
```

The launcher (`biz.car.osgi.Main`) bootstraps the OSGi framework (Eclipse OSGi 3.21.0) from `lib/` and `configuration/`.

## Architecture

### Module Layout

| Bundle | Category folder | Purpose |
|--------|----------------|---------|
| `oase.car` | `01_API` | Core constants, configuration, CAR extension; exports `biz.oase.car` |
| `oase.js` | `03_SERVICE` | Job Service with Apache Felix Gogo shell integration |
| `oase.h2` | `04_DB` | H2 Web Console as a Declarative Service (port 11212) |
| `oase.jdbc` | `04_DB` | JDBC connection pooling via HikariCP; registers OSGi JDBC service |
| `oase.http` | `05_WEB` | HTTP client (OkHttp3) — factory methods `GET/POST/PUT/DELETE/PATCH` |
| `oase.sm` | `06_SM` | Sort/Merge service; registers `Sorter`/`Merger` in CAR `ClassUtil.Registry` |
| `oase.sr` | — | System Reconciliation; depends on `oase.sm` + `oase.car` |
| `oase.webconsole` | root | Fragment-Host for Apache Felix Web Console (branding only) |

### Key Patterns

- **OSGi Declarative Services**: `@Component` with `@Activate`/`@Deactivate`; configuration via `@Designate` + Metatype.
- **Bundle Activators**: Legacy `BundleActivator` used in `oase.car`, `oase.js`, `oase.sm`, `oase.sr` for ClassUtil registration.
- **CAR ClassUtil.Registry**: Polymorphic procedure lookup — modules register implementation classes by name at activation.
- **Configuration Admin**: Runtime reconfiguration via OSGi Config Admin; configs live under `configuration/` in XENV.
- **Embedded dependencies**: OkHttp3 + Kotlin stdlib (oase.http), HikariCP (oase.jdbc), H2 engine (oase.h2) are bundled via bnd `-includeresource`.

### OSGi Manifest

Each module's `bnd.bnd` drives the generated manifest — exports, private packages, embedded JARs, and `Bundle-Activator`. The root `pom.xml` owns shared dependency versions; individual module `pom.xml` files handle artifact copying to XENV on `install`.

### Dependency Chain

```
oase.sr  →  oase.sm  →  oase.car  →  CAR framework (external)
oase.js  →  oase.car
oase.h2, oase.jdbc, oase.http  →  OSGi core only
oase.webconsole  →  Felix Web Console (fragment)
```

CAR framework JARs are `<scope>provided</scope>` — they are supplied by the runtime XENV, not packaged into bundles.
