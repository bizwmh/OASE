/* --------------------------------------------------------------------------
 * Project: OASE H2 Web Console
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.h2;

import org.h2.tools.Server;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import biz.car.SYS;
import biz.oase.h2.bundle.MSG;

/**
 * OSGi Declarative Service that starts and manages the H2 Web Console.
 * 
 * <p>The H2 Console provides a web-based interface for managing H2 databases.
 * Default URL: http://localhost:8082
 * 
 * @version 2.0.0 28.02.2026 05:31:34
 */
@Component(
    immediate = true,
    configurationPolicy = ConfigurationPolicy.OPTIONAL,
    service = H2Console.class
)
@Designate(ocd = H2Console.Config.class)
public class H2Console {

    @ObjectClassDefinition(
        name = "H2 Web Console Configuration",
        description = "Configuration for the H2 Database Web Console"
    )
    public @interface Config {
        
        @AttributeDefinition(
            name = "Enable Console",
            description = "Enable or disable the H2 Web Console"
        )
        boolean enabled() default true;
        
        @AttributeDefinition(
            name = "Console Port",
            description = "Port number for the H2 Web Console (default: 11212)"
        )
        int port() default 11212;
        
        @AttributeDefinition(
            name = "Allow Remote Access",
            description = "Allow connections from remote hosts (default: false for security)"
        )
        boolean allowOthers() default false;
        
        @AttributeDefinition(
            name = "Enable SSL",
            description = "Use SSL for the web console connection"
        )
        boolean useSSL() default false;
        
        @AttributeDefinition(
            name = "Open Browser",
            description = "Automatically open browser when console starts (disable in server environments)"
        )
        boolean openBrowser() default false;
    }
    
    private Server webServer;
    private Config config;
    
    @Activate
    protected void activate(Config aConfig) throws Exception {
        config = aConfig;
        
        if (!config.enabled()) {
            SYS.LOG.info(MSG.CONSOLE_DISABLED);
            return;
        }
        
        // Build H2 Console arguments
        String[] args = buildConsoleArgs(config);
        
        // Start the H2 Web Console server
        webServer = Server.createWebServer(args);
        webServer.start();
        
        String url = webServer.getURL();
        System.out.println("========================================"); //$NON-NLS-1$
        System.out.println("H2 Web Console started"); //$NON-NLS-1$
        System.out.println("URL: " + url); //$NON-NLS-1$
        System.out.println("Port: " + config.port()); //$NON-NLS-1$
        System.out.println("Remote Access: " + (config.allowOthers() ? "Enabled" : "Disabled")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        System.out.println("SSL: " + (config.useSSL() ? "Enabled" : "Disabled")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        System.out.println("========================================"); //$NON-NLS-1$
        System.out.println("To connect to your database:"); //$NON-NLS-1$
        System.out.println("  JDBC URL: jdbc:h2:../H2/DB"); //$NON-NLS-1$
        System.out.println("  Driver: org.h2.Driver"); //$NON-NLS-1$
        System.out.println("  User: sa"); //$NON-NLS-1$
        System.out.println("  Password: (empty)"); //$NON-NLS-1$
        System.out.println("========================================"); //$NON-NLS-1$
    }
    
    @Deactivate
    protected void deactivate() {
        if (webServer != null && webServer.isRunning(false)) {
            webServer.stop();
            SYS.LOG.info(MSG.CONSOLE_STOPPED);
            webServer = null;
        }
    }
    
    /**
     * Builds the command-line arguments for H2 Console server.
     */
    private String[] buildConsoleArgs(Config config) {
        java.util.List<String> args = new java.util.ArrayList<>();
        
        // Port
        args.add("-webPort"); //$NON-NLS-1$
        args.add(String.valueOf(config.port()));
        
        // Allow remote access
        if (config.allowOthers()) {
            args.add("-webAllowOthers"); //$NON-NLS-1$
        }
        
        // SSL
        if (config.useSSL()) {
            args.add("-webSSL"); //$NON-NLS-1$
        }
        
        // Don't open browser automatically in OSGi environment
        if (!config.openBrowser()) {
            args.add("-webDaemon"); //$NON-NLS-1$
        }      
        return args.toArray(new String[0]);
    }
    
    /**
     * Gets the H2 Console URL.
     * 
     * @return the console URL or null if not running
     */
    public String getConsoleURL() {
        return webServer != null ? webServer.getURL() : null;
    }
    
    /**
     * Checks if the console is running.
     * 
     * @return true if console is running
     */
    public boolean isRunning() {
        return webServer != null && webServer.isRunning(false);
    }
}
