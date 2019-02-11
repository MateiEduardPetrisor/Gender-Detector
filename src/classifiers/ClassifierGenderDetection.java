package classifiers;

import static org.bytedeco.javacpp.opencv_dnn.blobFromImage;
import static org.bytedeco.javacpp.opencv_dnn.readNetFromCaffe;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import java.io.File;
import java.io.IOException;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_dnn.Net;
import org.bytedeco.javacpp.indexer.Indexer;
import utils.Gender;

public class ClassifierGenderDetection {
	private Net neuralNetwork;
	private String prototxtDataFilePath;
	private String caffemodelDataFilePath;

	public ClassifierGenderDetection(String prototxtDataFilePath, String caffemodelDataFilePath) throws IOException {
		super();
		this.prototxtDataFilePath = prototxtDataFilePath;
		this.caffemodelDataFilePath = caffemodelDataFilePath;
		File protoTxtFile = new File(this.prototxtDataFilePath);
		File caffeModelFile = new File(this.caffemodelDataFilePath);
		if (protoTxtFile.exists() && caffeModelFile.exists()) {
			this.neuralNetwork = readNetFromCaffe(protoTxtFile.getAbsolutePath(), caffeModelFile.getAbsolutePath());
		} else {
			throw new IOException("Model Data Not Found!");
		}
	}

	public Gender predictGender(Mat image) {
		Mat img = image.clone();
		resize(img, img, new Size(227, 227));
		Mat inputBlob = blobFromImage(img);
		this.neuralNetwork.setInput(inputBlob, "data", 1.0, null);
		Mat prob = this.neuralNetwork.forward("prob");
		Indexer indexer = prob.createIndexer();
		if (indexer.getDouble(0, 0) > indexer.getDouble(0, 1)) {
			return Gender.MALE;
		} else {
			return Gender.FEMALE;
		}
	}
}