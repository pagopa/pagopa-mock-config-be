package it.gov.pagopa.mockconfig.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.gov.pagopa.mockconfig.model.AppInfo;
import it.gov.pagopa.mockconfig.model.ProblemJson;
import it.gov.pagopa.mockconfig.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Validated
public class HomeController {

  @Value("${server.servlet.context-path:/}")
  String basePath;
  
  @Value("${application.name}")
  private String name;

  @Value("${application.version}")
  private String version;

  @Value("${application.environment}")
  private String environment;

  @Autowired
  HealthCheckService healthCheckService;


  /**
   * @return redirect to Swagger page documentation
   */
  @Hidden
  @GetMapping("")
  public RedirectView home() {
    if (!basePath.endsWith("/")) {
      basePath += "/";
    }
    return new RedirectView(basePath + "swagger-ui.html");
  }
  
  /**
   * Return the application running status
   *
   * @return the objct containing the status information
   */
  @Operation(summary = "Return the application running status", security = {@SecurityRequirement(name = "ApiKey"), @SecurityRequirement(name = "Authorization")}, tags = {"Home"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppInfo.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
          @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
          @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
          @ApiResponse(responseCode = "500", description = "Service unavailable", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class)))})
  @GetMapping("/info")
  public ResponseEntity<AppInfo> healthCheck() {

      AppInfo info = AppInfo.builder()
              .name(name)
              .version(version)
              .environment(environment)
              .dbConnection(healthCheckService.checkDBConnection())
              .build();
      return ResponseEntity.status(HttpStatus.OK).body(info);
  }

}