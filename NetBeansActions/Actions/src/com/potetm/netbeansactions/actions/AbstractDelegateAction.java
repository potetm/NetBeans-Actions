/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.potetm.netbeansactions.actions;

import com.potetm.netbeansactions.actions.api.ActionProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author potetm
 */
public abstract class AbstractDelegateAction extends AbstractAction implements ActionListener {

	private final Logger logger;
	protected ActionProvider actionProvider;

	public AbstractDelegateAction() {
		this(null);
	}

	public AbstractDelegateAction(String name) {
		super(name);

		Class thisClass = this.getClass();

		logger = LoggerFactory.getLogger(thisClass);

		String actionProviderName = "com.potetm.netbeansactions.actions.api." + thisClass.getSimpleName() + "Provider";
		Class<? extends ActionProvider> actionProviderClass;

		try {
			actionProviderClass = Class.forName(actionProviderName).asSubclass(ActionProvider.class);
		} catch (ClassNotFoundException classNotFoundException) {
			RuntimeException runtimeException = new RuntimeException(classNotFoundException);

			logger.error("Unable to obtain a class object from string \"{}\". Make sure the fully-qualified class name for the Provider Action is {}", new Object[]{actionProviderName,
								actionProviderName, runtimeException});

			throw runtimeException;
		}

		actionProvider = (AbstractActionProvider) Lookup.getDefault().lookup(actionProviderClass);

		if (actionProvider == null) {
			NullPointerException nullPointerException = new NullPointerException("actionProvider is null" );

			logger.error("Unable to lookup action {}", actionProviderClass, nullPointerException);

			throw nullPointerException;
		}
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		try {
			actionProvider.fireActionPerformed(new ActionEvent(this, actionEvent.getID(), actionEvent.getActionCommand()));
		} catch (Exception actionException) {
			NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(actionException.getMessage());
			DialogDisplayer.getDefault().notify(notifyDescriptor);
		}
	}
}
