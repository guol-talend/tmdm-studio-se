// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.detailtabs.sections.model.annotationinfo.langinfo;

import java.util.Collection;

import org.eclipse.xsd.XSDComponent;

import com.amalto.workbench.detailtabs.sections.handlers.CommitHandler;
import com.amalto.workbench.detailtabs.sections.handlers.DisplayFormatCommitHandler;

public class DisplayFormatInfoCollection extends LanguageInfoCollection {

    public DisplayFormatInfoCollection(XSDComponent sourceXSDComponent, Collection<LanguageInfo> initLanguageInfos) {
        super(sourceXSDComponent, initLanguageInfos);
    }

    public DisplayFormatInfoCollection(XSDComponent sourceXSDComponent, LanguageInfo[] initLanguageInfos) {
        super(sourceXSDComponent, initLanguageInfos);
    }

    public CommitHandler<DisplayFormatInfoCollection> createCommitHandler() {
        return new DisplayFormatCommitHandler(this);
    }

}
