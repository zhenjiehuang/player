package player.config;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import player.model.ListMedia;

/**
 * 播放列表
 * 
 * @author Administrator
 *
 */
public class ListMediaProperty {

	private static Map<String, ListMedia> map;

	static {
		map = new TreeMap<String, ListMedia>();
		map.put("list1", new ListMedia());
		map.put("list2", new ListMedia());
	}

	public static Set<String> getListDir() {
		return map.keySet();
	}

	public static ListMedia getList(String dir) {
		return map.get(dir);
	}

}
