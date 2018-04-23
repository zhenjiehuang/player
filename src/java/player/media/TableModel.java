package player.media;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import player.media.ListMedia.Media;

@SuppressWarnings("unchecked")
public class TableModel extends DefaultTableModel {

	private static final long serialVersionUID = 737219002971116058L;

	@Override
	public Object getValueAt(int row, int column) {
		Media t = (Media) dataVector.elementAt(row);
		return t.getName();
	}

	public Media getData(int row) {
		return (Media) dataVector.elementAt(row);
	}

	public void addDate(Media media) {
		dataVector.addElement(media);
		fireTableDataChanged();
	}

	public void addDates(List<Media> medias) {
		dataVector.addAll(medias);
		fireTableDataChanged();
	}

	public void addMedia(String key) {
		ListMedia media = ListMediaProperty.getList(key);
		dataVector.clear();
		List<Media> medias = media.getMedias();
		dataVector.addAll(medias);
		fireTableDataChanged();
	}
}
