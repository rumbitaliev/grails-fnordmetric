Grails FnordMetric Plugin
=============

This project allows for easy publication of metrics to FnordMetric enterprise from a grails application.

This project is based on [grails-statsd][grails-statsd-repo]

What is FnordMetric
--------------
[FnordMetric][FnordMetric_repo] is a framework for collecting and visualizing timeseries data. It enables you to build beautiful real-time analytics dashboards within minutes. For more information about FnordMetric see this [FnordMetric site][FnordMetric_site].

Installation
------------

For now you will need to make a local plugin, but eventually it should be as easy as:

    grails install-plugin fnordmetric


Out of the box, the plugin expects that FnordMetric is running on `localhost:2121`.  You can modify this (as well as any other pool config options) by adding a stanza like this to your `grails-app/conf/Config.groovy` file:

    grails {
        fnordmetric {
            poolConfig {
                // apache pool specific tweaks here.
                // See [the javadoc][genericobjectpool] for details.
            }
            port = 2121
            host = "localhost"
        }
    }

The poolConfig section will let you tweak any of the setter values made available by Apache Commons [GenericObjectPool][genericobjectpool].

Plugin Usage
------------

### FnordMetricService Bean ###

    def fnordMetricService

The `fnordMetricService` bean is the only service exposed by this plugin that you should need, and it contains the methods that are supported by the FnordMetric server. Currently this includes counters and timers.

Counters are used to count the number of times something happens and report on it. This is done through the increment method within the service. This method take the metric to count as the first parameter, an optional magnitude as a second parameter.

    def fnordMetricService

    fnordMetricService.increment('my_counter1.sum-3600')
    fnordMetricService.increment('my_counter2.sum-3600', 2)

Timers are used to record how long something took. Generally you will probably want to use the closure method to call timers, but there is also functions to specify times directly if desired. Timers also take in a metric name.

    def allBooks = service.withTimer('dbquery_books-mean-60') {
        Books.list()
    }

There is not very much code in the service so if you have questions please dig in.

### FnordMetric Annotations ###

In addition to the service you can also use annotations to push out metrics.

The following are available as annotations:

<table width="100%">
    <tr><td><b>Annotation</b></td><td><b>Description</b></td></tr>
    <tr><td>@FnordMetricCounter</td><td>Used to add to a counter when a method is called</td></tr>
    <tr><td>@FnordMetricTimer</td><td>Used to add a timer to the method that is called</td></tr>
</table>

[grails-statsd-repo]: https://github.com/charliek/grails-statsd
[FnordMetric_repo]: https://github.com/paulasmuth/FnordMetric
[FnordMetric_site]: http://FnordMetric.io/
[genericobjectpool]:http://commons.apache.org/pool/apidocs/org/apache/commons/pool/impl/GenericObjectPool.html