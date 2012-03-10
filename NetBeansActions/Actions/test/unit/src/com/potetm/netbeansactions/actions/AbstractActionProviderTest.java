/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.potetm.netbeansactions.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author potetm
 */
public class AbstractActionProviderTest {

	private static final String ACTION_LISTENER_TO_STRING = "I am an ActionListener";
	private ActionTestProvider abstractActionProvider;
	private ActionListener actionListener;

	@Before
	public void setUp() {
		abstractActionProvider = new ActionTestProvider();
		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				throw new UnsupportedOperationException("I have no action");
			}

			@Override
			public String toString() {
				return ACTION_LISTENER_TO_STRING;
			}
		};
	}

	/**
	 * Test of getActionListeners method, of class AbstractActionProvider.
	 */
	@Test
	public void testGetActionListeners() {
		List<ActionListener> actionListeners = abstractActionProvider.getActionListeners();
		assertEquals("The list of action listeners should be empty", 0, actionListeners.size());
	}

	/**
	 * Test of addActionListener method, of class AbstractActionProvider.
	 */
	@Test
	public void testAddActionListener() {
		abstractActionProvider.addActionListener(actionListener);
		List<ActionListener> actionListeners = abstractActionProvider.getActionListeners();
		assertEquals("There should be one ActionListener in the list", 1, actionListeners.size());
		assertEquals("What goes into the list must come out", ACTION_LISTENER_TO_STRING, actionListeners.get(0).toString());
	}

	/**
	 * Test of removeActionListener method, of class AbstractActionProvider.
	 */
	@Test
	public void testRemoveActionListener() {
		abstractActionProvider.addActionListener(actionListener);
		abstractActionProvider.removeActionListener(actionListener);
		List<ActionListener> actionListeners = abstractActionProvider.getActionListeners();
		assertEquals("There should be no listeners", 0, actionListeners.size());
	}

	/**
	 * Test of fireActionPerformed method, of class AbstractActionProvider.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testFireActionPerformed() {
		abstractActionProvider.addActionListener(actionListener);
		abstractActionProvider.fireActionPerformed(new ActionEvent(this, 12345, "Do something"));
	}

	/**
	 * Test of fireActionPerformed method, of class AbstractActionProvider.
	 */
	@Test(expected = NullPointerException.class)
	public void testFireActionPerformedNullActionEvent() {
		abstractActionProvider.addActionListener(actionListener);
		abstractActionProvider.fireActionPerformed(null);
	}

	/**
	 * Test of actionPerformed method, of class AbstractActionProvider.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testActionPerformed() {
		abstractActionProvider.addActionListener(actionListener);
		abstractActionProvider.actionPerformed(new ActionEvent(this, 12345, "Do something"));
	}

	/**
	 * Test of actionPerformed method, of class AbstractActionProvider.
	 */
	@Test(expected = NullPointerException.class)
	public void testActionPerformedNullActionEvent() {
		abstractActionProvider.addActionListener(actionListener);
		abstractActionProvider.actionPerformed(null);
	}

	public static class ActionTestProvider extends AbstractActionProvider {
	}
}
