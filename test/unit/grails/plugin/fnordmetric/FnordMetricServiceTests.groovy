package grails.plugin.fnordmetric

import grails.test.mixin.*

import org.junit.Test
import org.junit.Before
import org.gmock.GMockTestCase
import org.apache.commons.pool.ObjectPool

@TestFor(FnordMetricService)
class FnordMetricServiceTests extends GMockTestCase {

    FnordMetricService service
    FnordMetricTimingService timingService
    def pool
    def client

    @Before
    void setup() {
        service = new FnordMetricService()
        // Using gmock because mockFor does not support classes with constructors
        pool = mock(ObjectPool)
        client = mock(FnordMetricClient)
        timingService = mock(FnordMetricTimingService)
    }

    private void runTestWithPool(Closure closure) {
        pool.borrowObject().returns(client)
        pool.returnObject(client)
        play {
            service.fnordMetricPool = pool
            closure.call()
        }
    }

    @Test
    public void testTimingClosureWithDefaults() {
        String metric = 'test234'
        timingService.currentTimeMillis().returns(5)
        timingService.currentTimeMillis().returns(12)
        client.send("SAMPLE ${metric} 7\n")
        runTestWithPool {
            service.fnordMetricTimingService = timingService
            def value = service.withTimer(metric) {
                1+1
            }
            assert value == 2
        }
    }

    @Test
    public void testTimingClosure() {
        String metric = 'test234'
        timingService.currentTimeMillis().returns(2)
        timingService.currentTimeMillis().returns(4)
        client.send("SAMPLE ${metric} 2\n")
        runTestWithPool {
            service.fnordMetricTimingService = timingService
            def h = 'hello'
            def w = "world"
            def value = service.withTimer(metric) {
                "${h} ${w}"
            }
            assert value == "hello world"
        }
    }

    @Test
    public void testTiming() {
        String metric = 'sadfsdfsadfasdf'
        int timeInMS = 1234
        client.send("SAMPLE ${metric} ${timeInMS}\n")
        runTestWithPool {
            service.timing(metric, timeInMS)
        }
    }

    @Test
    public void testIncrementWithDefaults() {
        String metric = 'adasdfsad'
        client.send("SAMPLE ${metric} 1\n")
        runTestWithPool {
            service.increment(metric)
        }
    }

    @Test
    public void testIncrementWithDefaultSampling() {
        String metric = 'test23'
        int magnitude = 4
        client.send("SAMPLE ${metric} 4\n")
        runTestWithPool {
            service.increment(metric, magnitude)
        }
    }


}
