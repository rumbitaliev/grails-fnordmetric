package grails.plugin.fnordmetric

class FnordMetricService {

    static transactional = false

    def fnordMetricPool
    def fnordMetricTimingService

    private void withClient(Closure closure) {
        FnordMetricClient client = (FnordMetricClient) fnordMetricPool.borrowObject()
        try {
            closure.call(client)
        } finally {
            fnordMetricPool.returnObject(client)
        }
    }

    public def withTimer(String key, Closure closure) {
        log.debug("Start timer : ${key}")
        long startTime = fnordMetricTimingService.currentTimeMillis()
        def result = closure()
        long finishTime = fnordMetricTimingService.currentTimeMillis()
        long runTime = finishTime - startTime
        timing(key, runTime.toInteger())
        log.debug("End timer : ${key} : ${runTime}ms")
        return result
    }

    public void timing(String key, int value) {
        withClient { client ->
            client.send(String.format("SAMPLE %s %s\n", key, value));
        }
    }

    public void increment(String key) {
        increment(key, 1);
    }

    public void increment(String key, int magnitude) {
        String stat = String.format("SAMPLE %s %s\n", key, magnitude);
        withClient { client ->
            client.send(stat);
        }
    }

}
