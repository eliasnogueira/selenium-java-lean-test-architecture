The purpose of this project is to have a basic project with minimum viable testing architecture to automated web tests using Selenium WebDriver and Java as the programming language.

## Languages and Frameworks

This project using the following languages and frameworks:

* Java 8 as the programming language
* TestNG as the UnitTest framework to support the test creation

## Test architecture

We know that any automation project starting with a good test architecture.
This project can be your initial test architecture for a faster start.
You will see the following items in this architecture:

* Use of Page Objects patters
* Parallel execution
* BaseTest
* TestListner
* Logging
* Configuration through a properties file
* yaml templates to create a Selenium Grid infrastructure on OpenShift

Do you have any other item to add on this test architecture? Please do a pull request or open an issue to discuss

### Use of Page Objects patters

### Parallel execution

### BaseTest

### TestListner

### Logging

### Configuration through a properties file

The project use the a property file to configure basic mutable items, like:

* base.url: the main app URL
* grid.url: target grid url
* grid.port: target grid port
* log.directory = the name of the log folder
* log.dateformat = the data format for the log filename

The property file is inside _conf_ folder. This folder has three sub-folders:

* dev:
* test: 
* prod:

You can set different values for the properties keys based on your environment.
When you run a test without inform the parameter `env`, the property file used will be _conf/dev/config.properties_.

To change the environment, just use `-Denv=env_name` where _env_name_ is the name of the folder inside _conf_ folder.

E.g: running all tests using test configuration values

``` bash
mvn test -Denv=test
```

### Profiles executors on pom.xml

There is a profile called _multi-browser_ created to execute the test suite _multi_browser.xml_ inside _src/test/resources/suites_ folder.
To execute this suite, via command line you can call the parameter `-P` and the profile id.

Eg: executing the multi_browser suite
``` bash
mvn test -Pmulti-browser
```

If you have more than one suite on _src/test/resources/suites_ folder you can parameterize the xml file name.
To do this you need:

* Create a property on pom.xml called _suite_

```xml
    <properties>
        <suite>multi_browser</suite>
    </properties>
```

* Change the profile id

```xml
<profile>
   <id>web_execution</id>
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
mvn test -Pweb_execution -Dsuite=multi_browser
````

### Pipeline as a code

The two files of pipeline as a code are inside _pipeline_as_code_ folder.

#### Jenkins pipeline

You can you _Jenkisfile_ on this directory and refer _pipeline_as_code/Jenkinsfile_ on XXXXXX item on Jenkins.
This pipeline works on
