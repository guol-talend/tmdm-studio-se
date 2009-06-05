package com.amalto.service.loggingsmtp.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.naming.NamingException;
import javax.resource.cci.Connection;
import javax.resource.cci.Interaction;
import javax.resource.cci.MappedRecord;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sun.misc.BASE64Encoder;

import com.amalto.connector.jca.InteractionSpecImpl;
import com.amalto.connector.jca.RecordFactoryImpl;
import com.amalto.core.ejb.ItemPOJO;
import com.amalto.core.ejb.ItemPOJOPK;
import com.amalto.core.ejb.ServiceCtrlBean;
import com.amalto.core.ejb.local.ItemCtrl2Local;
import com.amalto.core.objects.routing.v2.ejb.RoutingRuleExpressionPOJO;
import com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJO;
import com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK;
import com.amalto.core.objects.transformers.v2.ejb.TransformerV2CtrlBean;
import com.amalto.core.objects.transformers.v2.ejb.TransformerV2POJO;
import com.amalto.core.objects.transformers.v2.ejb.TransformerV2POJOPK;
import com.amalto.core.objects.transformers.v2.util.TransformerContext;
import com.amalto.core.objects.transformers.v2.util.TransformerProcessStep;
import com.amalto.core.objects.transformers.v2.util.TransformerVariablesMapping;
import com.amalto.core.util.Util;
import com.amalto.core.util.XtentisException;



/**
 * @author bgrieder, jfeltesse
 * 
 * @ejb.bean name="LoggingSmtp"
 *           display-name="Logging Smtp"
 *           description="Logging Smtp"
 * 		  local-jndi-name = "amalto/local/service/loggingsmtp"
 *           type="Stateless"
 *           view-type="local"
 * 
 * @ejb.remote-facade
 * 
 * @ejb.permission
 * 	view-type = "remote"
 * 	role-name = "administration"
 * @ejb.permission
 * 	view-type = "local"
 * 	unchecked = "true"
 * 
 * 
 * 
 */
public class LoggingSmtpBean extends ServiceCtrlBean  implements SessionBean {
	
	private static final long serialVersionUID = 7149969138524906956L;
	
	private static final String transformerName = "Logging Events to Smtp HTML Transformer";
	private static final String xsltName = "SmtpLoggingEvents.xslt";
	private static final String routingRuleName = "Logging events to loggingSMTP service";
	
	private boolean configurationLoaded = false;
	private boolean serviceStarted = false;
	
	private String host;
	private Integer port;
	private String username;
	private String password;
	private Boolean auth;
	private String permanentbcc;
	private String logfilename;
	
    
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public String getJNDIName() throws XtentisException {		
		return "amalto/local/service/loggingsmtp";
	}
	
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public String getDescription(String twoLetterLanguageCode) throws XtentisException {
		if ("fr".matches(twoLetterLanguageCode.toLowerCase()))
			return "Le service smtp";
		return "The smtp service";
	}
	
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public String getStatus() throws XtentisException {
		// N/A
		return "N/A";
	}
	
	
	/**
	 * Creates the necessary transformer
	 */
	private void createTransformer() throws XtentisException {
		
		org.apache.log4j.Logger.getLogger(this.getClass()).debug("createTransformer() ");
				
		TransformerV2POJO trans= null;
		
		try {
			trans = Util.getTransformerV2CtrlLocal().existsTransformer(new TransformerV2POJOPK(transformerName));
		} catch (NamingException e) {
			String err = "Unable to find the transformers controller: "+e.getMessage();
			org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
			throw new XtentisException(err);
		} catch (CreateException e) {
			String err = "Unable to create the transformers controller: "+e.getMessage();
			org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
			throw new XtentisException(err);
		}
		
		//If the transformer is null, create it programatically
		if (trans==null) {
			//Read the XSLT
			String xslt;
			try {
				InputStream is = LoggingSmtpBean.class.getResourceAsStream(xsltName);
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				xslt = "";
				String line;
				while ((line=br.readLine())!=null) xslt+=line+"\n";
			} catch (UnsupportedEncodingException e) {
				String err = "Unable to read '"+xsltName+"'. UTF-8 is not supported on this machione";
				org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
				throw new XtentisException(err);
			} catch (IOException e) {
				String err = "Unable to read '"+xsltName+"' :"+e.getMessage();
				org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
				throw new XtentisException(err);
			}

	        //Create the Transformer
	        trans = new TransformerV2POJO();
	        trans.setName(transformerName);
	        trans.setDescription(transformerName);
	        TransformerProcessStep step = new TransformerProcessStep();
	        step.setDescription("XSLT Transformation");
	        step.setDisabled(false);
	        step.setPluginJNDI("amalto/local/transformer/plugin/xslt");
	        step.setParameters(xslt);
	        
	        TransformerVariablesMapping inputMapping = new TransformerVariablesMapping();
	        inputMapping.setPipelineVariable(TransformerV2CtrlBean.DEFAULT_VARIABLE);
	        inputMapping.setPluginVariable("xml");
	        step.setInputMappings(new ArrayList<TransformerVariablesMapping>(Arrays.asList(new TransformerVariablesMapping[]{inputMapping})));
	        
	        TransformerVariablesMapping outputMapping = new TransformerVariablesMapping();
	        outputMapping.setPipelineVariable("body");
	        outputMapping.setPluginVariable("text");
	        step.setOutputMappings(new ArrayList<TransformerVariablesMapping>(Arrays.asList(new TransformerVariablesMapping[]{outputMapping})));
	        trans.setProcessSteps(new ArrayList<TransformerProcessStep>(Arrays.asList(new TransformerProcessStep[]{step})));
	        
	        try {
				Util.getTransformerV2CtrlLocal().putTransformer(trans);
			} catch (NamingException e) {
				String err = "Unable to find the transformers controller: "+e.getMessage();
				org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
				throw new XtentisException(err);
			} catch (CreateException e) {
				String err = "Unable to create the '"+transformerName+"': "+e.getMessage();
				org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
				throw new XtentisException(err);
			}
		}
		
	}
	
	private void createRoutingRule() throws XtentisException {
		
		try {
			if (Util.getRoutingRuleCtrlLocal().existsRoutingRule(new RoutingRulePOJOPK(routingRuleName))!=null)
				return;
			
			Util.getRoutingRuleCtrlLocal().putRoutingRule(
				new RoutingRulePOJO(
					routingRuleName,
					"Sends all logging events to the logging stmp service",
					new ArrayList<RoutingRuleExpressionPOJO>(),
					false,
					"logging_event",
					getJNDIName(),
					"from=b2box@customer.com&to=support@amalto.com&subjectprefix=b2box Logging Event "
				)
			);
		} catch (NamingException e) {
			String err = "Unable to instantiate the routing rules controller: "+e.getMessage();
			org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
			throw new XtentisException(err);
		} catch (CreateException e) {
			String err = "Unable to create the 'Logging events to loggingSMTP service' routing rule: "+e.getMessage();
			org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
			throw new XtentisException(err);
		}
		
	}
	
	
	
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public void start() throws XtentisException {
		org.apache.log4j.Logger.getLogger(this.getClass()).debug("start() ");

		try {
		
			if (!configurationLoaded) getConfiguration(null);
			
			// Create the transformer, once
			createTransformer();
			
			//Create an ad-hoc routing rule
			createRoutingRule();
			
			serviceStarted = true;
			
			return;
			
		}
		catch (Exception e) {
			//No Exception rethrown to avoid infinite looping
			String err=
				"An error occured starting the Logging Smtp service: "+e.getClass().getName()+": "+e.getLocalizedMessage()+
				". No additional error will be thrown to avoid infinite error looping";
			org.apache.log4j.Logger.getLogger(this.getClass()).info("ERROR SYSTRACE: "+err,e);
			throw new XtentisException(err);
	    }
	}


	
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public void stop() throws XtentisException {
		// N/A
	}


	
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public Serializable receiveFromOutbound(HashMap<String, Serializable> map) throws XtentisException {
		throw new XtentisException("The loggingSMTP service should not receive anything from connectors");
	}



	
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public String receiveFromInbound(ItemPOJOPK itemPK, String routingOrderID, String parameters) throws com.amalto.core.util.XtentisException {
		org.apache.log4j.Logger.getLogger(this.getClass()).debug("receiveFromInbound() : sending message...");
		
		if (! "logging_event".equals(itemPK.getConceptName())) {
			throw new XtentisException("The smtp logging event adapter cannot process objects of the type "+itemPK.getConceptName());
		}
		
		if (!serviceStarted) {
			org.apache.log4j.Logger.getLogger(this.getClass()).debug("receiveFromInbound() : service not started. starting...");
			start();
		}
		//nor error thrown so we check the status
		if (!serviceStarted) {
			return "Error: Service not started";
		}
		
		if (!configurationLoaded) getConfiguration(null);
		
		Connection conx = null;
		
		try {
			
			String logFileName = null;
			String from = null;
			String to = null;
			String cc = null;
			String bcc = null;
			String subjectPrefix = null;
			String mails = null;
			
			
			// Parse parameters
			if (parameters != null) {
				String kvs[] = parameters.split("&");
				if (kvs!=null) {
					for (int i = 0; i < kvs.length; i++) {
						String[] kv = kvs[i].split("=");
						String key = kv[0].trim().toLowerCase();
						
						if (("logfilename".equals(key)) && (kv.length == 2)) {
							logFileName = kv[1];
						}
						if (("from".equals(key)) && (kv.length == 2)) {
							from = kv[1];
						}
						if (("to".equals(key)) && (kv.length == 2)) {
							to = kv[1];
						}
						if (("cc".equals(key)) && (kv.length == 2)) {
							cc = kv[1];
						}
						if (("bcc".equals(key)) && (kv.length == 2)) {
							bcc = kv[1];
						}
						if (("subjectprefix".equals(key)) && (kv.length == 2)) {
							subjectPrefix = kv[1];
						}
					}
				}
			}
				
			
			
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<outboundmails><email>");
			addXmlElement(sb, "from", from, true);
			addXmlElement(sb, "to", to, true);
			addXmlElement(sb, "cc", cc, true);
			
			if (permanentbcc != null) {
				if (bcc != null) bcc = bcc + ", " + permanentbcc;
				else bcc = permanentbcc;
			}
			addXmlElement(sb, "bcc", bcc, true);
			
			org.apache.log4j.Logger.getLogger(this.getClass()).debug("receiveFromInbound() : concept name: "+itemPK.getConceptName());
			
			// Send Logging events

			ItemCtrl2Local itemCtrl = Util.getItemCtrl2Local();
			ItemPOJO itemPOJO = itemCtrl.getItem(itemPK); 
			Element rootElement = itemPOJO.getProjection();
			String msg = StringEscapeUtils.unescapeXml(Util.getFirstTextNode(rootElement, "message"));
			String subject = StringUtils.abbreviate(msg, 100);
			addXmlElement(sb, "subject", (subjectPrefix == null ? "" : subjectPrefix+" : ") + (subject == null ? "" : subject), true);
							
			sb.append("<part><mime-type>text/html</mime-type><charset>UTF-8</charset>");
			
			//Extract the event as nicely formatted HTML
			//TransformerPluginContext context = getItemCtrlLocal().extractUsingTransformer(itemPK, new TransformerPOJOPK(transformerName));
			//HashMap<String, TypedContent> content = (HashMap<String, TypedContent>)context.get("com.amalto.core.pipeline");
			
			TransformerContext context = new TransformerContext(new TransformerV2POJOPK(transformerName));
			context.put("loggingsmtpready",Boolean.FALSE);
			
			ItemPOJO item = Util.getItemCtrl2Local().getItem(itemPK);
			Util.getTransformerV2CtrlLocal().executeUntilDone(
					context,
					new com.amalto.core.objects.transformers.v2.util.TypedContent(
						item.getProjectionAsString().getBytes("utf-8"),
						"text/xml; charset=utf-8"
					)
			);
			
			
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			int c;
			
			
			byte[] bodybytes = context.getFromPipeline("body").getContentBytes();
			addXmlElement(sb, "body", (new BASE64Encoder()).encode(bodybytes), false);
			
			sb.append("</part>");
			
			sb.append("</email></outboundmails>");
			mails = sb.toString();
			
			//Get Connection to the Smtp Sender
			conx  = getConnection("java:jca/xtentis/connector/smtp");
			Interaction interaction = conx.createInteraction();
	    	InteractionSpecImpl interactionSpec = new InteractionSpecImpl();
	    	
	    	//Create the Record
			MappedRecord recordIn = new RecordFactoryImpl().createMappedRecord(RecordFactoryImpl.RECORD_IN);
	    	HashMap<String,Serializable> params = new HashMap<String,Serializable>();
	    	params.put("host", host);
	    	params.put("port", port);
	    	params.put("username", username);
	    	params.put("password", password);
	    	params.put("mails", mails);
	    	params.put("auth", auth);
	    	params.put("logfilename", logFileName);
	    	recordIn.put(RecordFactoryImpl.PARAMS_HASHMAP_IN, params);
	    	
	    	//Process the post
			interactionSpec.setFunctionName(InteractionSpecImpl.FUNCTION_PUSH);
			MappedRecord result = (MappedRecord) interaction.execute(interactionSpec, recordIn);
			
			String statusCode = (String) result.get(RecordFactoryImpl.STATUS_CODE_OUT);
			String statusMsg = (String)((HashMap<String,Serializable>)result.get(RecordFactoryImpl.PARAMS_HASHMAP_OUT)).get("message");
			
			//parse the result
			if (!"OK".equals(statusCode)) {
				String err = "Smtp Service: could not send the logging  event: "+statusMsg;
				org.apache.log4j.Logger.getLogger(this.getClass()).debug(err);
				throw new XtentisException(err);
			} else {
				org.apache.log4j.Logger.getLogger(this.getClass()).debug("receiveFromInbound() : status="+statusCode+", message="+statusMsg);
				// Remove the item if it's been sent successfully
				Util.getItemCtrl2Local().deleteItem(itemPK);
				org.apache.log4j.Logger.getLogger(this.getClass()).debug("receiveFromInbound() : removed itemPK '"+itemPK.getIds()[0]+"'");
			}
			
			try {conx.close();} catch (Exception ex) { }
			return "";
			
		} catch (XtentisException e) {
			try {conx.close();} catch (Exception ex) { }
			throw (e);
		} catch (Exception e) {
			//No exception thrown to avoid infinite loop
			String err=
				"Logging Smtp: an error occured: "+e.getClass().getName()+": "+e.getLocalizedMessage()+
				". No additional error will be thrown to avoid infinite error looping.";
			org.apache.log4j.Logger.getLogger(this.getClass()).info("ERROR SYSTRACE: "+err,e);
			try {conx.close();} catch (Exception ex) { }
			throw new XtentisException(err);
	    }
		
	}


    
    private String getDefaultConfiguration() {
    	return
    		"<configuration>"+
    		"	<host>localhost</host>"+
    		"	<port>25</port>"+
    		"	<username></username>"+
    		"	<password></password>"+
    		"	<permanentbcc></permanentbcc>"+
    		"	<logfilename></logfilename>"+
			"</configuration>";
    }
    

    
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
    public String getConfiguration(String optionalParameters) throws XtentisException {
    	try {
    		String configuration = loadConfiguration();
    		if (configuration == null) {
    			org.apache.log4j.Logger.getLogger(this.getClass()).debug("getConfiguration() : configuration is null, fall back to default one");
    			configuration = getDefaultConfiguration();
    		}
    		
    		Document d = Util.parse(configuration);
    		
    		// Parsing & checking of mandatory parameters
    		String tmphost = StringEscapeUtils.unescapeXml(Util.getFirstTextNode(d.getDocumentElement(), "host"));
    		if  (tmphost == null) throw new XtentisException("Host required");
    		else this.host = tmphost;
    		
    		String tmpport = Util.getFirstTextNode(d.getDocumentElement(), "port");
    		if  (tmpport == null) throw new XtentisException("Port number required");
    		else this.port = new Integer(tmpport);
    		if (this.port.intValue() < 1) throw new XtentisException("Invalid port number");
    		
    		
    		// If username is null then authentication is set to false
    		String usertmp = StringEscapeUtils.unescapeXml(Util.getFirstTextNode(d.getDocumentElement(), "username"));
    		if (usertmp == null) auth = new Boolean(false);
    		else auth = new Boolean(true);
    		username = usertmp;
    		
    		// Parsing of not so important parameters
    		password = StringEscapeUtils.unescapeXml(Util.getFirstTextNode(d.getDocumentElement(), "password"));
    		permanentbcc = Util.getFirstTextNode(d.getDocumentElement(), "permanentbcc");
    		logfilename = StringEscapeUtils.unescapeXml(Util.getFirstTextNode(d.getDocumentElement(), "logfilename"));
    		
    		configurationLoaded = true;
    		
    		org.apache.log4j.Logger.getLogger(this.getClass()).debug("getConfiguration() : Configuration String: "+configuration);
    		org.apache.log4j.Logger.getLogger(this.getClass()).debug("getConfiguration() : Variables: host="+host+", port="+port+", "+
    				"username="+username+", password="+(password == null ? "null" : "(hidden)")+", permanentbcc="+permanentbcc+", logfilename="+logfilename);
    		
    		return configuration;
        } catch (Exception e) {
			//No Exception rethrown to avoid infinite looping
        	String err=
				"An error occured reading the configuration of the Logging Smtp service: "+e.getClass().getName()+": "+e.getLocalizedMessage()+
				". No additional error will be thrown to avoid infinite error looping.";
			org.apache.log4j.Logger.getLogger(this.getClass()).info("ERROR SYSTRACE: "+err,e);
			throw new XtentisException(err);
	    }	
    }


    
    /**
     * @throws EJBException
     * 
     * @ejb.interface-method view-type = "local"
     * @ejb.facade-method 
     */
	public void putConfiguration(String configuration) throws XtentisException {
		configurationLoaded = false;
		super.putConfiguration(configuration);
	}

	
	
	
	
	
	private void addXmlElement(StringBuffer target, String name, String value, boolean escapeXML) {
		
		if (value == null) value = "";
		else if (escapeXML) value = StringEscapeUtils.escapeXml(value);
		
		target.append("<"+name+">"+value+"</"+name+">");
	}
	
	
    
}