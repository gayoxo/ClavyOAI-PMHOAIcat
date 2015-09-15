/**
 * 
 */
package fdi.ucm.server.exportparser.oaipmhcat;

import java.util.ArrayList;

import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;

/**
 * Clase que genera las funciones estaticas para Oda.
 * @author Joaquin Gayoso-Cabada
 *
 */
public class StaticFuctionsOAIPMHCat {





	public static ArrayList<String> getCategoriasOAIPMH(
			CompleteElementType hastype) {
		
		ArrayList<String> Salida=new ArrayList<String>();
		
		ArrayList<CompleteOperationalValueType> Shows = hastype.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().toLowerCase().equals(StaticNamesOAIPMHCat.OAIPMH.toLowerCase()))
			{
					if (show.getName().toLowerCase().equals(StaticNamesOAIPMHCat.TYPE.toLowerCase()))
						if (!show.getDefault().trim().isEmpty())
							Salida.add(show.getDefault());

				
			}
		}
		
		return Salida;
	}

	public static boolean isIgnored(CompleteDocuments documentInspect) {
		
		ArrayList<CompleteOperationalValue> ShowsE = documentInspect.getViewsValues();
		for (CompleteOperationalValue show : ShowsE) {
			
			if (show.getType().getView().toLowerCase().equals(StaticNamesOAIPMHCat.OAIPMH.toLowerCase()))
			{

					if (show.getType().getName().toLowerCase().equals(StaticNamesOAIPMHCat.IGNORE.toLowerCase()))
							{
							boolean Salida=true;
							try {
								Salida=Boolean.parseBoolean(show.getValue());
							} catch (Exception e) {
							}
							
							return Salida;
							}

			}
		}
		
		
		return isIgnored(documentInspect.getDocument());
	}

	public static boolean isIgnored(CompleteGrammar hastype) {

		ArrayList<CompleteOperationalValueType> Shows = hastype.getViews();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().toLowerCase().equals(StaticNamesOAIPMHCat.OAIPMH.toLowerCase()))
			{

					if (show.getName().toLowerCase().equals(StaticNamesOAIPMHCat.IGNORE.toLowerCase()))
						if (!show.getDefault().trim().isEmpty())
							{
							boolean Salida=true;
							try {
								Salida=Boolean.parseBoolean(show.getDefault());
							} catch (Exception e) {
							}
							
							return Salida;
							}

			}
		}
		
		return false;
	}
	
}
