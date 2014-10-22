package rgms.infrastructure;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.*;

@WebListener
public class InitialiseListener implements ServletContextListener {
  private static final Logger logger = Logger.getLogger(InitialiseListener.class.getName());

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

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    logger.info("Application_Shutdown");
  }
}