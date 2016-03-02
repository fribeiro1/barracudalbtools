/*
 * Copyright 2012 Fernando Ribeiro
 * 
 * This file is part of Barracuda Load Balancer Tools.
 *
 * Barracuda Load Balancer Tools is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Barracuda Load Balancer Tools is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Barracuda Load Balancer Tools. If not, see <http://www.gnu.org/licenses/>.
 */
package br.eti.fernandoribeiro.maven.barracudalb;

import static java.lang.System.out;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * Shows information for a service and/or real server
 */
@Mojo(name = "show-service")
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ShowServiceMojo extends AbstractMojo {
	/**
	 * The IP address for the Barracuda Load Balancer API
	 */
	@Parameter(required = true)
	private String apiIp;

	/**
	 * The password for the Barracuda Load Balancer API
	 */
	@Parameter(required = true)
	private String apiPassword;

	/**
	 * The port for the Barracuda Load Balancer API
	 */
	@Parameter(defaultValue = "8000")
	private String apiPort;

	/**
	 * The protocol for the Barracuda Load Balancer API
	 */
	@Parameter(defaultValue = "http")
	private String apiProtocol;

	/**
	 * The IP address used by the real server
	 */
	@Parameter
	private String ip;

	/**
	 * The port used by the real server
	 */
	@Parameter
	private String port;

	/**
	 * A list of the information to show
	 */
	@Parameter(required = true)
	private String show;

	/**
	 * The VIP address used by the service
	 */
	@Parameter
	private String vip;

	public void execute() {

		try {
			getLog().info("Calling the Barracuda Load Balancer API");

			final XmlRpcClient client = new XmlRpcClient();

			final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

			config.setServerURL(new URL(apiProtocol + "://" + apiIp + ":"
					+ apiPort + "/cgi-mod/api.cgi?password=" + apiPassword));

			client.setConfig(config);

			final Map paramMap = new HashMap();

			if (vip != null)
				paramMap.put("vip", vip);

			if (ip != null)
				paramMap.put("ip", ip);

			if (port != null)
				paramMap.put("port", port);

			paramMap.put("show", show);

			final Map result = (Map) client.execute("service.show",
					new Object[] { paramMap });

			for (final Object key : result.keySet())
				out.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			getLog().error(
					"Cannot show information for service and/or real server", e);
		}

	}

}