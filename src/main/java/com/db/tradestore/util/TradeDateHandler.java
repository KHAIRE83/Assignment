package com.db.tradestore.util;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class TradeDateHandler extends StdDeserializer<LocalDate> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = LoggerFactory.getLogger(TradeDateHandler.class);

	public TradeDateHandler() {
		this(null);
	}

	public TradeDateHandler(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
		String date = jsonparser.getText();
		if (date == null) {
			throw new InvalidParameterException("Date is Null.");
		}
		try {
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
			LocalDate.parse(date, dtf);
			return LocalDate.parse(date, dtf);
		} catch (Exception e) {
			throw new IOException("Error while parsing Date in TradeDateHandler");
		}
	}
}
