A simple wrapper around jdbc drivers that will show some statistics about the running queries on a simple web page on port 18080.

To use:

Add the following jars to your classpath:

* jdbc-stats-0.1.jar
* slf4j-api-1.6.4.jar (add a slf4j binding if you want to see some logging)
* ssr-0.1.jar
* vst-0.4.jar

Add the following jar if you are not running in a web container:

* servlet-api-2.5.jar

Start your application and goto: http://<host app is running>:18080/

You will see an overview of the last 2500 queries run on your database and some timing stats about them.