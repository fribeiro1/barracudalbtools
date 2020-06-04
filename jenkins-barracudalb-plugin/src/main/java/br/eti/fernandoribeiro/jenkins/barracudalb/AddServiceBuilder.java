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
public class AddServiceBuilder extends Builder {

	@Extension
	public static class AddRealServerBuilderDescriptor extends
			BuildStepDescriptor<Builder> {

		@Override
		public String getDisplayName() {
			return "Barracuda Load Balancer: Add Service";
		}

		@Override
		public boolean isApplicable(
				Class<? extends AbstractProject> jobType) {
			return true;
		}

	}

	private String apiIp;

	private String apiPassword;

	private String apiPort;

	private String apiProtocol;

	private String name;

	private String port;

	private String protocol;

	private String type;

	private String vip;

	@DataBoundConstructor
	public AddServiceBuilder(String apiIp, String apiPassword,
			String apiPort, String apiProtocol, String name,
			String port, String protocol, String type,
			String vip) {
		this.apiIp = apiIp;

		this.apiPassword = apiPassword;

		this.apiPort = apiPort;

		this.apiProtocol = apiProtocol;

		this.name = name;

		this.port = port;

		this.protocol = protocol;

		this.type = type;

		this.vip = vip;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build,
			Launcher launcher, BuildListener listener) {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Calling the Barracuda Load Balancer API");

			XmlRpcClient client = new XmlRpcClient();

			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

			config.setServerURL(new URL(apiProtocol + "://" + apiIp + ":"
					+ apiPort + "/cgi-mod/api.cgi?password=" + apiPassword));

			client.setConfig(config);

			Map paramMap = new HashMap();

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

			Map result = (Map) client.execute("service.add",
					new Object[] { paramMap });

			for (Object key : result.keySet())
				logger.println(key + " = " + result.get(key));

		} catch (Exception e) {
			logger.println("Cannot add service");
		}

		return true;
	}

}