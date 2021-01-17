package top.shaozuo.geektime.architecture.week03;

import java.util.ArrayList;
import java.util.List;

public abstract class Container extends AbstractComponent {

    protected List<IComponent> subCompoents = new ArrayList<IComponent>();

    public Container(String name) {
        super(name);
    }

    @Override
    public void print() {
        super.print();
        if (!subCompoents.isEmpty()) {
            for (IComponent comp : subCompoents) {
                comp.print();
            }
        }
    }

    public void add(IComponent component) {
        subCompoents.add(component);
    }

}
