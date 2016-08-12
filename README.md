# robotium-showcase

This example shows how to use Robotium with `ActvitityTestRule` and @Test annotation, it means in Google's Espresso way.

##Robotium in Google's Espresso way

###Motivation 

Most of `Robotium` examples [`ActivityInstrumentationTestCase2`]([https://developer.android.com/reference/android/test/ActivityInstrumentationTestCase2.html]), which is already marked as deprecated, so it won't be maintained any more. `Android Reference` says clearly that:

> `**This class was deprecated in API level 24.**
>
> Use `ActivityTestRule` instead. New tests should be written using the `Android Testing Support Library`.

Another reason to change `ActivityInstrumentationTestCase2` into `ActivityTestRule` is a generated boilerplate code used for initialization and finishing tests. Futhermore, let's think for a moment what if instead of 

    @MediumTest
    public void testStartSecondActivity() throws Exception {
         solo.waitForActivity("SecondActivity", 2000); 
         ...
we could write:

    @Test(timeout = 2000)
    public void startMainActivityIsProperlyDisplayed() throws InterruptedException {
        solo.waitForActivity("MainActivity", 1000);
        ...

####To resume what was already said, we want to achieve:

- using `ActivityTestRule` or similar like `InstrumentationTestRule`
- writing our own test rules
- no more `ActivityInstrumentationTestCase2` which is deprecated
- adding support for new `Android Testing Support Library`
- reducing `Robotium` tests flakyness and boilerplate code
- no more `testDoSomethingWhatIsToDo` methods name
- using @Test, @Rule and other great Android test annotations
- no more @SmallTest, @MediumTest, @LargeTest (they're also deprecated)
- use @Test arguments like @Test(timeout = 3000) instead of @MediumTest
...


Before we start our journey with using `ActivityTestRule` with `Robotium` testing we need to need to change our existing configuration. 

###Configuration

####Start new Android Application project

For better experience create a new Android `Empty Project` and follows these steps:

####Add [`Android Testing Support Library`](https://developer.android.com/tools/testing-support-library/index.html) dependencies in project's `build.gradle` file:

        androidTestCompile 'com.android.support.test:runner:0.4.1'
        androidTestCompile 'com.android.support.test:rules:0.4.1'
        androidTestCompile 'com.android.support:support-annotations:24.1.1'
        androidTestCompile 'com.jayway.android.robotium:robotium-solo:5.6.1'

####Define `testInstrumentationRunner` in project's `build.gradle` file

Don't forget about defining your `testInstrumentationRunner` runner! Otherwise your tests won't run.

Just add this line:

      testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

Finally your `build.gradle file` should look like below:

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

####Define your own `ActivityTestRule`

`Android Testing Support Library` supports creating your own test rules for tests. 

For this example, just create in `androidTest` folder new Java class, let's say `MyActivityTestRule` copy existing code from my project. You would find it [here](https://github.com/piotrek1543/robotium-showcase/blob/master/app/src/androidTest/java/com/example/piotr/robotium_showcase/rule/MyActivityTestRule.java)

[`MyActivityTestRule`](https://github.com/piotrek1543/robotium-showcase/blob/master/app/src/androidTest/java/com/example/piotr/robotium_showcase/rule/MyActivityTestRule.java) is just a standard Google's `ActivityTestRule` with some additional getters, which we will use later in our test class.

> **NOTE:** This example is available here: https://github.com/piotrek1543/robotium-showcase

####Create your own first `Robotium` testing class

Now we have all needed configuration to start test coding using `Robotium`. 

Just create a new Java class and write the code below:

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

####Running the tests    
To run tests, **right-click** on the name of class or method and select `Run `[nameOfTest]``

####Aditional informations
As you may notice there are some new annotations to remember:
  *  `@RunWith()` - it describes which runner we would use in our test class.
  *  `@Rule` - it defines where your actual `TestRule` is defined
  *  `@Test` - thanks to this annotation, you can name your test method as you would like to, no more `testSomethingToDo`

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
