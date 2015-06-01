package net.leludo.pi.timer;

public final class MasterValueFormatter {

	public static String toJson(String name, long date, String value) {
		StringBuffer sb = new StringBuffer() ;

		sb.append("{") ;
		
		sb.append("\"").append("valeur").append("\"") ;
		sb.append(":").append(value);
		
		sb.append(",");
		
		sb.append("\"").append("date").append("\"") ;
		sb.append(":").append(date);
		
		sb.append(",");
		
		sb.append("\"").append("capteur").append("\"") ;
		sb.append(":");
		sb.append("{") ;
		sb.append("\"").append("id").append("\"") ;
		sb.append(":").append("\"").append(name).append("\"");
		sb.append(",");
		sb.append("\"").append("typeCapteur").append("\"") ;
		sb.append(":");
		
		sb.append("{") ;
		sb.append("\"").append("libelle").append("\"") ;
		sb.append(":").append("\"").append("Capteur de Ludo").append("\"");
		sb.append(",");
		sb.append("\"").append("unite").append("\"") ;
		sb.append(":").append("\"").append("°C").append("\"");
		sb.append("}") ;
	
		sb.append(",");
		sb.append("\"").append("frequenceMesures").append("\"") ;
		sb.append(":").append(5000);
		sb.append(",");
		sb.append("\"").append("modele").append("\"") ;
		sb.append(":").append("\"").append("BS20B18").append("\"") ;
		sb.append("}");
		sb.append("}") ;


		
//		sb.append("{") ;
//		sb.append("\"").append("id").append("\"") ;
//		sb.append(":").append("\"").append(name).append("\"");
//		sb.append(",");
//		sb.append("\"").append("temp").append("\"") ;
//		sb.append(":").append(value);
//		sb.append(",");
//		sb.append("\"").append("date").append("\"") ;
//		sb.append(":").append(date);
//		sb.append("}");
		
		return sb.toString();
	}
}
