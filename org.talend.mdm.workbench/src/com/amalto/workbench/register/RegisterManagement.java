// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.register;

import java.rmi.RemoteException;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;

import com.amalto.workbench.register.proxy.RegisterUserPortTypeProxy;
import com.amalto.workbench.service.GlobalServiceRegister;
import com.amalto.workbench.service.branding.IBrandingService;


/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id: RegisterManagement.java 21728 2009-02-09 10:23:23Z plegall $
 * 
 */
public class RegisterManagement {

    private static final int REGISTRATION_MAX_TRIES = 6;

    // REGISTRATION_DONE = 1 : registration OK
    private static final double REGISTRATION_DONE = 2;

    public static boolean register(String email, String country, boolean isProxyEnabled, String proxyHost,
            String proxyPort, String designerVersion, String projectLanguage, String osName, String osVersion,
            String javaVersion, long totalMemory, Long memRAM, int nbProc) throws Exception {
        boolean result = false;

        // if proxy is enabled
        if (isProxyEnabled) {
            // get parameter and put them in System.properties.
            System.setProperty("http.proxyHost", proxyHost); //$NON-NLS-1$
            System.setProperty("http.proxyPort", proxyPort); //$NON-NLS-1$

            // override automatic update parameters
            if (proxyPort != null && proxyPort.trim().equals("")) { //$NON-NLS-1$
                proxyPort = null;
            }

        }

        RegisterUserPortTypeProxy proxy = new RegisterUserPortTypeProxy();
        proxy.setEndpoint("http://www.talend.com/TalendRegisterWS/registerws.php"); //$NON-NLS-1$
        try {
        	IBrandingService brandingService = (IBrandingService) GlobalServiceRegister.getDefault().getService(
                       IBrandingService.class);
            result = proxy.registerUserWithAllUserInformations(email, country, designerVersion, brandingService.getShortProductName(), projectLanguage, osName, osVersion, javaVersion, totalMemory + "", memRAM //$NON-NLS-1$
                    + "", nbProc + ""); //$NON-NLS-1$ //$NON-NLS-2$
            if (result) {
                PlatformUI.getPreferenceStore().setValue("REGISTRATION_DONE", 1); //$NON-NLS-1$
            }
        } catch (RemoteException e) {
            decrementTry();
            throw (e);
        }
        return result;
    }

    /**
     * DOC mhirt Comment method "isProductRegistered".
     * 
     * @return
     */
    public static boolean isProductRegistered() {
        initPreferenceStore();
        IPreferenceStore prefStore = PlatformUI.getPreferenceStore();
        if ((prefStore.getInt("REGISTRATION_TRIES") > 1) && ((prefStore.getInt("REGISTRATION_DONE") != 1))) { //$NON-NLS-1$ //$NON-NLS-2$
            return false;
        }
        return true;
    }

    /**
     * DOC mhirt Comment method "init".
     * 
     * @return
     */
    private static void initPreferenceStore() {
        IPreferenceStore prefStore = PlatformUI.getPreferenceStore();
        if (prefStore.getDefaultInt("REGISTRATION_TRIES") == 0) { //$NON-NLS-1$
            prefStore.setDefault("REGISTRATION_TRIES", REGISTRATION_MAX_TRIES); //$NON-NLS-1$
        }
        if (prefStore.getDefaultInt("REGISTRATION_DONE") == 0) { //$NON-NLS-1$
            prefStore.setDefault("REGISTRATION_DONE", REGISTRATION_DONE); //$NON-NLS-1$
        }
    }

    /**
     * DOC mhirt Comment method "incrementTryNumber".
     */
    public static void decrementTry() {
        IPreferenceStore prefStore = PlatformUI.getPreferenceStore();
        prefStore.setValue("REGISTRATION_TRIES", prefStore.getInt("REGISTRATION_TRIES") - 1); //$NON-NLS-1$ //$NON-NLS-2$
    }

    // public static void main(String[] args) {
    // try {
    // boolean result = RegisterManagement.register("a@a.fr", "fr", "Beta2");
    // System.out.println(result);
    // } catch (BusinessException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
}
