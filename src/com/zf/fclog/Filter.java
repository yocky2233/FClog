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
			// ����Workbook����, ֻ��Workbook����
			// ֱ�Ӵӱ����ļ�����Workbook
			InputStream instream = new FileInputStream("./Log/"+file);
			readwb = Workbook.getWorkbook(instream);
			// Sheet���±��Ǵ�0��ʼ
			// ��ȡ��һ��Sheet��
			Sheet readsheet = readwb.getSheet(0);
			// ��ȡSheet������������������
//			int rsColumns = readsheet.getColumns();
			int rsColumns = 4;
			System.out.println("��������"+rsColumns);
			// ��ȡSheet������������������
			int rsRows = readsheet.getRows();
			System.out.println("��������"+rsRows);
			// ��ȡָ����Ԫ��Ķ�������
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
	 *��ȡ�������ص��ļ���������bug 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
