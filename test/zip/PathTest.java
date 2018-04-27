package zip;

import java.io.File;
import java.util.Arrays;

public class PathTest {
	public static void main(String[] args) {
		File f = new File("src//resourses");
		System.out.println(f.exists());
		System.out.println(Arrays.asList(f.list()));
	}
}
