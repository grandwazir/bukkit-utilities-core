package name.richardson.james.bukkit.utilities.localisation;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
public class MessagesFactory {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bukkit-utilities-core");
	private static final Messages PLAIN_MESSAGES = (Messages) Proxy.newProxyInstance(Messages.class.getClassLoader(), new Class[]{Messages.class}, new BasicMessageHandler(RESOURCE_BUNDLE));
	private static final Messages COLOURED_MESSAGES = (Messages) Proxy.newProxyInstance(Messages.class.getClassLoader(), new Class[]{Messages.class}, new ColouredMessageHandler(RESOURCE_BUNDLE));

	public static Messages getMessages() {
		return PLAIN_MESSAGES;
	}

	public static Messages getColouredMessages() {
		return COLOURED_MESSAGES;
	}

}
