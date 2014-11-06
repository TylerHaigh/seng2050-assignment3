package rgms.datacontext;

public class DocumentManager extends DataManager {
  private int threadId;

  /**
   * Create a Document Manager instance for the 
   * given thread
   * @param  threadId The thread
   */
  public DocumentManager(int threadId) { 
    this.threadId = threadId;
  }
}