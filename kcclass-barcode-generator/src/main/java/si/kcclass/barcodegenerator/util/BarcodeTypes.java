package si.kcclass.barcodegenerator.util;

public enum BarcodeTypes {

	CODE39("CODE39"), CODE128("CODE128"), INTERLEAVED_2_OF_5("INTERLEAVED_2_OF_5"), 
			POSTNET("POSTNET"), EAN8("EAN8"), EAN13("EAN13"), EAN128("EAN128"), 
			UPCA("UPCA"), UPCE("UPCE");

	private String type;

	private BarcodeTypes(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
}
