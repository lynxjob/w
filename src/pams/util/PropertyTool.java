package pams.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyTool {

	public static String readValue(String relativePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					relativePath));
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
		}

		return "error";
	}

	// 先查找properties中有无key对应的值，若有改之；若无，添加为一个新的属性
	public static void writeValue(String relativePath, String key, String value) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					relativePath));
			props.load(in);
			FileOutputStream out = new FileOutputStream(relativePath);
			props.setProperty(key, value);
			props.store(out, key + " is modified ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ClassLoader getClassLoader() {
		return PropertyTool.class.getClassLoader();
	}

	public InputStream getInputStream(String relativePath) {
		return getClassLoader().getResourceAsStream(relativePath);
	}
}
