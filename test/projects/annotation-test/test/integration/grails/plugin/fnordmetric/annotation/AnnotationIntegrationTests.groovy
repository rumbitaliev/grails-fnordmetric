package grails.plugin.fnordmetric.annotation

import org.junit.Before
import grails.plugin.fnordmetric.test.IntegrationTestUDPServer
import org.junit.After
import org.junit.Test
import grails.plugin.fnordmetric.FnordMetricTimingService

class AnnotationIntegrationTests extends GroovyTestCase {

    private static final long SLEEP_TIME = 200L
    def fnordMetricService
    IntegrationTestUDPServer server
    def yourBasicService
    def fnordMetricTimingService

    @Before
    public void setup() {
        // Don't like to stub out within integration tests, but the time dependency
        // causes non-deterministic tests
        long currentMs = 0
        fnordMetricService.fnordMetricTimingService = [currentTimeMillis: { ->
            currentMs += 2
            return currentMs
        }] as FnordMetricTimingService

        server = new IntegrationTestUDPServer()
        server.start()
    }

    @After
    public void tearDown() {
        fnordMetricService.fnordMetricTimingService = fnordMetricTimingService
        server.stop()
    }

    @Test
    public void testCountAnnotationWithDefaults() {
        assert yourBasicService.methodWithCounterAndDefaults() == 13
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE counterAndDefaults 1\n"
    }

    @Test
    public void testCountAnnotationWithMagnitude() {
        assert yourBasicService.methodWithCounterAndMagnitude(9) == 13
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE counterAndMagnitude 5\n"
    }

    @Test
    public void testCountAnnotationWithParameters() {
        assert yourBasicService.methodWithCounterAndAnnotationParamsProxy(4, 2) == 6
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE 42.4.2 3\n"
    }

    @Test
    public void testCountAnnotationWithVoidReturn() {
        assert yourBasicService.methodWithCounterAndVoidReturn(2) == null
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE test2 42\n"
    }

    @Test
    public void testTimerAnnotationWithDefaults() {
        assert yourBasicService.methodWithTimerAndDefaultsProxy() == 13
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE timerAndDefaults 2\n"
    }

    @Test
    public void testTimerAnnotationWithZeroSamplingRate() {
        assert yourBasicService.methodWithTimerAndZeroSampling(13) == 13
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        // TODO this test is verifying that zero sampling rates are not supported
        assert server.messages[0] == "SAMPLE timerAndZero 2\n"
    }

    @Test
    public void testTimerAnnotationWithParameters() {
        assert yourBasicService.methodWithTimerAndAnnotationParams(4, 2) == 6
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE 2.42.4 2\n"
    }

    @Test
    public void testTimerAnnotationWithVoidReturn() {
        assert yourBasicService.methodWithTimerAndVoidReturn(2) == null
        Thread.sleep(SLEEP_TIME)
        assert server.messages.size() == 1
        assert server.messages[0] == "SAMPLE test2 2\n"
    }
}
