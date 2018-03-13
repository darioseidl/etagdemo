package at.darioseidl.etagdemo;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "with-parent", types = Child.class)
public interface ChildProjection {

    String getName();

    Parent getParent();
}
