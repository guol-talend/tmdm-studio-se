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
package org.talend.mdm.repository.utils;

import java.util.regex.Pattern;

public class ValidateUtil {

    public static boolean matchCommonRegex(String newText) {
        String headRegex = "\\w(-|\\w)*"; //$NON-NLS-1$
        String tailRegex = ".*\\w";//$NON-NLS-1$

        return matches(headRegex, tailRegex, newText);
    }

    public static boolean matchCustomFormRegex(String newText) {
        String regex = "\\w(#|\\.|\\w)*";//$NON-NLS-1$
        String tailRegex = ".*\\w";//$NON-NLS-1$

        return matches(regex, tailRegex, newText);
    }

    public static boolean matchViewProcessRegex(String newText) {
        String regex = "\\w(#|\\.|\\w)*";//$NON-NLS-1$
        String tailRegex = ".*(#|\\w)";//$NON-NLS-1$

        return matches(regex, tailRegex, newText);
    }

    public static boolean matchSmartViewRegex(String newText) {
        if (newText.indexOf("#") != newText.lastIndexOf("#")) { //$NON-NLS-1$//$NON-NLS-2$
            return false;
        }

        return matchViewProcessRegex(newText);
    }

    public static boolean matchRoleRegex(String newText) {
        String regex = "\\w(#|\\w)*";//$NON-NLS-1$
        String tailRegex = ".*\\w";//$NON-NLS-1$

        return matches(regex, tailRegex, newText);
    }

    private static boolean matches(String regex, String tailRegex, String newText) {
        Pattern tailPattern = Pattern.compile(tailRegex);
        boolean match = tailPattern.matcher(newText).matches();

        if (match) {
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(newText).matches();
        }

        return false;
    }
}
