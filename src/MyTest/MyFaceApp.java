package MyTest;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.facepp.result.FaceppResult;

public class MyFaceApp {

	public static void main(String[] args) {
		
		HttpRequests httpRequests = new HttpRequests("2f0c3fd5708b4b225968281727e754da", "tQoz3GSQNXuy69hz10fuw2FxEQexxhD1");
		
		FaceppResult result = null;
		
		System.out.println("FacePlusPlus API Test:");
		
		//detection/detect
		try {
			result = httpRequests.detectionDetect(new PostParameters().setUrl("http://i.imgur.com/9Bi9zR9.jpg?1"));
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);

	}

}
