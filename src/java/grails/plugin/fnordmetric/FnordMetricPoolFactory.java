package grails.plugin.fnordmetric;

import org.apache.commons.pool.PoolableObjectFactory;

public class FnordMetricPoolFactory implements PoolableObjectFactory {

    final private String host;
    final private int port;

    public FnordMetricPoolFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public FnordMetricClient makeObject() throws Exception {
        return new FnordMetricClient(host, port);
    }

    public void destroyObject(Object o) throws Exception {
        ((FnordMetricClient) o).close();
    }

    public boolean validateObject(Object o) {
        return ((FnordMetricClient) o).isOpen();
    }

    public void activateObject(Object o) throws Exception {
        // Objects don't require setup when sent out
    }

    public void passivateObject(Object o) throws Exception {
        // Objects don't require changes to be returned to pool
    }
}
