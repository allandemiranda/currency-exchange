package app.bank.dummy.configurations;

import app.bank.dummy.projections.CurrencyProjection;
import app.bank.dummy.projections.UserProjection;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.ProjectionDefinitionConfiguration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(final @NonNull RepositoryRestConfiguration repositoryRestConfiguration, final CorsRegistry corsRegistry) {
    final ProjectionDefinitionConfiguration projectionConfiguration = repositoryRestConfiguration.getProjectionConfiguration();
    projectionConfiguration.addProjection(CurrencyProjection.class);
    projectionConfiguration.addProjection(UserProjection.class);

    repositoryRestConfiguration.getExposureConfiguration().disablePatchOnItemResources();
  }
}
