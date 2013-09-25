package grails.plugin.fnordmetric

import java.util.concurrent.TimeUnit
import grails.plugin.fnordmetric.FnordMetricTimer
import grails.plugin.fnordmetric.FnordMetricCounter

class YourBasicService {

    static transactional = false

    private int x = 42

    public int methodWithCounterAndAnnotationParamsProxy(int y, int z) {
        return methodWithCounterAndAnnotationParams(y, z)
    }
    
    @FnordMetricCounter(key='#{x}.#{y}.#{z}', magnitude=3)
    private int methodWithCounterAndAnnotationParams(int y, int z) {
        return y + z
    }

    @FnordMetricCounter(key='test#{x}', magnitude=42)
    public void methodWithCounterAndVoidReturn(int x) {
        // Void functions can be handled differently so we have a test
        1 + 1
    }


    public int methodWithTimerAndDefaultsProxy() {
        return methodWithTimerAndDefaults()
    }

    @FnordMetricCounter(key='counterAndDefaults')
    public int methodWithCounterAndDefaults() {
        return 6 + 7
    }

    @FnordMetricCounter(key='counterAndMagnitude', magnitude=5)
    public int methodWithCounterAndMagnitude(int x) {
        return x + 4
    }


    @FnordMetricTimer(key='timerAndDefaults')
    private int methodWithTimerAndDefaults() {
        return 6 + 7
    }

    @FnordMetricTimer(key='timerAndZero')
    public int methodWithTimerAndZeroSampling(int z) {
        return z
    }

    @FnordMetricTimer(key='#{z}.#{x}.#{y}')
    public int methodWithTimerAndAnnotationParams(int y, int z) {
        return y + z
    }

    @FnordMetricTimer(key='test#{x}')
    public void methodWithTimerAndVoidReturn(int x) {
        // Void functions can be handled differently so we have a test
        1 + 1
    }
}
