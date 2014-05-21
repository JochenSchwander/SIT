package de.hs_mannheim.sit.ss14;

import java.io.FilterInputStream;
import java.io.InputStream;


/**
 *
 * @author Phil-Patrick Kai Kwiotek
 *
 */
public class AESFilterInputStream extends FilterInputStream{

	protected AESFilterInputStream(InputStream in) {
		super(in);
		// TODO Auto-generated constructor stub
	}
}
