package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);

	}

}