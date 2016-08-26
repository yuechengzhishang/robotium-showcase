# robotium-showcase 

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-robotium--showcase-green.svg?style=true)](https://android-arsenal.com/details/3/4142)

This example shows how to use Robotium with `ActvitityTestRule` and @Test annotation, it means in Google's Espresso way.

## Robotium in Google's Espresso way

### Motivation 

Most of Robotium examples rely on [ActivityInstrumentationTestCase2](https://developer.android.com/reference/android/test/ActivityInstrumentationTestCase2.html), which is already marked as deprecated.  Instead, Use `ActivityTestRule` using the Android Testing Support Library.

Another reason to change from `ActivityInstrumentationTestCase2` to `ActivityTestRule` is that it uses generated boilerplate code for initializing and finishing tests. Furthermore, `@SmallTest`, `@MediumTest`, and `@LargeTest` annotations have been deprecated but a `timeout` parameter can specify the expected duration of the text:

Instead of:

```java
@MediumTest
public void testStartSecondActivity() throws Exception {
    solo.waitForActivity("SecondActivity", 2000); 
    ...
}
```

We would use:
```java
@Test(timeout = 2000)
public void startMainActivityIsProperlyDisplayed() throws InterruptedException {
    solo.waitForActivity("MainActivity", 1000);
    ...
}
```

Before we start using `ActivityTestRule` with `Robotium` testing, we need to need to change our existing configuration. 

### Configuration

####Start new Android Application project

For better experience create a new Android `Empty Project` and follows these steps:

Add the [Android Testing Support Library](https://developer.android.com/tools/testing-support-library/index.html) dependencies in project's `build.gradle` file:

```gradle
dependencies {
    androidTestCompile 'com.android.support.test:runner:0.4.1'
    androidTestCompile 'com.android.support.test:rules:0.4.1'
    androidTestCompile 'com.android.support:support-annotations:24.1.1'
    androidTestCompile 'com.jayway.android.robotium:robotium-solo:5.6.1'
}
```

#### Define `testInstrumentationRunner` in project's `build.gradle` file

Don't forget about defining your `testInstrumentationRunner` runner! Otherwise your tests won't run.

Just add this line:
```gradle
testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
```

Finally your `build.gradle file` should look like below:
```
apply plugin: 'com.android.application'
    
android {
  compileSdkVersion 24
  buildToolsVersion "24.0.1"
    
  defaultConfig {
     applicationId "com.example.piotr.myapplication"
     minSdkVersion 15
     targetSdkVersion 21
     versionCode 1
     versionName "1.0"
     testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
  }
    
  buildTypes {
     release {
          minifyEnabled false
          proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
     }
  }  
    
  dependencies {
     compile fileTree(dir: 'libs', include: ['*.jar'])
     testCompile 'junit:junit:4.12'
     compile 'com.android.support:appcompat-v7:24.1.1'
     compile 'com.android.support:design:24.1.1'
     androidTestCompile 'com.android.support.test:runner:0.4.1'
     androidTestCompile 'com.android.support.test:rules:0.4.1'
     androidTestCompile 'com.android.support:support-annotations:24.1.1'
     compile 'com.jayway.android.robotium:robotium-solo:5.6.1'    
  }
```      

#### Create `MainActivityTest` testing class

Now we have all needed configuration to start test coding using `Robotium`. 

Just create a new Java class and write the code below:

```java
  @RunWith(AndroidJUnit4.class) 
  public class MainActivityTest {

  private Solo solo;
    
  private static final String MAIN_ACTIVITY = MainActivity.class.getSimpleName();
    
  @Rule
  public MyActivityTestRule<MainActivity> mActivityRule = new MyActivityTestRule<>(MainActivity.class);
    
  @Before
  public void setUp() throws Exception {
     solo = new Solo(mActivityRule.getInstrumentation(), mActivityRule.getActivity());
  }
    
  @Test(timeout = 5000)
  public void checkIfMainActivityIsProperlyDisplayed() throws Exception {
    solo.waitForActivity("MainActivity", 2000);
    solo.assertCurrentActivity(mActivityRule.getActivity().getString(
              R.string.error_no_class_def_found, MAIN_ACTIVITY), MAIN_ACTIVITY);
    solo.getText("Hello World").isShown();
    
  }
}
```

#### Running the tests    

To run tests, **right-click** on the name of class or method and select `Run `[nameOfTest]``

#### Other annotations

As you may notice there are some new annotations to remember:
  *  `@RunWith()` - describes which runner we would use in our test class.
  *  `@Rule` - defines where your actual `TestRule` is defined
  *  `@Test` - thanks to this annotation, you can name your test method as you would like to (i.e. no more `testSomethingToDo`)

## References
* <https://github.com/codepath/android_guides/wiki/UI-Testing-with-Robotium/>
* <http://www.guru99.com/why-android-testing.html>
* <http://wiebe-elsinga.com/blog/whats-new-in-android-testing>
* <http://stackoverflow.com/questions/38783594/use-test-annotation-and-activitytestrule-with-robotium-espresso-way>

Issues
------

Feel free to submit issues and enhancement requests.

Contributing
------------

Please refer to each project's style guidelines and guidelines for submitting patches and additions. In general, we follow the "fork-and-pull" Git workflow.

 1. **Fork** the repo on GitHub
 2. **Clone** the project to your own machine
 3. **Commit** changes to your own branch
 4. **Push** your work back up to your fork
 5. Submit a **Pull request** so that we can review your changes

License
-------

    Copyright 2016 Piotr Ekert

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
