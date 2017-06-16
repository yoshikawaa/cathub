package com.example.app.search.issues.view;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.example.app.search.issues.entity.Assignee;
import com.example.app.search.issues.entity.Issue;
import com.example.app.search.issues.entity.Label;

@Component("issuesExcelView")
public class IssuesExcelView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            Workbook book,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Issue> issues = (List<Issue>) model.get("issues");

        Sheet sheet = book.createSheet("issues");

        final AtomicInteger row = new AtomicInteger(0);
        final AtomicInteger cell = new AtomicInteger(0);

        // create header row
        final String[] headers = { "No.", "Title", "Labels", "Milestone", "State", "Assignees" };
        CellStyle style = headerStyle(book.createCellStyle(), book.createFont());
        Row currentRow = row(sheet, row, cell);
        for (String header : headers) {
            cell(currentRow, cell, style).setCellValue(header);
        }

        // create data rows
        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);

            style = dataRowStyle(book.createCellStyle(), i);
            currentRow = row(sheet, row, cell);
            cell(currentRow, cell, style)
                    .setCellValue(String.join("#", issue.getRepository(), issue.getNumber().toString()));
            cell(currentRow, cell, style).setCellValue(issue.getTitle());
            cell(currentRow, cell, style).setCellValue(
                    String.join(",", issue.getLabels().stream().map(Label::getName).collect(Collectors.toList())));
            cell(currentRow, cell, style)
                    .setCellValue((issue.getMilestone() == null) ? null : issue.getMilestone().getTitle());
            cell(currentRow, cell, style).setCellValue(issue.getState());
            cell(currentRow, cell, style).setCellValue(String.join(",",
                    issue.getAssignees().stream().map(Assignee::getLogin).collect(Collectors.toList())));
        }

        // auto shaping
        IntStream.range(0, headers.length).forEach(i -> sheet.autoSizeColumn(i, true));
        sheet.setAutoFilter(new CellRangeAddress(0, issues.size(), 0, headers.length));
    }

    private Row row(Sheet sheet, AtomicInteger row, AtomicInteger cell) {
        cell.set(0);
        return sheet.createRow(row.getAndIncrement());
    }

    private Cell cell(Row currentRow, AtomicInteger cell, CellStyle style) {
        Cell currentCell = currentRow.createCell(cell.getAndIncrement());
        currentCell.setCellStyle(style);
        return currentCell;
    }

    private CellStyle defaultStyle(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    @SuppressWarnings("deprecation")
    private CellStyle headerStyle(CellStyle style, Font font) {
        style = defaultStyle(style);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.index);
        style.setFont(font);
        return style;
    }

    @SuppressWarnings("deprecation")
    private CellStyle dataRowStyle(CellStyle style, int index) {
        style = defaultStyle(style);
        style.setFillForegroundColor(
                (Math.floorMod(index, 2) == 0) ? IndexedColors.LIGHT_TURQUOISE.index : IndexedColors.WHITE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return style;
    }

}
