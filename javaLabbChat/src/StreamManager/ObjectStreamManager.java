package StreamManager;


import java.io.*;
import javax.swing.*;

/**
 * @author joachimparrow 2010
 * This is to read from an input stream in a separate thread, and call a callback
 * when something arrives. 
 **/
public class ObjectStreamManager {
    private final ObjectInputStream    theStream;
    private final ObjectStreamListener theListener;
    private final int                  theNumber;
    private volatile boolean           stopped = false;

    /**
     * 
     *  This creates and starts a stream manager for a stream. The manager
     * will continually read from the stream and forward objects through the
     * objectReceived() method of the ObjectStreamListener parameter
    
     *
     * @param   number   The number you give to the manager. It will be included in
     * all calls to readObject. That way you can have the same callback serving several managers
     * since for each received object you get the identity of the manager.
     * @param stream The stream on which the manager should listen.
     * @param listener The object that has the callback objectReceived() 
     *
     *
     */
    public ObjectStreamManager(int number, ObjectInputStream stream, ObjectStreamListener listener) {
        theNumber =   number;
        theStream =   stream;
        theListener = listener;
        new InnerListener().start();  // start to listen on a new thread.
    }

    // This private method accepts an object/exception pair and forwards them
    // to the callback, including also the manager number. The forwarding is scheduled
    // on the Swing thread through an anonymous inner class.
    
    private void callback(final Object object, final Exception exception) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        if (!stopped) {
                            theListener.objectReceived(theNumber, object, exception);
							if (exception != null) closeManager();
                        }
                    }
                });
    }

    // This is where the actual reading takes place.
     private class InnerListener extends Thread {

        @Override
        public void run() {
            while (!stopped) {                            // as long as no one stopped me
                try { 
                    callback(theStream.readObject(), null); // read an object and forward it
                } catch (Exception e) {                 // if Exception then forward it
                    callback(null, e);
                }
            }
            try {                   // I have been stopped: close stream
                theStream.close();
            } catch (IOException e) {
            }

        }
    }

    /**
     * Stop the manager and close the stream.
     **/
    public void closeManager() {
        stopped = true;
    }
}      // end of ObjectStreamManager
