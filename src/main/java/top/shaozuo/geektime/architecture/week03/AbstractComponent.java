package top.shaozuo.geektime.architecture.week03;

public abstract class AbstractComponent implements IComponent {

    protected String name;

    public AbstractComponent(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.println("print " + getComponentTag() + " (" + name + ")");
    }

    protected String getComponentTag() {
        return getClass().getSimpleName();
    }
}
