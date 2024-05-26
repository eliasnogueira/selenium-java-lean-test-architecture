# Lean Test Automation Architecture using Java and Selenium WebDriver

[![Actions Status](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/workflows/Build%20and%20Test/badge.svg)](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/actions)

**This project delivers to you a complete lean test architecture for your web tests using the best frameworks and
practices.**

It has a complete solution to run tests in different ways:

* local testing using the browser on your local machine
* parallel (or single) testing using Selenium Docker
* local testing using TestContainers
* Distributed execution using Selenium Grid

## Examples

### Local testing execution example

![Local testing execution example](assets/example_filed_test_with_report.gif)

### Parallel testing execution example with Selenium Grid

![Parallel testing execution example with Selenium Grid](assets/selenium-grid-execution.gif)

## Languages and Frameworks

This project uses the following languages and frameworks:

* [Java 22](https://openjdk.java.net/projects/jdk/22/) as the programming language
* [TestNG](https://testng.org/doc/) as the UnitTest framework to support the test creation
* [Selenium WebDriver](https://www.selenium.dev/) as the web browser automation framework using the Java binding
* [AssertJ](https://joel-costigliola.github.io/assertj/) as the fluent assertion library
* [Allure Report](https://docs.qameta.io/allure/) as the testing report strategy
* [DataFaker](https://www.datafaker.net/) as the faker data generation strategy
* [Log4J2](https://logging.apache.org/log4j/2.x/) as the logging management strategy
* [Owner](http://owner.aeonbits.org/) to minimize the code to handle the properties file
* [TestContainers](https://java.testcontainers.org/modules/webdriver_containers/) Webdriver Containers

## Test architecture

We know that any automation project starts with a good test architecture.

This project can be your initial test architecture for a faster start.
You will see the following items in this architecture:

* [Page Objects pattern](#page-objects-pattern)
* [Execution types](#execution-types)
* [BaseTest](#basetest)
* [TestListener](#testlistener)
* [Logging](#logging)
* [Configuration files](#configuration-files)
* [Parallel execution](#parallel-execution)
* [Test Data Factory](#test-data-factory)
* [Profiles executors on pom.xml](#profiles-executors-on-pomxml)
* [Pipeline as a code](#pipeline-as-a-code)
* [Test environment abstraction](#execution-with-docker-selenium-distributed)

Do you have any other items to add to this test architecture? Please do a pull request or open an issue to discuss.

### Page Objects pattern

I will not explain the Page Object pattern because you can find a lot of good explanations and examples on the internet.
Instead, I will explain what exactly about page objects I'm using in this project.

#### AbstractPageObject

This class has a protected constructor to remove the necessity to init the elements using the Page Factory.
Also, it sets the timeout from the `timeout` property value located on `general.properties` file.

All the Page Object classes should extend the `AbstractPageObject`.
It also tries to remove the `driver` object from the Page Object class as much as possible.

> **Important information**
>
> There's a `NavigationPage` on the `common` package inside the Page Objects.
> Notice that all the pages extend this one instead of the `AbstractPageObject`. I implemented this way:
> * because the previous and next buttons are fixed on the page (there's no refresh on the page)
> * to avoid creating or passing the new reference to the `NavigationPage` when we need to hit previous or next buttons

As much as possible avoid this strategy to not get an `ElementNotFoundException` or `StaleElementReferenceException`.
Use this approach if you know that the page does not refresh.

### Execution types

There are different execution types:

- `local`
- `local-suite`
- `selenium-grid`
- `testcontainers`

The `TargetFactory` class will resolve the target execution based on the `target` property value located
on `general.properties` file. Its usage is placed on the `BaseWeb` class before each test execution.

#### Local execution

##### Local machine

**This approach is automatically used when you run the test class in your IDE.**

When the `target` is `local` the `createLocalDriver()` method is used from the `BrowserFactory` class to return the
browser instance.

The browser used in the test is placed on the `browser` property in the `general.properties` file.

##### Local Suite

It's the same as the Local Execution, where the difference is that the browser is taken from the TestNG suite file instead of the `general.properties`
file, enabling you to run multi-browser test approach locally.

##### Testcontainers

This execution type uses the [WebDriver Containers](https://www.testcontainers.org/modules/webdriver_containers/) in
Testcontainers to run the tests in your machine, but using the Selenium docker images for Chrome or Firefox.

When the `target` is `testcontainers` the `TargetFactory` uses the `createTestContainersInstance()` method to initialize
the container based on the browser set in the `browser` property. Currently, Testcontainers only supports Chrome and
Firefox.

Example
```shell
mvn test -Pweb-execution -Dtarget=testcontainers -Dbrowser=chrome
```

#### Remote execution

##### Selenium Grid

The Selenium Grid approach executes the tests in remote machines (local or remote/cloud grid).
When the `target` is `selenium-grid` the `getOptions` method is used from the `BrowserFactory` to return the browser
option
class as the remote execution needs the browser capability.

The `DriverFactory` class has an internal method `createRemoteInstance` to return a `RemoteWebDriver` instance based on
the browser capability.

You must pay attention to the two required information regarding the remote execution: the `grid.url` and `grid.port`
property values on the `grid.properties` file. You must update these values before the start.

If you are using the `docker-compose.yml` file to start the Docker Selenium grid, the values on the `grid.properties`
file should work.

You can take a look at the [Execution with Docker Selenium Distributed](#execution-with-docker-selenium-distributed)
to run the parallel tests using this example.

#### BrowserFactory class

This Factory class is a Java enum that has all implemented browsers to use during the test execution.
Each browser is an `enum`, and each enum implements four methods:

* `createLocalDriver()`: creates the browser instance for the local execution. The browser driver is automatically
  managed by the WebDriverManager library
* `createDriver()`: creates the browser instance for the remote execution
* `getOptions()`: creates a new browser `Options` setting some specific configurations, and it's used for the remote
  executions using the Selenium Grid
* `createTestContainerDriver()` : Creates selenium grid lightweight test container in Standalone mode with Chrome/Firefox/Edge browser support.

You can see that the `createLocalDriver()` method use the `getOptions()` to get specific browser configurations, as
starting the browser maximized and others.

The `getOptions()` is also used for the remote execution as it is a subclass of the `AbstractDriverOptions` and can be
automatically accepted as either a `Capabilities` or `MutableCapabilities` class, which is required by
the `RemoteWebDriver` class.

#### DriverManager class

The
class [DriverManager](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/driver/DriverManager.java)
create a `ThreadLocal` for the WebDriver instance, to make sure there's no conflict when we run it in parallel.

### BaseTest

This testing pattern was implemented on
the [BaseWeb](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/test/java/com/eliasnogueira/BaseWeb.java)
class to automatically run the pre (setup) and post (teardown) conditions.

The pre-condition uses `@BeforeMethod` from TestNG creates the browser instance based on the values passed either local
or remote execution.
The post-condition uses `@AfterMethod` to close the browser instance.
Both have the `alwaysRun` parameter as `true` to force the run on a pipeline.

Pay attention that it was designed to open a browser instance to each `@Test` located in the test class.

This class also has the `TestListener` annotation which is a custom TestNG listener, and will be described in the next
section.

### TestListener

The `TestListener` is a class that
implements [ITestListener](https://testng.org/doc/documentation-main.html#logging-listeners).
The following method is used to help logging errors and attach additional information to the test report:

* `onTestStart`: add the browser information to the test report
* `onTestFailure`: log the exceptions and add a screenshot to the test report
* `onTestSkipped`: add the skipped test to the log

### Logging

All the log is done by the Log4J using the `@Log4j2` annotation.

The `log4j2.properties` has two strategies: console and file.
A file with all the log information will be automatically created on the user folder with `test_automation.log`
filename.
If you want to change it, update the `appender.file.fileName` property value.

The `log.error` is used to log all the exceptions this architecture might throw. Use `log.info` or `log.debug` to log
important information, like the users, automatically generated by the
factory [BookingDataFactory](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/data/BookingDataFactory.java)

### Parallel execution

The parallel test execution is based on
the [parallel tests](https://testng.org/doc/documentation-main.html#parallel-tests)
feature on TestNG. This is used by `selenium-grid.xml` test suite file which has the `parallel="tests"` attribute and value,
whereas `test` item inside the test suite will execute in parallel.
The browser in use for each `test` should be defined by a parameter, like:

```xml

<parameter name="browser" value="chrome"/>
```

You can define any parallel strategy.

It can be an excellent combination together with the grid strategy.

#### Execution with Docker Selenium Distributed

This project has the `docker-compose.yml` file to run the tests in a parallel way using Docker Selenium.
To be able to run it in parallel the file has
the [Dynamic Grid Implementation](https://github.com/SeleniumHQ/docker-selenium#dynamic-grid-) that will start the
container on demand.

This means that Docker Selenium will start a container test for a targeting browser.

Please note that you need to do the following actions before running it in parallel:

* Docker installed
* Pull the images for Chrome Edge and Firefox - Optional
  * Images are pulled if not available and initial test execution will be slow
      * `docker pull selenium-standalog-chrome`
      * `docker pull selenium-standalog-firefox`
      * `docker pull selenium/standalone-edge`
  * If you are using a MacBook with either M1 or M2 chip you must check the following experimental feature in Docker Desktop: Settings -> Features in development -> Use Rosetta for x86/amd64 emulation on Apple Silicon
* Pay attention to the `grid/config.toml` file that has comments for each specific SO
* Start the Grid by running the following command inside the `grid` folder
    * `docker-compose up`
* Run the project using the following command
```shell
mvn test -Pweb-execution -Dsuite=selenium-grid -Dtarget=selenium-grid -Dheadless=true
```
* Open the [Selenium Grid] page to see the node status

### Configuration files

This project uses a library called [Owner](http://owner.aeonbits.org/). You can find the class related to the property
file reader in the following classes:

* [Configuration](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/config/Configuration.java)
* [ConfigurationManager](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/config/ConfigurationManager.java)

There are 3 properties (configuration) files located on `src/test/java/resources/`:

* `general.properties`: general configuration as the target execution, browser, base url, timeout, and faker locale
* `grid.properties`: url and port for the Selenium grid usage

The properties were divided into three different ones to better separate the responsibilities and enable the changes
easy without having a lot of properties inside a single file.

### Test Data Factory

Is the utilization of the Factory design pattern with the Fluent Builder to generate dynamic data.
The [BookingDataFactory](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/data/BookingDataFactory.java)
has only one factory `createBookingData` returning a `Booking` object with dynamic data.

This dynamic data is generated by JavaFaker filling all the fields using the Build pattern.
The [Booking](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/model/Booking.java)
is the plain Java objects
and
the [BookingBuilder](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/model/BookingBuilder.java)
is the builder class.

You can see the usage of the Builder pattern in
the [BookingDataFactory](https://github.com/eliasnogueira/selenium-java-lean-test-achitecture/blob/master/src/main/java/com/eliasnogueira/data/BookingDataFactory.java)
class.

Reading reference: https://reflectoring.io/objectmother-fluent-builder

### Profiles executors on pom.xml

There is a profile called `web-execution` created to execute the test suite `local.xml`
inside `src/test/resources/suites` folder.
To execute this suite, via the command line you can call the parameter `-P` and the profile id.

Eg: executing the multi_browser suite

``` bash
mvn test -Pweb-execution -Dtestng.dtd.http=true 
```

If you have more than one suite on _src/test/resources/suites_ folder you can parameterize the xml file name.
To do this you need:

* Create a property on `pom.xml` called _suite_

```xml

<properties>
    <suite>local</suite>
</properties>
```

* Change the profile id

```xml

<profile>
    <id>web-execution</id>
</profile>   
```

* Replace the xml file name to `${suite}` on the profile

```xml

<configuration>
    <suiteXmlFiles>
        <suiteXmlFile>src/test/resources/suites/${suite}.xml</suiteXmlFile>
    </suiteXmlFiles>
</configuration>
```

* Use `-Dsuite=suite_name` to call the suite

````bash
mvn test -Pweb-execution -Dsuite=parallel
````

### Pipeline as a code

The two files of the pipeline as a code are inside `pipeline_as_code` folder.

* GitHub Actions to use it inside the GitHub located at `.github\workflows`
* Jenkins: `Jenkinsfile` to be used on a Jenkins pipeline located at `pipeline_as_code`
* GitLab CI: `.gitlab-ci.yml` to be used on a GitLab CI `pipeline_as_code`
