# Android Studio TDD/BTDD Example for Sauce Labs Integration.
This code is provided on an "AS-IS” basis. Your tests and testing environments may require you to modify this framework. Issues regarding this framework should be submitted through GitHub. For questions regarding Sauce Labs integration, please see the Sauce Labs documentation at https://wiki.saucelabs.com/. This framework is not maintained by Sauce Labs Support. 

# Setup
* Install Android Studio.
* Create a JUnit run configuration.
* Add Sauce Labs credentials to the run config as environment variables.
```
SAUCE_USERNAME=<sauce username>
SAUCE_ACCESS_KEY=<sauce access key>
BUILD_TAG=<optional build id>
```
* Select UnitTest build variant.


# Running tests
* Run/Debug tests using Android Studio.

# Console Instructions
* Set Sauce Labs credentials
```
$export SAUCE_USERNAME=<sauce username>
$export SAUCE_ACCESS_KEY=<sauce access key>
$export BUILD_TAG=<optional build id>
```
* Run the gradle task as shown below
```
$./gradlew testDebugUnitTest 
```
