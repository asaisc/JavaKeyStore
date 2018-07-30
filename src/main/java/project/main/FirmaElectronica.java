package project.main;

import project.core.ImpInt.ServiceImpInt;

public class FirmaElectronica {

	public FirmaElectronica(String cadena) {
		ServiceImpInt obj = new ServiceImpInt();
		String firma = obj.firmar(cadena);
		System.out.println("Firma: " + firma);
		boolean firmaValida = obj.validar(firma);
		System.out.println(firmaValida);
	}

	public static void main(String[] args) {
		new FirmaElectronica(args[0]);

	}

}
