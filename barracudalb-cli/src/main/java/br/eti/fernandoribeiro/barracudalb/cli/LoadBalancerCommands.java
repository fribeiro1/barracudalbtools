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
package br.eti.fernandoribeiro.barracudalb.cli;

import static java.lang.System.out;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class LoadBalancerCommands implements CommandMarker {
	private static final Logger LOGGER = Logger
			.getLogger(LoadBalancerCommands.class.getName());

	@CliCommand(value = "barracudalb add-real-server", help = "Adds a real server to a service")
	public void addRealServer(
			@CliOption(key = { "apiProtocol" }, unspecifiedDefaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@CliOption(key = { "apiIp" }, mandatory = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@CliOption(key = { "apiPort" }, unspecifiedDefaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@CliOption(key = { "apiPassword" }, mandatory = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@CliOption(key = { "vip" }, help = "The VIP address used by the service") final String vip,
			@CliOption(key = { "ip" }, help = "The IP address used by the real server") final String ip,
			@CliOption(key = { "port" }, help = "The port used by the real server") final String port) {

		try {
			LOGGER.info("Calling the Barracuda Load Balancer API");

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

			final Map result = (Map) client.execute("server.add",
					new Object[] { paramMap });

			for (final Object key : result.keySet())
				out.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot add real server to service", e);
		}

	}

	@CliCommand(value = "barracudalb add-service", help = "Adds a service")
	public void addService(
			@CliOption(key = { "apiProtocol" }, unspecifiedDefaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@CliOption(key = { "apiIp" }, mandatory = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@CliOption(key = { "apiPort" }, unspecifiedDefaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@CliOption(key = { "apiPassword" }, mandatory = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@CliOption(key = { "name" }, help = "The name of the service") final String name,
			@CliOption(key = { "vip" }, help = "The VIP address used by the service") final String vip,
			@CliOption(key = { "protocol" }, help = "The protocol used by the service") final String protocol,
			@CliOption(key = { "port" }, help = "The port used by the service") final String port,
			@CliOption(key = { "type" }, help = "The type of the service") final String type) {

		try {
			LOGGER.info("Calling the Barracuda Load Balancer API");

			final XmlRpcClient client = new XmlRpcClient();

			final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

			config.setServerURL(new URL(apiProtocol + "://" + apiIp + ":"
					+ apiPort + "/cgi-mod/api.cgi?password=" + apiPassword));

			client.setConfig(config);

			final Map paramMap = new HashMap();

			if (name != null)
				paramMap.put("name", name);

			if (vip != null)
				paramMap.put("vip", vip);

			if (protocol != null)
				paramMap.put("protocol", protocol);

			if (port != null)
				paramMap.put("port", port);

			if (type != null)
				paramMap.put("type", type);

			final Map result = (Map) client.execute("service.add",
					new Object[] { paramMap });

			for (final Object key : result.keySet())
				out.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot add service", e);
		}

	}

	@CliCommand(value = "barracudalb change-real-server-state", help = "Changes the state of a real server")
	public void changeRealServerState(
			@CliOption(key = { "apiProtocol" }, unspecifiedDefaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@CliOption(key = { "apiIp" }, mandatory = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@CliOption(key = { "apiPort" }, unspecifiedDefaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@CliOption(key = { "apiPassword" }, mandatory = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@CliOption(key = { "vip" }, help = "The VIP address used by the service") final String vip,
			@CliOption(key = { "ip" }, help = "The IP address used by the real server") final String ip,
			@CliOption(key = { "port" }, help = "The port used by the real server") final String port,
			@CliOption(key = { "action" }, help = "The action to perform") final String action) {

		try {
			LOGGER.info("Calling the Barracuda Load Balancer API");

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

			if (action != null)
				paramMap.put("action", action);

			final Map result = (Map) client.execute("server.change_state",
					new Object[] { paramMap });

			for (final Object key : result.keySet())
				out.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot change state of real server", e);
		}

	}

	@CliCommand(value = "barracudalb delete-real-server", help = "Deletes a real server from a service")
	public void deleteRealServer(
			@CliOption(key = { "apiProtocol" }, unspecifiedDefaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@CliOption(key = { "apiIp" }, mandatory = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@CliOption(key = { "apiPort" }, unspecifiedDefaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@CliOption(key = { "apiPassword" }, mandatory = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@CliOption(key = { "vip" }, help = "The VIP address used by the service") final String vip,
			@CliOption(key = { "ip" }, help = "The IP address used by the real server") final String ip,
			@CliOption(key = { "port" }, help = "The port used by the real server") final String port) {

		try {
			LOGGER.info("Calling the Barracuda Load Balancer API");

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

			final Map result = (Map) client.execute("server.delete",
					new Object[] { paramMap });

			for (final Object key : result.keySet())
				out.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot delete real server from service", e);
		}

	}

	@CliCommand(value = "barracudalb delete-service", help = "Deletes a service")
	public void deleteService(
			@CliOption(key = { "apiProtocol" }, unspecifiedDefaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@CliOption(key = { "apiIp" }, mandatory = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@CliOption(key = { "apiPort" }, unspecifiedDefaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@CliOption(key = { "apiPassword" }, mandatory = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@CliOption(key = { "vip" }, help = "The VIP address used by the service") final String vip,
			@CliOption(key = { "protocol" }, help = "The protocol used by the service") final String protocol,
			@CliOption(key = { "port" }, help = "The port used by the service") final String port) {

		try {
			LOGGER.info("Calling the Barracuda Load Balancer API");

			final XmlRpcClient client = new XmlRpcClient();

			final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

			config.setServerURL(new URL(apiProtocol + "://" + apiIp + ":"
					+ apiPort + "/cgi-mod/api.cgi?password=" + apiPassword));

			client.setConfig(config);

			final Map paramMap = new HashMap();

			if (vip != null)
				paramMap.put("vip", vip);

			if (protocol != null)
				paramMap.put("protocol", protocol);

			if (port != null)
				paramMap.put("port", port);

			final Map result = (Map) client.execute("service.delete",
					new Object[] { paramMap });

			for (final Object key : result.keySet())
				out.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot delete service", e);
		}

	}

	@CliCommand(value = "barracudalb show-service", help = "Shows information for a service and/or real server")
	public void showService(
			@CliOption(key = { "apiProtocol" }, unspecifiedDefaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@CliOption(key = { "apiIp" }, mandatory = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@CliOption(key = { "apiPort" }, unspecifiedDefaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@CliOption(key = { "apiPassword" }, mandatory = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@CliOption(key = { "vip" }, help = "The VIP address used by the service") final String vip,
			@CliOption(key = { "ip" }, help = "The IP address used by the real server") final String ip,
			@CliOption(key = { "port" }, help = "The port used by the real server") final String port,
			@CliOption(key = { "show" }, mandatory = true, help = "A list of the information to show") final String show) {

		try {
			LOGGER.info("Calling the Barracuda Load Balancer API");

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
			LOGGER.log(Level.SEVERE,
					"Cannot show information for service and/or real server", e);
		}

	}

}