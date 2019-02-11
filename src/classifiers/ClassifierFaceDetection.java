package classifiers;

import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import java.io.File;
import java.io.IOException;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

public class ClassifierFaceDetection {
	private CascadeClassifier cascadeClassifier;
	private String modelXmlFilePath;

	public ClassifierFaceDetection(String modelXmlFilePath) throws IOException {
		super();
		this.modelXmlFilePath = modelXmlFilePath;
		File xmlFile = new File(this.modelXmlFilePath);
		if (xmlFile.exists()) {
			this.cascadeClassifier = new CascadeClassifier(xmlFile.getAbsolutePath());
		} else {
			throw new IOException("Model Data Not Found");
		}
	}

	public boolean detectFace(Mat image) throws IOException {
		Mat img = image.clone();
		cvtColor(img, img, CV_BGR2GRAY);
		RectVector rectVector = new RectVector();
		this.cascadeClassifier.detectMultiScale(img, rectVector);
		if (rectVector.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
}