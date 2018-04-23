package player.view.action.mediaplayer;

import static player.view.action.Resource.resource;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import player.Application;
import player.view.action.StandardAction;

public class MediaOpenAction extends StandardAction {

	private static final long serialVersionUID = 4778923166794051440L;

	private Component parent;

	private JFileChooser fileChooser = new JFileChooser();

	public MediaOpenAction(Component parent) {
		super(resource("menu.media.item.openFile"));
		this.parent = parent;
		fileChooser.setMultiSelectionEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(parent)) {
			File[] files = fileChooser.getSelectedFiles();
			File file = fileChooser.getSelectedFile();
			String mrl = file.getAbsolutePath();
			Application app = Application.application();
			app.mediaPlayerComponent().getMediaPlayer().playMedia(mrl);
			app.listMediaPane().addFile(files);
		}
	}
}
