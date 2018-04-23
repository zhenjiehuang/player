package player.media;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import player.media.ListMedia.Media;

/**
 * 播放列表
 * 
 * @author Administrator
 *
 */
public class ListMediaProperty {

	private static final String PATH = "E://player/";

	private static TreeMap<String, ListMedia> map;

	static {
		map = new TreeMap<String, ListMedia>();
		try {
			Files.lines(Paths.get(PATH + "list")).forEach(line -> {
				map.put(line, null);
			});
			if (!map.isEmpty()) {
				String key = map.firstKey();
				map.put(key, getMedia(key));
			}

			new Thread(new Runnable() {
				@Override
				public void run() {
					for (Entry<String, ListMedia> entry : map.entrySet()) {
						if (entry.getValue() == null) {
							entry.setValue(getMedia(entry.getKey()));
						}
					}
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ListMedia getMedia(String key) {
		try {
			ListMedia media = new ListMedia();
			List<Media> medias = new ArrayList<Media>();
			Files.lines(Paths.get(PATH + key)).forEach(line -> {
				Media m = new Media();
				String[] infos = line.split(",");
				m.setName(infos[0].replaceAll("\"", ","));
				m.setPath(infos[1]);
				if (infos.length > 2 && infos[2] != null && infos[2].trim().length() != 0) {
					m.setLocation(Integer.valueOf(infos[2]));
				}
				if (infos.length > 3 && infos[3] != null && infos[3].trim().length() != 0) {
					m.setDelay(Integer.valueOf(infos[3]));
				}
				medias.add(m);
			});

			media.setMedias(medias);

			return media;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Set<String> getListDir() {
		return map.keySet();
	}

	public static ListMedia getList(String dir) {
		return map.get(dir);
	}

	public static void delete(String dir, String path) {
		ListMedia listMedia = map.get(dir);
		List<Media> medias = listMedia.getMedias();
		Iterator<Media> it = medias.iterator();
		while (it.hasNext()) {
			Media m = it.next();
			if (m.getPath().equals(path)) {
				it.remove();
				break;
			}
		}
		writeFile(dir, listMedia);
	}

	public static void setList(ListMedia list) {
		String key = list.getName();
		ListMedia old = map.get(key);
		try {
			writeFile(key, list);
			map.put(key, list);
		} catch (Exception e) {
			if (old != null) {
				writeFile(key, old);
			}
			e.printStackTrace();
		}
	}

	private static void writeFile(String dir, ListMedia listMedia) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(PATH + dir)));
			for (Media m : listMedia.getMedias()) {
				bw.write(m.getName());
				bw.write(",");
				bw.write(m.getPath());
				bw.write(",");
				bw.write(m.getLocation());
				bw.write(",");
				bw.write(m.getDelay());
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(ListMediaProperty.getListDir());
		ListMediaProperty.getList("a");

		System.out.println();
	}

}
