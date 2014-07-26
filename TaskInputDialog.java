

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class TaskInputDialog extends JDialog implements ActionListener{
	
	private static JTextField buyPrice;
	private static JTextField sellPrice;
	private static JTextField itemName;
	private static JTextField itemAmt;
	private final JPanel optionsPanel;
	
	private final JButton okButton;
	private final JPanel buttonPanel;
	private final JButton cancelButton;
	
	private boolean cancel;
	
	private TaskInputDialog(final String title, final JFrame parent){
		super(parent, title, true);
		setLayout(new BorderLayout());
		
		cancel = false;
		
		buyPrice = new JTextField(12);
		
		sellPrice = new JTextField(12);
		
		itemName = new JTextField(12);
		
		itemAmt = new JTextField(12);
		
		optionsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		optionsPanel.add(new JLabel("Item"));
		optionsPanel.add(itemName);
		optionsPanel.add(new JLabel("Amount"));
		optionsPanel.add(itemAmt);
		optionsPanel.add(new JLabel("Buy Price"));
		optionsPanel.add(buyPrice);
		optionsPanel.add(new JLabel("Sell Price"));
		optionsPanel.add(sellPrice);


		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		add(optionsPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(final ActionEvent e){
		final Object source = e.getSource();
		if(source.equals(okButton) || source.equals(cancelButton)){
			cancel = source.equals(cancelButton);
			dispose();
		}
	}
	
	private boolean valid(){
		try{
			Double.parseDouble(buyPrice.getText().trim());
			Double.parseDouble(sellPrice.getText().trim());
			Double.parseDouble(itemAmt.getText().trim());
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	public static Task showInputDialog(final String title, final JFrame parent){
		final TaskInputDialog dialog = new TaskInputDialog(title, parent);
		dialog.pack();
		dialog.setVisible(true);
		while(dialog.isVisible());
		if(dialog.cancel)
			return null;
		if(!dialog.valid()){
			JOptionPane.showMessageDialog(parent, "Invalid data input", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		final int amt = Integer.parseInt(itemAmt.getText());
		final String name = itemName.getText();
		final double bPrice = Double.parseDouble(buyPrice.getText());
		final double sPrice = Double.parseDouble(sellPrice.getText());
		return new Task(amt, name, bPrice, sPrice);		
	}

}
