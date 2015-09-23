/**
 * 
 */
package fdi.ucm.server.exportparser.oaipmhcatmods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteStructure;

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

	public static boolean isIgnored(CompleteDocuments documentInspect,List<CompleteGrammar> lista) {
		
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
		
		for (CompleteGrammar completeOperationalValue : lista) {
			if (isInGrammar(documentInspect, completeOperationalValue)&&isIgnored(completeOperationalValue))
					return true;
		}
		
		return false;
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

	
	public static HashMap<String,String> getCategoriasOAIPMHMODSValues(
			CompleteElementType hastype) {
		HashMap<String,String> Salida=new HashMap<String,String>();
		
		ArrayList<CompleteOperationalValueType> Shows = hastype.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().toLowerCase().equals(StaticNamesOAIPMHCat.MODS.toLowerCase()))
			{
					Salida.put(show.getName().toLowerCase(), show.getDefault());
			}
		}
		
		return Salida;
	}
	
	
	public static boolean isInGrammar(CompleteDocuments iterable_element,
			CompleteGrammar completeGrammar) {
		HashSet<Long> ElemT=new HashSet<Long>();
		for (CompleteElement dd : iterable_element.getDescription()) {
			ElemT.add(dd.getHastype().getClavilenoid());
		}
		
		return isInGrammar(ElemT, completeGrammar.getSons());
		
		
	}



	private static boolean isInGrammar(HashSet<Long> elemT,
			List<CompleteStructure> sons) {
		for (CompleteStructure CSlong1 : sons) {
			if (elemT.contains(CSlong1.getClavilenoid())||isInGrammar(elemT, CSlong1.getSons()))
				return true;
			
		}
		return false;
	}
	
}
