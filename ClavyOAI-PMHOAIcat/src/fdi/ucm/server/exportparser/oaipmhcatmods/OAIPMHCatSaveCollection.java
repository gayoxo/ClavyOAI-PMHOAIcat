/**
 * 
 */
package fdi.ucm.server.exportparser.oaipmhcatmods;

import java.io.File;
import java.util.ArrayList;
import fdi.ucm.server.modelComplete.ImportExportDataEnum;
import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.SaveCollection;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.CompleteLogAndUpdates;

/**
 * Clase que impementa el plugin de OAI_PMH para OAICAT para Localhost
 * @author Joaquin Gayoso-Cabada
 *
 */
public class OAIPMHCatSaveCollection extends SaveCollection {

	private static final String ODA = "OAI-PMH + MODS en OAICat (MySQL only)";
	private ArrayList<ImportExportPair> Parametros;
	private boolean Create;
	private String Database;
	private String Path;
	private String FileIO;
	private String SOURCE_FOLDER = ""; // SourceFolder path
	
	/**
	 * Constructor por defecto
	 */
		public OAIPMHCatSaveCollection() {
	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.SaveCollection#processCollecccion(fdi.ucm.shared.model.collection.Collection)
	 */
	@Override
	public CompleteLogAndUpdates processCollecccion(CompleteCollection Salvar,
			String PathTemporalFiles) throws CompleteImportRuntimeException{
		try {
			Path=PathTemporalFiles;
			SOURCE_FOLDER=Path+"Oda"+File.separator;
			File Dir=new File(SOURCE_FOLDER);
			Dir.mkdirs();
			
			CompleteLogAndUpdates CL=new CompleteLogAndUpdates();
			
			SaveProcessMainOAIPMHCat.resetTablas();
			
			SaveProcessMainOAIPMHCat oda = new SaveProcessMainOAIPMHCat(Salvar,CL);
			oda.preocess();
			
			
			return CL;
			

		} catch (CompleteImportRuntimeException e) {
			System.err.println("Exception OdaException " +e.getGENERIC_ERROR());
			e.printStackTrace();
			throw e;
		}
		
	}

	/**
	 * QUitar caracteres especiales.
	 * @param str texto de entrada.
	 * @return texto sin caracteres especiales.
	 */
	private boolean hasSpecialChar(String str) {
		for (int i = 0; i < str.length(); i++) {
			   char c = str.charAt(i);
			   if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_')) {
			         return true;
			      }
				   
		}
		return false;
	}

	


	@Override
	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "MySQL Server Direction"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "MySQL OAI-PMH OAICat Destiny Database"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Number, "MySQL Port"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "MySQL User"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.EncriptedText, "MySQL Password"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Create if not exist (Create a new database and generate structure by zero)"));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
	}

	@Override
	public void setConfiguracion(ArrayList<String> DateEntrada) {
		if (DateEntrada!=null)
		{
			
			Database=DateEntrada.get(1);
			if (hasSpecialChar(Database))
					throw new CompleteImportRuntimeException("DDBB Name errors, can not accept this name, please use only compatible characters");
			
			boolean existe=MySQLConnectionOAIPMHCat.CheckDBS(DateEntrada.get(0),Database,Integer.parseInt(DateEntrada.get(2)),DateEntrada.get(3),DateEntrada.get(4));
			Create=Boolean.parseBoolean(DateEntrada.get(5));

			if (!existe&&!Create)
				throw new CompleteImportRuntimeException("DDBB not exist and you do not select \"Create if not exist\" checkbox");
			else{
					
					MySQLConnectionOAIPMHCat.getInstance(DateEntrada.get(0),Database,Integer.parseInt(DateEntrada.get(2)),DateEntrada.get(3),DateEntrada.get(4));
				

			}
		}
		}
		

	@Override
	public String getName() {
		return ODA;
	}

	@Override
	public boolean isFileOutput() {
		return false;
	}

	@Override
	public String FileOutput() {
		return FileIO;
	}

	@Override
	public void SetlocalTemporalFolder(String TemporalPath) {
		Path=TemporalPath;
		
	}



	
}
