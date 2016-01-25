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
package com.amalto.workbench.detailtabs.sections.handlers;

import com.amalto.workbench.detailtabs.sections.model.ISubmittable;

class DefaultCommitHandler<T extends ISubmittable> extends CommitHandler<T> {

    public DefaultCommitHandler(T submittedObj) {
        super(submittedObj);
    }

    @Override
    protected boolean doSubmit() {
        return false;
    }

    @Override
    protected void validateCommit() {
    }

}
