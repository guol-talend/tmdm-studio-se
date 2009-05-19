package com.amalto.workbench.actions;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDSchema;

import com.amalto.workbench.AmaltoWorbenchPlugin;
import com.amalto.workbench.dialogs.AnnotationLanguageLabelsDialog;
import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.providers.XSDTreeContentProvider;
import com.amalto.workbench.utils.XSDAnnotationsStructure;

public class XSDSetAnnotationLabelAction extends Action{

	protected DataModelMainPage page = null;
	protected XSDSchema schema = null;
	
	public XSDSetAnnotationLabelAction(DataModelMainPage page) {
		super();
		this.page = page;
		setImageDescriptor(AmaltoWorbenchPlugin.getImageDescriptor( "icons/annotation.gif"));
		setText("Set the Labels");
		setToolTipText("Set the Element Labels");
	}
	
	public void run() {
		try {
			super.run();
            schema = ((XSDTreeContentProvider)page.getTreeViewer().getContentProvider()).getXsdSchema();
            IStructuredSelection selection = (IStructuredSelection)page.getTreeViewer().getSelection();
            	
            XSDAnnotationsStructure struc = new XSDAnnotationsStructure((XSDComponent)selection.getFirstElement());
            if (struc.getAnnotation() == null) {
            	throw new RuntimeException("Unable to set an annotation for object of type "+selection.getFirstElement().getClass().getName());
            }
   
            AnnotationLanguageLabelsDialog dlg = new AnnotationLanguageLabelsDialog(
            		struc.getLabels(),
					new AnnotationLabelDialogSelectionListener(page),
					page.getEditorSite().getShell(),
					"Set the Labels"
			);
			dlg.setBlockOnOpen(true);
			dlg.open();
            
			if (dlg.getReturnCode() == Window.OK)  {			
				//remove existing annotations with labels
				struc.removeAllLabels();
				//add the new ones
				LinkedHashMap<String, String> descriptions = dlg.getDescriptionsMap();
	        	Set<String> isoCodes = descriptions.keySet();
	        	for (Iterator iter = isoCodes.iterator(); iter.hasNext(); ) {
					String isoCode = (String) iter.next();
					struc.setLabel(isoCode, descriptions.get(isoCode));
				}
	        }
			
			if (struc.hasChanged()) {
				page.markDirty();
				page.getTreeViewer().refresh(true);
				page.getTreeViewer().expandToLevel(selection.getFirstElement(), 2);
			}
       		
       
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					page.getSite().getShell(),
					"Error", 
					"An error occured trying to create a new Element: "+e.getLocalizedMessage()
			);
		}		
	}
	public void runWithEvent(Event event) {
		super.runWithEvent(event);
	}
	
	
	
	/**
	 * This class listens to actions on the AnnotationLableDialog
	 * @author bgrieder
	 *
	 */
	class AnnotationLabelDialogSelectionListener implements SelectionListener{
		protected DataModelMainPage dmPage = null;
		
		public AnnotationLabelDialogSelectionListener(DataModelMainPage page) {
			super();
			this.dmPage = page;
		}
		
		public void widgetSelected(SelectionEvent e) {
			AnnotationLanguageLabelsDialog dlg = (AnnotationLanguageLabelsDialog)((Widget)e.getSource()).getData("dialog");
			if (dlg.getReturnCode() == Window.OK)  {
				//No particular check on content
				/*
				if (descriptions.size()==0) {
					MessageDialog.openError(
							viewer.getControl().getShell(),
							"Error", 
							"The Menu Entry must have at least one description"
					);
					return;
				}
				*/
	        }
			dlg.close();

		}
		public void widgetDefaultSelected(SelectionEvent e) {};
	}


}