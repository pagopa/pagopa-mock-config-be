package it.gov.pagopa.mockconfig.entity;

import it.gov.pagopa.mockconfig.model.enumeration.HttpMethod;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "mock_resource")
public class MockResourceEntity implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "subsystem_url")
    private String subsystemUrl;

    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "http_method")
    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "name")
    private String name;

    @Column(name = "archetype_id", insertable = false, updatable = false)
    private String archetypeId;

    @OneToMany(targetEntity = MockRuleEntity.class, fetch = FetchType.EAGER, mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MockRuleEntity> rules;

    @ManyToMany(targetEntity = ResourceTagEntity.class)
    @JoinTable(name = "mock_resource_tag",
            joinColumns = {@JoinColumn(name = "mock_resource_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    private List<ResourceTagEntity> tags;

    @OneToOne(targetEntity = ArchetypeEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "archetype_id")
    private ArchetypeEntity archetype;
}
