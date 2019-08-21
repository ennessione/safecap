/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/

package uk.ac.ncl.safecap.diagram.misc.copypaste;

import java.util.Hashtable;

import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandlerProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandlerContext;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Concrete class that implements the <code>IGlobalActionHandlerProvider</code>
 * providing <code>IGlobalActionHandler</code> for all diagrams supporting
 * render capabilities.
 * 
 * @author cmahoney
 */
public final class MyDiagramUIRenderGlobalActionHandlerProvider extends AbstractGlobalActionHandlerProvider {

	/**
	 * List for handlers.
	 */
	private final Hashtable handlerList = new Hashtable();

	private org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler z;

	/**
	 * Creates a new instance.
	 */
	public MyDiagramUIRenderGlobalActionHandlerProvider() {
		super();
	}

	/**
	 * Returns a global action handler that supports global image operations (cut,
	 * copy, and paste).
	 */
	@Override
	public IGlobalActionHandler getGlobalActionHandler(

			final IGlobalActionHandlerContext context) {

		/* Create the handler */

		if (!getHandlerList().containsKey(context.getActivePart())) {

			getHandlerList().put(context.getActivePart(),

					new MyEditorClipboardSupportGlobalActionHandler());

			/*
			 * Register as a part listener so that the cache can be cleared when the part is
			 * disposed
			 */

			context.getActivePart().getSite().getPage().addPartListener(new IPartListener() {
				private IWorkbenchPart localPart = context.getActivePart();

				@Override
				public void partActivated(IWorkbenchPart part) {
				}

				@Override
				public void partBroughtToTop(IWorkbenchPart part) {
					// Do nothing
				}

				@Override
				public void partClosed(IWorkbenchPart part) {
					/* Remove the cache associated with the part */
					if (part != null && part == localPart && getHandlerList().containsKey(part)) {
						getHandlerList().remove(part);
						localPart.getSite().getPage().removePartListener(this);
						localPart = null;
					}
				}

				@Override
				public void partDeactivated(IWorkbenchPart part) {
					// Do nothing
				}

				@Override
				public void partOpened(IWorkbenchPart part) {
					// Do nothing
				}

			});

		}
		return (MyEditorClipboardSupportGlobalActionHandler) getHandlerList().get(context.getActivePart());
	}

	/**
	 * Returns the handlerList.
	 * 
	 * @return Hashtable
	 */
	private Hashtable getHandlerList() {
		return handlerList;
	}
}
