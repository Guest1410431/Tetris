package Game;
//The 7 unique pieces in tetris, along with a no-shape, and junk piece
public enum Shape
{
	//Null Shape
	NoShape,
	//Junk block, not null, not a piece
	Junk,
   /*  _
	* |_|
	* |_|
	* |_|
	* |_|
	*/
	I,
	/*    _
	 *   |_|
	 *  _|_|
	 * |_|_|  
	 */
	J,
	/*  _
	 * |_|
	 * |_|_
	 * |_|_| 
	 */
	L,
	/* ___
	 *|_|_|
	 *|_|_| 
	 */
	O,
	/*    ___
	 *  _|_|_|
	 * |_|_|
	 */
	S,
	/* _____
	 *|_|_|_|
	 *  |_| 
	 */
	T,
	/* ___
	 *|_|_|_
	 *  |_|_|
	 */
	Z
}
