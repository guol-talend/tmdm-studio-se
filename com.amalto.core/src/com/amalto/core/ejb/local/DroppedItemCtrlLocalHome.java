/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.local;

/**
 * Local home interface for DroppedItemCtrl.
 * @xdoclet-generated at 25-06-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface DroppedItemCtrlLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/DroppedItemCtrlLocal";
   public static final String JNDI_NAME="amalto/local/core/droppeditemctrl";

   public com.amalto.core.ejb.local.DroppedItemCtrlLocal create()
      throws javax.ejb.CreateException;

}
