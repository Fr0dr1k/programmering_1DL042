package StreamManager;

/**
 *
 * @author joachimparrow

 * This is the interface for the listener. It must have a method
 * objectReceived. Whenever reading from the stream results in an object
 * being read or exception being thrown, the object or exception is
 * forwarded to the listener through objectReceived().
 *
 *
 */
public interface ObjectStreamListener {
/**
 * This method is called whenever an object is received or an exception is thrown.
 * @param number    The number of the manager as defined in its constructor
 * @param object  The object received on the stream
 * @param exception     The exception thrown when reading from the stream.
 *          Can be IOException or ClassNotFoundException.
 *          One of name and exception will always be null.
 *          In case of an exception the manager and stream are closed.
 **/
    public void objectReceived(int number, Object object, Exception exception);
}
