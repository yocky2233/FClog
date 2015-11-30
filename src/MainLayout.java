import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;


public class MainLayout {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ��һ���֣���ʼ������
		Display display = new Display(); // ����Display���ʵ��
		final Shell shell = new Shell(display, SWT.MIN); // ������Display��shell��ʵ��,SWT.MIN�޷����
		shell.setBounds(500, 500, 570, 360); // ���ô��ڴ�С
		shell.setText(" Hello World "); // ���ô��ڱ���
		shell.setImage(new Image(display, "./th_Tumblr_Black_Pen.png")); // ����������ͼƬ
		//�ڵ�ǰ�����д�������
		Group group = new Group(shell,SWT.VERTICAL);
		group.setBounds(10, 10, 470, 100);
		group.setText("���������˺�");

		shell.layout();
		shell.open();//�򿪴���
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
