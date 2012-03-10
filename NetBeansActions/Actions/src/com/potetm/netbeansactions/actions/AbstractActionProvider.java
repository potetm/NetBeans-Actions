/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.potetm.netbeansactions.actions;

import com.potetm.netbeansactions.actions.api.ActionProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;

/**
 *
 * @author potetm
 */
public abstract class AbstractActionProvider extends AbstractAction implements ActionProvider {

	protected List<ActionListener> actionListeners = new ArrayList<ActionListener>();

	public AbstractActionProvider() {
	}

	public AbstractActionProvider(String name) {
		super(name);
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}

	@Override
	public void removeActionListener(ActionListener actionListener) {
		actionListeners.remove(actionListener);
	}

	@Override
	public void fireActionPerformed(ActionEvent actionEvent) {
		for (ActionListener actionListener : actionListeners) {
			actionListener.actionPerformed(new ActionEvent(this, actionEvent.getID(), actionEvent.getActionCommand()));
		}
	}

	@Override
	public List<ActionListener> getActionListeners() {
		return actionListeners;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		fireActionPerformed(ae);
	}
}
