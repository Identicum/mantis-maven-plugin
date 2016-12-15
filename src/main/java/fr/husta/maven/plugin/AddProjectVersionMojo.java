package fr.husta.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.math.BigInteger;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.maven.model.IssueManagement;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectVersionData;
import fr.husta.maven.plugin.util.MantisConnector;
import fr.husta.maven.plugin.util.MantisUtils;
import fr.husta.maven.plugin.util.ReleaseUtils;

/**
 * Adds a version to a project.
 */
@Mojo(name = "add-project-version", requiresProject = true)
public class AddProjectVersionMojo extends AbstractSecureMantisMojo
{

    @Component
    protected MavenProject project;

    @Parameter(property = "mantis.versionName", defaultValue = "${project.version}", required = true)
    protected String versionName;
    
    @Parameter(property = "maven.mantis.deprecateOlders", defaultValue = "false")
    protected String deprecateOlders;
    

    protected IssueManagement issueManagement;

    public void execute() throws MojoExecutionException
    {
        issueManagement = project.getIssueManagement();
        if (issueManagement != null)
        {
            getLog().debug("IssueManagement -> system = " + issueManagement.getSystem());
            getLog().debug("IssueManagement -> url = " + issueManagement.getUrl());

            final String ISSUE_MNGT_MANTIS = "Mantis";
            if (issueManagement.getSystem() != null
                    && ISSUE_MNGT_MANTIS.equals(issueManagement.getSystem()) == false)
            {
                getLog().warn("IssueManagement -> system should be set to '" + ISSUE_MNGT_MANTIS + "'.");
            }
        }

        try
        {
            // connection to Mantis SOAP API
            MantisConnectPortType portType = MantisUtils.createConnector(getMantisSoapApiUrl());
            MantisConnector mantisConnector = new MantisConnector(portType);

            getLog().debug("projectName = '" + projectName + "'");

            // find ProjectId from Name
            BigInteger projectId = mantisConnector.getProjectIdByName(this.getLogin(), this.getPassword(), projectName);
            
            getLog().debug("deprecateOlders = " + deprecateOlders);
            
            if("true".equalsIgnoreCase(this.deprecateOlders))
            {
            	getLog().info("Updating previous version as obsolete");
            	ProjectVersionData[] projectVersions = mantisConnector.getProjectVersions(this.getLogin(), this.getPassword(), projectId);
            	for(ProjectVersionData projectVersion : projectVersions)
            	{
            		getLog().debug("Version " + projectVersion.getName() + " has obsolete flag set in " + projectVersion.getObsolete());
            		if(projectVersion.getObsolete().equals(Boolean.FALSE))
            		{
            			getLog().debug("Updating version " + projectVersion.getName());
            			projectVersion.setObsolete(Boolean.TRUE);
            			mantisConnector.updateProjectVersion( this.getLogin(), this.getPassword(), projectVersion);
            		}
            	}
            }
            
            // call to web service method
            String releaseVersion = ReleaseUtils.getReleaseVersion(versionName);
            getLog().info("Version '" + releaseVersion + "' to be created.");
            mantisConnector.addProjectVersion(this.getLogin(), this.getPassword(), projectId, releaseVersion);
            getLog().info("Version '" + releaseVersion + "' created in Mantis.");

        }
        catch (ServiceException e)
        {
            // getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (RemoteException e)
        {
            // getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage(), e);
        }

    }
}
