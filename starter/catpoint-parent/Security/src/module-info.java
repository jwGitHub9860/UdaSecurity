module Security {
    // Exports "com.udacity.catpoint.data" package to Allow OTHER Modules to Access Java Files INSIDE "data" Package in "Security" Child Module
    exports com.udacity.catpoint.data; // "CatpointGui.java", "ControlPanel.java", "DisplayPanel.java", "ImagePanel.java", and "SensorPanel.java" Use "com.udacity.catpoint.data" Package

    // Exports "com.udacity.catpoint.service" package to Allow OTHER Modules to Access Java Files INSIDE "service" Package in "Security" Child Module
    exports com.udacity.catpoint.service; // "CatpointGui.java", "ControlPanel.java", "DisplayPanel.java", "ImagePanel.java", and "SensorPanel.java" Use "com.udacity.catpoint.service" Package

    // Requires ALL Files in "Security" Child Module TO HAVE ACCESS TO "com.google.common" Package
    requires com.google.common;
}