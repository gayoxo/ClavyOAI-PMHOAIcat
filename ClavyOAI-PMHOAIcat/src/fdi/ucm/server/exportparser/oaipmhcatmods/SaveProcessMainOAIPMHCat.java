package fdi.ucm.server.exportparser.oaipmhcatmods;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import fdi.ucm.server.exportparser.common.OAIPMHCatXMLSynonim;
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.CompleteLogAndUpdates;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteStructure;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Clase que parsea una coleccion del sistema en el formato Oda.
 * @author Joaquin Gayoso-Cabada
 *
 */
public class SaveProcessMainOAIPMHCat {
	

	
	protected static final String ERROR_DE_CREACION_POR_FALTA_DE_OV_DUEÑO_EN_LOS_DOCUMENTOS_FILE = "Error de creacion por falta de OV dueño en los documentos File";
	protected static final String ERROR_DE_CREACION_POR_FALTA_DE_FILE_FISICO_EN_LOS_DOCUMENTOS_FILE = "Error de creacion por falta de File Fisico en los documentos File";
	protected static final String EXISTE_ERROR_EN_EL_PARSEADO_DE_LAS_ITERACIONES = "Existe error en el parseado de las iteraciones.";
	protected static final String ERROR_DE_CREACION_POR_FALTA_DE_META_OBJETO_VIRTUAL = "Error de creacion por falta de Meta Objeto virtual.";
	protected CompleteCollection toOda;
	protected HashMap<CompleteElementType, Integer> ModeloOda;
	protected HashMap<CompleteDocuments, Integer> tabla;
	protected CompleteLogAndUpdates ColectionLog;
	protected CompleteTextElementType IDOV;
	private HashMap<Long, ArrayList<String>> Tabla_DC;
	private HashMap<Long, HashMap<String, String>> Tabla_MODSValue;
	private OAIPMHCatXMLSynonim Sinonimos;
	private boolean Controlado;
	private static final char separator='~';
	private static final char separator_comodin='¬';
			

	/**
	 * Constructor por defecto
	 * @param cL 
	 * @param pathFile 
	 * @param controlado 
	 * @param Coleccion coleccion a insertar en oda.
	 */
	public SaveProcessMainOAIPMHCat(CompleteCollection coleccion, CompleteLogAndUpdates cL, String pathFile, boolean controlado){
		toOda=coleccion;
		ColectionLog=cL;
		Controlado=controlado;
		
		Sinonimos=new OAIPMHCatXMLSynonim();
		
		if (pathFile!=null&&!pathFile.isEmpty())
			Sinonimos.processXML(pathFile,ColectionLog);
	}


	/**
	 * Procesa la clase
	 * @throws ImportRuntimeException en caso de errores varios. Consultar el error en {@link CompleteImportRuntimeException}
	 */
	public void preocess() throws CompleteImportRuntimeException {
		

		
		Tabla_DC=new HashMap<Long, ArrayList<String>>();
		Tabla_MODSValue=new HashMap<Long, HashMap<String,String>>();
		
		findTablaDC();
		findTablaMODS();
		
		processOV();
		
		
	}


	private void findTablaMODS() {
		for (CompleteGrammar gramarInspect : toOda.getMetamodelGrammar()) {
		if (!StaticFuctionsOAIPMHCat.isIgnored(gramarInspect))
			findTablaMODS(gramarInspect.getSons());
	}
		
	}



	private void findTablaMODS(List<CompleteStructure> listaHijos) {
		for (CompleteStructure struictureInspect : listaHijos) {
			if (struictureInspect instanceof CompleteElementType)
			{
				HashMap<String,String> OAI_Category_Value=StaticFuctionsOAIPMHCat.getCategoriasOAIPMHMODSValues((CompleteElementType)struictureInspect);
				Tabla_MODSValue.put(struictureInspect.getClavilenoid(), OAI_Category_Value);
			}
			
			findTablaMODS(struictureInspect.getSons());
				
		}
		
	}






	private void processOV() {
		for (CompleteDocuments documentInspect : toOda.getEstructuras()) {
			if (!StaticFuctionsOAIPMHCat.isIgnored(documentInspect))
				processDocument(documentInspect);
		}
		
	}



	private void processDocument(CompleteDocuments documentInspect) {
		
		String LocalIdentificador;
		
		StringBuffer SBtitle=new StringBuffer();
		StringBuffer SBcreator=new StringBuffer();
		StringBuffer SBcontributor=new StringBuffer();
		StringBuffer SBdate=new StringBuffer();
		StringBuffer SBlanguage=new StringBuffer();
		StringBuffer SBpublisher=new StringBuffer();
		StringBuffer SBrelation=new StringBuffer();
		StringBuffer SBformat=new StringBuffer();
		StringBuffer SBtype=new StringBuffer();
		StringBuffer SBdescription=new StringBuffer();
		StringBuffer SBidentifier=new StringBuffer();
		StringBuffer SBsource=new StringBuffer();
		StringBuffer SBsubject=new StringBuffer();
		StringBuffer SBcoverage=new StringBuffer();
		StringBuffer SBrights=new StringBuffer();
		StringBuffer SBdatecreated=new StringBuffer();
		
		StringBuffer SBmodstitleInfo=new StringBuffer();
		StringBuffer SBmodsname=new StringBuffer();
		StringBuffer SBmodstypeofresource=new StringBuffer();
		StringBuffer SBmodsgenre=new StringBuffer();
		StringBuffer SBmodsorigininfo=new StringBuffer();
		StringBuffer SBmodslanguage=new StringBuffer();
		StringBuffer SBmodsphysicaldescription=new StringBuffer();
		StringBuffer SBmodsabstract=new StringBuffer();
		StringBuffer SBmodstableofcontents=new StringBuffer();
		StringBuffer SBmodstargetaudience=new StringBuffer();
		StringBuffer SBmodsnote=new StringBuffer();
		StringBuffer SBmodssubject=new StringBuffer();
		StringBuffer SBmodsclassification=new StringBuffer();
		StringBuffer SBmodsrelateditem=new StringBuffer();
		StringBuffer SBmodsidentifier=new StringBuffer();
		StringBuffer SBmodslocation=new StringBuffer();
		StringBuffer SBmodsaccesscondition=new StringBuffer();
		StringBuffer SBmodspart=new StringBuffer();
		StringBuffer SBmodsextension=new StringBuffer();
		StringBuffer SBmodsrecordinfo=new StringBuffer();
		
		
		SBdescription.append(separatorClean(documentInspect.getDescriptionText()));
		SBmodsabstract.append(setValue(separatorClean(documentInspect.getDescriptionText()),"<abstract>"));
		SBmodsrecordinfo.append("<recordinfo><recordContentSource authorityURI=\"http://cellproject.net/authorities/source-database\">Ciberia</recordContentSource></recordinfo>");
		
		
		
		LocalIdentificador=Long.toString(documentInspect.getClavilenoid());
		
		
		for (CompleteElement elementInspect : documentInspect.getDescription()) {
			
			if (elementInspect instanceof CompleteTextElement)
			{
			ArrayList<String> ListaDC = Tabla_DC.get(elementInspect.getHastype().getClavilenoid());
			String Valor = separatorClean(((CompleteTextElement) elementInspect).getValue());
			if (Valor!=null&&!Valor.trim().isEmpty()&&ListaDC!=null)
			{
			for (String string : ListaDC) {
				
				switch (string.toLowerCase()) {
				case "dc:title":
					if (SBtitle.length()>0)
						SBtitle.append(separator);
					SBtitle.append(Valor);
					break;
				case "dc:creator":
					if (SBcreator.length()>0)
						SBcreator.append(separator);
					SBcreator.append(Valor);
					break;
				case "dc:contributor":
					if (SBcontributor.length()>0)
						SBcontributor.append(separator);
					SBcontributor.append(Valor);
					break;
				case "dc:date":
					if (SBdate.length()>0)
						SBdate.append(separator);
					SBdate.append(Valor);
					break;
				case "dc:language":
					
					
					String[] SS=Valor.split("[,|;|:|.| |/]");
					for (String stringori2 : SS) {
						
						if (!stringori2.trim().isEmpty())
						{
						
							String string2=stringori2;
							if (ListaDC.contains("language"))
							{
								string2 = Sinonimos.getLanguage().get(stringori2.trim().toLowerCase());
								if (string2==null)
									if (Controlado)
										{
										string2 = Sinonimos.getLanguage().get("otros");
										if (string2==null)
										{
											ColectionLog.getLogLines().add("Language ->"+stringori2+" Unknown and is controled");	
											string2="";
										}
										string2="";
										}
									else
										string2=stringori2;
							}
							
						
							if (!string2.trim().isEmpty())
							{
						if (SBlanguage.length()>0)
							SBlanguage.append(separator);	
						
						SBlanguage.append(string2);
							}	
						}
					}
					
					
					break;
				case "dc:publisher":
					if (SBpublisher.length()>0)
						SBpublisher.append(separator);
					SBpublisher.append(Valor);
					break;
				case "dc:relation":
					if (SBrelation.length()>0)
						SBrelation.append(separator);
					SBrelation.append(Valor);
					break;
				case "dc:format":
					
					String[] SS3=Valor.split("[,|;|:|.|/]");
					for (String stringori2 : SS3) {
						
						String string2=stringori2;
						if (ListaDC.contains("publication-type"))
						{
							string2 = Sinonimos.getPublication().get(stringori2.trim().toLowerCase());
							if (string2==null)
								if (Controlado)
									{
									string2 = Sinonimos.getPublication().get("otros");
									if (string2==null)
									{
										ColectionLog.getLogLines().add("Format ->"+stringori2+" Unknown and is controled");	
										string2="";
									}
									}
								else
									string2=stringori2;
						}
						
						if (ListaDC.contains("procedural-modality"))
						{
							string2 = Sinonimos.getProcedural().get(stringori2.trim().toLowerCase());
							if (string2==null)
								if (Controlado)
									{
									string2 = Sinonimos.getProcedural().get("otros");
									if (string2==null)
									{
										ColectionLog.getLogLines().add("Format ->"+stringori2+" Unknown and is controled");	
										string2="";
									}
									}
								else
									string2=stringori2;
						}
						
						if (ListaDC.contains("mechanism"))
						{
							string2 = Sinonimos.getMechanism().get(stringori2.trim().toLowerCase());
							if (string2==null)
								if (Controlado)
									{
									string2 = Sinonimos.getMechanism().get("otros");
									if (string2==null)
									{
										ColectionLog.getLogLines().add("Format ->"+stringori2+" Unknown and is controled");	
										string2="";
									}
									}
								else
									string2=stringori2;
						}
						
						if (ListaDC.contains("formats"))
						{
							string2 = Sinonimos.getFormatos().get(stringori2.trim().toLowerCase());
							if (string2==null)
								if (Controlado)
									{
									string2 = Sinonimos.getFormatos().get("otros");
									if (string2==null)
									{
										ColectionLog.getLogLines().add("Format ->"+stringori2+" Unknown and is controled");	
										string2="";
									}
									}
								else
									string2=stringori2;
						}
						
						if (!string2.trim().isEmpty())
						{
					if (SBformat.length()>0)
						SBformat.append(separator);
					SBformat.append(string2);
						}
					}
					
					break;
				case "dc:type":
					
					String[] SS2=Valor.split("[,|;|:|.|/]");
					for (String string2 : SS2) {
					
						if (!string2.trim().isEmpty())
						{
						
						if (SBtype.length()>0)
						SBtype.append(separator);
					SBtype.append(string2);
						}
					}
					
					break;
				case "dc:description":
					if (SBdescription.length()>0)
						SBdescription.append(separator);
					SBdescription.append(Valor);
					break;
				case "dc:identifier":
					if (SBidentifier.length()>0)
						SBidentifier.append(separator);
					SBidentifier.append(Valor);
					
					if (Valor.length()<255)
						LocalIdentificador=Valor;
					
					break;
				case "dc:source":
					if (SBsource.length()>0)
						SBsource.append(separator);
					SBsource.append(Valor);
					break;
				case "dc:subject":
					if (SBsubject.length()>0)
						SBsubject.append(separator);
					SBsubject.append(Valor);
					break;
				case "dc:coverage":
					if (SBcoverage.length()>0)
						SBcoverage.append(separator);
					SBcoverage.append(Valor);
					break;
				case "dc:rights":
					if (SBrights.length()>0)
						SBrights.append(separator);
					SBrights.append(Valor);
					break;

				case "dc:datecreated":
					if (SBdatecreated.length()>0)
						SBdatecreated.append(separator);
					SBdatecreated.append(Valor);
					break;
				default:
					break;
				}
				
			}	
			}
			HashMap<String, String> ListaMODSVALUES = Tabla_MODSValue.get(elementInspect.getHastype().getClavilenoid());
			if (Valor!=null&&!Valor.trim().isEmpty()&&ListaMODSVALUES!=null)
			{
			for (Entry<String, String> Enstring : ListaMODSVALUES.entrySet()) {
				
				String string=Enstring.getKey();
				
				switch (string.toLowerCase()) {
				case "<titleinfo>":
				case "<titleinfo><title>":
				case "<titleinfo><subtitle>":
				case "<titleinfo><partnumber>":
				case "<titleinfo><partname>":
				case "<titleinfo><nonsort>":
					if (SBmodstitleInfo.length()>0)
						SBmodstitleInfo.append(separator);
					SBmodstitleInfo.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<name>":
				case "<name><namepart>":
				case "<name><displayform>":
				case "<name><affiliation>":
				case "<name><role>":
				case "<name><description>":
				case "<name><etal>":
					if (SBmodsname.length()>0)
						SBmodsname.append(separator);
					SBmodsname.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<typeofresource>":
					String[] SStr=Valor.split("[,|;|:|.|/]");
					for (String string2 : SStr) {
						if (!string2.trim().isEmpty())
						{
						
					if (SBmodstypeofresource.length()>0)
						SBmodstypeofresource.append(separator);
					SBmodstypeofresource.append(setValue(string2,Enstring.getValue()));
						}
					}
					break;
				case "<genre>":
					String[] SSgen=Valor.split("[,|;|:|.]");
					for (String stringori2 : SSgen) {
						
						String string2=stringori2;
						if (ListaMODSVALUES.containsKey("procedural-modality"))
						{
							string2 = Sinonimos.getProcedural().get(stringori2.trim().toLowerCase());
							if (string2==null)
								if (Controlado)
									{
									string2 = Sinonimos.getProcedural().get("otros");
									if (string2==null)
									{
										ColectionLog.getLogLines().add("genre ->"+stringori2+" Unknown and is controled");	
										string2="";
									}
									}
							
								else
									string2=stringori2;
						}

						
						
						if (!string2.trim().isEmpty())
						{
						
					if (SBmodsgenre.length()>0)
						SBmodsgenre.append(separator);
					SBmodsgenre.append(setValue(string2,Enstring.getValue()));
						}

					}
					break;
				case "<origininfo>":
				case "<origininfo><place>":
				case "<origininfo><publisher>":
				case "<origininfo><dateissued>":
				case "<origininfo><datecreated>":
				case "<origininfo><datecaptured>":
				case "<origininfo><datevalid>":
				case "<origininfo><datemodified>":
				case "<origininfo><copyrightdate>":
				case "<origininfo><dateother>":
				case "<origininfo><edition>":
				case "<origininfo><issuance>":
				case "<origininfo><frequency>":
					if (SBmodsorigininfo.length()>0)
						SBmodsorigininfo.append(separator);
					SBmodsorigininfo.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<language>":
				case "<language><languageterm>":
				case "<language><scriptterm>":
					String[] SSlang=Valor.split("[,|;|:|.| |/]");
					for (String stringori2 : SSlang) {
						if (!stringori2.trim().isEmpty())
						{

							String string2=stringori2;
							if (ListaMODSVALUES.containsKey("language"))
							{
								string2 = Sinonimos.getLanguage().get(stringori2.trim().toLowerCase());
								if (string2==null)
									if (Controlado)
										{
										string2 = Sinonimos.getLanguage().get("otros");
										if (string2==null)
										{
											ColectionLog.getLogLines().add("Language ->"+stringori2+" Unknown and is controled");	
											string2="";
										}
										}
								
									else
										string2=stringori2;
							}

						
							if (!string2.trim().isEmpty())
							{
					if (SBmodslanguage.length()>0)
						SBmodslanguage.append(separator);
					SBmodslanguage.append(setValue(string2,Enstring.getValue()));
							}
						
						}
							
					}
					break;
				case "<physicaldescription>":
				case "<physicaldescription><form>":
				case "<physicaldescription><reformattingquality>":
				case "<physicaldescription><internetmediatype>":
				case "<physicaldescription><extent>":
				case "<physicaldescription><digitalorigin>":
				case "<physicaldescription><note>":
					if (SBmodsphysicaldescription.length()>0)
						SBmodsphysicaldescription.append(separator);
					
					
					
					
					String string2=Valor;
					if (ListaMODSVALUES.containsKey("mechanism"))
					{
						string2 = Sinonimos.getMechanism().get(Valor.trim().toLowerCase());
						if (string2==null)
							if (Controlado)
								{
								string2 = Sinonimos.getMechanism().get("otros");
								if (string2==null)
								{
									ColectionLog.getLogLines().add("Mechanism ->"+Valor+" Unknown and is controled");	
									string2="";
								}
								}
						
							else
								string2=Valor;
					}
					
					if (ListaMODSVALUES.containsKey("publication-type"))
					{
						string2 = Sinonimos.getPublication().get(Valor.trim().toLowerCase());
						if (string2==null)
							if (Controlado)
								{
								string2 = Sinonimos.getPublication().get("otros");
								if (string2==null)
								{
									ColectionLog.getLogLines().add("Mechanism ->"+Valor+" Unknown and is controled");	
									string2="";
								}
								}
						
							else
								string2=Valor;
					}
					
					if (ListaMODSVALUES.containsKey("format"))
					{
						string2 = Sinonimos.getFormatos().get(Valor.trim().toLowerCase());
						if (string2==null)
							if (Controlado)
								{
								string2 = Sinonimos.getFormatos().get("otros");
								if (string2==null)
								{
									ColectionLog.getLogLines().add("Mechanism ->"+Valor+" Unknown and is controled");	
									string2="";
								}
								}
						
							else
								string2=Valor;
					}

				
					if (!string2.trim().isEmpty())
					{
			if (SBmodslanguage.length()>0)
				SBmodslanguage.append(separator);
			SBmodsphysicaldescription.append(setValue(string2,Enstring.getValue()));
					}
					
					
//					SBmodsphysicaldescription.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<abstract>":
					if (SBmodsabstract.length()>0)
						SBmodsabstract.append(separator);
					SBmodsabstract.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<tableofcontents>":
					if (SBmodstableofcontents.length()>0)
						SBmodstableofcontents.append(separator);
					SBmodstableofcontents.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<targetaudience>":
					if (SBmodstargetaudience.length()>0)
						SBmodstargetaudience.append(separator);
					SBmodstargetaudience.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<note>":
					if (SBmodsnote.length()>0)
						SBmodsnote.append(separator);
					SBmodsnote.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<subject>":
				case "<subject><topic>":
				case "<subject><geographic>":
				case "<subject><temporal>":
				case "<subject><titleinfo>":
				case "<subject><name>":
				case "<subject><genre>":
				case "<subject><hierarchicalgeographic>":
				case "<subject><cartographics>":
				case "<subject><geographiccode>":
				case "<subject><occupation>":
					if (SBmodssubject.length()>0)
						SBmodssubject.append(separator);
					SBmodssubject.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<classification>":
					String[] SSclas=Valor.split("[,|;|:|.| |/]");
					for (String stringclass : SSclas) {
						if (!stringclass.trim().isEmpty())
						{
						
					if (SBmodsclassification.length()>0)
						SBmodsclassification.append(separator);
					SBmodsclassification.append(setValue(stringclass,Enstring.getValue()));
						}
					}
					break;
				case "<relateditem>":
					if (SBmodsrelateditem.length()>0)
						SBmodsrelateditem.append(separator);
					SBmodsrelateditem.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<identifier>":
					if (SBmodsidentifier.length()>0)
						SBmodsidentifier.append(separator);
					SBmodsidentifier.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<location>":
				case "<location><physicallocation>":
				case "<location><shelflocator>":
				case "<location><url>":
				case "<location><holdingsimple>":
				case "<location><holdingexternal>":
					if (SBmodslocation.length()>0)
						SBmodslocation.append(separator);
					SBmodslocation.append(setValue(Valor,Enstring.getValue()));
					break;

				case "<accesscondition>":
					if (SBmodsaccesscondition.length()>0)
						SBmodsaccesscondition.append(separator);
					SBmodsaccesscondition.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<part>":
				case "<part><detail>":
				case "<part><date>":
				case "<part><text>":
					if (SBmodspart.length()>0)
						SBmodspart.append(separator);
					SBmodspart.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<extentpart>":	
					if (SBmodspart.length()>0)
						SBmodspart.append(separator);
					SBmodspart.append(setValue(Valor,Enstring.getValue()));
					break;
				case "<extension>":
					if (SBmodsextension.length()>0)
						SBmodsextension.append(separator);
					SBmodsextension.append(setValue(Valor,Enstring.getValue()));
					break;	
				case "<recordinfo>":	
				case "<recordinfo><recordcontentsource>":
				case "<recordinfo><recordcreationdate>":
				case "<recordinfo><recordchangedate>":
				case "<recordinfo><recordidentifier>":
				case "<recordinfo><recordorigin>":
				case "<recordinfo><languageofcataloging>":
				case "<recordinfo><descriptionstandard>":
					if (SBmodsrecordinfo.length()>0)
						SBmodsrecordinfo.append(separator);
					SBmodsrecordinfo.append(setValue(Valor,Enstring.getValue()));
					break;
					
				default:
					break;
				}
				
			}	
			}
		}
		}
		
		String title=null;
		String creator=null;
		String contributor=null;
		String date=null;
		String language=null;
		String publisher=null;
		String relation=null;
		String format=null;
		String type=null;
		String description=null;
		String identifier=null;
		String source=null;
		String subject=null;
		String coverage=null;
		String rights=null;
		String datecreated=null;
		
		String modstitleInfo=null;
		String modsname=null;
		String modstypeofresource=null;
		String modsgenre=null;
		String modsorigininfo=null;
		String modslanguage=null;
		String modsphysicaldescription=null;
		String modsabstract=null;
		String modstableofcontents=null;
		String modstargetaudience=null;
		String modsnote=null;
		String modssubject=null;
		String modsclassification=null;
		String modsrelateditem=null;
		String modsidentifier=null;
		String modslocation=null;
		String modsaccesscondition=null;
		String modspart=null;
		String modsextension=null;
		String modsrecordinfo=null;
		
		
		if (!SBtitle.toString().trim().isEmpty())
			title="'"+SBtitle.toString()+"'";
		if (!SBcreator.toString().trim().isEmpty())
			creator="'"+SBcreator.toString()+"'";
		if (!SBcontributor.toString().trim().isEmpty())
			contributor="'"+SBcontributor.toString()+"'";
		if (!SBdate.toString().trim().isEmpty())
			date="'"+SBdate.toString()+"'";
		if (!SBlanguage.toString().trim().isEmpty())
			language="'"+SBlanguage.toString()+"'";
		if (!SBpublisher.toString().trim().isEmpty())
			publisher="'"+SBpublisher.toString()+"'";
		if (!SBrelation.toString().trim().isEmpty())
			relation="'"+SBrelation.toString()+"'";	
		if (!SBformat.toString().trim().isEmpty())
			format="'"+SBformat.toString()+"'";	
		if (!SBtype.toString().trim().isEmpty())
			type="'"+SBtype.toString()+"'";		
		if (!SBdescription.toString().trim().isEmpty())
			description="'"+SBdescription.toString()+"'";	
		if (!SBidentifier.toString().trim().isEmpty())
			identifier="'"+SBidentifier.toString()+"'";
		if (!SBsource.toString().trim().isEmpty())
			source="'"+SBsource.toString()+"'";
		if (!SBcoverage.toString().trim().isEmpty())
			coverage="'"+SBcoverage.toString()+"'";
		if (!SBrights.toString().trim().isEmpty())
			rights="'"+SBrights.toString()+"'";
		if (!SBdatecreated.toString().trim().isEmpty())
			datecreated="'"+SBdatecreated.toString()+"'";
		
		if (!SBmodstitleInfo.toString().trim().isEmpty())
			modstitleInfo="'"+SBmodstitleInfo.toString()+"'";
		if (!SBmodsname.toString().trim().isEmpty())
			modsname="'"+SBmodsname.toString()+"'";
		if (!SBmodstypeofresource.toString().trim().isEmpty())
			modstypeofresource="'"+SBmodstypeofresource.toString()+"'";
		if (!SBmodsgenre.toString().trim().isEmpty())
			modsgenre="'"+SBmodsgenre.toString()+"'";
		if (!SBmodsorigininfo.toString().trim().isEmpty())
			modsorigininfo="'"+SBmodsorigininfo.toString()+"'";
		if (!SBmodslanguage.toString().trim().isEmpty())
			modslanguage="'"+SBmodslanguage.toString()+"'";
		if (!SBmodsphysicaldescription.toString().trim().isEmpty())
			modsphysicaldescription="'"+SBmodsphysicaldescription.toString()+"'";
		if (!SBmodsabstract.toString().trim().isEmpty())
			modsabstract="'"+SBmodsabstract.toString()+"'";
		if (!SBmodstableofcontents.toString().trim().isEmpty())
			modstableofcontents="'"+SBmodstableofcontents.toString()+"'";
		if (!SBmodstargetaudience.toString().trim().isEmpty())
			modstargetaudience="'"+SBmodstargetaudience.toString()+"'";
		if (!SBmodsnote.toString().trim().isEmpty())
			modsnote="'"+SBmodsnote.toString()+"'";
		if (!SBmodssubject.toString().trim().isEmpty())
			modssubject="'"+SBmodssubject.toString()+"'";
		if (!SBmodsclassification.toString().trim().isEmpty())
			modsclassification="'"+SBmodsclassification.toString()+"'";
		if (!SBmodsrelateditem.toString().trim().isEmpty())
			modsrelateditem="'"+SBmodsrelateditem.toString()+"'";
		if (!SBmodsidentifier.toString().trim().isEmpty())
			modsidentifier="'"+SBmodsidentifier.toString()+"'";
		if (!SBmodslocation.toString().trim().isEmpty())
			modslocation="'"+SBmodslocation.toString()+"'";
		if (!SBmodsaccesscondition.toString().trim().isEmpty())
			modsaccesscondition="'"+SBmodsaccesscondition.toString()+"'";
		if (!SBmodspart.toString().trim().isEmpty())
			modspart="'"+SBmodspart.toString()+"'";
		if (!SBmodsextension.toString().trim().isEmpty())
			modsextension="'"+SBmodsextension.toString()+"'";
		
		
		
		
		if (!SBmodsrecordinfo.toString().trim().isEmpty())
			modsrecordinfo="'"+SBmodsrecordinfo.toString()+"'";
		
	
		
		
		Calendar Start = new GregorianCalendar();
		Date dateCal = Start.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat(
				"yyyy-MM-dd");
		String date1 = format1.format(dateCal);
		
		try {
			MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `metadata_record` (" +
					"`local_identifier`," +
					 "`timestamp`," +
					 "`title`," +
					 "`creator`," +
					 "`contributor`," +
					 "`date`," +
					  "`language`," +
					  "`publisher`," +
					  "`relation`," +
					  "`format`," +
					  "`type`," +
					  "`description`," +
					  "`identifier`," +
					  "`source`," +
					  "`subject`," +
					  "`datecreated`," +
					  "`coverage`," +
					  "`rights`," +
					  "`modstitleInfo`," +
					  "`modsname`," +
					  "`modstypeofresource`," +
					  "`modsgenre`," +
					  "`modsorigininfo`," +
					  "`modslanguage`," +
					  "`modsphysicaldescription`," +
					  "`modsabstract`," +
					  "`modstableofcontents`," +
					  "`modstargetaudience`," +
					  "`modsnote`," +
					  "`modssubject`," +
					  "`modsclassification`," +
					  "`modsrelateditem`," +
					  "`modsidentifier`," +
					  "`modslocation`," +
					  "`modsaccesscondition`," +
					  "`modspart`," +
					  "`modsextension`," +
					  "`modsrecordinfo`" +
					") VALUES (" +
					LocalIdentificador+","+
					"'"+date1+"',"+
					title+","+
					creator+","+
					contributor+","+
					date+","+
					language+","+
					publisher+","+
					relation+","+
					format+","+
					type+","+
					description+","+
					identifier+","+
					source+","+
					subject+","+
					datecreated+","+
					coverage+","+
					rights+","+
					modstitleInfo+","+
					modsname+","+
					modstypeofresource+","+
					modsgenre+","+
					modsorigininfo+","+
					modslanguage+","+
					modsphysicaldescription+","+
					modsabstract+","+
					modstableofcontents+","+
					modstargetaudience+","+
					modsnote+","+
					modssubject+","+
					modsclassification+","+
					modsrelateditem+","+
					modsidentifier+","+
					modslocation+","+
					modsaccesscondition+","+
					modspart+","+
					modsextension+","+
					modsrecordinfo+
					");");
		} catch (Exception e) {
			e.printStackTrace();
			ColectionLog.getLogLines().add("Error in insert element ClavyId=" + documentInspect.getClavilenoid()+" in database" );
		}
	}



	private String setValue(String valor, String tipo) {
		StringBuffer Salida=new StringBuffer();
		String[] tipoT = tipo.split("<");
		ArrayList<String> LtipoT=new ArrayList<String>();
		for (String string : tipoT)
			LtipoT.add(string);
		Collections.reverse(LtipoT);
		String tiposub=tipo.replaceAll("$i", valor);
		Salida.append(tiposub).append(valor);
		for (String string : LtipoT) {
			if (!string.isEmpty())
				{
				String[] Final = string.split(">");
				if (!Final[0].isEmpty())
					{
					String[] Final2 = Final[0].split(" ");
					if (!Final2[0].isEmpty())
						Salida.append("</").append(Final2[0]).append(">");
					}
				}
		}
		return Salida.toString();
	}



	private String separatorClean(String descriptionText) {
		return descriptionText.replace(separator, separator_comodin);
	}



	private void findTablaDC() {
		for (CompleteGrammar gramarInspect : toOda.getMetamodelGrammar()) {
			if (!StaticFuctionsOAIPMHCat.isIgnored(gramarInspect))
				findTablaDC(gramarInspect.getSons());
		}
		
	}



	private void findTablaDC(List<CompleteStructure> listaHijos) {
		for (CompleteStructure struictureInspect : listaHijos) {
			if (struictureInspect instanceof CompleteElementType)
			{
				ArrayList<String> OAI_Category=StaticFuctionsOAIPMHCat.getCategoriasOAIPMH((CompleteElementType)struictureInspect);			
				Tabla_DC.put(struictureInspect.getClavilenoid(), OAI_Category);
			}
			
			findTablaDC(struictureInspect.getSons());
				
		}
		
	}





	/**
	 * Reseta las tablas sin borrar las tablas añadidas
	 */
	public static void resetTablas() {
		MySQLConnectionOAIPMHCat.RunQuerry("DROP TABLE IF EXISTS `about_list`;");
		MySQLConnectionOAIPMHCat.RunQuerry("CREATE TABLE `about_list` ("+
				"`about_ID` int(11) NOT NULL AUTO_INCREMENT,"+
				 " `about_value` varchar(50) DEFAULT NULL,"+
				  "PRIMARY KEY (`about_ID`),"+
				  "KEY `about_ID` (`about_ID`)"+
				") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;");


		MySQLConnectionOAIPMHCat.RunQuerry("DROP TABLE IF EXISTS `metadata_record`;");
		MySQLConnectionOAIPMHCat.RunQuerry("CREATE TABLE `metadata_record` ("+
				"`local_identifier` varchar(50) NOT NULL,"+
				 "`timestamp` datetime DEFAULT NULL,"+
				  "`title` LONGTEXT DEFAULT NULL,"+
				  "`creator` LONGTEXT DEFAULT NULL,"+
				  "`date` LONGTEXT DEFAULT NULL,"+
				  "`language` LONGTEXT DEFAULT NULL,"+
				  "`publisher` LONGTEXT DEFAULT NULL,"+
				  "`relation` LONGTEXT DEFAULT NULL,"+
				  "`format` LONGTEXT DEFAULT NULL,"+
				  "`description` LONGTEXT DEFAULT NULL,"+
				  "`type` LONGTEXT DEFAULT NULL,"+
				  "`source` LONGTEXT DEFAULT NULL,"+
				  "`identifier` LONGTEXT DEFAULT NULL,"+
				  "`subject` LONGTEXT DEFAULT NULL,"+
				  "`contributor` LONGTEXT DEFAULT NULL,"+
				  "`datecreated` LONGTEXT DEFAULT NULL,"+
				  "`coverage` LONGTEXT DEFAULT NULL,"+
				  "`rights` LONGTEXT DEFAULT NULL,"+
				  "`modstitleInfo` LONGTEXT DEFAULT NULL,"+
				  "`modsname` LONGTEXT DEFAULT NULL,"+
				  "`modstypeofresource` LONGTEXT DEFAULT NULL,"+
				  "`modsgenre` LONGTEXT DEFAULT NULL,"+
				  "`modsorigininfo` LONGTEXT DEFAULT NULL,"+
				  "`modslanguage` LONGTEXT DEFAULT NULL,"+
				  "`modsphysicaldescription` LONGTEXT DEFAULT NULL,"+
				  "`modsabstract` LONGTEXT DEFAULT NULL,"+
				  "`modstableofcontents` LONGTEXT DEFAULT NULL,"+
				  "`modstargetaudience` LONGTEXT DEFAULT NULL,"+
				  "`modsnote` LONGTEXT DEFAULT NULL,"+
				  "`modssubject` LONGTEXT DEFAULT NULL,"+
				  "`modsclassification` LONGTEXT DEFAULT NULL,"+
				  "`modsrelateditem` LONGTEXT DEFAULT NULL,"+
				  "`modsidentifier` LONGTEXT DEFAULT NULL,"+
				  "`modslocation` LONGTEXT DEFAULT NULL,"+
				  "`modsaccesscondition` LONGTEXT DEFAULT NULL,"+
				  "`modspart` LONGTEXT DEFAULT NULL,"+
				  "`modsextension` LONGTEXT DEFAULT NULL,"+
				  "`modsrecordinfo` LONGTEXT DEFAULT NULL,"+
				  "PRIMARY KEY (`local_identifier`)"+
				") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
		
				
		MySQLConnectionOAIPMHCat.RunQuerry("DROP TABLE IF EXISTS `record_about_map`;");
		MySQLConnectionOAIPMHCat.RunQuerry("CREATE TABLE `record_about_map` ("+
				"`local_identifier` varchar(50) NOT NULL,"+
				 "`about_ID` int(11) NOT NULL DEFAULT '0',"+
				  "PRIMARY KEY (`local_identifier`,`about_ID`),"+
				  "KEY `about_ID` (`about_ID`)"+
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		
		MySQLConnectionOAIPMHCat.RunQuerry("DROP TABLE IF EXISTS `record_set_map`;");
		MySQLConnectionOAIPMHCat.RunQuerry("CREATE TABLE `record_set_map` ("+
				"`local_identifier` varchar(50) NOT NULL,"+
				 "`set_spec` varchar(50) NOT NULL,"+
				  "PRIMARY KEY (`local_identifier`,`set_spec`)"+
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		
		
		MySQLConnectionOAIPMHCat.RunQuerry("DROP TABLE IF EXISTS `set_list`;");
		MySQLConnectionOAIPMHCat.RunQuerry("CREATE TABLE `set_list` ("+
				"`set_spec` varchar(50) NOT NULL,"+
				"`set_name` varchar(50) DEFAULT NULL,"+
				"`set_description` varchar(50) DEFAULT NULL,"+
				"PRIMARY KEY (`set_spec`)"+
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");


				
	}

	


	

//	/**
//	 * Salva una imagen dado un destino
//	 * @param imageUrl
//	 * @param destinationFile
//	 * @throws IOException
//	 */
//	private void saveImage(URL imageUrl, String destinationFile) throws IOException {
//
//		URL url = imageUrl;
//		InputStream is = url.openStream();
//		OutputStream os = new FileOutputStream(destinationFile);
//
//		byte[] b = new byte[2048];
//		int length;
//
//		while ((length = is.read(b)) != -1) {
//			os.write(b, 0, length);
//		}
//
//		is.close();
//		os.close();
//	}
//	
//	protected CompleteTextElementType findIdov() {
//		for (CompleteGrammar meta : toOda.getMetamodelGrammar()) {
//			if (StaticFuctionsOda2.isVirtualObject(meta))
//				return findMetaDatosIDOVenOV(meta);
//	}
//		return null;
//	}
//
//	private CompleteTextElementType findMetaDatosIDOVenOV(CompleteGrammar meta) {
//		for (CompleteStructure iterable_element : meta.getSons()) {
//			if (iterable_element instanceof CompleteTextElementType)
//				{
//				if (StaticFuctionsOda2.isIDOV((CompleteTextElementType) iterable_element))
//					return (CompleteTextElementType) iterable_element;
//				}
//			
//			}
//		return null;
//					
//	}
//	
//	
	
}
