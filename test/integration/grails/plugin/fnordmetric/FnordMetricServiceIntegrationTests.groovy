package grails.plugin.fnordmetric

import org.junit.After
import org.junit.Before
import org.junit.Test

import grails.plugin.fnordmetric.test.IntegrationTestUDPServer

class FnordMetricServiceIntegrationTests extends GroovyTestCase {

    def fnordMetricService
    IntegrationTestUDPServer server
    private static final long SLEEP_TIME = 200L

    @Before
    public void setup() {
        server = new IntegrationTestUDPServer()
        server.start()
    }

    @After
    public void tearDown() {
        server.stop()
    }

    @Test
    public void testIncrement() {
        fnordMetricService.increment('key', 5)
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE key 5\n"
    }

    @Test
    public void testTiming() {
        fnordMetricService.timing('key2', 42)
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE key2 42\n"
    }

}
