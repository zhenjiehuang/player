package player.view.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import player.media.ListMedia.Media;
import player.media.ListMediaProperty;
import player.media.TableModel;

public class ListMediaPane extends JPanel {

	private static final long serialVersionUID = 7002234837820448544L;

	private JPopupMenu menu;

	private JMenuItem file = new JMenuItem("删除选择文件");

	private JMenuItem location = new JMenuItem("打开文件位置");

	private JComboBox<String> dir = new JComboBox<String>();

	private TableModel model = new TableModel();

	private JTable table = new JTable(model) {
		private static final long serialVersionUID = -6105659655539810869L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return column != 0;
		}

		@Override
		public TableCellEditor getCellEditor(int row, int column) {
			if (column == 0) {
				JTextField text = new JTextField();
				return new DefaultCellEditor(text) {
					private static final long serialVersionUID = -9022132820822952850L;

					@Override
					public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
							int row, int column) {
						if (column == 1) {
							return new JButton();
						} else {
							return super.getTableCellEditorComponent(table, value, isSelected, row, column);
						}
					}
				};
			}
			return new Cell();
		}

		@Override
		public TableCellRenderer getCellRenderer(int row, int column) {
			if (column == 0) {
				return new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 8314457254787788075L;

					@Override
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						if (column == 1) {
							return new JButton();
						} else {
							setBackground(new Color(220, 220, 220));
							return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
						}
					}
				};
			}
			return new Cell();
		};

		@Override
		public TableCellEditor getCellEditor() {
			return cellEditor;
		}

	};

	public ListMediaPane() {
		init();
		initListener();
	}

	private void init() {
		setLayout(new BorderLayout());

		add(northPane(), BorderLayout.NORTH);
		add(centerPane(), BorderLayout.CENTER);
	}

	private JPanel northPane() {
		JPanel pane = new JPanel(new BorderLayout());
		// pane.add(new JLabel(), BorderLayout.WEST);
		pane.add(dir, BorderLayout.CENTER);

		Set<String> dirs = ListMediaProperty.getListDir();

		for (String d : dirs) {
			dir.addItem(d);
		}

		return pane;
	}

	private JScrollPane centerPane() {
		table.setTableHeader(null);

		JScrollPane pane = new JScrollPane(table);

		model.addColumn("");
		model.addColumn("");

		if (dir.getItemCount() > 0) {
			model.addMedia(dir.getItemAt(0));
		}

		table.getColumnModel().getColumn(1).setPreferredWidth(20);
		TableColumn colunm = table.getColumnModel().getColumn(1);
		colunm.setMaxWidth(25);
		colunm.setMinWidth(25);
		table.setRowHeight(25);
		return pane;
	}

	private void initListener() {
		dir.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String item = (String) e.getItem();
					model.addMedia(item);
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (MouseEvent.BUTTON1 == e.getButton() && e.getClickCount() > 1) {
					System.out.println();
					// TODO
				} else if (MouseEvent.BUTTON3 == e.getButton()) {
					int column = table.columnAtPoint(e.getPoint());
					if (column == 1) {
						return;
					}
					int row = table.rowAtPoint(e.getPoint());
					if (row == -1) {
						return;
					}
					// 将表格所选项设为当前右键点击的行
					table.setRowSelectionInterval(row, row);
					// System.out.println(model.getValueAt(row, 0));
					if (menu != null) {
						menu.setVisible(false);
					}
					initMenu(e.getPoint());
				}
			}
		});

		file.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Media media = model.getData(row);
				File file = new File(media.getPath());
				file.deleteOnExit();
				model.removeRow(row);
				model.fireTableDataChanged();
				ListMediaProperty.delete(dir.getSelectedItem().toString(), media.getPath());
			}
		});
	}

	private void initMenu(Point point) {
		menu = new JPopupMenu();
		menu.add(file);
		menu.add(location);
		// menu.add
		menu.show(table, point.x, point.y);
	}

	class Cell extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
		private static final long serialVersionUID = -3974374374725087781L;
		private JButton button = null;
		private int row;

		public Cell() {
			button = new JButton("×");
			button.addActionListener(this);
			button.setBorder(new EmptyBorder(2, 2, 2, 2));
			button.setPreferredSize(new Dimension(10, 10));
			button.setBackground(new Color(220, 220, 220));
			// button.setForeground(new Color(220, 220, 220));
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.row = row;
			return button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			model.removeRow(row);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			return button;
		}
	}

}
