package name.richardson.james.bukkit.utilities.configuration;

import java.net.URL;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

public interface DatabaseConfiguration {
	
	public DataSourceConfig getDataSourceConfig();
	
	public ServerConfig getServerConfig();
	
}