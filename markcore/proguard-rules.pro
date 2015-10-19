# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-libraryjars libs/gson-2.2.1.jar
-dontwarn com.google.gson.*
-keep class com.google.gson.** { *;}
-keep interface com.google.gson.** { *; }


-libraryjars libs/volley.jar
-dontwarn com.android.volley.*
-keep class com.android.volley.** { *;}
-keep interface com.android.volley.** { *; }


-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
