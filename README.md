![Logo](https://www.browserstack.com/images/static/header-logo.jpg)



# <h1 align="center"> :zap: BrowserStack  Android Accessibility Testing using Espresso + ATF <a href="https://developer.android.com/training/testing/espresso">  <img src="https://developer.android.com/images/training/testing/espresso.png"  alt="WebdriverIO"  height="35"  /></a>  <img src="https://i0.wp.com/www.digitala11y.com/wp-content/uploads/2021/12/digital-a11y-logo-e1621173355118-1.png?fit=298%2C320&ssl=1" height="37"  /> :zap:</h1>



# :label: [Introduction](https://github.com/BrowserStackCE/espresso-atf-browserstack-example#introduction)



Accessibility Test Framework (ATF) for Android help people with disabilities access Android apps, developers of those apps need to consider how their apps will be presented to accessibility services. Some good practices can be checked by automated tools, such as if a View has a contentDescription. Other rules require human judgment, such as whether or not a contentDescription makes sense to all users.




# :gear: [Repository Setup](https://github.com/BrowserStackCE/espresso-atf-browserstack-example#repositorysetup)



## Prerequisites

Ensure you have the following dependencies installed on the machine


* Android Studio ( Download it from [Download Android Studio and SDK tools](https://developer.android.com/studio "https://developer.android.com/studio") )
* Download JDK16 from [here](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)
* Update your JDK location via Project Structure (Android Studio)
    * Go to File > Project Structure.
    * Select the SDK Location section in the list of the left.
    * Select Gradle Settings
    * Under Gradle JDK, Select Java version 16.
* [BrowserStack App Automate Account](https://www.browserstack.com/automate). ![BrowserStack](https://img.shields.io/badge/For-BrowserStackAppAutomate-Green)



## :wrench: Repository Configuration



:pushpin: Clone this repository

<code> git clone git@github.com:BrowserStackCE/espresso-atf-browserstack-example.git</code>

:pushpin: Navigate to the repository directory

<code>cd espresso-atf-browserstack-example</code>


# :rocket: [Test Execution](https://github.com/BrowserStackCE/espresso-atf-browserstack-example#testexecution)



## Test Execution Prerequisites [![BrowserStack](https://img.shields.io/badge/For-BrowserStackAppAutomate-Green)]()



:pushpin: Create a new [BrowserStack account](https://www.browserstack.com/users/sign_up) or use an existing one.

:pushpin: Identify your BrowserStack username and access key from the [BrowserStack AppAutomate Dashboard](https://app-automate.browserstack.com/) and export them as environment variables using the below commands.


- For Unix-like or Mac machines:



```sh

export BROWSERSTACK_USERNAME=<browserstack-username> &&

export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>

```



- For Windows:



```shell

set BROWSERSTACK_USERNAME=<browserstack-username>

set BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>

```



## Test Execution

Espresso tests are built into a separate `apk` file from your app. Hence, you will need access to both the app (`.apk` or `.aab` file) as well as Espresso test-suite (separate `.apk` file).

### :pushpin: Manual Test execution from Command line
* **Execute below commands to build the application**

    * **Build the main application:** `./gradlew assemble`
      (apk will be generated in the `app/build/outputs/apk/debug/` directory)

    * **Build the test application:** `./gradlew assembleAndroidTest`
      (apk will be generated in the `app/build/outputs/apk/androidTest/debug/` directory)


* **Upload your app**

Upload your Android app (`.apk` or `.aab` file) to BrowserStack servers using our [REST API](https://www.browserstack.com/docs/app-automate/api-reference/espresso/apps#upload-an-app "https://www.browserstack.com/docs/app-automate/api-reference/espresso/apps#upload-an-app"). Here is an example `cURL` request to upload the app :

```sh
curl -u "$BROWSERSTACK_USERNAME:$BROWSERSTACK_ACCESS_KEY" \
-X POST "https://api-cloud.browserstack.com/app-automate/espresso/v2/app" \
-F "file=@app/build/outputs/apk/debug/app-debug.apk"
```
A sample response for the above request is shown below:


```sh
{
    "app_url":"bs://j3c874f21852ba57957a3fdc33f47514288c4ba4"
}
```
Note the value of `app_url` returned in the response to above REST API request. This will be used later to specify the application under test for your Espresso test execution.

* **Upload your test-suite**

Upload your Espresso + ATF test-suite (`.apk` file) to BrowserStack servers using our [REST API](https://www.browserstack.com/docs/app-automate/api-reference/espresso/tests#upload-a-test-suite "https://www.browserstack.com/docs/app-automate/api-reference/espresso/tests#upload-a-test-suite"). Here is an example `cURL` request to upload the test-suite :

```sh 
curl -u "$BROWSERSTACK_USERNAME:$BROWSERSTACK_ACCESS_KEY" \
-X POST "https://api-cloud.browserstack.com/app-automate/espresso/v2/test-suite" \
-F "file=@app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk"
```

A sample response for the above request is shown below:

```sh
{
    "test_url":"bs://f7c874f21852ba57957a3fdc33f47514288c4ba4"
}
```

* **Execute Espresso + ATF tests**

Once the app and test-suite are uploaded to BrowserStack servers, you are ready to run your first Espresso test on BrowserStack. Use the [REST API](https://www.browserstack.com/docs/app-automate/api-reference/espresso/builds#execute-a-build "https://www.browserstack.com/docs/app-automate/api-reference/espresso/builds#execute-a-build") to start the test execution as shown in the example `cURL` request below :

```sh
curl -u "$BROWSERSTACK_USERNAME:$BROWSERSTACK_ACCESS_KEY" \
-X POST "https://api-cloud.browserstack.com/app-automate/espresso/v2/build" \
-d '{"app": "bs://j3c874f21852ba57957a3fdc33f47514288c4ba4", "testSuite": "bs://f7c874f21852ba57957a3fdc33f47514288c4ba4", "devices": ["Samsung Galaxy S9 Plus-9.0"]}' \
-H "Content-Type: application/json" 
```
An example response to the above request is shown below. Note that the `build_id` is used to uniquely identify your test execution.
```sh
{
    "message" : "Success",
    "build_id" : "4d2b4deb810af077d5aed98f479bfdd2e64f36c3"
}
```
### :pushpin:Test execution from Command line with Browserstack Gradle Plugin

* **Add browserStackConfig parameters to module's [build.gradle](app/build.gradle) file**

```
browserStackConfig {
    username = "<browserstack_username>"
    accessKey = "<browserstack_access_key>"
    configFilePath = '<path/to/your/json/configFile>'
}
```

* **Execute below command to run test on Browserstack**

```sh
./gradlew clean executeDebugTestsOnBrowserstack
```



:page_facing_up: **Note:**
* List of supported devices and be found [here](https://api.browserstack.com/app-automate/espresso/devices.json) (basic auth required).  For example :
```sh 
curl -u "$BROWSERSTACK_USERNAME:$BROWSERSTACK_ACCESS_KEY" \ 
https://api-cloud.browserstack.com/app-automate/devices.json 
```
* We have configured a list of test capabilities in this [configuration file](app/config-browserstack.json). You are free update them based on your device test requirements. To view the list of all supported parameters for Espresso tests on BrowserStack, visit complete list of API parameters section inside our [Espresso Get Started documentation](https://www.browserstack.com/app-automate/espresso/get-started)



# :card_file_box: [Additional Resources](https://github.com/BrowserStackCE/espresso-atf-browserstack-example#additionalresources)



- View your test results on the [BrowserStack App-Automate dashboard](https://app-automate.browserstack.com/dashboard/v2)

-   To learn more about accessibility testing, visit the accessibility testing page on [developer.android.com](https://developer.android.com/guide/topics/ui/accessibility/testing "https://developer.android.com/guide/topics/ui/accessibility/testing").

-   Get the source for the **Accessibility Test Framework** by visiting its [GitHub](https://github.com/google/Accessibility-Test-Framework-for-Android "https://github.com/google/Accessibility-Test-Framework-for-Android") repository.

- Customizing your tests capabilities on BrowserStack using our [test capability generator](https://www.browserstack.com/app-automate/capabilities)

- Browserstack Gradle Plugin [Github](https://github.com/browserstack/browserstack-gradle-plugin)