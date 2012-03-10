/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.potetm.netbeansactions.actions;

import com.potetm.netbeansactions.actions.api.ActionProvider;
import com.potetm.netbeansactions.actions.api.ActionTestDelegateProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 * This is a very bad unit test, because it relies on the external interface
 * com.potetm.netbeansactions.actions.api.ActionTestProvider to work. However,
 * that's how AbstractDelegateAction functions, so it's a necessary evil.
 *
 * @author potetm
 */
public class AbstractDelegateActionTest {

	private static final String ACTION_LISTENER_TO_STRING = "I am an ActionListener";
	private ActionProvider actionProvider;
	private ActionListener actionListener;

	@Before
	public void setUp() {
		actionProvider = Lookup.getDefault().lookup(ActionTestDelegateProviderImpl.class);
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
	 * Test of actionPerformed method, of class AbstractDelegateAction.
	 * UnsupportedOperationException will be thrown by actionListener.
	 */
	@Test
	public void testActionPerformed() {
		actionProvider.addActionListener(actionListener);
		ActionEvent actionEvent = new ActionEvent(this, 1234, "Do something!");
		ActionTestDelegate actionTest = new ActionTestDelegate();

		try {
			actionTest.actionPerformed(actionEvent);
		} catch (UnsupportedOperationException unsupportedOperationException) {
			assertEquals("I have no action", unsupportedOperationException.getMessage());
		}
	}

	/*
	 * Notice the naming of these two classes. AbstractDelegateAction will use
	 * Lookup to find the class named className + "Provider".
	 */
	public static class ActionTestDelegate extends AbstractDelegateAction {
	}

	@ServiceProvider(service = ActionTestDelegateProvider.class)
	public static class ActionTestDelegateProviderImpl extends AbstractActionProvider implements ActionTestDelegateProvider {
	}
}
