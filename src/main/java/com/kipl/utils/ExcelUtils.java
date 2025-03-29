package com.kipl.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;

/**
 * @author Charan.Anumasa
 */
public class ExcelUtils {
	private static final Log LOG = LogFactory.getLog(ExcelUtils.class);

	@SuppressWarnings("unused")
	private final Workbook workbook;

	public ExcelUtils(Workbook workbook) {
		this.workbook = workbook;
	}
	
    private static Map<Workbook, CellStyle> defaultCellStyleCache = new HashMap<>();

	public static void createRows(Workbook workbook, Sheet sheet, int rowNo, List<Object> rowValue, boolean bold,
			String... extraParams) {
		Row rowHeader = sheet.createRow(rowNo);
		
		CellStyle cs = getCellStyle(workbook, bold, 11);
		
		CellStyle dateCellStyle = getDateCellStyle(workbook, bold, 11);
		CellStyle timestampCellStyle = getTimestampCellStyle(workbook, bold, 11);
		for (int i = 0; i < rowValue.size(); i++) {
			Cell cell = rowHeader.createCell(i);
			if (rowValue.get(i) instanceof Date)
				cell.setCellStyle(dateCellStyle);
			else if (rowValue.get(i) instanceof java.sql.Date)
				cell.setCellStyle(dateCellStyle);
			else if (rowValue.get(i) instanceof java.sql.Timestamp)
				cell.setCellStyle(timestampCellStyle);
			else
				cell.setCellStyle(cs);

			if (extraParams.length > 0) {
				if (i == Integer.parseInt(extraParams[0])) {
					LOG.info("==BB==="+extraParams[0]);
					CellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					Font font = workbook.createFont();
					font.setFontName("Calibri");
					font.setFontHeightInPoints((short) 10);
					if (rowValue.get(i) instanceof Double) {
						double val = (Double) rowValue.get(i);
						if (val > 0) {
							font.setColor(IndexedColors.GREEN.getIndex());
						} else if (val < 0) {
							font.setColor(IndexedColors.RED.getIndex());
						}
						cellStyle.setFont(font);
						cell.setCellStyle(cellStyle);
					}
				}
			}
			setCellValue(cell,rowValue.get(i));
		}
	}
	public static void createRowsWithOneCellStyle(Workbook workbook, Sheet sheet, int rowNo, List<Object> rowValue, boolean bold,
			CellStyle cs,String... extraParams) {
		Row rowHeader = sheet.createRow(rowNo);
		
		//CellStyle dateCellStyle = getDateCellStyle(workbook, bold, 11);
		//CellStyle timestampCellStyle = getTimestampCellStyle(workbook, bold, 11);
		for (int i = 0; i < rowValue.size(); i++) {
			Cell cell = rowHeader.createCell(i);
			/*if (rowValue.get(i) instanceof Date)
				cell.setCellStyle(dateCellStyle);
			else if (rowValue.get(i) instanceof java.sql.Date)
				cell.setCellStyle(dateCellStyle);
			else if (rowValue.get(i) instanceof java.sql.Timestamp)
				cell.setCellStyle(timestampCellStyle);
			else*/
				cell.setCellStyle(cs);

			if (extraParams.length > 0) {
				if (i == Integer.parseInt(extraParams[0])) {
					LOG.info("==CC==="+extraParams[0]);
					CellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					Font font = workbook.createFont();
					font.setFontName("Calibri");
					font.setFontHeightInPoints((short) 10);
					if (rowValue.get(i) instanceof Double) {
						double val = (Double) rowValue.get(i);
						if (val > 0) {
							font.setColor(IndexedColors.GREEN.getIndex());
						} else if (val < 0) {
							font.setColor(IndexedColors.RED.getIndex());
						}
						cellStyle.setFont(font);
						cell.setCellStyle(cellStyle);
					}
				}
			}
			setCellValue(cell,rowValue.get(i));
		}
	}
	private static void setCellValue(Cell cell, Object value) {
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof java.sql.Date) {
            cell.setCellValue((java.sql.Date) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
    }

	public static CellStyle getCellStyle(Workbook workbook, boolean bold, int fontWeight) {
		if (defaultCellStyleCache.containsKey(workbook)) {
            return defaultCellStyleCache.get(workbook);
        }
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) fontWeight);

		if (bold) {
			headerFont.setBold(true);
		}

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);

		if (bold) {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		else {
		defaultCellStyleCache.put(workbook, cellStyle);
		}
	    return cellStyle;
	}
	
	public static CellStyle createStyle(Workbook workbook, boolean bold, int fontWeight) {
		
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) fontWeight);

		if (bold) {
			headerFont.setBold(true);
		}

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);

		if (bold) {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		
	    return cellStyle;
	}
	
	public static CellStyle createStyleForChilds(Workbook workbook, boolean bold, int fontWeight) {
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) fontWeight);

		if (bold) {
			headerFont.setBold(true);
		}

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // Horizontal centering
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Vertical centering

		if (bold) {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		return cellStyle;
	}
	
	 public static CellStyle getDateCellStyle(Workbook workbook, boolean bold, int fontSize) {
	 
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) fontSize);

		if (bold) {
			headerFont.setBold(true);

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(headerFont);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));

			return cellStyle;
		}
		return workbook.createCellStyle();
	}

	public static CellStyle getTimestampCellStyle(Workbook workbook, boolean bold, int fontSize) {
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) fontSize);

		if (bold) {
			headerFont.setBold(true);
		}

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);

		if (bold) {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}

		cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss"));
		return cellStyle;
	}
	
	
	@Bean
	public CellStyle cellEmptyStyle(Workbook workbook) {
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) 12); // Adjust the font size as needed

		// BOLDWEIGHT_BOLD is deprecated, use setBold(true)
		headerFont.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		// cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		return cellStyle;
	}

	@Bean
	public CellStyle optionalCellStyle(Workbook workbook) {
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBold(true); // Use setBold method instead of setBoldweight

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Use FillPatternType enum

		return cellStyle;
	}

	public void mergeCells(Workbook workbook, Sheet sheet, Row row, int cellNo, String mergeRange, String cellValue,
			boolean border, CellStyle cellStyle) {
		Cell cell = row.createCell(cellNo);
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // Use HorizontalAlignment enum
		cell.setCellStyle(cellStyle);
		cell.setCellValue(cellValue);

		sheet.addMergedRegion(CellRangeAddress.valueOf(mergeRange));

		if (border) {
			RegionUtil.setBorderTop(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
			RegionUtil.setBorderLeft(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
			RegionUtil.setBorderRight(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
			RegionUtil.setBorderBottom(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
		}
	}

	public void mergeDoubleCells(Workbook workbook, Sheet sheet, Row row, int cellNo, String mergeRange,
			String cellValue, boolean border, CellStyle cellStyle) {
		Cell cell = row.createCell(cellNo);
		cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Use FillPatternType enum
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // Use HorizontalAlignment enum
		cell.setCellStyle(cellStyle);
		cell.setCellValue(cellValue);

		sheet.addMergedRegion(CellRangeAddress.valueOf(mergeRange));

		if (border) {
			RegionUtil.setBorderLeft(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
			RegionUtil.setBorderRight(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
		}
	}

	@Bean
	public CellStyle formulaCellStyle(Workbook workbook) {
		CellStyle cellStyle = getCellStyle(workbook, true, 10); // Adjust the parameters as needed

		cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Use FillPatternType enum

		return cellStyle;
	}

	public static void createOptionalCells(Workbook workbook, Sheet sheet, int rowNo, String[] cellValue,
			short fontSize) {
		Row row = sheet.createRow(rowNo);
		CellStyle cellStyle = getCellEmptyStyle(workbook, true, fontSize);

		for (int i = 0; i < cellValue.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(cellValue[i]);
		}
	}

	private static CellStyle getCellEmptyStyle(Workbook workbook, boolean bold, short fontSize) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(bold);
		font.setFontHeightInPoints(fontSize);
		style.setFont(font);
		return style;
	}

	@SuppressWarnings("resource")
	public void createOptionalCells(String filePath, int rowNo, String[] cellValue) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		Row row = sheet.createRow(rowNo);

		// Create a cell style with the desired formatting (bold and font size)
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 10);
		cellStyle.setFont(font);

		for (int i = 0; i < cellValue.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(cellValue[i]);
		}

		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			workbook.write(outputStream);
		}
	}

	public static Cell createCell(XSSFSheet sheet, int row, int col, Object value, boolean isHeader) {
	        Row sheetRow = sheet.getRow(row);
	        if (sheetRow == null) {
	            sheetRow = sheet.createRow(row);
	        }

	        Cell cell = sheetRow.createCell(col);
	        cell.setCellValue(String.valueOf(value));

	        if (isHeader) {
	            // Set styles for headers, if needed
	            // Example: You can set a bold font for headers
	            CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
	            Font headerFont = sheet.getWorkbook().createFont();
	            headerFont.setBold(true);
	            headerStyle.setFont(headerFont);
	            cell.setCellStyle(headerStyle);
	        }

	        return cell;
	    }
	

@SuppressWarnings("deprecation")
public static void setCellStyleToNumeric(Workbook workbook, Sheet sheet, int startRow, List<Object> rowData, int numericCellindexes[], boolean headerRow, CellStyle cs) {
			Row row = sheet.createRow(startRow);
		    for (int i = 0; i < rowData.size(); i++) {
		        Cell cell = row.createCell(i);
		        final int cellIndex = i; 
		        boolean exists = Arrays.stream(numericCellindexes).anyMatch(x -> x == cellIndex);
				if (exists) {
					Object obj = rowData.get(i);
					cell.setCellType(CellType.NUMERIC);
						cell.setCellValue(Double.parseDouble((String) obj));
				} else {
					setCellValue(cell, rowData.get(i));
				}
				cell.setCellStyle(cs);
			}
		}
	


@SuppressWarnings("deprecation")
public static void setCellStyleToNumericandFloat(Workbook workbook, Sheet sheet, int startRow, List<Object> rowData, int[] numericCellIndexes,  boolean headerRow, CellStyle cs) {
    Row row = sheet.createRow(startRow);
    DataFormat format = workbook.createDataFormat();

    CellStyle numericCellStyle = workbook.createCellStyle();
    numericCellStyle.setDataFormat(format.getFormat("0.00")); // Ensuring two decimal places for numeric values
    numericCellStyle.cloneStyleFrom(cs);

    CellStyle floatCellStyle = workbook.createCellStyle();
    floatCellStyle.setDataFormat(format.getFormat("0.0")); // Ensuring one decimal place for float values
    floatCellStyle.cloneStyleFrom(cs);

    for (int i = 0; i < rowData.size(); i++) {
        Cell cell = row.createCell(i);
        Object obj = rowData.get(i);
        int index = i;
        boolean isNumeric = Arrays.stream(numericCellIndexes).anyMatch(x -> x == index);

        if (isNumeric ) {
            cell.setCellType(CellType.NUMERIC);

            if ("0".equals(obj)) {
                cell.setCellValue(0.00);
                cell.setCellStyle(numericCellStyle);
            } else {
            	DecimalFormat df = new DecimalFormat("0.00");
                try {
                	LOG.info("======1======"+rowData.get(i));
                    double value = ((Number) obj).doubleValue();
                    LOG.info("======2======"+value);
                    cell.setCellValue(df.format(value));
                    cell.setCellStyle(numericCellStyle);
                } catch (Exception e) {
                    try {
                        double value = Double.parseDouble(obj.toString());
                        LOG.info("======2======"+value);
                        cell.setCellValue(df.format(value));
                        cell.setCellStyle(numericCellStyle);
                    } catch (NumberFormatException ex) {
                        cell.setCellValue(obj.toString());
                    }
                }
            }
        } else {
            setCellValue(cell, rowData.get(i));
            cell.setCellStyle(cs);
        }
    }
}

public static void mergeCells(XSSFWorkbook workbook, XSSFSheet sheet, Row row, int cellNo, String mergeRange,
		String cellValue, boolean border, CellStyle cellStyle) {
	Cell cell = row.createCell(cellNo);
	cellStyle.setAlignment(HorizontalAlignment.CENTER); // Use HorizontalAlignment enum
	cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	cell.setCellStyle(cellStyle);
	cell.setCellValue(cellValue);

	sheet.addMergedRegion(CellRangeAddress.valueOf(mergeRange));

	if (border) {
		RegionUtil.setBorderTop(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN, CellRangeAddress.valueOf(mergeRange), sheet);
	}

}

	

public static CellStyle createStyleByIndexColor(Workbook workbook, boolean bold, int fontWeight, IndexedColors indexColor) {
	
	Font headerFont = workbook.createFont();
	headerFont.setFontName("Calibri");
	headerFont.setFontHeightInPoints((short) fontWeight);

	if (bold) {
		headerFont.setBold(true);
	}

	CellStyle cellStyle = workbook.createCellStyle();
	cellStyle.setFont(headerFont);
	cellStyle.setBorderBottom(BorderStyle.THIN);
	cellStyle.setBorderTop(BorderStyle.THIN);
	cellStyle.setBorderRight(BorderStyle.THIN);
	cellStyle.setBorderLeft(BorderStyle.THIN);

	if (bold) {
		cellStyle.setFillForegroundColor(indexColor.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	
    return cellStyle;
}

	public static void createRowsWithMergeRows(Workbook workbook, Sheet sheet, int rowNo, List<Object> rowValue,
			boolean bold, CellStyle cs, String... extraParams) {// Create the header row
		Row rowHeader = sheet.createRow(rowNo);
	
		// Define a CellStyle for centering the header (both horizontal and vertical)
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER); // Horizontal centering
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Vertical centering
		// Set the font to bold if required
		if (bold) {
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerStyle.setFont(headerFont);
		}
		// Apply the headerStyle to all header cells
		for (int i = 0; i < rowValue.size(); i++) {
			Cell cell = rowHeader.createCell(i);
			cell.setCellStyle(headerStyle); // Set the header style
			setCellValue(cell, rowValue.get(i)); // Set the value of the cell
		}
		// Merge cells across multiple rows (for example, for the first 3 rows) in
		// specific columns
		if (extraParams.length > 0) {
			// Check if 'extraParams' contains the rows to merge (for example, ["3"] means
			// merge first 3 rows in columns)
			for (String param : extraParams) {
				int rowsToMerge = Integer.parseInt(param); // Number of rows to merge
				if (rowsToMerge > 1) {
					// Iterate over the columns you want to merge (e.g., 0, 1, 2 for the first 3
					// columns)
					for (int col = 0; col < rowValue.size(); col++) {
						// Merge the first `rowsToMerge` rows for the current column
						sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo + rowsToMerge - 1, col, col));
					}
				}
			}
		}
		// Apply general borders (top, bottom, left, right) to all cells in the row
		for (int i = 0; i < rowValue.size(); i++) {
			Cell cell = rowHeader.getCell(i);
			// Set top and bottom borders for all cells
			cs.setBorderTop(BorderStyle.THIN);
			cs.setBorderBottom(BorderStyle.THIN);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(cs);
		}
	}
}
