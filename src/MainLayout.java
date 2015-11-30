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
		// 第一部分：初始化窗口
		Display display = new Display(); // 创建Display类的实例
		final Shell shell = new Shell(display, SWT.MIN); // 创建该Display的shell类实例,SWT.MIN无法最大化
		shell.setBounds(500, 500, 570, 360); // 设置窗口大小
		shell.setText(" Hello World "); // 设置窗口标题
		shell.setImage(new Image(display, "./th_Tumblr_Black_Pen.png")); // 给窗口设置图片
		//在当前窗口中创建分组
		Group group = new Group(shell,SWT.VERTICAL);
		group.setBounds(10, 10, 470, 100);
		group.setText("设置禅道账号");

		shell.layout();
		shell.open();//打开窗口
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
