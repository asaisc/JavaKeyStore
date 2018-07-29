package project.main;

import project.core.ImpInt.ServiceImpInt;

public class FirmaElectronica {

	public FirmaElectronica(String firma) {
		ServiceImpInt obj = new ServiceImpInt();
		System.out.println("Firma: " + obj.firmar(firma));
	}

	public static void main(String[] args) {
		new FirmaElectronica(args[0]);

	}

}
