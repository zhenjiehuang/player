package player.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4923135687538047828L;

	private JButton addFile = new JButton("添加文件");

	private JFileChooser fileChooser = new JFileChooser();

	private JPanel mediaPlayerComponent = new JPanel();
	// private EmbeddedMediaPlayerComponent mediaPlayerComponent = new
	// EmbeddedMediaPlayerComponent();

	public MainFrame() {
		init();
		initListener();
		setTitle("播放器");
		setSize(900, 600);
		setLocationRelativeTo(null);
	}

	private void init() {
		JSplitPane pane = new JSplitPane();
		pane.setSize(600, 0);
		pane.setDividerSize(5);
		pane.setDividerLocation(0.2D);

		pane.setLeftComponent(new ListMediaPane());

		JPanel center = new JPanel(new BorderLayout());
		center.add(mediaPlayerComponent, BorderLayout.CENTER);
		center.add(new SouthPane(), BorderLayout.SOUTH);

		pane.setRightComponent(center);

		add(pane);
	}

	private void initListener() {
		addFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(MainFrame.this)) {
					File file = fileChooser.getSelectedFile();
					String mrl = file.getAbsolutePath();
					// mediaPlayerComponent.getMediaPlayer().playMedia(mrl);
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
