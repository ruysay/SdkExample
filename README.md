#SDK Example

This project is a SDK example which provides a library queries the ‘comments’ API at `https://jsonplaceholder.typicode.com/`. The
SDK will query one comment every minute from comment id of 1 to 500, and print out the
comment using `android.util.Log.i()`. 
The the device enters doze mode, it will schedule a background job which will be executed in maintenance windows.

The SDK shall be easily installable into an app as an
aar file, and should be doze mode proof and resilient to device restarts.


##Usage

1. Link the library to your project


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
 - Evernote Android-Job, handles jobs in the background
    https://github.com/evernote/android-job
 - Okhttp
 - Gson


