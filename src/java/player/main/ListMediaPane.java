package player.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import player.config.ListMediaProperty;
import player.model.ListMedia;

public class ListMediaPane extends JPanel {

	private static final long serialVersionUID = 7002234837820448544L;

	private JComboBox<String> dir = new JComboBox<String>();

	private DefaultTableModel model = new DefaultTableModel();

	private JTable table = new JTable(model) {
		private static final long serialVersionUID = -6105659655539810869L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return true;
		}

		@Override
		public TableCellEditor getCellEditor(int row, int column) {
			if (column == 0) {

				JTextField text = new JTextField();
				text.setEditable(false);
				return new DefaultCellEditor(text) {
					private static final long serialVersionUID = -9022132820822952850L;

					@Override
					public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
							int row, int column) {
						return super.getTableCellEditorComponent(table, value, isSelected, row, column);
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
						return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					}
				};
			}
			return new Cell();
		};

		@Override
		public TableCellEditor getCellEditor() {
			return cellEditor;
		};

	};

	public ListMediaPane() {
		init();
		initListener();
	}

	private void init() {
		setLayout(new BorderLayout());

		add(centerPane(), BorderLayout.CENTER);
		add(northPane(), BorderLayout.NORTH);
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

		return pane;
	}

	private void initListener() {
		dir.addItemListener(new ItemListener() {

			int i = 1;

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String item = (String) e.getItem();
					ListMedia media = ListMediaProperty.getList(item);
					model.addRow(new String[] { i++ + "" });
					model.fireTableDataChanged();

					media.getMedias();
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (MouseEvent.BUTTON3 == e.getButton()) {
					int row = table.rowAtPoint(e.getPoint());
					if (row == -1) {
						return;
					}
					// 将表格所选项设为当前右键点击的行
					table.setRowSelectionInterval(row, row);
					System.out.println(model.getValueAt(row, 0));
				}
			}
		});
	}

	class Cell extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {

		public Cell() {
			button = new JButton("×");
			button.addActionListener(this);
		}

		private static final long serialVersionUID = -3974374374725087781L;
		private JButton button = null;

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JPanel pane = new JPanel(new BorderLayout());
			pane.add(button, BorderLayout.EAST);
			return button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			JPanel pane = new JPanel(new BorderLayout());
			pane.add(button, BorderLayout.EAST);
			return button;
		}
	}

}
