/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.service.itemdispatcher.ejb.local;

/**
 * Local home interface for ItemDispatcher.
 * @xdoclet-generated at 14-07-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface ItemDispatcherLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/ItemDispatcherLocal";
   public static final String JNDI_NAME="amalto/local/service/itemdispatcher";

   public com.amalto.service.itemdispatcher.ejb.local.ItemDispatcherLocal create()
      throws javax.ejb.CreateException;

}
