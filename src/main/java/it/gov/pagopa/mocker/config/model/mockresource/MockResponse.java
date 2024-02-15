package it.gov.pagopa.mocker.config.model.mockresource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * The model that define the mocked response that will be generated by Mocker
 * if the rule condition are all verified.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The mocked response that will be generated by Mocker if the rule condition are all verified.")
public class MockResponse implements Serializable {

    @JsonProperty("id")
    @Schema(description = "The unique identifier of the mock response", example = "26e05a1f-9621-4e24-a57d-28694ff30306")
    private String id;

    @JsonProperty("body")
    @Schema(description = "The response body in Base64 format related to the mock response.", example = "eyJtZXNzYWdlIjoiT0shIn0=")
    private String body;

    @JsonProperty("status")
    @Schema(description = "The HTTP response status related to the mock response.", example = "200")
    @NotNull(message = "The HTTP status to be assigned to the mocked response cannot be null.")
    @Positive(message = "The HTTP status to be assigned to the mocked response must be greater than 0.")
    private Integer status;

    @JsonProperty("headers")
    @Schema(description = "The list of header to be set to mock response.")
    @NotNull(message = "The header to be assigned the mocked response for the mock rule cannot be null.")
    @Valid
    private List<ResponseHeader> headers;

    @JsonProperty("injected_parameters")
    @Schema(description = "The list of parameters that will be injected from request body to response body by Mocker.")
    @NotNull(message = "The parameter to be injected in the mocked response for the mock rule cannot be null.")
    @Valid
    private List<String> parameters;


    @Hidden
    public void setIdIfNull(String id) {
        if (this.id == null) {
            this.id = id;
        }
    }
}