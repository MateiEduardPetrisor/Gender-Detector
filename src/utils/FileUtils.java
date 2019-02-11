package utils;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import java.io.File;
import java.io.IOException;
import org.bytedeco.javacpp.opencv_core.Mat;

public class FileUtils {

	public static File[] getPictures(String picturesDirectoryPath) throws IOException {
		File directory = new File(picturesDirectoryPath);
		File[] pictureFiles;
		if (directory.exists() && directory.isDirectory()) {
			pictureFiles = directory.listFiles();
			return pictureFiles;
		} else {
			throw new IOException();
		}
	}

	public static Mat readImage(String pictureFilePath) throws IOException {
		File imageFile = new File(pictureFilePath);
		if (imageFile.exists()) {
			Mat matImage = imread(imageFile.getAbsolutePath(), CV_BGR2GRAY);
			return matImage;
		} else {
			throw new IOException();
		}
	}
}