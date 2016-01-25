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
package org.talend.mdm.repository.core.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.properties.ByteArray;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.ReferenceFileItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.utils.XmiResourceManager;
import org.talend.mdm.repository.core.IRepositoryNodeResourceProvider;
import org.talend.mdm.repository.model.mdmproperties.ContainerItem;
import org.talend.mdm.repository.model.mdmproperties.MDMServerObjectItem;

/**
 * DOC hbhong class global comment. Detailled comment <br/>
 * 
 */
public abstract class AbstractRepositoryNodeResourceProvider implements IRepositoryNodeResourceProvider {

    Logger log = Logger.getLogger(AbstractRepositoryNodeResourceProvider.class);

    protected XmiResourceManager xmiResourceManager = ProxyRepositoryFactory.getInstance().getRepositoryFactoryFromProvider()
            .getResourceManager();

    protected Resource createCommonItemResource(IProject project, Item item, ERepositoryObjectType repositoryType, IPath path)
            throws PersistenceException {
        return xmiResourceManager.createItemResource(project, item, path, repositoryType, false);
    }

    public boolean canHandleItem(Item item) {
        if (item instanceof ContainerItem) {
            return canHandleRepObjType(((ContainerItem) item).getRepObjType());
        }
        ERepositoryObjectType repObjType = getRepositoryObjectType(item);
        return repObjType != null;
    }

    public Resource save(Item item) throws PersistenceException {
        if (item instanceof MDMServerObjectItem) {
            return xmiResourceManager.getItemResource(item);
        }
        return null;
    }

    public List<IRepositoryViewObject> findMember(IProject project, ERepositoryObjectType type, boolean hasSystemFolder) {

        String folderPath = type.getFolder();
        IFolder folder = project.getFolder(folderPath);
        if (!folder.exists()) {
            try {
                folder.create(true, true, null);
            } catch (CoreException e) {
                log.error(e.getMessage(), e);
            }
        }
        return findMember(folder, type);

    }

    /**
     * DOC hbhong Comment method "findMember".
     * 
     * @param folder
     * @param type
     * @return
     */
    private List<IRepositoryViewObject> findMember(IFolder folder, ERepositoryObjectType type) {
        List<IRepositoryViewObject> viewObjs = new ArrayList<IRepositoryViewObject>();
        try {
            for (IResource resource : folder.members()) {
                if (resource instanceof IFile) {
                    if (xmiResourceManager.isPropertyFile((IFile) resource)) {
                        Property property = xmiResourceManager.loadProperty(resource);

                    }
                }
            }
        } catch (CoreException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean needSaveReferenceFile() {
        return false;
    }

    public void handleReferenceFile(Item item) {
        // do nothing
    }

    public void linkReferenceFile(Item item, IFile file) {
        try {
            file.refreshLocal(0, null);
            ReferenceFileItem fileItem = findReferenceFileItem(item, file);
            if (fileItem != null) {
                fileItem.getContent().setInnerContentFromFile(file);
            } else {
                ReferenceFileItem procFileItem = PropertiesFactory.eINSTANCE.createReferenceFileItem();
                ByteArray byteArray = PropertiesFactory.eINSTANCE.createByteArray();
                byteArray.setInnerContentFromFile(file);
                procFileItem.setContent(byteArray);
                procFileItem.setExtension(file.getFileExtension());
                // procFileItem.setName(file.getName());
                // item.getReferenceResources().clear();
                item.getReferenceResources().add(procFileItem);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (CoreException e) {
            log.error(e.getMessage(), e);
        }
    }

    private ReferenceFileItem findReferenceFileItem(Item item, IFile file) {
        EList referenceResources = item.getReferenceResources();
        if (referenceResources != null) {
            for (Object refObj : referenceResources) {
                ReferenceFileItem fileItem = (ReferenceFileItem) refObj;

                URI uri = fileItem.getContent().eResource().getURI();
                String name = uri.lastSegment();

                if (name != null && name.equals(file.getName())) {
                    return fileItem;
                }
            }
        }
        return null;
    }
}
