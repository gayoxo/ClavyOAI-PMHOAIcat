/**
 * 
 */
package fdi.ucm.server.exportparser.oaipmhcatmods;

import java.util.ArrayList;
import java.util.HashMap;

import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalView;

/**
 * Clase que genera las funciones estaticas para Oda.
 * @author Joaquin Gayoso-Cabada
 *
 */
public class StaticFuctionsOAIPMHCat {





	public static ArrayList<String> getCategoriasOAIPMH(
			CompleteElementType hastype) {
		
		ArrayList<String> Salida=new ArrayList<String>();
		
		ArrayList<CompleteOperationalView> Shows = hastype.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().toLowerCase().equals(StaticNamesOAIPMHCat.OAIPMH.toLowerCase()))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType CompleteOperationalValueType : ShowValue) {
					if (CompleteOperationalValueType.getName().toLowerCase().equals(StaticNamesOAIPMHCat.TYPE.toLowerCase()))
						if (!CompleteOperationalValueType.getDefault().trim().isEmpty())
							Salida.add(CompleteOperationalValueType.getDefault());

				}
			}
		}
		
		return Salida;
	}

	public static boolean isIgnored(CompleteDocuments documentInspect) {
		
		ArrayList<CompleteOperationalValue> ShowsE = documentInspect.getViewsValues();
		for (CompleteOperationalValue show : ShowsE) {
			
			if (show.getType().getView().getName().toLowerCase().equals(StaticNamesOAIPMHCat.OAIPMH.toLowerCase()))
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

		ArrayList<CompleteOperationalView> Shows = hastype.getViews();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().toLowerCase().equals(StaticNamesOAIPMHCat.OAIPMH.toLowerCase()))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType CompleteOperationalValueType : ShowValue) {
					if (CompleteOperationalValueType.getName().toLowerCase().equals(StaticNamesOAIPMHCat.IGNORE.toLowerCase()))
						if (!CompleteOperationalValueType.getDefault().trim().isEmpty())
							{
							boolean Salida=true;
							try {
								Salida=Boolean.parseBoolean(CompleteOperationalValueType.getDefault());
							} catch (Exception e) {
							}
							
							return Salida;
							}

				}
			}
		}
		
		return false;
	}

	public static ArrayList<String> getCategoriasOAIPMHMODS(
			CompleteElementType hastype) {
ArrayList<String> Salida=new ArrayList<String>();
		
		ArrayList<CompleteOperationalView> Shows = hastype.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().toLowerCase().equals(StaticNamesOAIPMHCat.MODS.toLowerCase()))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType CompleteOperationalValueType : ShowValue) {
					if (CompleteOperationalValueType.getName().toLowerCase().equals(StaticNamesOAIPMHCat.TYPE.toLowerCase()))
						if (!CompleteOperationalValueType.getDefault().trim().isEmpty())
							Salida.add(CompleteOperationalValueType.getDefault());

				}
			}
		}
		
		return Salida;
	}
	
	public static HashMap<String,String> getCategoriasOAIPMHMODSValues(
			CompleteElementType hastype) {
		HashMap<String,String> Salida=new HashMap<String,String>();
		
		ArrayList<CompleteOperationalView> Shows = hastype.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().toLowerCase().equals(StaticNamesOAIPMHCat.MODSVALUE.toLowerCase()))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType CompleteOperationalValueType : ShowValue) 
					Salida.put(CompleteOperationalValueType.getName().toLowerCase(), CompleteOperationalValueType.getDefault());
			}
		}
		
		return Salida;
	}
	
}
