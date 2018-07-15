#SDK Example

This project is a SDK example which provides a library queries the ‘comments’ API at `https://jsonplaceholder.typicode.com/`. The
SDK will query one comment every minute from comment id of 1 to 500, and print out the
comment using `android.util.Log.i()`. 
The the device enters doze mode, it will schedule a background job which will be executed in maintenance windows.

The SDK shall be easily installable into an app as an
aar file, and should be doze mode proof and resilient to device restarts.


##Usage

1. Link the library to your project

Create ‘libs’ folder under src/main
Copy SDK-debug.aar to src/main/libs

update dependency of libraries and SDK
~~~~
    implementation 'com.evernote:android-job:1.3.0-alpha03'
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation (name:'SDK-debug', ext:'aar')
~~~~

link aar file by editing statment in your application top-level build.gradle as below
~~~~
allprojects {
    repositories {
        google()
        jcenter()
        flatDir {
            dirs 'src/main/libs'
        }
    }
}
~~~~

2. Extend BaseActivity provided by the library
For Example, if MainActivity is the Activity that you would like to use the library.
~~~~java
public class MainActivity extends BaseActivity { .... }
~~~~

3. Add Following in the project manifest file.
~~~~
<service
    android:name="com.api.local.sdk.LsServicePlus"
    android:enabled="true" />
~~~~

LsServicePlus is a background service which queries comments API periodically.
LsJobService is a 

4. Add Permission for the library
~~~~
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
~~~~

5. Check result
Result of querying comments will be filter by TAG "LsServicePlus"
adb logcat | grep LsServicePlus

##ToDo
- Refine Doze mode job handling 
  1.Enqueue comment fetching jobs into Background job pipe when device is idle, 
    Enqueued jobs will be executed during maintenance windows.
  2.Improve comment query url handling

- Refine SDK configuration
  1. Extract query settings to a config file
  2. SDK will read setting from config file

##3rd party library dependency (besides Google)
This library used external libraries:
 - Evernote Android-Job
 - Okhttp
 - Gson


