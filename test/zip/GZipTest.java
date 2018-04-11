package zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipTest {
	public static void main(String[] args) {
		GZIPOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new GZIPOutputStream(new FileOutputStream("E://1.gz"));
			in = new FileInputStream("E://1");

			byte[] bs = new byte[1024];
			while (in.read(bs) != -1) {
				out.write(bs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
			} catch (Exception e2) {
			}
		}
	}
}
