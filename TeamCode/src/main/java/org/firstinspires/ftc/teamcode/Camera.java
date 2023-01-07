package org.firstinspires.ftc.teamcode;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Scalar;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;

public class Camera {
//    public static void main(String[] args) {
//        // Load the OpenCV library
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//        // Load the input image
//        Mat image = Imgcodecs.imread("input.jpg");
//
//        // Convert the image to the HSV color space
//        Mat hsvImage = new Mat();
//        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);
//
//        // Define the range of yellow colors in the HSV color space
//        Scalar lowerBound = new Scalar(20, 100, 100);
//        Scalar upperBound = new Scalar(30, 255, 255);
//
//        // Create a mask that only contains the yellow pixels
//        Mat mask = new Mat();
//        Core.inRange(hsvImage, lowerBound, upperBound, mask);
//
//        // Use the mask to identify the yellow pixels in the original image
//        Mat yellowImage = new Mat();
//        image.copyTo(yellowImage, mask);
//
//        // Save the output image
//        Imgcodecs.imwrite("output.jpg", yellowImage);
//    }
}
