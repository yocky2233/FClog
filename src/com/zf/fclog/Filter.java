package com.zf.fclog;

import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Filter {

	public void readFile(String file,String account,String password) {
		jxl.Workbook readwb = null;
		try {
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			InputStream instream = new FileInputStream("./Log/"+file);
			readwb = Workbook.getWorkbook(instream);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet readsheet = readwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
//			int rsColumns = readsheet.getColumns();
			int rsColumns = 4;
			System.out.println("总列数："+rsColumns);
			// 获取Sheet表中所包含的总行数
			int rsRows = readsheet.getRows();
			System.out.println("总行数："+rsRows);
			// 获取指定单元格的对象引用
			for (int i = 1; i < rsRows; i++) {
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = readsheet.getCell(j, i);
					System.out.print(cell.getContents() + " ");
				}
				System.out.println();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	/*
	 *读取过滤下载的文件，进行提bug 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
