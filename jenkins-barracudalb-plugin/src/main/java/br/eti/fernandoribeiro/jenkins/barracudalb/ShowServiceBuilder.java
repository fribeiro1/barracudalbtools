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
package br.eti.fernandoribeiro.jenkins.barracudalb;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.kohsuke.stapler.DataBoundConstructor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ShowServiceBuilder extends Builder {

	@Extension
	public static final class ShowServiceBuilderDescriptor extends
			BuildStepDescriptor<Builder> {

		@Override
		public String getDisplayName() {
			return "Barracuda Load Balancer: Show Service";
		}

		@Override
		public boolean isApplicable(
				final Class<? extends AbstractProject> jobType) {
			return true;
		}

	}

	private String apiIp;

	private String apiPassword;

	private String apiPort;

	private String apiProtocol;

	private String ip;

	private String port;

	private String show;

	private String vip;

	@DataBoundConstructor
	public ShowServiceBuilder(final String apiIp, final String apiPassword,
			final String apiPort, final String apiProtocol, final String ip,
			final String port, final String show, final String vip) {
		this.apiIp = apiIp;

		this.apiPassword = apiPassword;

		this.apiPort = apiPort;

		this.apiProtocol = apiProtocol;

		this.ip = ip;

		this.port = port;

		this.show = show;

		this.vip = vip;
	}

	@Override
	public boolean perform(final AbstractBuild<?, ?> build,
			final Launcher launcher, final BuildListener listener) {
		final PrintStream logger = listener.getLogger();

		try {
			logger.println("Calling the Barracuda Load Balancer API");

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
				logger.println(key + " = " + result.get(key));

		} catch (final Exception e) {
			logger.println("Cannot show information for service and/or real server");
		}

		return true;
	}

}