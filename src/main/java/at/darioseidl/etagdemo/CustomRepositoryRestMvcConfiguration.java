package at.darioseidl.etagdemo;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.webmvc.HttpHeadersPreparer;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
@EnableConfigurationProperties(RepositoryRestProperties.class)
public class CustomRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

	public CustomRepositoryRestMvcConfiguration(
			ApplicationContext context,
			@Qualifier("mvcConversionService") ObjectFactory<ConversionService> conversionService) {
		super(context, conversionService);
	}

	@Override
	public HttpHeadersPreparer httpHeadersPreparer() {
		return new CustomHttpHeadersPreparer(auditableBeanWrapperFactory());
	}
}
