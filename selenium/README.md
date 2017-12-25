# Selenium Tests

The Selenium functional tests can be ran as classical integration tests:

    mvn clean verify

## Troubleshooting

### Run Tomcat Server

Web application **java-example-selenium** can be ran in an embedded Tomcat
server without running tests:

    mvn clean tomcat7:run

Then, visit <http://localhost:8080/java-examples-selenium/>. This preview is
useful for understanding the web-app mechanism, before writing any functional
tests.

### Run Selenium Server

    mvn selenium:start-server

## References

- [Deep Shah's Blog: How to run selenium tests as part of maven build phase][1]

[1]: http://www.gitshah.com/2010/10/how-to-run-selenium-tests-as-part-of.html
