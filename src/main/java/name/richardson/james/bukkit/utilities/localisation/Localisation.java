package name.richardson.james.bukkit.utilities.localisation;

public interface Localisation {

  /**
   * Gets a localised message. 
   * 
   * This method assumes that the owner is this object calling the method,
   * @param key
   * @return localised message
   */
  public String getMessage(String key);

  /**
   * Gets a localised message with inserted elements. 
   * 
   * Returns a message that uses the additional supplied elements to replace tokens in the base
   * message. Elements will be coerced into their respective String value.
   * 
   * Tokens will be replaced in the order that the elements are provided.
   * 
   * @param key
   * @param elements
   * a variable length array of elements to replace tokens in the message.
   * @return localised message
   */
  public String getMessage(String key, Object... elements);

  /**
   * Gets a localised message with a specific prefix. 
   * 
   * If a class is supplied as the prefix the SimpleName of the class is converted into lowercase and
   * used. Otherwise the object will be coerced into a String.
   * 
   * @param prefix
   * @param key
   * @return localised message
   */
  public String getMessage(Object prefix, String key);

  /**
   * Gets a localised message with a specific prefix and inserted elements. 
   * 
   * If a class is supplied as the prefix the SimpleName of the class is converted into lowercase and
   * used. Otherwise the object will be coerced into a String.
   * 
   * Returns a message that uses the additional supplied elements to replace tokens in the base
   * message. Elements will be coerced into their respective String value.
   * 
   * Tokens will be replaced in the order that the elements are provided.
   * 
   * @param prefix
   * @param key
   * @param elements
   * @return localised message
   */
  public String getMessage(Object prefix, String key, Object... elements);

}
