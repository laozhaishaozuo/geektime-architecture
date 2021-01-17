package top.shaozuo.geektime.architecture.week03;

public class Main {

    public static void main(String[] args) {
        Window window = new Window("WINDOW窗口");
        window.add(new Picture("LOGO图片"));
        window.add(new Button("登录"));
        window.add(new Button("注册"));
        Frame frame = new Frame("Frame1");
        frame.add(new Label("用户名"));
        frame.add(new TextBox("文本框"));
        frame.add(new Label("密码"));
        frame.add(new PasswordBox("密码框"));
        frame.add(new CheckBox("复选框"));
        frame.add(new TextBox("记住用户名"));
        frame.add(new LinkLabel("忘记密码"));

        window.add(frame);

        window.print();
    }
}
