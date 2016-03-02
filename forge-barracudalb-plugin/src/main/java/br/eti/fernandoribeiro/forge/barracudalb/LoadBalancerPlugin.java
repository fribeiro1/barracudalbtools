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
package br.eti.fernandoribeiro.forge.barracudalb;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.jboss.forge.shell.Shell;
import org.jboss.forge.shell.ShellMessages;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.Help;
import org.jboss.forge.shell.plugins.Option;
import org.jboss.forge.shell.plugins.Plugin;

@Alias("barracudalb")
@Help("Manages services and/or real servers in the Barracuda Load Balancer")
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class LoadBalancerPlugin implements Plugin {
	@Inject
	private Shell shell;

	@Command(value = "add-real-server", help = "Adds a real server to a service")
	public void addRealServer(
			@Option(name = "apiProtocol", defaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@Option(name = "apiIp", required = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@Option(name = "apiPort", defaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@Option(name = "apiPassword", required = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@Option(name = "vip", help = "The VIP address used by the service") final String vip,
			@Option(name = "ip", help = "The IP address used by the real server") final String ip,
			@Option(name = "port", help = "The port used by the real server") final String port) {

		try {
			ShellMessages
					.info(shell, "Calling the Barracuda Load Balancer API");

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
				shell.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			ShellMessages.error(shell, "Cannot add real server to service");
		}

	}

	@Command(value = "add-service", help = "Adds a service")
	public void addService(
			@Option(name = "apiProtocol", defaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@Option(name = "apiIp", required = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@Option(name = "apiPort", defaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@Option(name = "apiPassword", required = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@Option(name = "name", help = "The name of the service") final String name,
			@Option(name = "vip", help = "The VIP address used by the service") final String vip,
			@Option(name = "protocol", help = "The protocol used by the service") final String protocol,
			@Option(name = "port", help = "The port used by the service") final String port,
			@Option(name = "type", help = "The type of the service") final String type) {

		try {
			ShellMessages
					.info(shell, "Calling the Barracuda Load Balancer API");

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
				shell.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			ShellMessages.error(shell, "Cannot add service");
		}

	}

	@Command(value = "change-real-server-state", help = "Changes the state of a real server")
	public void changeRealServerState(
			@Option(name = "apiProtocol", defaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@Option(name = "apiIp", required = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@Option(name = "apiPort", defaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@Option(name = "apiPassword", required = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@Option(name = "vip", help = "The VIP address used by the service") final String vip,
			@Option(name = "ip", help = "The IP address used by the real server") final String ip,
			@Option(name = "port", help = "The port used by the real server") final String port,
			@Option(name = "action", help = "The action to perform") final String action) {

		try {
			ShellMessages
					.info(shell, "Calling the Barracuda Load Balancer API");

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
				shell.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			ShellMessages.error(shell, "Cannot change state of real server");
		}

	}

	@Command(value = "delete-real-server", help = "Deletes a real server from a service")
	public void deleteRealServer(
			@Option(name = "apiProtocol", defaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@Option(name = "apiIp", required = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@Option(name = "apiPort", defaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@Option(name = "apiPassword", required = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@Option(name = "vip", help = "The VIP address used by the service") final String vip,
			@Option(name = "ip", help = "The IP address used by the real server") final String ip,
			@Option(name = "port", help = "The port used by the real server") final String port) {

		try {
			ShellMessages
					.info(shell, "Calling the Barracuda Load Balancer API");

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
				shell.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			ShellMessages
					.error(shell, "Cannot delete real server from service");
		}

	}

	@Command(value = "delete-service", help = "Deletes a service")
	public void deleteService(
			@Option(name = "apiProtocol", defaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@Option(name = "apiIp", required = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@Option(name = "apiPort", defaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@Option(name = "apiPassword", required = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@Option(name = "vip", help = "The VIP address used by the service") final String vip,
			@Option(name = "protocol", help = "The protocol used by the service") final String protocol,
			@Option(name = "port", help = "The port used by the service") final String port) {

		try {
			ShellMessages
					.info(shell, "Calling the Barracuda Load Balancer API");

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
				shell.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			ShellMessages.error(shell, "Cannot delete service");
		}

	}

	@Command(value = "show-service", help = "Shows information for a service and/or real server")
	public void showService(
			@Option(name = "apiProtocol", defaultValue = "http", help = "The protocol for the Barracuda Load Balancer API") final String apiProtocol,
			@Option(name = "apiIp", required = true, help = "The IP address for the Barracuda Load Balancer API") final String apiIp,
			@Option(name = "apiPort", defaultValue = "8000", help = "The port for the Barracuda Load Balancer API") final String apiPort,
			@Option(name = "apiPassword", required = true, help = "The password for the Barracuda Load Balancer API") final String apiPassword,
			@Option(name = "vip", help = "The VIP address used by the service") final String vip,
			@Option(name = "ip", help = "The IP address used by the real server") final String ip,
			@Option(name = "port", help = "The port used by the real server") final String port,
			@Option(name = "show", required = true, help = "A list of the information to show") final String show) {

		try {
			ShellMessages
					.info(shell, "Calling the Barracuda Load Balancer API");

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
				shell.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			ShellMessages.error(shell,
					"Cannot show information for service and/or real server");
		}

	}

}