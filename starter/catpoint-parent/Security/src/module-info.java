module Security {
    // Exports "com.udacity.catpoint.service" package to Allow OTHER Modules to Access Java Files INSIDE "Security" Child Module
    exports com.udacity.catpoint.service;

    // Requires ALL Files in "Security" Child Module TO HAVE ACCESS TO "com.google.common" Package
    requires com.google.common;
}