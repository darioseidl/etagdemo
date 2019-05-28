package at.darioseidl.etagdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "with-value", types = Child.class)
public interface ChildOpenProjection {

    String getName();

    Parent getParent();

    @Value("Mr. #{target.name}")
    String getFullName();
}
