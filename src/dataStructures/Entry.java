/**
 * 
 */
package dataStructures;

import java.io.Serializable;

/**
 * Definition of the Entry interface, with K being 
 * the Key for the element and V the value stored
 * @author Tiago Teles
 *
 */
public interface Entry<K, V> extends Serializable {
	// Returns the key in the entry.
	K getKey( );
	 
	// Returns the value in the entry.
	V getValue( );
}
