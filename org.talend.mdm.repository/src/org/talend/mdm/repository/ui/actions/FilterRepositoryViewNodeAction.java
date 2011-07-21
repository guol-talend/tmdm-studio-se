package org.talend.mdm.repository.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.talend.mdm.repository.core.IRepositoryViewFilter;
import org.talend.mdm.repository.plugin.RepositoryPlugin;
import org.talend.mdm.repository.ui.navigator.filter.MDMRepositoryViewFilter;

public class FilterRepositoryViewNodeAction implements IViewActionDelegate {

    IPropertyChangeListener pListener = new IPropertyChangeListener() {

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getProperty().equals(IRepositoryViewFilter.PROP_REFRESH)) {
                viewFilter.updatePreferences();
                //
                if (action.isChecked()) {
                    run(action);
                }
            }
        }

    };

    public FilterRepositoryViewNodeAction() {
        viewFilter = new MDMRepositoryViewFilter();
        RepositoryPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(pListener);
    }

    private CommonViewer commonViewer;

    MDMRepositoryViewFilter viewFilter;

    private IAction action;

    @Override
    public void run(IAction action) {
        if (commonViewer != null) {
            commonViewer.removeFilter(viewFilter);
            if (action.isChecked()) {
                commonViewer.addFilter(viewFilter);
            }
        }
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.action = action;
    }

    @Override
    public void init(IViewPart view) {
        if (view instanceof CommonNavigator) {
            this.commonViewer = ((CommonNavigator) view).getCommonViewer();
        }
    }

}