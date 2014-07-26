import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class FlipLog extends JFrame implements ActionListener {

	public static JList<String> list;
	public static DefaultListModel<String> listModel;
	public final JScrollPane listScroll;

	private final JButton addButton;
	private final JButton removeButton;
	private final JButton modifyButton;
	private final JButton settingsButton;
	private final JButton saveButton;
	private final JButton loadButton;
	private final JPanel buttonPanel;
	
	JList dropList;
    JButton addDrop;
    JButton modifyDrop;
    JButton removeDrop;
    JButton displayPrice;
    JButton saveList;
    JButton loadList;

	private final JTabbedPane pane;

	public FlipLog() throws NumberFormatException, IOException {
		super("Flipping Log - Created by Seismic");

		/*
		BufferedReader bf = null;
		File file = new File("log.txt");
		if (file.exists()) {
			bf = new BufferedReader(new FileReader("settings.txt"));
			Constants.DEFAULT_MASTER = Integer.parseInt(bf.readLine());
			Constants.DEFAULT_LEVEL = Integer.parseInt(bf.readLine());
			bf.close();
		}*/
		setLayout(new BorderLayout());	
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>();
		list.setModel(listModel);
		listScroll = new JScrollPane(list);
		
		pane = new JTabbedPane();
		
		addButton = new JButton("Add A Flip");
		addButton.addActionListener(this);
		
		removeButton = new JButton("Remove A Flip");
		removeButton.addActionListener(this);
		
		modifyButton = new JButton("Modify A Flip");
		modifyButton.addActionListener(this);
		modifyButton.setEnabled(false);
		
		settingsButton = new JButton("Change Settings");
		settingsButton.addActionListener(this);
		settingsButton.setEnabled(false);
		
		saveButton = new JButton("Save Flips");
		saveButton.addActionListener(this);
		
		loadButton = new JButton("Load Flips");
		loadButton.addActionListener(this);
		
		buttonPanel = new JPanel(new GridLayout(3, 3, 5, 5));
		buttonPanel.setBorder(new TitledBorder("Options"));
		buttonPanel.add(addButton);
		buttonPanel.add(settingsButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(modifyButton);
		buttonPanel.add(loadButton);
		
		JPanel taskPanel = new JPanel(new BorderLayout());
		taskPanel.add(listScroll, BorderLayout.CENTER);
		taskPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		pane.addTab("Flip List", taskPanel);
		
		JPanel dropPanel = new JPanel(new BorderLayout());
		
		dropList = new JList<String>();
		
		
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(dropList);
		dropPanel.add(listPanel, BorderLayout.CENTER);
		
		JPanel blankPanel = new JPanel();
		JPanel blankPanel1 = new JPanel();
		JPanel blankPanel2 = new JPanel();


		add(pane, BorderLayout.CENTER);
		
	}

	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(addButton)) {
			final Task task = TaskInputDialog.showInputDialog("Add New Flip",
					this);
			if (task == null)
				return;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					listModel.addElement(task.toString());
					list.repaint();
				}
			});
		}
		if (source.equals(removeButton)) {
			listModel.removeElementAt(list.getSelectedIndex());
		}
		if (source.equals(modifyButton)) {
			final int index = list.getSelectedIndex();
			final Task task = TaskInputDialog.showInputDialog("Modify Flip",
					this);
			if (task == null)
				return;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					listModel.setElementAt(task.toString(), index);
					list.repaint();
				}
			});
		}
		if (source.equals(saveButton)) {
			try {
				saveToFile();
				JOptionPane.showMessageDialog(null, "Saved flips to file!",
						"Saved!", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (source.equals(loadButton)) {
			try {
				loadFile();
				JOptionPane.showMessageDialog(null, "Loaded tasks from file!",
						"Loaded!", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void saveToFile() throws IOException // Saves to the file
	{
		PrintWriter fileoutput;
		fileoutput = new PrintWriter(new FileWriter("log.txt"));
		int n = list.getModel().getSize();
		fileoutput.println("" + n);
		for (int i = 0; i < n; i++) {
			if (listModel.getElementAt(i) != null) {
				fileoutput.println(listModel.getElementAt(i));
			}
		}
		fileoutput.close();
	}

	public static void loadFile() throws IOException // method to read the file
														// and store them into
														// arrays
	{
		String line;
		BufferedReader bf;
		bf = new BufferedReader(new FileReader("log.txt"));
		int length = Integer.parseInt(bf.readLine());
		for (int i = 1; i < length + 1; i++) {
			listModel.addElement(bf.readLine());
			list.repaint();
		}
		bf.close(); // Closes the file
	}

	public static void main(String args[]) throws NumberFormatException,
			IOException {
		final FlipLog window = new FlipLog();
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setSize(700, 500);
		window.setVisible(true); // Looool
	}
}
