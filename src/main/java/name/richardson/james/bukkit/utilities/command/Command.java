package name.richardson.james.bukkit.utilities.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.Permissible;

public interface Command {

  public void execute(List<String> arguments, CommandSender sender);
	
	public String getName();
	
	public String getDescription();
	
	public String getUsage();
	
	public boolean isAuthorized(Permissible permissible);
	
	public List<String> onTabComplete(List<String> arguments, CommandSender sender);
	
}
