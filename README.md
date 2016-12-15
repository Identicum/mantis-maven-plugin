mantis-maven-plugin
===================

General information
-------------------

A Maven plugin for Mantis integration.  
Based on the [Mantis SOAP API](http://www.mantisbt.org/wiki/doku.php/mantisbt:faq#does_mantisbt_provide_a_webservice_interface) (Web Service).  
Live test of the web service on the MantisBT site : [Test it](http://www.mantisbt.org/bugs/api/soap/mantisconnect.php) ([WSDL](http://www.mantisbt.org/bugs/api/soap/mantisconnect.php?wsdl)).

Configuration
-------------

This plugin requires the following information:
* Mantis URL: absolute URL to Mantis home (i.e.: http://mantis.org/bt)
* Mantis username: username to authenticate in Mantis 
* Mantis password: password to authenticate in Mantis
* Mantis project name: where the version should be added
* Deprecate Olders: flag to mark older versions as obsolete on goal **add-project-version**

Configuration properties were moved to the configuration section inside the plugin in the `pom.xml` and username and password can now be defined in `~/.m2/settings.xml` file:

```xml
<plugin>
	<groupId>com.identicum.utils</groupId>
	<artifactId>mantis-maven-plugin</artifactId>
	<version>0.3-SNAPSHOT</version>
	<configuration>
		<server>bt</server><!-- server in settings.xml -->
		<url>http://10.1.1.153/btora/</url>
		<projectName>Identicum - IRM</projectName>
		<deprecateOlders>true</deprecateOlders>
	</configuration>
</plugin>
``` 

Goals
-----
1. **mantis:add-project-version** - Create a project's version.  
   See method :  [mc_project_version_add](http://www.mantisbt.org/bugs/api/soap/mantisconnect.php?wsdl#op.idp11724320)
2. **mantis:display-project-versions** - Displays the versions of a project.  
   See method : [mc_project_get_versions](http://www.mantisbt.org/bugs/api/soap/mantisconnect.php?wsdl#op.idp11723424)
3. **mantis:display-version** - Displays the version of Mantis server.  
   See method : [mc_version](http://www.mantisbt.org/bugs/api/soap/mantisconnect.php?wsdl#op.idp11681248)


Help
----

1. Maven Plugin Development :
    * Maven official site : [Maven - Guide to Developing Java Plugins](http://maven.apache.org/guides/plugin/guide-java-plugin-development.html)
    * Maven Plugin API : [The Descriptor and Annotations](http://maven.apache.org/developers/mojo-api-specification.html#The_Descriptor_and_Annotations)
    * Maven Plugin Plugin : [Configuring Generation of Help Mojo](http://maven.apache.org/plugin-tools/maven-plugin-plugin/examples/generate-help.html)
    * Maven Plugin Tools : [Introduction](http://maven.apache.org/plugin-tools/) _(The Maven Plugin Tools contains the necessary tools to play with Maven Plugins.)_
    * Maven Plugin Prefix : [Introduction to Plugin Prefix Resolution](http://maven.apache.org/guides/introduction/introduction-to-plugin-prefix-mapping.html)
    * Book _"Better Builds with Maven"_ : [Developing Custom Maven Plugins](http://www.maestrodev.com/better-builds-with-maven/developing-custom-maven-plugins/)
    * Book _"Maven: The Complete Reference"_ : [Writing Plugins](http://www.sonatype.com/books/mvnref-book/reference/writing-plugins.html)
    * Book _"Better Builds with Maven"_ : [Mojo Parameter Expressions](http://www.maestrodev.com/better-builds-with-maven/resources-for-plugin-developers/mojo-parameter-expressions/)

2. Maven & GitHub :
    * Maven + GitHub : [Maven Tips and Tricks: Using GitHub](http://www.sonatype.com/people/2009/09/maven-tips-and-tricks-using-github/)

3. Eclipse & GitHub :
    * GitHub links for Eclipse : [Git in Eclipse](http://eclipse.github.com/)
    * Eclipse EGit + GitHub : [EGit/GitHub/User Guide](http://wiki.eclipse.org/EGit/GitHub/UserGuide)
    * Eclipse GitHub plugin : [Update Site](http://download.eclipse.org/egit/github/updates-nightly)
    * Git with Eclipse : [Git with Eclipse (EGit) - Tutorial](http://www.vogella.com/articles/EGit/article.html)
    * Slides about EGit & GitHub [Using The EGit Eclipse Plugin With Git Hub](http://www.slideshare.net/loianeg/using-the-egit-eclipse-plugin-with-git-hub-2578587)
