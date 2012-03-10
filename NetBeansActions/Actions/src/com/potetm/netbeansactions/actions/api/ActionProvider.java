/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.potetm.netbeansactions.actions.api;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author potetm
 */
public interface ActionProvider {

	public void addActionListener(ActionListener actionListener);

	public void removeActionListener(ActionListener actionListener);

	public void fireActionPerformed(ActionEvent actionEvent);

	public List<ActionListener> getActionListeners();
}
