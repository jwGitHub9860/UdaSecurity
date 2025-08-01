module Image {
    // Requires ALL Files in "Image" Child Module TO HAVE ACCESS TO "java.awt.image" Package
    requires java.awt.image;

    // Requires ALL Files in "Image" Child Module TO HAVE ACCESS TO "software.amazon.awssdk" Package
    requires software.amazon.awssdk;
}