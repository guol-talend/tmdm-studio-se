/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.local;

/**
 * Local interface for ItemCtrl2.
 * @xdoclet-generated at 25-06-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface ItemCtrl2Local
   extends javax.ejb.EJBLocalObject
{
   /**
    * Creates or updates a item
    * @throws XtentisException
    */
   public com.amalto.core.ejb.ItemPOJOPK putItem( com.amalto.core.ejb.ItemPOJO item,com.amalto.core.objects.datamodel.ejb.DataModelPOJO datamodel ) throws com.amalto.core.util.XtentisException;

   /**
    * Get item
    * @throws XtentisException
    */
   public com.amalto.core.ejb.ItemPOJO getItem( com.amalto.core.ejb.ItemPOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Get an item - no exception is thrown: returns null if not found
    * @throws XtentisException
    */
   public com.amalto.core.ejb.ItemPOJO existsItem( com.amalto.core.ejb.ItemPOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Remove an item - returns null if no item was deleted
    * @throws XtentisException
    */
   public com.amalto.core.ejb.ItemPOJOPK deleteItem( com.amalto.core.ejb.ItemPOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Delete items in a stateless mode: open a connection --> perform delete --> close the connection
    * @throws XtentisException
    */
   public int deleteItems( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String conceptName,com.amalto.xmlserver.interfaces.IWhereItem search,int spellTreshold ) throws com.amalto.core.util.XtentisException;

   /**
    * Drop an item - returns null if no item was dropped
    * @throws XtentisException
    */
   public com.amalto.core.ejb.DroppedItemPOJOPK dropItem( com.amalto.core.ejb.ItemPOJOPK itemPOJOPK,java.lang.String partPath ) throws com.amalto.core.util.XtentisException;

   /**
    * Get unordered items of a Concept using an optional where condition
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param conceptName The name of the concept
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    * @return The ordered list of results
    * @throws XtentisException    */
   public java.util.ArrayList getItems( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String conceptName,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Get potentially ordered items of a Concept using an optional where condition
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param conceptName The name of the concept
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param orderBy The full path of the item user to order
    * @param direction One of {@link IXmlServerSLWrapper#ORDER_ASCENDING} or {@link IXmlServerSLWrapper#ORDER_DESCENDING}
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    * @return The ordered list of results
    * @throws XtentisException    */
   public java.util.ArrayList getItems( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String conceptName,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,java.lang.String orderBy,java.lang.String direction,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Search Items thru a view in a cluster and specifying a condition
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param viewPOJOPK The View
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    * @return The ordered list of results
    * @throws XtentisException
    */
   public java.util.ArrayList viewSearch( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,com.amalto.core.objects.view.ejb.ViewPOJOPK viewPOJOPK,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Search ordered Items thru a view in a cluster and specifying a condition
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param viewPOJOPK The View
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param orderBy The full path of the item user to order
    * @param direction One of {@link IXmlServerSLWrapper#ORDER_ASCENDING} or {@link IXmlServerSLWrapper#ORDER_DESCENDING}
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    * @return The ordered list of results
    * @throws XtentisException
    */
   public java.util.ArrayList viewSearch( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,com.amalto.core.objects.view.ejb.ViewPOJOPK viewPOJOPK,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,java.lang.String orderBy,java.lang.String direction,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Returns an ordered collection of results searched in a cluster and specifying an optional condition<br/> The results are xml objects made of elements constituted by the specified viewablePaths
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param forceMainPivot An optional pivot that will appear first in the list of pivots in the query<br>: This allows forcing cartesian products: for instance Order Header vs Order Line
    * @param viewablePaths The list of elements returned in each result
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    * @return The ordered list of results
    * @throws XtentisException
    */
   public java.util.ArrayList xPathsSearch( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String forceMainPivot,java.util.ArrayList viewablePaths,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Returns an ordered collection of results searched in a cluster and specifying an optional condition<br/> The results are xml objects made of elements constituted by the specified viewablePaths
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param forceMainPivot An optional pivot that will appear first in the list of pivots in the query<br>: This allows forcing cartesian products: for instance Order Header vs Order Line
    * @param viewablePaths The list of elements returned in each result
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param orderBy The full path of the item user to order
    * @param direction One of {@link IXmlServerSLWrapper#ORDER_ASCENDING} or {@link IXmlServerSLWrapper#ORDER_DESCENDING}
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    * @return The ordered list of results
    * @throws XtentisException
    */
   public java.util.ArrayList xPathsSearch( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String forceMainPivot,java.util.ArrayList viewablePaths,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,java.lang.String orderBy,java.lang.String direction,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Count the items denoted by concept name meeting the optional condition whereItem
    * @param dataClusterPOJOPK
    * @param conceptName
    * @param whereItem
    * @param spellThreshold
    * @return The number of items found
    * @throws XtentisException
    */
   public long count( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String conceptName,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold ) throws com.amalto.core.util.XtentisException;

   /**
    * Search ordered Items thru a view in a cluster and specifying a condition
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param viewPOJOPK The View
    * @param searchValue The value/sentenced searched
    * @param matchAllWords If <code>true</code>, the items must match all the words in the sentence
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param orderBy The full path of the item user to order
    * @param direction One of {@link IXmlServerSLWrapper#ORDER_ASCENDING} or {@link IXmlServerSLWrapper#ORDER_DESCENDING}
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    * @return The ordered list of results
    * @throws XtentisException
    */
   public java.util.ArrayList quickSearch( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,com.amalto.core.objects.view.ejb.ViewPOJOPK viewPOJOPK,java.lang.String searchValue,boolean matchAllWords,int spellThreshold,java.lang.String orderBy,java.lang.String direction,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Get the possible value for the business Element Path, optionally filtered by a condition
    * @param dataClusterPOJOPK The data cluster where to run the query
    * @param businessElementPath The business element path. Must be of the form <code>ConceptName/[optional sub elements]/element</code>
    * @param whereItem The optional condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param orderBy The full path of the item user to order
    * @param direction One of {@link IXmlServerSLWrapper#ORDER_ASCENDING} or {@link IXmlServerSLWrapper#ORDER_DESCENDING}
    * @return The list of values
    * @throws XtentisException
    */
   public java.util.ArrayList getFullPathValues( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String businessElementPath,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,java.lang.String orderBy,java.lang.String direction ) throws com.amalto.core.util.XtentisException;

   /**
    * Extracts data using a transformer In addtion to any entry added by the plugins, the returned context contains at least one entry <code>com.amalto.core.pipeline</code> which holds a Hashmap with entries containing the outputs of the various plugins as TypedContent
    * @deprecated - Use a combination of {@link ItemCtrl2Bean#getItem(ItemPOJOPK)} and {@link TransformerV2CtrlBean#executeUntilDone(TransformerContext)}
    * @throws XtentisException
    */
   public com.amalto.core.util.TransformerPluginContext extractUsingTransformer( com.amalto.core.ejb.ItemPOJOPK pojoPK,com.amalto.core.ejb.TransformerPOJOPK transformerPOJOPK ) throws com.amalto.core.util.XtentisException;

   /**
    * Extract an Item thru a transformer
    * @deprecated - Use a combination of {@link ItemCtrl2Bean#getItem(ItemPOJOPK)} and {@link TransformerV2CtrlBean#execute(TransformerContext, TransformerCallBack)}
    * @throws XtentisException
    */
   public void extractUsingTransformer( com.amalto.core.ejb.ItemPOJOPK pojoPK,com.amalto.core.ejb.TransformerPOJOPK transformerPOJOPK,com.amalto.core.util.TransformerPluginContext context,com.amalto.core.util.TransformerPluginCallBack globalCallBack ) throws com.amalto.core.util.XtentisException;

   /**
    * Extract results thru a view and transform them using a transformer<br/> This call is asynchronous and results will be pushed via the passed {@link TransformerCallBack}
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param context The {@link TransformerContext} containi the inital context and the transformer name
    * @param globalCallBack The callback function called by the transformer when it completes a step
    * @param viewPOJOPK A filtering view
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param orderBy The full path of the item user to order
    * @param direction One of {@link IXmlServerSLWrapper#ORDER_ASCENDING} or {@link IXmlServerSLWrapper#ORDER_DESCENDING}
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    */
   public void extractUsingTransformerThroughView( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,com.amalto.core.objects.transformers.v2.util.TransformerContext context,com.amalto.core.objects.transformers.v2.util.TransformerCallBack globalCallBack,com.amalto.core.objects.view.ejb.ViewPOJOPK viewPOJOPK,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,java.lang.String orderBy,java.lang.String direction,int start,int limit ) throws com.amalto.core.util.XtentisException;

   /**
    * Extract results thru a view and transform them using a transformer<br/> This call is asynchronous and results will be pushed via the passed {@link TransformerCallBack}
    * @param dataClusterPOJOPK The Data Cluster where to run the query
    * @param transformerPOJOPK The transformer to use
    * @param viewPOJOPK A filtering view
    * @param whereItem The condition
    * @param spellThreshold The condition spell checking threshold. A negative value de-activates spell
    * @param orderBy The full path of the item user to order
    * @param direction One of {@link IXmlServerSLWrapper#ORDER_ASCENDING} or {@link IXmlServerSLWrapper#ORDER_DESCENDING}
    * @param start The first item index (starts at zero)
    * @param limit The maximum number of items to return
    */
   public com.amalto.core.objects.transformers.v2.util.TransformerContext extractUsingTransformerThroughView( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,com.amalto.core.objects.transformers.v2.ejb.TransformerV2POJOPK transformerPOJOPK,com.amalto.core.objects.view.ejb.ViewPOJOPK viewPOJOPK,com.amalto.xmlserver.interfaces.IWhereItem whereItem,int spellThreshold,java.lang.String orderBy,java.lang.String direction,int start,int limit ) throws com.amalto.core.util.XtentisException;

   public java.util.ArrayList runQuery( java.lang.String revisionID,com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK,java.lang.String query,java.lang.String[] parameters ) throws com.amalto.core.util.XtentisException;

   /**
    * Returns a map with keys being the concepts found in the Data Cluster and as value the revisionID
    * @param dataClusterPOJOPK
    * @return A {@link TreeMap} of concept names to revision IDs
    * @throws XtentisException
    */
   public java.util.TreeMap getConceptsInDataCluster( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dataClusterPOJOPK ) throws com.amalto.core.util.XtentisException;

}
