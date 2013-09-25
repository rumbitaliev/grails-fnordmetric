package grails.plugin.fnordmetric

import grails.plugin.fnordmetric.FnordMetricTimer
import grails.plugin.fnordmetric.FnordMetricCounter

class TestController {

    def yourBasicService
    def fnordMetricService

    def x = 'BLAH'

    def index() {
        fnordMetricService.increment('homepage.visit')
        render 'hello'
    }

    def test2() {
        fnordMetricService.withTimer('homepage.test2') {
            yourBasicService.methodWithTimerAndDefaultsProxy()
        }
        render 'annotation test'
    }

    @FnordMetricTimer(key='#{x}.method.timer')
    private void testMethod() {
        println 'method called2'
    }

    @FnordMetricCounter(key='#{x}.method.counter1')
    def test3() {
        render 'called annotation maybe?'
    }

    @FnordMetricCounter(key='test.method.counter2', magnitude=4)
    def test4() {
        testMethod()
        render 'called annotation maybe?'
    }

    @FnordMetricCounter(key='#{x}.test34')
    def testMethod2(int x){
        return x + 1
    }


    def test6() {
        testMethod2(42)
        render 'test'
    }

}
