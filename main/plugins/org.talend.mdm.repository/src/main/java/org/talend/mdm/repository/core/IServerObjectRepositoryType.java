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
package org.talend.mdm.repository.core;

import org.talend.core.model.repository.ERepositoryObjectType;

/**
 * DOC hbhong class global comment. Detailled comment <br/>
 * 
 */
public interface IServerObjectRepositoryType {

    // public ERepositoryObjectType TYPE_CATEGORY = (ERepositoryObjectType) ERepositoryObjectType.valueOf(
    //            ERepositoryObjectType.class, "MDM.Category"); //$NON-NLS-1$
    //
    // public ERepositoryObjectType TYPE_FOLDER = (ERepositoryObjectType) ERepositoryObjectType.valueOf(
    //            ERepositoryObjectType.class, "MDM.Folder"); //$NON-NLS-1$

    // /

    public ERepositoryObjectType TYPE_ROLE = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.Role"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_MENU = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.Menu"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_DATAMODEL = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.DataModel"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_DATACLUSTER = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.DataCluster"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_STOREPROCEDURE = ERepositoryObjectType.valueOf(ERepositoryObjectType.class,
            "MDM.StoredProcedure"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_UNIVERSE = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.Universe"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_SYNCHRONIZATIONPLAN = ERepositoryObjectType.valueOf(ERepositoryObjectType.class,
            "MDM.SynchronizationPlan"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_VIEW = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.View"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_WORKFLOW = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.Workflow"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_TRANSFORMERV2 = ERepositoryObjectType.valueOf(ERepositoryObjectType.class,
            "MDM.TransformerV2"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_ROUTINGRULE = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.RoutingRule"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_JOB = ERepositoryObjectType.PROCESS;

    public ERepositoryObjectType TYPE_EVENTMANAGER = ERepositoryObjectType.valueOf(ERepositoryObjectType.class,
            "MDM.EventManager"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_SERVICECONFIGURATION = ERepositoryObjectType.valueOf(ERepositoryObjectType.class,
            "MDM.ServiceConfiguration"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_RESOURCE = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.Resource"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_CUSTOM_FORM = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.CustomForm"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_RECYCLE_BIN = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "MDM.RecycleBin"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_MATCH_RULE_MAPINFO = ERepositoryObjectType.valueOf(ERepositoryObjectType.class,
            "MDM.MatchRuleMapInfo"); //$NON-NLS-1$

    public ERepositoryObjectType TYPE_MATCH_RULE = ERepositoryObjectType.TDQ_RULES_MATCHER;

    public ERepositoryObjectType[] ALL_TYPES = new ERepositoryObjectType[] { TYPE_CUSTOM_FORM, TYPE_DATACLUSTER, TYPE_DATAMODEL,
            TYPE_EVENTMANAGER, TYPE_JOB, TYPE_MENU, TYPE_MATCH_RULE, TYPE_RESOURCE, TYPE_ROLE, TYPE_ROUTINGRULE,
            TYPE_SERVICECONFIGURATION, TYPE_STOREPROCEDURE, TYPE_SYNCHRONIZATIONPLAN, TYPE_TRANSFORMERV2, TYPE_UNIVERSE,
            TYPE_VIEW, TYPE_WORKFLOW };
}
