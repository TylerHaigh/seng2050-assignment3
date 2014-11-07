package rgms.infrastructure;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.*;

/**
 * Servlet to be called upon application start to set handling of logging
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
@WebListener
public class InitialiseListener implements ServletContextListener {
  private static final Logger logger = Logger.getLogger(InitialiseListener.class.getName());

  /**
   * Starts the Listener and sets up handlers to start logging the application
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    //create the console handler
    Handler consoleHandler = new ConsoleHandler();

    //create filehandler
    Handler fileHandler = null;
    try {
      fileHandler = new FileHandler("%h/aggregates_rgms.log", true);
      fileHandler.setLevel(Level.INFO);
    }
    catch (java.io.IOException e) {
      logger.log(Level.WARNING, "File handler failed", e);
    }

    //add handlers
    Logger.getLogger("").addHandler(consoleHandler);
    if (fileHandler != null)
      Logger.getLogger("").addHandler(fileHandler);

    logger.info("Application_Start");
  }

  /**
   * Logs that the application has stopped
   */
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    logger.info("Application_Shutdown");
  }
}