/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.car;

import java.io.File;
import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;
import org.osgi.framework.startlevel.BundleStartLevel;

import biz.car.SYS;
import biz.oase.car.bundle.MSG;

/**
 * A framework bundle located in the bundle cache.<br>
 * Provides access to functions which are related to OSGi bundles. An instance
 * of this class represents the LOG context bundle hosting the OSGi framework.
 * An instance of this class is valid only when the OSGi bundle is in a state
 * between STARTING and STOPPED.
 *
 * @version 1.0.0 07.02.2025 15:04:08
 */
public class XBundle {

	private Bundle myBundle;

	/**
	 * Creates a default <code>XBundle</code> instance.
	 * 
	 * @param aBundle the bundle from the framework's bundle cache.
	 */
	public XBundle(Bundle aBundle) {
		super();

		myBundle = aBundle;
	}

	/**
	 * Creates a default <code>XBundle</code> instance.
	 * 
	 * @param aClass the class being a resource of the bundle.
	 */
	public XBundle(Class<?> aClass) {
		super();

		myBundle = createHostBundle(aClass);
	}

	/**
	 * Creates a default <code>XBundle</code> instance.
	 */
	protected XBundle() {
		super();

		myBundle = createHostBundle(getClass());
	}

	@Override
	public boolean equals(Object aResource) {
		boolean l_ret = false;

		if (aResource != null) {
			if (aResource instanceof XBundle) {
				XBundle l_bundle = (XBundle) aResource;
				l_ret = id().equals(l_bundle.id());
			}
		}
		return l_ret;
	}

	/**
	 * @return the persistent storage area for this bundle provided by the OSGi
	 *         framework
	 */
	public File getDataArea() {
		return unwrapped().getDataFile(""); //$NON-NLS-1$
	}

	/**
	 * @param aFileName the relative file name within the bundle's data area
	 * @return the file object in the persistent storage area of this bundle
	 */
	public File getDataFile(String aFileName) {
		return unwrapped().getDataFile(aFileName);
	}

	/**
	 * Returns the value of the specified bundle property. If the key is not found
	 * in the Framework properties, the system properties are then searched. The
	 * method returns <code>null</code> if the property is not found.
	 * 
	 * @param aKey the key of the property
	 * @return the property value found or <code>null</code>
	 */
	public String getProperty(String aKey) {
		BundleContext l_ctx = getBundleContext();
		String l_ret = l_ctx.getProperty(aKey);

		return l_ret;
	}

	/**
	 * Find the specified resource from this bundle's class loader.
	 * 
	 * 
	 * @param aName The name of the resource
	 * @return A URL to the named resource, or <code>null</code> if the resource
	 *         could not be found.
	 */
	public URL getResource(String aName) {
		Bundle l_bundle = unwrapped();
		URL l_ret = l_bundle.getResource(aName);

		return l_ret;
	}

	@Override
	public int hashCode() {
		int l_ret = id().hashCode();

		return l_ret;
	}

	/**
	 * The resource id is the bundle symbolic name followed by the bundle version.
	 * 
	 * @return the identifier for this resource
	 */
	public String id() {
		return symbolicName() + "::" + version().toString(); //$NON-NLS-1$
	}

	/**
	 * @return the date of the last modification of the bundle resource
	 */
	public long lastModified() {
		return myBundle.getLastModified();
	}

	/**
	 * Loads the specified class using this bundle's class loader.
	 * 
	 * @param aClassName The name of the class to load.
	 * @return The Class object for the requested class.
	 * @throws ClassNotFoundException If no such class can be found or if the bundle
	 *                                is a fragment bundle or if the caller does not
	 *                                have the appropriate
	 *                                <code>AdminPermission[this,CLASS]</code>, and
	 *                                the Java Runtime Environment supports
	 *                                permissions.
	 */
	public Class<?> loadClass(String aClassName) throws ClassNotFoundException {
		Bundle l_bundle = unwrapped();

		return l_bundle.loadClass(aClassName);
	}

	/**
	 * @return the string representation of the resource location
	 */
	public String location() {
		return myBundle.getLocation();
	}

	/**
	 * Starts this bundle.
	 * 
	 * @param aLevel the start level to use for start
	 */
	public void start(int aLevel) {
		try {
			String l_header = myBundle.getHeaders().get(Constants.FRAGMENT_HOST);

			setStartLevel(aLevel);

			if (l_header == null) {
				myBundle.start(Bundle.START_ACTIVATION_POLICY);
			}
		} catch (BundleException anEx) {
			throw SYS.LOG.exception(anEx);
		}
	}

	/**
	 * @return the current start level of the bundle
	 */
	public int startLevel() {
		BundleStartLevel l_lvl = myBundle.adapt(BundleStartLevel.class);
		int l_ret = l_lvl.getStartLevel();

		return l_ret;
	}

	/**
	 * @return the symbolic name of the bundle
	 */
	public String symbolicName() {
		return myBundle.getSymbolicName();
	}

	@Override
	public String toString() {
		return id();
	}

	/**
	 * Un-installs the associated framework bundle.
	 */
	public void uninstall() {
		try {
			myBundle.uninstall();
		} catch (BundleException anEx) {
			throw SYS.LOG.exception(anEx);
		}
	}

	/**
	 * Unwrappes this instance to the originating bundle.
	 * 
	 * @return the origin of this <code>XBundle</code> instance
	 */
	public Bundle unwrapped() {
		return myBundle;
	}

	/**
	 * Updates the associated framework bundle.
	 */
	public void update() {
		try {
			myBundle.update();
		} catch (BundleException anEx) {
			throw SYS.LOG.exception(anEx);
		}
	}

	/**
	 * @return the bundle version
	 */
	public Version version() {
		return myBundle.getVersion();
	}

	/**
	 * Returns this bundle's {@link BundleContext}. The returned
	 * <code>BundleContext</code> can be used by the caller to act on behalf of this
	 * bundle.
	 * 
	 * <p>
	 * If this bundle is not in the {@link #STARTING}, {@link #ACTIVE}, or
	 * {@link #STOPPING} states or this bundle is a fragment bundle, then this
	 * bundle has no valid <code>BundleContext</code>. This method will return
	 * <code>null</code> if this bundle has no valid <code>BundleContext</code>.
	 * 
	 * @return A <code>BundleContext</code> for this bundle or <code>null</code> if
	 *         this bundle has no valid <code>BundleContext</code>.
	 * @throws SecurityException If the caller does not have the appropriate
	 *                           <code>AdminPermission[this,CONTEXT]</code>, and the
	 *                           Java Runtime Environment supports permissions.
	 */
	protected BundleContext getBundleContext() {
		return unwrapped().getBundleContext();
	}

	/**
	 * @return the host bundle
	 */
	private Bundle createHostBundle(Class<?> aClass) {
		Bundle l_ret = FrameworkUtil.getBundle(aClass);

		if (l_ret == null) {
			String l_name = aClass.getName();

			throw SYS.LOG.exception(MSG.CLASS_NOT_FROM_BUNDLE, l_name);
		}
		return l_ret;
	}

	private void setStartLevel(int aLevel) {
		BundleStartLevel l_bsl = myBundle.adapt(BundleStartLevel.class);
		l_bsl.setStartLevel(aLevel);
	}
}
