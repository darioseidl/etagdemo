package at.darioseidl.etagdemo;

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.auditing.AuditableBeanWrapper;
import org.springframework.data.auditing.AuditableBeanWrapperFactory;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.projection.TargetAware;
import org.springframework.data.rest.webmvc.HttpHeadersPreparer;
import org.springframework.data.rest.webmvc.support.ETag;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

public class CustomHttpHeadersPreparer extends HttpHeadersPreparer {

    private final @NonNull AuditableBeanWrapperFactory auditableBeanWrapperFactory;
    private final ConfigurableConversionService conversionService = new DefaultConversionService();

    {
        Jsr310Converters.getConvertersToRegister().forEach(conversionService::addConverter);
    }

    public CustomHttpHeadersPreparer(AuditableBeanWrapperFactory auditableBeanWrapperFactory) {
        super(auditableBeanWrapperFactory);
        this.auditableBeanWrapperFactory = auditableBeanWrapperFactory;
    }

    @Override
    public HttpHeaders prepareHeaders(PersistentEntity<?, ?> entity, Object value) {

        Assert.notNull(entity, "PersistentEntity must not be null!");
        Assert.notNull(value, "Entity value must not be null!");

        // Unproxy dynamic JDK proxy to be able to get version information for ETag
        if (value instanceof TargetAware) {
            TargetAware targetAware = (TargetAware) value;
            value = targetAware.getTarget();
        }

        // Add ETag
        HttpHeaders headers = ETag.from(entity, value).addTo(new HttpHeaders());

        // Add Last-Modified
        getLastModifiedInMilliseconds(value).ifPresent(it -> headers.setLastModified(it));

        return headers;
    }

    private Object getPrivateField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Optional<AuditableBeanWrapper> getAuditableBeanWrapper(Object source) {
        return auditableBeanWrapperFactory.getBeanWrapperFor(source);
    }

    private Optional<Long> getLastModifiedInMilliseconds(Object object) {

        return getAuditableBeanWrapper(object)//
                .flatMap(it -> it.getLastModifiedDate())//
                .map(it -> conversionService.convert(it, Date.class))//
                .map(it -> conversionService.convert(it, Instant.class))//
                .map(it -> it.toEpochMilli());
    }
}
