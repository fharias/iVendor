package com.fabioarias.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.fabioarias.model.FacturaDTO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.graphics.Bitmap;

public class BarcodeUtil {
	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	public static Bitmap encodeBarcode(String data, BarcodeFormat format,
			int xwidth, int xheight) {
		Bitmap bitmap = null;
		try {
			MultiFormatWriter writer = new MultiFormatWriter();
			BitMatrix result = writer.encode(data, format, xwidth, xheight);
			int width = result.getWidth();
			int height = result.getHeight();
			int[] pixels = new int[width * height];
			// All are 0, or black, by default
			for (int y = 0; y < height; y++) {
				int offset = y * width;
				for (int x = 0; x < width; x++) {
					pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
				}
			}
			bitmap = Bitmap
					.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return bitmap;
	}

	public static FacturaDTO getFactura(String xml) {
		FacturaDTO facturaDTO = null;
		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			Map<String, String> map = new HashMap<String, String>();
			xpp.setInput(new StringReader(xml));
			int eventType = xpp.getEventType();
			String tag = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					System.out.println("Start document");
				} else if (eventType == XmlPullParser.START_TAG) {
					System.out.println("Start tag " + xpp.getName());
					tag = xpp.getName();
				} else if (eventType == XmlPullParser.END_TAG) {
					System.out.println("End tag " + xpp.getName());
				} else if (eventType == XmlPullParser.TEXT) {
					System.out.println("Text " + xpp.getText());
					map.put(tag, xpp.getText());
				}
				eventType = xpp.next();
			}
			if (map != null) {
				facturaDTO = new FacturaDTO();
				facturaDTO.setNumero(map.get("F"));
				facturaDTO.setFecha(map.get("FE"));
				facturaDTO.setValor(map.get("MNT"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return facturaDTO;
	}
}
