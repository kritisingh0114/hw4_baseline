package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/** Represents the model for an Expense Tracker.
 * @author Heather Conboy
 * @version 1.0
*/
public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> listeners;
  

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
  /** Creates an expense tracker model.
  */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    listeners = new ArrayList<ExpenseTrackerModelListener>();
  }

  /**
   * Adds a transaction, t, to the transactions list
   *
   * @param t The new transaction to be added
   */  
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  /**
   * Removes the transaction, t, from the transactions list
   *
   * @param t The new transaction to be removed
   */  
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  /**
   * Gets the list of transactions
   *
   * @return A list of transactions
   */  
  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  /**
   * All the non-null indices. once validated, are added to the input list after it is cleared
   *
   * @param newMatchedFilterIndices List of input indices
   */  
  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
      stateChanged();
  }

  /**
   * Returns the a copy of list of filter indeces
   *
   * @return A list of integers
   */  
  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return A boolean, if the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      // TODO
      if (listeners == null){
        listeners.add(listener);
      }
      if(listener != null && !listeners.contains(listener)){
        listeners.add(listener);
        return true;
      }
      return false;
  }

  /**
   * Returns the number of listerners that have been registered 
   *
   * @return An integer representing the number of listeners
   */  
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      //TODO
      if (listeners == null){
        return 0;
      }
      return listeners.size();
  }

  /**
   * Checks whether a listener has been registered, returns True if it has, false if not
   *
   * @return A boolean, true if the list of listeners contains the new listener, false otherwise
   */  
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      //TODO
      if (listeners == null){
        return false;
      }
      return listeners.contains(listener);
  }

  /**
   * Updates the view when the state of the model has changed
   *
   */  
  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      //TODO
      if (listeners == null){
        return;
      }
      for(ExpenseTrackerModelListener curListener: listeners){
        curListener.update(this);
      }
  }
}
