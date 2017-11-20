
/**
 * 
 * A Handle is used to refer to WHERE in the memory pool the data it contains
 * exists.
 * 
 * @author l3oGi_000
 * @version Aug 28, 2016
 */
public class Handle {

    /*
     * Fields
     */

    /**
     * Where memPool should look initially to construct the int
     * length value to return a byte string
     */
    public int offset;
    
    /**
     * True when removing handle from hash table
     * False when valid handle/string exists in table
     */
    public boolean isTombstone = false;

    /**
     * Constructor
     * 
     * @param num
     *      sets the offset to num's value
     * 
     */
    public Handle(int num) {
        offset = num;
    }

    /**
     * Sets the offset to input num's value
     * @param num
     *      sets the offset
     */
    public void setOffset(int num) {
        offset = num;
    }

    /**
     * 
     * @return
     *      the handles current offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the tombStone for handle to true
     */
    public void setTombstone() {
        isTombstone = true;
    }

}
