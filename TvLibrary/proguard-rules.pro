# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android-sdk-windows/tools/proguard/proguard-android.txt
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


-optimizationpasses 5
##混淆时不会产生形形色色的类名
#-dontusemixedcaseclassnames
##指定不去忽略非公共的库类。
-dontskipnonpubliclibraryclasses
##指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers
##不预校验
-dontpreverify

-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt

##优化
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-allowaccessmodification

##保持annotation注释属性，泛型中常用
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile

##保持SourceFile/LineNumberTable属性
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

-keepattributes Signature

##继承之下面的类不进行混淆保持原样
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

-ignorewarning

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

##保持TvWidget库不混淆
-dontwarn com.open.androidtvwidget.**
-keep class com.open.androidtvwidget.** { *;}

##保持TvLibrary库不混淆
-dontwarn com.funhotel.tvlibrary.**
-keep class com.funhotel.tvlibrary.** { *;}

#Gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *;}


#okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}

#Gson
-dontwarn jp.wasabeef.glide.transformations.**
-keep class jp.wasabeef.glide.transformations.** { *;}


-dontwarn android.support.annotation.**
-keep class android.support.annotation.** { *;}


-dontwarn android.support.multidex.**
-keep class android.support.multidex.** { *;}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
