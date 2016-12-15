package fr.husta.maven.plugin;

import org.apache.maven.artifact.manager.WagonManager;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.wagon.authentication.AuthenticationInfo;

/**
 * Abstract class for Mantis Mojos - Secured with login/password.
 * 
 * @author Guillaume
 */
public abstract class AbstractSecureMantisMojo extends AbstractMantisMojo
{
	@Component
    private WagonManager wagonManager;

    @Parameter(property = "maven.mantis.server", required = true)
    protected String server;

    @Parameter(property = "maven.mantis.projectName", defaultValue = "${project.artifactId}", required = true)
    protected String projectName;
	
    /**
     * Mantis server login.
     */
    @Parameter(property = "mantis.login")
    protected String login;

    /**
     * Mantis server password.
     */
    @Parameter(property = "mantis.password")
    protected String password;
    
    protected String getLogin()
    {
    	if( this.login == null)
    	{
    		AuthenticationInfo info = wagonManager.getAuthenticationInfo( server );
    		this.login = info.getUserName();
    	}
    	return this.login;
    }
    
    protected String getPassword()
    {
    	if( this.password == null)
    	{
    		AuthenticationInfo info = wagonManager.getAuthenticationInfo( server );
    		this.password = info.getPassword();
    	}
    	return this.password;
    }

}
