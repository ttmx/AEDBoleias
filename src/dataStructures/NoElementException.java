/**
 * 
 */
package dataStructures;

/**
 * @author Tiago Teles
 *
 */
public class NoElementException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NoElementException( )
	{
	super();
	}
	public NoElementException( String msg )
	{
	super(msg);
	}
}
