// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.mdm.repository.core.impl.transformerV2;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.Image;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.mdm.repository.core.impl.AbstractLabelProvider;
import org.talend.mdm.repository.i18n.Messages;
import org.talend.mdm.repository.model.mdmproperties.ContainerItem;
import org.talend.mdm.repository.model.mdmproperties.WSTransformerV2Item;
import org.talend.mdm.repository.plugin.RepositoryPlugin;
import org.talend.mdm.repository.utils.EclipseResourceManager;
import org.talend.mdm.repository.utils.RepositoryTransformUtil;

/**
 * DOC hbhong class global comment. Detailled comment <br/>
 * 
 */
public class TransformerV2LabelProvider extends AbstractLabelProvider {

    private static final Image IMG = EclipseResourceManager.getImage(RepositoryPlugin.PLUGIN_ID, "icons/transformer.png"); //$NON-NLS-1$;

    public String getCategoryLabel(ERepositoryObjectType type) {
        return Messages.TransformerV2XX_ProcessCategoryName;
    }

    @Override
    public Image getCategoryImage(Item item) {
        return IMG;
    }

    @Override
    protected String getConainerItemText(Item item) {
        if (item.getState().getPath().equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_BEFORESAVE)) {
            return Messages.TransformerV2XX_BeforeSaving;
        } else if (item.getState().getPath().equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_BEFOREDEL)) {
            return Messages.TransformerV2XX_BeforeDeleting;
        } else if (item.getState().getPath().equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_ENTITYACTION)) {
            return Messages.TransformerV2XX_EntityAction;
        } else if (item.getState().getPath().equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_WELCOMEACTION)) {
            return Messages.TransformerV2XX_WelcomeAction;
        } else if (item.getState().getPath().equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_SMARTVIEW)) {
            return Messages.TransformerV2XX_SmartView;
        } else if (item.getState().getPath().equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_OTHER)) {
            return Messages.TransformerV2XX_Other;
        }

        return super.getConainerItemText(item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.mdm.repository.core.impl.AbstractLabelProvider#getText(org.talend.core.model.properties.Item)
     */
    @Override
    protected String getText(Item item) {
        String text = super.getText(item);
        String itemText = RepositoryTransformUtil.getInstance().transformToSilyProcessName(text, false);
        return itemText;
    }

    @Override
    public Image getImage(Object element) {
        Image img = super.getImage(element);
        if (img == null) {
            Item item = getItem(element);
            if (item != null) {
                if (item instanceof WSTransformerV2Item) {
                    img = IMG;
                }
            }
        }
        return img;
    }

    @Override
    protected boolean isSystemServerObjectItem(Object element) {
        Item item = getItem(element);
        if (item instanceof ContainerItem) {
            String path = item.getState().getPath();
            if (path.equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_BEFORESAVE)
                    || path.equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_BEFOREDEL)
                    || path.equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_ENTITYACTION)
                    || path.equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_WELCOMEACTION)
                    || path.equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_SMARTVIEW)
                    || path.equals(IPath.SEPARATOR + ITransformerV2NodeConsDef.PATH_OTHER)) {
                return true;
            }
        }

        return false;
    }
}
