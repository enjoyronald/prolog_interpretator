package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;



public class DeleteListener implements ActionListener {
	JEditorPane jep;
	
	public DeleteListener(JEditorPane jep){
		this.jep=jep;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jep.replaceSelection("");
	}

}
