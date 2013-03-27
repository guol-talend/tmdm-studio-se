// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.workbench.serverexplorer.console;

import org.eclipse.ui.PlatformUI;

/**
 * created by Karelun Huang on Mar 25, 2013 Detailled comment
 *
 */
public class MDMServerPreferenceService {

    public static final String REFRESH_FREQ = "_mdm_refresh_frequency"; //$NON-NLS-1$

    public static final String DISPLAY_MAX_LINES = "_mdm_max_lines_displayed"; //$NON-NLS-1$

    public static int getRefrehFrequency() {
        PlatformUI.getPreferenceStore().setDefault(REFRESH_FREQ, 2);
        return PlatformUI.getPreferenceStore().getInt(REFRESH_FREQ);
    }

    public static int getDefaultRefreshFrequency() {
        return PlatformUI.getPreferenceStore().getDefaultInt(REFRESH_FREQ);
    }

    public static void setRefreshFrequency(int value) {
        PlatformUI.getPreferenceStore().setValue(REFRESH_FREQ, value);
    }

    public static int getMaxDisplayedLines() {
        PlatformUI.getPreferenceStore().setDefault(DISPLAY_MAX_LINES, 100);
        return PlatformUI.getPreferenceStore().getInt(DISPLAY_MAX_LINES);
    }

    public static int getDefaultMaxDisplayedLines() {
        return PlatformUI.getPreferenceStore().getDefaultInt(DISPLAY_MAX_LINES);
    }

    public static void setMaxDisplayedLines(int value) {
        PlatformUI.getPreferenceStore().setValue(DISPLAY_MAX_LINES, value);
    }
}
