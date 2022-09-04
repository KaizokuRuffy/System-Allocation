package Service.Util;

import java.io.File;

public class FileUtil {
	
	public static boolean deleteDir(File dir) {
		
		File[] list = dir.listFiles();
		if(list != null)
			for(File file : list)
				deleteDir(file);
		
		return dir.delete();
	}

}
