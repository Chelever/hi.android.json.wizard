package com.renan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.text.Normalizer;
import java.util.InputMismatchException;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


//import com.hi.corujadrugstore.R;

/**
 * Created by renan.pinto on 03,Maio,2017.
 */

public class FieldValidation {

	public static boolean isCPF(String CPF) {
		CPF = removePointAndLine(CPF);
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111")
				|| CPF.equals("22222222222") || CPF.equals("33333333333")
				|| CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777")
				|| CPF.equals("88888888888") || CPF.equals("99999999999")
				|| (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere
											// numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static String phoneFormat(String number) {
		if (number == null)
			return "";
		StringBuilder sb = new StringBuilder(number);
		if (sb.length() > 0) {
			sb = new StringBuilder(sb.toString().replaceAll("\\(", ""));
			sb.insert(0, "(");
		}
		if (sb.length() > 2) {
			sb = new StringBuilder(sb.toString().replaceAll("\\)", ""));
			sb.insert(3, ")");
		}
		return sb.toString();
	}

	public static boolean isValidZipCode(String zipCode) {
		zipCode = removeAllSymbols(zipCode);
		if (zipCode == null)
			return false;
		if (zipCode.length() != 8)
			return false;
		if (zipCode.contains("[a-zA-Z]+"))
			return false;
		return true;
	}

	public static String removeBrakets(String number) {
		StringBuilder sb = new StringBuilder(number);
		sb = new StringBuilder(sb.toString().replaceAll("\\(", ""));
		sb = new StringBuilder(sb.toString().replaceAll("\\)", ""));
		return sb.toString();
	}

	public static boolean isFilled(String text) {
		return text.length() > 0;
	}

	public static String cpfFormat(String CPF) {
		String cpf = removePointAndLine(CPF);
		StringBuilder cpfStrb = new StringBuilder(cpf);
		
		if (cpfStrb.length() > 3) {
			cpfStrb.insert(3, ".");
		}
		
		if (cpfStrb.length() > 7) {
			cpfStrb.insert(7, ".");
		}
		
		if (cpfStrb.length() > 11) {
			cpfStrb.insert(11, "-");
		}
		
		return cpfStrb.toString();
//		return (cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
//				+ cpf.substring(6, 9) + "-" + cpf.substring(9, 11));
	}
	
	public static String cnpjFormat(String CNPJ) {
		String cnpj = removeAllSymbols(CNPJ);
		StringBuilder cnpjStrb = new StringBuilder(cnpj);
		
		if (cnpjStrb.length() > 2) {
			cnpjStrb.insert(2, ".");
		}
		
		if (cnpjStrb.length() > 6) {
			cnpjStrb.insert(6, ".");
		}
		
		if (cnpjStrb.length() > 10) {
			cnpjStrb.insert(10, "/");
		}
		
		if (cnpjStrb.length() > 15) {
			cnpjStrb.insert(15, "-");
		}
		
		return cnpjStrb.toString();
	}
	
	
	public static String cepFormat(String CEP) {
		String cep = removeAllSymbols(CEP);
		StringBuilder cnpjStrb = new StringBuilder(cep);
		
		if (cnpjStrb.length() > 5) {
			cnpjStrb.insert(5, "-");
		}
		
		return cnpjStrb.toString();
	}
	
	
	public static String removeAllSymbols(String string){
		string =  string.replaceAll("[^\\p{Digit}]+", "");
		return string;
	}
	
	public static String removePointAndLine(String number) {
		StringBuilder sb = new StringBuilder(number);
		sb = new StringBuilder(sb.toString().replaceAll("\\.", ""));
		sb = new StringBuilder(sb.toString().replaceAll("\\-", ""));
		return sb.toString();
	}
	

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	// private static Logger passwordLogger;
	private static StringBuilder retVal;

	public static String getPasswordLogger() {
		if (retVal != null) {
			return retVal.toString();
		}
		return "No password tested";
	}

	@SuppressLint("DefaultLocale")
	public static boolean isValidNewPass(String pass1) {
		// passwordLogger = Logger.getLogger("Password");
		retVal = new StringBuilder();

		if (pass1.length() < 1)
			retVal.append("Empty fields <br>");

		if (pass1 != null) {

			// if (pass1.equals(pass2)) {
			// Log.d("Password",pass1 + " = " + pass2);

			boolean hasUppercase = !pass1.equals(pass1.toLowerCase());
			boolean hasLowercase = !pass1.equals(pass1.toUpperCase());
			boolean hasNumber = pass1.matches(".*\\d.*");
			boolean noSpecialChar = pass1.matches("[a-zA-Z0-9 ]*");

			if (pass1.length() < 5) {
				Log.d("Password", pass1 + " is length < 8");
				retVal.append("Password is too short. Needs to have 11 characters <br>");
				// return false;
			}

//			if (!hasUppercase) {
//				Log.d("Password", pass1 + " <-- needs uppercase");
//				retVal.append("Password needs an upper case <br>");
				// return false;
//			}

			if (!hasLowercase) {
				Log.d("Password", pass1 + " <-- needs lowercase");
				retVal.append("Password needs a lowercase <br>");
				// return false;
			}

			if (!hasNumber) {
				Log.d("Password", pass1 + "<-- needs a number");
				retVal.append("Password needs a number <br>");
				// return false;
			}

			// if(noSpecialChar){
			// Log.d("Password",pass1 + "<-- needs a specail character");
			// retVal.append("Password needs a special character i.e. !,@,#, etc.  <br>");
			// // return false;
			// }
			// }else{
			// Log.d("Password",pass1 + " != " + pass2);
			// retVal.append("Passwords don't match<br>");
			// // return false;
			// }
		}
		if (retVal.length() == 0) {
			Log.d("Password", "Password validates");
			retVal.append("Success");
			return true;
		}
		return false;

	}

	public static boolean isValidPassCompare(String pass1, String pass2) {
		if (pass1 == null || pass2 == null)
			return false;
//		if (pass1.length() < 8 || pass2.length() < 8)
//			return false;
		return pass1.equals(pass2);
	}

	public static boolean isCNPJ(String CNPJ) {
		CNPJ = removeAllSymbols(CNPJ);
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111")
				|| CNPJ.equals("22222222222222")
				|| CNPJ.equals("33333333333333")
				|| CNPJ.equals("44444444444444")
				|| CNPJ.equals("55555555555555")
				|| CNPJ.equals("66666666666666")
				|| CNPJ.equals("77777777777777")
				|| CNPJ.equals("88888888888888")
				|| CNPJ.equals("99999999999999") || (CNPJ.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos
			// informados.
			if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static String imprimeCNPJ(String CNPJ) {
		// máscara do CNPJ: 99.999.999.9999-99
		return (CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "."
				+ CNPJ.substring(5, 8) + "." + CNPJ.substring(8, 12) + "-" + CNPJ
					.substring(12, 14));
	}

	public static Address getAdressByZipCode(Context context, String zipCode) {
		List<Address> addresses;
		Address address = null;
		final Geocoder geocoder = new Geocoder(context);
		try {
			addresses = geocoder.getFromLocationName(zipCode, 1);
			if (addresses != null && !addresses.isEmpty()) {
				address = addresses.get(0);
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return address;
	}

	public static boolean isValidPhone(String telefone) {

		telefone = removeBrakets(telefone);
	    //verifica se tem a qtde de numero correto
	    if (!(telefone.length() >= 10 && telefone.length() <= 11)) return false;

	    //Se tiver 11 caracteres, verificar se começa com 9 o celular
	    if (telefone.length() == 11 && Integer.parseInt(telefone.substring(2, 3)) != 9) return false;

	    //verifica se não é nenhum numero digitado errado (propositalmente)
//	    for (int n = 0; n < 10; n++) {
//	        //um for de 0 a 9.
//	        //estou utilizando o metodo Array(q+1).join(n) onde "q" é a quantidade e n é o
//	        //caractere a ser repetido
//	        if (telefone == new Array(11).join(n) || telefone == new Array(12).join(n)) return false;
//	    }
	    //DDDs validos
	    int[] codigosDDD = new int[]{11, 12, 13, 14, 15, 16, 17, 18, 19,
	        21, 22, 24, 27, 28, 31, 32, 33, 34,
	        35, 37, 38, 41, 42, 43, 44, 45, 46,
	        47, 48, 49, 51, 53, 54, 55, 61, 62,
	        64, 63, 65, 66, 67, 68, 69, 71, 73,
	        74, 75, 77, 79, 81, 82, 83, 84, 85,
	        86, 87, 88, 89, 91, 92, 93, 94, 95,
	        96, 97, 98, 99};
	    //verifica se o DDD é valido (sim, da pra verificar rsrsrs)
	    if(codigosDDD.length<Integer.parseInt(telefone.substring(0, 2)))return false;
	    
	    boolean codigoIncorreto = true;
	    for (int i : codigosDDD) {
			if(i == Integer.parseInt(telefone.substring(0, 2)))
				codigoIncorreto = false;
		}
	    if(codigoIncorreto)
	    return false;
	    
	    if (codigosDDD[Integer.parseInt(telefone.substring(0, 2))]== -1) return false;
//	    int[] numbers = new int[]{2, 3, 4, 5, 7};
//	    
//	    if (telefone.length() == 10 && numbers[Integer.parseInt(telefone.substring(2, 3))] == -1) return false;

	    //se passar por todas as validações acima, então está tudo certo
	    return true;
	}

	public static boolean isValidArea(String area) {
		if (area == null || area.length() == 0) {
			return false;
		}
		return area.length() == 2;
	}

	public static boolean isValidCode(String text) {
		return text.length() > 0;
	}

	public static boolean isValidPlace(String text) {
		return text.length() > 0;
	}

	public static boolean isValidCnes(String text) {
		return text.length() > 0;
	}

	public static boolean isValidAdress(String text) {
		return text.length() > 0;
	}

	public static boolean isValidCompanyName(String text) {
		return text.length() > 0;
	}

//	public static String getAbrState(Context context, String state) {
//		String ste = formatToCompare(state);
//		String[] brazilAbr = context.getResources().getStringArray(
//				R.array.state_abr_brazil);
//		String[] brazilNames = context.getResources().getStringArray(
//				R.array.state_name_brazil);
//		for (int i = 0; i < brazilNames.length; i++) {
//			if (formatToCompare(brazilNames[i]).equals(ste)) {
//				return brazilAbr[i + 1];
//			}
//		}
//		return ste;
//	}

//	public static String getNameState(Context context, String abr) {
//		String ste = formatToCompare(abr);
//		String[] brazilAbr = context.getResources().getStringArray(
//				R.array.state_abr_brazil);
//		String[] brazilNames = context.getResources().getStringArray(
//				R.array.state_name_brazil);
//		for (int i = 0; i < brazilNames.length; i++) {
//			if (formatToCompare(brazilAbr[i]).equals(ste)) {
//				return brazilNames[i + 1];
//			}
//		}
//		return ste;
//	}

	public static String formatToCompare(String string) {
		return removerAcentos(string.toLowerCase());
	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll(
				"[^\\p{ASCII}]", "");
	}
}