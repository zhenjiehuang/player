/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009, 2010, 2011, 2012, 2013, 2014, 2015 Caprica Software Limited.
 */

package uk.co.caprica.vlcj.runtime.windows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Windows specific run-time utilities.
 */
public final class WindowsRuntimeUtil {

	/**
	 * Log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(WindowsRuntimeUtil.class);

	/**
	 * The VLC registry key, under HKLM.
	 */
	public static final String VLC_REGISTRY_KEY = "SOFTWARE\\VideoLAN\\VLC";

	/**
	 * The VLC registry key for the installation directory.
	 */
	public static final String VLC_INSTALL_DIR_KEY = "InstallDir";

	/**
	 * Prevent direct instantiation by others.
	 */
	private WindowsRuntimeUtil() {
	}

	/**
	 * Get the VLC installation directory.
	 * <p>
	 * If vlc is installed correctly, this should not be needed.
	 *
	 * @return fully-qualified directory name, or <code>null</code> if the value
	 *         could not be obtained
	 */
	public static String getVlcInstallDir() {
		logger.debug("getVlcInstallDir()");
		try {
			return "Z:\\workspace\\test\\player\\bin";
		} catch (Exception e) {
			logger.warn("Failed to get VLC installation directory from the registry", e);
			return null;
		}
	}
}
