package cjh.cleverc.services.fileupload;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	// 리눅스 기준으로 파일 경로를 작성 ( 루트 경로인 /으로 시작한다. )
	// 윈도우라면 workspace의 드라이브를 파악하여 JVM이 알아서 처리해준다.
	// 따라서 workspace가 C드라이브에 있다면 C드라이브에 upload 폴더를 생성해 놓아야 한다.
	private static final String SAVE_PATH = "C:\\Users\\chl\\Desktop\\springworkspace\\spring_mvc\\src\\main\\webapp\\resources\\upload";
	//private static final String PREFIX_URL = "C:\\Users\\chl\\Desktop\\springworkspace\\spring_mvc\\src\\main\\webapp\\resources\\upload\\";
	
	public Map<String,String> restore(MultipartFile[] multipartFile) {
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0; i<multipartFile.length;i++) {
			try {
				//파일정보
				String originFilename = multipartFile[i].getOriginalFilename();
				//확장자
				String extName
					= originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
				// 서버에서 저장 할 파일 이름
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
				Calendar cal = Calendar.getInstance();
				String saveFileName = i+"upload"+sdf.format(cal.getTime())+extName;
				//업로드
				byte[] data = multipartFile[i].getBytes();
				FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" +saveFileName);
				fos.write(data);
				fos.close();
				System.out.println("업로드성공"+i);
				map.put("msg", "good한글");
			}
			catch (Exception e) {
				System.out.println("errrrror"+i);
				map.put("msg", "bed한글");
			}
		}
		
		return map;
		
	}	//map.put();
	//return map; 빈보다 맵이 가볍다.
	//Long size = multipartFile.getSize();
	/*
	 * System.out.println("originFilename : " + originFilename);
	 * System.out.println("extensionName : " + extName);
	 * System.out.println("size : " + size); System.out.println("saveFileName : " +
	 * saveFileName);
	 */
	
	// 현재 시간을 기준으로 파일 이름 생성
	/*
	 * private String genSaveFileName(String extName) { String fileName = "";
	 * 
	 * Calendar calendar = Calendar.getInstance(); fileName +=
	 * calendar.get(Calendar.YEAR); fileName += calendar.get(Calendar.MONTH);
	 * fileName += calendar.get(Calendar.DATE); fileName +=
	 * calendar.get(Calendar.HOUR); fileName += calendar.get(Calendar.MINUTE);
	 * fileName += calendar.get(Calendar.SECOND); fileName +=
	 * calendar.get(Calendar.MILLISECOND); fileName += extName;
	 * 
	 * return fileName; }
	 */
	
	//writeFile(multipartFile, saveFileName);
	// 파일을 실제로 write 하는 메서드
	/*
	 * private boolean writeFile(MultipartFile multipartFile, String saveFileName)
	 * throws Exception{ boolean result = false;
	 * 
	 * byte[] data = multipartFile.getBytes(); FileOutputStream fos = new
	 * FileOutputStream(SAVE_PATH + "/" + saveFileName); fos.write(data);
	 * fos.close();
	 * 
	 * return result; }
	 */
}