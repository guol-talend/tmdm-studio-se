amalto.namespace("amalto.reporting");

amalto.reporting.Reporting = function () {


	///loadResource("/reporting/secure/css/Reporting.css", "" );


	var MSG_LOADING={
		'fr':'Chargement...',
		'en':'Loading...'
	}
	var  TITLE_SEARCH_PANEL =    {
	    'fr' : 'Recherche',
	    'en' : 'Search panel'
	}
	var  LABEL_LINES_PER_PAGE =    {
	    'fr' : 'Nombre de lignes par page',
	    'en' : 'Number of lines per page'
	}

	
	var LABEL_DISPLAYING = {
		'fr':'Résultats affichés : ',
		'en':'Displaying items'
	}
	var LABEL_OF = {
		'fr':'sur',
		'en':'of'
	}
	 
	 
	var FIELD_REQUIRED = {
		'fr' : 'Ce champ est obligatoire',
		'en' :'This field is required'
	}

	var  MSG_CONFIRM_DELETE_REPORTING =    {
	    'fr' : 'Voulez vous réellement effacer ce rapport : ',
	    'en' : 'Do you really want to delete this report : '
	}
	
	var OPERATOR_UNDEFINED = {
	    'fr' : 'cliquer pour choisir',
	    'en' : 'click to select'	
	}

	var BUTTON_SAVE = {
		'fr':'Sauvegarder',
		'en':'Save'
	}

	var BUTTON_DISPLAY = {
	'fr':'Visualiser',
	'en':'Display'
	}
	var BUTTON_EXPORT = {
	'fr':'Exporter vers Excel',
	'en':'Export to Excel'
	}	
	var BUTTON_DELETE = {
		'fr':'Supprimer',
		'en':'Delete'
	}
	var BUTTON_EDIT={
		'fr':'Editer',
		'en':'Edit'
	}
	var NEW_REPORTING = {
	'fr':'Nouveau rapport',
	'en':'New report'
	}

    var DUPLICATE_REPORTING = {
    	'fr':'Duplicate report',
    	'en':'Duplicate report'
    }
    
	var REPORTING = {
		'en':'Reports',
		'fr':'Rapports'
	}

	var NEW_REPORTING = {
		'fr':'Nouveau rapport',
		'en':'New report'
	}

	var ALERT_EXISTING_REPORTING={
		'fr':'Il existe déjà un rapport avec ce nom. \nVeuillez choisir un autre nom',
		'en':'A report with this name exists. \nPlease choose another name.'
	}
	
	var HEAD_FIELD = {
		'fr':'Champ',
		'en':'Field'
	}
	var HEAD_VALUE = {
		'fr':'Valeur',
		'en':'Value'
	}
	var HEAD_OPERATOR = {
		'fr':'Op&eacute;rateur',
		'en':'Operator'
	}
	
	var REPORTING_EXISTANT = {
		'fr':"Rapports existants",
		'en':"Reports"
	}
	
	var LABEL_SELECT = {
	'fr':'Sélectionnez',
	'en':'Select'
	}

	var LABEL_NAME = {
		'fr':'Nom du rapport',
		'en':'Report name'
	}
	
	var LABEL_SHARE_REPORTING = {
		'fr':'Partager ce rapport',
		'en':'Share this report'
	}
	
	var LABEL_DATAOBJECT = {
		'fr':'Objet de données',
		'en':'Data object'
	}
	
	var LABEL_FIELDS = {
		'fr':'Champs à afficher',
		'en':'Display fields'
	}
	var LABEL_AVAILABLE_FIELDS = {
		'fr':'Champs disponibles',
		'en':'Available fields'
	}
	
	var LABEL_FILTER = {
	'fr' : 'Filtres',
	'en' : 'Filters'
	}
	
	var LABEL_PIVOT = {
		'fr':'Pivot de recherche',
		'en':'Search pivot'
	}
	
	var LABEL_STORED_PROCEDURE = {
		'fr':'La procédure stockée',
		'en':'Stored procedure'
	}
	
	var LABEL_STORED_PROCEDURE_PARAM = {
		'fr':'Paramètres de procédure stockée',
		'en':'Stored procedure parameters'
	}
	
	var BUTTON_ADD_FIELD = {
		'fr':'Ajouter un fieldre',
		'en':'Add a field'
	}
	
	var BUTTON_ADD_FILTER = {
		'fr':'Ajouter un filtre',
		'en':'Add a filter'
	}
	
	var BUTTON_ADD_PARAM = {
		'fr':'Ajoutez un paramètre',
		'en':'Add a parameter'
	}
	
	var config_cal = {
		'fr':{
	   			MONTHS_LONG:["Janvier","F&eacute;vrier","Mars","Avril","Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","D&eacute;cembre"],
	   			WEEKDAYS_SHORT:[]
	   		},
		'en':null		
	}	


	var SAVE_WITHOUT_NAME ={
		'fr':'Le rapport doit avoir un nom',
		'en':'The report must be named'	
	}
	var SAVE_WITHOUT_FIELD = {
		'fr':'Aucun champ sélectionné',
		'en':'The report must have some fields'	
	}
	
	
	var OPERATORS = {
	'fr':{
   			CONTAINS:"contient le(s) mot(s)",
   			EQUALS:"est égal à",
   			NOT_EQUALS:"n'est pas égal à",
   			GREATER_THAN:"est supérieur à",
   			GREATER_THAN_OR_EQUAL:"est supérieur ou égal à",
   			LOWER_THAN:"est inférieur à",
   			LOWER_THAN_OR_EQUAL:"est inférieur ou égal à",
   			STARTSWITH:"contient un mot commençant par",
   			STRICTCONTAINS:"contient la phrase"
   		},
	'en':{
   			CONTAINS:"contains the word(s)",
   			EQUALS:"is equal to",
   			NOT_EQUALS:"is not equal to",
   			GREATER_THAN:"is greater than",
   			GREATER_THAN_OR_EQUAL:"is greater or equals",
   			LOWER_THAN:"is lower than",
   			LOWER_THAN_OR_EQUAL:"is lower or equals",
   			STARTSWITH:"contains a word starting with",
   			STRICTCONTAINS:"contains the sentence"
   		}	
	}

	var reportingPanel;	
	var reporting = new Array();
	var fields  = new Array();
	var EDIT = false;
	var DUPLICATE = false;
	/********************************************************************
	 * Action show reporting
	 ********************************************************************/
	
	 function displayReporting(){
	  	showReportingPanel();
	 }
	 
	 
	 function showReportingPanel() {
	 	var tabPanel = amalto.core.getTabPanel();
		if(tabPanel.getItem('reporting') == undefined){
		reportingPanel = new Ext.Panel({
				id: 'reporting',
				title: REPORTING[language],
				deferredRender: false,
				layout:'border',
				autoScroll: false,
				border: false,
				bodyBorder:false,
				//bodyborder: true,
				closable: true,
				items: 
				[	
					new Ext.FormPanel({
						id: 'select-reporting',
			    		//title: TITLE_SEARCH_PANEL[language],
			    		region: 'north',
						border:false,
						bodyBorder:false,
						autoScroll: true,	
						collapsible: true,
						header:true,
						bodyStyle:'padding:5px',
						height:200,		
						split:true,
						border: true,
						bodyborder: true,
						labelWidth:200,
						buttonAlign:'left',
						defaults: {labelSeparator:''},
						items:[
							new Ext.form.ComboBox({
								fieldLabel:REPORTING[language],
								id:'reportingSelect',
								store: new Ext.data.Store({
										proxy: new Ext.data.SimpleDWRProxy(ReportingInterface.getReportingsName),
							        	reader: new Ext.data.MapReader()
									}),
								displayField: 'value',
								valueField: 'key',
							  	loadingText:MSG_LOADING[language],
					         	mode:'remote',
					          	triggerAction:'all',
					          	editable:false,
					          	listeners:{
					          		'select' : function( combo, record, index ){
								                	var key = record.data.key;
								                	getReporting(key);
					          		}
					          	}	               
							}),
							{
								xtype:'textfield',
								fieldLabel:LABEL_DATAOBJECT[language],
								id:'typeReportingInfo',
								anchor:'90%',
								readOnly:true,
								style:'border-width:0; background-color:white;background-image:none'
							},
							{
								xtype:'textfield',
								fieldLabel:LABEL_FILTER[language],
								anchor:'90%',
								id:'reportingInfo',
								readOnly:true,
								style:'border-width:0; background-color:white;background-image:none'
							},
							{
								xtype:'textfield',
								fieldLabel:'Pivot',
								id:'pivot',
								anchor:'90%',
								readOnly:true,
								style:'border-width:0; background-color:white;background-image:none'
							}
						],
						buttons:[
							new Ext.Button({
								id:'displayButton',
						        text: BUTTON_DISPLAY[language],
								handler: getReportingContent,
								disabled:true
					    	}),
					    	new Ext.Button({
					    		id:'exportButton',
						        text: BUTTON_EXPORT[language],
								handler: exportReporting,
								disabled:true
					    	}),
					    	new Ext.Button({
					    		id:'deleteButton',
						        text: BUTTON_DELETE[language],
								handler: deleteReporting,
								disabled:true
					    	}),
					    	new Ext.Button({
					    		id:'editButton',
						        text: BUTTON_EDIT[language],
								handler: editReporting,
								disabled:true
					    	}),
					    	new Ext.Button({
						        text: NEW_REPORTING[language],
								handler: newReporting
					    	}),
					    	new Ext.Button({
					    		id:'duplicateButton',
						        text: DUPLICATE_REPORTING[language],
								handler: duplicateReporting,
								disabled:true
					    	})
						]
						
					}),
					new Ext.Panel({
						id: 'display-reporting',
			    		region: 'center',
			    		layout:'fit',
						border: false,
						//height:300,
						header:true,
						split:true,
						collapsible: false,
						bodyborder: true
					})					    
				]
			});			

		}		
		tabPanel.add(reportingPanel); 
		reportingPanel.show();
		reportingPanel.doLayout();
		amalto.core.doLayout();
	}
	
	function getReporting(reportingName){
		ReportingInterface.getReporting(getReportingCB,reportingName);
		Ext.getCmp('displayButton').setDisabled(false);
		Ext.getCmp('exportButton').setDisabled(false);
	}
	
	function getReportingCB(result){
		reporting = result;
		html2 = "";
		for(var i=0;i<reporting.filters.length;i++){
			html2+=reporting.filters[i].field+" "+reporting.filters[i].operator+" "+reporting.filters[i].value+" - ";
		}
		var cmp = Ext.getCmp('reportingInfo');
		Ext.getCmp('reportingInfo').setValue(html2);
		
		Ext.getCmp('typeReportingInfo').setValue(reporting.concept);
		
		
		if(reporting.erasable==true ){ 
			Ext.getCmp('deleteButton').setDisabled(false);
			Ext.getCmp('editButton').setDisabled(false);
			Ext.getCmp('duplicateButton').setDisabled(false);
		}else{
			if(reporting.unErasableButDeleteable){
				Ext.getCmp('deleteButton').setDisabled(false);
			}else{
				Ext.getCmp('deleteButton').setDisabled(true);
			}
			Ext.getCmp('editButton').setDisabled(true);
			Ext.getCmp('duplicateButton').setDisabled(true);
		}
				
		var pivot=(reporting.pivotField!=null && reporting.pivotField!="null") ? reporting.pivotField:"";
		Ext.getCmp('pivot').setValue(pivot);
		for(var i=0; i<fields.length; i++) {
			Ext.getCmp('select-reporting').remove(fields[i]);
		}
		fields = new Array();
		for(var i=0;i<reporting.parameters.length;i++){
			if(reporting.parameters[i].type=="String"){
				var field = new Ext.form.TextField({
					fieldLabel:reporting.parameters[i].description,
					id:'parameter_'+i,
					width:250
				});
				Ext.getCmp('select-reporting').add(field);
				fields.push('parameter_'+i);
			}
			if(reporting.parameters[i].type=="Date"){
				var field = new Ext.form.DateField({
								id:'parameter_'+i,
								fieldLabel:reporting.parameters[i].description,
								allowBlank:false,
								format:"Ymd",
								useQuickTips:false,
								labelSeparator:'',
								width:170
							});
				Ext.getCmp('select-reporting').add(field);
				fields.push('parameter_'+i);
			}			
		}
		reportingPanel.doLayout();

		//DWRUtil.setValue('parametersSpan',html3);
		//setTimeout('getReportingContent()',100);
	}
	
	function displayReportingEnter(e){
		e.preventDefault();
		getReportingContent();
	}
	
	function getReportingContent(){
		var reportingName = DWRUtil.getValue('reportingSelect');
		if(reportingName!=LABEL_SELECT[language]){	    
			displayReportingGrid(reportingName,50);
		}
	}
	
	function displayReportingGrid(reportingName, pageSize) {
		var tmpFields = new Array;
	    for(var i=0; i<reporting.fields.length; i++) {
	    	var tmp = ""+i;
	    	tmpFields.push(tmp);
	    }
	   
	    var schema = {
		    root: 'reporting',
	    	totalProperty: 'TotalCount',
	    	id: 'nothing',
			fields: tmpFields
		};
	  	var myColumns = new Array();
		for(var k=0;k<reporting.fields.length;k++){
			myColumns[k]={header: reporting.fields[k].field, sortable: true,dataIndex: ""+k};
		}
		
		
		myParams = "";
		for(var k=0;k<reporting.parameters.length;k++){
			if (k >0)
			myParams += "###";
			myParams += DWRUtil.getValue('parameter_'+k);
		}
		
		//cm.defaultWidth = 200;
	   	
		var schema = {
		    root: 'reporting',
	    	totalProperty: 'TotalCount',
	    	id: 'nothing',
			fields: tmpFields
		};
		
		
		var store = new Ext.data.Store({
			    proxy: new Ext.data.HttpProxy({
		        	url: '/reporting/secure/ReportingRemotePaging'
		        }),
		       // sortInfo:{field: _viewItems2.keys[0], direction: "ASC"},
		        baseParams:{reportingName:reportingName,params:myParams},
		        reader: new Ext.data.JsonReader(schema),
		        remoteSort: true
		    });
		    

		
		var grid = new Ext.grid.GridPanel({
			//renderTo:'items-list',
			id:'reporting-grid',
		    store: store,
		    //height:200,
		    //autoHeight:true,
		    autoScroll:true,
		    columns: myColumns,
		    border:false,
			enableColumnMove:true,
			//forceFit:true,
		    viewConfig: {
		    	autoFill:true,
		        forceFit: false
		    },
			tbar: new Ext.PagingToolbar({
		        pageSize: parseInt(pageSize),
		        store: store,
		        displayInfo: true,
		        displayMsg: LABEL_DISPLAYING[language]+' {0} - {1} '+LABEL_OF[language]+' {2}',
		        emptyMsg: "No items to display",
		        items:[ 
		        	new Ext.Toolbar.Separator(),
		        	new Ext.Toolbar.TextItem(LABEL_LINES_PER_PAGE[language]+" : "),
		        	new Ext.form.TextField({
						id:'lineMaxReporting',
						value:pageSize,
						width:30,
						listeners: {
				                	'specialkey': function(a, e) {
							            if(e.getKey() == e.ENTER) {
					                		var lineMax = DWRUtil.getValue('lineMaxReporting');
											if(lineMax==null || lineMax=="") 
												lineMax=50;
											displayReportingGrid(reportingName, lineMax);
							            } 
									}
				                }
		        })/*,
		        	new Ext.Toolbar.Spacer(),
		        	 new Ext.Button({
		                text: 'Display',
		                handler: function(){
		                		var lineMax = DWRUtil.getValue('lineMaxReporting');
								if(lineMax==null || lineMax=="") 
									lineMax=15;
								displayReportingGrid(reportingName, pageSize);
							}
		            })*/]
		    })
		});
		
		layoutCenterPanel = Ext.getCmp('display-reporting');
		if(Ext.get('reporting-grid')!=undefined) {
			layoutCenterPanel.remove('reporting-grid');
		}		
		layoutCenterPanel.add(grid);
		reportingPanel.doLayout();  
		amalto.core.doLayout();
		grid.setHeight(layoutCenterPanel.getInnerHeight());
		
		store.load({params:{start:0, limit:pageSize,reportingName: reportingName}});
		   
	   amalto.core.ready('');   
	}   
	
	
	/************************************************************
	                EXPORT REPORTING , DELETE REPORTING
	************************************************************/
	
	function exportReporting(){
		var reportingName = DWRUtil.getValue('reportingSelect');
		
		myParams = "";
		
		for(var k=0;k<reporting.parameters.length;k++){
				
			if (k >0) {
				//myParams += "###";
				//myParams += "_";
				myParams += "%23%23%23";
			}
			myParams += DWRUtil.getValue('parameter_'+k);
		}
		
		
		if(reportingName!=LABEL_SELECT[language]){		
			reportingName = escape(reportingName);
			window.location.href="/reporting/secure/ReportingExportServlet?reportingName="+reportingName+"&params="+myParams;	
		}
	}
	
	function deleteReporting(){
		var reportingName = DWRUtil.getValue('reportingSelect');
		if(reportingName!=LABEL_SELECT[language]){
			var msg = MSG_CONFIRM_DELETE_REPORTING[language]+reportingName+"?";
			Ext.Msg.confirm("confirm",msg,function re(en){
				if(en=="yes"){
				ReportingInterface.deleteReporting(deleteReportingCB,reportingName);
			}});
		}
	}
	
	function deleteReportingCB(){
		DWRUtil.setValue('reportingInfo',"");
		ReportingInterface.getReportingsName();
		Ext.getCmp('reportingSelect').setValue('');
		Ext.getCmp('reportingSelect').store.reload();
	}
	
	
	
	/************************************************************
	                NEW REPORTING 
	************************************************************/
	
	var newReportingPanel;
	
	function editReporting(){
		newReporting(true,false, false);
	}
	
	function duplicateReporting() {
		newReporting(true, false, true);
	}
	
	function newReporting(edit,isEdit, isDuplicate){
		var readonly = "";
		if(!isEdit && !isDuplicate)
			readonly = 'readonly="true" ';
			
		if(edit!=undefined)	EDIT= edit;
		DUPLICATE = isDuplicate;
		var tabPanel = amalto.core.getTabPanel();
		amalto.core.working();

		if(tabPanel.getItem('newReportingDiv') == undefined){
		/*	if(grid1) grid1.destroy(true);
			if(grid2) grid2.destroy(true);
			if(filterGrid2) filterGrid2.destroy(true);*/
			//update the div structure
			var html = 
				'	<div id="chooseReportingName" style="display:inline;width:400;float:left">'+
				'	<div class="left">'+LABEL_NAME[language]+' :</div>'+
				'	<div class="leftField"><input type="text" ' + readonly +
				'	id="reportingName" /></div>'+
				'	<br/><br/>'+
				'	<div class="left">'+LABEL_SHARE_REPORTING[language]+' :</div>'+
				'	<div class="leftField"><input style="text-align:left" type="checkbox" id="sharedCheckBox" /></div>'+
				'	<br/><br/>'+
				'	<div class="left">'+LABEL_DATAOBJECT[language]+' :</div>'+
				'	<div  class="leftField"><select id="businessConceptsList" onChange="amalto.reporting.Reporting.getTranslation();">'+
				'		<option>'+MSG_LOADING[language]+'</option>'+
				'	</select>'+
				'	</div>'+
				'	</div>'+
				'	<br/>'+
				''+
				'<br style="clear: both;"/><br/= style="clear: both;"/>	'+
				'<div id="chooseDisplayFields" style="display:none;">'+
				'	<div class="left">'+LABEL_FIELDS[language]+' :</div>'+
				'	<div id="ddgrid1" style="text-align:left;border: 1px solid #c3daf9;float: left;	width:250px;height:225px;" class="ygrid-mso"></div>'+
				'	<div class="leftField">&nbsp;</div>'+
				'	<div id="ddgrid2" style="text-align:left;border: 1px solid #c3daf9;float: left;	width:250px;height:225px;" class="ygrid-mso"></div>'+
				'	<input type="button" id="addFieldBTN" value="'+BUTTON_ADD_FIELD[language]+'" onclick="amalto.reporting.Reporting.addFieldToTable();"/>' +				
				'	</div>'+
				'	<br style="clear: both;"/><br style="clear: both;"/>'+
				' <div id="chooseFilterFields" style="display:none;">	'+
				'	<div class="left">'+LABEL_FILTER[language]+' :</div>'+
				'	<div id="ddfilterGrid2"  style="text-align:left;border: 1px solid #c3daf9;float: left;	width:508px;height:100px;" class="ygrid-mso"></div>' +
				'	<div class="leftField">&nbsp;</div>' +
				'	<input type="button" value="'+BUTTON_ADD_FILTER[language]+'" onclick="amalto.reporting.Reporting.addFilterToTable();"/>' +
				'<div id="dateContainer"/> ' +
				'	</div>' +
				'' +
				'<br style="clear: both;"/><br style="clear: both;"/>' +
				'<div id="choosePivot" style="display:none;">	' +
				'	<div class="left">'+LABEL_PIVOT[language]+' :</div>' +
				'	<select id="pivotList"  style="text-align:leftField" onkeypress="DWRUtil.onReturn(event, amalto.reporting.Reporting.saveReporting);">' +
				'<option value=""></option></select>' +
				'</div>' +
				'<br/><br style="clear: both;"/>	' +
				'<div id="chooseStoredProcedure" style="display:none;">	' +
				'	<div class="left">'+LABEL_STORED_PROCEDURE[language]+' :</div>' +
				'	<select id="storedProcedureList"  style="text-align:leftField" onChange="amalto.reporting.Reporting.getTranslation2();"><option value=""></option></select>' +
				'</div>' +
				'   <br/><br style="clear: both;"/>	' +
				' <div id="chooseStoredProcedureParams" style="display:none;">	'+
				'	<div class="left">'+LABEL_STORED_PROCEDURE_PARAM[language]+' :</div>'+
				'	<div id="SPPEditGrid"  style="text-align:left;border: 1px solid #c3daf9;float: left; width:508px;height:100px;" class="ygrid-mso"></div>' +
				'	<div class="leftField">&nbsp;</div>' +
				'	<input type="button" value="'+BUTTON_ADD_PARAM[language]+'" onclick="amalto.reporting.Reporting.addParamToTable();"/>' +
				' </div>' +
				'' +
				'<br style="clear: both;"/><br style="clear: both;"/>' +
				'<div id="finish" style="display:inline;">' +
				'	<div class="leftField">' +
				'		<input type="button" value="'+BUTTON_SAVE[language]+'"  onclick="amalto.reporting.Reporting.saveReporting();" /></div>' +
				'</div>' +
				'			' +
			    '<div style="display:none"><select id="fieldListGrid"></select></div>';
			    
				
			var contentPanel = new Ext.Panel({
				id:'newReportingDiv', 
				title:(edit==true?reporting.reportingName:NEW_REPORTING[language] ), 
				layout:'fit',
				bodyStyle:'padding:5px',
				autoScroll:true,
				html:html,
				closable:true
			});	
	
		}		
		tabPanel.add(contentPanel); 
		contentPanel.show();
		contentPanel.doLayout();
		amalto.core.doLayout();					


		//b2box & mdm compliant
		ReportingInterface.getBusinessConcepts(language,function(results){					
			if (Ext.get('businessConceptsList')!=null){
				DWRUtil.removeAllOptions('businessConceptsList');
				DWRUtil.addOptions('businessConceptsList',[LABEL_SELECT[language]]);
				DWRUtil.addOptions('businessConceptsList',results);
				amalto.core.ready();
			}			
			if(edit==true || isDuplicate == true){
				DWRUtil.setValue('reportingName',reporting.reportingName);
				DWRUtil.setValue('sharedCheckBox',reporting.shared);
				DWRUtil.setValue('businessConceptsList',reporting.concept);
//				DWRUtil.setValue('pivotList',reporting.pivotXpath);
				getTranslation();	
			}
		});
		

	}
	
	
	var xPathToLabel;
	function getTranslation(){ 
		amalto.core.working('');    
		$('chooseDisplayFields').style.display="inline";
		$('chooseFilterFields').style.display="inline";
		$('choosePivot').style.display="inline";
		$('chooseStoredProcedure').style.display="inline";
		$('addFieldBTN').disabled = true;
	    var businessConcept = DWRUtil.getValue('businessConceptsList');
	    
	    ReportingInterface.getXpathToLabel(businessConcept,language, function(result){
	    	//alert(DWRUtil.toDescriptiveString(result,2));
	    	xPathToLabel = result;
	    	DWRUtil.removeAllOptions('fieldListGrid');
			DWRUtil.addOptions( 'fieldListGrid', result);
			//DWRUtil.removeAllOptions('pivotList');
			DWRUtil.addOptions( 'pivotList', result);
			if(reporting.pivotXpath)
				DWRUtil.setValue('pivotList',xPathToLabel[reporting.pivotXpath]);
				
		    ReportingInterface.getStoredProcedurePKs(function(result){
				DWRUtil.removeAllOptions('storedProcedureList');
				DWRUtil.addOptions( 'storedProcedureList',[LABEL_SELECT[language]]);
				DWRUtil.addOptions( 'storedProcedureList', result);
				
				if(reporting.storedProcedure){
					DWRUtil.setValue('storedProcedureList',reporting.storedProcedure);
					$('chooseStoredProcedureParams').style.display="inline";
					$('addFieldBTN').disabled = false;
				    fixParametersGrid(reporting.storedProcedure);
				}
	       });	
	    
	    	createGrid(result);	
	    	amalto.core.ready();
	    	
	    });
	    
	    
	    
	    
	    
	    if(EDIT==true){
	    	
	    	
	    }
	}
	
	function getTranslation2(){
		var storedProcedure = DWRUtil.getValue('storedProcedureList');
	    if(storedProcedure==[LABEL_SELECT[language]])storedProcedure='';
	    
	    if(storedProcedure==''){
	    	$('chooseStoredProcedureParams').style.display="none";
	    	$('addFieldBTN').disabled = true;
	    }else{
	    	$('chooseStoredProcedureParams').style.display="inline";
	    	$('addFieldBTN').disabled = false;
	    	fixParametersGrid(storedProcedure);
	    }
		
	}
	
	/**
	 * fix the paramGrid with default parameters.
	 */
	function fixParametersGrid(storedProcedure) {
		ReportingInterface.getNumberOfParamters(storedProcedure, function(result){
    		paramGrid1.getStore().removeAll();
	    	paramGrid1.getStore().commitChanges();
	    	
	    	for(var i = 0; i < result; i++) {
	    		addParamToTable(true, i);
	    	}
	    });
	}
	
	function saveReporting(){
	    var documentType = DWRUtil.getValue('businessConceptsList');
	    var fields = new Array();
	    var filters = new Array();
	    var parameters = new Array();
	    var pivot = DWRUtil.getValue('pivotList');
	    var storedProcedure = DWRUtil.getValue('storedProcedureList');
	    if(storedProcedure==[LABEL_SELECT[language]])storedProcedure='';
	
	    if(DWRUtil.getValue('reportingName')==""){
	    	Ext.Msg.alert("alert",SAVE_WITHOUT_NAME[language]);
	    	return;
	    }
	    
	    var store = grid2.getStore();
	    store.commitChanges();
	    for(var i=0; i<store.getCount(); i++) {
	    	fields[i]={
	            xpath:store.getAt(i).get('element'),
	            field:store.getAt(i).get('element').substring(store.getAt(i).get('element').indexOf("/") + 1)
	        };
	    }
	   // alert(grid1.getStore().getTotalCount());
	    //log(DWRUtil.toDescriptiveString(grid1.getStore(),5));
	    //alert(DWRUtil.toDescriptiveString(grid1.getStore(),1));
	    
		//log(DWRUtil.toDescriptiveString(grid2.getStore(),5));
   	    //alert(grid2.getStore().getTotalCount());
	    //alert(DWRUtil.toDescriptiveString(grid2.getStore(),1));

	     if(fields.length==0){
	    	Ext.Msg.alert("alert",SAVE_WITHOUT_FIELD[language]);
	    	return;
	    }
	    var store = filterGrid2.getStore();
	    for(var i=0; i<store.getCount(); i++) {	
	        if(store.getAt(i).get('field')!=""){
	        	filters[i]={
	            xpath:store.getAt(i).get('field'),
	            operator:store.getAt(i).get('operator'),
	            value:store.getAt(i).get('value')
	        	}    
	        }    
	        //alert(filterGrid2.dataModel.getRowId);
	        //alert(DWRUtil.toDescriptiveString(filters[i]));
	    }
	    
	    if(storedProcedure!=null&&storedProcedure!=''){
	    	var pstore = paramGrid1.getStore();
	    	for(var i=0; i<pstore.getCount(); i++) {	
		        if(pstore.getAt(i).get('Name')!=""){
		        	parameters[i]={
		            name:pstore.getAt(i).get('Name'),
		            description:pstore.getAt(i).get('Description'),
		            type:pstore.getAt(i).get('Type')
		        	}    
		        }
	        }
	    }
	    
	    var reporting={
	        reportingName:DWRUtil.getValue('reportingName'),
	        concept:DWRUtil.getValue('businessConceptsList'),
	        shared:DWRUtil.getValue('sharedCheckBox'),
	        fields:fields,
	        filters:filters,
	        pivotXpath:pivot,
	        storedProcedure:storedProcedure,
	        parameters:parameters
	    };
	    //alert(DWRUtil.toDescriptiveString(reporting,2));
	    ReportingInterface.saveReporting(saveReportingCB,reporting,documentType,language, DUPLICATE ? false : EDIT);
	 
	} 
	
	function saveReportingCB(result){
		if(result=="ERROR1") 
			Ext.Msg.alert("alert",ALERT_EXISTING_REPORTING[language]);
		else{		
			var tabPanel = amalto.core.getTabPanel();
			tabPanel.remove('newReportingDiv');
			if(Ext.get('reporting')) ReportingInterface.getReportingsName();
			Ext.getCmp('reportingSelect').setValue('');
			Ext.getCmp('reportingSelect').store.reload();
		}			
	}
	
	function keyEnter(evenement){
		var touche = window.event ? evenement.keyCode : evenement.which;
		Ext.Msg.alert("alert",touche);
	
	}
	
	function keyListenerNewReporting(evenement){
		var touche = window.event ? evenement.keyCode : evenement.which;
		//alert(touche);
		if(touche==0) cancelNewReporting();	
		if(touche==13) saveReporting();	
	}
	
	/**
	 * add the xpathtolabel.
	 */
	function addFiledToXPath(xPath, label) {
        xPathToLabel[xPath] = label;
	}
	
    /**
     * ok listener
     */
	function okAddFieldToTable(businessConcept, label) {
    	var businessConcept = DWRUtil.getValue('editConceptCombo');
 		var label = DWRUtil.getValue('fieldsCombo');

 		if(label == ''){
			alert("Field Name can't be empty! ");
			return;
 		}
		
		var xpath = businessConcept != "" ? businessConcept + "/" + label : label;
		ReportingInterface.addFieldsToXpath(xpath, label);
		addFiledToXPath(xpath, label);
		
		var store = grid2.getStore();
		var recordType = Ext.data.Record.create([
   		  {name: "element", type: "string"}]);

		orec = new recordType();
        orec.data = {element:xpath};
        orec.data.newRecord = true;
        orec.commit();
           
    	grid2.getStore().add(orec);
		
		grid2.getStore().commitChanges();
		grid2.getView().refresh();
		
		editFieldsWindow.hide();
		editFieldsWindow.destroy();
	}
	
	/**
	 * add a field to grid2
	 */
	function addFieldToTable() {
		this.fieldsStore = new Ext.data.Store({
			proxy: new Ext.data.SimpleDWRProxy(ReportingInterface.getXpathToLabel),
        	reader: new Ext.data.MapReader()
		});
		
		function onBeforeloadXpathStore(){
		    var conceptValue = DWRUtil.getValue('editConceptCombo');
	        Ext.apply(this.fieldsStore.baseParams,{
	          regex: conceptValue
	        });
		}
		
		this.fieldsStore.on('beforeload', 
            function(button, event) {
				onBeforeloadXpathStore();
			}.createDelegate(this)
		);
		
	    var editFieldPanel = new Ext.form.FormPanel({
	         baseCls: 'x-plain',
		     labelAlign: 'left',     
		     xtype : "form",
		     items : [{
		     	name : "editConceptCombo",
				fieldLabel : "Data Object",
				xtype : "combo",
				store: new Ext.data.Store({
					proxy: new Ext.data.SimpleDWRProxy(ReportingInterface.getBusinessConcepts),
		        	reader: new Ext.data.MapReader()
				}),
				displayField: 'value',
				valueField: 'key',
				mode:'remote',
				triggerAction:'all',
				editable:true,
				listeners : {
		    	    select : function(combo, record, index) {
		    	 		fieldsStore.reload();
		    	 	}.createDelegate(this)
		     	}
			 },{
				name : "fieldsCombo",
				fieldLabel : "Available fields",
				xtype : "combo",
				store: fieldsStore,
				displayField: 'value',
				valueField: 'key',
				mode:'remote',
				triggerAction:'all',
				editable:true,
				allowBlank : false
			 }]
		});
        
	    this.editFieldsWindow = new Ext.Window({
	        title: "Fields Editor",
	        width: 320,
	        height:200,
	        layout: 'fit',
	        plain:true,
	        bodyStyle:'padding:5px;',
	        buttonAlign:'center',
	        items: editFieldPanel,
	        modal:true,
		    buttons: [{
	            text: "OK",
	            handler: function(){
		    		okAddFieldToTable();
				}.createDelegate(this)
	        },{
	        	text: "CANCEL",
	        	handler: function() {
		    		this.editFieldsWindow.hide();
					this.editFieldsWindow.destroy();
	        	}.createDelegate(this)
	        }]
	    });
	
	    this.editFieldsWindow.show();
	}
	
	function addFilterToTable(){ 
		var recordType = Ext.data.Record.create([
		  //{name: "id", type: "int"},
		  {name: "field", type: "string"},
		  {name: "operator", type: "string"},
		  {name: "value", type: "string"}
		  ]);
		  
		orec = new recordType();
        orec.data = {
        	field:"",
        	operator:"",
        	value:"",
        	trash:''
        };
        orec.data.newRecord = true;
        orec.commit();
        filterGrid2.stopEditing();
        filterGrid2.getStore().add( orec);
        filterGrid2.startEditing(0, 0);
            
	    var rowTemplate = new Array();
		rowTemplate[0]="";
		rowTemplate[1]="";
		rowTemplate[2]="";
		rowTemplate[3]="<img src='img/genericUI/trash.gif'  border=\"0\" />";
	    filterGrid2.getStore().commitChanges();
	}
	
	function addParamToTable(defaultParmeter, index){ 
		var recordType = Ext.data.Record.create([
		  //{name: "id", type: "int"},
		  {name: "Name", type: "string"},
		  {name: "Description", type: "string"},
		  {name: "Type", type: "string"}
		  ]);
		  
		var nrec = new recordType();
		
		if(defaultParmeter) {
			nrec.data = {
		        	Name:"parameter" + index,
		        	Description:"",
		        	Type:"String",
		        	trash:''
		        };
		}
		else {
	        nrec.data = {
	        	Name:"",
	        	Description:"",
	        	Type:"",
	        	trash:''
	        };
		}
		
        nrec.data.newRecord = true;
        nrec.commit();
        paramGrid1.stopEditing();
        paramGrid1.getStore().add(nrec);
        paramGrid1.startEditing(0, 0);
            
	    var rowTemplate = new Array();
		rowTemplate[0]="";
		rowTemplate[1]="";
		rowTemplate[2]="";
		rowTemplate[3]="<img src='img/genericUI/trash.gif'  border=\"0\" />";
	    paramGrid1.getStore().commitChanges();
	}
	
	
	var grid1, grid2;
	var filterGrid1, filterGrid2, paramGrid1;
	

	
	
	function createGrid(){
		 Ext.QuickTips.init();

	    // shared reader
	    var reader = new Ext.data.ArrayReader({}, [
	        {name: 'element'}
	    ]);
	
		var myData = new Array();
		/*for(var i=0; i<result.length; i++) {
			myData.push([result[i]]);
		}*/
		//alert(DWRUtil.toDescriptiveString(xPathToLabel,3));
		for (var i in xPathToLabel) {
			//alert('key is: ' + i + ', value is: ' + xPathToLabel[i]);
			myData.push([i]);
		}
		
		//delete entries already in the right grid
		//k: add by lym mdm:0009719 
		if(EDIT==true){
			var k=0;
			for(var i=0; i<myData.length+k; i++) {
				for(var j=0; j<reporting.fields.length; j++) {
					if(myData[i-k][0]==reporting.fields[j].xpath){
						myData.splice(i-k,1);
						k++;
						break;
					}
				}	
			}
	    }
	    
		//alert(DWRUtil.toDescriptiveString(myData,2));
	    // create the data store
	    var dsRelac1 = new Ext.data.Store({
	        reader: reader
	        //sortInfo:{field: 'element', direction: "ASC"}
	    });
	    dsRelac1.loadData(myData);
	
		function renderField(val){
	    	return xPathToLabel[val];
	    }
	    	
	    // create the Grid1
	    grid1 = new Ext.grid.GridPanel({
	        applyTo: 'ddgrid1',
	        title:LABEL_AVAILABLE_FIELDS[language],
	        store: dsRelac1,
	        columns: [
	            {id:'element',header: "Element",  sortable: false, dataIndex: 'element', renderer: renderField}
	        ],
	        viewConfig: {
	            forceFit: true
	        },
	        autoScroll :true,	
	        width:250,
	        height:225,
	        border:false,
	        // enable drag and Drop
	        enableDragDrop: true,
	        dropConfig: {
	            appendOnly:true
	        },
	        ddGroup: "GridDD"
	       // style:'	border: 1px solid #c3daf9;float: left;	width:250px;height:225px;'
	    });
	
	    grid1.render();
	
		var myData2 = new Array();	
	    if(EDIT==true){
		    for(var i=0; i<reporting.fields.length; i++) {
				myData2.push([reporting.fields[i].xpath]);
			}
	    }

	    // create the data store
	    var dsRelac2 = new Ext.data.SimpleStore({
	        fields: [
	            {name: 'element'}
	        ]
	    });
	    dsRelac2.loadData(myData2);
	
	
	    // create the Grid2
	    grid2 = new Ext.grid.GridPanel({
	        applyTo: 'ddgrid2',
	        title:LABEL_FIELDS[language],
	        store: dsRelac2,
	        columns: [
 				{id:'element',header: "Element",  sortable: true, dataIndex: 'element', renderer: renderField}	
	        ],
	        stripeRows: true,
	        viewConfig: {
	        forceFit: true
	        },
			width:250,
			height:225,
			border:false,
	        enableDragDrop: true,
	        dropConfig: {
	            appendOnly:true
	        },
	        ddGroup: "GridDD2"
	       //style:'	border: 1px solid #c3daf9;float: left;		width:250px;height:225px;'
	    });
	
	    grid2.render();
	
	    // Grid 1 -> Grid 2
	    //this is a grid DragDrop
	    var ddrow = new Ext.dd.DropTarget(grid2.getEl(), {
	        ddGroup: "GridDD",  // Data come from
	        // copy:true,
	        notifyDrop : function(dd, e, data){
	            var sm = grid1.getSelectionModel();
	
	            // get the rows of the gridRelac1 we have selected
	            //var rows = sm.getSelections();
				//var cindex = dd.getDragData(e).rowIndex;
				//alert(DWRUtil.toDescriptiveString(dd.getDragData,1));
	            // put this data into gridRelac2
	            //dsRelac2.insert(cindex, data.selections);
	            
	            for(var i=0; i<data.selections.length; i++) {
	            	dsRelac1.remove(data.selections[i]);
	            	dsRelac2.add(data.selections[i]);
	            }
	            dsRelac2.commitChanges();
	           //log("dsRelac2.getCount()  "+dsRelac2.getCount());
	           //log("dsRelac.getCount()  "+dsRelac1.getCount());
	            // dsRelac2.add(data.selections);
	            /*
	            for(i = 0; i < rows.length; i++) {
	                    rowData = dsRelac1.getById(rows[i].id);
	                   // if(!this.copy)
	                   //     dsRelac1.remove(dsRelac1.getById(rows[i].id));
	                    dsRelac2.insert(cindex,rowData);
	            };
	            
	            */
	            grid2.getView().refresh();
	        }
	    });
	    
	    // Grid 2 -> Grid 1
	    //this is a grid DragDrop
	    var ddrow2 = new Ext.dd.DropTarget(grid1.getEl(), {
	        ddGroup: "GridDD2",  // Data come from
	        // copy:true,
	        notifyDrop : function(dd, e, data){
	            var sm = grid2.getSelectionModel();
	
	            // get the rows of the gridRelac1 we have selected
	            //var rows = sm.getSelections();
				//var cindex = dd.getDragData(e).rowIndex;
	            // put this data into gridRelac2
	           // dsRelac1.insert(cindex, data.selections);
	            for(var i=0; i<data.selections.length; i++) {
	            	dsRelac2.remove(data.selections[i]);
	            	dsRelac1.add(data.selections[i]);
	            }
	            
	            // dsRelac2.add(data.selections);*/
	            /*
	            for(i = 0; i < rows.length; i++) {
	                    rowData = dsRelac1.getById(rows[i].id);
	                   // if(!this.copy)
	                   //     dsRelac1.remove(dsRelac1.getById(rows[i].id));
	                    dsRelac2.insert(cindex,rowData);
	            };
	            
	            */
   	           //log("dsRelac2.getCount()  "+dsRelac2.getCount());
	           //log("dsRelac.getCount()  "+dsRelac1.getCount());
	            grid2.getView().refresh();
	        }
	    });
	    
	    
	
	    /*  i don't dnow whether this part is useful
	    var ddGrid = new Ext.grid.GridDragZone(gridRelac1, {
	        containerScroll: true,
	        ddGroup: "GridDD"
	    });
	    */
	
	     // Grid 2 <-> Grid 2
	 /*   var ddrowTarget = new Ext.dd.DropTarget(grid1.container, {
	        ddGroup: "GridDD",
	        // copy:false,
	        notifyDrop : function(dd, e, data){
	            var sm = grid2.getSelectionModel();
	            var rows = sm.getSelections();
	            var cindex = dd.getDragData(e).rowIndex;    // Here is need
	
	            // dsRelac2.remove(this, data.selections);
	            // dsRelac2.insert(cindex,data.selections);
	
	            for(i = 0; i < rows.length; i++) {
	                rowData = dsRelac2.getById(rows[i].id);
	                // if(!this.copy)
	                    dsRelac2.remove(dsRelac2.getById(rows[i].id));
	                dsRelac2.insert(cindex, rowData);
	            };
	
	            grid2.getView().refresh();
	
	            // put the cursor focus on the row of the gridRelac2 which we just draged
	            grid2.getSelectionModel().selectRow(cindex); 
	

	        }
	    });*/

        var ddrow = new Ext.dd.DropTarget(grid2.container, {
            ddGroup : 'GridDD2',
            copy:false,
            notifyDrop : function(dd, e, data){
                var sm=grid2.getSelectionModel();
                var rows=sm.getSelections();

                var cindex=dd.getDragData(e).rowIndex;
                if (typeof cindex != "undefined") {
                    for (i = 0; i < rows.length; i++) {
                        rowData=dsRelac2.getById(rows[i].id);
                        if(!this.copy) {
                            dsRelac2.remove(dsRelac2.getById(rows[i].id));
                            dsRelac2.insert(cindex,rowData);
                        }
                    }
                    sm.selectRecords(rows);
                } 
            }
        });
    
	
		var myData = new Array();
		/*for(var i=0; i<result.length; i++) {
			var row = new Array();
			row.push(result[i], xPathToLabel[result[i]]);
			myData.push(row);
		}*/
		for (var i in xPathToLabel) {
			//alert('key is: ' + i + ', value is: ' + xPathToLabel[i]);
			var row = new Array();
			row.push(i, xPathToLabel[i]);
			myData.push(row);
		}
	    
		var fieldStore = new Ext.data.SimpleStore({
		  fields: [ "value", "text" ],
		  data: myData
		});
		
		var comboField = new Ext.form.ComboBox({
		  store: fieldStore,
		  valueField: "value",
		  displayField: "text",
          mode:'local',
          triggerAction:'all',
          editable:false
		});
		
		
		var operatorStore = new Ext.data.SimpleStore({
		  fields: [ "value", "text" ],
		  data: [
        		['EQUALS',OPERATORS[language]['EQUALS']],
        		['CONTAINS',OPERATORS[language]['CONTAINS']],
        		['GREATER_THAN',OPERATORS[language]['GREATER_THAN']],
		        ['GREATER_THAN_OR_EQUAL',OPERATORS[language]['GREATER_THAN_OR_EQUAL']],
		        ['LOWER_THAN',OPERATORS[language]['LOWER_THAN']],
		        ['LOWER_THAN_OR_EQUAL',OPERATORS[language]['LOWER_THAN_OR_EQUAL']],
		        ['NOT_EQUALS',OPERATORS[language]['NOT_EQUALS']],
		        ['STARTSWITH',OPERATORS[language]['STARTSWITH']],
		        ['STRICTCONTAINS',OPERATORS[language]['STRICTCONTAINS']]
		  ]
		});
		
		var comboOperator = new Ext.form.ComboBox({
		  store: operatorStore,
		  valueField: "value",
		  displayField: "text",
          mode:'local',
          triggerAction:'all',
          editable:false
		});

	    
	   	function renderField(val){
	   		return  (typeof xPathToLabel[val] != "undefined") ? 
	   		   xPathToLabel[val] : val.substring(val.indexOf("/") + 1);
	    }
	    
	    function renderOperator(val){
			if(val==null) return OPERATOR_UNDEFINED[language];
			return OPERATORS[language][val];
		}
		
	    var dsFilter = new Ext.data.SimpleStore({
	        fields: [
	            {name: 'field'},
	            {name: 'operator'},
	            {name: 'value'},
	            {name: 'trash'}
	        ]
	    });
	    
	    var filterData =new Array();
	    if(EDIT==true){
	    	for(var i=0; i<reporting.filters.length; i++) {
	    		filterData.push([reporting.filters[i].xpath,reporting.filters[i].operator,reporting.filters[i].value]);
	    	}
	    }
	    
	    dsFilter.loadData(filterData);
	    
	    function trash(){
			return "<img src='img/genericUI/trash.gif' border=\"0\" />";
		}
		
		
	    var myColumnsFilter = [
		    {
		    	header: HEAD_FIELD[language], 
		    	dataIndex:'field', 
                editor: comboField,
    			renderer: Ext.ux.comboBoxRenderer(comboField),
		    	width: 160
		    },
		    {
		    	header: HEAD_OPERATOR[language], 
		    	dataIndex:'operator',    
                editor: comboOperator,
			renderer: Ext.ux.comboBoxRenderer(comboOperator),
	            width:150
	         },
		    {header: HEAD_VALUE[language], dataIndex:'value', editor: new Ext.form.TextField({allowBlank: false,blankText:FIELD_REQUIRED[language]}),  width:150},
		    {header: BUTTON_DELETE[language], dataIndex:'trash', width:30,renderer:trash}
		];
	    filterGrid2 = new Ext.grid.EditorGridPanel({
	        applyTo: 'ddfilterGrid2',
	        title:LABEL_FILTER[language],
	       	store: dsFilter,
	        columns: myColumnsFilter,
	        stripeRows: true,
	        width:508,
	        height:100,
	        border:false,
	        viewConfig: {
	        	forceFit: true
	        },
	        clicksToEdit:1,
	        /*tbar:new Ext.Toolbar.Button({
	        	text:BUTTON_ADD_FILTER[language],
	        	handler:addFilterToTable
	        }),*/
			listeners: {
	        	cellclick: function(g, rowIndex,  columnIndex, e){
								if (columnIndex ==3) {
									dsFilter.remove(dsFilter.getAt(rowIndex));
								}
								//alert(g.getStore().getAt(rowIndex).get('field'));
						    	if (columnIndex ==2 && g.getStore().getAt(rowIndex).get('field').indexOf("Date")>0){
						    		//getFilterCalendar(rowIndex);
						    	}
						    	
									
	        	}
	        }
        // style:'	border: 1px solid #c3daf9;float: left;	width:508px;height:100px;'
	    });
  	filterGrid2.render();
  	
  	var dsParam = new Ext.data.SimpleStore({
	        fields: [
	            {name: 'Name'},
	            {name: 'Description'},
	            {name: 'Type'},
	            {name: 'trash'}
	        ]
	    });
	    
	 var paramData =new Array();
	    if(EDIT==true){
	    	for(var i=0; i<reporting.parameters.length; i++) {
	    		paramData.push([reporting.parameters[i].name,reporting.parameters[i].description,reporting.parameters[i].type]);
	    	}
	    }
	    
	dsParam.loadData(paramData);
  	
  	var typeStore = new Ext.data.SimpleStore({
		  fields: [ "value", "text" ],
		  data: [
        		['String','String'],
        		['Date','Date']
		  ]
		});
		
	var comboType = new Ext.form.ComboBox({
		  store: typeStore,
		  valueField: "value",
		  displayField: "text",
          mode:'local',
          triggerAction:'all',
          editable:true
		});
  	
  	var myColumnsParam = [
		    
		    {header: 'Name', dataIndex:'Name', editor: new Ext.form.TextField({allowBlank: false,blankText:FIELD_REQUIRED[language]}),  width:150},
		    {header: 'Description', dataIndex:'Description', editor: new Ext.form.TextField({allowBlank: false,blankText:FIELD_REQUIRED[language]}),  width:150},
		    {
		    	header: 'Type', 
		    	dataIndex:'Type',    
                editor: comboType,
			    renderer: Ext.ux.comboBoxRenderer(comboType),
	            width:120
	         },
		    {header: BUTTON_DELETE[language], dataIndex:'trash', width:30,renderer:trash}
		];
  	
  	paramGrid1 = new Ext.grid.EditorGridPanel({
	        applyTo: 'SPPEditGrid',
	       	store: dsParam,
	        columns: myColumnsParam,
	        stripeRows: true,
	        width:508,
	        height:100,
	        border:false,
	        viewConfig: {
	        	forceFit: true
	        },
	        clicksToEdit:1,
			listeners: {
	        	cellclick: function(g, rowIndex,  columnIndex, e){
								if (columnIndex ==3) {
									dsParam.remove(dsParam.getAt(rowIndex));
								}				
	        	}
	        }
            // style:'	border: 1px solid #c3daf9;float: left;	width:508px;height:100px;'
	    });
  	paramGrid1.render();

	}


	var panel;
	function getFilterCalendar(rowIndex){
		var d=new Date(); 
		d.setTime(Date.parse(filterGrid2.getStore().getAt(rowIndex).get('value')));
		
		var dateField = new Ext.form.DateField({
								applyTo:'dateContainer',
								allowBlank:false,
								format:"Ymd",
								useQuickTips:false,
								labelSeparator:'',
								width:170
								//value: [new Date(2008,4,8),new Date(2008,4,11)]
							});
		amalto.core.doLayout();
		return;
		
		if(panel) panel.destroy();
		var theBody = "<div id=\"dateContainer2\"></div>";
	
	    // Instantiate the Dialog
		panel = new YAHOO.widget.Panel(
		         "dateContainer", 
				 {   
				 //y:500,			 
				   visible: true,
				   draggable: true,
				   close: true,
				   zIndex:10000,
				   context:["ddfilterGrid2","tr","tl"]//align top right corner of calendar with top left corner of filtergrid
				 } );
	    panel.setBody(theBody);  
	    panel.render(document.body);  	
	    
		/*if(ON_CAL==1)	{$('dateContainer').style.display="none";ON_CAL=0;return;}
		if(ON_CAL==0)	{$('dateContainer').style.display="block";ON_CAL=1;}*/
		
		$('dateContainer').style.display="block";
		var myCal = new YAHOO.widget.Calendar(
			"calEl",
	   		"dateContainer2",
	   		config_cal[language]
	   		);
	   	if(d!="Invalid Date")	
	   		myCal.cfg.setProperty("pagedate",d);
	   	var fnMyHandler = function(){
	   		var month = myCal.getSelectedDates()[0].getMonth()+1;
	   		if(month<10) month = "0"+month;
	   		var day = myCal.getSelectedDates()[0].getDate();
	   		if(day<10) day="0"+day;
	 		var text = myCal.getSelectedDates()[0].getFullYear()+"-"+month+"-"+day;
	   		try{filterGrid2.dataModel.setValueAt(text,rowIndex,2);}
	
	   		catch(error){Ext.Msg.alert("alert",error);}
	   		//DWRUtil.setValue(input,text);
	   		$('dateContainer').style.display="none";
	   		//ON_CAL=0;
	   	};
		myCal.selectEvent.subscribe(fnMyHandler);
		myCal.render();	
	}
	
	 	return {
			init: function() {displayReporting(); },
			getReporting: function(){getReporting();},
			getReportingContent: function(){getReportingContent();},
			exportReporting: function(){exportReporting()},
			deleteReporting: function(){deleteReporting()},
			newReporting: function(){newReporting()},
			editReporting: function(){editReporting()},
			duplicateReporting: function(){duplicateReporting()},
			getTranslation: function(){getTranslation();},
			getTranslation2: function(){getTranslation2();},
			addFilterToTable: function(){addFilterToTable();},
			addParamToTable: function(){addParamToTable();},
			saveReporting: function(){saveReporting();},
			addFieldToTable: function(){addFieldToTable();}
	 	}
	
}();