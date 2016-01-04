package fdi.ucm.server.exportparser.oaipmhcat;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

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
	private static final char separator='~';
	private static final char separator_comodin='¬';
			

	private OAIPMHCatXMLSynonim Sinonimos;
	/**
	 * Constructor por defecto
	 * @param cL 
	 * @param pathFile 
	 * @param Coleccion coleccion a insertar en oda.
	 */
	public SaveProcessMainOAIPMHCat(CompleteCollection coleccion, CompleteLogAndUpdates cL, String pathFile){
		toOda=coleccion;
		ColectionLog=cL;
		
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
		
		findTablaDC();
		
		processOV();
		
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
		
		
		SBdescription.append(separatorClean(documentInspect.getDescriptionText()));
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
					
					
					String[] SS=Valor.split("[,|;|:|.]");
					for (String string2 : SS) {
						string2 = Sinonimos.getLanguage().get(string2.toLowerCase());
						if (!string2.trim().isEmpty())
						{
						if (SBlanguage.length()>0)
							SBlanguage.append(separator);	
						
						SBlanguage.append(string2);
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
					
					String[] SS3=Valor.split("[,|;|:|.]");
					for (String string2 : SS3) {
						if (!string2.trim().isEmpty())
						{
						
					if (SBformat.length()>0)
						SBformat.append(separator);
					SBformat.append(string2);
						}
					}
					
					break;
				case "dc:type":
					
					String[] SS2=Valor.split("[,|;|:|.]");
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
					  "`rights`" +
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
					rights+
					");");
		} catch (Exception e) {
			e.printStackTrace();
			ColectionLog.getLogLines().add("Error in insert element ClavyId=" + documentInspect.getClavilenoid()+" in database" );
		}
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
