package edu.db.tool.deid.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Duy Bui
 */
public class FileUtil {
        public static String NL = System.getProperty("line.separator");
	
	public static String getAbsolutePath(String relativePath){
		return (new File(relativePath)).getAbsolutePath();
	}

	
	public static Set<String> readFileBylineSet(String fileName) {

		Set<String> lines = new HashSet<String>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.trim().equals(""))
					continue;
							

				lines.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return lines;
	}
	
	public static List<String> readFileByline(String fileName) {

		List<String> lines = new ArrayList<String>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.trim().equals(""))
					continue;

				lines.add(line);
			}
		} catch (Exception e) {
			File f=new File(fileName);                                
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return lines;
	}


	public static String readFile(String fileName) {

		StringBuffer buf = new StringBuffer();
//		Debug.print(fileName);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buf.append(line + NL);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return buf.toString();
	}
	
	public static StringBuffer readFileBuf(String fileName) {

		StringBuffer buf = new StringBuffer();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buf.append(line + NL);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return buf;
	}

	public static void append(String content, String filePath) {
		// System.out.println(filePath);

		BufferedWriter writer = null;
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}

			writer = new BufferedWriter(new FileWriter(f, true));
			writer.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	public static void writeFileUTF8(String content, String filePath, boolean isAppend) {
		// System.out.println(filePath);

		BufferedWriter writer = null;
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}

			writer = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(filePath), "UTF-8"));
			
			writer.write(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {

					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
	
	public static boolean IsExist(String filePath){
		return (new File(filePath)).exists();
	}
	
	public static void writeFile(String content, String filePath, boolean isAppend) {
		// System.out.println(filePath);

		BufferedWriter writer = null;
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}

			writer = new BufferedWriter(new FileWriter(filePath, isAppend));
			
			writer.write(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {

					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
	
	public static void deleteFolder(String folderPath) {
		
		File folder=new File(folderPath);
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f.getAbsolutePath());
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	public static void createFolderIfNotExists(String folderPath) {
		File outputDir = new File(folderPath);
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
	}
	
	
	public  static void copyfile(String srFile, String dtFile){
	    try{
	      File f1 = new File(srFile);
	      File f2 = new File(dtFile);
	      InputStream in = new FileInputStream(f1);

	      //For Overwrite the file.
	      OutputStream out = new FileOutputStream(f2);

	      byte[] buf = new byte[1024];
	      int len;
	      while ((len = in.read(buf)) > 0){
	        out.write(buf, 0, len);
	      }
	      in.close();
	      out.close();

	    }
	    catch(Exception ex){
	      ex.printStackTrace();
	    }
	    
	  }
	
	public static void prepareDir(String path){
		File dir=new File(path);
		if(!dir.exists())
			dir.mkdirs();
	}
	
	public static void cleanFile(String path){
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isExist(String filePath){
		return (new File(filePath)).exists();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
