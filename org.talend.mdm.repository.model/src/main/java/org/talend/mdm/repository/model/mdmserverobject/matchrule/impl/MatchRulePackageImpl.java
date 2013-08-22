/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.mdm.repository.model.mdmserverobject.matchrule.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.talend.mdm.repository.model.mdmmetadata.MdmmetadataPackage;

import org.talend.mdm.repository.model.mdmserverobject.MdmserverobjectPackage;

import org.talend.mdm.repository.model.mdmserverobject.impl.MdmserverobjectPackageImpl;

import org.talend.mdm.repository.model.mdmserverobject.matchrule.BlockingKey;
import org.talend.mdm.repository.model.mdmserverobject.matchrule.BlockingKeyDefinition;
import org.talend.mdm.repository.model.mdmserverobject.matchrule.EntityMapInfo;
import org.talend.mdm.repository.model.mdmserverobject.matchrule.MatchRuleFactory;
import org.talend.mdm.repository.model.mdmserverobject.matchrule.MatchRuleMapInfo;
import org.talend.mdm.repository.model.mdmserverobject.matchrule.MatchRulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MatchRulePackageImpl extends EPackageImpl implements MatchRulePackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass matchRuleMapInfoEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass entityMapInfoEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass matchKeyXPathMapEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass blockingKeyDefinitionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass blockingKeyEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.talend.mdm.repository.model.mdmserverobject.matchrule.MatchRulePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private MatchRulePackageImpl() {
        super(eNS_URI, MatchRuleFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link MatchRulePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static MatchRulePackage init() {
        if (isInited) return (MatchRulePackage)EPackage.Registry.INSTANCE.getEPackage(MatchRulePackage.eNS_URI);

        // Obtain or create and register package
        MatchRulePackageImpl theMatchRulePackage = (MatchRulePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MatchRulePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MatchRulePackageImpl());

        isInited = true;

        // Initialize simple dependencies
        EcorePackage.eINSTANCE.eClass();
        MdmmetadataPackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        MdmserverobjectPackageImpl theMdmserverobjectPackage = (MdmserverobjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(MdmserverobjectPackage.eNS_URI) instanceof MdmserverobjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(MdmserverobjectPackage.eNS_URI) : MdmserverobjectPackage.eINSTANCE);

        // Create package meta-data objects
        theMatchRulePackage.createPackageContents();
        theMdmserverobjectPackage.createPackageContents();

        // Initialize created meta-data
        theMatchRulePackage.initializePackageContents();
        theMdmserverobjectPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theMatchRulePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(MatchRulePackage.eNS_URI, theMatchRulePackage);
        return theMatchRulePackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMatchRuleMapInfo() {
        return matchRuleMapInfoEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMatchRuleMapInfo_EntityMapInfos() {
        return (EReference)matchRuleMapInfoEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEntityMapInfo() {
        return entityMapInfoEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEntityMapInfo_EntityName() {
        return (EAttribute)entityMapInfoEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEntityMapInfo_MatchKeyMap() {
        return (EReference)entityMapInfoEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEntityMapInfo_BlockingKeyDefinition() {
        return (EReference)entityMapInfoEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEntityMapInfo_MatchRuleName() {
        return (EAttribute)entityMapInfoEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEntityMapInfo_Parent() {
        return (EReference)entityMapInfoEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMatchKeyXPathMap() {
        return matchKeyXPathMapEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMatchKeyXPathMap_Key() {
        return (EAttribute)matchKeyXPathMapEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMatchKeyXPathMap_Value() {
        return (EAttribute)matchKeyXPathMapEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getBlockingKeyDefinition() {
        return blockingKeyDefinitionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getBlockingKeyDefinition_BlockingKeys() {
        return (EReference)blockingKeyDefinitionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlockingKeyDefinition_UseBuiltInColumn() {
        return (EAttribute)blockingKeyDefinitionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getBlockingKey() {
        return blockingKeyEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlockingKey_ElmentName() {
        return (EAttribute)blockingKeyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MatchRuleFactory getMatchRuleFactory() {
        return (MatchRuleFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        matchRuleMapInfoEClass = createEClass(MATCH_RULE_MAP_INFO);
        createEReference(matchRuleMapInfoEClass, MATCH_RULE_MAP_INFO__ENTITY_MAP_INFOS);

        entityMapInfoEClass = createEClass(ENTITY_MAP_INFO);
        createEAttribute(entityMapInfoEClass, ENTITY_MAP_INFO__ENTITY_NAME);
        createEReference(entityMapInfoEClass, ENTITY_MAP_INFO__MATCH_KEY_MAP);
        createEReference(entityMapInfoEClass, ENTITY_MAP_INFO__BLOCKING_KEY_DEFINITION);
        createEAttribute(entityMapInfoEClass, ENTITY_MAP_INFO__MATCH_RULE_NAME);
        createEReference(entityMapInfoEClass, ENTITY_MAP_INFO__PARENT);

        matchKeyXPathMapEClass = createEClass(MATCH_KEY_XPATH_MAP);
        createEAttribute(matchKeyXPathMapEClass, MATCH_KEY_XPATH_MAP__KEY);
        createEAttribute(matchKeyXPathMapEClass, MATCH_KEY_XPATH_MAP__VALUE);

        blockingKeyDefinitionEClass = createEClass(BLOCKING_KEY_DEFINITION);
        createEReference(blockingKeyDefinitionEClass, BLOCKING_KEY_DEFINITION__BLOCKING_KEYS);
        createEAttribute(blockingKeyDefinitionEClass, BLOCKING_KEY_DEFINITION__USE_BUILT_IN_COLUMN);

        blockingKeyEClass = createEClass(BLOCKING_KEY);
        createEAttribute(blockingKeyEClass, BLOCKING_KEY__ELMENT_NAME);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(matchRuleMapInfoEClass, MatchRuleMapInfo.class, "MatchRuleMapInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getMatchRuleMapInfo_EntityMapInfos(), this.getEntityMapInfo(), this.getEntityMapInfo_Parent(), "entityMapInfos", null, 0, -1, MatchRuleMapInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(entityMapInfoEClass, EntityMapInfo.class, "EntityMapInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEntityMapInfo_EntityName(), theEcorePackage.getEString(), "entityName", null, 0, 1, EntityMapInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getEntityMapInfo_MatchKeyMap(), this.getMatchKeyXPathMap(), null, "matchKeyMap", null, 0, -1, EntityMapInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getEntityMapInfo_BlockingKeyDefinition(), this.getBlockingKeyDefinition(), null, "blockingKeyDefinition", null, 0, 1, EntityMapInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getEntityMapInfo_MatchRuleName(), theEcorePackage.getEString(), "matchRuleName", null, 0, 1, EntityMapInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getEntityMapInfo_Parent(), this.getMatchRuleMapInfo(), this.getMatchRuleMapInfo_EntityMapInfos(), "parent", null, 0, 1, EntityMapInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(matchKeyXPathMapEClass, Map.Entry.class, "MatchKeyXPathMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getMatchKeyXPathMap_Key(), theEcorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMatchKeyXPathMap_Value(), theEcorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(blockingKeyDefinitionEClass, BlockingKeyDefinition.class, "BlockingKeyDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getBlockingKeyDefinition_BlockingKeys(), this.getBlockingKey(), null, "blockingKeys", null, 0, -1, BlockingKeyDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBlockingKeyDefinition_UseBuiltInColumn(), theEcorePackage.getEBoolean(), "useBuiltInColumn", null, 0, 1, BlockingKeyDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(blockingKeyEClass, BlockingKey.class, "BlockingKey", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getBlockingKey_ElmentName(), theEcorePackage.getEString(), "elmentName", null, 0, 1, BlockingKey.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    }

} //MatchRulePackageImpl