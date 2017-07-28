# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk\Android\Sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontshrink
-dontoptimize
-useuniqueclassmembernames
-dontusemixedcaseclassnames
-keepattributes Signature,*Annotation*
-adaptresourcefilenames **.properties
-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF
-dontpreverify
-verbose
-ignorewarnings
-dontwarn


-keep public class * extends android.app.Activity

-keep public class * extends android.app.Application

-keep public class * extends android.app.Service

-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends android.app.backup.BackupAgentHelper

-keep public class * extends android.preference.Preference

-keep class * extends android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep class * extends android.widget.Button
-keep class android.support.v4.**{ *;}
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v7.**
-keep interface android.support.v7.app.** { *; }
-dontwarn android.support.**    # 忽略警告
-keep class com.android.support.**{ *;}
-keep class com.qq.e.** { *;}
-keep class com.qq.e.comm.adevent.**{ *;}
-keep class com.qq.e.comm.constants.**{ *;}
-keep class com.qq.e.comm.manangers.**{ *;}
-keep class com.qq.e.comm.net.**{*;}
-keep class com.qq.e.comm.net.pi.**{*;}
-keep class com.qq.e.comm.net.services.**{*;}
-keep class com.qq.e.comm.net.uitl.**{*;}
-keep class com.jyad.** {*;}
-keep class com.ac.** {*;}
-keep class com.di.disdk.** {*;}
-keep class com.report.** {*;}
-keep class com.upay.sdk_gdt_ad.adApi.**{*;}
-keep class com.upay.sdk_gdt_ad.UpayAdApp
-keep class com.upay.sdk_gdt_ad.inf.**{*;}
-keep class com.upay.sdk_gdt_ad.core.GDTVideoAD
-keep class com.upay.sdk_gdt_ad.Utils.**{*;
}
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
void onClick*(...);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-libraryjars /libs/ao.jar
-libraryjars /libs/GDTUnionSDK.4.10.548.min.jar