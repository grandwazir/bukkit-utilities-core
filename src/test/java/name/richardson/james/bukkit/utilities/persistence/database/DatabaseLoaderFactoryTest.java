package name.richardson.james.bukkit.utilities.persistence.database;

import java.util.Arrays;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import name.richardson.james.bukkit.utilities.persistence.configuration.DatabaseConfiguration;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class DatabaseLoaderFactoryTest extends TestCase {

	private DatabaseConfiguration configuration;

	@Test
	public void getDatabaseLoader_WhenSQLite_ReturnSQLiteLoader() {
		when(configuration.getDataSourceConfig().getDriver()).thenReturn("sqlite");
		assertSame(DatabaseLoaderFactory.getDatabaseLoader(configuration).getClass(), SQLiteDatabaseLoader.class);
	}

	@Test
	public void getDatabaseLoader_WhenNotSQLite_ReturnDefaultLoader() {
		when(configuration.getDataSourceConfig().getDriver()).thenReturn("anythingelse");
		assertSame(DatabaseLoaderFactory.getDatabaseLoader(configuration).getClass(), DefaultDatabaseLoader.class);
	}

	@Before
	public void setUp() {
		this.configuration = mock(DatabaseConfiguration.class, RETURNS_DEEP_STUBS);
		when(configuration.getServerConfig().getClasses()).thenReturn(Arrays.<Class<?>>asList(DatabaseLoaderFactoryTest.class));
	}

}

