package me.leo.skywars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CopyDirectory {

	public static void main(String[] args) throws IOException {
		CopyDirectory cd = new CopyDirectory();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the source directory or file name : ");
		String source = in.readLine();
		File src = new File(source);
		System.out.println("Enter the destination directory or file name : ");
		String destination = in.readLine();
		File dst = new File(destination);
		cd.copyDirectory(src, dst);
	}

	public void copyDirectory(File srcPath, File dstPath) throws IOException {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdir();
			}
			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i]));
			}
		}

		else {
			if (!srcPath.exists()) {
				System.out.println("File or directory does not exist.");
				System.exit(0);
			} else {
				InputStream in = new FileInputStream(srcPath);
				OutputStream out = new FileOutputStream(dstPath);
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
		//System.out.println("Directory copied.");
	}

}