package app;

import static utils.FileUtils.getPictures;
import static utils.FileUtils.readImage;
import static utils.StringConstants.caffeModelPath;
import static utils.StringConstants.faceCascadeXmlPath;
import static utils.StringConstants.picturesDirectoryPath;
import static utils.StringConstants.protoTxtPath;
import java.io.File;
import java.io.IOException;
import org.bytedeco.javacpp.opencv_core.Mat;
import classifiers.ClassifierFaceDetection;
import classifiers.ClassifierGenderDetection;
import utils.Gender;

public class MainClass {

	private static ClassifierFaceDetection faceDetector;
	private static ClassifierGenderDetection genderDetector;

	static {
		try {
			faceDetector = new ClassifierFaceDetection(faceCascadeXmlPath);
			genderDetector = new ClassifierGenderDetection(protoTxtPath, caffeModelPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			File[] pictures = getPictures(picturesDirectoryPath);
			for (int i = 0; i < pictures.length; i++) {
				Mat img = readImage(pictures[i].getAbsolutePath());
				boolean isFaceDetected = faceDetector.detectFace(img);
				if (isFaceDetected) {
					Gender gender = genderDetector.predictGender(img);
					if (gender.equals(Gender.FEMALE)) {
						System.out.println("Picture " + pictures[i].getName() + " Is " + gender);
					} else {
						System.out.println("Picture " + pictures[i].getName() + " Is " + gender);
					}
				} else {
					System.out.println("Picture " + pictures[i].getName() + " Does Not Contain A Face");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}