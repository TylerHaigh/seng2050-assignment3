package rgms.controller;

import java.net.*;
import java.util.logging.*;

public class Controller {
  private static final Logger logger = Logger.getLogger(Controller.class.getName());

  public static String getAction(URI requestUri) {
    String[] paths = requestUri.getPath().toLowerCase().split("/");
    String actionName = paths[paths.length - 1];
    System.out.println(actionName);

    return actionName;
  }

  public static String getAction(String requestString) {
    try {
      return getAction(new URI(requestString));
    }
    catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "URI Error", e);
      return null;
    }
  }
}