# OASE â€“ OSGi Application Server Environment

## Ãœberblick

OASE ist eine schlanke Entwicklungs- und Laufzeitplattform fÃ¼r OSGi-basierte Anwendungen. Sie stellt Anwendungsentwicklern eine vollstÃ¤ndig vorkonfigurierte Infrastruktur bereit â€“ eingebetteter Webserver, Datenbank, Identity & Access Management, HTTP-Client, Konfigurationsmanagement und Hot Deployment â€“ sodass sich Entwickler auf ihre fachliche Logik konzentrieren kÃ¶nnen.

Die Plattform basiert auf dem Eclipse Equinox OSGi-Framework und nutzt gezielt Apache Felix-Services (SCR, ConfigAdmin, EventAdmin, HTTP Jetty) als pragmatischen Mix beider Ã–kosysteme.

GesamtgrÃ¶ÃŸe der Laufzeitumgebung: ca. 19 MB.

---

## Common Application Runtime (CAR)

Das HerzstÃ¼ck von OASE ist **CAR** â€“ die Common Application Runtime. CAR bildet die Abstraktionsschicht zwischen dem OSGi-Framework und den darauf laufenden Anwendungen.

CAR besteht aus drei Artefakten:

| Artefakt | Ort | GrÃ¶ÃŸe | Aufgabe |
|---|---|---|---|
| `car-2.0.0.jar` | `lib/` | ~65 KB | Kern-API und Utilities (`biz.car.*`) |
| `car.osgi-2.0.0.jar` | `lib/` | ~33 KB | OSGi-Launcher und Framework-Bootstrap |
| `oase.car-2.0.0.jar` | `bundles/01_API/` | ~4 KB | API-Bundle fÃ¼r die OSGi-Service-Registry |

Die CAR-Bibliotheken in `lib/` werden bewusst nicht als OSGi-Bundles installiert, sondern Ã¼ber den System-Classloader geladen und via `org.osgi.framework.system.packages.extra` dem Framework zur VerfÃ¼gung gestellt. Dadurch teilen alle Bundles garantiert dieselben CAR-Klassen â€“ ohne Classloader-Konflikte.

Die exportierten CAR-Packages sind:

- `biz.car` â€“ Kernklassen (SYS, CAR, XLogger, XRuntimeException)
- `biz.car.config` â€“ Konfigurationszugriff (ACS, XConfig)
- `biz.car.util` â€“ Hilfsfunktionen (SFI, Delay, XTimestamp)
- `biz.car.io` â€“ I/O-Abstraktion (DirectoryWatcher, DirectoryListener)
- `biz.car.csv` â€“ CSV-Verarbeitung

ZusÃ¤tzlich werden **SLF4J** und **Typesafe Config** als System Packages exportiert, sodass alle Bundles eine einheitliche Logging- und Konfigurationsinstanz verwenden.

---

## Verzeichnisstruktur

```
OASE/
â”œâ”€â”€ bin/                    Startskripte und Windows-Service-Executables
â”œâ”€â”€ bundles/                Install Area â€“ Bundle-Repository und Deployment-Ziel
â”‚   â”œâ”€â”€ 01_API/             Startlevel 1 â€“ APIs und Interfaces
â”‚   â”œâ”€â”€ 02_CM/              Startlevel 2 â€“ Configuration Management
â”‚   â”œâ”€â”€ 03_SERVICE/         Startlevel 3 â€“ Service-Schicht
â”‚   â”œâ”€â”€ 04_DB/              Startlevel 4 â€“ Datenbank
â”‚   â”œâ”€â”€ 05_WEB/             Startlevel 5 â€“ Webserver und HTTP
â”‚   â””â”€â”€ *.jar               Startlevel 10 (Default) â€“ Management-Tools
â”œâ”€â”€ bundle-cache/           OSGi Storage Area â€“ interner Framework-Cache
â”œâ”€â”€ configuration/          Konfigurationsdateien
â”œâ”€â”€ H2/                     H2-Datenbankdateien
â”œâ”€â”€ lib/                    Launcher-Classpath (Equinox, CAR, Logback, Config)
â”œâ”€â”€ log/                    Log- und Diagnosedateien
â””â”€â”€ workspace/              OSGi Data Area â€“ anwendungsspezifische Daten
```

### Trennung der Bereiche

Die Plattform trennt vier Dateisystembereiche sauber voneinander:

| Bereich | Verzeichnis | Framework-Property | Zweck |
|---|---|---|---|
| Install Area | `bundles/` | `osgi.install.area` | Bundle-Repository; wird vom WatchService Ã¼berwacht |
| Storage Area | `bundle-cache/` | `org.osgi.framework.storage` | Interner Framework-Cache fÃ¼r Bundle-ZustÃ¤nde und Classloader |
| Data Area | `workspace/` | `framework.data.area` | Anwendungsspezifischer Datenbereich |
| Configuration | `configuration/` | `framework.configuration.area` | Zentrale Konfigurationsdateien |

---

## Bundle-Schichtung

Die Plattform-Bundles sind in nummerierten Unterordnern der Install Area organisiert. Die fÃ¼hrende Zahl im Ordnernamen definiert den OSGi-Startlevel:

### Startlevel 1 â€“ API

Grundlegende Interfaces und AbhÃ¤ngigkeiten: OSGi Service-APIs (UserAdmin, Preferences, SCR Component), Commons FileUpload/IO und das `oase.car` API-Bundle.

### Startlevel 2 â€“ Configuration Management

Apache Felix ConfigAdmin und MetaType fÃ¼r die zentrale, deklarative Konfigurationsverwaltung aller Bundles.

### Startlevel 3 â€“ Services

Apache Felix SCR (Declarative Services), EventAdmin fÃ¼r asynchrones Event-Handling, Equinox UserAdmin und Preferences fÃ¼r Benutzerverwaltung und Einstellungen.

### Startlevel 4 â€“ Datenbank

H2 (Version 2.2.224) als eingebettete Datenbank. Die IAM-Datenbank (`H2/IAM.mv.db`) stellt Identity & Access Management bereit.

### Startlevel 5 â€“ Web

Apache Felix HTTP Jetty als eingebetteter Webserver und `car.http-1.0.0.jar` â€“ ein abstrahiertes HTTP-Client-Interface auf Basis von OkHttp3.

### Startlevel 10 (Default) â€“ Management

JARs direkt unter `bundles/` erhalten den Default-Startlevel 10: Apache Felix WebConsole mit Plugins (DS, Memory, PackageAdmin), Gogo Shell, Equinox Console und eine eigene `oase.webconsole`. Diese Tools starten zuletzt, wenn alle Plattformdienste bereits verfÃ¼gbar sind.

Anwendungsentwickler integrieren eigene Bundles, indem sie einen Ordner wie `06_MYAPP` anlegen â€“ der Startlevel ergibt sich automatisch aus dem Ordnernamen.

Unterordner ohne fÃ¼hrende Zahl werden vom Deployment ignoriert und kÃ¶nnen fÃ¼r andere Zwecke genutzt werden.

---

## Launcher (car.osgi)

### Startsequenz

Der Launcher in `biz.car.osgi.Launcher` implementiert die vollstÃ¤ndige Startsequenz als `Runnable`:

1. **System Properties laden** â€“ Logback-Konfiguration (`logback.configurationFile`)
2. **Framework Properties laden** â€“ mit Fallback-Kaskade (siehe unten)
3. **Data Area erstellen** â€“ `workspace/` via `mkdirs()`
4. **Shutdown Hook registrieren** â€“ sauberes Herunterfahren bei VM-Exit
5. **Framework-Konfiguration aufbauen** â€“ Key-Mapping von CAR- auf Equinox-Properties
6. **Framework initialisieren** â€“ Equinox-Instanz via `ServiceLoader<FrameworkFactory>`
7. **Listener registrieren** â€“ Framework-, Bundle- und Service-Listener fÃ¼r Diagnose
8. **Install Area reconcilen** â€“ Bundles synchronisieren und starten
9. **Hot Deployment aktivieren** â€“ WatchService auf die Install Area (falls konfiguriert)
10. **Framework starten** â€“ blockiert bis zum Shutdown; unterstÃ¼tzt Framework-Updates via Restart-Loop

### Properties-Loading mit Fallback

Die Konfiguration folgt einem dreistufigen Override-Pattern auf Basis von Typesafe Config:

1. **Eingebettete Defaults** (`framework.default.properties` in den Ressourcen)
2. **Anwendungs-Properties** (via `ACS.parseResource()`)
3. **Dateisystem-Fallback** (externe `.properties`-Datei)

Jede Stufe Ã¼berschreibt die vorherige mittels `Config.withFallback()`. Benutzer mÃ¼ssen nur die Properties angeben, die sie Ã¤ndern wollen â€“ alles andere fÃ¤llt auf die Defaults zurÃ¼ck.

### Framework-Abstraktion durch Key-Mapping

Ein zentrales Designmerkmal: OASE definiert eine eigene, saubere Property-Nomenklatur und mappt diese zur Laufzeit auf die Equinox-spezifischen Keys. Die Zuordnung ist in `Equinox.properties` hinterlegt:

| CAR-Property | Equinox-Property |
|---|---|
| `bundle.startLevel` | `osgi.bundles.defaultStartLevel` |
| `framework.startLevel` | `org.osgi.framework.startlevel.beginning` |
| `framework.storage.area` | `org.osgi.framework.storage` |
| `framework.data.area` | `osgi.instance.area` |
| `framework.install.area` | `osgi.install.area` |
| `framework.console` | `osgi.console` |
| `bundle.cache.clean` | `org.osgi.framework.storage.clean` |

Ein Wechsel des OSGi-Frameworks (z.B. von Equinox auf Felix) erfordert lediglich eine neue Mapping-Datei â€“ der Anwendungscode und die `framework.properties` bleiben unberÃ¼hrt.

---

## Hot Deployment

### Architektur

Der `Deployer` implementiert das `DirectoryListener`-Interface von CAR und nutzt einen `DirectoryWatcher` (basierend auf Javas `WatchService`), der die gesamte Install Area rekursiv Ã¼berwacht.

### Debouncing

Bei DateiÃ¤nderungen wird nicht sofort reconciled. Stattdessen nutzt der Deployer einen `ScheduledExecutorService` mit 1 Sekunde VerzÃ¶gerung. Kommen in der Zwischenzeit weitere Events, wird der Timer zurÃ¼ckgesetzt. So werden Batch-Ã„nderungen (z.B. mehrere JARs gleichzeitig kopiert) in einem einzigen Zyklus verarbeitet.

### Reconciliation-Zyklus

Der Deployment-Zyklus folgt dem OSGi-Standardverfahren:

1. **Reconcile** â€“ `InstallArea` vergleicht die JARs im Dateisystem mit den installierten Bundles. Drei FÃ¤lle: JAR neu â†’ `installBundle()`, JAR neuer als Bundle â†’ `update()`, Bundle ohne JAR â†’ `uninstall()`.
2. **Refresh** â€“ `FrameworkWiring.refreshBundles()` lÃ¶st die PaketabhÃ¤ngigkeiten neu auf und bereinigt veraltete Classloader.
3. **Wait** â€“ Ein `CountDownLatch` wartet auf das `PACKAGES_REFRESHED`-Event (Timeout: 30 Sekunden).
4. **Start** â€“ Alle neu installierten und aktualisierten Bundles werden gestartet, Fragment-Bundles werden dabei Ã¼bersprungen.

### Startlevel-Zuweisung

Die `InstallArea.install()`-Methode extrahiert den Startlevel aus dem Dateipfad mittels Regex (`/\d{2}`). Ein JAR unter `bundles/03_SERVICE/` erhÃ¤lt Startlevel 3, ein JAR direkt unter `bundles/` den konfigurierten Default (10).

### Dynamische Verzeichniserkennung

Wird ein neuer Unterordner in der Install Area erstellt, erkennt der Deployer dies Ã¼ber `ENTRY_CREATE` und registriert den Ordner automatisch beim WatchService. Neue Anwendungsordner werden so ohne Neustart in das Hot Deployment einbezogen.

---

## Observability

### Logging

OASE nutzt Logback Ã¼ber SLF4J mit zwei separaten Log-KanÃ¤len:

- **Anwendungs-Logger** (`SYS.LOG`) â€“ fÃ¼r Framework-Lifecycle-Meldungen (Start, Stop, Fehler)
- **Diagnose-Logger** (`DIAG.LOG`) â€“ fÃ¼r detaillierte Framework-, Bundle- und Service-Events

Der Name des Diagnose-Loggers ist Ã¼ber `reference.conf` konfigurierbar (Default: `car.osgi.diagnose`).

### Framework-Diagnose

Beim Start schreibt `FrameworkDiagnose.accept()` einen vollstÃ¤ndigen Snapshot in die Diagnosedatei: alle Framework-Properties sowie fÃ¼r jedes installierte Bundle die ID, den State, die Version, den Startlevel, den Zeitstempel der letzten Ã„nderung und die Location.

### Event-Listener

Drei Listener protokollieren alle Laufzeitereignisse Ã¼ber den Diagnose-Logger:

- **XFrameworkListener** â€“ Framework-Events (STARTED, ERROR, PACKAGES REFRESHED, etc.)
- **XBundleListener** â€“ Bundle-Events (installed, started, stopped, updated, etc.) mit Ausnahme des System-Bundles
- **XServiceListener** â€“ Service-Events (REGISTERED, CHANGED, REMOVING)

Die Event-Typen werden Ã¼ber `BND.conf` von Integer-Werten in lesbare Strings gemappt.

---

## Konfiguration

### framework.properties

Die zentrale Konfigurationsdatei. Alle Properties sind optional â€“ nicht gesetzte Werte fallen auf die Defaults in `framework.default.properties` zurÃ¼ck.

Wichtige konfigurierbare Parameter:

| Property | Default | Beschreibung |
|---|---|---|
| `bundle.startLevel` | 10 | Default-Startlevel fÃ¼r neue Bundles |
| `framework.startLevel` | 10 | Framework-Startlevel (Obergrenze) |
| `framework.install.area` | `bundles` | Pfad zur Install Area |
| `framework.storage.area` | `bundle-cache` | Pfad zum Framework-Cache |
| `framework.data.area` | `workspace` | Pfad zur Data Area |
| `framework.hotdeploy.enabled` | `true` | Hot Deployment ein/aus |
| `framework.console` | (aktiviert) | Gogo Console (optional mit Port fÃ¼r Telnet) |
| `org.osgi.service.http.port` | 8282 | Port des eingebetteten Webservers |

### system.properties

System-Properties fÃ¼r die JVM, insbesondere der Pfad zur Logback-Konfiguration.

### Classloader-Konfiguration

`org.osgi.framework.bundle.parent = ext` setzt den Extension-Classloader als Parent fÃ¼r Bundles. Damit sind die JARs aus `lib/` (die Ã¼ber den Classpath geladen werden) fÃ¼r alle Bundles sichtbar.

---

## Windows-Service-Integration

OASE wird Ã¼ber Apache Commons Daemon (Procrun) als Windows-Service betrieben:

| Datei | Funktion |
|---|---|
| `OASE.exe` | Service-Runner (Procrun) |
| `OASEw.exe` | GUI-Monitor fÃ¼r den Service |
| `Service.bat` | Service-Installation und -Verwaltung |
| `winsvc.bat` | Konfiguration der Service-Parameter |
| `Console.bat` | Konsolenstart fÃ¼r Entwicklung |

Die Entry-Points `Main.main()` und `Main.stop()` werden von Procrun aufgerufen. Der Launcher lÃ¤uft in einem eigenen Thread, ein Shutdown Hook stellt sicher, dass Framework und WatchService bei VM-Exit sauber heruntergefahren werden.

---

## Plattformdienste fÃ¼r Anwendungsentwickler

Zusammengefasst stellt OASE folgende Dienste bereit:

- **CAR-API** â€“ Einheitliche Abstraktionsschicht fÃ¼r Konfiguration, Logging, I/O und Utilities
- **Identity & Access Management** â€“ UserAdmin-Service mit H2-persistierter IAM-Datenbank
- **Konfigurationsmanagement** â€“ Felix ConfigAdmin und MetaType fÃ¼r deklarative Bundle-Konfiguration
- **Event-System** â€“ Felix EventAdmin fÃ¼r asynchrone Kommunikation zwischen Bundles
- **HTTP-Client** â€“ Abstrahiertes Interface Ã¼ber OkHttp3, bereitgestellt als OSGi-Service
- **Webserver** â€“ Eingebetteter Jetty Ã¼ber Felix HTTP Service
- **Datenbank** â€“ Eingebettete H2-Datenbank
- **Hot Deployment** â€“ Dateibasiertes Deployment mit automatischer Reconciliation
- **Management-Console** â€“ Felix WebConsole und Gogo Shell zur Laufzeitinspektion

Entwickler programmieren gegen die CAR-APIs und die OSGi-Service-Registry. Die Infrastruktur wird vollstÃ¤ndig von der Plattform bereitgestellt und verwaltet.