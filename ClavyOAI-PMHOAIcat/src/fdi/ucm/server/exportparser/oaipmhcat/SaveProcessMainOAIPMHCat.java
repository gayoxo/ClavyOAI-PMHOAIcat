package fdi.ucm.server.exportparser.oaipmhcat;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			

	/**
	 * Constructor por defecto
	 * @param cL 
	 * @param Coleccion coleccion a insertar en oda.
	 */
	public SaveProcessMainOAIPMHCat(CompleteCollection coleccion, CompleteLogAndUpdates cL){
		toOda=coleccion;
		ColectionLog=cL;
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
			ArrayList<String> ListaDC = Tabla_DC.get(elementInspect.getHastype());
			String Valor = separatorClean(((CompleteTextElement) elementInspect).getValue());
			if (Valor.trim()!=null&&ListaDC!=null)
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
					if (SBlanguage.length()>0)
						SBlanguage.append(separator);
					SBlanguage.append(Valor);
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
					if (SBformat.length()>0)
						SBformat.append(separator);
					SBformat.append(Valor);
					break;
				case "dc:type":
					if (SBtype.length()>0)
						SBtype.append(separator);
					SBtype.append(Valor);
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
					"'2003-01-01 00:00:00',"+
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


//
//	protected void removeIGNOREDC(List<CompleteElementType> metaDatos) {
//		ArrayList<CompleteElementType> Borrar=new ArrayList<>();
//		for (CompleteStructure completeElementType : metaDatos) {
//			if (completeElementType instanceof CompleteElementType&& StaticFuctionsOda2.isIgnored((CompleteElementType)completeElementType))
//				Borrar.add((CompleteElementType)completeElementType);
//		}
//		
//		for (CompleteElementType completeStructure : Borrar) {
//			metaDatos.remove((CompleteElementType)completeStructure);
//		}
//		
//		for (CompleteElementType completeStructure : metaDatos) {
//			removeIGNORED(completeStructure.getSons());
//		}
//		
//	}
//
//
//
//	private void removeIGNORED(List<CompleteStructure> metaDatos) {
//		
//		ArrayList<CompleteElementType> Borrar=new ArrayList<>();
//		for (CompleteStructure completeElementType : metaDatos) {
//			if (completeElementType instanceof CompleteElementType&& StaticFuctionsOda2.isIgnored((CompleteElementType)completeElementType))
//				Borrar.add((CompleteElementType)completeElementType);
//		}
//		
//		for (CompleteElementType completeStructure : Borrar) {
//			metaDatos.remove(completeStructure);
//		}
//		
//		for (CompleteStructure completeStructure : metaDatos) {
//			removeIGNORED(completeStructure.getSons());
//		}
//		
//	}
//
//
//
//	/**
//	 * Busca un meta con el texto VirtualObject
//	 * @return the Meta
//	 */
//	private boolean findVOM() throws CompleteImportRuntimeException {
//		for (CompleteGrammar iterable_element : toOda.getMetamodelGrammar()) {
//				 if (StaticFuctionsOda2.isVirtualObject(iterable_element))
//					 return true;
//
//		}
//		return false;
//	}
//	
//
//	/**
//	 * Busca un meta con el texto File
//	 * @return the Meta
//	 */
//	private boolean findFile() {
//		for (CompleteGrammar iterable_element : toOda.getMetamodelGrammar()) {
//				if (StaticFuctionsOda2.isFile(iterable_element))
//					 return true;
//		}
//		return false;
//	}
//	
//	
//
//	/**
//	 * Busca un File fisico dentro del File
//	 * @return
//	 */
//	private boolean findFileFisico() {
//		boolean NoError=false;
//		for (CompleteGrammar iterable_element : toOda.getMetamodelGrammar()) {
//				if (StaticFuctionsOda2.isFile(iterable_element))
//					 {
//					if (!findfindFileFisicoINFile(iterable_element))
//				 		 return false;
//				 	 else NoError=true;
//					 }
//		}
//		return NoError;
//	}
//	
//	
//	/**
//	 * Busca un File Dentro de los Meta File
//	 * @param padre
//	 * @return
//	 */
//	private boolean findfindFileFisicoINFile(CompleteGrammar padre) {
//		for (CompleteStructure iterable_element2 : padre.getSons()) {
//			if (iterable_element2 instanceof CompleteElementType)
//			{
//			 if (StaticFuctionsOda2.isFileFisico(((CompleteElementType) iterable_element2)))
//				 return true;
//			}
//		}
//		return false;
//	}
//	
//	private boolean findFileOwner() {
//		boolean NoError=false;
//		for (CompleteGrammar iterable_element : toOda.getMetamodelGrammar()) {
//				if (StaticFuctionsOda2.isFile( iterable_element))
//					 {
//					if (!findfindOwnerINFile( iterable_element))
//				 		 return false;
//				 	 else NoError=true;
//					 }
//		}
//		return NoError;
//	}
//
//
//	private boolean findfindOwnerINFile(CompleteGrammar padre) {
//		for (CompleteStructure iterable_element2 : padre.getSons()) {
//			if (iterable_element2 instanceof CompleteElementType)
//			{
//			 if (StaticFuctionsOda2.isOwner(((CompleteElementType) iterable_element2)))
//				 return true;
//			}
//		}
//		return false;
//	}
//
//
//	
//	
//
//	/**
//	 * Busca los metadatos
//	 * @return Lista de Metadatos
//	 */
//	protected ArrayList<CompleteElementType> findMetaDatos() {
//		ArrayList<CompleteElementType> Salida=new ArrayList<CompleteElementType>();
//		for (CompleteGrammar meta : toOda.getMetamodelGrammar()) {
//				if (StaticFuctionsOda2.isVirtualObject(meta))
//					Salida.addAll(findMetaDatosInVO(meta));
//		}
//		return Salida;
//
//	}
//
//
//	/**
//	 * Busca los Metadatos y los Datos dentro del VO
//	 * @param elementType
//	 * @return
//	 */
//	private ArrayList<CompleteElementType> findMetaDatosInVO(CompleteGrammar elementType) {
//		ArrayList<CompleteElementType> Salida=new ArrayList<CompleteElementType>();
//		for (CompleteStructure iterable_element : elementType.getSons()) {
//			if (iterable_element instanceof CompleteElementType)
//				{
//				if (StaticFuctionsOda2.isMetaDatos((CompleteElementType) iterable_element))
//					Salida.add((CompleteElementType) iterable_element);
//				else if (StaticFuctionsOda2.isDatos((CompleteElementType) iterable_element))
//					Salida.add((CompleteElementType) iterable_element);
//				}
//			else if (iterable_element instanceof CompleteIterator)
//			{
//			for (CompleteStructure completeElementType : iterable_element.getSons()) {
//				
//				if ((completeElementType instanceof CompleteElementType)&&(StaticFuctionsOda2.isResources((CompleteElementType) completeElementType)))
//					Salida.add((CompleteElementType) completeElementType);
//			}
//			
//			}
//					
//		}
//		return Salida;
//	}
//
//
//
//	/**
//	 * Procesa el modelo para las raices del modelo.
//	 * @param list raices del modelo de entrada.
//	 * @throws ImportRuntimeException error si esta mal introducido el modelo en la funcion 
//	 */
//	protected void processModeloIniciales(List<CompleteStructure> list) throws CompleteImportRuntimeException {
//		list=CleanRepeticiones(list,new ArrayList<Integer>());
//		rellenaTablaVocabularios(list);
//		for (CompleteStructure Cattribute : list) {
//
//				CompleteElementType attribute=(CompleteElementType) Cattribute;
//			int Salida=3;
//			if (StaticFuctionsOda2.isMetaDatos(attribute))
//				{
//					Salida=2;
//					ModeloOda.put(attribute, Salida);
//					processModelo(attribute.getSons(),Salida);
//				}
//			else if (StaticFuctionsOda2.isDatos(attribute))
//				{
//					Salida=1;
//					ModeloOda.put(attribute, Salida);
//					processModelo(attribute.getSons(),Salida);
//				}
//			else if (StaticFuctionsOda2.isResources(attribute))
//			{
//				Salida=3;
//				ModeloOda.put(attribute, Salida);
//				processModelo(attribute.getSons(),Salida);
//			}
//				
//
//		}
//	}
//
//	/**
//	 * Limpia de iteraciones la lista y las sustituye por meta, realiza el mismo proceso dentro de los atributos, quitando el contexto.
//	 * @param list lista a limpiar
//	 * @return Lista limpia de elementos
//	 */
//	protected ArrayList<CompleteStructure> CleanRepeticiones(List<CompleteStructure> list,ArrayList<Integer> Ambitos) {
//		ArrayList<CompleteStructure> Salida=new ArrayList<CompleteStructure>();
//		for (CompleteStructure collectionAttribute : list) {
//			if (collectionAttribute instanceof CompleteIterator)
//			{
//				ArrayList<CompleteStructure> Hermanos=processIteracion((CompleteIterator)collectionAttribute,Ambitos);
//				for (CompleteStructure collectionAttribute2 : Hermanos) {
//					Salida.add(collectionAttribute2);
//				}
//			}else
//				{
//				Salida.add(collectionAttribute);
//				ArrayList<CompleteStructure> Entrada=CleanRepeticiones(collectionAttribute.getSons(),Ambitos);
//				collectionAttribute.setSons(Entrada);
//				}
//				
//		}
//		return Salida;
//	}
//
//
//	/**
//	 * Procesa una iteracion duplicando los elementos necesarios
//	 * @param iteracionElement elemento iterador a procesar
//	 * @param profundidad profundidad actual de ambito
//	 * @param ambitos ambitos ya procesados en los que me encuentro
//	 * @return lista de elementios duplicados
//	 */
//	private ArrayList<CompleteStructure> processIteracion(
//			CompleteIterator iteracionElement,ArrayList<Integer> element) {
//		ArrayList<CompleteStructure> Salida=new ArrayList<CompleteStructure>();
//		Integer Repetir =iteracionElement.getAmbitoSTotales();
//		for (CompleteStructure collectionAttribute : iteracionElement.getSons()) {
//			Integer A;
//			for (int i = 1; i <= Repetir; i++) {
//				A=new Integer(i);
//				element.add(A);
//				if (collectionAttribute instanceof CompleteElementType)
//					{
//						CompleteElementType nuevo=CloneEle((CompleteElementType)collectionAttribute,element);
//						nuevo.setFather(iteracionElement.getFather());
//						if (
//							(collectionAttribute instanceof CompleteLinkElementType)||
//							(collectionAttribute instanceof CompleteResourceElementType)||
//							(collectionAttribute instanceof CompleteTextElementType)
//							)
//						{
//							
//							replaceRefe(element,nuevo,(CompleteElementType) collectionAttribute);
//						}
//						Salida.add(nuevo);
//						ArrayList<CompleteStructure> salidarec = CleanRepeticiones(nuevo.getSons(),element);
//						nuevo.setSons(salidarec);
//					}
//				else 
//					{
//					CompleteIterator nuevo=new CompleteIterator();
//					nuevo.setFather(iteracionElement.getFather());
//					for (CompleteStructure iterable_element : collectionAttribute.getSons()) {
//						CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//						nuevo.getSons().add(cloneEle);
//						if (cloneEle instanceof CompleteElementType)
//							replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType)iterable_element);
//					}
//					ArrayList<CompleteStructure> salidarec = processIteracion(nuevo, element);
//					for (CompleteStructure collectionAttribute2 : salidarec) {
//						Salida.add(collectionAttribute2);
//					}
//					}
//				element.remove(A);
//			}
//		}
//		
//		return Salida;
//	}
//
//
//	/**
//	 * REmplaza la referencia antigua por una nueva
//	 * @param element lista de el ambito actual	
//	 * @param nuevo nuevo elemento
//	 * @param collectionAttribute viejo elemento
//	 */
//	private void replaceRefe(ArrayList<Integer> element, CompleteElementType nuevo,
//			CompleteElementType collectionAttribute) {
//		for (CompleteDocuments section : toOda.getEstructuras()) {
//			for (CompleteElement meta : section.getDescription()) {
//				if (meta.getHastype()==collectionAttribute&&compareAmbitos(meta.getAmbitos(),element))
//					meta.setHastype(nuevo);
//			}
//		}
//		
//	}
//
//
//	/**
//	 * Compara si el elemento esta dentro del ambito.
//	 * @param ambitos ambito del objeto.
//	 * @param element ambito actual.
//	 * @return si esta dentro del ambito.
//	 */
//	private boolean compareAmbitos(ArrayList<Integer> ambitos,
//			ArrayList<Integer> element) {
//		int itera=0;
//		while (itera<element.size()) {
//			if ((itera>=ambitos.size())||(ambitos.get(itera).intValue()!=element.get(itera)))
//				return false;
//			itera++;
//		}
//		return true;
//	}
//
//
//	/**
//	 * Clona el elemento atribuido
//	 * @param collectionAttribute elemento a colar
//	 * @param element 
//	 * @return nuevo elemento
//	 */
//	private CompleteElementType CloneEle(CompleteElementType collectionAttribute, ArrayList<Integer> element) {
//		CompleteElementType nuevo=null;
////		if (collectionAttribute instanceof MetaControlled)
////				{
////				nuevo=new MetaControlled(((MetaControlled) collectionAttribute).getName(),collectionAttribute.getFather());
////				for (OperationalView nuevoShow : collectionAttribute.getShows()) {
////					OperationalView NuevoShownClonado=nuevoShow;
////					((ElementType)nuevo).getShows().add(NuevoShownClonado);
////				}
////				for (Structure iterable_element : collectionAttribute.getSons()) {
////
////					Structure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
////					nuevo.getSons().add(cloneEle);
////					if (cloneEle instanceof ElementType)
////						replaceRefe(element,(ElementType) cloneEle,(ElementType) iterable_element);
////				}
////				}
////		else
//			if	(collectionAttribute instanceof CompleteLinkElementType)
//		{
//			nuevo=new CompleteLinkElementType(((CompleteLinkElementType) collectionAttribute).getName(),collectionAttribute.getFather());
//			for (CompleteOperationalView nuevoShow : collectionAttribute.getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			for (CompleteStructure iterable_element : collectionAttribute.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}
//		}
//		else if	(collectionAttribute instanceof CompleteResourceElementType)
//		{
//			nuevo=new CompleteResourceElementType(((CompleteResourceElementType) collectionAttribute).getName(),collectionAttribute.getFather());
//			for (CompleteOperationalView nuevoShow : collectionAttribute.getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			for (CompleteStructure iterable_element : collectionAttribute.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}
//		}
//		else if	(collectionAttribute instanceof CompleteTextElementType)
//		{
//
//			nuevo=new CompleteTextElementType(((CompleteTextElementType) collectionAttribute).getName(), collectionAttribute.getFather());
//			for (CompleteOperationalView nuevoShow : collectionAttribute.getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			for (CompleteStructure iterable_element : collectionAttribute.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}
//		}
//
//		else {
//			nuevo=new CompleteElementType(((CompleteElementType) collectionAttribute).getName(), collectionAttribute.getFather());
//			for (CompleteOperationalView nuevoShow : collectionAttribute.getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			for (CompleteStructure iterable_element : collectionAttribute.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}
//		}
//		
//		return nuevo;
//	}
//
//
//	
//
///**
// * Funcion que clona un elemento de manera profunda
// * @param elementoColonar elemento a clonar
// * @param padreNUevo nuevo padre
// * @param element 
// * @return el elemento nuevo
// */
//	private CompleteStructure CloneEleProfundo(
//			CompleteStructure elementoColonar, CompleteStructure padreNUevo, ArrayList<Integer> element) {
//		CompleteStructure nuevo=null;
////		if (elementoColonar instanceof MetaControlled)
////		{
////		nuevo=new MetaControlled(((MetaControlled) elementoColonar).getName(), padreNUevo);
////		for (OperationalView nuevoShow : ((ElementType) elementoColonar).getShows()) {
////			OperationalView NuevoShownClonado=nuevoShow;
////			((ElementType)nuevo).getShows().add(NuevoShownClonado);
////		}
////		for (Structure iterable_element : elementoColonar.getSons()) {
////			Structure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
////			nuevo.getSons().add(cloneEle);
////			if (cloneEle instanceof ElementType)
////				replaceRefe(element,(ElementType) cloneEle,(ElementType) iterable_element);
////		}
////		}
////		else
//			if	(elementoColonar instanceof CompleteLinkElementType)
//		{
//			nuevo=new CompleteLinkElementType(((CompleteLinkElementType) elementoColonar).getName(), padreNUevo);
//			for (CompleteOperationalView nuevoShow : ((CompleteElementType) elementoColonar).getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			for (CompleteStructure iterable_element : elementoColonar.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}	
//		}
//		else if	(elementoColonar instanceof CompleteResourceElementType)
//		{
//			nuevo=new CompleteResourceElementType(((CompleteResourceElementType) elementoColonar).getName(), padreNUevo);
//			for (CompleteOperationalView nuevoShow : ((CompleteElementType) elementoColonar).getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			for (CompleteStructure iterable_element : elementoColonar.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}	
//		}
//		else if	(elementoColonar instanceof CompleteTextElementType)
//		{
//			nuevo=new CompleteTextElementType(((CompleteTextElementType) elementoColonar).getName(), padreNUevo);
//			for (CompleteOperationalView nuevoShow : ((CompleteElementType) elementoColonar).getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			for (CompleteStructure iterable_element : elementoColonar.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}
//		}
//		else if (elementoColonar instanceof CompleteElementType){
//			nuevo=new CompleteElementType(((CompleteElementType) elementoColonar).getName(), padreNUevo);
//			for (CompleteOperationalView nuevoShow : ((CompleteElementType) elementoColonar).getShows()) {
//				CompleteOperationalView NuevoShownClonado=nuevoShow;
//				((CompleteElementType)nuevo).getShows().add(NuevoShownClonado);
//			}
//			
//			for (CompleteStructure iterable_element : elementoColonar.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}
//		}
//		else {
//			nuevo=new CompleteIterator(padreNUevo);
//			for (CompleteStructure iterable_element : elementoColonar.getSons()) {
//				CompleteStructure cloneEle = CloneEleProfundo(iterable_element,nuevo,element);
//				nuevo.getSons().add(cloneEle);
//				if (cloneEle instanceof CompleteElementType)
//					replaceRefe(element,(CompleteElementType) cloneEle,(CompleteElementType) iterable_element);
//			}
//		}
//		return nuevo;
//	}
//
//
//	/**
//	 * Rellena la tabla con los vocabularios y la tabla de compartidos con aquellos que compartiran vocabulario.
//	 * @param list modelo de entrada.
//	 */
//	protected void rellenaTablaVocabularios(List<CompleteStructure> list) {
//		for (CompleteStructure attribute : list) {
//			if (attribute instanceof CompleteElementType)
//				if (attribute instanceof CompleteTextElementType&&StaticFuctionsOda2.isControled((CompleteTextElementType)attribute))
//				{
//					Integer Numero=StaticFuctionsOda2.getVocNumber((CompleteTextElementType) attribute);
//					Boolean Compartido=StaticFuctionsOda2.getVocCompartido((CompleteTextElementType) attribute);
//					if (Numero!=null&&Compartido)
//							Vocabularios.add(Numero);
//
//				}
//			rellenaTablaVocabularios(attribute.getSons());
//		}
//	}
//
//	/**
//	 * Procea el modelo para las no raices de los metas.
//	 * @param list hijos del padre.
//	 * @param padre padre meta de modelo.
//	 * @throws CompleteImportRuntimeException error si esta mal introducido el modelo en la funcion
//	 */
//	protected void processModelo(List<CompleteStructure> list, int padre) throws CompleteImportRuntimeException {
//	
//		int pos = getOrden(padre);
//		
//		for (CompleteStructure Cattribute : list) {
//			
//			
////			int pos = getOrden(padre);
//			
//			if (Cattribute instanceof CompleteIterator)
//				throw new CompleteImportRuntimeException(EXISTE_ERROR_EN_EL_PARSEADO_DE_LAS_ITERACIONES);
//			else{
//				CompleteElementType attribute=(CompleteElementType) Cattribute;
//			
//			String Name = attribute.getName().replaceAll("'", "\\\\'");
//			String Browser;
//			if (StaticFuctionsOda2.getBrowseable(attribute))
//				Browser="S";
//			else Browser="N";
//			
//			String Visible;
//			if (StaticFuctionsOda2.getVisible(attribute))
//					Visible="S";
//			else Visible="N";
//			
//			String Extensible;
//			if (StaticFuctionsOda2.isExtensible(attribute))
//				Extensible="S";
//			else Extensible="N";
//			
//			int Salida=2;
//			if (attribute instanceof CompleteTextElementType){
//				
//				if (StaticFuctionsOda2.isNumeric(attribute))
//					Salida=MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `section_data` (`idpadre`, `nombre`, `visible`,`orden`, `browseable`, `tipo_valores`, `extensible`, `vocabulario`) VALUES ('"+padre+"','"+Name+"', '"+Visible+"','"+pos+"','"+Browser+"' , 'N', '"+Extensible+"', '0');");
//				else if (StaticFuctionsOda2.isDate(attribute))
//					Salida=MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `section_data` (`idpadre`, `nombre`, `visible`,`orden`, `browseable`, `tipo_valores`, `extensible`, `vocabulario`) VALUES ('"+padre+"','"+Name+"', '"+Visible+"','"+pos+"','"+Browser+"' , 'F', '"+Extensible+"', '0');");
//				else if (StaticFuctionsOda2.isControled(attribute))
//					{
//					Integer vocabularioN=StaticFuctionsOda2.getVocNumber((CompleteTextElementType) attribute);
//					Integer otro=VocabulariosSalida.get(vocabularioN);
//					String catalogocomp;
//					if (otro!=null)
//					{
//						catalogocomp=otro.toString();
//						
//						Salida= MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `section_data` (`idpadre`, `nombre`, `visible`,`orden`, `browseable`, `tipo_valores`, `extensible`, `vocabulario`) VALUES ('"+padre+"','"+Name+"', '"+Visible+"','"+pos+"','"+Browser+"' , 'C', 'S', '"+catalogocomp+"');");
//						
//					}
//					else {
//						if (Vocabularios.contains(vocabularioN))
//							{
//							catalogocomp="1";
//							
//							Salida= MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `section_data` (`idpadre`, `nombre`, `visible`,`orden`, `browseable`, `tipo_valores`, `extensible`, `vocabulario`) VALUES ('"+padre+"','"+Name+"', '"+Visible+"','"+pos+"','"+Browser+"' , 'C', 'S', '"+catalogocomp+"');");
//							
//							VocabulariosSalida.put(vocabularioN,Salida );
//							}
//						else {
//						catalogocomp="0";
//						
//						Salida= MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `section_data` (`idpadre`, `nombre`, `visible`,`orden`, `browseable`, `tipo_valores`, `extensible`, `vocabulario`) VALUES ('"+padre+"','"+Name+"', '"+Visible+"','"+pos+"','"+Browser+"' , 'C', 'S', '"+catalogocomp+"');");
//						}
//					}
//					}
//				else
//					Salida=MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `section_data` (`idpadre`, `nombre`, `visible`,`orden`, `browseable`, `tipo_valores`, `extensible`, `vocabulario`) VALUES ('"+padre+"','"+Name+"', '"+Visible+"','"+pos+"','"+Browser+"' , 'T', '"+Extensible+"', '0');");
//			}
//
//			else {
//			
//				Salida=MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `section_data` (`idpadre`, `nombre`, `visible`,`orden`, `browseable`, `tipo_valores`, `extensible`, `vocabulario`) VALUES ('"+padre+"','"+Name+"', '"+Visible+"','"+pos+"','"+Browser+"' , 'X', '"+Extensible+"', '0');");
//			}
//			ModeloOda.put(attribute, Salida);
//			processModelo(attribute.getSons(),Salida);
//			pos++;	
//			}
//		} 
//	}
//
//	/**
//	 * inserta los elementos en el padre aasignado
//	 * @param padre padre a insertar
//	 * @param name nombre
//	 * @param browser si es navegable
//	 * @param Tipo tipo a insertar
//	 * @param vocabulario voavulario
//	 * @return id de salida en la tabla
//	 */
//	protected int getOrden(int padre) {
//		try {
//			ResultSet rs=MySQLConnectionOAIPMHCat.RunQuerrySELECT("SELECT MAX(orden) FROM section_data WHERE idpadre="+padre+";");
//			if (rs!=null) 
//			{
//				rs.next();
//					
//					Object datoO= rs.getObject("MAX(orden)");
//					String Dato="0";
//					if (datoO!=null)
//						Dato=datoO.toString();
//					int orden=Integer.parseInt(Dato);
//					
//					rs.close();
//					
//					return orden++;
//					
//				
//			
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}
//
//	/**
//	 * Procesa los objetos virtuales
//	 * @param list lista de Secciones candidatas a oV
//	  * @throws ImportRuntimeException en caso de errores varios. Consultar el error en {@link ImportRuntimeException}
//	 */
//	protected void processOV(List<CompleteDocuments> list) throws CompleteImportRuntimeException {
//		for (CompleteDocuments resources : list) {
//			if (StaticFuctionsOda2.isAVirtualObject(resources))
//				saveOV(resources);
//		}
//		
//		for (Entry<CompleteDocuments, Integer> completeDocuments : tabla.entrySet()) {
//			procesa_Atributos(completeDocuments.getKey(),completeDocuments.getValue());
//		}
//	}
//
////	/**
////	 * Dtermina si un objeto es un recurso o es una categoria OV
////	 * @param resources
////	 * @return si es un OV
////	 */
////	private boolean ISOV(Construct resources) {
////		for (MetaValue valor : resources.getDescription()) {
////			if ((valor instanceof MetaTextValue)&&(valor.getHastype().getName().equals(Oda2StaticNames.IDOV)))
////				return true;
////				
////		}
////		return false;
////	}
//
//
//	/**
//	 * Funcion que procesa un OV ya asignado.
//	 * @param ObjetoDigital OV en forma de seccion.
//	 * @throws ImportRuntimeException en caso de errores varios. Consultar el error en {@link ImportRuntimeException}
//	 */
//	protected void saveOV(CompleteDocuments ObjetoDigital) throws CompleteImportRuntimeException {
//		try{
////		Integer Idov=StaticFuctionsOda2.findIdov(ObjetoDigital);
//		boolean Public=StaticFuctionsOda2.getPublic(ObjetoDigital);
//		boolean Private=StaticFuctionsOda2.getPrivate(ObjetoDigital);
//		
//		String SPublic="N";
//		if (Public)
//			SPublic="S";
//		
//		String SPrivate="N";
//		if (Private)
//			SPrivate="S";
//		
//				
//		int Salida = MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `virtual_object` (`ispublic`,`isprivate`) VALUES ('"+SPublic+"','"+SPrivate+"');");
//		
//		
//		tabla.put(ObjetoDigital,Salida);
//		procesa_descripcion(ObjetoDigital,Salida);
//		procesa_iconos(ObjetoDigital,Salida);
//		
//		
//		}catch (NumberFormatException e)
//		{
//			throw new CompleteImportRuntimeException("Los Identificadores no son numeros.");
//		}
//		
//	}
//
//
//	/**
//	 * Procesa la descripcion del OV
//	 * @param objetoDigital
//	 * @param idov
//	 */
//	protected void procesa_descripcion(CompleteDocuments objetoDigital, Integer Idov) {
//		MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `text_data` (`idov`, `idseccion`, `value`) VALUES ('"+Idov+"', '111', '"+SQLScaped(objetoDigital.getDescriptionText())+"');");
//		
//	}
//
//
//	/**
//	 * Procesalos iconos y los introduce en la lista de el idov
//	 * @param ObjetoDigital recurso de entrada
//	 * @param ObjetoDigitalIdov Identificador del ObjetoDigital
//	 * @throws ImportRuntimeException si el Objeto no tiene idov
//	 */
//	protected void procesa_iconos(CompleteDocuments ObjetoDigital, Integer ObjetoDigitalIdov) throws CompleteImportRuntimeException {
//		
//		String Path= StaticFuctionsOda2.getIcon(ObjetoDigital);
//		if (Path!=null)
//			Iconos.put(ObjetoDigitalIdov, new CompleteFile(Path,toOda));		
//	}
//
//	
////	/**
////	 * Procesa un recurso sobre su Objeto Digital
////	 * @param recursoAProcesar Recurso que sera procesado
////	 * @param idov identificador del sueño del recurso.
////	 * @param visibleValue2 
////	 * @return 
////	 * @throws ImportRuntimeException si el elemento no tiene un campo en su descripcion necesario.
////	 */
////	protected abstract int procesa_recursos(CompleteDocuments recursoAProcesar, Integer idov, boolean visibleValue2);
////	
//	/**
//	 * Procesa un recurso sobre su Objeto Digital
//	 * @param recursoAProcesar Recurso que sera procesado
//	 * @param idov identificador del sueño del recurso.
//	 * @param visibleValue2 
//	 * @return 
//	 * @throws ImportRuntimeException si el elemento no tiene un campo en su descripcion necesario.
//	 */
//	protected int procesa_recursos(CompleteDocuments recursoAProcesar, Integer idov, boolean visibleValue2) throws CompleteImportRuntimeException {
//
//		
//		if (recursoAProcesar==null)
//			return -1;
//		
//		
//		boolean visBool=visibleValue2;
//		String VisString;
//		if (visBool) 
//				VisString="S";
//		else 
//			VisString="N";
//		
//	
//				CompleteDocuments recursoAProcesarC = (CompleteDocuments)recursoAProcesar;
//				
//				if (StaticFuctionsOda2.isAVirtualObject(recursoAProcesarC))
//				{
//					
//					Integer Idov=tabla.get(recursoAProcesarC);
//					if (Idov!=null)
//						 {
//						int Salida = MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `resources` (`idov`, `visible`,`iconoov`, `idov_refered`, `type`) VALUES ('"+idov+"', '"+VisString+"','N', '"+Idov+"','OV')");	
//						return Salida;
//						 }
//					else
//						ColectionLog.getLogLines().add("Link a objeto virtual: "+ recursoAProcesarC.getDescriptionText()+ "no existe en la lista de recursos, pero tiene un link, IGNORADO" );
//				}
//				else
//					if (StaticFuctionsOda2.isAFile(recursoAProcesarC))
//				{
//						CompleteResourceElement FIleRel=StaticFuctionsOda2.findMetaValueFile(recursoAProcesarC.getDescription());
//						CompleteLinkElement idovrefVal= StaticFuctionsOda2.findMetaValueIDOVowner(recursoAProcesarC.getDescription());
//					
//					
//					if (idovrefVal!=null)
//					{
//						Integer Idov=tabla.get(idovrefVal.getValue());
//					
//						
//						if  (FIleRel!=null && Idov!=null && (FIleRel instanceof CompleteResourceElementFile))
//							{
//								CompleteFile Icon=Iconos.get(idov);
//								String iconoov;
//								if (Icon!=null && Icon.getPath().equals((((CompleteResourceElementFile)FIleRel).getValue()).getPath()))
//									iconoov="S";
//								else iconoov="N";
//								String[] spliteStri=(((CompleteResourceElementFile)FIleRel).getValue()).getPath().split("/");
//								String NameS = spliteStri[spliteStri.length-1];
//								
//								
//								if (Idov==idov)
//									{
//									
//									
//									int Salida =MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `resources` (`idov`, `visible`,`iconoov`, `name`, `type`) VALUES ('"+idov+"', '"+VisString+"','"+iconoov+"', '"+NameS+"', 'P' )");
//									return Salida;
//									
//									}
//								else
//									{
//									int Salida =MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `resources` (`idov`, `visible`, `name`,`idresource_refered`, `type`) VALUES ('"+idov+"', '"+VisString+"', '"+NameS+"', '"+Idov+"','F')");
//									return Salida;
//									}
//							}
//						else ColectionLog.getLogLines().add("EL file referencia es nulo, o no es un file o el dueño no es un Objeto virtual valido con identificadorArchivo:"+recursoAProcesarC.getDescriptionText()+", IGNORADO");
//					}
//					else ColectionLog.getLogLines().add("El objeto dueño del archivo es nulo o no Objeto Virtual, Archivo:"+recursoAProcesarC.getDescriptionText()+", IGNORADO ");
//				}
//					else
//						if (StaticFuctionsOda2.isAURL(recursoAProcesarC))
//						{
//							String ValueUri="";
//							CompleteResourceElement UniFile=StaticFuctionsOda2.findMetaValueUri(recursoAProcesarC.getDescription());
//							if (UniFile instanceof CompleteResourceElementURL)
//								ValueUri=((CompleteResourceElementURL)UniFile).getValue();
//							else if (UniFile instanceof CompleteResourceElementFile&&((CompleteResourceElementFile)UniFile).getValue()!=null)
//								ValueUri=((CompleteResourceElementFile)UniFile).getValue().getPath();
//								
//								if  (UniFile!=null&&!ValueUri.isEmpty())
//									{
//
//											int Salida =MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `resources` (`idov`, `visible`,`iconoov`, `name`, `type`) VALUES ('"+idov+"', '"+VisString+"','N', '"+ValueUri+"', 'U' )");
//											return Salida;
//
//
//									}
//								else ColectionLog.getLogLines().add("El URI referencia es nulo, o vacio identificadorArchivo:"+recursoAProcesarC.getDescriptionText()+", IGNORADO");
//						}
//				return -1;
//		
//	}
//
//
//
//	/**
//	 * Clase que procesa los atributos de un OV
//	 * @param DO Ovirtual
//	 * @param Idov Identificador del objeto.
//	 * @throws ImportRuntimeException en caso de errores varios. Consultar el error en {@link CompleteImportRuntimeException}
//	 */
//	protected void procesa_Atributos(CompleteDocuments DO, Integer Idov) throws CompleteImportRuntimeException {
//		
//		ArrayList<CompleteElement> Normales=new ArrayList<CompleteElement>();
//		ArrayList<CompleteElement> Recursos=new ArrayList<CompleteElement>();
//		HashMap<Integer, Long> AmbitoSalida=new HashMap<Integer, Long>();
//		
//		for (CompleteElement attributeValue : DO.getDescription()) 
//		{
//			if (dependedelfile(attributeValue.getHastype()))
//				Recursos.add(attributeValue);
//			else
//				Normales.add(attributeValue);
//		}
//		
//		
//		
//		for (CompleteElement attributeValue : Normales) {
//						
//			if (attributeValue.getHastype() instanceof CompleteResourceElementType)
//			{
//				if (StaticFuctionsOda2.isResources(attributeValue.getHastype()))	
//				{
//					boolean VisibleValue= StaticFuctionsOda2.getVisible(((CompleteResourceElement)attributeValue));
//						if (attributeValue instanceof CompleteLinkElement)
//							{
//							Integer Value=procesa_recursos(((CompleteLinkElement)attributeValue).getValue(),Idov,VisibleValue);
//							if (Value>0&&attributeValue.getAmbitos().size()>0)
//								AmbitoSalida.put(attributeValue.getAmbitos().get(0),new Long(Value));
//							}
//				}
//			}
//			if (attributeValue.getHastype() instanceof CompleteLinkElementType)
//			{
//				if (StaticFuctionsOda2.isResources(attributeValue.getHastype()))	
//				{
//					boolean VisibleValue= StaticFuctionsOda2.getVisible(((CompleteLinkElement)attributeValue));
//						if (attributeValue instanceof CompleteLinkElement)
//							{
//							Integer Value=procesa_recursos(((CompleteLinkElement)attributeValue).getValue(),Idov,VisibleValue);
//							if (Value>0&&attributeValue.getAmbitos().size()>0)
//								AmbitoSalida.put(attributeValue.getAmbitos().get(0),new Long(Value));
//							}
//				}
//			}
//			else{
//			Integer seccion=ModeloOda.get(attributeValue.getHastype());
//			if (seccion!=null){
//			if (attributeValue instanceof CompleteTextElement){
//				
//				if (StaticFuctionsOda2.isNumeric(attributeValue.getHastype()))
//				{
//					String value = SQLScaped(((CompleteTextElement) attributeValue).getValue());
//					value=value.replace("'", "\\'");
//					MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `numeric_data` (`idov`, `idseccion`, `value`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"');");
//
//				}
//				else if (StaticFuctionsOda2.isDate(attributeValue.getHastype()))
//				{
//					Date fecha = null;
//					//yyyy-MM-dd HH:mm:ss
//					try {
//						SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//					} catch (Exception e) {
//						//Nada
//						fecha = null;
//					}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yy");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//						
//					if (fecha==null)
//						ColectionLog.getLogLines().add("Error en formato del texto para la fecha \""+((CompleteTextElement) attributeValue).getValue()+"\", solo formatos compatibles yyyy-MM-dd HH:mm:ss ó yyyy-MM-dd ó yyyyMMdd ó dd/MM/yyyy ó dd/MM/yy");
//					else
//					{
//						DateFormat df = new SimpleDateFormat ("yyyyMMdd");
//						String value=df.format(fecha);
//						MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `date_data` (`idov`, `idseccion`, `value`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"');");
//					}
//					
//				}
//				else if (StaticFuctionsOda2.isControled(attributeValue.getHastype()))
//				{
//					String value = ((CompleteTextElement) attributeValue).getValue();
//					value=value.replace("'", "\\'");
//					if (value!=null)
//						MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `controlled_data` (`idov`, `idseccion`, `value`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"');");
//				}
//				else
//				{
//				
//				String value = SQLScaped(((CompleteTextElement) attributeValue).getValue());
//				value=value.replace("'", "\\'");
//				MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `text_data` (`idov`, `idseccion`, `value`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"');");
//				}
//			}
//			}
//			}
//		}
//		
//		for (CompleteElement attributeValue : Recursos) {
//			
//			Integer seccion=ModeloOda.get(attributeValue.getHastype());
//			Long ValueRec=null;
//			if (attributeValue.getAmbitos().size()>0)
//				ValueRec=AmbitoSalida.get(attributeValue.getAmbitos().get(0));
//			if (seccion!=null&&ValueRec!=null){
//				
//			if (attributeValue instanceof CompleteTextElement){
//				
//				if (StaticFuctionsOda2.isNumeric(attributeValue.getHastype()))
//				{
//					String value = SQLScaped(((CompleteTextElement) attributeValue).getValue());
//					value=value.replace("'", "\\'");
//					MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `numeric_data` (`idov`, `idseccion`, `value`,`idrecurso`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"', '"+ValueRec+"');");
//
//				}
//				else if (StaticFuctionsOda2.isDate(attributeValue.getHastype()))
//				{
//					
//					Date fecha = null;
//					//yyyy-MM-dd HH:mm:ss
//					try {
//						SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//					} catch (Exception e) {
//						//Nada
//						fecha = null;
//					}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					if (fecha==null)
//						try {
//							SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yy");
//							fecha = formatoDelTexto.parse(((CompleteTextElement) attributeValue).getValue());
//						} catch (Exception e) {
//							//Nada
//							fecha = null;
//						}
//					
//					
//					if (fecha==null)
//						ColectionLog.getLogLines().add("Error en formato del texto para la fecha \""+((CompleteTextElement) attributeValue).getValue()+"\", solo formatos compatibles yyyy-MM-dd HH:mm:ss ó yyyy-MM-dd ó yyyyMMdd ó dd/MM/yyyy ó dd/MM/yy");
//					else
//					{
//						DateFormat df = new SimpleDateFormat ("yyyyMMdd");
//						String value=df.format(fecha);
//						MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `date_data` (`idov`, `idseccion`, `value`,`idrecurso`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"', '"+ValueRec+"');");
//					} 
//					
//				}
//				else if (StaticFuctionsOda2.isControled(attributeValue.getHastype()))
//				{
//					String value = ((CompleteTextElement) attributeValue).getValue();
//					value=value.replace("'", "\\'");
//					if (value!=null)
//						MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `controlled_data` (`idov`, `idseccion`, `value`,`idrecurso`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"', '"+ValueRec+"');");
//				}
//				else
//				{
//				
//				String value = SQLScaped(((CompleteTextElement) attributeValue).getValue());
//				value=value.replace("'", "\\'");
//				MySQLConnectionOAIPMHCat.RunQuerryINSERT("INSERT INTO `text_data` (`idov`, `idseccion`, `value`,`idrecurso`) VALUES ('"+Idov+"', '"+seccion+"', '"+value+"', '"+ValueRec+"');");
//				}
//			}
//			}
//		}
//		
//	}
//
//	/**
//	 * Depende del file o es un atributo estandar
//	 * @param hastype
//	 * @return
//	 */
//	private boolean dependedelfile(CompleteStructure hastype) {
//			if (hastype.getFather()==null)
//				return false;
//			else if ((hastype.getFather()instanceof CompleteElementType)&&StaticFuctionsOda2.isResources((CompleteElementType) hastype.getFather()))
//				return true;
//			else 
//				return dependedelfile(hastype.getFather());
//	}
//
//
//
//	private String SQLScaped(String value) {
//		String Salida=value;
//		Salida=Salida.replace("'", "''");
//		Salida=Salida.replace("\"", "\\\"");
//		return Salida;
//	}


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
				  "`title` varchar(50) DEFAULT NULL,"+
				  "`creator` varchar(50) DEFAULT NULL,"+
				  "`date` varchar(50) DEFAULT NULL,"+
				  "`language` varchar(50) DEFAULT NULL,"+
				  "`publisher` varchar(50) DEFAULT NULL,"+
				  "`relation` varchar(50) DEFAULT NULL,"+
				  "`format` varchar(50) DEFAULT NULL,"+
				  "`description` varchar(50) DEFAULT NULL,"+
				  "`type` varchar(50) DEFAULT NULL,"+
				  "`source` varchar(50) DEFAULT NULL,"+
				  "`identifier` varchar(50) DEFAULT NULL,"+
				  "`subject` varchar(50) DEFAULT NULL,"+
				  "`contributor` varchar(50) DEFAULT NULL,"+
				  "`datecreated` varchar(50) DEFAULT NULL,"+
				  "`coverage` varchar(50) DEFAULT NULL,"+
				  "`rights` varchar(50) DEFAULT NULL,"+
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
