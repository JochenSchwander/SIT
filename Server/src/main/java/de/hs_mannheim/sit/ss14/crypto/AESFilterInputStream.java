package de.hs_mannheim.sit.ss14.crypto;

import java.io.FilterInputStream;
import java.io.InputStream;


/**
 *
 * @author Phil-Patrick Kai Kwiotek
 *
 */
public class AESFilterInputStream extends FilterInputStream{

	public AESFilterInputStream(InputStream in, String key) {
		super(in);
		// TODO Auto-generated constructor stub
	}
}
