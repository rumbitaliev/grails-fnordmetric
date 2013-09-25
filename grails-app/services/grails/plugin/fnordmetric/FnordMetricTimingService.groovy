package grails.plugin.fnordmetric

class FnordMetricTimingService {

    static transactional = false

    public long currentTimeMillis() {
        System.currentTimeMillis()
    }

}
