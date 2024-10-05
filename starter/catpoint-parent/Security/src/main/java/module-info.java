module com.udacity.catpoint.security {
    requires org.slf4j;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.services.rekognition;
    requires java.desktop;
    requires software.amazon.awssdk.regions;
    requires com.udacity.catpoint.image;
    requires miglayout.swing;
    requires java.prefs;
    requires com.google.gson;
    requires guava;

    exports com.udacity.catpoint.security.data to com.google.common;
}