package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;

import copier_coller.Utilities;

public class SelectListener implements ActionListener {
	
JEditorPane jep;
	
	public SelectListener(JEditorPane jep){
		this.jep=jep;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Utilities.setAsText(jep.getText());
	}

}
