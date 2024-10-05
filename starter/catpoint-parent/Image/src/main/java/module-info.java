module com.udacity.catpoint.image {
//    requires org.slf4j;
        requires software.amazon.awssdk.core;
        requires software.amazon.awssdk.auth;
        requires software.amazon.awssdk.services.rekognition;
        requires java.desktop;
        requires software.amazon.awssdk.regions;
        requires org.slf4j;
        exports com.udacity.catpoint.image;
}