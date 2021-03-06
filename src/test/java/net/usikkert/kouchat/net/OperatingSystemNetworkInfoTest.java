
/***************************************************************************
 *   Copyright 2006-2012 by Christian Ihle                                 *
 *   kontakt@usikkert.net                                                  *
 *                                                                         *
 *   This file is part of KouChat.                                         *
 *                                                                         *
 *   KouChat is free software; you can redistribute it and/or modify       *
 *   it under the terms of the GNU Lesser General Public License as        *
 *   published by the Free Software Foundation, either version 3 of        *
 *   the License, or (at your option) any later version.                   *
 *                                                                         *
 *   KouChat is distributed in the hope that it will be useful,            *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU      *
 *   Lesser General Public License for more details.                       *
 *                                                                         *
 *   You should have received a copy of the GNU Lesser General Public      *
 *   License along with KouChat.                                           *
 *   If not, see <http://www.gnu.org/licenses/>.                           *
 ***************************************************************************/

package net.usikkert.kouchat.net;

import static org.junit.Assert.*;

import java.net.NetworkInterface;
import java.util.Enumeration;

import org.junit.Test;

/**
 * Test of {@link OperatingSystemNetworkInfo}.
 *
 * @author Christian Ihle
 */
public class OperatingSystemNetworkInfoTest {

    /**
     * Tests if the network interface for the operating system can be found.
     *
     * <p>But only if there are usable network interfaces available.</p>
     */
    @Test
    public void testFindingTheOSNetworkInterface() {
        final Enumeration<NetworkInterface> networkInterfaces = NetworkUtils.getNetworkInterfaces();
        final OperatingSystemNetworkInfo osNicInfo = new OperatingSystemNetworkInfo();
        final NetworkInterface osInterface = osNicInfo.getOperatingSystemNetworkInterface();

        if (networkInterfaces == null) {
            System.err.println("No network interfaces found.");
            assertNull(osInterface);
            return;
        }

        boolean validNetworkAvailable = false;

        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();

            if (NetworkUtils.isUsable(networkInterface)) {
                validNetworkAvailable = true;
                break;
            }
        }

        if (!validNetworkAvailable) {
            System.err.println("No usable network interfaces found.");
            assertNull(osInterface);
            return;
        }

        assertNotNull(osInterface);

        // This is known to sometimes fail in Vista. It is unknown why Vista
        // prefers unusable network interfaces.
        assertTrue(NetworkUtils.isUsable(osInterface));
    }
}
